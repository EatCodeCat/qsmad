import Vue from "vue";
import Router from "vue-router";
import home from "@/views/home";
import login from "@/views/login";
import task from "@/views/task";
import edittask from "@/views/edittask.vue";
Vue.use(Router)

const router = new Router({
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
                    path: '/edittask/:id?',
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
router.beforeEach((to, from, next) => {
    if (!localStorage['Authorization'] && to.path != '/login' ) {
        alert("请登录！")
        next({path: '/login'})
    }
    else{
        next()
    }
})
export default router;
