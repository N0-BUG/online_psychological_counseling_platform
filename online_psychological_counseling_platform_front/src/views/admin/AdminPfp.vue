<script setup>

import {ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";

document.title = '头像修改'
//提示
const open = () => {
  ElMessage({
    message: '修改成功',
    type: 'success',
    center: true,
    duration: 3000,
  })
}
const adminPfp = ref('')
const imageUrl = ref('')

const handleAvatarSuccess = function (response, uploadFile) {
  imageUrl.value = URL.createObjectURL(uploadFile.raw);
  adminPfp.value = response.data
};
const adminName = useRoute().params.username;

async function updatePfp() {
  try {
    const response = await axios.post('http://localhost:8091/admin/updatePfp', {
      adminPfp: adminPfp.value,
      adminName: adminName,
    });
    if (response.data.code == 200) {
      open();
      location.reload();
    }
  } catch (error) {
    console.log('请求失败');
  }
}


</script>

<template>
  <div class="container">
    <el-upload
        class="avatar-uploader"
        action="http://localhost:8091/admin/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
    >
      <el-avatar v-if="imageUrl" shape="square" :size="150" :src="imageUrl">
      </el-avatar>
      <el-icon v-else class="avatar-uploader-icon">
        <Plus/>
        上传头像
      </el-icon>
    </el-upload>
    <el-button type="primary"
               plain style="margin-left: 150px;margin-top: 50px"
               @click="updatePfp">提交修改
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

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>