<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import router from "../../router";
import {ElMessage} from "element-plus";

document.title = '基本信息'
//性别
const radio1 = ref(null)
//是否修改性别
const sex = ref(true)
const sexYes = () => {
  sex.value = false
}
//是否修改年龄
const age = ref(true)
const ageYes = () => {
  age.value = false
}
//出生日期
const value1 = ref(null)
//个性签名
const input = ref(null)
//是否修改个性签名
const signature = ref(true)
//用户信息
const user = ref([])
//用户头像
const userImageUrl = (user) => {
  return `${user.userPfp}`;
}
const signatureYes = () => {
  signature.value = false
}
//获取用户信息
const userData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/user/detail', {
      params: {
        username: useRoute().params.username
      }
    })
    user.value = response.data.data
  } catch (error) {
    console.error(error)
  }
};
onMounted(() => {
  userData()

})
//获取用户名
const username = useRoute().params.username

async function update() {
  if (!radio1.value && sex.value == false) {
    alert('请选择性别');
    return;
  }

  if (!value1.value && age.value == false) {
    alert('请选择你的出生日期');
    return;
  }

  if (!input.value && signature.value == false) {
    alert('请编辑你的个性签名');
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/user/updateUser', {
      username: username,
      userSex: radio1.value,
      userAge: value1.value,
      userSignature: input.value,
    });
    if (response.data.code == 200) {
      open()
      location.reload();
    }
  } catch (error) {
    console.log('请求失败');
  }
}

//提示
const open = () => {
  ElMessage({
    message: '修改成功',
    type: 'success',
    center: true,
    duration: 3000,
  })
}
</script>

<template>
  <div class="container">
    头像:
    <el-avatar :size="70" :src="userImageUrl(user)"/>
    <div class="top">
      性别:<span v-if="sex" style="margin-left: 40px">{{ user.userSex }} </span>
      <span v-else style="width: 200px;margin-left: 40px">
        <el-radio-group v-model="radio1" size="large">
          <el-radio-button label="男" value="男"/>
          <el-radio-button label="女" value="女"/>
        </el-radio-group>
      </span>
      <el-button v-if="sex" type="primary" plain style="margin-left: 100px" @click="sexYes">修改</el-button>
    </div>
    <div class="top">
      年龄:<span v-if="age" style="margin-left: 40px">{{ user.userAge }} </span>
      <span v-else style="width: 200px;margin-left: 40px">
      <el-date-picker
          v-model="value1"
          type="date"
          placeholder="请选择你的出生日期"
          @change="date"
          value-format="x"
      />
      </span>
      <el-button v-if="age" type="primary" plain style="margin-left: 100px" @click="ageYes">修改</el-button>
    </div>
    <div class="username">
      用户名:<span style="margin-left: 40px">{{ user.username }} </span>
    </div>
    <div class="phone">手机号:
      <span style="margin-left: 30px">{{ user.phoneNumber }}
      </span>
    </div>
    <div class="sig">
      个性签名:<span v-if="signature" style="margin-left: 40px">{{ user.userSignature }} </span>
      <el-input v-else v-model="input" size="large"
                style="width: 240px;margin-left: 40px" placeholder="编辑你的个性签名"/>
      <el-button v-if="signature" type="primary" plain style="margin-left: 800px;margin-top: -50px"
                 @click="signatureYes">编辑
      </el-button>
    </div>
    <el-button type="primary"
               plain style="margin-left: 150px;margin-top: 50px"
               @click="update">提交修改
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

.top {
  margin-top: 50px;
}

.sig {
  margin-left: -40px;
  margin-top: 50px;
}

.username {
  margin-left: -20px;
  margin-top: 50px;
}

.phone {
  margin-left: -20px;
  margin-top: 50px;
}
</style>