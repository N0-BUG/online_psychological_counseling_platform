<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "../../router";
//同意

document.title = '预约消息'
const username = useRoute().params.username
const reservationUser = ref('')
const reservationConsultant = ref('')
const consultantName = ref('')
const isConsultant = ref('')
const isCon = async () => {
  try {
    const response = await axios.get('http://localhost:8091/account/isConsultant', {
      params: {
        username: username,
      }
    })
    if (response.data.code === 200) {
      isConsultant.value = response.data.data;
    }
  } catch (error) {
    console.error(error)
  }
};
const chat = (index, row) => {
  const user = {
    username: row.reservationConsultant
  };
  reservationUser.value = row.reservationUser
  reservationConsultant.value = row.reservationConsultant
  router.push({
    name: 'chat',
    params: {
      isConsultant: isConsultant.value,
      reservationUser: reservationUser.value,
      reservationConsultant: reservationConsultant.value
    }
  })

// 将user对象转换为字符串
  const userStr = JSON.stringify(user);

// 存入sessionStorage
  sessionStorage.setItem("user", userStr);
}


//同意请求
const agreeRes = async (index, row) => {
  try {
    const reservationUser = row.reservationUser
    const response = await axios.post('http://localhost:8091/reservation/agreeRes', {
      reservationUser: reservationUser,
      username: username
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
const refuseRes = async (index, row) => {
  try {
    const reservationUser = row.reservationUser
    const response = await axios.post('http://localhost:8091/reservation/refuseRes', {
      reservationUser: reservationUser,
      username: username
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
const reservationData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/reservation/cRecord', {
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
onMounted(() => {
  reservationData()
  isCon()
})
watch(search, () => {
  reservationData()
});

</script>
<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂没有对你的预约" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="reservationUser" label="预约人" width="150"/>
    <el-table-column prop="reservationTime" label="预约的时间" sortable width="200"/>
    <el-table-column prop="reservationMessage" label="相关备注" width="280"/>
    <el-table-column prop="reservationNow" label="提交时间" sortable width="190"/>
    <el-table-column prop="reservationStatus" label="状态" width="150"/>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">
        <el-button-group>
          <el-button link style="font-size: 20px" size="large" type="primary"
                     v-if="scope.row.reservationStatus==='处理中'"
                     @click="agreeRes(scope.$index, scope.row)"
          >
            同意
          </el-button>

          <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                     v-if="scope.row.reservationStatus==='处理中'"
                     @click="refuseRes(scope.$index, scope.row)"
          >
            拒绝
          </el-button>

          <el-button link style="margin-left: 60px;font-size: 17px" size="large" type="success"
                     v-if="scope.row.reservationStatus==='预约成功'"
                     @click="chat(scope.$index, scope.row)"
          >
            沟通
          </el-button>
        </el-button-group>

      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入咨询者姓名查询"/>
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