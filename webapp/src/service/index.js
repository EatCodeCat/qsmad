/**
 * Created by TQ on 2017/6/15.
 */

import  axios from 'axios'
import {Loading} from 'element-ui';
import { Message } from 'element-ui';

class Service {

    constructor(myAxios) {
        if (myAxios) {
            this.axios = myAxios
        }
        else {
            this.axios = axios
        }

    }
    //重构http.post，指定默认错误处理
    post(url, body, options) {

        var loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.post(url, body, options).then((res) => {
            loader.close()
            return res;
        }, (res) => {
            loader.close()
            Message.error('网络错误，请重试');
            console.error(res);
            return {};
        })
    }

    //重构http.get，指定默认错误处理
    get(url, options) {
        var loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.get(url, options).then((res) => {
            loader.close()
            return res;
        }, (res) => {
            loader.close()
            this.$message.error('网络错误，请重试');
            console.error(res);
            return {};
        })
    }

    put(url, body, options) {
        var loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.put(url, body, options).then((res) => {
            loader.close()
            return res;
        }, (res) => {
            loader.close()
            this.$message.error('网络错误，请重试');
            console.error(res);
            return {};
        })
    }

    delete(url, options) {
        var loader = Loading.service({
            text: '加载中...',
            lock: true
        })

        return this.axios.delete(url, options).then((res) => {
            loader.close()
            return res;
        }, (res) => {
            loader.close()
            this.$message.error('网络错误，请重试');
            console.error(res);
            return {};
        })
    }

}

var service = new Service();

//红包
service.grouphbInfoList = (gId) => {
    return service.get("/api/data/chatgroup/grouphbList?gid=" + gId);
}


//微信群数据
service.getgroupList = function () {
    var url = '/api/data/chatgroup/groupList';
    return service.get(url);
};

//地区
service.getDimArea = function () {
    var url = '/api/data/dim/dimArea';
    return service.get(url);
}
//品牌
service.getDimBrand = function () {
    var url = '/api/data/dim/dimBrand';
    return service.get(url);
}
//保存微信群
service.saveGroup = function (entity, cnum = 0) {
    var url = '/api/data/chatgroup/mcreateGroup/' + cnum;
    return service.post(url, entity);
}

//活动列表
service.grouphdList = function (param) {
    var url = '/wxanacms/chatgrouphd/listByPage';
    return service.get(url, {params: param});
}
//红包列表
service.grouphbList = function (gid) {
    var url = '/api/data/chatgroup/grouphbList?gid=' + gid;
    return service.get(url);
}
//红包详情
service.grouphbInfo = function (hbid) {
    var url = '/api/data/chatgroup/grouphbInfo/' + hbid;
    return service.get(url);
}
//保存红包
service.createGrouphb = function (param) {
    var url = '/api/data/chatgroup/createGrouphb'
    return service.post(url, param)
}

//群成员
service.getmemberList = function (gid) {
    var url = '/api/data/chatgroup/memberList/' + gid;
    return service.get(url);
}

//活动列表
service.createGrouphd = function (param) {
    var url = '/api/data/chatgroup/createGrouphd';
    return service.post(url, param);
}

//活动审核
service.auditHd = (result, hdId) => {
    var url = '/wxanacms/chatgrouphd/audit';
    return service.get(url, {params: {result, hdId}})
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
