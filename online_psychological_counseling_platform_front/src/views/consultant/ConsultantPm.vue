<script setup>
import {onMounted, ref} from 'vue'
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

document.title = '私信'
const search = ref('')
const username = useRoute().params.username;
const tableData = ref([]);
const dialog = ref(false)
const pmId = ref()
const replyMsg = ref()
const reply = (index, row) => {
  pmId.value = row.pmId
}
//私信信息请求
const pMessageData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/pmessage/my', {
      params: {
        username: username,
        search: search.value
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
//回复请求
const replyPm = async (index, row) => {
  try {
    const response = await axios.post('http://localhost:8091/pmessage/reply', {
      pmId: pmId.value,
      pmBack: replyMsg.value
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '回复成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '回复失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '回复失败',
    });
  }
};
onMounted(() => {
  pMessageData()
})
</script>

<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂没有收到私信"
            style="width: 100%;height: auto;z-index: 1"
  >
    <el-table-column fixed prop="pmSender" label="发信人" width="150"/>
    <el-table-column prop="pmContent" label="发信内容" sortable width="280"/>
    <el-table-column prop="pmTime" label="发信时间" width="220"/>
    <el-table-column prop="pmBack" label="我的回复" width="300"/>
    <el-table-column fixed="right" label="Operations" width="280">
      <template #default="scope">
        <el-button link style="font-size: 17px" size="large" type="primary"
                   v-if="scope.row.pmBack==='未回复'"
                   @click="dialog=true;reply(scope.$index, scope.row)"
        >
          回复
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入私信者用户名"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialog" title="回复私信" width="650">
    <div class="dialogContainer">
      <el-input v-model="replyMsg" size="large" placeholder="输入内容"/>
      <div style="margin-top: 50px;display: flex">
        <el-button @click="dialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="replyPm" type="primary" style="width: 190px;margin-left: 80px"
        >回复
        </el-button>
      </div>
    </div>
  </el-dialog>
</template>
<style scoped>
.container {
  display: flex;
  width: 700px;
  height: 100%;
  font-family: 楷体;
  font-size: 16px;
  background-color: whitesmoke;

}
</style>