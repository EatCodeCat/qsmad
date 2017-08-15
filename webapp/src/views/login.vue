<template>
    <div class="login">
        <h3 style="text-align: center">qsm任务管理系统</h3>
        <el-form label-width="80px" :model="form">
            <el-form-item label="账号">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="success">登录</el-button>
            </el-form-item>

        </el-form>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                baseUrl: '',
                form: {}
            }
        },
        created(){
        },
        methods: {},
        components: {}
    }
</script>
<style scope>
    .login {
        margin: 0 auto;
        width: 400px;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -200px;
        margin-top: -200px;
        border: 1px solid #ddddff;
        padding-right: 30px;
        background-color: #ffffff;
        box-shadow: 1px 1px 2px 2px #c4c4c4;
    }

    .login button {
        width: 60%;
        display: block;
        margin: 0 auto;
        position: relative;
        left: 20px;
    }

</style>
