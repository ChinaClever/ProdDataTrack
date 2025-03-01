<template>
  <ContentWrap>
    <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="auto">
        <el-form-item label="结果" prop="testResult">
          <el-select
            v-model="queryParams.testResult"
            placeholder="请选择测试结果"
            class="!w-90px">
            <el-option label="全部" value="all" />
            <el-option label="通过" value="1" />
            <el-option label="失败" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="语言" prop="language">
          <el-select
            v-model="queryParams.language"
            placeholder="请选择语言"
            class="!w-90px">
            <el-option label="全部" value="all" />
            <el-option label="中文" value="0" />
            <el-option label="英文" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单号">
          <el-input 
            v-model="queryParams.orderId" 
            clearable
            class="!w-170px"/>
        </el-form-item>
        <el-form-item label="成品代码">
          <el-input 
            v-model="queryParams.productSN" 
            clearable
            class="!w-200px"/>
        </el-form-item>
        <el-form-item label="结束时间段" prop="timeRange">
          <el-date-picker
            value-format="YYYY-MM-DD HH:mm:ss"
            v-model="queryParams.timeRange"
            type="datetimerange"
            :shortcuts="shortcuts"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            :disabled-date="disabledDate" 
            class="!w-335px"/>
        </el-form-item>
        <el-form-item >
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-1px" /> 搜索</el-button>
        </el-form-item> 
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <!-- 添加行号列 -->
      <el-table-column label="序号" align="center" width="60px">
        <template #default="{ $index }">
          {{ $index + 1 + (queryParams.pageNo - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <!-- 遍历其他列 -->  
      <template v-for="column in tableColumns">
        <el-table-column :key="column.prop" :label="column.label" :align="column.align" :prop="column.prop" :formatter="column.formatter" :width="column.width" v-if="column.istrue"/>
      </template>
    </el-table>
    <Pagination
      :total="total"
      :page-size-arr="pageSizeArr"
      layout = "total, sizes, prev, pager, next, jumper"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"/>
  </ContentWrap>
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
  timeRange: undefined,
  orderId: undefined,
  productSN: undefined,
  testResult: 'all',
  language: '0'
})
const pageSizeArr = ref([15,30,50,100])
// 时间段快捷选项
const shortcuts = [
    {
    text: '最近一小时',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setHours(start.getHours() - 1)
      return [start, end]
    },
  },
    {
    text: '最近一天',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setDate(start.getDate() - 1)
      return [start, end]
    },
  },
  {
    text: '最近一周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setDate(start.getDate() - 7)
      return [start, end]
    },
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setMonth(start.getMonth() - 1)
      return [start, end]
    },
  },
  {
    text: '最近六个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setMonth(start.getMonth() - 6)
      return [start, end]
    },
  },
]
// 禁选未来的日期
const disabledDate = (date) => {
  const today = new Date();
  return date > today;
}

// 监听 queryParams.productSN,orderId,timeRange 的变化 如果是空或 null，设置为 undefined 不然搜索不到
watch(() => queryParams.productSN, (newProductSN) => {
  if (newProductSN == null || newProductSN == '') {
    queryParams.productSN = undefined; 
  }
});
watch(() => queryParams.orderId, (newOrderId) => {
  if (newOrderId == null || newOrderId == '') {
    queryParams.orderId = undefined; 
  }
});
watch(() => queryParams.timeRange, (newTimeRange) => {
  if (newTimeRange == null || newTimeRange == '') {
    queryParams.timeRange = undefined; 
  }
});

watch(() => queryParams.testResult, (_newTestResult) => {
  handleQuery()
});
watch(() => queryParams.language, (_newLanguage) => {
  handleQuery()
});

const tableColumns = ref([
  { label: '订单号', align: 'center', prop: 'order_id' , istrue: true, width: '200px'},
  { label: '成品代码', align: 'center', prop: 'product_sn' , istrue: true, width: '230px'},
  { label: '模块序列号', align: 'center', prop: 'module_sn' , istrue: true, width: '230px'},
  // { label: '订单数量', align: 'center', prop: 'order_num' , istrue: true, width: '100px'},
  // { label: '已测试数量', align: 'center', prop: 'test_num' , istrue: true, width: '100px'},
  { label: '设备名称', align: 'center', prop: 'dev_name' , istrue: true, width: '150px'},
  // { label: '工具名称', align: 'center', prop: 'tool_name' , istrue: true, width: '150px'},
  { label: '测试结果', align: 'center', prop: 'test_result', istrue: true, formatter: formatTestResult},
  { label: '测试项目', align: 'center', prop: 'test_item', istrue: true, width: '200px'},
  // { label: 'test_cfg', align: 'center', prop: 'test_cfg', istrue: true, width: '200px'},
  { label: '测试要求', align: 'center', prop: 'test_request', istrue: true, width: '200px'},
  { label: '结果判定', align: 'center', prop: 'test_process', istrue: true, width: '200px'},
  { label: '开始时间', align: 'center', prop: 'start_time', istrue: true, formatter: formatTime, width: '200px'},
  { label: '结束时间', align: 'center', prop: 'end_time', istrue:true, formatter: formatTime, width: '200px'},  
  { label: '软件版本', align: 'center', prop: 'soft_version', istrue: true},
  { label: '语言', align: 'center', prop: 'language_select' , istrue: true, formatter: formatLanguage, width: '100px'},
]);

/** 搜索按钮操作 */
const handleQuery = () => {
    queryParams.pageNo = 1
    getList()
}

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

// 格式化日期列
function formatTime(_row: any, _column: any, cellValue: number): string {
  if (!cellValue) {
    return ''
  }
  return dayjs(cellValue).format('YYYY-MM-DD HH:mm:ss')
}
// 格式化语言列
function formatLanguage(_row: any, _column: any, cellValue: number): string {
  if (cellValue == 0) {
    return '中文'
  }else{
    return 'English'
  }
}
// 格式化结果列
function formatTestResult(row: any, _column: any, cellValue: number): string {
  if (cellValue == 0) {
    if( row.language_select == '0'){
      return '失败'
    }
    return 'Fail'
  }else{
    if( row.language_select == '0'){
    return '通过'
    }
    return 'Pass'
  }
}

/** 初始化 **/
onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>