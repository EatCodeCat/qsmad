/**
 * Created by TQ on 2017/5/10.
 */

import Vue from "vue";


var host  = 'http://127.0.0.1:5000'

//重构http.post，指定默认错误处理
function post (url, body, options){
    if(host){
        url = host + url;
    }
    return Vue.http.post (url, body).then ((res) =>{
        return res;
    }, (res) =>{
        console.error (res);
        return {};
    })
}
//重构http.get，指定默认错误处理
function get (url, options){
    if(host){
        url = host + url;
    }
    if(options){
        Object.assign (Vue.http.headers.custom, options.headers)
    }

    return Vue.http.get (url, options).then ((res) =>{
        return res;
    }, (res) =>{
    
        console.error (res);
        return {};
    })
}


const fetch_task_list = function(index, fitler){

    return get('/tasklist/page/' + index, {params:fitler})

}

const  updata_save_task = function(model){
    return post('/addtask', JSON.stringify(model))
}

const  delete_task = function(id){
    return get('/deletetask/id/' + id)
}




var fetch = {
    fetch_task_list,
    updata_save_task,
    delete_task
}

export default fetch
