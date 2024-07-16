<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "../../router";
//结果
const result = ref('')
document.title = '资质认证'
</script>

<template>
  <div class="container">
    <el-result style="font-size: 40px" icon="success" title="认证结果">
      <template #sub-title>
        <h1 style="font-size: 20px">认证成功</h1>
      </template>
    </el-result>
  </div>
</template>

<style scoped>
/*容器*/
.container {
  position: absolute;
  top: 70%;
  left: 40%;
  transform: translate(-50%, -50%);
  padding: 20px;
  height: 100%;
  width: 600px;
  text-align: center;
  font-family: 华文楷体;

}

</style>