<template>
  <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
    <!-- 添加行号列 -->
    <!-- <el-table-column label="序号" align="center" width="80px">
      <template #default="{ $index }">
        {{ $index + 1 + (queryParams.pageNo - 1) * queryParams.pageSize }}
      </template>
    </el-table-column> -->
    <!-- 遍历其他列 -->  
    <template v-for="column in tableColumns">
      <el-table-column :key="column.prop" :label="column.label" :align="column.align" :prop="column.prop" :formatter="column.formatter" :width="column.width" v-if="column.istrue"/>
    </template>
  </el-table>
  <!-- 分页 -->
  <Pagination
    :total="total"
    :page-size-arr="pageSizeArr"
    layout = "sizes, prev, pager, next, jumper"
    v-model:page="queryParams.pageNo"
    v-model:limit="queryParams.pageSize"
    />
</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { TestDataApi } from '@/api/bus/testData'
const loading = ref(true)
const list = ref<Array<{ }>>([]) as any; 
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 15,
})
const pageSizeArr = ref([15,30,50,100])

const tableColumns = ref([
  { label: 'product_sn', align: 'center', prop: 'product_sn' , istrue: true, width: '100px'},
  { label: 'soft_version', align: 'center', prop: 'soft_version', istrue: true},
  { label: 'language_select', align: 'center', prop: 'language_select' , istrue: true},
  { label: 'order_id', align: 'center', prop: 'order_id' , istrue: true},
  { label: 'order_num', align: 'center', prop: 'order_num' , istrue: true},
  { label: 'test_num', align: 'center', prop: 'test_num' , istrue: true},
  { label: 'dev_name', align: 'center', prop: 'dev_name' , istrue: true},
  { label: 'tool_name', align: 'center', prop: 'tool_name' , istrue: true},
  { label: 'test_result', align: 'center', prop: 'test_result', istrue: true},
  { label: 'start_time', align: 'center', prop: 'start_time', istrue: true, formatter: formatTime},
  { label: 'test_type', align: 'center', prop: 'test_type', istrue: true},
  { label: 'test_step', align: 'center', prop: 'test_step', istrue: true},
  { label: 'test_item', align: 'center', prop: 'test_item', istrue: true},
  { label: 'test_cfg', align: 'center', prop: 'test_cfg', istrue: true},
  { label: 'test_request', align: 'center', prop: 'test_request', istrue: true},
  { label: 'test_process', align: 'center', prop: 'test_process', istrue: true},
  { label: 'end_time', align: 'center', prop: 'end_time', istrue:true, formatter: formatTime,},
]);


/** 初始化数据 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TestDataApi.getTestDataPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// 格式化日期
function formatTime(_row: any, _column: any, cellValue: number): string {
  if (!cellValue) {
    return ''
  }
  return dayjs(cellValue).format('YYYY-MM-DD HH:mm:ss')
}

/** 初始化 **/
onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>