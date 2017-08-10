<style scoped>
.layout {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
}

.layout-logo {
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
}

.layout-nav {
    width: 420px;
    margin: 0 auto;
}

.layout-assistant {
    width: 300px;
    margin: 0 auto;
    height: inherit;
}

.layout-breadcrumb {
    padding: 10px 15px 0;
}

.layout-content {
    min-height: 200px;
    margin: 15px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
}

.layout-content-main {
    padding: 10px;
}

.layout-copy {
    text-align: center;
    padding: 10px 0 20px;
    color: #9ea7b4;
}
</style>

<template>
    <div class="layout">
        <Menu mode="horizontal" theme="dark" active-name="1">
            <div class="layout-nav">
                <Menu-item name="1">
                    <Icon type="ios-navigate"></Icon>
                    爬虫任务
                </Menu-item>
            </div>
        </Menu>
        <div class="layout-breadcrumb">
            <Breadcrumb>
                <Breadcrumb-item href="#">首页</Breadcrumb-item>
                <Breadcrumb-item href="#">爬虫任务</Breadcrumb-item>
            </Breadcrumb>
        </div>
        <div class="layout-content">
    
            <Card :bordered="false">
                <p slot="title" style="    height:36px;">
                    <span>搜索</span>
                    <Button @click="addTask" type="primary" style="float: right;">新增任务</Button>
                </p>
                <Form ref="formInline" inline :label-width="80">
                    <Form-item label="任务名称">
                        <Input placeholder="任务名称"></Input>
                    </Form-item>
                    <Form-item label="class名称">
                        <Input placeholder="class名称"></Input>
                    </Form-item>
                    <Form-item label="运行状态">
                        <Select style="width:200px">
                            <Option value="1">完成</Option>
                            <Option value="2">等待执行</Option>
                            <Option value="3">运行中</Option>
                            <Option value="4">未执行</Option>
    
                        </Select>
                    </Form-item>
                    <Form-item label="运行时间">
                        <Date-picker type="date" :options="options1" placeholder="选择日期" style="width: 200px"></Date-picker>
                    </Form-item>
    
                    <Form-item>
                        <Button type="primary">搜索</Button>
                    </Form-item>
                </Form>
            </Card>
            <Table :data="tableData1" :columns="tableColumns1" stripe></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :total="total" :page-size="15" :current="1" @on-change="changePage"></Page>
                </div>
            </div>
        </div>
        <Modal v-model="modal1" title="新增" width="900">
            <TaskEdit :data="model" @close="modal1 = false"></TaskEdit>
            <div slot="footer">
            </div>
        </Modal>
    </div>
</template>

<script>
import TaskEdit from './taskedit.vue'
import fetch from '../service/datafetch.js'

export default {
    components: { TaskEdit },
    data() {
        return {
            model: {},
            total: 0,
            modal1: false,
            options1: {
                shortcuts: [{
                    text: '今天',
                    value() {
                        return new Date();
                    }
                },
                {
                    text: '昨天',
                    value() {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        return date;
                    }
                },
                {
                    text: '一周前',
                    value() {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        return date;
                    }
                }
                ]
            },
            tableData1: [],
            tableColumns1: [{
                title: '序号',
                key: 'index',
                render: (h, params) => {
                    return h('span', params.index + 1);
                }
            },
            {
                title: '任务名称',
                key: 'task_name'
            },
            {
                title: '状态',
                key: 'status',
                render: (h, params) => {
                    const row = params.row;
                    const color = row.status === 1 ? 'blue' : row.status === 2 ? 'green' : 'red';
                    const text = row.status === 1 ? '抓取中' : row.status === 2 ? '抓取完成' : '未开始';
                    return h('Tag', {
                        props: {
                            type: 'dot',
                            color: color
                        }
                    }, text);
                }
            },
            {
                title: '运行Class',
                key: 'task_cls',
            },
            {
                title: '内容等级',
                key: 'warnning_lv',
            },
            {
                title: '执行周期',
                key: 'loop_type',
                render: (h, params) => {
                    var t = params.row.loop_type;
                    var msg = ''
                    if (t == 1) {
                        msg = "立即执行"
                    }
                    else if (t == 2) {
                        msg = params.row.exec_time;
                    }
                    else if (t == 3) {
                        msg = params.row.cron;
                    }
                    return h('div', msg);
                }
            },
            {
                title: '进度',
                key: 'progress',
                render: (h, params) => {
                    var p = 0
                    return h('Progress', {
                        props: {
                            percent: p || 0,
                        }
                    });
                }
            },
            {
                title: '更新时间',
                key: 'update'

            },
            {
                title: '操作',
                key: 'update',
                render: (h, params) => {
                    return h('div', [
                        h('Button', {
                            props: {
                                type: 'primary',
                                size: 'small'
                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                    this.edit(params.index)
                                }
                            }
                        }, '编辑'),
                        h('Button', {
                            props: {
                                type: 'error',
                                size: 'small'
                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                    this.remove(params.index)
                                }
                            }
                        }, '删除'),
                        h('Button', {
                            props: {
                                type: 'success',
                                size: 'small'
                            },

                            on: {
                                click: () => {
                                    this.setStatus(params.index)
                                }
                            }
                        }, '运行')
                    ]);
                }

            }
            ]
        }
    },
    mounted() {
        this.fetchData(0)
    },
    created() {

    },
    methods: {
        addTask() {
            this.model = {};
            this.modal1 = true;
        },
        edit(index) {
            this.model = this.tableData1[index];
            this.modal1 = true;
        },
        fetchData(page) {
            fetch.fetch_task_list(page).then((res) => {
                this.tableData1 = res.body.data;
                this.total = res.body.total;

            })
        },
        remove(index) {

            fetch.delete_task(this.tableData1[index]._id)
            this.tableData1.splice(index, 1);

        },
        changePage(page) {
            this.fetchData(page - 1)
        },
        newtask() {
            this.$router.push('taskedit')
        }
    }
}
</script>
