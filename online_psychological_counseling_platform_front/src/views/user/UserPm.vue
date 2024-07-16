<script setup>
import {onMounted, ref, watch} from 'vue'
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";

const username = useRoute().params.username;
const tableData = ref([]);
//评价信息请求
const pMessageData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/pmessage/detail', {
      params: {
        pmSender: username,
        search: search.value
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
//删除
const del = (index, row) => {
  id.value = row.pmId;
}
const id = ref();
const search = ref('');
const confirm = () => {
  ElMessageBox.confirm(
      '确定要删除该私信吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '算了',
        type: 'warning',
      }
  )
      .then(() => {
        delPm();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};

//删除私信
const delPm = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/pmessage/del', {
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
  pMessageData()
})
watch(search, () => {
  pMessageData()
});
</script>


<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="你暂未评价过" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="pmRecipient" label="所私信的咨询师" width="150"/>
    <el-table-column prop="pmContent" label="私信内容" width="350"/>
    <el-table-column prop="pmTime" label="私信时间" sortable width="260"/>
    <el-table-column prop="pmExa" label="状态" width="110"/>
    <el-table-column prop="pmBack" label="回复消息" width="160"/>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">
        <el-button link style="font-size: 17px" size="large" type="danger"
                   @click="del(scope.$index, scope.row);confirm()"
        >
          删除私信
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入私信的咨询师姓名查询"/>
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