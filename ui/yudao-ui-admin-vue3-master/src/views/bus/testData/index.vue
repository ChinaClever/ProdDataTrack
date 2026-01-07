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
            v-model.trim="queryParams.orderId" 
            clearable
            class="!w-140px"/>
        </el-form-item>
        <el-form-item label="成品代码">
          <el-input 
            v-model.trim="queryParams.productSN" 
            clearable
            class="!w-180px"/>
        </el-form-item>
        <el-form-item label="模块序列号">
          <el-input 
            v-model.trim="queryParams.moduleSn" 
            clearable
            class="!w-140px"/>
        </el-form-item>
        <el-form-item label="测试项目">
          <el-input 
            v-model.trim="queryParams.testItem" 
            clearable
            class="!w-140px"/>
        </el-form-item>
        <el-form-item label="测试要求">
          <el-input 
            v-model.trim="queryParams.testRequest" 
            clearable
            class="!w-140px"/>
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
          <!-- <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-1px" /> 搜索</el-button> -->
          <el-button  type="danger" @click="()=> dialogStatus = true" >批量删除</el-button>
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
      <template v-for="column in tableColumns" :key="column.prop">
  <!-- 普通列 -->
        <el-table-column
          v-if="column.istrue && column.prop !== 'id'"
          :label="column.label"
          :align="column.align"
          :prop="column.prop"
          :formatter="column.formatter"
          :width="column.width"
        />

        <!-- 操作列 -->
        <el-table-column
          v-else-if="column.istrue && column.prop === 'id'"
          :label="column.label"
          :align="column.align"
          :width="column.width"
        >
          <template #default="{ row }">
            <el-button type="primary" link @click="handleAction(row.id)">删除</el-button>
            <el-button type="primary" link @click="()=> {resetQuery = true; handleReset(row)}" >修改</el-button>
            <el-button type="primary" link @click="()=> {openUrlOpen = true;antherArr = row}">查看报告</el-button>
          </template>
        </el-table-column>
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


  <el-dialog v-model="dialogStatus" width="600px" @before-close="()=> dialogStatus = false">
    <div>
      <span class=" mr-2">填写模块序列号：</span>
      <el-input v-model="moduleSn"  style="width: 220px;"/>
      <el-button type="primary" class=" ml-2" @click="handlesubmit">提交</el-button>
    </div>
  </el-dialog>

  <el-dialog v-model="resetQuery" width="800px" @before-close="()=> {resetQuery = false; }">
    <div>
      
     <el-form ref="formRef" :model="form" label-width="80px">
      <el-row :gutter="16">
        <el-col :xs="24" :sm="12">
          <el-form-item label="订单号" prop="orderId">
            <el-input v-model="form.orderId" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="成品代码" prop="productSn">
            <el-input v-model="form.productSn" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="模块序列" prop="moduleSn">
            <el-input v-model="form.moduleSn" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="设备名称" prop="devName">
            <el-input v-model="form.devName" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="测试结果" prop="testResult">
            <!-- <el-input v-model="form.testResult" style="width: 100%" /> -->
            <el-select
              v-model="form.testResult"
              placeholder="请选择测试结果"
              class="!w-90px">
              <el-option label="通过" value="1" />
              <el-option label="失败" value="0" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="测试项目" prop="testItem">
            <el-input v-model="form.testItem" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24">
          <el-form-item label="测试要求" prop="testRequest">
            <el-input v-model="form.testRequest" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="结果判定" prop="testProcess">
            <el-input v-model="form.testProcess" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12">
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="12">
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </div>
  </el-dialog>

  <el-dialog
    v-model="openUrlOpen"
    width="350px"
    title="查看报告"
    :before-close="(done) => {
      openUrlOpen = false
      antherArr = null
      done()
    }"
  >
    <div>
       <el-button type="primary" @click="handleOpenReport">查看外部出厂报告</el-button>
    <el-button type="primary" @click="handleOutReport">查看内部出厂报告</el-button>
    </div>
  </el-dialog>


</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { TestDataApi } from '@/api/bus/testData'
import { ElMessage, ElMessageBox } from 'element-plus';
import { useDebounceFn } from '@vueuse/core'

import router from '@/router';
const loading = ref(true)
const list = ref<Array<{ }>>([]) as any; 
const total = ref(0)
const dialogStatus = ref(false)
const moduleSn = ref("")
const resetQuery = ref(false)
const openUrlOpen = ref(false)
const formRef = ref()
const antherArr = ref<any | null>(null)

const form = reactive({
  id: 0,
  productSn:'',
  moduleSn:'',
  orderId:'',
  devName:'',
  testResult:'',
  testItem:'',
  testRequest:'',
  testProcess:'',
  endTime:'',
  startTime:'',
})

const queryParams = reactive({
  pageNo: 1,
  pageSize: 15,
  timeRange: undefined,
  orderId: undefined,
  productSN: undefined,
  testResult: 'all',
  language: '0',
  moduleSn:undefined,
  testItem:undefined,
  testRequest:undefined
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

const runQuery = () => {
  queryParams.pageNo = 1
  getList()
}
const runQueryDebounced = useDebounceFn(runQuery, 300)

// 监听 queryParams.productSN,orderId,timeRange 的变化 如果是空或 null，设置为 undefined 不然搜索不到
watch(() => queryParams.productSN, (newProductSN) => {
  if (newProductSN == null || newProductSN == '') {
    queryParams.productSN = undefined; 
  }
  runQueryDebounced()
});
watch(() => queryParams.orderId, (newOrderId) => {
  if (newOrderId == null || newOrderId == '') {
    queryParams.orderId = undefined; 
  }
  runQueryDebounced()
});
watch(() => queryParams.timeRange, (newTimeRange) => {
  if (newTimeRange == null || newTimeRange == '') {
    queryParams.timeRange = undefined; 
  }
  runQueryDebounced()
});

watch(() => queryParams.testResult, (_newTestResult) => {
  runQueryDebounced()
});
watch(() => queryParams.language, (_newLanguage) => {
  runQueryDebounced()
});

watch(() => queryParams.moduleSn, (newModuleSn) => {
  if (newModuleSn == null || newModuleSn === '') {
    queryParams.moduleSn = undefined
  }
  runQueryDebounced()
})

watch(() => queryParams.testItem, (newTestItem) => {
  if (newTestItem == null || newTestItem === '') {
    queryParams.testItem = undefined
  }
  runQueryDebounced()
})
watch(() => queryParams.testRequest, (testRequest) => {
  if (testRequest == null || testRequest === '') {
    queryParams.testRequest = undefined
  }
  runQueryDebounced()
})

const tableColumns = ref([
  { label: '订单号', align: 'center', prop: 'orderId' , istrue: true, width: '200px'},
  { label: '成品代码', align: 'center', prop: 'productSn' , istrue: true, width: '230px'},
  { label: '模块序列号', align: 'center', prop: 'moduleSn' , istrue: true, width: '230px'},
  // { label: '订单数量', align: 'center', prop: 'order_num' , istrue: true, width: '100px'},
  // { label: '已测试数量', align: 'center', prop: 'test_num' , istrue: true, width: '100px'},
  { label: '设备名称', align: 'center', prop: 'devName' , istrue: true, width: '150px'},
  // { label: '工具名称', align: 'center', prop: 'tool_name' , istrue: true, width: '150px'},
  { label: '测试结果', align: 'center', prop: 'testResult', istrue: true, formatter: formatTestResult},
  { label: '测试项目', align: 'center', prop: 'testItem', istrue: true, width: '200px'},
  // { label: 'test_cfg', align: 'center', prop: 'test_cfg', istrue: true, width: '200px'},
  { label: '测试要求', align: 'center', prop: 'testRequest', istrue: true, width: '200px'},
  { label: '结果判定', align: 'center', prop: 'testProcess', istrue: true, width: '200px'},
  { label: '开始时间', align: 'center', prop: 'startTime', istrue: true, formatter: formatTime, width: '200px'},
  { label: '结束时间', align: 'center', prop: 'endTime', istrue:true, formatter: formatTime, width: '200px'},  
  { label: '软件版本', align: 'center', prop: 'softVersion', istrue: true},
  { label: '语言', align: 'center', prop: 'languageSelect' , istrue: true, formatter: formatLanguage, width: '100px'},
  { label:'操作',align:'center',prop:'id',istrue: true,width:'200px'}
]);

const handleReset =(row)=>{
  console.log("row",row)
  form.id = row.id
  form.productSn = row.productSn
  form.moduleSn = row.moduleSn
  form.orderId = row.orderId
  form.devName = row.devName
  form.testResult = row.testResult
  form.testItem = row.testItem
  form.testRequest = row.testRequest
  form.testProcess = row.testProcess
  form.startTime = row.startTime
  form.endTime = row.endTime
}


const handleUpdate = async ()=>{
  const res = await TestDataApi.updateTestData(form)
  if(res){
    ElMessage.success('修改成功')
    getList()
  }
}

const handleOpenReport = () => {
  const row = antherArr.value
  if (!row) {
    ElMessage.warning('请先选择一条数据')
    return
  }
  const productSN = row.productSn ?? row.productSN
  const moduleSN = row.moduleSn ?? row.moduleSN
  const orderId = row.orderId
  if (!productSN || !moduleSN || !orderId) {
    ElMessage.warning('报告参数不完整，请检查数据')
    return
  }
  const url = router.resolve({
    path: '/report',
    query: { productSN: String(productSN), orderId: String(orderId), moduleSN: String(moduleSN) }
  }).href
  window.open(url, '_blank', 'noopener,noreferrer')
}


const handleOutReport = () => {
  const row = antherArr.value
  if (!row) {
    ElMessage.warning('请先选择一条数据')
    return
  }
  const moduleSN = row.moduleSn ?? row.moduleSN
  if (!moduleSN) {
    ElMessage.warning('模块序列号为空，无法打开内部报告')
    return
  }
  openUrlOpen.value = false
  router.push({
    path: '/bus/busreport',
    query: { moduleSN: String(moduleSN) }
  })
}


/** 搜索按钮操作 */

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

const handleAction = async (id: number | string) => {
  
  console.log('id =', id)
  await ElMessageBox.confirm('确定要删除吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
  const res = await TestDataApi.deleteTestData(id)
  if(res){
    ElMessage.success('删除成功')
    getList()
  }
}

const handlesubmit =async ()=>{
  const res = await TestDataApi.deleteBatchTestData(moduleSn.value)
  if(res){
    ElMessage.success('删除成功')
    getList()
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
    if( row.languageSelect == '0'){
      return '失败'
    }
    return 'Fail'
  }else{
    if( row.languageSelect == '0'){
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

<style scoped lang="scss">
:deep(.el-form-item){
  margin-right: 10px;
}
</style>
