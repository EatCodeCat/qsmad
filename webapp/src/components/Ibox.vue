
<template>
    <div class="ibox">
        <div class="ibox-title" v-show="showTitle">
            <slot  name="title"></slot>
            <div class="ibox-tools">
                <slot  name="tools"></slot>
            </div>
        </div>
        <div class="ibox-content">
            <slot name="content"></slot>
        </div>
    </div>

</template>

<script>

    export default{
        props: {
            showTitle:{
                type: Boolean,
                default:true
            }
        },
        data(){
            return {

            }

        }
    }

</script>

<style scoped lang="scss">
    .ibox {
        clear: both;
        margin-bottom: 10px;
        margin-top: 0;
        padding: 0;
    }
    .ibox-title {
        -moz-border-bottom-colors: none;
        -moz-border-left-colors: none;
        -moz-border-right-colors: none;
        -moz-border-top-colors: none;
        background-color: #ffffff;
        border-color: #C7D8E2;
        border-image: none;
        border-style: solid solid none;
        border-width: 4px 0px 0;
        color: inherit;
        margin-bottom: 0;
        padding: 16px 15px 7px;
        min-height: 48px;
    }
    .ibox-tools {
        display: inline-block;
        float: right;
        margin-top: 0;
        position: relative;
        padding: 0;
    }

    .ibox-content {
        clear: both;
    }
    .ibox-content {
        background-color: #ffffff;
        color: inherit;
        padding: 10px 15px 15px 15px;
        border-color: #e7eaec;
        border-image: none;
        border-style: solid solid none;
        border-width: 1px 0px;
    }
    .ibox-title h5 {
        display: inline-block;
        font-size: 16px;
        margin: 0 0 7px;
        padding: 0;
        text-overflow: ellipsis;
        float: left;
        color: #666;
    }
    .vue-echarts{
        width: 100%;
        height: 100%;
    }

</style>
