<template>
    <el-row class="tac">
        <el-row>
            <el-menu theme="dark" :default-active="'1'" class="el-menu-demo" mode="horizontal" :router="true"
                     @select="handleSelect">
                <el-menu-item index="task">任务</el-menu-item>

                <el-menu-item index="2">我的信息</el-menu-item>
            </el-menu>
        </el-row>
        <el-row style="margin-top: 10px">
            <el-col :span="24">
                <el-col :span="24" class="content-wrapper">
                    <transition name="fade" mode="out-in">
                        <router-view></router-view>
                    </transition>
                </el-col>
            </el-col>

        </el-row>
    </el-row>
</template>
<script>

    export default {
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        },
        mounted() {
            this.$refs.menuContainer.style.height = (window.innerHeight - 60) + "px";
            this.$refs.contentContainer.style.height = (window.innerHeight - 60) + "px";
        }
    }

</script>

<style scoped lang="scss">

    .el-menu--horizontal.el-menu--dark .el-submenu .el-menu-item.is-active, .el-menu-item.is-active {
        color: #fff;
        background: #20a0ff;
    }

</style>
