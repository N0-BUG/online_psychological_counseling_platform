<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
//同意

document.title = '资质申请消息'


//同意请求
const agreeAppli = async (index, row) => {
  try {
    const appliPhone = row.appliPhone;
    const response = await axios.post('http://localhost:8091/applis/agreeAppli', {
      appliPhone: appliPhone
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
const refuseAppli = async (index, row) => {
  try {
    const appliPhone = row.appliPhone;
    const response = await axios.post('http://localhost:8091/applis/refuseAppli', {
      appliPhone: appliPhone
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
const appliData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/applis/all', {
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
  appliData()
})
watch(search, () => {
  appliData()
});

</script>
<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="暂无待处理资质申请" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="appliName" label="申请人" width="200"/>
    <el-table-column prop="appliPhone" label="申请人电话" width="250"/>
    <el-table-column prop="appliType" label="擅长类型" width="230"/>
    <el-table-column prop="appliTime" label="申请提交时间" width="250"/>
    <el-table-column fixed="right" label="Operations" width="270">
      <template #default="scope">
        <el-button-group>
          <el-button link style="font-size: 17px" size="large" type="primary"
                     @click="agreeAppli(scope.$index, scope.row)"
          >
            通过
          </el-button>

          <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                     @click="refuseAppli(scope.$index, scope.row)"
          >
            不通过
          </el-button>

        </el-button-group>

      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入申请者姓名查询"/>
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