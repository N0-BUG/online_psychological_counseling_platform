<template>
  <h1 class="t3">
    登录
  </h1>
  <div style="opacity: 0.9">
    用户名/手机号:
    <el-input v-model="input" style="width:
                240px" placeholder="请输入用户名或手机号"/>
  </div>
  <br/>

  <div style="margin-left: 107px;margin-top: 40px;opacity: 0.9">密码:
    <el-input
        v-model="password"
        style="width: 240px"
        type="password"
        placeholder="请输入密码"
        show-password
    />
  </div>


  <div class="mb-2 flex items-center text-sm">
    <el-radio-group v-model="radio1" class="ml-4">
      <el-radio value="user" size="large">用户</el-radio>
      <el-radio value="manager" size="large">管理员</el-radio>
    </el-radio-group>
  </div>


  <el-button type="primary" @click="login" style="opacity: 0.9"
  >点此登录
  </el-button>

  <br/>

  <div class="link">
    <router-link to="/register">没有账号？去注册</router-link>
    >
  </div>
</template>

<script setup>
import axios from "axios";

document.title = '登录'
import router from "../../router";
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";

const input = ref('')
const password = ref('')
const radio1 = ref('')

async function login() {
  if (!input.value) {
    ElMessage({
      type: 'warning',
      message: '用户名不能为空',
    })
    return;
  }

  if (!password.value) {
    ElMessage({
      type: 'warning',
      message: '密码不能为空',
    })

    return;
  }

  if (!radio1.value) {
    ElMessage({
      type: 'warning',
      message: '请选择用户类型',
    })

    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/account/login', {
      input: input.value,
      password: password.value,
      accountIdentify: radio1.value
    });
    if (response.data.code !== 200) {
      alert(response.data.msg)
      input.value = '';
      password.value = '';
      radio1.value = '';

      throw new Error('状态码不是200');

    } else {
      alert("登录成功")
      const token = '已登录';
      sessionStorage.setItem('token', token)
      if (radio1.value === 'user') {
        await router.push({
          name: 'detail',
          params: {
            username: response.data.data.username,
            isConsultant: response.data.data.isConsultant,
            phoneNumber: response.data.data.phoneNumber,
          }
        })
      } else await router.push({
        name: 'adminHome',
        params: {
          username: response.data.data.username
        }
      })
    }

  } catch (error) {
    console.error('请求失败')
  }

}


</script>

<style scoped>
/*checkbox*/
.mb-2 {
  margin-top: 35px;
  margin-left: 60px;
  opacity: 0.9;
}

.t3 {
  font-size: 30px;
  margin-bottom: 80px;
  margin-top: -20px;
  margin-left: 100px;
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

