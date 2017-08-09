from mongodao import mongoclient

class TaskModel(mongoclient.MClient):

    def __init__(self):
        super().__init__('mini_task_db', 'crawl_tasks')

    def insert_one(self, item):
       return super().insert_one(item)

    def get_all_tasks(self, ):
        return self.find()

    def get_task_by_id(self, id):
        return self.find_one(_id=self.ObjectId(id))

    def replace_one(self, id, model):
        model["_id"] = self.ObjectId(model["_id"])
        return super().replace_one(model, _id=self.ObjectId(id))

    def update_task(self, update, id):
        return self.update(update, {'_id': self.ObjectId(id)})

    def search_tasks(self, index, page, **filter):
        return self.search(index, page, **filter)

    def find_one_and_replace(self, condit, replace):
        return self.find_one_and_replace(condit, replace)

    def delete(self, id):
        return self.delete(self.ObjectId(id))
