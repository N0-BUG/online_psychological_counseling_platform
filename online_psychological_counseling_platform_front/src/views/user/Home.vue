<script setup>
import {onMounted, ref} from 'vue'
import axios from "axios";

const items = ref([])
//公告信息
const noticeData = async () => {
  try {
    const response = await axios.get('http://localhost:8091/notice/info', {})
    items.value = response.data.data

  } catch (error) {
    console.error(error)
  }
};
console.log(items.value)
onMounted(() => {
  noticeData()
})
</script>
<template>
  <div class="block text-center" style="height: 100%;width: 100%;font-family: '微軟正黑體 Light'">
    <el-card class="announcement-board">
      <template #header>
        <span style="font-family: '微軟正黑體 Light'">公告栏</span>
      </template>
      <el-timeline>
        <el-timeline-item
            v-for="item in items"
            :key="item.noticeId"
            :timestamp="item.noticeTime"
            placement="top"
        >
          <el-card>
            <h4>{{ item.noticeTitle }}</h4>
            <p>{{ item.noticeInfo }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>

  </div>

</template>

<style scoped>
.announcement-board {
  margin: 0 20px 0;
}

/*走马灯*/
.el-carousel__item h3 {
  z-index: -1;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}
</style>

