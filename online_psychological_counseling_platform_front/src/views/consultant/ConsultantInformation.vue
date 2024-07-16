<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import router from "../../router";
import {ElMessage} from "element-plus";

document.title = '基本信息'
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
//性别
const radio1 = ref(null)
//是否修改性别
const sex = ref(true)
const sexYes = () => {
  sex.value = false
}
//是否修改年龄
const age = ref(true)
const ageYes = () => {
  age.value = false
}
//出生日期
const value2 = ref(null)
//是否修改擅长类型
const type = ref(true)
//咨询师信息
const consultant = ref([])
//用户头像
const consultantImageUrl = (consultant) => {
  return `${consultant.consultantPfp}`;
}
const typeYes = () => {
  type.value = false
}
//获取咨询师信息
const consultantData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/consultant/info', {
      params: {
        username: useRoute().params.username

      }
    })
    consultant.value = response.data.data
  } catch (error) {
    console.error(error)
  }
};
onMounted(() => {
  consultantData()
})
//获取用户名
const username = useRoute().params.username

async function update() {
  if (!radio1.value && sex.value == false) {
    alert('请选择性别');
    return;
  }

  if (!value2.value && age.value == false) {
    alert('请选择你的出生日期');
    return;
  }

  if (!value1.value && type.value == false) {
    alert('请选择你的擅长类型');
    return;
  }
  try {
    const response = await axios.post('http://localhost:8091/consultant/updateConsultant', {
      username: username,
      consultantSex: radio1.value,
      consultantAge: value2.value,
      consultantType: value1.value,
    });
    if (response.data.code == 200) {
      open()
      location.reload();
    }
  } catch (error) {
    console.log('请求失败');
  }
}

//提示
const open = () => {
  ElMessage({
    message: '修改成功',
    type: 'success',
    center: true,
    duration: 3000,
  })
}
</script>

<template>
  <div class="container">
    头像:
    <el-avatar :size="70" :src="consultantImageUrl(consultant)"/>
    <div class="top">
      性别:<span v-if="sex" style="margin-left: 40px">{{ consultant.consultantSex }} </span>
      <span v-else style="width: 200px;margin-left: 40px">
            <el-radio-group v-model="radio1" size="large">
              <el-radio-button label="男" value="男"/>
              <el-radio-button label="女" value="女"/>
            </el-radio-group>
          </span>
      <el-button v-if="sex" type="primary" plain style="margin-left: 100px" @click="sexYes">修改</el-button>
    </div>
    <div class="top">
      年龄:<span v-if="age" style="margin-left: 40px">{{ consultant.consultantAge }} </span>
      <span v-else style="width: 200px;margin-left: 40px">
          <el-date-picker
              v-model="value2"
              type="date"
              placeholder="请选择你的出生日期"
              @change="date"
              value-format="x"
          />
          </span>
      <el-button v-if="age" type="primary" plain style="margin-left: 100px" @click="ageYes">修改</el-button>
    </div>
    <div class="username">
      姓名:<span style="margin-left: 40px">{{ consultant.consultantName }} </span>
    </div>
    <div class="phone">手机号:
      <span style="margin-left: 30px">{{ consultant.consultantPhone }}
          </span>
    </div>
    <div class="sig">
      擅长类型:<span v-if="type" style="margin-left: 40px">{{ consultant.consultantType }} </span>
      <el-select
          v-else
          v-model="value1"
          placeholder="选择你擅长的类型"
          size="default"
          clearable
          style="width: 240px;margin-left: 5px"
      >
        <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
      <el-button v-if="type" type="primary" plain style="margin-left: 60px"
                 @click="typeYes">修改
      </el-button>
    </div>
    <el-button type="primary"
               plain style="margin-left: 150px;margin-top: 50px"
               @click="update">提交修改
    </el-button>
  </div>

</template>

<style scoped>
.container {
  width: 700px;
  height: 100%;
  font-family: 楷体;
  font-size: 20px;
  background-color: whitesmoke;
  margin-left: 100px;
}

.top {
  margin-top: 50px;
}

.sig {
  margin-left: -40px;
  margin-top: 50px;
}

.username {
  margin-top: 50px;
}

.phone {
  margin-left: -20px;
  margin-top: 50px;
}
</style>