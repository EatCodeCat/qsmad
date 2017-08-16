/**
 * Created by TQ on 2017/6/15.
 */

import axios from "axios";
import {Loading, Message} from "element-ui";
import Vue from "vue";

class Service {

    constructor(myAxios) {
        if (myAxios) {
            this.axios = myAxios
        }
        else {
            this.axios = axios
        }
        this.axios.interceptors.response.use(function (response) {
            return response;
        },  (error)=> {
            console.log(error)
            Message.error('网络错误，请重试');
            //Vue.router.push('/login')

            this.loader.close();
            // 对响应错误做点什么
            return Promise.reject(error);
        });

    }
    cleanAuth(){
        delete this.axios.defaults.headers.common['Authorization']
    }
    setAuth(){
        if(localStorage['Authorization']){
            this.axios.defaults.headers.common['Authorization'] = localStorage['Authorization'];
        }
    }

    //重构http.post，指定默认错误处理
    post(url, body, options) {

         this.loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.post(url, body, options).then((res) => {
            this.loader.close()
            return res;
        })
    }

    //重构http.get，指定默认错误处理
    get(url, options) {
        this.loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.get(url, options).then((res) => {
            this.loader.close()
            return res;
        })
    }

    put(url, body, options) {
        this.loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.put(url, body, options).then((res) => {
            this.loader.close()
            return res;
        })
    }

    delete(url, options) {
        this.loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.delete(url, options).then((res) => {
            this.loader.close()
            return res;
        })
    }

}

var service = new Service();

//红包
service.login = (param) => {
    return service.post("/api/login", param);
}


service.resource = function (baseUrl) {

    return {
        listByPage(params){
            return service.get(baseUrl + '/listByPage', {params: params})
        },
        save(model){
            return service.post(baseUrl + '/save', model);
        },
        update(model){
            return service.put(baseUrl + '/save', model);
        },
        getById(id){
            return service.get(baseUrl + '/entity/' + id)
        },
        delete(id){
            return service.delete(baseUrl + '/entity/' + id)
        }
    }
}


export  default  {
    install: function (Vue, options) {
        Vue.$service = service
        Vue.prototype.$service = service
    }

};
