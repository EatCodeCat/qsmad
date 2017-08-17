<template>
    <div class="login">
        <h3 style="text-align: center">qsm任务管理系统</h3>

        <el-form label-width="80px" :model="form" @submit="login" ref="ruleForm" :rules="rules">
            <el-form-item label="账号" prop="username">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="login">登录</el-button>
            </el-form-item>

        </el-form>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                baseUrl: '',
                form: {},
                rules: {
                    username: [
                        {required: true, message: '账号不能为空', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '密码不能为空', trigger: 'blur'},
                    ]
                }
            }
        },
        created(){
            localStorage.removeItem('Authorization');
            this.$service.cleanAuth();
        },
        methods: {
            login(){
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        this.$service.login(this.form).then(res => {

                            var authResoult = res.data.result
                            localStorage.setItem('Authorization', authResoult)
                            this.$service.setAuth();
                            this.$router.push('/task')

                        }, error=>{
                            this.failMsg('账号密码错误');
                        })
                    } else {


                        console.log('error submit!!');
                        return false;
                    }
                });

            }
        },
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
