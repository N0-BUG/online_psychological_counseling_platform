<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
//同意

document.title = '私信消息'


//同意请求
const agreePm = async (index, row) => {
  try {
    const id = row.pmId;
    const response = await axios.post('http://localhost:8091/pmessage/agreePm', {
      pmId: id
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
const refusePm = async (index, row) => {
  try {
    const id = row.pmId
    const response = await axios.post('http://localhost:8091/pmessage/refusePm', {
      pmId: id
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
const pmData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/pmessage/all', {
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
  pmData()
})
watch(search, () => {
  pmData()
});

</script>
<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂无待处理的私信记录" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="pmSender" label="发送者" width="150"/>
    <el-table-column prop="pmRecipient" label="接收的咨询师" width="200"/>
    <el-table-column prop="pmContent" label="私信内容" width="280"/>

    <el-table-column prop="pmTime" label="发送时间" sortable width="300"/>
    <el-table-column fixed="right" label="Operations" width="280">
      <template #default="scope">
        <el-button-group>
          <el-button link style="font-size: 17px" size="large" type="primary"
                     @click="agreePm(scope.$index, scope.row)"
          >
            通过
          </el-button>

          <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                     @click="refusePm(scope.$index, scope.row)"
          >
            不通过
          </el-button>

        </el-button-group>

      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入私信者用户名查询"/>
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