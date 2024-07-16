<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "../../router";
//取消预约
const cancel = (index, row) => {
  reservationConsultant.value = row.reservationConsultant;
}
const reservationUser = useRoute().params.username
//联系咨询师
const isConsultant = ref('')
const reservationConsultant = ref('')
const isCon = async () => {
  try {
    const response = await axios.get('http://localhost:8091/account/isConsultant', {
      params: {
        username: reservationUser
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
  router.push({
    name: 'userChat',
    params: {
      isConsultant: isConsultant.value
    }
  })
}
//重新预约
const again = (index, row) => {
  reservationConsultant.value = row.reservationConsultant;
}
//取消请求
const cancelRes = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/reservation/cancelRes', {
      params: {
        reservationUser: reservationUser,
        reservationConsultant: reservationConsultant.value
      }
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '取消成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '取消失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '取消失败',
    });
  }
};
const confirm = () => {
  ElMessageBox.confirm(
      '确定要取消该预约吗?',
      '警告',
      {
        confirmButtonText: '是的',
        cancelButtonText: '算了',
        type: 'warning',
      }
  )
      .then(() => {
        cancelRes();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};
const open = () => {
  ElMessage({
    message: '修改成功',
    type: 'success',
    center: true,
    duration: 3000,
  })
}
//备注信息
const resMessage = ref('')
const radio1 = ref('')
//时段
const value = ref([])
//时间信息
const date = ref('')
//搜索
const search = ref('')
const tableData = ref([])


const update = (index, row) => {

  resMessage.value = row.reservationMessage;
  reservationConsultant.value = row.reservationConsultant;
}

async function updateSend() {
  try {
    const response = await axios.post('http://localhost:8091/reservation/updateRes', {
      reservationUser: reservationUser,
      reservationTime: date.value,
      reservationConsultant: reservationConsultant.value,
      reservationMessage: resMessage.value,

    });
    if (response.data.code == 200) {
      open();
      location.reload();
    }
  } catch (error) {
    console.log('请求失败');
  }
}

async function againRes() {
  if (!evaMessage.value) {
    ElMessage({
      type: 'warning',
      message: '请选择预约时间',
    })
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/reservation/againRes', {
      reservationUser: reservationUser,
      reservationTime: date.value,
      reservationConsultant: reservationConsultant.value,
    });
    if (response.data.code == 200) {
      open();
      location.reload();
    }
  } catch (error) {
    console.log('请求失败');
  }
}


//预约请求
const reservationData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/reservation/resRecord', {
      params: {
        reservationUser: reservationUser,
        search: search.value
      }
    })
    tableData.value = response.data.data
    sessionStorage.setItem("username", reservationUser);
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

//dialog
const updateDialog = ref(false)
const reDialog = ref(false)
const evaDialog = ref(false)
const evaMessage = ref('')
const evaluate = ref('')
const evaluated = ref('');
const props = {
  expandTrigger: 'hover',
}
const handleChange = (value) => {
  // 拼接后赋值
  date.value = value[0] + ' ' + value[1];
}


//动态选项
const options = [];
const daysOfWeek = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
const timeSlots = ['9:00-11:00', '14:00-17:00'];

const today = new Date();
const currentDay = today.getDay();
const currentHour = today.getHours();

for (let i = 0; i < 14; i++) { // 从今天开始，连续生成两个星期的选项
  const date = new Date();
  date.setDate(today.getDate() + i);

  if (date.getDate() < today.getDate()) {
    date.setMonth(today.getMonth() + 1);
  }

  const dayOfWeek = daysOfWeek[(currentDay + i) % 7];
  const formattedDate = (date.getMonth() + 1) + '月' + date.getDate() + '日';
  const label = formattedDate + ' ' + dayOfWeek;

  if (i === 0 && currentHour >= 17) {
    continue;
  }

  const option = {
    value: formattedDate,
    label: label,
    children: timeSlots.map(timeSlot => ({
      value: timeSlot,
      label: timeSlot,
    }))
  };

  options.push(option);
}
const eva = (index, row) => {
  evaluate.value = row.reservationUser;
  evaluated.value = row.reservationConsultant;
}
const sendEva = async () => {
  if (!evaMessage.value) {

    ElMessage({
      type: 'warning',
      message: '请输入评价信息',
    })

    return;
  }
  if (!radio1.value) {
    ElMessage({
      type: 'warning',
      message: '请选择是否匿名',
    })
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/evaluate/eva', {
      evaluated: evaluated.value,
      evaluate: evaluate.value,
      evaluateShow: radio1.value,
      evaluateMessage: evaMessage.value
    });
    if (response.data.code === 200) {
      ElMessage({
        type: 'success',
        message: '评价成功',
      });
      location.reload();
    } else {
      ElMessage({
        type: 'error',
        message: '评价失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '操作失败',
    });
  }
};

</script>
<template>
  <el-table size="large" :data="tableData" class="container" max-height="600px" empty-text="你暂无预约记录" stripe
            style="width: 100%;height: auto"
  >
    <el-table-column fixed prop="reservationConsultant" label="所约咨询师" width="150"/>
    <el-table-column prop="reservationTime" label="预约的时间" sortable width="250"/>
    <el-table-column prop="reservationMessage" label="相关备注" width="260"/>
    <el-table-column prop="reservationNow" label="提交时间" sortable width="200"/>
    <el-table-column prop="reservationStatus" label="状态" width="100"/>
    <el-table-column fixed="right" label="Operations" width="250">
      <template #default="scope">
        <el-button-group>
          <el-button link style="font-size: 17px" size="large" type="danger"
                     v-if="scope.row.reservationStatus==='处理中'"
                     @click="cancel(scope.$index, scope.row);confirm()"
          >
            取消预约
          </el-button>

          <el-button link style="margin-left: 20px;font-size: 17px" size="large" type="primary"
                     v-if="scope.row.reservationStatus==='处理中'"
                     @click="updateDialog = true;update(scope.$index, scope.row)"
          >
            修改预约
          </el-button>


          <el-button link style="margin-left: 60px;font-size: 17px" size="large" type="success"
                     v-if="scope.row.reservationStatus==='预约成功'"
                     @click="chat(scope.$index, scope.row)"
          >
            联系咨询师
          </el-button>
          <el-button link style="margin-left: 60px;font-size: 20px" size="large" type="info"
                     v-if="scope.row.reservationStatus==='预约失败'"
                     @click="reDialog=true;again(scope.$index, scope.row)"
          >
            重新预约
          </el-button>
          <el-button link style="margin-left: 60px;font-size: 20px" size="large" type="primary"
                     v-if="scope.row.reservationStatus==='咨询结束'"
                     @click="evaDialog=true;eva(scope.$index, scope.row)"
          >
            评价
          </el-button>
        </el-button-group>

      </template>
      <template #header>
        <el-input v-model="search" size="large" placeholder="输入咨询师姓名查询"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="updateDialog" title="修改预约(当前是你的原选择)" width="650">
    <div class="dialogContainer">

      <div class="m-4" style="margin-top: 50px">
        修改咨询时段:
        <el-cascader
            size="large"
            style="margin-left: 30px"
            v-model="value"
            :options="options"
            :props="props"
            @change="handleChange"
        />
      </div>
      <hr/>
      <div style="margin-top: 50px;margin-left: 50px">
        修改备注信息:
        <el-input
            show-word-limit
            maxlength="50"
            type="textarea"
            v-model="resMessage" style="width:
                280px;height:70px;margin-left: 28px;margin-bottom: -20px" placeholder="填写备注信息(如希望提前联系)"/>
      </div>
      <div style="margin-top: 50px;display: flex">
        <el-button @click="updateDialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="updateSend" type="primary" style="width: 190px;margin-left: 80px"
        >提交修改
        </el-button>
      </div>
    </div>
  </el-dialog>
  <el-dialog v-model="reDialog" title="重新预约(请重新选择预约时间)" width="650">
    <div class="dialogContainer">
      <div class="m-4" style="margin-top: 50px">
        修改咨询时段:
        <el-cascader
            size="large"
            style="margin-left: 30px"
            v-model="value"
            :options="options"
            :props="props"
            @change="handleChange"
        />
      </div>
      <div style="margin-top: 50px;display: flex">
        <el-button @click="reDialog = false" type="primary" style="width: 190px;margin-left: 60px"
        >取消
        </el-button>
        <el-button @click="againRes" type="primary" style="width: 190px;margin-left: 80px"
        >提交
        </el-button>
      </div>
    </div>
  </el-dialog>
  <el-dialog v-model="evaDialog" title="进行评价" width="500"
             style="font-family: 楷体">
    <el-form>
      <el-form-item label="评价内容:" :label-width="formLabelWidth">
        <el-input v-model="evaMessage" autocomplete="off"/>
        <div class="mb-2 flex items-center text-sm">
          是否匿名:
          <el-radio-group v-model="radio1" class="ml-4">
            <el-radio value="yes" size="large">是</el-radio>
            <el-radio value="no" size="large">否</el-radio>
          </el-radio-group>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="evaDialog = false">下次再说</el-button>
        <el-button type="primary" @click="sendEva">
          评价
        </el-button>
      </div>
    </template>
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
  text-align: center;
  font-family: 华文楷体;
  font-size: 30px;


}

</style>