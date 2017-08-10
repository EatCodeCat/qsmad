<template>
    <Form ref="model" :model="model" :label-width="80">
    
        <Form-item label="任务名称" prop="name">
            <Row :gutter="8">
                <Col span="10">
                <Input v-model="model.task_name" placeholder="任务名称"></Input>
                </Col>
                <Col span="10">
                <Input v-model="model.host" placeholder="Host"></Input>
                </Col>
            </Row>
        </Form-item>
        <Row>
            <Col span="12">
            <Form-item label="任务Class" prop="mail">
                <Input v-model="model.task_cls" placeholder="任务Class"></Input>
            </Form-item>
            </Col>
            <Col span="12">
            <Form-item label="内容等级" prop="mail">
                <Input-number :max="10" :min="1" v-model="model.warnning_lv"></Input-number>
            </Form-item>
            </Col>
        </Row>
    
        <Form-item label="运行类型" prop="interest">
    
            <Radio-group v-model="model.loop_type">
                <Radio label="1">立即</Radio>
                <Radio label="2">时间</Radio>
                <Radio label="3">重复</Radio>
            </Radio-group>
    
        </Form-item>
    
        <Form-item label="日期" v-show="model.loop_type == 2">
    
            <Date-picker type="datetime" v-model="model.exec_time" :options="options1" placeholder="选择日期"></Date-picker>
    
        </Form-item>
    
        <Form-item label="Cron" v-show="model.loop_type == 3">
    
            <Input v-model="model.cron" placeholder="Cron"></Input>
    
        </Form-item>
    
        <Form-item label="url">
            <Row :gutter="8">
                <Col span="8">
                <Input v-model="url" placeholder="url"></Input>
                </Col>
                <Col span="4"> http://xxxx-{page}
                </Col>
                <Col span="7">
                <Input v-model="b_page" placeholder="开始" style="width:40%"></Input> -
                <Input style="width:40%" v-model="e_page" placeholder="结束"></Input>
                </Col>
                <Col span="4">
                <Button type="primary" @click="addUrl">生成</Button>
                </Col>
            </Row>
        </Form-item>
    
        <Form-item label="urls" prop="desc">
    
            <Table border :columns="urlcol" :data="model.url_items"></Table>
    
        </Form-item>
    
        <Form-item>
            <Button type="primary" @click="handleSubmit('model')">提交</Button>
        </Form-item>
    
    </Form>
</template>
<script>

import fetch from '../service/datafetch.js'
export default {

    props: ['data'],
    data() {
        return {
            model: {
                task_name: '',
                task_cls: '',
                cron: '',
                url_items: [],
                loop_type: 1,
                warnning_lv: 1,
                host: ''
            },
            b_page: '',
            e_page: '',
            url: '',
            urlcol: [{
                title: 'url',
                key: 'url',
            },
            {
                title: '状态',
                key: 'status',
                render: (h, params) => {
                    const row = params.row;
                    const color = row.status === 1 ? 'blue' : row.status === 2 ? 'green' : 'red';
                    const text = row.status === 1 ? '抓取成功' : row.status === 2 ? '未抓取' : '抓取失败';
                    return h('Tag', {
                        props: {
                            type: 'dot',
                            color: color
                        }
                    }, text);
                }
            },
            {
                title: '抓取时间',
                key: 'address'
            },
            {
                title: '操作',
                key: 'action',
                align: 'center',
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
                                    this.show(params.index)
                                }
                            }

                        }, '编辑'),
                        h('Button', {
                            props: {
                                type: 'error',
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                    this.remove(params.index)
                                }
                            }
                        }, '删除')
                    ]);
                }
            }
            ],
            url_items: [],
            options1: {
                shortcuts: [{
                    text: '今天',
                    value() {
                        return new Date();
                    }
                },
                {
                    text: '明天',
                    value() {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24);
                        return date;
                    }
                },
                {
                    text: '一周后',
                    value() {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                        return date;
                    }
                }
                ]
            },
        }
    },
    watch: {
        data(val, oldVal) {
            if (val._id) {
                Object.assign(this.model, val)
            }
            else {
                this.model = {
                    task_name: '',
                    task_cls: '',
                    cron: '',
                    url_items: [],
                    loop_type: 1,
                    warnning_lv: 1,
                    host: ''
                };
            }
        }
    },

    methods: {
        remove(index) {
            this.model.url_items.splice(index, 1);
        },
        addUrl() {

            if (this.b_page && this.e_page) {

                for (var i = this.b_page; i < this.e_page; i++) {
                    this.model.url_items.push({ url: this.url.replace('{page}', i),status: 3 })
                }
            }
            else {
                if(this.url){
                    this.model.url_items.push({ url: this.url, status: 0 })
                }
            }

        },
        handleSubmit(name) {
            fetch.updata_save_task(this.model).then((resp) => {

                if (resp.body.result == 0) {
                    this.$Modal.success({
                        title: '结果',
                        content: "保存成功"
                    });
                }
                else {
                    this.$Modal.error({
                        title: '结果',
                        content: "保存失败"
                    });
                }
                this.$emit('close')
            })
        }
    }
}
</script>