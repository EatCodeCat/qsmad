# coding=utf-8
__author__ = 'think'


from mongodao import mongoclient



class VideosModel(mongoclient.MClient):
    def __init__(self):
        super().__init__('yellohub', 'videos')