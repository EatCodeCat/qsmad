import importlib
from collections import deque

import minspider
from mongodao.taskmodel import TaskModel
from taskperistent.mongoperist import MongoPerist
from task.basecrawler import BaseCrawler
from minspider.taskmanger import TaskItem



def execute_all():
    taskmodel = TaskModel()
    task_list = taskmodel.get_all_tasks()
    t_task = [TaskItem(**item) for item in task_list]
    deque_list = deque()
    for item in t_task:
        deque_list += build_task(item)
    m = minspider.TaskManager(deque_list)
    m.do_task()
    return m

def execute_one(item):
    deque_list = deque()
    deque_list += build_task(item)
    m = minspider.TaskManager(deque_list)
    m.do_task()
    return m

def build_task(item, module='task'):
    deque_list = deque()
    mod_task = importlib.import_module('.' + item.task_cls, module)
    task_cls = [getattr(mod_task, it) for it in dir(mod_task) if not it.startswith('_') and hasattr(
        getattr(mod_task, it), '__base__') and getattr(mod_task, it).__base__ == BaseCrawler]
    for cls in task_cls:
        murl = minspider.UrlManager(deque([minspider.UrlItem(urlitem.url) for urlitem in item.url_items]))
        ins = cls(item, murl, MongoPerist())
        deque_list.append(ins)
    return deque_list

if __name__ == "__main__":
    execute_all()
