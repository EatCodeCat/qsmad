import traceback
from collections import Iterable
from bs4 import BeautifulSoup
from .webspider import WebSpider
from .peristent import Peristenter


class Crawler:
    def __init__(self, urlmanager, perist=Peristenter()):
        self.urlmanager = urlmanager
        self.headers = {}
        self.spider = WebSpider(self.headers)
        self.Soup = BeautifulSoup
        self.items = []
        self.cur_item = {}
        self.perist = perist

    def do_crawl(self):
        for urlitem in self.urlmanager:
            try:
                response = self.spider.get(urlitem.url)
                items = self.parse(response)
                self.item_persistence(items)
                self.items = []
            except Exception as e:
                exstr = traceback.format_exc()
                print(exstr)
                self.urlmanager.cur_url_is_fail()


        #保存结果
        done_result = self.urlmanager.done()

        self.url_item_persistence(done_result)

        return self.items

    def request(self, url, callback, method='GET'):
        response = self.spider.get(url)
        if hasattr(callback, '__call__'):
            return callback(response)
        else:
            return response

    def parse(self, response):
        bs = self.Soup(response.text, 'lxml')
        return bs

    def response_txt_soup(self, response):
        bs = self.Soup(response.text, 'lxml')
        return bs

    def get_arr_frist_el(self, arr):
        if len(arr) > 0:
            return arr[0]
        return {'string': ''}

    def item_persistence(self, items):
        if items is not None:
            for it in items:
                self.perist.item_save(it)

    def url_item_persistence(self, items):
        if items is not None:
            if isinstance(items, dict):
                self.perist.url_item_save(items)
            elif isinstance(items, Iterable):
                for it in items:
                    self.perist.url_item_save(it)
            else:
                self.perist.url_item_save(items)
