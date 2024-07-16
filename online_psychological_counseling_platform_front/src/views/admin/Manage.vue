<script setup>

import {ArrowLeftBold, HomeFilled, List, Postcard, Remove, Setting, UserFilled} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";
import router from "../../router";
import axios from "axios";
import {markRaw, onMounted, ref} from "vue";
import {ElLoading, ElMessage, ElMessageBox} from "element-plus";

document.title = '管理员主页'
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

//首页
function adminHome() {
  router.push({
    name: 'adminHome',
  })
}

//修改密码
function adminPassword() {
  router.push({
    name: 'adminPassword',
  })
}

//修改用户名
function adminUsername() {
  router.push({
    name: 'adminName',
  })
}

//基本信息
function adminInfo() {
  router.push({
    name: 'adminInfo'
  })
}


//修改头像
function adminPfp() {
  router.push({
    name: 'adminPfp'
  })
}

//查看用户信息
function lookUser() {
  router.push({
    name: 'selectUser'
  })
}

//查看咨询师信息
function lookConsultant() {
  router.push({
    name: 'selectConsultant'
  })
}

//评价审核
function evaluate() {
  router.push({
    name: 'evaluateExa'
  })
}

//私信审核
function pm() {
  router.push({
    name: 'pmExa'
  })
}

//私信审核
function appli() {
  router.push({
    name: 'appliExa'
  })
}

const adminName = useRoute().params.username;
//注销
const removeAccount = async () => {
  try {
    const response = await axios.delete('http://localhost:8091/account/remove', {
      params: {
        username: adminName,
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
//获取头像
const circleUrl = ref('')
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8091/admin/selectPfp', {
      params: {
        adminName: useRoute().params.username
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

//日志
function log() {
  router.push({
    name: 'adminLog'
  })
}

//公告
function notice() {
  router.push({
    name: 'notice',
  })
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
          管理员主页
        </span>
              <el-tag>访问管理员:{{ useRoute().params.username }}</el-tag>
            </div>
          </template>
        </el-page-header>
      </el-header>
      <el-container>
        <el-aside width="360px">
          <el-row class="tac" style="margin-top: 65px;height: 770px">
            <el-col :span="12">
              <el-menu
                  active-text-color="#ffd04b"
                  background-color="#545c64"
                  class="el-menu-vertical-demo"
                  default-active="2"
                  text-color="#fff"

              >

                <el-menu-item index="1" @click="adminHome">
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
                      <el-menu-item index="2-1-1" @click="adminInfo">
                        基本信息
                      </el-menu-item>
                      <el-menu-item index="2-1-2" @click="adminPfp">头像修改</el-menu-item>
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
                      <el-menu-item index="2-2-1" @click="adminPassword">登录密码</el-menu-item>
                      <el-menu-item index="2-2-2" @click="adminUsername">用户名</el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>
                  <!--账户管理-->
                  <el-sub-menu index="2-3">
                    <template #title>
                      <el-icon>
                        <UserFilled/>
                      </el-icon>
                      <span>人员管理</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="2-3-1" @click="openFullScreen2();lookUser()">用户管理</el-menu-item>
                      <el-menu-item index="2-3-2" @click="openFullScreen2();lookConsultant()">咨询师管理</el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>
                  <el-sub-menu index="2-4">
                    <template #title>
                      <el-icon>
                        <List/>
                      </el-icon>
                      <span>业务处理</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="2-4-1" @click="openFullScreen2();evaluate()"
                                    type="primary"
                      >评价审核
                      </el-menu-item>
                      <el-menu-item index="2-4-2" @click="openFullScreen2();pm()">私信审核</el-menu-item>
                      <el-menu-item index="2-4-3" @click="openFullScreen2();appli()">资质核验</el-menu-item>
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
                    <el-menu-item index="3-1" @click="openFullScreen2();notice()">
                      <el-icon>
                        <Bell/>
                      </el-icon>
                      <span>公告管理</span>
                    </el-menu-item>
                    <el-menu-item index="3-3" @click="remove">
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