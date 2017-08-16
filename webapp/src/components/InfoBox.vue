<template>
    <div>
        <el-col :span="colSpan">
            <ibox>
                <div slot="title">
                    <h5>{{data.title}}</h5>
                    <span class="label pull-right">{{data.unit}}</span>
                </div>
                <div slot="content" style="min-height: 135px;">
                    <div class="row main-info">
                        <el-col :span="6">
                            <div :class="'icon box-icon'+index"></div>
                        </el-col>
                        <el-col :span="18">
                            <h2 class="primary-light">{{data.mainIndex.value}}<sub style="font-size:9px;">{{data.mainIndex.unit}}</sub></h2>
                        </el-col>
                    </div>
                    <div class="row sub-info" >

                        <el-col class="silver" :span="8" v-for="item in data.subIndex"
                             style="font-size: 12px;margin-top:5px;height: 35px;font-weight: bold;">
                            <small class="stats-label">{{item.text}}</small>
                            <strong>{{item.value}}</strong>
                        </el-col>
                    </div>
                </div>

            </ibox>
        </el-col>
    </div>

</template>

<script>

    export default{

        props: {
            colSpan: {
                default: 6,
                type: Number
            },
            data: {
                type: Object
            },
            index:{
                type:Number,
                default:0
            }
        },
        data(){

            return {}
        }


    }


</script>

<style lang="scss" scoped="">

    .icon{
        width: 45px;
        height: 45px;
        padding: 10px 16px;
        border-radius: 4px;
        font-size: 16px;
        line-height: 1.33;
    }
    .box-icon0 {
        background: url("../assets/images/cj.png");
        background-size: cover;
        background-color: #fdad6a !important;
        border-color: #fdad6a !important;
        color: #FFFFFF !important;
    }
    .sub-info{
        line-height: 25px;
        font-size: 14px;
        strong{
            display: block;
        }
    }

    .box-icon1 {
        background: url("../assets/images/jg.png");
        background-size: cover;
        background-color: #a8dfcb !important;
        border-color: #a8dfcb !important;
        color: #FFFFFF !important;
    }

    .box-icon2 {
        background: url("../assets/images/cc.png");
        background-size: cover;
        background-color: #ecc680 !important;
        border-color: #ecc680 !important;
        color: #FFFFFF !important;
    }
    .box-icon3 {
        background: url("../assets/images/qd.png");
        background-size: cover;
        background-color: #86c8ee !important;
        border-color: #86c8ee !important;
        color: #FFFFFF !important;
    }


</style>
