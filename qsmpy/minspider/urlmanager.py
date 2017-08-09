from collections import deque
import logging
from enum import Enum
from datetime import datetime


class CrawlStatus(Enum):
    CRAWL_FAIL = 0
    UN_CRAWL = 1
    CRAWL_SUCCESS = 2


class UrlItem:
    def __init__(self, url, crawl_count=0, status=CrawlStatus.UN_CRAWL, crawl_time=None):

        self.url = url
        self.crawl_time = crawl_time
        self.crawl_count = crawl_count
        self.status = status

    def __eq__(self, other):
        if isinstance(other, UrlItem):
            return self.url == other.url
        else:
            return False

    def get_dict(self):
        temp = self.__dict__.copy()
        temp['status'] = self.__dict__['status'].value
        return temp

    def items(self):
        return self.get_dict()



class UrlManager:
    def __init__(self, crawl_list=[], name=''):
        self.crawl_list = deque([UrlItem(it) for it in crawl_list])
        self.fail_craw_list = []
        self.success_crawl_list = []
        self.name = name
        self.cur_item = None
        self.is_fail = False
        self.total = len(crawl_list)

    def push_crawl_url(self, url_item):

        if url_item not in self.crawl_list:
            self.crawl_list.append(url_item)
            return True
        else:
            return False

    def __iter__(self):
        return self  # 实例本身就是迭代对象，故返回自己

    def __next__(self):
        item = self.get_a_crawl_item()
        if item is not None:
            return self.cur_item
        else:
            raise StopIteration()

    def get_a_crawl_item(self):
        if not self.is_fail and self.cur_item is not None:
            self.cur_item.crawl_time = datetime.now()
            self.cur_item.status = CrawlStatus.CRAWL_SUCCESS
            self.success_crawl_list.append(self.cur_item)
        length = len(self.crawl_list)
        if length > 0:
            self.cur_item = self.crawl_list.popleft()
            self.cur_item.crawl_count += 1
            self.cur_item.crawl_time = datetime.now()
            print('index (%s)-%s' % (length, self.cur_item.url))
            return self.cur_item

    def cur_url_is_fail(self):
        self.cur_item.status = CrawlStatus.CRAWL_FAIL
        self.fail_craw_list.append(self.cur_item)
        self.is_fail = True

    def repeat_cur_url(self):
        self.crawl_list.append(self.curUrl)

    def progress(self):
        return len(self.crawl_list) / self.total

    def done(self):
        logging.debug("done")
        if len(self.crawl_list) > 0:
            logging.warning('还有未爬取url任务!')

        return self.success_crawl_list + self.fail_craw_list


if __name__ == '__main__':
    print(UrlItem('aa').getdict())
