<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <span style="margin-left: 40px">
   <el-icon size="90"><Promotion/></el-icon>
        </span>
        <span class="t1">
          在线心理咨询平台
        </span>
        <span class="t2">
          欢迎进入
        </span>
      </el-header>
      <el-container>
        <el-aside class="aside">

        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>

</script>
<style scoped>
.aside {
  background-image: url("/src/assets/aside.jpg");
}

/*标题*/
.t1 {
  margin-left: 10px;
  font-size: 60px;
  font-family: 字心坊小呀小布丁体;
  color: skyblue;
}

.t2 {
  font-size: 30px;
  margin-left: 30px;
  font-family: 幼圆;
}


/*外层容器*/
.common-layout {
  height: 100%;
  width: 100%;

  :deep(.el-button) {
    margin-top: 20px;
    width: 370px;
    margin-left: 120px;
    text-align: center;
    height: 40px;
    border-radius: 10px;
  }

  :deep(.el-aside) {
    background-size: 100% 650px;
    height: calc(70vh);
    width: 60.2%;
    position: absolute;
    top: 50%;
    transform: translate(-10px, -50%);
  }

  :deep(.el-main) {
    font-family: 幼圆;
    font-size: 25px;
    text-align: center;
    padding-top: 40px;
    height: calc(70vh);
    top: 50%;
    position: absolute;
    width: 591px;
    transform: translate(882px, -50%);
    background-image: url("/src/assets/main.webp");
    background-size: 100% 560px;
  }
}


</style>