<script>


export default {
    data() {
        return {
            formKey: '',
            DB_tree_structure: {
                domains: [{
                    key: '1',
                    stem: "请填写学号",
                    type: "填空",
                    option: [],
                    subproblem: [],
                    key_real: "学号",
                    multiple: Boolean(1 != 1),
                    example_value: "42021280",

                }, {
                    key: '2',
                    stem: "请填手机号",
                    type: "填空",
                    option: [],
                    subproblem: [],
                    key_real: "手机号",
                    multiple: Boolean(1 == 1),
                    example_value: "18301605060",
                }, {
                    key: '3',
                    stem: "请填汽车信息",
                    type: "对象",
                    option: [],
                    subproblem: [
                        {
                            key: '3-1',
                            stem: "请填车牌号",
                            type: "填空",
                            option: [],
                            subproblem: [],
                            key_real: "车牌号",
                            multiple: Boolean(1 != 1),
                            example_value: "北pushf111",
                        }, {
                            key: '3-2',
                            stem: "请填驾驶员姓名",
                            type: "填空",
                            option: [],
                            subproblem: [],
                            key_real: "驾驶员",
                            multiple: Boolean(1 == 1),
                            example_value: "贺云飞",
                        }
                    ],
                    key_real: "汽车",
                    multiple: Boolean(1 == 1),
                    example_value: " ",
                }, {
                    key: '4',
                    stem: "选择性别",
                    type: "选择",
                    option: ["男", "女"],
                    subproblem: [],
                    key_real: "性别",
                    multiple: Boolean(1 != 1),
                    example_value: "女",
                }]
            },
            //选项设置_显示
            drawer: false,
            direction: 'rtl',
            dialogFormVisible: false,
            //选项设置_暂存选项
            temporary_storage_Options: [
                {sub_stem: "男"},
                {sub_stem: "女"}
            ],
            //key_real修改弹窗
            dialogVisible_keyReal: false,
            temporary_storage_Options_key_real: "",
            //stem修改弹窗
            dialogVisible_stem: false,
            //修改对象描述
            dialogVisible_Object_Description: false

        };

    },
    methods: {
        //设置选项_弃用
        /*
            set_options(DB_cell) {
            this.$alert("<strong><el-form  label-width='100px'><el-form-item v-for='(domain, index) in dynamicValidateForm.domains' :label='index' :key='domain.key' :prop='domains. + index' ><el-input v-model='domain.value'></el-input><el-button @click.prevent='removeDomain(domain)''>删除</el-button></el-form-item><el-form-item><el-button @click='addDomain'>新增域名</el-button></el-form-item></el-form></strong>", '请设置选项', {
              dangerouslyUseHTMLString: true
            });
          },
        */
        //设置选项——————————————————————————————————————————————
        //设置选项——开始
        start_set_options(DB_cell) {
            //清空缓存
            this.temporary_storage_Options.splice(0);
            //将选项导入暂存
            for (let i in DB_cell.option) {
                this.temporary_storage_Options.push({sub_stem: DB_cell.option[i]})
            }
            //显示弹窗
            this.dialogFormVisible = true
        },
        //设置选项新增选项
        new_set_options() {
            this.temporary_storage_Options.push({sub_stem: ""})
        },
        //设置选项删除选项
        delet_set_options(option_item) {
            var index = this.temporary_storage_Options.indexOf(option_item)
            if (index !== -1) {
                this.temporary_storage_Options.splice(index, 1)
            }
        },
        //设置选项——弹窗结束
        save_set_options(DB_cell) {
            //清理保存界面
            DB_cell.option.splice(0);
            //将暂存保存
            for (let i in this.temporary_storage_Options) {
                DB_cell.option.push(this.temporary_storage_Options[i].sub_stem)
            }
            //关闭弹窗
            this.dialogFormVisible = false
        },
        //修改字符串弹窗——控制
        start_change_realkey(DB_cell, what) {
            this.temporary_storage_Options_key_real = ""
            this.temporary_storage_Options_key_real = DB_cell
            if (what == 1) {
                this.dialogVisible_keyReal = true
            } else if (what == 2) {
                this.dialogVisible_stem = true
            } else {
                this.dialogVisible_Object_Description = true
            }
        },
        save_change_realkey(DB_cell) {
            console.log(DB_cell)
            //DB_cell.key_real = this.temporary_storage_Options_key_real
            this.dialogVisible_keyReal = false

        },
        submitForm() {
            console.log("start submitForm")
            //生成answer_item
            let answer_collection = new Object();
            //var answer_collection: any = Object.create(null);
            answer_collection.formKey = this.formKey;
            answer_collection.stru = this.DB_tree_structure.domains;
            //发送answer_item
            let data = JSON.stringify();
            console.log(data)
            this.$api.post("/user/form/createStru", {
                formKey: this.formKey,
                stru: this.DB_tree_structure
            }).then((res) => {
                    this.msgSuccess('提交成功')
                }
            )
        },
        removeDomain(item) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var index = this.DB_tree_structure.domains.indexOf(item)
                if (index !== -1) {
                    this.DB_tree_structure.domains.splice(index, 1)
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        add_obj() {
            this.DB_tree_structure.domains.push({
                key: String(this.DB_tree_structure.domains.length + 1),
                stem: "默认对象描述",
                type: "对象",
                option: [],
                subproblem: [],
                key_real: "默认对象名",
                multiple: Boolean(1 != 1),
                example_value: "",

            });
        },
        add_info() {
            this.DB_tree_structure.domains.push({
                key: String(this.DB_tree_structure.domains.length + 1),
                stem: "默认题干",
                type: "填空",
                option: [],
                subproblem: [],
                key_real: "默认信息名",
                multiple: Boolean(1 != 1),
                example_value: "",

            });
            //this.DB_tree_structure.domains[]

        },
        add_sub_info(DB_cell) {
            DB_cell.subproblem.push({
                key: String(DB_cell.subproblem.length + 1),
                stem: "默认对象描述",
                type: "对象",
                option: [],
                subproblem: [],
                key_real: "默认对象名",
                multiple: Boolean(1 != 1),
                example_value: "",

            });
        },
        remove_sub_info(DB_cell, item) {
            this.$confirm('此操作将永久删除该子文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var index = DB_cell.subproblem.indexOf(item)
                if (index !== -1) {
                    DB_cell.subproblem.splice(index, 1)
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        //从后端获取已有数据库结构
        Obtain_database_structure_from_backend() {
          this.$api.get(`/user/form/queryStru`, {
              params: {
                  formKey: this.formKey
              }
          }).then((res)=>{
                console.log(res)
              if(res.data==null){
                  this.DB_tree_structure={domains:[]}
              }else{
                  this.DB_tree_structure=res.data

              }
              console.log(this.DB_tree_structure)
              }
          )
        },
    },
    mounted() {
        this.formKey = this.$route.query.key
        this.Obtain_database_structure_from_backend()
    }
}

</script>

/* 弃用
<el-button type="text" @click="drawer = true">点击修改选项</el-button>
<el-drawer title="请在下面设置选择题的选项" :visible.sync="drawer" :direction="direction" :before-close="handleClose">
<el-form label-width="auto" :model="DB_cell.option" class="demo-dynamic">
    <el-form-item v-for="(option, index) in DB_cell.option" :label="'选项 ' + (index + 1)" :key="index"
                  :prop="'domains.' + index + '.value'" :rules="{
        required: true, message: '选项不能为空', trigger: 'blur'
      }">
        <el-input v-model="option.value"></el-input>
        <el-button
                @click.prevent="removeDomain(domain)">删除
        </el-button>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" @click="submitForm('dynamicValidateForm')">确认</el-button>
        <el-button @click="addDomain">新增选项</el-button>
        <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
    </el-form-item>
</el-form>
</el-drawer>
*/
<template>
    <div class="parent">
        <div class="div1">
            <el-button class="buttonClass" type="primary" @click="add_info">
                新建&emsp;
                <h1>信息</h1>
            </el-button>
            <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p>
            <el-button class="buttonClass" type="primary" @click="add_obj">
                新建&emsp;
                <h1>对象</h1>
            </el-button>
            <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p>
            <el-button class="buttonClass2" type="success" @click="submitForm">
                <h1>保存所有修改</h1>
            </el-button>
        </div>
        <div class="div2">
            <el-scrollbar style="height: 100% ;width: 100% ">
                {{ formKey }}/br
                {{ DB_tree_structure }}
                <el-form :model="DB_tree_structure" label-width="auto" class="demo-dynamic">
                    <el-form-item v-for="(DB_cell, index) in DB_tree_structure.domains" :label="'信息' + index"
                                  :key="DB_cell.key"
                                  :prop="'domains.' + index + '.value'">
                        <!-------------------------一条信息-------------------------->
                        <el-card v-if="DB_cell.type != '对象'" class="box-card" style="width: 60vw">
                            <div slot="header" class="clearfix">
                                <span style="font-size: large;">信息名： {{ DB_cell.key_real }}</span>
                                <el-button @click="start_change_realkey(DB_cell, 1)"
                                           style="float: right; padding: 3px 0"
                                           type="text">修改信息名
                                </el-button>
                                <el-dialog title="修改信息名称" :visible.sync="dialogVisible_keyReal" width="30%"
                                           :before-close="handleClose">
                                    <span>请再此输入信息名然后点击确认</span>
                                    <el-input v-model="temporary_storage_Options_key_real.key_real"></el-input>
                                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="save_change_realkey(DB_cell)">确 定</el-button>
                  </span>
                                </el-dialog>
                            </div>
                            <div class="text-item">
                                是否为多选：
                                <el-checkbox v-model="DB_cell.multiple"></el-checkbox>
                            </div>
                            <div class="text-item">题干：{{ DB_cell.stem }}
                                <el-button @click="start_change_realkey(DB_cell, 2)"
                                           style="float: right; padding: 3px 0"
                                           type="text">修改题干
                                </el-button>
                                <el-dialog title="修改题干" :visible.sync="dialogVisible_stem" width="30%"
                                           :before-close="handleClose">
                                    <span>请再此输入题干名然后点击确认</span>
                                    <el-input v-model="temporary_storage_Options_key_real.stem"></el-input>
                                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible_stem = false">确 定</el-button>
                  </span>
                                </el-dialog>
                            </div>
                            <div class="text-item">
                                题目类型：
                                <el-radio v-model="DB_cell.type" label="填空">填空题</el-radio>
                                <el-radio v-model="DB_cell.type" label="选择">单选题</el-radio>
                            </div>
                            <!-------------------------选择题额外设计-->
                            <div v-if="DB_cell.type == '选择'" class="text-item">
                                选项：{{ DB_cell.option }}<br>
                                <el-button type="text" @click="start_set_options(DB_cell)">点击修改选项</el-button>

                                <el-dialog title="请在下面设置选择题的选项" :visible.sync="dialogFormVisible">
                                    <el-form :model="DB_cell.option">
                                        <el-form-item v-for="(option, index) in temporary_storage_Options"
                                                      :label="'选项 ' + (index + 1)"
                                                      :key="index" :prop="'domains.' + index + '.value'" :rules="{
                        required: true, message: '选项不能为空', trigger: 'blur'
                      }" :label-width="formLabelWidth">
                                            <el-input v-model="option.sub_stem"></el-input>
                                            <el-button type="danger" icon="el-icon-delete" circle
                                                       @click.prevent="delet_set_options(option)"></el-button>
                                        </el-form-item>
                                        <el-form-item :label-width="formLabelWidth">
                                            <el-button @click="new_set_options" type="success"
                                                       style="position: absolute;left: 50%;top: 50%;transform: translate(-50%, -50%);">
                                                新增选项
                                            </el-button>
                                        </el-form-item>
                                    </el-form>
                                    <div slot="footer" class="dialog-footer">
                                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                                        <el-button type="primary" @click="save_set_options(DB_cell)">确 定</el-button>
                                    </div>
                                </el-dialog>

                            </div>
                            <div class="text-item">
                                <el-button type="danger" icon="el-icon-delete" circle
                                           @click.prevent="removeDomain(DB_cell)"></el-button>
                            </div>

                        </el-card>
                        <!-------------------------一条对象-------------------------->
                        <el-card v-if="DB_cell.type == '对象'" class="box-card" style="width: 60vw">
                            <div slot="header" class="clearfix">
                                <span style="font-size: large;">对象名： {{ DB_cell.key_real }}</span>
                                <el-button @click="start_change_realkey(DB_cell, 1)"
                                           style="float: right; padding: 3px 0"
                                           type="text">修改对象名
                                </el-button>
                                <el-dialog title="修改对象名称" :visible.sync="dialogVisible_keyReal" width="30%"
                                           :before-close="handleClose">
                                    <span>请再此输入对象名然后点击确认</span>
                                    <el-input v-model="temporary_storage_Options_key_real.key_real"></el-input>
                                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="save_change_realkey(DB_cell)">确 定</el-button>
                  </span>
                                </el-dialog>
                            </div>
                            <div class="text-item">
                                是否为多选：
                                <el-checkbox v-model="DB_cell.multiple"></el-checkbox>
                            </div>
                            <div class="text-item">对象描述：{{ DB_cell.stem }}
                                <el-button @click="start_change_realkey(DB_cell, false)"
                                           style="float: right; padding: 3px 0"
                                           type="text">修改对象描述
                                </el-button>
                                <el-dialog title="修改对象描述" :visible.sync="dialogVisible_Object_Description"
                                           width="30%"
                                           :before-close="handleClose">
                                    <span>请再此输入对象描述然后点击确认</span>
                                    <el-input v-model="temporary_storage_Options_key_real.stem" type="textarea"
                                              :autosize="{ minRows: 2, maxRows: 6 }" placeholder="请输入内容">
                                    </el-input>
                                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible_Object_Description = false">确 定</el-button>
                  </span>
                                </el-dialog>
                            </div>
                            <!--————————————————————对象的子题目设置——————————————————————————————-->
                            <div class="text-item">
                                <el-card class="box-card" style="width: 56vw">
                                    子题目设计：
                                    <el-form :model="DB_cell.subproblem" label-width="auto" class="demo-dynamic">
                                        <el-form-item v-for="(sub_DB_cell, index) in DB_cell.subproblem"
                                                      :key="sub_DB_cell.key"
                                                      :prop="'sub_DB_cell.' + index + '.value'"
                                                      style="padding-top: 10px;">

                                            <!-------------------------对象的一条信息-------------------------->
                                            <el-card class="box-card" style="width: 53.5vw">
                                                <div slot="header" class="clearfix">
                                                    <span style="font-size: large;">子信息名： {{
                                                        sub_DB_cell.key_real
                                                        }}</span>
                                                    <el-button @click="start_change_realkey(sub_DB_cell, 1)"
                                                               style="float: right; padding: 3px 0"
                                                               type="text">修改子信息名
                                                    </el-button>
                                                    <el-dialog title="修改子信息名称"
                                                               :visible.sync="dialogVisible_keyReal" width="30%"
                                                               :before-close="handleClose">
                                                        <span>请再此输入子信息名然后点击确认</span>
                                                        <el-input
                                                                v-model="temporary_storage_Options_key_real.key_real"></el-input>
                                                        <span slot="footer" class="dialog-footer">
                              <el-button type="primary" @click="save_change_realkey(sub_DB_cell)">确 定</el-button>
                            </span>
                                                    </el-dialog>
                                                </div>
                                                <div class="text-item">
                                                    是否为多选：
                                                    <el-checkbox v-model="sub_DB_cell.multiple"></el-checkbox>
                                                </div>
                                                <div class="text-item">题干：{{ sub_DB_cell.stem }}
                                                    <el-button @click="start_change_realkey(sub_DB_cell, 2)"
                                                               style="float: right; padding: 3px 0"
                                                               type="text">修改题干
                                                    </el-button>
                                                    <el-dialog title="修改题干" :visible.sync="dialogVisible_stem"
                                                               width="30%"
                                                               :before-close="handleClose">
                                                        <span>请再此输入题干名然后点击确认</span>
                                                        <el-input
                                                                v-model="temporary_storage_Options_key_real.stem"></el-input>
                                                        <span slot="footer" class="dialog-footer">
                              <el-button type="primary" @click="dialogVisible_stem = false">确 定</el-button>
                            </span>
                                                    </el-dialog>
                                                </div>
                                                <div class="text-item">
                                                    题目类型：
                                                    <el-radio v-model="sub_DB_cell.type" label="填空">填空题</el-radio>
                                                    <el-radio v-model="sub_DB_cell.type" label="选择">单选题</el-radio>
                                                </div>
                                                <!-------------------------选择题额外设计-->
                                                <div v-if="sub_DB_cell.type == '选择'" class="text-item">
                                                    选项：{{ sub_DB_cell.option }}<br>
                                                    <el-button type="text" @click="start_set_options(sub_DB_cell)">
                                                        点击修改选项
                                                    </el-button>

                                                    <el-dialog title="请在下面设置选择题的选项"
                                                               :visible.sync="dialogFormVisible">
                                                        <el-form :model="sub_DB_cell.option">
                                                            <el-form-item
                                                                    v-for="(option, index) in temporary_storage_Options"
                                                                    :label="'选项 ' + (index + 1)" :key="index"
                                                                    :prop="'domains.' + index + '.value'" :rules="{
                                  required: true, message: '选项不能为空', trigger: 'blur'
                                }" :label-width="formLabelWidth">
                                                                <el-input v-model="option.sub_stem"></el-input>
                                                                <el-button type="danger"
                                                                           icon="el-icon-delete" circle
                                                                           @click.prevent="delet_set_options(option)"></el-button>
                                                            </el-form-item>
                                                            <el-form-item :label-width="formLabelWidth">
                                                                <el-button @click="new_set_options" type="success"
                                                                           style="position: absolute;left: 50%;top: 50%;transform: translate(-50%, -50%);">
                                                                    新增选项
                                                                </el-button>
                                                            </el-form-item>
                                                        </el-form>
                                                        <div slot="footer" class="dialog-footer">
                                                            <el-button @click="dialogFormVisible = false">取 消
                                                            </el-button>
                                                            <el-button type="primary"
                                                                       @click="save_set_options(sub_DB_cell)">确 定
                                                            </el-button>
                                                        </div>
                                                    </el-dialog>

                                                </div>
                                                <div class="text-item">
                                                    <el-button type="danger" icon="el-icon-delete" circle
                                                               @click.prevent="remove_sub_info(DB_cell, sub_DB_cell)"></el-button>
                                                </div>

                                            </el-card>
                                        </el-form-item>
                                        <el-card class="box-card" style="width: 53.5vw">
                                            <el-form-item>
                                                <el-button @click="add_sub_info(DB_cell)">新增子信息</el-button>
                                            </el-form-item>
                                        </el-card>

                                    </el-form>
                                </el-card>
                            </div>
                            <div class="text-item">
                                <el-button type="danger" icon="el-icon-delete" circle
                                           @click.prevent="removeDomain(DB_cell)"></el-button>
                            </div>

                        </el-card>

                    </el-form-item>
                </el-form>
            </el-scrollbar>
        </div>
    </div>
</template>

<style>
.parent {
    display: grid;
    grid-template-columns: repeat(1000, 1fr);
    grid-template-rows: repeat(14, 1fr);
    grid-column-gap: 0px;
    grid-row-gap: 0px;
    position: absolute;
    width: 99%;
    height: 99%;
    background-color: brown;

}

.div1 {
    grid-area: 1 / 1 / 3 / 1001;
    background-color: darkgrey;
    width: 100%;
    height: 100%;

    display: -webkit-flex;
    display: flex;
    -webkit-align-items: center;
    align-items: center;
    -webkit-justify-content: center;
    justify-content: center;
}

.div2 {
    grid-area: 3 / 1 / 15 / 1001;
    background-color: #f0f2f5;
    width: 100%;
    height: 100%;
    display: flex;
    /* 水平居中 */
    padding-top: 20px;
    flex-direction: column;
    align-items: center;
}

.demo-dynamic {
    display: flex;
    /* 水平居中 */
    flex-direction: column;
    align-items: center;
}

.show_DB {
    background-color: #f0f2f5;
}

.div3 {
    grid-area: 3 / 619 / 15 / 1001;
    background-color: darkgrey;
}

.buttonClass {
    width: 150px;
    height: 92.7px;
    padding: 0;
}

.buttonClass2 {
    width: 230px;
    height: 92.7px;
    padding: 0;
}


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
    width: 480px;
}

.text-item {
    margin-bottom: 5px;
    /*border:1px solid #000*/
}
</style>
