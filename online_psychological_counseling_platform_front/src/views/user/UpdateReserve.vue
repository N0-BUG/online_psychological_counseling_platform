<script setup>
//咨询师头像
import {useRoute} from "vue-router";
import {ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

document.title = '预约'
//咨询方式
const radio1 = ref('')
const consultantImageUrl = useRoute().params.consultantPfp
//返回
const back = () => {
  history.back()
}
//时段
const value = ref([])
//时间信息
const date = ref('')
const props = {
  expandTrigger: 'hover',
}
const handleChange = (value) => {
  // 拼接后赋值
  date.value = value[0] + ' ' + value[1];
}
//备注信息
const resMessage = ref('')
//预约人
const reservationUser = useRoute().params.username
//预约的咨询师
const reservationConsultant = useRoute().params.consultantName
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
//申请提示
const open = () => {
  ElMessage({
    message: '提交成功,请等待咨询师处理，' +
        '本页面将在4秒后自动跳转！',
    type: 'success',
    center: true
  })
}

//预约请求
async function res() {
  if (!radio1.value) {
    alert('请选择咨询方式');
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/reservation/res', {
      // 传参
      reservationUser: reservationUser,
      reservationTime: date.value,
      reservationConsultant: reservationConsultant,
      reservationMessage: resMessage.value,
      reservationType: radio1.value,
    });
    if (response.data.code == 200) {
      open();
      setTimeout(() => {
        history.back()
      }, 4000); // 4秒后跳转
    }


  } catch (error) {
    console.log('请求失败');
  }
}


</script>

<template>
  <div class="container">
    <p class="t3">
      <el-icon class="ic" @click="back">
        <ArrowLeftBold/>
      </el-icon>
      咨询预约
    </p>
    <div style="margin-left: -210px">
      <el-avatar shape="square" :size="150" :src="consultantImageUrl"
      ></el-avatar>
    </div>
    <div style="margin-left: 140px;margin-top: -80px">
      {{ useRoute().params.consultantName }}
    </div>
    <div style="margin-top: 100px">
      <sapn style="margin-left: -38px">咨询方式:</sapn>
      <span style="width: 200px;margin-top: -20px;margin-left: 40px">
        <el-radio-group v-model="radio1" size="large">
          <el-radio-button label="文字聊天" value="文字聊天"/>
          <el-radio-button label="语音聊天" value="语音聊天"/>
        </el-radio-group>
      </span>
    </div>
    <hr/>
    <div class="m-4" style="margin-top: 50px">
      咨询时段:
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
    <div style="margin-top: 50px;margin-left: 53px">
      备注信息:
      <el-input
          show-word-limit
          maxlength="30"
          type="text"
          v-model="resMessage" style="width:
                280px;height:70px;margin-left: 28px" placeholder="填写备注信息(如希望提前联系)"/>
    </div>
    <div style="margin-top: 50px;display: flex">
      <el-button type="primary" @click="back" style="width: 190px;margin-left: 70px"
      >返回
      </el-button>

      <el-button type="primary" @click="res" style="width: 190px;margin-left: 80px"
      >提交预约
      </el-button>
    </div>
  </div>
</template>

<style scoped>
.t3 {
  color: skyblue;
  font-size: 40px;
}

.el-button {
  height: 50px;
}

.ic:hover {
  cursor: pointer;
}

/*容器*/
.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  height: 100%;
  width: 600px;
  text-align: center;
  font-family: 华文楷体;
  font-size: 30px;
  border: #eeeeee solid 1px;
  background-image: url("/src/assets/background.jpg");
}

</style>