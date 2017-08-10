const routers = [{
    path: '/',
    meta: {
        title: ''
    },
    component: (resolve) => require(['./views/index.vue'], resolve)
},
{
    path: '/taskedit',
    meta: {
        title: ''
    },
    component: (resolve) => require(['./views/taskedit.vue'], resolve)
}];
export default routers;