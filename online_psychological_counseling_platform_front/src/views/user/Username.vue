<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "../../router";

document.title = '用户名修改'
//旧密码
const newUsername = ref('')

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

async function updateUs() {
  if (!newUsername.value) {
    alert('用户名不能为空');
    return;
  }

  try {
    const response = await axios.post('http://localhost:8091/account/updateUs', {
      newUsername: newUsername.value,
      username: username,
    });
    if (response.data.code == 200) {
      open();
      await router.push({
        name: 'login',
      })


    } else alert(response.data.msg)
  } catch (error) {
    console.log('请求失败');
  }
}


</script>

<template>
  <div class="container">
    <div style="margin-top: 40px">请输入你想修改的用户名:
      <div style="margin-left: 230px;margin-top: 10px">
        <el-input
            v-model="newUsername"
            style="width: 240px"
            type="text"
            placeholder="请输入新用户名"

        />
      </div>
    </div>
    <el-button type="primary"
               plain style="margin-left: 210px;margin-top: 100px"
               @click="updateUs">提交修改
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
  margin-left: 70px;
}

</style>