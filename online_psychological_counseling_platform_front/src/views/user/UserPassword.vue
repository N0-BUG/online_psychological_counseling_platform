<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "../../router";

document.title = '登录密码修改'
//旧密码
const password = ref('')
//新密码
const password1 = ref('')
const password2 = ref('')
const checkPassword = ref('')
//提示
const open = () => {
  ElMessage({
    message: '修改成功,请重新登录!',
    type: 'success',
    center: true,
    duration: 5000,
  })
}
//取用户名
const username = useRoute().params.username;

async function updatePs() {
  if (!password.value) {
    alert('请输入旧密码');
    return;
  }
  if (!password1.value) {
    alert('请输入新密码');
    return;
  }
  if (!password2.value) {
    alert('请输入确认密码');
    return;
  }
  if (password.value != checkPassword.value) {
    alert('密码错误，请检查旧密码');
    return;
  }
  if (password1.value != password2.value) {
    alert('两次输入密码不同');
    password2.value = '';
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/account/updatePs', {
      password: password1.value,
      username: username,
    });
    if (response.data.code == 200) {
      open();
      await router.push({
        name: 'login',
      })

    }
  } catch (error) {
    console.log('请求失败');
  }
}

async function check() {
  try {
    const response = await axios.get('http://localhost:8091/account/check', {
      params: {
        username: username,
      }
    });
    checkPassword.value = response.data.data.password;
  } catch (error) {
    console.log('请求失败');
  }
}

onMounted(() => {
  check();
})


</script>

<template>
  <div class="container">
    <div style="margin-left: 20px;margin-top: 30px">请输入旧密码:
      <el-input
          v-model="password"
          style="width: 240px"
          type="password"
          placeholder="请输入旧密码"
          show-password
      />
    </div>
    <div style="margin-left: 20px;margin-top: 40px">请输入新密码:
      <el-input
          v-model="password1"
          style="width: 240px"
          type="password"
          placeholder="请输入新密码"
          show-password
      />
    </div>
    <br/>
    <div style="margin-left: 60px;margin-top: 30px">确认密码:
      <el-input
          v-model="password2"
          style="width: 240px"
          type="password"
          placeholder="请确认密码"
          show-password
      />
    </div>
    <el-button type="primary"
               plain style="margin-left: 150px;margin-top: 50px"
               @click="updatePs">提交修改
    </el-button>
  </div>
</template>

<style scoped>
.container {
  width: 700px;
  height: 100%;
  font-family: 楷体;
  font-size: 20px;
  background-color: whitesmoke;
  margin-left: 100px;
}

</style>