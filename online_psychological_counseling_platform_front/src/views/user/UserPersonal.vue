<script setup>

import {useRoute} from "vue-router";
import {ArrowLeftBold, HomeFilled, List, Postcard, Remove, RemoveFilled, UserFilled} from "@element-plus/icons-vue";
import {
  Document,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import router from "../../router";
import {markRaw, onMounted, ref} from "vue";
import axios from "axios";
import {ElLoading, ElMessage, ElMessageBox} from "element-plus";

//返回
const back = () => {
  router.back()

};

//登出
function logout() {
  sessionStorage.removeItem('token')
  router.push({
    name: 'login'
  })
}

//基本信息
function basic() {
  router.push({
    name: 'basic',
  })
}

//修改头像
function userPfp() {
  router.push({
    name: 'userPfp',
  })
}

//修改密码
function password() {
  router.push({
    name: 'password',
  })
}

//修改用户名
function upUsername() {
  router.push({
    name: 'username',
  })
}

const username = useRoute().params.username;
//注销
const removeAccount = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/account/remove', {
      params: {
        username: username,
      }
    });
    if (response.data.code === 200) {

      alert('注销成功,如有需要可自行注册!')

      await router.push({
        name: 'login',
      })
    } else {
      ElMessage({
        type: 'error',
        message: '注销失败',
      });
    }
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '注销失败',
    });
  }
};
const remove = () => {
  ElMessageBox.confirm(
      '你的账号将被注销,所有信息将被清空，确定吗?',
      '警告',
      {
        cancelButtonText: '取消',
        confirmButtonText: '确定',

        type: 'warning',
        icon: markRaw(Remove)
      }
  )
      .then(() => {
        removeAccount()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '放弃操作',
        });
      });
};

//预约记录
function res() {
  router.push({
    name: 'userReserve',
  })
}

//资质认证
function qua() {
  router.push({
    name: 'qualification',
  })
}

//我的评价
function eva() {
  router.push({
    name: 'evaluate',
  })
}

//我的私信
function pm() {
  router.push({
    name: 'userPm',
  })
}

//首页
function showHome() {
  router.push({
    name: 'home',
  })
}

//日志
function log() {
  router.push({
    name: 'log'
  })
}

//获取头像
const circleUrl = ref('')
onMounted(async () => {
  const username = useRoute().params.username
  const user = {
    username: username
  };

// 将user对象转换为字符串
  const userStr = JSON.stringify(user);

// 存入sessionStorage
  sessionStorage.setItem("user", userStr);
  try {
    const response = await axios.get('http://localhost:8091/user/selectPfp', {
      params: {
        username: useRoute().params.username
      }
    });
    circleUrl.value = response.data.data;
  } catch (error) {
    console.error('Error fetching avatar URL:', error);
  }
});
const openFullScreen2 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: '数据加载中',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  setTimeout(() => {
    loading.close()
  }, 2000)
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header style="height: 60px;width: 100%;background-color: #cce5F3;z-index:2;position: fixed">
        <el-page-header :icon="ArrowLeftBold" style="margin-top: 15px" @back="back">
          <template #content>
            <div class="flex items-center">
              <el-dropdown>
                <el-avatar :size="32" :src="circleUrl" class="mr3"></el-avatar>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <span class="text-large font-600 mr-3"> 贵大心理 </span>
              <span class="text-sm mr-2" style="color: var(--el-text-color-regular)">
          个人主页
        </span>
              <el-tag>访问用户:{{ useRoute().params.username }}</el-tag>
            </div>
          </template>
        </el-page-header>
      </el-header>
      <el-container>
        <el-aside width="360px">
          <el-row class="tac" style="margin-top: 65px;height: 770px">
            <el-col :span="12">
              <el-menu
                  background-color="#FFFFFF"
                  class="el-menu-vertical-demo"
                  default-active="2"
                  text-color="#000000"

              >

                <el-menu-item index="1" @click="showHome">
                  <el-icon>
                    <HomeFilled/>
                  </el-icon>
                  <span>首页</span>
                </el-menu-item>
                <el-sub-menu index="2">
                  <template #title>
                    <el-icon>
                      <UserFilled/>
                    </el-icon>
                    <span>个人中心</span>
                  </template>
                  <el-sub-menu index="2-1">
                    <template #title>
                      <el-icon>
                        <Postcard/>
                      </el-icon>
                      <span>个人资料</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="2-1-1" @click="basic">
                        基本信息
                      </el-menu-item>
                      <el-menu-item index="2-1-2" @click="userPfp">头像修改</el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>
                  <!--个人资料-->
                  <el-sub-menu index="2-2">
                    <template #title>
                      <el-icon>
                        <User/>
                      </el-icon>
                      <span>账户管理</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="2-2-1" @click="password">登录密码</el-menu-item>
                      <el-menu-item index="2-2-2" @click="upUsername">用户名</el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>
                  <!--账户管理-->
                  <el-sub-menu index="2-3">
                    <template #title>
                      <el-icon>
                        <List/>
                      </el-icon>
                      <span>我的业务</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="2-3-1" @click="openFullScreen2();res()">预约记录</el-menu-item>
                      <el-menu-item index="2-3-2" @click="openFullScreen2();eva()">我的评价</el-menu-item>
                      <el-menu-item index="2-3-3" @click="openFullScreen2();pm()">我的私信</el-menu-item>
                      <el-menu-item index="2-3-4" @click="qua">资质认证</el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>
                  <!--我的业务-->
                </el-sub-menu>
                <el-sub-menu index="3">
                  <template #title>
                    <el-icon>
                      <Setting/>
                    </el-icon>
                    <span>设置</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="3-1" @click="openFullScreen2();log()">
                      <el-icon>
                        <View/>
                      </el-icon>
                      <span>登录日志</span>
                    </el-menu-item>
                    <el-menu-item index="3-2" @click="remove">
                      <el-icon>
                        <Remove/>
                      </el-icon>
                      <span>注销账户</span>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
                <!--设置-->
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-container>
          <el-main class="main">
            <router-view/>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
/*外层容器*/
.common-layout {
  height: 100%;
  width: 100%;
  background-color: #cce5F3;
}

.mb-2 {
  --el-alert-bg-color: blue;
  text-align: center;
  font-size: 20px;

}

.main {
  width: 100%;
  border: whitesmoke solid 1px;
  margin-left: -170px;
  margin-top: 65px;
  background-color: whitesmoke;
}
</style>