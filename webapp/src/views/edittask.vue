<template>
    <el-form ref="ruleForm" :rules="rules" :model="form" label-width="80px"
             style="width: 700px;margin: 0 auto">
        <el-form-item label="名称" prop="taskName">
            <el-input v-model="form.taskName"></el-input>
        </el-form-item>
        <el-form-item label="关键字" prop="goodsKey">
            <el-input v-model="form.goodsKey"></el-input>
        </el-form-item>
        <el-form-item label="商品ID" prop="goodsList[0]">
            <el-col :span="6">
                <el-input v-model="goodsList[0]"></el-input>
            </el-col>
            <el-col :span="4">
                <el-button style="margin-left: 10px;" @click="addGoods" type="primary" size="small"
                           icon="plus"></el-button>
            </el-col>
        </el-form-item>
        <el-form-item label=" " v-for="(item, i) in goodsList.slice(1)">
            <el-col :span="6">
                <el-input v-model="goodsList[i]"></el-input>
            </el-col>
            <el-col :span="4">
                <el-button style="margin-left: 10px;" type="primary" @click="removeGoods(i)" size="small"
                           icon="minus"></el-button>
            </el-col>
        </el-form-item>
        <el-form-item label="执行时间" prop="loop_time">
            <el-col :span="11">
                <el-time-picker placeholder="选择时间" v-model="form.loop_time"></el-time-picker>
            </el-col>

        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="$router.go(-1)">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        data() {
            return {

                baseUrl: '/api/task',
                form: {
                    taskName: '',
                    goodsKey: '',
                    loop_time: '',
                    goodsList: ''
                },
                gnId: '',
                goodsList: [],
                rules: {
                    taskName: [
                        {required: true, message: '名称不能为空', trigger: 'blur'},
                    ],
                    goodsKey: [
                        {required: true, message: '关键字不能为空', trigger: 'blur'},
                    ],
                    loop_time: [
                        {type: 'date', required: true, message: '执行时间不能为空', trigger: 'change'},
                    ],
                    goodsList:[
                        {required: true, message: '商品ID不能为空', trigger: 'blur'},
                    ],
                }
            }
        },
        created(){
            var id = this.$route.params.id;
            if (id > 0) {
                this.getById(id).then(res => {

                    this.form = res.data;
                    this.goodsList = this.form.goodsList.split(',')
                    this.form.loop_time = new Date(2017, 7, 1,
                        this.form.hour, this.form.minute, this.form.second
                    )

                })
            }
        },
        methods: {
            removeGoods(i){
                this.goodsList.splice(i, 1)
            },
            addGoods(){
                this.goodsList.push('');
            },
            onSubmit() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        this.form.goodsList = this.goodsList.join(',') || null;

                        if(!this.form.goodsList || this.form.goodsList.length == 0){
                            this.failMsg('商品ID不能为空')
                            return
                        }

                        var hour = this.form.loop_time.getHours();
                        var minute = this.form.loop_time.getMinutes();
                        var second = this.form.loop_time.getSeconds();
                        this.form = {hour, minute, second, ...this.form};

                        if(this.form.id > 0){
                            this.update(this.form).then((res) => {
                                this.successMsg();
                            })
                        }
                        else{
                            this.save(this.form).then((res) => {
                                this.successMsg();
                            })
                        }

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    }
</script>
