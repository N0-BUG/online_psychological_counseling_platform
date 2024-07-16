<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
//同意

document.title = '预约消息'


//同意请求
const agreeEva = async (index, row) => {
  try {
    const id = row.evaluateId;
    const response = await axios.post('http://localhost:8091/evaluate/agreeEva', {
      evaluateId: id
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '操作成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '操作失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '操作失败',
    });
  }
};
//拒绝请求
const refuseEva = async (index, row) => {
  try {
    const id = row.evaluateId
    const response = await axios.post('http://localhost:8091/evaluate/refuseEva', {
      evaluateId: id
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '操作成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '操作失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '操作失败',
    });
  }
};
//搜索
const search = ref('')
const tableData = ref([])

//预约记录
const evaluateData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/evaluate/all', {
      params: {
        search: search.value
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
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂无待处理的评价记录" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="evaluate" label="评价人" width="120"/>
    <el-table-column prop="evaluated" label="被评价的咨询师" width="190"/>
    <el-table-column prop="evaluateMessage" label="评价内容" width="240"/>
    <el-table-column prop="evaluateShow" label="是否匿名" width="190"/>
    <el-table-column prop="evaluateTime" label="评价时间" sortable width="280"/>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">
        <el-button-group>
          <el-button link style="font-size: 17px" size="large" type="primary"
                     @click="agreeEva(scope.$index, scope.row)"
          >
            通过
          </el-button>

          <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                     @click="refuseEva(scope.$index, scope.row)"
          >
            不通过
          </el-button>

        </el-button-group>

      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入评价者用户名查询"/>
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

/*容器*/
.dialogContainer {
  padding: 20px;
  height: 100%;
  text-align: center;
  font-family: 华文楷体;
  font-size: 30px;


}

</style>