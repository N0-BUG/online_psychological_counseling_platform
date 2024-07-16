<template>
  <div class="container">
    <p class="t3">
      <el-icon class="ic" @click="back">
        <ArrowLeftBold/>
      </el-icon>
      入驻申请
    </p>
    <span>

          <el-upload
              class="avatar-uploader"
              action="http://localhost:8091/applis/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <el-avatar v-if="imageUrl" shape="square" :size="150" :src="imageUrl">

            </el-avatar>
        <el-icon v-else class="avatar-uploader-icon"><Plus/> 上传头像</el-icon>
      </el-upload>
        </span>
    <div style="margin-top: 60px">
      真实姓名:
      <el-input v-model="appliName" style="width:
                240px" placeholder="请填写"/>
    </div>


    <div style="margin-top: 50px">
      手机号码:
      <el-input v-model="appliPhone" style="width:
                240px;" placeholder="请填写"/>
    </div>
    <div style="margin-top: 50px">
      擅长类型:
      <el-select
          v-model="value1"
          placeholder="选择你擅长的类型"
          size="default"
          clearable
          style="width: 240px;margin-left: 5px"
      >
        <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </div>
    <div style="margin-top: 50px;display: flex">
      <el-button type="primary" @click="back" style="width: 190px;margin-left: 70px"
      >返回
      </el-button>
      <el-button type="primary" @click="apply" style="width: 190px;margin-left: 80px"
      >提交申请
      </el-button>
    </div>
  </div>
</template>
<script setup>

import {ref} from "vue";
import axios from "axios";
import router from "../../router";
import {ElMessage} from "element-plus";

document.title = '咨询师申请'
const imageUrl = ref('')
const appliPfp = ref('')
const handleAvatarSuccess = function (response, uploadFile) {
  imageUrl.value = URL.createObjectURL(uploadFile.raw);
  appliPfp.value = response.data
};
//定义选项的数据
const value1 = ref('')
const appliName = ref('')
const appliPhone = ref('')
const options1 = [
  {
    value: '恋爱情感',
    label: '恋爱情感',
  },
  {
    value: '职场发展',
    label: '职场发展',
  },
  {
    value: '亲子教育',
    label: '亲子教育',
  },
  {
    value: '人际关系',
    label: '人际关系',
  },
  {
    value: '个人成长',
    label: '个人成长',
  },
  {
    value: '情绪压力',
    label: '情绪压力',
  },
  {
    value: '婚姻家庭',
    label: '婚姻家庭',
  },
  {
    value: '性健康',
    label: '性健康',
  }
]
//申请提示
const open = () => {
  ElMessage({
    message: '提交成功,请等待管理员审核，' +
        '本页面将在4秒后自动跳转！',
    type: 'success',
    center: true
  })
}
const back = () => {
  router.back()

};


//申请请求
function isPhoneNumber(value) {
  const reg = /^1[3456789]\d{9}$/;
  return reg.test(value);
}


async function apply() {
  if (!appliName.value) {
    alert('姓名不能为空');
    return;
  }
  if (!appliPhone.value) {
    alert('手机号不能为空');
    return;
  }
  if (!isPhoneNumber(appliPhone.value)) {
    alert('手机号格式不正确');
    appliPhone.value = '';
    return;
  }

  try {
    const response = await axios.post('http://localhost:8091/applis/apply', {
      appliName: appliName.value,
      appliPhone: appliPhone.value,
      appliType: value1.value,
      appliPfp: appliPfp.value
    });
    if (response.data.code == 200) {
      open();
      setTimeout(() => {
        history.back()
      }, 4000); // 4秒后跳转
    }


  } catch (error) {
    console.log('请求失败');
  }
}
</script>

<style scoped>
.t3 {
  color: skyblue;
  font-size: 40px;
}

.ic:hover {
  cursor: pointer;
}

/*容器*/
.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  height: 100%;
  width: 600px;
  text-align: center;
  font-family: 华文楷体;
  font-size: 30px;
  border: #eeeeee solid 1px;
  background-image: url("/src/assets/background.jpg");
}

.avatar-uploader .avatar {
  width: 150px;
  height: 150px;
  display: block;

}
</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-button {
  height: 50px;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>