<script setup>
//搜索
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
//删除
const del = (index, row) => {
  id.value = row.evaluateId;
}
const id = ref();
const search = ref('');
const username = useRoute().params.username
const tableData = ref([])
const confirm = () => {
  ElMessageBox.confirm(
      '确定要删除该评论吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '算了',
        type: 'warning',
      }
  )
      .then(() => {
        delEval();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};
//评价信息请求
const evaluateData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/evaluate/user', {
      params: {
        evaluate: username,
        search: search.value,
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
//删除评价
const delEval = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/evaluate/del', {
      params: {
        id: id.value,
      }
    })
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '删除成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '删除失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '删除失败',
    });
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
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="你暂未评价过" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="evaluated" label="所评价的咨询师" width="150"/>
    <el-table-column prop="evaluateMessage" label="评价内容" width="350"/>
    <el-table-column prop="evaluateTime" label="评价时间" sortable width="280"/>
    <el-table-column prop="evaluateExa" label="状态" width="160"/>
    <el-table-column fixed="right" label="Operations" width="300">
      <template #default="scope">
        <el-button link style="font-size: 17px" size="large" type="danger"
                   @click="del(scope.$index, scope.row);confirm()"
        >
          删除评价
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入评价的咨询师姓名查询"/>
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