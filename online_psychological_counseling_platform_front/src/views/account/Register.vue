<template>
  <h1 class="t3">
    注册
  </h1>
  <div style="opacity: 0.9">
    请输入手机号:
    <el-input v-model="phoneNumber" style="width:
                240px" placeholder="Please input"/>
  </div>
  <br/>
  <div style="opacity: 0.9">
    请输入用户名:
    <el-input v-model="username" style="width:
                240px" placeholder="Please input"/>
  </div>
  <div style="margin-left: 20px;margin-top: 30px;opacity: 0.9">请输入密码:
    <el-input
        v-model="password1"
        style="width: 240px"
        type="password"
        placeholder="请输入密码"
        show-password
    />
  </div>
  <br/>
  <div style="margin-left: 42px;opacity: 0.9">确认密码:
    <el-input
        v-model="password2"
        style="width: 240px"
        type="password"
        placeholder="请确认密码"
        show-password
    />
  </div>
  <!--  <div class="mb-2 flex items-center text-sm">-->
  <!--    <el-radio-group v-model="radio1" class="ml-4">-->
  <!--      <el-radio value="user" size="large">用户</el-radio>-->
  <!--      <el-radio value="manager" size="large">管理员</el-radio>-->
  <!--    </el-radio-group>-->
  <!--  </div>-->
  <el-button type="primary" @click="register" style="opacity: 0.9">点此注册</el-button>
  <br/>
  <div class="link">
    <router-link to="/login">已有账号？去登录</router-link>
  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";

document.title = '注册'
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "../../router";

const phoneNumber = ref('')
const username = ref('')
const radio1 = ref('')
const password1 = ref('')
const password2 = ref('')

function isPhoneNumber(value) {
  const reg = /^1[3456789]\d{9}$/;
  return reg.test(value);
}

async function register() {
  if (!phoneNumber.value) {
    ElMessage({
      type: 'warning',
      message: '手机号不能为空',
    })
    return;
  }
  if (!isPhoneNumber(phoneNumber.value)) {
    ElMessage({
      type: 'warning',
      message: '手机号格式不正确',
    })
    phoneNumber.value = '';
    return;
  }
  if (!username.value) {
    ElMessage({
      type: 'warning',
      message: '用户名不能为空',
    })
    return;
  }
  if (!password1.value) {
    ElMessage({
      type: 'warning',
      message: '密码不能为空',
    })
    return;
  }
  if (!password2.value) {
    ElMessage({
      type: 'warning',
      message: '请输入确认密码',
    })
    return;
  }
  if (password1.value != password2.value) {
    ElMessage({
      type: 'warning',
      message: '两次输入密码不同',
    })
    password2.value = '';
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/account/register', {
      phoneNumber: phoneNumber.value,
      username: username.value,
      password: password1.value,
    });
    if (response.data.code !== 200) {
      alert(response.data.msg)

      throw new Error('状态码不是200');

    } else {
      alert(response.data.data)
      await router.push('/');
    }
  } catch (error) {
    console.log('请求失败');
  }
}
</script>

<style scoped>
/*checkbox*/
.mb-2 {
  margin-top: 25px;
  margin-left: 60px;
  opacity: 0.9;
}

.t3 {
  font-size: 30px;
  margin-bottom: 80px;
  margin-top: -20px;
  margin-left: 80px;
  letter-spacing: 90px;
  opacity: 0.9;
}

/*链接*/
.link {
  padding-top: 20px;
  margin-left: 110px;
  font-size: 20px;
  opacity: 0.9;
}

</style>

