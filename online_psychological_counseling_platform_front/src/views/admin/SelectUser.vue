<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {Plus} from "@element-plus/icons-vue";

document.title = '用户管理'
const adminName = useRoute().params.username

const del = async (index, row) => {
  username.value = row.username
}
const addDialog = ref(false)
//删除用户
const delUser = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/account/remove', {
      params: {
        username: username.value
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
      '确定要删除该用户吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        delUser();
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
const radio1 = ref('')
const age = ref('')
const username = ref('')
const phone = ref('')
//用户信息
const userData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/user/info', {
      params: {
        search: search.value
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
//添加用户
const addUser = async () => {
  if (!radio1.value) {
    ElMessage({
      type: 'warning',
      message: '请选择性别',
    })
    return;
  }
  if (!age.value) {
    ElMessage({
      type: 'warning',
      message: '请填写年龄',
    })
    return;
  }
  if (age.value < 0) {
    ElMessage({
      type: 'warning',
      message: '年龄不合规',
    })
    return;
  }
  if (!username.value) {
    ElMessage({
      type: 'warning',
      message: '用户名不能为空',
    })
    return;
  }
  if (!phone.value) {
    ElMessage({
      type: 'warning',
      message: '手机号不能为空',
    })
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/user/add', {
      username: username.value,
      userAge: age.value,
      userSex: radio1.value,
      phoneNumber: phone.value,

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
        message: '该用户名已存在',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '该用户已存在',
    });
  }
};
onMounted(() => {
  userData()
})
watch(search, () => {
  userData()
});
const loading = ref(true)
</script>
<template>
  <el-table size="large" :data="tableData" class="container" max-height="600px"
            empty-text="无用户信息" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="username" label="用户名" width="130"/>
    <el-table-column prop="userSex" label="性别" width="130"/>
    <el-table-column prop="userAge" label="年龄" sortable width="130"/>
    <el-table-column prop="phoneNumber" label="电话" width="150"/>
    <el-table-column prop="userSignature" label="个性签名" width="280"/>
    <el-table-column label="add" width="130">
      <template #header>
        <div class="add" @click="addDialog = true">
          <el-icon>
            <Plus/>
          </el-icon>
          添加用户
        </div>

      </template>
    </el-table-column>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">

        <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                   @click="del(scope.$index, scope.row);confirm()"
        >
          删除该用户
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入用户名查询"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="addDialog" title="添加用户" width="650">
    <div class="dialogContainer">
      <div class="top">
        性别:
        <span style="width: 200px;margin-left: 40px">
        <el-radio-group v-model="radio1" size="large">
          <el-radio-button label="男" value="男"/>
          <el-radio-button label="女" value="女"/>
        </el-radio-group>
      </span>

      </div>
      <div class="top">
        年龄:
        <span style="width: 200px;margin-left: 20px">
      <el-input v-model="age" size="large"
                style="width: 240px;margin-left: 20px" placeholder="填写年龄"/>
      </span>

      </div>
      <div class="username">
        用户名:<span style="margin-left: 20px">
      <el-input v-model="username" size="large"
                style="width: 240px;margin-left: 20px" placeholder="填写用户名"/></span>
      </div>
      <div class="phone">手机号:
        <span>
        <el-input v-model="phone" size="large"
                  style="width: 240px;margin-left: 30px" placeholder="填写手机号"/>
          </span>
      </div>
      <div style="margin-top: 50px;display: flex">
        <el-button @click="addDialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="addUser" type="primary" style="width: 190px;margin-left: 80px"
        >添加
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