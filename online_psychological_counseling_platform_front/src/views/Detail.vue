<template>
  <div class="common-layout">
    <el-container>
      <el-header style="height: 60px;width: 100%;background-color:#cce5F3;z-index:2;position: fixed">
        <el-page-header :icon="ArrowLeftBold" style="margin-top: 15px" @back="back">
          <template #content>
            <div class="flex items-center">
              <el-dropdown>
                <el-avatar :size="32" :src="circleUrl" class="mr3"></el-avatar>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="personal">个人主页</el-dropdown-item>
                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <span class="text-large font-600 mr-3"> 贵大心理 </span>
              <span class="text-sm mr-2" style="color: var(--el-text-color-regular)">
          首页
        </span>
              <el-tag>访问用户:{{ useRoute().params.username }}</el-tag>
            </div>
          </template>
        </el-page-header>
      </el-header>
      <el-main style="height: 600px;width: 100%;margin-top: 60px">
        <div style="background-color:whitesmoke;padding: 10px;border-radius: 10px">
          <div style="margin-left: 26px;margin-top:-30px;font-size: 20px"><h1>
            心理咨询师:</h1>

          </div>

          <span style="margin-left: 18px">类型:</span>
          <el-select
              v-model="value1"
              placeholder="选择你想咨询的类型"
              size="large"
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
          <el-button v-if="isConsultant=='no'" @click="apply" color="#626aef" type="primary"
                     style="margin-left: 300px;width: 300px;height: 60px;font-size: 20px">
            <el-icon size="30px">
              <Edit/>
            </el-icon>
            心理咨询师申请入驻
          </el-button>

          <el-input v-model="input" style="width: 280px;height:40px;
                margin-left: 160px" placeholder="输入姓名进行查询">
            <template #prefix>
              <el-button @click="search" style="border: none">
                <el-icon size="large" style="color: skyblue;">
                  <Search/>
                </el-icon>
              </el-button>
            </template>
          </el-input>
        </div>
        <div class="show"
             style=" background-color:whitesmoke;padding: 10px;border-radius: 10px">
          <ConsultantPage ref="childRef" @sendTotal="handleSendTotal"/>
        </div>
      </el-main>
      <el-footer style="height: 90px;width: 100%">
        <div class="demo-pagination-block">
          <el-pagination
              v-model:current-page="currentPage"
              layout="prev, pager, next, jumper"
              :total="total"
              @current-change="handleCurrentChange"
          />
        </div>
      </el-footer>
    </el-container>
  </div>
</template>
<script setup>
document.title = '用户首页'
import ConsultantPage from "./ConsultantPage.vue";
import {onMounted, ref, provide} from 'vue'
import {useRoute} from "vue-router";
import axios from "axios";
import router from "../router";
import {ArrowLeftBold} from "@element-plus/icons-vue";
//搜索框
const input = ref('')
//定义选项的数据
const value1 = ref('')

// 提供数据
provide('value1', value1);

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

//登出
function logout() {
  sessionStorage.removeItem('token')
  router.push({
    name: 'login'
  })
}

const isConsultant = useRoute().params.isConsultant;

//个人主页

function personal() {
  if (isConsultant === 'no') {
    router.push({
      name: 'home',
    });
  } else
    router.push({
      name: 'consultantHome',
    });
}

const back = () => {
  router.back()

};

//用户名
const username = useRoute().params.username;
//头像
const circleUrl = ref();
const phoneNumber = useRoute().params.phoneNumber;
//分页
const currentPage = ref(1)
//传页数
provide('currentPage', currentPage);
//传搜索词
provide('input', input);
//提供头像和用户名
provide('circleUrl', circleUrl);
provide('username', username)
const total = ref(0); // 父组件中的 total 响应式引用

//子组件
const childRef = ref(null);
//监听事件
const handleSendTotal = (value) => {
  total.value = value; // 接收子组件传递的 total 值
};


//获取头像
onMounted(async () => {
  if (isConsultant == 'no') {
    try {
      const response = await axios.get('http://localhost:8091/user/selectPfp', {
        params: {
          username: username
        }
      });
      circleUrl.value = response.data.data;
    } catch (error) {
      console.error('Error fetching avatar URL:', error);
    }
  } else {
    try {
      const response = await axios.get('http://localhost:8091/consultant/selectPfp', {
        params: {
          username: username
        }
      });
      circleUrl.value = response.data.data;
    } catch (error) {
      console.error('Error fetching avatar URL:', error);
    }
  }

//   try {
//     const response = await axios.get('http://localhost:8091/account/isConsultant', {
//       params: {
//         username: username
//       }
//     });
//     isConsultant.value = response.data.data
//     console.log(isConsultant.value)
//   } catch (error) {
//     console.error('Error fetching isConsultant:', error);
//   }
});


function handleCurrentChange(val) {
  // const currentPage = val
}


//调用子组件方法的函数
const search = () => {
  value1.value = '';
  // 确保子组件已经挂载
  if (childRef.value) {

    childRef.value.fetchData();
  }
};

//申请
async function apply() {
  try {
    const response = await axios.get('http://localhost:8091/applis/again', {
      params: {
        appliPhone: phoneNumber
      }
    });
    // 如果返回状态码为 200，则不跳转
    if (response.data.code === 200) {
      alert('您已提交过申请，请耐心等待审核！')
    } else {
      // 跳转到 'application' 页面
      await router.push({
        name: 'application'
      });
    }
  } catch (error) {
    console.error('Error occurred:', error);
  }
}
</script>
<style scoped>
/*外层容器*/
.common-layout {
  height: 100%;
  width: 100%;
  background-color: #cce5F3;
}

/*咨询师信息组件*/
.show {
  margin-top: 20px;
}

.header {
  font-size: 40px;
  font-family: 楷体;
  margin-left: 100px;
  letter-spacing: 20px;
  margin-top: 20px;
}


.toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

/*头像*/
.ava {
  margin-left: 1200px;
  margin-top: -100px
}

.mr3:hover {
  cursor: pointer;
}

/*分页*/
.demo-pagination-block {
  margin-left: 35%;
  

}

.demo-pagination-block .el-pagination {
  --el-pagination-font-size: 20px; /* 调整字体大小 */
  font-family: 楷体;

}
</style>