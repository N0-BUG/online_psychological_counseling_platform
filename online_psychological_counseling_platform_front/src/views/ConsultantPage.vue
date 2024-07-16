<template>
  <el-space alignment="flex-start" direction="vertical">
    <el-skeleton :loading="loading" :throttle="1000" animated="true" style="width: 240px">
      <template #template>
        <el-skeleton-item style="width: 240px; height: 240px" variant="image"/>
        <div style="padding: 14px">
          <el-skeleton-item style="width: 50%" variant="h3"/>
          <div style=" display: flex; align-items: center; margin-top: 16px; height: 16px; ">
            <el-skeleton-item style="margin-right: 16px" variant="text"/>
            <el-skeleton-item style="width: 30%" variant="text"/>
          </div>
        </div>
      </template>
      <template #default>
        <el-card v-for="consultant in filterConsultants" :key="consultant.consultantPhone"

                 style="margin-left: 20px; flex:0 0 250px;margin-top: 10px">
          <img class="image"
               :src="consultantImageUrl(consultant)"
               style="background-size: 100%;object-fit: cover"/>
          <div style="padding: 4px;overflow: hidden;line-height: 20px;font-family: 楷体">
            <div>年龄:{{ consultant.consultantAge }}</div>
            <div>性别:{{ consultant.consultantSex }}</div>
            <span>姓名:{{ consultant.consultantName }}</span>
            <div class="bottom card-header">
              <div>擅长类型:{{ consultant.consultantType }}</div>
              <span>累计帮助人数:<strong>{{ consultant.consultantNo }}</strong>人</span> <br/>
              <el-button @click="consultantDetail(consultant)"
                         class="button" style="margin-left: 120px" text>查看详情
              </el-button>
            </div>
          </div>
        </el-card>
      </template>
    </el-skeleton>
  </el-space>
</template>
<script setup>
import {computed, inject, onMounted, ref, defineEmits, watch} from 'vue'
import axios from "axios";
import {useRouter} from "vue-router";

const loading = ref(false)
const consultants = ref([]);
const filterConsultants = ref([]);
//注入页数
const currentPage = inject('currentPage');
//注入关键词
const input = inject('input');
//总页数
const total = ref('')
// 从父组件注入数据
const value1 = inject('value1');

const circleUrl = inject('circleUrl')
const username = inject('username')
//定义一个事件
const emit = defineEmits(['sendTotal']);
const fetchData = async () => {
  try {
    loading.value = true
    const response = await axios.get('http://localhost:8091/consultant/findAll', {
      params: {
        pageNum: currentPage.value, // 这里应该是你想要的页码
        pageSize: 15, // 这里应该是你想要的每页数量(固定）
        input: input.value
      }
    })
    //不搜索刷新

    total.value = response.data.data.total;
    emit('sendTotal', total.value); // 触发事件，将 total 值发送给父组件
    consultants.value = response.data.data.records;
    filterConsultants.value = response.data.data.records;
    filterConsultants.value = filteredData.value
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false

  }
};

//过滤数据
const filteredData = computed(() => {
  // 如果 value1 为空，则不进行过滤
  if (value1.value == null) {
    return consultants.value;
  }
  // 使用 filter 方法进行过滤
  return consultants.value.filter(item => {
    // 如果 value1 不为空，检查 consultantType 字段是否包含 value1 的值
    const typeMatch = !value1.value || item.consultantType.includes(value1.value);

    // 如果两者都不为空，则同时满足条件
    return typeMatch;

  });
});

// 监听 value1 和 value2 的变化，以便在它们变化时更新过滤后的数据
watch(value1, () => {
  filterConsultants.value = filteredData.value
});
const consultantImageUrl = (consultant) => {
  // 假设后端返回的 consultantPfp 是图片的标识符，你需要构建完整的图片 URL
  return `${consultant.consultantPfp}`;
}
watch(input, (newValue) => {
  // 只在 searchValue 被删除时触发
  if (!newValue) {
    location.reload()
  }
})
//监听页码
watch(currentPage, () => {
  fetchData();
});
//在组件挂载时获取数据
onMounted(() => {
  fetchData()
})
defineExpose({
  fetchData
});
//查看详情
const router = useRouter()

function consultantDetail(consultant) {
  try {
    router.push({
      name: 'consultantDetail',
      params: {
        consultantName: consultant.consultantName,
        consultantPhone: consultant.consultantPhone,
        circleUrl: circleUrl.value,
        username: username,

      }
    });
  } catch (error) {
    console.error('路由跳转错误：', error);
  }
}
</script>

<style scoped>
.image {
  width: 100%;
}
</style>