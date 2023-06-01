<script lang="ts">
//中英文
/*
题干：stem
*/
//some test
//视图更新
//数据定义+声明+初始化
/*
interface question_item {        //需要展示的一道题
      key: string
      stem: string
      type: string
      option: string[]

      subproblem: question_item[]
      //答案
      key_real: string
      value: string

    }
interface question_option_item {           //需要保存的答案
      key: number
      value: string

    }
*/

export default {
    name: 'S',
    data() {
        //保存待展示的问卷题目
        const question_list = {
            questions: [
                {
                    key: '1',
                    stem: "请填写学号",
                    type: "填空",
                    option: [],
                    subproblem: [],
                    key_real: "学号",
                    value: " "
                },
                {
                    key: '2',
                    stem: "请选择性别",
                    type: "选择",
                    option: ["男", "女"],
                    subproblem: [],
                    key_real: "性别",
                    value: " "
                },
                {
                    key: '5',          //数组
                    stem: "专利",
                    type: "数组",
                    option: [],
                    subproblem: [
                        {
                            key: '5-1',
                            stem: "专利",
                            type: "对象",
                            option: [],
                            subproblem: [
                                {
                                    key: '5-1-1',
                                    stem: "请填写专利号",
                                    type: "填空",
                                    option: [],
                                    subproblem: [],
                                    key_real: "专利号",
                                    value: " "
                                }, {
                                    key: '5-1-3',          //数组基本测试
                                    stem: "专利发明人",
                                    type: "数组",
                                    option: [],
                                    subproblem: [
                                        {
                                            key: '5-1-3-1',
                                            stem: "请填写专利发明人姓名",
                                            type: "填空",
                                            option: [],
                                            subproblem: [],
                                            key_real: "专利发明人",
                                            value: " "
                                        },
                                    ],
                                    key_real: "专利发明人",
                                    value: " "
                                }
                            ],
                            key_real: "专利",
                            value: " "
                        },
                    ],
                    key_real: "专利",
                    value: " "
                }, {     //邮箱
                    key: '6',
                    stem: "请填写邮箱",
                    type: "数组",
                    option: [],
                    subproblem: [
                        {     //邮箱
                            key: '6-1',
                            stem: "请填写邮箱",
                            type: "填空",
                            option: [],
                            subproblem: [],
                            key_real: "邮箱",
                            value: " "
                        }
                    ],
                    key_real: "邮箱",
                    value: " "
                }, {
                    key: '7',
                    stem: "汽车",
                    type: "对象",
                    option: [],
                    subproblem: [
                        {
                            key: '7-1',
                            stem: "请填写车牌号",
                            type: "填空",
                            option: [],
                            subproblem: [],
                            key_real: "车牌号",
                            value: " "
                        }, {
                            key: '7-2',
                            stem: "请填写汽车类型",
                            type: "填空",
                            option: [],
                            subproblem: [],
                            key_real: "汽车类别",
                            value: " "
                        }, {
                            key: '7-3',          //数组基本测试
                            stem: "驾驶员",
                            type: "数组",
                            option: [],
                            subproblem: [
                                {
                                    key: '7-3-1',
                                    stem: "请填写驾驶员姓名",
                                    type: "填空",
                                    option: [],
                                    subproblem: [],
                                    key_real: "驾驶员姓名",
                                    value: " "
                                },
                            ],
                            key_real: "驾驶员姓名",
                            value: " "
                        }
                    ],
                    key_real: "汽车",
                    value: " "
                },
            ],
        }
        const new_question = (item) => {
            //console.warn(item);
            //新建对象————深拷贝
            //将对象插入列表
            item.subproblem.push(JSON.parse(JSON.stringify(item.subproblem[0])))

            //修改新建的对象的key
            item.subproblem[item.subproblem.length - 1].key = String(item.subproblem.length)

            //console.warn(item.subproblem);

        }
        //减少问题
        const remove_question = (item) => {
            //console.warn(item);
            var item_lenght = item.subproblem.length
            if (item_lenght > 1) {
                item.subproblem.splice(item.subproblem.length - 1, 1)
            }

            /*
            const index = question_list.questions.indexOf(item)

            if (index !== -1) {
              question_list.questions.splice(index, 1)
            }
            */
        }
        return {
            formKey: '',

            question_list: question_list,
            new_question: new_question,
            remove_question: remove_question,
            dialogFormStart: true,
            WeChat_signal: "",
            answer_collection: {},
        }
    },
    methods: {
        //----------------问题增减----------------
        //新增问题
        new_question(item) {
            console.warn(item);
            //新建对象————深拷贝
            //将对象插入列表
            item.subproblem.push(JSON.parse(JSON.stringify(item.subproblem[0])))

            //修改新建的对象的key
            item.subproblem[item.subproblem.length - 1].key = item.subproblem[item.subproblem.length - 1].key.slice(0, item.subproblem[item.subproblem.length - 1].key.length - 1) + String(item.subproblem.length)
            //修改对象的子属性
            item.subproblem[item.subproblem.length - 1].subproblem.forEach((value, index, array) => {
                value.key = item.subproblem[item.subproblem.length - 1].key + value.key.slice(3, value.key.length)
                value.subproblem.forEach((value, index, array) => {
                    value.key = item.subproblem[item.subproblem.length - 1].key + value.key.slice(3, value.key.length)
                })
            })

            //console.warn(item.subproblem);
        },
        //减少问题
        remove_question(item) {
            //console.warn(item);
            var item_lenght = item.subproblem.length
            if (item_lenght > 1) {
                item.subproblem.splice(item.subproblem.length - 1, 1)
            }

            /*
            const index = question_list.questions.indexOf(item)

            if (index !== -1) {
              question_list.questions.splice(index, 1)
            }
            */
        },
        //监视器
        //通过后端数据更新问卷

        //前后端交流
        //接收选择填空
        /**
         * 生成一个基本的填空题或选择题
         * question_info ：{
      key: '1',
      stem: "请填写学号",
      type: "填空",
      option: [],
      subproblem: [],
      key_real: "学号",
      multiple: Boolean(1 != 1),
      example_value: "42021280",

    }

         * @author 贺云飞
         *
         */
        Receive_simple_questions_from_backend(question_info, key_info) {
            let question = JSON.parse(JSON.stringify(question_info))
            delete question.multiple
            delete question.example_value
            question.value = " "
            /*
            {
              key: '1',
              stem: "请填写学号，哈哈哈哈哈哈哈",
              type: "填空",
              option: [],
              subproblem: [],
              key_real: " ",
              value: " "
            }
            */
            //生成key
            if (key_info == "") {
                question.key = String(this.question_list.questions.length)
            } else {
                //parseInt
                question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
            }
            return question;
        },
        //接收简单数组
        /**
         * 生成一个只包含基本的填空题或选择题的数组题
         * @author 贺云飞
         *
         */
        Receive_simple_list_from_backend(question_info, key_info) {
            let question = {
                key: '1',
                stem: "请填写学号，哈哈哈哈哈哈哈",
                type: "数组",
                option: [],
                subproblem: [],
                key_real: " ",
                value: " "
            }
            //生成key
            if (key_info == "") {
                question.key = String(this.question_list.questions.length)
            } else {
                //parseInt
                question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
            }
            //生成stem和key_real
            question.stem = question_info.stem
            question.key_real = question_info.key_real
            //生成子问题
            question.subproblem.push(
                this.Receive_simple_questions_from_backend(question_info, question)
            )
            //填入数据
            //question_list.questions.push(question)
            return question;
        },
        //接收对象
        Receive_object_from_backend(question_info, key_info) {
            let question = {
                key: '1',
                stem: "汽车",
                type: "对象",
                option: [],
                subproblem: [],
                key_real: " ",
                value: " "
            }
            //生成key
            if (key_info == "") {
                question.key = String(this.question_list.questions.length)
            } else {
                //parseInt
                question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
            }
            //生成stem和key_real
            question.stem = question_info.stem
            question.key_real = question_info.key_real
            //生成子问题
            question_info.subproblem.forEach(
                (sub_question_info, index) => {
                    //console.log(response.data[key])
                    //开始处理一项
                    //判断数据类型
                    if (sub_question_info.multiple) {   //为简单数组
                        question.subproblem.push(this.Receive_simple_list_from_backend(sub_question_info, question))
                    } else {
                        question.subproblem.push(this.Receive_simple_questions_from_backend(sub_question_info, question))
                    }
                }
            );

            //填入数据
            //question_list.questions.push(question)
            return question;
        },
        //接收对象数组
        Receive_object_list_from_backend(question_info, key_info) {
            let question = {
                key: '1',
                stem: " ",
                type: "数组",
                option: [],
                subproblem: [],
                key_real: " ",
                value: " "
            }
            //生成key
            if (key_info == "") {
                question.key = String(this.question_list.questions.length)
            }
            //生成stem和key_real
            question.stem = question_info.stem
            question.key_real = question_info.key_real
            //生成子问题
            question.subproblem.push(
                this.Receive_object_from_backend(question_info, question)
            )
            //填入数据
            //question_list.questions.push(question)
            return question;
        },
        //从后端接收问卷数据的顶层模块
        Receive_questionnaire_form_backend(DB_cell_list) {
            //清空原有列表
            this.question_list.questions = []
            //添加新列表
            DB_cell_list.forEach(
                (sub_question_info, index) => {
                    //console.log(response.data[key])
                    //开始处理一项
                    //判断数据类型
                    if (sub_question_info.type == "对象") {         //为对象
                        if (sub_question_info.multiple) {   //为复杂数组
                            this.question_list.questions.push(this.Receive_object_list_from_backend(sub_question_info, ""))
                        } else {
                            this.question_list.questions.push(this.Receive_object_from_backend(sub_question_info, ""))
                        }
                    } else {
                        if (sub_question_info.multiple) {   //为简单数组
                            this.question_list.questions.push(this.Receive_simple_list_from_backend(sub_question_info, ""))
                        } else {
                            this.question_list.questions.push(this.Receive_simple_questions_from_backend(sub_question_info, ""))
                        }
                    }
                }
            );
        },
        submit_form() {
            console.log("aasaaaaaaaaaaaaaaaaaaa")
            //生成answer_item
            let answer_collection = this.answer_collection;
            this.question_list.questions.forEach(
                (question_info, index) => {
                    //简单题
                    if (question_info.type == "选择" || question_info.type == "填空") {
                        answer_collection[question_info.key_real] = question_info.value
                    }
                    //简单数组
                    if (question_info.type == "数组" && question_info.subproblem[0].type != "对象") {
                        answer_collection[question_info.key_real] = []
                        for (let sub_question of question_info.subproblem) {
                            answer_collection[question_info.key_real].push(sub_question.value)
                        }
                    }
                    //对象
                    if (question_info.type == "对象") {
                        answer_collection[question_info.key_real] = {}
                        for (let sub_question of question_info.subproblem) {
                            (answer_collection[question_info.key_real])[sub_question.key_real] = sub_question.value
                        }
                    }
                    //对象数组
                    if (question_info.type == "数组" && question_info.subproblem[0].type == "对象") {
                        answer_collection[question_info.key_real] = []
                        for (let sub_obj of question_info.subproblem) {
                            console.log(sub_obj)
                            var sub_obj_for_submit = new Object();
                            for (let sub_question of sub_obj.subproblem) {

                                if (sub_question.type == "选择" || sub_question.type == "填空") {
                                    sub_obj_for_submit[sub_question.key_real] = sub_question.value
                                } else {
                                    sub_obj_for_submit[sub_question.key_real] = []
                                    for (let sub_sub_question of sub_question.subproblem) {
                                        sub_obj_for_submit[sub_question.key_real].push(sub_sub_question.value)
                                    }
                                }

                            }
                            answer_collection[question_info.key_real].push(sub_obj_for_submit)
                        }
                    }


                });
            //向后端传送数据
            console.log(answer_collection)
            console.log("start submitForm")

            //发送answer_item
            this.$api.post("/user/form/data/create", {
                formKey: this.formKey,
                pin:this.WeChat_signal,
                originalData: answer_collection
            }).then((res) => {
                    this.msgSuccess('提交成功')
                }
            )
        },


        queryStru() {
            this.$api.get(`/user/form/queryStru`, {
                params: {
                    formKey: this.formKey
                }
            }).then((res) => {
                    console.log(res)
                    // this.DB_tree_structure=res.data
                    this.Receive_questionnaire_form_backend(res.data.domains)
                }
            )
        },
        /**
         * 开始填写前询问微信号,处理已填写的问卷
         * @anther hyf
         */
        Ask_WeChat_before_investigation() {
            this.$api.get(`/user/form/data/queryDataById`, {
                params: {
                    weChatSignal:this.WeChat_signal,
                    formKey: this.formKey
                }
            }).then((res) => {
                    console.log("success")
                this.Ask_WeChat_before_investigation_111(res.data)

                }
            )
        },
        /**
         * 处理已填写的问卷答案，删去已填问题
         * @anther hyf
         */
        Ask_WeChat_before_investigation_111(anwser_list) {
            this.dialogFormStart = false
            // this.queryStruById()
            //发送微信号接受已填写的数据
            /*
            var anwser_list = {
                date: '2016-05-03',
                name: '王小虎',
                province: '上海',
                city: '普陀区',
                address: '上海市普陀区金沙江路 1518 弄',
                姓名: "贺云飞",
                邮箱: ["11111", "222222", "333333"],
                汽车: {车牌号: "1223", 驾驶员姓名: ["HYF", "HYF2"]},
                专利: [{专利号: "321", 专利发明人: ["HWH_1_1", "HWH_1_2"]}, {
                    专利号: "321_2",
                    专利发明人: ["HWH_2_1", "HWH_2_2"]
                }],
                学号: "dddd",
            }
            */
            console.log("dasdfweqdwed")
            console.log(anwser_list)
            //更具已填写数据重新删去已填问题
            let index_list_question_needs_deleted = []
            this.question_list.questions.forEach(
                (question_info, index) => {
                    console.log("开始检查")
                    console.log(this.question_list.questions[index])
                    //简单题：key_real重合->直接删掉问题   and   简单数组：key_real重合->直接删掉问题
                    if (question_info.type == "选择" || question_info.type == "填空" || (question_info.type == "数组" && question_info.subproblem[0].type != "对象")) {
                        console.log("这是简单的")
                        let flag = false
                        for (let anwser_key of Object.keys(anwser_list)) {
                            if (question_info.key_real == anwser_key) {
                                flag = true
                            }
                        }
                        //删除
                        if (flag) {
                            index_list_question_needs_deleted.push(index)

                        }
                    }
                    //对象：key_real重合且无更多问题->直接删掉问题、key_real重合且有更多问题->为已做问题填入答案
                    else if (question_info.type == "对象") {
                        console.log("这是对象")
                        //是否有key_real重合
                        let flag = false
                        for (let anwser_key of Object.keys(anwser_list)) {
                            if (question_info.key_real == anwser_key) {
                                flag = true
                            }
                        }
                        var flag_more_questions = false
                        if (flag) {
                            //是否有更多的问题
                            for (let sub_question_item of question_info.subproblem) {
                                for (let anwser_key of Object.keys(anwser_list[question_info.key_real])) {
                                    console.log(anwser_key)
                                    if (sub_question_item.key_real == anwser_key) {
                                        flag_more_questions = true
                                        //是多余的问题->填入已写答案
                                        console.log(anwser_list[question_info.key_real][anwser_key])
                                        sub_question_item.value = anwser_list[question_info.key_real][anwser_key]
                                        //是多余的数组问题->填入已写答案
                                        if (sub_question_item.type == "数组") {
                                            for (let sub_sub_answer_index = 0; sub_sub_answer_index < anwser_list[question_info.key_real][anwser_key].length; sub_sub_answer_index++) {
                                                if (sub_sub_answer_index != 0) {
                                                    this.new_question(sub_question_item)
                                                }
                                                sub_question_item.subproblem[sub_sub_answer_index].value = anwser_list[question_info.key_real][anwser_key][sub_sub_answer_index]
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (flag_more_questions) {
                            //有没有的问题->删除整条题的答案
                            delete anwser_list[question_info.key_real]
                        } else {
                            //没有更多的问题->删除整道题
                            index_list_question_needs_deleted.push(index)
                            return
                        }
                    } else if (question_info.type == "数组" && question_info.subproblem[0].type == "对象") {
                        console.log("这是对象数组")
                        //对象数组：：key_real重合且无更多问题->直接删掉问题、key_real重合且有更多问题->为已做问题填入答案
                        //是否有key_real重合
                        let flag = false
                        for (let anwser_key of Object.keys(anwser_list)) {
                            if (question_info.key_real == anwser_key) {
                                flag = true
                            }
                        }
                        var flag_more_questions = false
                        if (flag) {
                            //是否有更多的问题
                            for (let sub_question_item of question_info.subproblem[0].subproblem) {
                                for (let anwser_key of Object.keys(anwser_list[question_info.key_real][0])) {
                                    if (sub_question_item.key_real == anwser_key) {
                                        flag_more_questions = true
                                        //是多余的问题->填入已写答案
                                        //console.log(anwser_list[question_info.key_real][anwser_key])
                                        sub_question_item.value = anwser_list[question_info.key_real][0][anwser_key]
                                        //是多余的数组问题->填入已写答案
                                        if (sub_question_item.type == "数组") {
                                            for (let sub_sub_answer_index = 0; sub_sub_answer_index < anwser_list[question_info.key_real][0][anwser_key].length; sub_sub_answer_index++) {
                                                if (sub_sub_answer_index != 0) {
                                                    this.new_question(sub_question_item)
                                                }
                                                sub_question_item.subproblem[sub_sub_answer_index].value = anwser_list[question_info.key_real][0][anwser_key][sub_sub_answer_index]
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        if (flag_more_questions) {
                            //有没有的问题->将已填答案填入

                            for (let sub_answer_index = 1; sub_answer_index < anwser_list[question_info.key_real].length; sub_answer_index++) {
                                //创建对象
                                this.new_question(question_info)
                                //修改对象的value
                                for (let sub_question_item of question_info.subproblem[sub_answer_index].subproblem) {
                                    for (let anwser_key of Object.keys(anwser_list[question_info.key_real][sub_answer_index])) {
                                        if (sub_question_item.key_real == anwser_key) {
                                            flag_more_questions = true
                                            //是多余的问题->填入已写答案
                                            //console.log(anwser_list[question_info.key_real][anwser_key])
                                            sub_question_item.value = anwser_list[question_info.key_real][sub_answer_index][anwser_key]
                                            //是多余的数组问题->填入已写答案
                                            if (sub_question_item.type == "数组") {
                                                for (let sub_sub_answer_index = 0; sub_sub_answer_index < anwser_list[question_info.key_real][sub_answer_index][anwser_key].length; sub_sub_answer_index++) {
                                                    if (sub_sub_answer_index != 0) {
                                                        this.new_question(sub_question_item)
                                                    }
                                                    sub_question_item.subproblem[sub_sub_answer_index].value = anwser_list[question_info.key_real][sub_answer_index][anwser_key][sub_sub_answer_index]
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                            //有没有的问题->删除整条题的答案
                            delete anwser_list[question_info.key_real]
                        } else {
                            //没有更多的问题->删除整道题
                            index_list_question_needs_deleted.push(index)
                            return
                        }
                    }
                    //将从后端获得的答案列表存入待提交的答案
                    this.answer_collection = anwser_list
                })
            let index_offset = 0;
            for (let index of index_list_question_needs_deleted) {
                //console.log(index)
                this.question_list.questions.splice(index + index_offset, 1)
                index_offset -= 1
            }

        },


    },
    mounted() {
        this.formKey = this.$route.query.key || this.$route.params.key
        console.log(this.formKey)
        console.log("formkey")
        this.queryStru()
    },


}


//------------------------------分界线--------------------


/*
<el-scrollbar :height="window_height" :width="window_width">
  </el-scrollbar>

*/
</script>

<template>
    <div class="base1">
        <div class="inner1">
            {{ question_list }}
            <el-dialog title="请输入微信号" :visible.sync="dialogFormStart">
                <el-input v-model="WeChat_signal" placeholder="请输入"></el-input>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="Ask_WeChat_before_investigation()">确 定</el-button>
                </div>
            </el-dialog>
        </div>
        <div class="inner2">
            <el-form ref="formRef" :model="question_list" label-width=auto class="demo-dynamic">
                <!--_______________一道题_______________________-->
                <el-space wrap size="large" direction="vertical" alignment="left">
                    <el-form-item v-for="(questions, index) in question_list.questions" :key="questions.key"
                                  :label="'questions' + index" :prop="'questions.' + index + '.value'"
                                  style="width: 60vw">
                        <el-card style="width: 100%">
                            <p v-if="questions.type != '数组'">{{ questions.stem }}</p>
                            <!--对象-->
                            <el-card v-if="questions.type == '对象'" style="width: 100%">
                                <!--_______________对象的一道题_______________________-->
                                <el-form>
                                    <el-space wrap size="default" direction="vertical" alignment="left">
                                        <el-form-item v-for="(sub_questions, index) in questions.subproblem"
                                                      :key="sub_questions.key"
                                                      :label="'sub_questions' + index"
                                                      :prop="'sub_questions.' + index + '.value'" style="width: 50vw">
                                            <el-card style="width: 100%">
                                                <p v-if="sub_questions.type != '数组'">{{ sub_questions.stem }}</p>
                                                <!--子对象 的 子数组-->

                                                <!--_______________对象的子数组的一道题_______________________-->
                                                <el-space wrap size="small" direction="vertical" alignment="left"
                                                          v-if="sub_questions.type == '数组'">
                                                    <el-space wrap size="large" direction="horizontal">
                                                        <span v-if="sub_questions.type == '数组'">{{ questions.stem }} &emsp;</span>
                                                        <el-button v-if="sub_questions.type == '数组'"
                                                                   @click="new_question(sub_questions)"
                                                                   type="success" round>新增一条
                                                        </el-button>
                                                        <el-button v-if="sub_questions.type == '数组'"
                                                                   @click="remove_question(sub_questions)"
                                                                   type="danger" round>删除最后一条
                                                        </el-button>
                                                    </el-space>
                                                    <el-form-item
                                                            v-for="(sub_sub_questions, index) in sub_questions.subproblem"
                                                            :key="sub_sub_questions.key"
                                                            :label="'sub_sub_questions' + index"
                                                            :prop="'sub_sub_questions.' + index + '.value'"
                                                            style="width: 41vw">
                                                        <el-card style="width: 100%">
                                                            <p>{{ sub_sub_questions.stem }}</p>
                                                            <!--填空-->
                                                            <el-input v-if="sub_sub_questions.type == '填空'"
                                                                      v-model="sub_sub_questions.value"
                                                                      autosize/>
                                                            <!--选择-->
                                                            <el-select v-if="sub_sub_questions.type == '选择'"
                                                                       v-model="sub_sub_questions.value"
                                                                       size="large">
                                                                <el-option v-for="item in sub_sub_questions.option"
                                                                           :key="item" :label="item"
                                                                           :value="item"/>
                                                            </el-select>
                                                        </el-card>
                                                    </el-form-item>
                                                </el-space>
                                                <!--_______________对象的子数组的一道题_________结束______________-->

                                                <!--填空-->
                                                <el-input v-if="sub_questions.type == '填空'"
                                                          v-model="sub_questions.value" autosize/>
                                                <!--选择-->
                                                <el-select v-if="sub_questions.type == '选择'"
                                                           v-model="sub_questions.value" size="large">
                                                    <el-option v-for="item in sub_questions.option" :key="item"
                                                               :label="item" :value="item"/>
                                                </el-select>
                                            </el-card>

                                        </el-form-item>
                                    </el-space>
                                </el-form>
                                <!--_______________对象的一道题_____结束__________________-->
                            </el-card>
                            <!--数组-->
                            <el-space wrap size="default" direction="vertical" alignment="left">
                                <el-space wrap size="large" direction="horizontal">
                                    <span v-if="questions.type == '数组'">{{ questions.stem }}&emsp;&emsp;</span>
                                    <el-button v-if="questions.type == '数组'" @click="new_question(questions)"
                                               type="success" round>新增一条
                                    </el-button>
                                    <el-button v-if="questions.type == '数组'" @click="remove_question(questions)"
                                               type="danger" round>删除最后一条
                                    </el-button>
                                </el-space>
                                <!--_______________数组的一道题_______________________-->

                                <el-form>
                                    <el-space wrap size="small" direction="vertical" alignment="left"
                                              v-if="questions.type == '数组'">

                                        <el-form-item v-for="(sub_questions, index) in questions.subproblem"
                                                      :key="sub_questions.key"
                                                      :label="'sub_questions' + index"
                                                      :prop="'sub_questions.' + index + '.value'" style="width: 50vw">
                                            <el-card style="width: 100%">
                                                <p>{{ sub_questions.stem }}</p>
                                                <!--数组的子对象-->
                                                <el-card v-if="sub_questions.type == '对象'" style="width: 100%">
                                                    <el-space wrap size="small" alignment="left" direction="vertical">
                                                        <!--_______________数组的 子对象的 一道题_______________________-->
                                                        <el-form-item
                                                                v-for="(sub_sub_questions, index) in sub_questions.subproblem"
                                                                :key="sub_sub_questions.key"
                                                                :label="'sub_sub_questions' + index"
                                                                :prop="'sub_sub_questions.' + index + '.value'">
                                                            <el-card style="width: 100%">

                                                                <p v-if="sub_sub_questions.type != '数组'">
                                                                    {{ sub_sub_questions.stem }}</p>
                                                                <!--数组的子对象的子数组-->


                                                                <!--_______________数组的 子对象的 子数组的 一道题_______________________-->
                                                                <el-space wrap size="small" direction="vertical"
                                                                          alignment="left"
                                                                          v-if="sub_sub_questions.type == '数组'">
                                                                    <el-space wrap size="large" direction="horizontal">
                                    <span v-if="sub_sub_questions.type == '数组'">{{
                                        sub_sub_questions.stem
                                        }}&emsp;&emsp;</span>
                                                                        <el-button
                                                                                v-if="sub_sub_questions.type == '数组'"
                                                                                @click="new_question(sub_sub_questions)"
                                                                                type="success" round>新增一条
                                                                        </el-button>
                                                                        <el-button
                                                                                v-if="sub_sub_questions.type == '数组'"
                                                                                @click="remove_question(sub_sub_questions)"
                                                                                type="danger" round>删除最后一条
                                                                        </el-button>
                                                                    </el-space>
                                                                    <el-form-item
                                                                            v-for="(sub_sub_sub_questions, index) in sub_sub_questions.subproblem"
                                                                            :key="sub_sub_sub_questions.key"
                                                                            :label="'sub_sub_sub_questions' + index"
                                                                            :prop="'sub_sub_sub_questions.' + index + '.value'"
                                                                            style="width: 30vw">
                                                                        <el-card style="width: 100%">
                                                                            <p>{{ sub_sub_sub_questions.stem }}</p>
                                                                            <!--数组的 子对象的 子数组的 填空-->
                                                                            <el-input
                                                                                    v-if="sub_sub_sub_questions.type == '填空'"
                                                                                    v-model="sub_sub_sub_questions.value"
                                                                                    autosize/>
                                                                            <!--数组的 子对象的 子数组的 选择-->
                                                                            <el-select
                                                                                    v-if="sub_sub_sub_questions.type == '选择'"
                                                                                    v-model="sub_sub_sub_questions.value"
                                                                                    size="large">
                                                                                <el-option
                                                                                        v-for="item in sub_sub_sub_questions.option"
                                                                                        :key="item" :label="item"
                                                                                        :value="item"/>
                                                                            </el-select>
                                                                        </el-card>
                                                                    </el-form-item>
                                                                </el-space>
                                                                <!--_______________数组的 子对象的 子数组的 一道题________结束_______________-->

                                                                <!--数组的 子对象的 填空-->
                                                                <el-input v-if="sub_sub_questions.type == '填空'"
                                                                          v-model="sub_sub_questions.value"
                                                                          autosize/>
                                                                <!--数组的 子对象的 选择-->
                                                                <el-select v-if="sub_sub_questions.type == '选择'"
                                                                           v-model="sub_sub_questions.value"
                                                                           size="large">
                                                                    <el-option v-for="item in sub_sub_questions.option"
                                                                               :key="item" :label="item"
                                                                               :value="item"/>
                                                                </el-select>
                                                            </el-card>
                                                        </el-form-item>
                                                        <!--_______________数组的 子对象的 一道题_____结束__________________-->
                                                    </el-space>
                                                </el-card>

                                                <!--数组的子对象结束-->
                                                <!--数组的子填空-->
                                                <el-input v-if="sub_questions.type == '填空'"
                                                          v-model="sub_questions.value" autosize/>
                                                <!--数组的子选择-->
                                                <el-select v-if="sub_questions.type == '选择'"
                                                           v-model="sub_questions.value" size="large">
                                                    <el-option v-for="item in sub_questions.option" :key="item"
                                                               :label="item" :value="item"/>
                                                </el-select>
                                            </el-card>
                                        </el-form-item>
                                    </el-space>
                                </el-form>
                                <!--_______________数组的一道题_________结束______________-->
                            </el-space>
                            <!--填空-->
                            <el-input v-if="questions.type == '填空'" v-model="questions.value" autosize/>
                            <!--选择-->
                            <el-select v-if="questions.type == '选择'" v-model="questions.value" size="large">
                                <el-option v-for="item in questions.option" :key="item" :label="item" :value="item"/>
                            </el-select>
                        </el-card>

                    </el-form-item>
                </el-space>
                <el-form-item>
                    <el-button class="submit" type="primary" @click="submit_form()">Submit</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<style>
.base1 {
    height: auto;
}

.inner1 {

    width: 100%;
    height: auto;
}

.inner2 {

    width: 90vw;
    display: flex;
    justify-content: center;
    /* 水平居中 */

}

.demo-dynamic {
}

.submit {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    min-width: 306px;


}
</style>
