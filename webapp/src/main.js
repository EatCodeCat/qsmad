// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";

import ElementUI from "element-ui";
import "element-ui/lib/theme-default/index.css";
import "./assets/css/common.css";
import VueResouce from "vue-resource";
import Ibox from "./components/Ibox.vue";
import Pagination from "./components/Pagination.vue";
import Service from "./service";
import {dateFormat} from "./utils";

Vue.use(VueResouce);
Vue.use(Service);

Vue.component('ibox', Ibox);
Vue.component('pagination', Pagination);

Vue.use(ElementUI)

Vue.config.productionTip = false

Vue.filter('dateFormat', dateFormat)
Vue.mixin({
    methods: {
        pageCurrentChange(curPage){
            this.listByPage(curPage);
        },
        listByPage(curPage, params){
            this.curPage = curPage;
            if (this.baseUrl) {
                this.$service.resource(this.baseUrl).listByPage(Object.assign({page: curPage}, params)).then((res) => {
                    this.tableData = res.data.content;
                    this.page = res.data;
                })
            }
        },
        save(entity, cb){
            this.$service.resource(this.baseUrl).save(entity).then((res) => {
                this.$message({
                    showClose: true,
                    message: '保存成功',
                    type: 'success'
                })
                if(typeof cb == 'function'){
                    cb();
                }
                else{
                    this.$router.go(-1);
                }

            })
        },
        update(entity){
            this.$service.resource(this.baseUrl).update(entity).then((res) => {
                this.$message({
                    showClose: true,
                    message: '保存成功',
                    type: 'success'
                })
                this.$router.go(-1);
            })
        },
        successMsg(msg = "保存成功"){
            this.$message({
                showClose: true,
                message: msg,
                type: 'success'
            })
        },
        failMsg(msg = "保存失败"){
            this.$message({
                showClose: true,
                message: msg,
                type: 'error'
            })
        },
        del(id){
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$service.resource(this.baseUrl).delete(id).then((res) => {
                    this.$message({
                        showClose: true,
                        message: '删除成功',
                        type: 'success'
                    })
                    this.listByPage(this.curPage);
                })

            })
        },
        getById(id){
            return this.$service.resource(this.baseUrl).getById(id);
        }
    },
    created(){
        if (this.tableData) {
            this.listByPage(0);
        }
    }
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    template: '<App/>',
    components: {App}
})
