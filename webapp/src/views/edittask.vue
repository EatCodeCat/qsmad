<template>
    <el-form ref="form" :model="form" label-width="80px" style="width: 700px;margin: 0 auto">
        <el-form-item label="活动名称">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="关键字">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="商品ID">
            <el-col :span="6">
                <el-input v-model="form.name"></el-input>
            </el-col>
        </el-form-item>
        <el-form-item label="执行时间">
            <el-col :span="11">
                <el-time-picker type="fixed-time" placeholder="选择时间" v-model="form.date2"
                                style="width: 100%;"></el-time-picker>
            </el-col>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                }
            }
        },
        methods: {
            onSubmit() {
                console.log('submit!');
            }
        }
    }
</script>
