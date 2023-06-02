<script lang="ts" setup>
//中英文
/*
题干：stem
*/
import { reactive, ref } from 'vue'
// import type { FormInstance } from 'element-ui'
import axios from 'axios';
//some test
//视图更新
var window_width = ref(document.body.scrollWidth  + 'px')
var window_height = ref(window.innerHeight + 'px')
//数据定义+声明+初始化

interface question_item {        //需要展示的一道题
  key: string
  stem: string
  type: string
  option: string []

  subproblem:question_item []
  //答案
  key_real: string
  value: string

}

interface question_option_item {           //需要保存的答案
  key: number
  value: string

}

const question_list = reactive<{    //保存待展示的问卷题目
  questions: question_item[]
}>({
  questions: [
  {
      key: '1',
      stem: "请填写学号",
      type: "填空",
      option: [],
      subproblem: [],
      key_real:" ",
      value: " "
    },
    {
      key: '2',
      stem: "请选择性别",
      type: "选择",
      option: ["男","女"],
      subproblem: [],
      key_real:" ",
      value: " "
    },
    {
      key: '5',          //数组
      stem: "手机号",
      type: "数组",
      option: [ ],
      subproblem: [
      {
      key: '5-1',
      stem: "汽车",
      type: "对象",
      option: [ ],
      subproblem: [
        {
        key: '5-1-1',
        stem: "请填写车牌号",
        type: "填空",
        option: [],
        subproblem: [],
        key_real:"汽车",
        value: " "
      },{
        key: '5-1-2',
        stem: "请填写汽车类型",
        type: "填空",
        option: [],
        subproblem: [],
        key_real:"汽车",
        value: " "
      },{
      key: '5-1-3',          //数组基本测试
      stem: "驾驶员姓名",
      type: "数组",
      option: [ ],
      subproblem: [
        {
        key: '5-1-3-1',
        stem: "请填写驾驶员姓名",
        type: "填空",
        option: [],
        subproblem: [],
        key_real:"汽车",
        value: " "
      },{
        key: '5-1-3-2',
        stem: "填写驾驶员姓名",
        type: "填空",
        option: [],
        subproblem: [],
        key_real:"汽车",
        value: " "
      }
      ],
      key_real:"汽车",
      value: " "
    }
      ],
      key_real:"汽车",
      value: " "
    },
      ],
      key_real:"汽车",
      value: " "
    },

  ],
})


//----------------问题增减----------------
//新增问题
const new_question = (item: question_item) => {
  //console.warn(item);
  //新建对象————深拷贝
  //将对象插入列表
  item.subproblem.push(JSON.parse(JSON.stringify(item.subproblem[0])))

  //修改新建的对象的key
  item.subproblem[item.subproblem.length - 1].key = item.subproblem[item.subproblem.length - 1].key.slice(0,item.subproblem[item.subproblem.length - 1].key.length-1) + String(item.subproblem.length)
  //修改对象的子属性
  item.subproblem[item.subproblem.length - 1].subproblem.forEach((value, index, array)=>{
    value.key = item.subproblem[item.subproblem.length - 1].key + value.key.slice(3,value.key.length)
    value.subproblem.forEach((value, index, array)=>{
      value.key = item.subproblem[item.subproblem.length - 1].key + value.key.slice(3,value.key.length)
    })
  });

  //console.warn(item.subproblem);

}
//减少问题
const remove_question = (item: question_item) => {
  //console.warn(item);
  var item_lenght = item.subproblem.length
  if (item_lenght>1){
    item.subproblem.splice(item.subproblem.length-1, 1)
  }

  /*
  const index = question_list.questions.indexOf(item)

  if (index !== -1) {
    question_list.questions.splice(index, 1)
  }
  */
}
//监视器
//通过后端数据更新问卷

//前后端交流
//接收选择填空
function Receive_simple_questions_from_backend(key_real_input, question_info,key_info){
  let question : question_item = {
      key: '1',
      stem: "请填写学号，哈哈哈哈哈哈哈",
      type: "填空",
      option: [],
      subproblem: [],
      key_real: " ",
      value: " "
    }
  //生成key
  if(key_info == ""){
    question.key = String(question_list.questions.length)
  }else{
    //parseInt
    question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
  }
  //生成stem
  question.stem = question_info['题干']
  //生成type
  question.type = question_info['题目类型']
  //生成其他
  if(question.type == "选择"){
    question.option = question_info['选项']
  }
  question.key_real = key_real_input
  //填入数据
  //question_list.questions.push(question)
  return question;
}
//接收简单数组
function Receive_simple_list_from_backend(key_real_input, question_info,key_info){
  let question : question_item = {
      key: '1',
      stem: "请填写学号，哈哈哈哈哈哈哈",
      type: "数组",
      option: [],
      subproblem: [],
      key_real: " ",
      value: " "
    }
  //生成key
  if(key_info == ""){
    question.key = String(question_list.questions.length)
  }else{
    //parseInt
    question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
  }
  //生成stem
  question.stem = key_real_input
  //生成type

  //生成子问题
  question.subproblem.push(
    Receive_simple_questions_from_backend( key_real_input, question_info, question)
  )
  //生成其他
  question.key_real = key_real_input
  //填入数据
  //question_list.questions.push(question)
  return question;
}
//接收对象
function Receive_object_from_backend( key_real_input, sub_question_info, key_info){
  let question : question_item = {
      key: '1',
      stem: "请填写学号，哈哈哈哈哈哈哈",
      type: "对象",
      option: [],
      subproblem: [],
      key_real: " ",
      value: " "
    }
  //生成key
  if(key_info == ""){
    question.key = String(question_list.questions.length)
  }else{
    //parseInt
    question.key = key_info.key + '-' + String(key_info.subproblem.length + 1)
  }
  //生成stem
  question.stem = key_real_input
  //生成type

  //生成子问题
  for  (let key  in sub_question_info) {
      //console.log(response.data[key])
      //判断这一项是否需要处理
      if(key.indexOf('_') == 0 || key.indexOf('!') == 0){
        continue
      }
      if(key.indexOf('_') == 1 ){
        continue
      }
      //开始处理一项
      let value = sub_question_info[key]
      let extra_information = sub_question_info['_' + key]
      //判断数据类型
      if(extra_information['可有多个'] == "True" ){   //为简单数组
          Receive_simple_list_from_backend(key, extra_information,"")
        }else{
          Receive_simple_questions_from_backend(key, extra_information,"")
        }
  }

  //生成其他
  question.key_real = key_real_input
  //填入数据
  //question_list.questions.push(question)
  return question;
}
//接收数组
function Receive_object_list_from_backend(key_real_input, sub_question_info,key_info){
  let question : question_item = {
      key: '1',
      stem: " ",
      type: "数组",
      option: [],
      subproblem: [],
      key_real: " ",
      value: " "
    }
  //生成key
  if(key_info == ""){
    question.key = String(question_list.questions.length)
  }
  //生成stem
  question.stem = key_real_input
  //生成type

  //生成子问题
  question.subproblem.push(
    Receive_object_from_backend( key_real_input, sub_question_info, question)
  )
  //生成其他
  question.key_real = key_real_input
  //填入数据
  //question_list.questions.push(question)
  return question;
}
//---------------------请求问卷----------
//---------------------接收问卷----------
var qing_qiu = '/user?ID=12345'
axios.get(qing_qiu)
  .then(function (response) {
    // 处理成功情况
    for  (let key  in response.data) {
      //console.log(response.data[key])
      //判断这一项是否需要处理
      if(key.indexOf('_') == 0 || key.indexOf('!') == 0){
        continue
      }
      if(key.indexOf('_') == 1 ){
        continue
      }
      //开始处理一项
      let value = response.data[key]
      let extra_information = response.data['_' + key]
      //判断数据类型
      if(typeof value == "object"){         //为对象
        if(extra_information['可有多个'] == "True" ){   //为复杂数组
          Receive_object_list_from_backend(key, value,"")
        }else{
          Receive_object_from_backend( key, value, "")
        }
      }else{                                //为属性
        if(extra_information['可有多个'] == "True" ){   //为简单数组
          Receive_simple_list_from_backend(key, extra_information,"")
        }else{
          Receive_simple_questions_from_backend(key, extra_information,"")
        }
      }
    }
  })
  .catch(function (error) {
    // 处理错误情况
    console.log(error);
  })
  .then(function () {
    // 总是会执行
  });


//---------------------提交--------------
const submitForm = () => {
  //生成answer_item
  let answer_collection = new Object();
  //遍历答案

  //发送answer_item
  let answer_collection_for_send = JSON.stringify(answer_collection);
  const customConfig = {
    headers: {
    'Content-Type': 'application/json'
    }
  };
  axios.post('https://testapi.org/post', answer_collection_for_send, customConfig);

}
//------------------------------分界线--------------------


</script>

<template >

<div class = "base1">
  <div class = "inner1">
{{ question_list }}
</div>
<div class="inner2">
  <el-form
    ref="formRef"
    :model="question_list"
    label-width=auto
    class="demo-dynamic"
  >
    <!--_______________一道题_______________________-->
    <el-space wrap size="large" direction="vertical" alignment = "left">
    <el-form-item
      v-for="(questions, index) in question_list.questions"
      :key="questions.key"
      :label="'questions' + index"
      :prop="'questions.' + index + '.value'"
      style="width: 60vw"
    >
    <el-card  style="width: 100%">
      <p  v-if="questions.type != '数组'">{{ questions.stem }}</p>
      <!--对象-->
      <el-card v-if="questions.type == '对象'" style="width: 100%">
        <!--_______________对象的一道题_______________________-->
        <el-form>
        <el-space wrap size="default" direction="vertical" alignment = "left">
        <el-form-item
          v-for="(sub_questions, index) in questions.subproblem"
          :key="sub_questions.key"
          :label="'sub_questions' + index"
          :prop="'sub_questions.' + index + '.value'"
          style="width: 50vw"
        >
        <el-card style="width: 100%">
          <p v-if="sub_questions.type != '数组'">{{ sub_questions.stem }}</p>
          <!--子对象 的 子数组-->

              <!--_______________对象的子数组的一道题_______________________-->
          <el-space wrap size="small" direction="vertical" alignment = "left">
            <el-space wrap size="large" direction="horizontal">
            <span  v-if="sub_questions.type == '数组'">{{ questions.stem }} </span>
            <el-button v-if="sub_questions.type == '数组'" @click= "new_question(sub_questions)" type="success" round>新增一条</el-button>
            <el-button v-if="sub_questions.type == '数组'" @click= "remove_question(sub_questions)" type="danger" round>删除最后一条</el-button>
            </el-space >
              <el-form-item
                v-if="sub_questions.type == '数组'"
                v-for="(sub_sub_questions, index) in sub_questions.subproblem"
                :key="sub_sub_questions.key"
                :label="'sub_sub_questions' + index"
                :prop="'sub_sub_questions.' + index + '.value'"
                style="width: 41vw"

              >
                <el-card style="width: 100%">
                  <p >{{ sub_sub_questions.stem }}</p>
                  <!--填空-->
                  <el-input v-if="sub_sub_questions.type == '填空'" v-model="sub_sub_questions.value"  autosize/>
                  <!--选择-->
                  <el-select v-if="sub_sub_questions.type == '选择'" v-model="sub_sub_questions.value" size="large">
                    <el-option
                      v-for="item in sub_sub_questions.option"
                      :key="item"
                      :label="item"
                      :value="item"
                    />
                  </el-select>
                </el-card>
                </el-form-item>
              </el-space>
            <!--_______________对象的子数组的一道题_________结束______________-->

          <!--填空-->
          <el-input v-if="sub_questions.type == '填空'" v-model="sub_questions.value" autosize/>
          <!--选择-->
          <el-select v-if="sub_questions.type == '选择'" v-model="sub_questions.value" size="large">
            <el-option
              v-for="item in sub_questions.option"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-card>

        </el-form-item>
        </el-space>
      </el-form>
        <!--_______________对象的一道题_____结束__________________-->
      </el-card>
      <!--数组-->
      <el-space wrap size="default" direction="vertical" alignment = "left">
      <el-space wrap size="large" direction="horizontal">
        <span  v-if="questions.type == '数组'">{{ questions.stem }}</span>
        <el-button v-if="questions.type == '数组'" @click= "new_question(questions)" type="success" round>新增一条</el-button>
        <el-button v-if="questions.type == '数组'" @click= "remove_question(questions)" type="danger" round>删除最后一条</el-button>
      </el-space>
          <!--_______________数组的一道题_______________________-->

          <el-form>
            <el-space wrap size="small" direction="vertical" alignment = "left">

          <el-form-item
            v-if="questions.type == '数组'"
            v-for="(sub_questions, index) in questions.subproblem"
            :key="sub_questions.key"
            :label="'sub_questions' + index"
            :prop="'sub_questions.' + index + '.value'"
            style="width: 50vw"
          >
            <el-card style="width: 100%">
              <p >{{ sub_questions.stem }}</p>
              <!--数组的子对象-->
              <el-card v-if="sub_questions.type == '对象'" style="width: 100%">
                <el-space wrap size="small" alignment = "left" direction="vertical">
                <!--_______________数组的 子对象的 一道题_______________________-->
                <el-form-item
                  v-for="(sub_sub_questions, index) in sub_questions.subproblem"
                  :key="sub_sub_questions.key"
                  :label="'sub_sub_questions' + index"
                  :prop="'sub_sub_questions.' + index + '.value'"
                >
                <el-card style="width: 100%">

                  <p v-if="sub_sub_questions.type != '数组'">{{ sub_sub_questions.stem }}</p>
                  <!--数组的子对象的子数组-->


                      <!--_______________数组的 子对象的 子数组的 一道题_______________________-->
                  <el-space wrap size="small" direction="vertical" alignment = "left">
                    <el-space wrap size="large" direction="horizontal">
                      <span  v-if="sub_sub_questions.type == '数组'">{{ sub_sub_questions.stem }}</span>
                      <el-button v-if="sub_sub_questions.type == '数组'" @click= "new_question(sub_sub_questions)" type="success" round>新增一条</el-button>
                      <el-button v-if="sub_sub_questions.type == '数组'" @click= "remove_question(sub_sub_questions)" type="danger" round>删除最后一条</el-button>
                    </el-space>
                    <el-form-item
                      v-if="sub_sub_questions.type == '数组'"
                      v-for="(sub_sub_sub_questions, index) in sub_sub_questions.subproblem"
                      :key="sub_sub_sub_questions.key"
                      :label="'sub_sub_sub_questions' + index"
                      :prop="'sub_sub_sub_questions.' + index + '.value'"
                      style="width: 30vw"
                    >
                      <el-card style="width: 100%">
                        <p >{{ sub_sub_sub_questions.stem }}</p>
                        <!--数组的 子对象的 子数组的 填空-->
                        <el-input v-if="sub_sub_sub_questions.type == '填空'" v-model="sub_sub_sub_questions.value" autosize/>
                        <!--数组的 子对象的 子数组的 选择-->
                        <el-select v-if="sub_sub_sub_questions.type == '选择'" v-model="sub_sub_sub_questions.value" size="large">
                          <el-option
                            v-for="item in sub_sub_sub_questions.option"
                            :key="item"
                            :label="item"
                            :value="item"
                          />
                        </el-select>
                      </el-card>
                      </el-form-item>
                  </el-space>
                      <!--_______________数组的 子对象的 子数组的 一道题________结束_______________-->

                  <!--数组的 子对象的 填空-->
                  <el-input v-if="sub_sub_questions.type == '填空'" v-model="sub_sub_questions.value" autosize />
                  <!--数组的 子对象的 选择-->
                  <el-select v-if="sub_sub_questions.type == '选择'" v-model="sub_sub_questions.value" size="large">
                    <el-option
                      v-for="item in sub_sub_questions.option"
                      :key="item"
                      :label="item"
                      :value="item"
                    />
                  </el-select>
                </el-card>
                </el-form-item>
                <!--_______________数组的 子对象的 一道题_____结束__________________-->
              </el-space>
              </el-card>

              <!--数组的子对象结束-->
              <!--数组的子填空-->
              <el-input v-if="sub_questions.type == '填空'" v-model="sub_questions.value" autosize />
              <!--数组的子选择-->
              <el-select v-if="sub_questions.type == '选择'" v-model="sub_questions.value" size="large">
                <el-option
                  v-for="item in sub_questions.option"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-card>
            </el-form-item>
          </el-space>
          </el-form>
        <!--_______________数组的一道题_________结束______________-->
      </el-space>
      <!--填空-->
      <el-input v-if="questions.type == '填空'" v-model="questions.value" autosize />
      <!--选择-->
      <el-select v-if="questions.type == '选择'" v-model="questions.value" size="large">
        <el-option
          v-for="item in questions.option"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
    </el-card>

    </el-form-item>
    </el-space>
    <el-form-item>
      <el-button class = "submit" type="primary" @click="submitForm()">Submit</el-button>
    </el-form-item>
  </el-form>
</div>
</div>

</template>

<style>

.base1{
    overflow: auto;
    height:100vh;
    width:70vw;
    display: -webkit-flex;
    -webkit-flex-direction: column;
 }
  .inner1{

    width: 100%;
    height: auto;
    position: relative;
}
.inner2{

    width: 90%;
    position: relative;
}
.submit{
  position: absolute;
    left: 50%;
    top:50%;
    transform: translate(-50%,-50%);
    min-width: 306px;


}
</style>
