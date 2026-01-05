<template>
  <ContentWrap>
    <el-form
      class="module-test-query-form"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="90px"
    >
      <div class="module-test-query-form__row">
        <el-form-item label="结果" prop="judgeResult">
          <el-select
            v-model="queryParams.judgeResult"
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
            class="!w-170px"/>
        </el-form-item>
        <el-form-item label="成品代码">
          <el-input 
            v-model.trim="queryParams.productSN" 
            clearable
            class="!w-170px"/>
        </el-form-item>
         <el-form-item label="模块序列号">
          <el-input 
            v-model.trim="queryParams.moduleSn" 
            clearable
            class="!w-170px"/>
        </el-form-item>
        <el-form-item label="测试项目">
          <el-input 
            v-model.trim="queryParams.testItem" 
            clearable
            class="!w-170px"/>
        </el-form-item>
        
        
      </div>

      <div class="module-test-query-form__row">
        <!-- <el-form-item label="序列号">
          <el-input 
            v-model.trim="queryParams.moduleSN" 
            clearable
            class="!w-200px"/>
        </el-form-item> -->
        <el-form-item label="测试要求">
          <el-input 
            v-model.trim="queryParams.testRequire" 
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
            class="!w-255px"/>
        </el-form-item>
        <el-form-item label=" ">
          <!-- <el-button @click="handleQuery"><Icon icon="ep:search" /> 搜索</el-button> -->
          <el-button  type="danger" @click="()=> dialogStatus = true" >批量删除</el-button>
        </el-form-item> 
      </div>
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
          <el-table-column
            v-if="column.istrue && column.prop !== 'id'"
            :label="column.label"
            :align="column.align"
            :prop="column.prop"
            :formatter="column.formatter"
            :width="column.width"
          />
          <el-table-column
            v-else-if="column.istrue && column.prop === 'id'"
            :label="column.label"
            :align="column.align"
            :width="column.width"
          >
          <template #default="{ row }"> 
            <el-button @click="handleDelete(row.id)" type="danger" link>删除</el-button>
            <el-button @click="()=> {resetQuery = true;handleReset(row)}" type="primary" link>修改</el-button>
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
          <el-form-item label="模块类型" prop="moduleType">
            <el-input v-model="form.moduleType" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="测试结果" prop="judgeResult">
            <!-- <el-input v-model="form.judgeResult" style="width: 100%" /> -->
            <el-select
              v-model="form.judgeResult"
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
          <el-form-item label="测试要求" prop="testRequire">
            <el-input v-model="form.testRequire" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24">
          <el-form-item label="检验结果" prop="testResult">
            <el-input v-model="form.testResult" style="width: 100%" />
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

   <el-dialog v-model="dialogStatus" width="600px" @before-close="()=> dialogStatus = false">
    <div>
      <span class=" mr-2">填写模块序列号：</span>
      <el-input v-model="moduleSn"  style="width: 220px;"/>
      <el-button type="primary" class=" ml-2" @click="handlesubmit">提交</el-button>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { ModuleTestApi } from '@/api/bus/moduleTest'
import { ElMessage, ElMessageBox } from 'element-plus';
import { useDebounceFn } from '@vueuse/core'

const loading = ref(true)
const list = ref<Array<{ }>>([]) as any; 
const total = ref(0)
const resetQuery = ref(false)
const dialogStatus = ref(false)
const moduleSn = ref("")

const queryParams = reactive({
  pageNo: 1,
  pageSize: 15,
  timeRange: undefined,
  orderId: undefined,
  productSN: undefined,
  judgeResult: 'all',
  language: '0',
  moduleSn: undefined,
  testItem: undefined,
  testRequire: undefined
})

const form = reactive({
  id: 0,
  productSn:'',
  moduleSn:'',
  orderId:'',
  moduleType:'',
  testResult:'',
  testItem:'',
  testRequire:'',
  judgeResult:'',
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
watch(() => queryParams.moduleSn, (newModuleSn) => {
  if (newModuleSn == null || newModuleSn == '') {
    queryParams.moduleSn = undefined
  }
})
watch(() => queryParams.timeRange, (newTimeRange) => {
  if (newTimeRange == null || newTimeRange == '') {
    queryParams.timeRange = undefined; 
  }
});
watch(() => queryParams.testItem, (newTestItem) => {
  if (newTestItem == null || newTestItem == '') {
    queryParams.testItem = undefined
  }
})
watch(() => queryParams.testRequire, (newTestRequire) => {
  if (newTestRequire == null || newTestRequire == '') {
    queryParams.testRequire = undefined
  }
})

watch(() => queryParams.judgeResult, (_newJudgeResult) => {
  handleQuery()
});
watch(() => queryParams.language, (_newLanguage) => {
  handleQuery()
});

const tableColumns = ref([
  { label: '订单号', align:'center', prop: 'orderId' , istrue: true, width: '180px'},
  { label: '成品代码', align: 'center', prop: 'productSn' , istrue: true, width: '200px'},
  { label: '模块序列号', align: 'center', prop: 'moduleSn' , istrue: true, width: '230px'},
  { label: '模块类型', align: 'center', prop: 'moduleType' , istrue: true, width: '150px'},
  { label: '检验步骤', align: 'center', prop: 'testStep', istrue: true, width: '200px'},
  { label: '检验项目', align: 'center', prop: 'testItem', istrue: true, width: '220px'},
  { label: '检验要求', align: 'center', prop: 'testRequire', istrue: true, width: '230px'},
  { label: '检验结果', align: 'center', prop: 'testResult', istrue: true, width: '180px'},
  { label: '判定结果', align: 'center', prop: 'judgeResult', istrue: true, formatter: formatJudgeResult},
  { label: '检验时间', align: 'center', prop: 'testTime', istrue: true,  width: '150px'},
  { label: '软件版本', align: 'center', prop: 'softVersion', istrue: true},
  { label: '语言', align: 'center', prop: 'languageSelect' , istrue: true, formatter: formatLanguage, width: '100px'},
  { label:'操作',align:'center',prop:'id',istrue: true,width:'120px'}
]);

/** 搜索按钮操作 */
const handleQuery = () => {
    queryParams.pageNo = 1
    getList()
}
const handleReset =(row)=>{
  console.log("row",row)
  form.id = row.id
  form.productSn = row.productSn
  form.moduleSn = row.moduleSn
  form.orderId = row.orderId
  form.moduleType = row.moduleType
  form.testResult = row.testResult
  form.testItem = row.testItem
  form.testRequire = row.testRequire
  form.judgeResult = row.judgeResult
}


const runQuery = () => {
  queryParams.pageNo = 1
  getList()
}
const runQueryDebounced = useDebounceFn(runQuery, 300)


watch(()=> queryParams.timeRange,()=>{
  runQueryDebounced()
} )

watch(()=> queryParams.orderId,()=>{
  runQueryDebounced()
})

watch(()=> queryParams.productSN,()=>{
  runQueryDebounced()
})

watch(()=> queryParams.moduleSn,()=>{
  runQueryDebounced()
})

watch(()=> queryParams.testItem,()=>{
  runQueryDebounced()
})

watch(()=> queryParams.testRequire,()=>{
  runQueryDebounced()
});


const handlesubmit = async()=>{
  await ElMessageBox.confirm('确定要删除吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
})
const res = await ModuleTestApi.deleteModuleTest(moduleSn)
if(res){
  ElMessage.success('删除成功')
  getList()
}
}

/** 初始化数据 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ModuleTestApi.getModuleTestPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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
function formatJudgeResult(row: any, _column: any, cellValue: number): string {
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


const handleUpdate = async()=>{
  const res = await ModuleTestApi.updateModuleTest(form)
  if(res){
    ElMessage.success('修改成功')
    getList()
  }
}

const handleDelete =async(id)=>{

  await ElMessageBox.confirm('确定要删除吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })



  const res = await ModuleTestApi.deleteModuleTest(id)
  if(res){
    ElMessage.success('删除成功')
    getList()
  
}
}





/** 初始化 **/
onMounted(() => { 
  getList();
})
</script>

<style scoped lang="scss">
.module-test-query-form__row {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  margin-right: 10px;
  gap: 12px 12px;
}

.module-test-query-form__row + .module-test-query-form__row {
  margin-top: 12px;
}

.module-test-query-form__row :deep(.el-form-item) {
  margin: 0;
}
</style>
