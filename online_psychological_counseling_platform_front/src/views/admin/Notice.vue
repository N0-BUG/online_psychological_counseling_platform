<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {Plus} from "@element-plus/icons-vue";

document.title = '公告管理'
const adminName = useRoute().params.username

const del = async (index, row) => {
  noticeId.value = row.noticeId
}
const addDialog = ref(false)
//删除公告
const delNotice = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/notice/del', {
      params: {
        noticeId: noticeId.value
      }
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
const confirm = () => {
  ElMessageBox.confirm(
      '确定要删除该公告吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        delNotice();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};
//搜索
const search = ref('')
const tableData = ref([])
const noticeTitle = ref('')
const noticeInfo = ref('')
const noticeId = ref('')
//公告信息
const noticeData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/notice/info', {
      params: {
        search: search.value
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
//发布公告
const addNotice = async () => {
  if (!noticeTitle.value) {
    ElMessage({
      type: 'warning',
      message: '请输入标题',
    })
    return;
  }
  if (!noticeInfo.value) {
    ElMessage({
      type: 'warning',
      message: '请输入内容',
    })
    return;
  }

  try {
    const response = await axios.post('http://localhost:8091/notice/add', {
      noticeTitle: noticeTitle.value,
      noticeInfo: noticeInfo.value,
      noticePublisher: adminName

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
onMounted(() => {
  noticeData()
})
watch(search, () => {
  noticeData()
});
const loading = ref(true)
</script>
<template>
  <el-table size="large" :data="tableData" class="container" max-height="600px"
            empty-text="无公告信息" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="noticeId" label="序号" width="80"/>
    <el-table-column fixed prop="noticePublisher" label="发布人" width="130"/>
    <el-table-column prop="noticeTitle" label="标题" width="130"/>
    <el-table-column prop="noticeInfo" label="公告内容" width="280"/>
    <el-table-column prop="noticeTime" label="发布时间" sortable width="200"/>
    <el-table-column label="add" width="130">
      <template #header>
        <div class="add" @click="addDialog = true">
          <el-icon>
            <Plus/>
          </el-icon>
          发布公告
        </div>

      </template>
    </el-table-column>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">

        <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                   @click="del(scope.$index, scope.row);confirm()"
        >
          删除该公告
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入发布时间查询"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="addDialog" title="发布公告" width="650">
    <div class="dialogContainer">

      <div class="top">
        标题:
        <span style="width: 200px;margin-left: 20px">
      <el-input v-model="noticeTitle" size="large"
                style="width: 240px;margin-left: 20px" placeholder="填写标题"/>
      </span>
      </div>
      <div class="username">
        内容:<span style="margin-left: 45px">
      <el-input v-model="noticeInfo" size="large"
                style="width: 240px;margin-left: 20px" placeholder="填写公告内容"/></span>
      </div>

      <div style="margin-top: 50px;display: flex">
        <el-button @click="addDialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="addNotice" type="primary" style="width: 190px;margin-left: 80px"
        >发布
        </el-button>
      </div>
    </div>
  </el-dialog>
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
  font-family: 华文楷体;
  font-size: 30px;

}

.top {
  margin-top: 50px;
  margin-left: 20px;
}

.sig {
  margin-left: -25px;
  margin-top: 50px;
}

.username {

  margin-top: 50px;
}

.phone {

  margin-top: 50px;
}

.username {
  margin-top: 50px;
}


.add:hover {
  cursor: pointer;
}

.add:active {
  color: #ffd04b;
}

body {
  margin: 0;
}

.example-showcase .el-loading-mask {
  z-index: 9;
}
</style>