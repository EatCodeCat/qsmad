from flask import Flask, jsonify, render_template, request,make_response
from functools import wraps
from mongodao.taskmodel import TaskModel
import  traceback
import  datetime

taskmodel = TaskModel()

app = Flask(__name__)


def allow_cross_domain(fun):
    @wraps(fun)
    def wrapper_fun(*args, **kwargs):
        rst = make_response(fun(*args, **kwargs))
        rst.headers['Access-Control-Allow-Origin'] = '*'
        rst.headers['Access-Control-Allow-Methods'] = 'PUT,GET,POST,DELETE'
        allow_headers = "Referer,Accept,Origin,User-Agent"
        rst.headers['Access-Control-Allow-Headers'] = allow_headers
        return rst
    return wrapper_fun



@app.route('/')
@allow_cross_domain
def index():
    return render_template('index_prod.html')


@app.route('/deletetask/id/<id>')
@allow_cross_domain
def delete_task(id):
    try:
        taskmodel.delete(id)
        return jsonify(result=0)
    except Exception as e:
        exstr = traceback.format_exc()
        print(exstr)
        return jsonify(result=1)



@app.route('/addtask', methods=['POST'])
@allow_cross_domain
def add_update__task():
    try:
        request.json['update'] =datetime.datetime.now()
        if "_id" not in request.json:
            _id = taskmodel.insert_one(request.json)

        else:
            taskmodel.replace_one(request.json['_id'], request.json)
        return jsonify(result=0)
    except Exception as  e:
        exstr = traceback.format_exc()
        print(exstr)
        return jsonify(result=1)



@app.route('/tasklist/page/<int:index>')
@allow_cross_domain
def task_list(index):
    page = 15
    cursor, count = taskmodel.search_tasks(index, page)
    json_arr = []
    for it in cursor:
        it['_id'] = str(it['_id'])
        json_arr.append(it)
    return jsonify({'data':json_arr, 'total':count})

@app.route('/task/id/<id>')
@allow_cross_domain
def task(id):
    model = taskmodel.get_task_by_id(id)
    model['_id'] = str(model['_id'])
    return jsonify(model)

if __name__ == '__main__':
    app.run(debug=True)