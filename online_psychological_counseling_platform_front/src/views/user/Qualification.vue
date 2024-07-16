<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "../../router";
//结果
const result = ref('')

const username = useRoute().params.username;
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8091/applis/result', {
      params: {
        username: username
      }
    });
    result.value = response.data.data;
  } catch (error) {
    console.error('Error fetching result:', error);
  }
});

function apply() {
  router.push({
    name: 'application'
  })
}
</script>

<template>
  <div class="container">
    <el-result v-if="result==='处理中'" style="font-size: 40px" icon="info" title="认证结果">
      <template #sub-title>
        <h1 style="font-size: 20px">处理中...请耐心等待...</h1>
      </template>
    </el-result>
    <el-result v-if="result==='未提交'" style="font-size: 40px" icon="info">
      <template #sub-title>
        <h1 style="font-size: 20px">暂未提交认证</h1>
      </template>
      <template #extra>
        <el-button type="primary" @click="apply">进行认证</el-button>
      </template>
    </el-result>
    <el-result
        v-if="result==='申请失败'"
        icon="error"
        title="认证结果"
    >
      <template #sub-title>
        <h1 style="font-size: 20px">认证失败</h1>
      </template>
      <template #extra>
        <el-button type="primary" @click="apply">重申</el-button>
      </template>
    </el-result>
  </div>
</template>

<style scoped>
/*容器*/
.container {
  position: absolute;
  top: 70%;
  left: 40%;
  transform: translate(-50%, -50%);
  padding: 20px;
  height: 100%;
  width: 600px;
  text-align: center;
  font-family: 华文楷体;

}

</style>