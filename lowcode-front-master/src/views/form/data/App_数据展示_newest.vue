<script>
export default {
    data() {
        return {
            formKey: '',
            drawer: {},
            test: "1234",
            tablename: "学生",
            tableData: [
                {
                    date: '2016-05-03',
                    name: '王小虎',
                    province: '上海',
                    city: '普陀区',
                    address: '上海市普陀区金沙江路 1518 弄',
                    姓名: "贺云飞",
                    邮箱: ["11111", "222222", "333333"],
                    汽车: {车牌号: "1223", 驾驶员: ["HYF", "HYF2"]},
                    专利: [{专利号: "321", 专利发明人: "HWH"}, {专利号: "321_2", 专利发明人: "HWH_2"}]
                }, {
                    date: '2016-05-02',
                    name: '王小虎',
                    province: '上海',
                    city: '1111普陀区',
                    address: '上海市普陀区金沙江路 1518 弄',
                    zip: 200333,
                    邮箱: ["11111", "222222", "333333"],
                    汽车: {车牌号: "1223", 驾驶员: ["HYF", "HYF2"]},
                    专利: [{专利号: "321", 专利发明人: "HWH"}, {专利号: "321_2", 专利发明人: "HWH_2"}],
                    专利222: [{专利号_222: "321", 专利发明人_222: "HWH"}, {
                        专利号_222: "321_2",
                        专利发明人_222: "HWH_2"
                    }]
                },
            ],
            table_structure: [
                {label: "date", type: "普通", sub_info: []},
                {label: "姓名", type: "普通", sub_info: []},
                {label: "邮箱", type: "数组", sub_info: []},
                {
                    label: "汽车",
                    type: "对象",
                    sub_info: [{label: "车牌号", type: "普通", sub_info: []}, {
                        label: "驾驶员",
                        type: "数组",
                        sub_info: []
                    }]
                },
                {
                    label: "专利",
                    type: "对象数组",
                    sub_info: [{label: "专利号", type: "普通", sub_info: []}, {
                        label: "专利发明人",
                        type: "数组",
                        sub_info: []
                    }]
                },
            ],
        }
    },
    methods: {
        formDataQuery() {
            this.$api.get('/user/form/data/query', {
                params: {
                    formKey: this.formKey
                }
            }).then((res)=>{
                console.log(res.data)
                this.tableData=res.data
                }

            )
        },

        //右抽屉显示
        Right_drawer_display(index, key) {
            console.log(this.drawer)
            this.drawer[index + key] = true
            this.$forceUpdate()
            console.log(this.drawer)
        },
        //右抽屉关闭
        Right_drawer_closed(index, key) {
            this.drawer[index + key] = false
            this.$forceUpdate()
        },
        handleDownload(content, name) {
            // 下载保存json文件
            var eleLink = document.createElement("a");
            eleLink.download = name + '.json';
            eleLink.style.display = "none";
            // 字符内容转变成blob地址
            var data = JSON.stringify(content, undefined, 4);
            var blob = new Blob([data], {type: "text/json"});
            eleLink.href = URL.createObjectURL(blob);
            // 触发点击
            document.body.appendChild(eleLink);
            eleLink.click();
            // 然后移除
            document.body.removeChild(eleLink);
        }


    },
    mounted() {
        this.formKey = this.$route.query.key || this.$route.params.key
        this.formDataQuery()
    }
}

</script>

<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h1>{{ tablename }}
                    <el-button @click="handleDownload(tableData,tablename)" type="success" plain
                               style="margin-left: 30px;">
                        将信息保存到本地{{ key }}
                    </el-button>
                </h1>

            </div>
            <div v-for="(info_need_show, index) in tableData" class="text item">
                <el-descriptions :title="tablename + '信息_' + index" direction="vertical" border>
                    <el-descriptions-item v-for="(value, key) in info_need_show" :label="key">
                        <span v-if="typeof value == 'string'">{{ value }}</span>
                        <div v-if="value.constructor == Array">
                            <div v-if="typeof value[0] == 'string'">
                                <p class="box-card" v-for="(sub_info_need_show, index) in value" style="margin: 2px;">
                                    第{{ index + 1 }}个{{ key }}： {{ sub_info_need_show }}</p>
                            </div>
                            <!---------------是对象数组-->
                            <div v-else>
                                <div v-for="(sub_value, sub_key) in value[0]">
                                    <div v-if="typeof sub_value == 'string'">
                                        <el-tag style="margin: 4px;">{{ sub_key }}： {{ sub_value }}</el-tag>
                                    </div>
                                    <div v-else>
                                        <el-tag v-for="(sub_info_need_show, index) in sub_value" style="margin: 2px;"
                                                effect="plain">
                                            第{{ index + 1 }}个{{ sub_key }}： {{ sub_info_need_show }}
                                        </el-tag>

                                    </div>
                                </div>
                                <el-button @click="Right_drawer_display(index, key)" type="primary"
                                           style="margin-left: 16px;">
                                    点我查看所有{{ key }}
                                </el-button>
                                <el-drawer :title="'所有' + key" :visible.sync="drawer[String(index) + key]"
                                           @close="Right_drawer_closed(index, key)" size="38.2%">
                                    <el-card class="box-card" v-for="(sub_info_need_show, index) in value"
                                             style="margin: 5px;">
                                        第{{ index + 1 }}个{{ key }}：
                                        <div v-for="(sub_value, sub_key) in sub_info_need_show">
                                            <div v-if="typeof sub_value == 'string'">
                                                <el-tag style="margin: 4px;">{{ sub_key }}： {{ sub_value }}</el-tag>
                                            </div>
                                            <div v-else>
                                                <el-tag v-for="(sub_info_need_show, index) in sub_value"
                                                        style="margin: 2px;" effect="plain">
                                                    第{{ index + 1 }}个{{ sub_key }}： {{ sub_info_need_show }}
                                                </el-tag>

                                            </div>


                                        </div>
                                    </el-card>
                                </el-drawer>
                            </div>
                        </div>
                        <!---------------是对象-->
                        <div v-if="value.constructor == Object">
                            <div v-for="(sub_value, sub_key) in value">
                                <div v-if="typeof sub_value == 'string'">
                                    <el-tag style="margin: 4px;">{{ sub_key }}： {{ sub_value }}</el-tag>
                                </div>
                                <div v-else>
                                    <el-tag v-for="(sub_info_need_show, index) in sub_value" style="margin: 2px;"
                                            effect="plain">
                                        第{{ index + 1 }}个{{ sub_key }}： {{ sub_info_need_show }}
                                    </el-tag>

                                </div>


                            </div>

                        </div>

                    </el-descriptions-item>
                </el-descriptions>

            </div>
        </el-card>


    </div>
</template>

<style>
.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: auto;
}
</style>
