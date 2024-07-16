<script setup>
//搜索
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";

const username = useRoute().params.username
const tableData = ref([])

//日志请求
const logData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/log/all', {
      params: {
        logLogin: username,
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};

onMounted(() => {
  logData()
})

</script>

<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂无日志记录" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="logLogin" label="登录人" width="600"/>
    <el-table-column prop="logTime" label="登录时间" width="900"/>


  </el-table>
</template>

<style scoped>
.container {
  font-family: 楷体;
  background-color: whitesmoke;
  z-index: 1;
  font-size: 16px;
}
</style>