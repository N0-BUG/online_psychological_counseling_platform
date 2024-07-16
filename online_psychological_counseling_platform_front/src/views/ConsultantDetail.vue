<template>
  <div class="common-layout">
    <el-container>
      <el-header style="height: 60px;width: 100%;background-color: #cce5F3;z-index:2;position: fixed">
        <el-page-header :icon="ArrowLeftBold" style="margin-top: 15px" @back="back">
          <template #content>
            <div class="flex items-center">
              <el-dropdown>
                <el-avatar :size="32" :src="circleUrl" class="mr3"></el-avatar>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="personal">个人主页</el-dropdown-item>
                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <span class="text-large font-600 mr-3"> 贵大心理 </span>
              <span class="text-sm mr-2" style="color: var(--el-text-color-regular)">
          咨询师详情
        </span>
              <el-tag>访问用户:{{ useRoute().params.username }}</el-tag>
            </div>
          </template>
        </el-page-header>
      </el-header>
      <el-container>
        <el-aside width="1000px" style="margin-top: 80px">
          <div style="width: 100%;height:300px">
            <div style="background-color: lightskyblue;
          height: 130px;width: 100%">
            </div>
            <div style="margin-left: 270px;margin-top: -40px;
          font-family: 楷体;
          font-size:30px;
          color: white">{{ useRoute().params.consultantName }}
              <span style="margin-left: 40px">性别:{{ consultant.consultantSex }}</span>
              <span style="margin-left: 40px">年龄:{{ consultant.consultantAge }}</span>
            </div>
            <div>
              <el-avatar shape="square" :size="200" :src="consultantImageUrl(consultant)"
                         style="margin-left: 50px;margin-top: -70px"></el-avatar>
            </div>
            <div style="margin-left: 270px;margin-top: -100px;font-size: 20px">
              在贵大心理帮助过<span><strong style="font-size: 30px">{{ consultant.consultantNo }}</strong></span>人
            </div>
            <div plain @click="dialogFormVisible = true" class="chat">
              <div style="margin-left: 800px;margin-top: -30px">
                <el-icon size="60" color="skyblue">
                  <Message/>
                </el-icon>
              </div>
              <div style="margin-left:860px ;margin-top: -50px;font-size: 30px">私信</div>
            </div>
            <el-dialog v-model="dialogFormVisible" title="私信" width="500"
                       style="font-family: 楷体">
              <el-form>
                <el-form-item label="私信内容:" :label-width="formLabelWidth">
                  <el-input v-model="pmContent" autocomplete="off"/>
                </el-form-item>
              </el-form>
              <template #footer>
                <div class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="dialogFormVisible = false;send()">
                    发送
                  </el-button>
                </div>
              </template>
            </el-dialog>
            <transition name="fade">
              <el-result v-if="showResult"
                         icon="success"
                         title="发送成功"

              />
            </transition>
          </div>
          <div style="margin-left: 50px;">
            <h1 style="font-size: 25px">基本信息</h1>
            <hr/>
            <h3 style="font-size: 20px">擅长方向</h3>
            <h1 style="letter-spacing: 5px">{{ consultant.consultantType }}</h1>
          </div>
          <div style="margin-left: 50px">
            <h1 style="font-size: 25px">评价反馈</h1>
            <hr/>
            <div v-for="evaluate in evaluates" :key="evaluate.evaluateId"
                 style="margin-top: 10px">
              <span> <el-avatar :size="32" :src="circleUrl" class="mr3"></el-avatar>
              </span>
              <span style="margin-left: 10px;font-size: 20px;display:block;">
                <strong v-if="evaluate.evaluateShow==='yes'">匿名</strong>
                <strong v-else>{{ evaluate.evaluate }}</strong>
              </span>
              <span style="text-align:right;margin-left: 650px;font-size: 20px">{{
                  evaluate.evaluateTime
                }}</span>
              <div style="height: 60px;background-color:#eeeeee;font-size: 20px;
              border-radius: 5px;margin-top:7px;align-items: center;
              display: flex">
                {{ evaluate.evaluateMessage }}
              </div>
            </div>
          </div>
        </el-aside>
        <el-main style="margin-left:1030px;margin-top:80px;
     position: fixed;height: 500px;width: 400px;border: #eeeeee ;
     box-shadow: 0 0 3px rgba(0, 0, 0, 0.3)">
          <h1 style="font-size: 30px">目前我们提供:</h1>

          <!--          <div>-->
          <!--            <el-icon color="skyblue" size="70px" style="margin-left: 60px">-->
          <!--              <Mic/>-->
          <!--            </el-icon>-->
          <!--          </div>-->
          <!--          <div style="margin-left:120px;margin-top: -60px;font-size: 30px">在线文字咨询</div>-->

          <div style="margin-top: 60px">
            <el-icon color="skyblue" size="70px" style="margin-left: 60px">
              <ChatLineSquare/>
            </el-icon>
          </div>
          <div style="margin-left: 130px;margin-top: -60px;font-size: 30px"> 在线文字咨询
          </div>
          <div class="mb-4">
            <el-button @click="res" type="primary" round
                       style="height: 50px;width: 300px;margin-top: 100px;margin-left: 32px">
              立即预约
            </el-button>
          </div>
        </el-main>

      </el-container>
    </el-container>
  </div>

</template>
<script setup>
import router from "../router";

document.title = '咨询师详情页'
import {computed, inject, onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import {ArrowLeftBold, Message} from "@element-plus/icons-vue";
import axios from "axios";
//用户头像
const circleUrl = useRoute().params.circleUrl;
//咨询师头像
const consultantImageUrl = (consultant) => {
  //定义来传给预约组件
  consultantPfp = consultant.consultantPfp
  // 假设后端返回的 consultantPfp 是图片的标识符，你需要构建完整的图片 URL，返回给当前组件
  return `${consultant.consultantPfp}`;
}
//咨询师数组
const consultant = ref([]);
//评价数组
const evaluates = ref([]);
//dialog
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const pmContent = ref('');
const showResult = ref(false);
const pmSender = useRoute().params.username;
const pmRecipient = useRoute().params.consultantName;
let consultantPfp = ref('')
const back = () => {
  router.back()

};
//是否是咨询师
const isConsultant = ref('');
onMounted(() => {
  try {
    const response = axios.get('http://localhost:8091/account/isConsultant', {
      params: {
        username: username
      }
    });
    isConsultant.value = response.data.data
  } catch (error) {
    console.error('Error fetching isConsultant:', error);
  }
})

//获取咨询师详情
const consultantData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/consultant/detail', {
      params: {
        consultantPhone: useRoute().params.consultantPhone
      }
    })
    consultant.value = response.data.data
    // consultantPfp.value = consultant.consultantPfp
  } catch (error) {
    console.error(error)
  }
};
//获取评价信息
const evaluateData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/evaluate/consultant', {
      params: {
        evaluated: useRoute().params.consultantName,
      }
    })
    evaluates.value = response.data.data
  } catch (error) {
    console.error(error)
  }
};

//发送私信
const send = async () => {
  if (!pmContent.value) {
    alert('请输入内容')
    dialogFormVisible.value = true
    return
  }
  try {
    const response = await axios.post('http://localhost:8091/pmessage/send', {
      pmContent: pmContent.value,
      pmSender: pmSender,
      pmRecipient: pmRecipient
    });
    pmContent.value = '';
    if (response.data.code == 200) {
      showResult.value = true;
      setTimeout(() => {
        showResult.value = false;
      }, 1000); // 3秒后隐藏
    }
  } catch (error) {
    console.error(error)
  }
};

//预约
async function res() {
  try {
    const response = await axios.get('http://localhost:8091/reservation/again', {
      params: {
        //用户名查看是否预约过
        reservationUser: pmSender,
        reservationConsultant: pmRecipient
      }
    });
    // 如果返回状态码为 200，则不跳转
    if (response.data.code === 200) {
      alert('您已提交过预约，请等待处理！')
    } else {

      // 跳转到 'application' 页面
      await router.push({

        name: 'reserve',
        params: {
          //传入咨询师姓名头像,用户名
          consultantName: pmRecipient,
          consultantPfp: consultantPfp,
          username: pmSender
        }
      });
    }
  } catch (error) {
    console.error('Error occurred:', error);
  }
}

//登出
function logout() {
  sessionStorage.removeItem('token')
  router.push({
    name: 'login'
  })
}

//个人主页

function personal() {
  if (isConsultant.value === 'no') {
    router.push({
      name: 'home',
    });
  } else router.push({
    name: 'consultantHome',
  });
}

onMounted(() => {
  consultantData()
  evaluateData()
})
</script>

<style scoped>
.common-layout {
  height: 100%;
  width: 100%;
  background-color: #cce5F3;
}

.header {
  font-size: 40px;
  font-family: 楷体;
  margin-left: 100px;
  letter-spacing: 20px;
  margin-top: 20px;
}

hr {
  border-color: blue;
}

.mr3:hover {
  cursor: pointer;
}


.chat:hover {
  cursor: pointer;
  color: slateblue;
}

/*消失*/
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.8s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

.element {
  background-color: lightblue;
  padding: 10px;
}
</style>