<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";

document.title = '咨询师管理'
const addDialog = ref(false)
const adminName = useRoute().params.username
//定义选项的数据
const value1 = ref(null)
const options1 = [
  {
    value: '恋爱情感',
    label: '恋爱情感',
  },
  {
    value: '职场发展',
    label: '职场发展',
  },
  {
    value: '亲子教育',
    label: '亲子教育',
  },
  {
    value: '人际关系',
    label: '人际关系',
  },
  {
    value: '个人成长',
    label: '个人成长',
  },
  {
    value: '情绪压力',
    label: '情绪压力',
  },
  {
    value: '婚姻家庭',
    label: '婚姻家庭',
  },
  {
    value: '性健康',
    label: '性健康',
  }
]
const consultantName = ref('')
const del = async (index, row) => {
  consultantName.value = row.consultantName
}
//删除咨询师
const delConsultant = async (index, row) => {
  try {
    const response = await axios.delete('http://localhost:8091/consultant/del', {
      params: {
        consultantName: consultantName.value
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
      '确定要删除该咨询师吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        delConsultant();
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

//用户信息
const consultantData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/consultant/all', {
      params: {
        search: search.value
      }
    })
    tableData.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
const radio1 = ref('')
const age = ref('')
const phone = ref('')
const name = ref('')
//添加咨询师
const addConsultant = async () => {
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
  if (!name.value) {
    ElMessage({
      type: 'warning',
      message: '姓名不能为空',
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
    const response = await axios.post('http://localhost:8091/consultant/add', {
      consultantName: name.value,
      consultantAge: age.value,
      consultantSex: radio1.value,
      consultantPhone: phone.value,
      consultantType: value1.value

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
        message: '该咨询师已存在',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '该咨询师已存在',
    });
  }
};
onMounted(() => {
  consultantData()
})
watch(search, () => {
  consultantData()
});

</script>
<template>
  <el-table size="large" :data="tableData" max-height="600px" class="container" empty-text="无咨询师信息" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="consultantName" label="姓名" width="130"/>
    <el-table-column prop="consultantSex" label="性别" width="130"/>
    <el-table-column prop="consultantAge" label="年龄" sortable width="110"/>
    <el-table-column prop="consultantPhone" label="电话" width="150"/>
    <el-table-column prop="consultantType" label="擅长类型" width="150"/>
    <el-table-column prop="consultantNo" label="累计帮助人数" sortable width="160"/>
    <el-table-column label="add" width="200">
      <template #header>
        <div class="add" @click="addDialog=true">
          <el-icon>
            <Plus/>
          </el-icon>
          添加咨询师
        </div>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">

        <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="danger"
                   @click="del(scope.$index, scope.row);confirm()"
        >
          删除该咨询师
        </el-button>
      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入咨询师姓名名查询"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="addDialog" title="添加用户" width="650">
    <div class="dialogContainer">
      <div style="margin-left: 20px">
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
        姓名:<span style="margin-left: 20px">
      <el-input v-model="name" size="large"
                style="width: 240px;margin-left: 30px" placeholder="填写用户名"/></span>
      </div>
      <div class="phone">手机号:
        <span>
        <el-input v-model="phone" size="large"
                  style="width: 240px;margin-left: 35px" placeholder="填写手机号"/>
          </span>
      </div>
      <div class="sig">
        擅长类型:
        <el-select
            v-model="value1"
            placeholder="选择类型"
            size="default"
            clearable
            style="width: 240px;margin-left: 30px"
        >
          <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </div>
      <div style="margin-top: 50px;display: flex">
        <el-button @click="addDialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="addConsultant" type="primary" style="width: 190px;margin-left: 80px"
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
  margin-left: -30px;
  margin-top: 50px;
}

.username {
  margin-left: 20px;
  margin-top: 50px;
}

.phone {
  margin-left: -5px;
  margin-top: 50px;
}


.add:hover {
  cursor: pointer;
}


.add:active {
  color: #ffd04b;
}
</style>