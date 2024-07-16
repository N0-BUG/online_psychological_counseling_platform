<template>
  <div style="background-color: #cce5F3;background-size: 100%;height:100%;width: 100%">
    <div style="padding: 10px;
   ">
      <el-row>
        <el-col :span="6" style="background-color: whitesmoke">
          <el-card style="width: 100%; min-height: 300px; color: #333">
            <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">
              联系人<span style="font-size: 12px">（点击聊天气泡开始聊天）</span>
            </div>
            <div v-for="user in users" :key="user.username" style="padding: 10px 0">
              <span>{{ user.username }}</span>
              <i style="margin-left: 10px; font-size: 16px; cursor: pointer"
                 @click="chatUser = user.username">
                <el-icon>
                  <ChatDotRound/>
                </el-icon>
              </i>
              <span v-if="user.username === chatUser"
                    style="font-size: 15px;color: limegreen; margin-left: 5px">联系中...</span>
            </div>
          </el-card>
          <el-button v-if="isConsultant==='yes'" style="margin-left: 150px;margin-top:40px;font-size: 17px" size="large"
                     type="primary"

                     @click="confirm"
          >
            咨询结束
          </el-button>
        </el-col>
        <el-col :span="18" style="background-color: whitesmoke">
          <div
              style="width: 900px; background-color: white;
                border-radius: 5px; box-shadow: 0 0 10px #ccc;margin: 0 auto 0 5px;">
            <div style="text-align: center; line-height: 50px;">
              在线心理咨询室({{ chatUser }})
            </div>
            <div style="height: 350px; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
            <div style="height: 200px">
              <textarea v-model="text"
                        style="height: 160px; width: 860px; padding: 20px; border: none; border-top: 1px solid #ccc;
                        border-bottom: 1px solid #ccc; outline: none"></textarea>
              <div style="text-align: right; padding-right: 10px">
                <el-button size="mini" type="primary" @click="send">发送</el-button>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>

</template>
<script setup>

import {ref, onMounted} from 'vue'
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import {useRoute} from "vue-router";
import router from "../router";

const isConsultant = useRoute().params.isConsultant;
const user = ref({})
const users = ref([])
const chatUser = ref('')
const text = ref('')
const messages = ref([])
const content = ref('')
const avatarUrl = ref('')
let socket;
const send = () => {
  if (!chatUser.value) {
    ElMessage({type: 'warning', message: "请选择聊天对象"})
    return
  }
  if (!text.value) {
    ElMessage({type: 'warning', message: "请输入内容"})
  } else {
    if (typeof WebSocket === "undefined") {
      console.log("您的浏览器不支持WebSocket")
    } else {

      console.log("您的浏览器支持WebSocket")
      const message = {
        from: user.value.username, to: chatUser.value, text: text.value,
        avatarUrl: avatarUrl.value
      }

      socket.send(JSON.stringify(message))
      messages.value.push({user: user.value.username, text: text.value, avatarUrl: avatarUrl.value})
      createContent(null, user.value.username, text.value, avatarUrl.value)
      text.value = ''
    }
  }
}

const createContent = (remoteUser, nowUser, text, avatarUrl) => {
  let html = '';
  if (nowUser) {
    html = `
      <div class="el-row" style="padding: 5px 0">
        <div class="el-col el-col-22" style="text-align: right; padding-right: 5px">
          <div class="tip left">${text}</div>
        </div>
        <div class="el-col el-col-2">
          <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">
            <img src="${avatarUrl}" style="object-fit: cover;">
          </span>
        </div>
      </div>
    `;
  } else if (remoteUser) {
    html = `
      <div class="el-row" style="padding: 5px 0">
        <div class="el-col el-col-2" style="text-align: right">
           <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">
            <img src="${avatarUrl}" style="object-fit: cover;">
          </span>
        </div>
        <div class="el-col el-col-22" style="text-align: left; padding-left: 5px">
          <div class="tip right">${text}</div>
        </div>
      </div>
    `;
  }
  content.value += html;
}

user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
const init = () => {
  const username = user.value.username

  if (typeof WebSocket === "undefined") {
    console.log("您的浏览器不支持WebSocket")
  } else {
    console.log("您的浏览器支持WebSocket")
    const socketUrl = `ws://localhost:8091/chat/${username}`
    if (socket) {
      socket.close()
      socket = null
    }
    socket = new WebSocket(socketUrl)
    socket.onopen = () => {
      console.log("websocket已打开")
    }
    socket.onmessage = (msg) => {
      console.log("收到数据====" + msg.data)
      const data = JSON.parse(msg.data)
      if (data.users) {
        users.value = data.users.filter(u => u.username !== username)
      } else {
        if (data.from === chatUser.value) {
          messages.value.push(data)
          createContent(data.from, null, data.text, data.avatarUrl)
        }
      }
    }
    socket.onclose = () => {
      console.log("websocket已关闭")
    }
    socket.onerror = () => {
      console.log("websocket发生了错误")
    }
  }
}
const reservationUser = useRoute().params.reservationUser
const reservationConsultant = useRoute().params.reservationConsultant
const overRes = async () => {
  try {
    const response = await axios.post('http://localhost:8091/reservation/overRes', {
      reservationUser: reservationUser,
      reservationConsultant: reservationConsultant
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '操作成功',
      });
      await router.push({
        name: 'consultantRes'
      })
    } else {
      ElMessage({
        type: 'error',
        message: '操作失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '操作失败',
    });
  }
};
const confirm = () => {
  ElMessageBox.confirm(
      '确定要结束该预约吗?',
      '警告',
      {
        confirmButtonText: '是的',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        overRes();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8091/user/chatPfp', {
      params: {
        username: user.value.username,
      }
    });
    if (response.data.code == 200) {
      avatarUrl.value = response.data.data;
    }
  } catch (error) {
    console.log('请求失败');
  }
  init()
})

</script>
<style>
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width: auto;
  display: inline-block !important;
  display: inline;
}

.right {
  background-color: deepskyblue;
}

.left {
  background-color: forestgreen;
}
</style>
