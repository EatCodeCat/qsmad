<template>
    <div id=task>

        <el-row style="margin-bottom: 10px;">
            <el-col :span="20">
                <strong style="line-height: 36px">qsm广告自动投注任务列表</strong>
            </el-col>
            <el-col :span="4">
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
        <div>

        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                baseUrl: '/api/task',
                tableData: [],
                status:{0:'暂停', 1 :'正在执行',2 :'停止', 3 :'等待执行'}
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
            }
        },
        components: {}
    }
</script>
<style scope>
    #task {
        background-color: #fff;
        padding: 10px;
    }
</style>
