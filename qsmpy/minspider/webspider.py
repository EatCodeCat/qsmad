import requests
import logging


class WebSpider:
    def __init__(self, headers):
        self.headers = {
            'accept-encoding': 'gzip, deflate, sdch, br',
            'accept-language': 'zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,nb;q=0.2,sk;q=0.2,zh-TW;q=0.2',
            'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)' +
                          ' Chrome/57.0.2987.98 Safari/537.36'
        }
        self.headers = dict(self.headers, **headers)

    def get_text(self, url):
        r = requests.get(url, headers=self.headers, verify=False)
        return r.text

    def get_json(self, url, **param):
        resp = self.get(url, param)
        return resp.json()

    def get(self, url, param=None):
        logging.debug(url)
        r = requests.get(url, param, headers=self.headers, verify=False)
        return r

    def post(self, url, data=None, json=None):
        return requests.post(url, data, json, headers=self.headers, verify=False)

    def set_headers(self, item):
        self.headers = dict(self.headers, **item)
