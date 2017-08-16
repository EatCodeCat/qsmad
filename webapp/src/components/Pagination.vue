<template>
    <div id="Pagination">
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="page.number + 1"
            :page-size="page.size"
            layout="total, prev, pager, next"
            :total="page.totalElements">
        </el-pagination>
    </div>
</template>
<script>
    export default{
        props: {
            page:{
                default:{
                    totalElements: 0,
                    number: 0,
                    size: 0,
                }
            }
        },
        created(){
        },
        methods: {
            handleCurrentChange(page){
                this.$emit('current-change', page -1);
            }
        },
        components: {}
    }
</script>
<style scope>
</style>
