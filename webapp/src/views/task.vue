<template>
    <div id=task>

        <el-row style="margin-bottom: 10px;line-height: 36px">
            <el-col :span="4">
                <strong>qsm广告自动投注任务列表</strong>
            </el-col>
            <el-col :span="3">
                <el-button @click="dialogFormVisible = true" style="float: left" type="info">登录QSM账号</el-button>
            </el-col>
            <el-col :span="17">
                <el-button @click="$router.push('/edittask')" style="float: right" type="info">新增任务</el-button>
            </el-col>

        </el-row>
        <el-table
            :data="tableData" border style="width: 100%">
            <el-table-column
                type="index"
                label="ID"
                width="60">
            </el-table-column>
            <el-table-column
                prop="taskName"
                label="任务名称">
            </el-table-column>
            <el-table-column
                prop="goodsKey"
                label="关键字">
            </el-table-column>
            <el-table-column
                prop="taskName"
                label="商品ID列表">
                <template scope="scope">
                    <el-tag :type="'primary'" v-for="(item, key) in getGoodsList(scope.row).split(',')"
                            style="margin-left: 3px" :key="key"
                            close-transition>{{item}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                prop="taskResult"
                label="执行结果">
            </el-table-column>
            <el-table-column
                prop="status"
                label="状态">
                <template scope="scope">
                    {{status[scope.row.status]}}
                </template>
            </el-table-column>
            <el-table-column
                prop="status"
                label="等待执行时间">
                <template scope="scope">
                    {{scope.row.hour}}:{{scope.row.minute}}:{{scope.row.second}}
                </template>

            </el-table-column>
            <el-table-column
                prop="status"
                label="运行时间">
                <template scope="scope">
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <el-button
                        size="small"
                        @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="QSM登录" :visible.sync="dialogFormVisible">
            <el-form :model="form" label-width="80px">
                <el-form-item label="用户名称">
                    <el-input v-model="qsmForm.qsmUsername"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="qsmForm.qsmPassword"></el-input>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-col :span="8">
                        <el-input v-model="qsmForm.code"></el-input>
                    </el-col>
                    <el-col :span="12">
                        <img :src="codeSrc"/>
                    </el-col>
                    <el-col :span="4">
                        <el-button @click="reflush">刷新</el-button>
                    </el-col>

                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="qsmLogin">确 定</el-button>
            </div>
        </el-dialog>
    </div>

</template>
<script>
    import ElCol from "element-ui/packages/col/src/col";
    import ElButton from "../../node_modules/element-ui/packages/button/src/button";
    export default {
        data(){
            return {
                baseUrl: '/api/task',
                tableData: [],
                status: {0: '暂停', 1: '正在执行', 2: '停止', 3: '等待执行'},
                dialogFormVisible: false,
                codeSrc: '/api/user/image?t=1',
                qsmForm: {}
            }
        },
        created(){
        },
        methods: {
            handleDelete(i, row){
                this.del(row.id)
            },
            handleEdit(i, row){
                this.$router.push('/edittask/' + row.id)
            },
            getGoodsList(row){
                return row.goodsList || ''
            },
            qsmLogin(){

                this.$service.post("/api/user/qsmlogin/" + this.qsmForm.code, this.qsmForm).then((res) => {
                    if (res.code == 1) {
                        this.successMsg('登录成功')
                    }
                    else {
                        this.failMsg("登录失败")
                    }

                })

            },
            reflush(){
                this.codeSrc = '/api/user/image?t=' + (new Date()).getTime()
            }
        },
        components: {
            ElButton,
            ElCol
        }
    }
</script>
<style scope>
    #task {
        background-color: #fff;
        padding: 10px;
    }
</style>
