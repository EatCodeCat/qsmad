import Vue from "vue";
import Router from "vue-router";
import home from "@/views/home";
import login from "@/views/login";
import task from "@/views/task";
import edittask from "@/views/edittask.vue";
Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: '首页',
            component: home,
            children: [
                {
                    path: '/task',
                    name: '任务',
                    component: task,
                },
                {
                    path: '/edittask',
                    name: 'edittask',
                    component: edittask,
                }

            ],
        },
        {
            path: '/login',
            name: '登录',
            component: login,
        }
    ]
})
