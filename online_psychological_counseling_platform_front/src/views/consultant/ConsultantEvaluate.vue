<script setup>
//搜索
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";

const search = ref('');
const username = useRoute().params.username
const tableData = ref([])
//评价信息请求
const evaluateData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/evaluate/username', {
      params: {
        username: username,
        search: search.value,
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};

onMounted(() => {
  evaluateData()
})
watch(search, () => {
  evaluateData()
});
</script>

<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="未有人对你做出评价" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed label="评价者" width="200">
      <template #default="scope">
        {{ scope.row.evaluateShow === 'yes' ? scope.row.evaluate : '匿名' }}
      </template>
    </el-table-column>
    <el-table-column prop="evaluateMessage" label="评价内容" width="400"/>
    <el-table-column prop="evaluateTime" label="评价时间" sortable width="320"/>
    <el-table-column fixed="right" label="Operations" width="310">
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入评价者用户名"/>
      </template>
    </el-table-column>
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