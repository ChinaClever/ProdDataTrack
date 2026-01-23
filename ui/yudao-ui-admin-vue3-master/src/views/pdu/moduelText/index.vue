<template>
  <div class="  w-full bg-white">
      <el-col :xl="24"  :xs="24"  :lg="24"  >
        
           <el-card  class=" flex-col flex-1" >
            <el-skeleton animated :loading="load">
              <el-form inline>
                <el-form-item label="结果">
                  <el-select style="width:160px" v-model="queryParams.result" >
                    <el-option label="全部" value="all" />
                    <el-option label="通过" value="1" />
                    <el-option label="失败" value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="语言">
                  <el-select style="width:160px"  v-model="queryParams.language">
                    <el-option label="全部" value="all" />
                    <el-option label="中文" value="0" />
                    <el-option label="英文" value="1" />
                    </el-select>
                </el-form-item>
                <el-form-item label="订单号">
                  <el-input v-model="queryParams.orderId" style="width:160px" clearable />
                </el-form-item>
                <el-form-item label="客户名">
                  <el-input v-model="queryParams.clientName" style="width:160px" clearable />
                </el-form-item>
                <el-form-item label="模块类型">
                    <el-input v-model="queryParams.moduleType" style="width:160px" clearable />
                </el-form-item>
                <el-form-item label="模块序列号">
                    <el-input v-model="queryParams.moduleSn" style="width:160px" clearable />
                </el-form-item>
                <el-form-item label="成品代码">
                    <el-input v-model="queryParams.productSn" style="width:160px" clearable />
                </el-form-item>
                <el-form-item label="时间段">
                  <el-date-picker
                    v-model="queryParams.timeRange"
                    style="width:320px"
                    type="datetimerange"
                    range-separator="-"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    format="YYYY-MM-DD HH:mm:ss"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    clearable
                  />
                </el-form-item>
                <el-form-item label="显示列">
                  <el-checkbox-group v-model="selectedOptionalColumns">
                    <el-checkbox v-for="col in optionalColumns" :key="col.key" :label="col.key">
                      {{ col.label }}
                    </el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-form>
            </el-skeleton>
          </el-card>
      
       
      </el-col>
      
  </div>
  <div class="mt-6">
        <el-table :data="table" class=" table-style" border>
          <el-table-column
            label="序号"
            type="index"
            :index="getRowIndex"
            min-width="80"
            align="center"
          />
          <el-table-column  label="测试时间" prop="testDate" min-width="120" align="center" />
          <el-table-column label="客户名称"  prop="clientName" min-width="130" align="center"   />
          <el-table-column label="模块类型" prop="moduleType" min-width="130" align="center"  />
            <el-table-column label="测试结果" prop="result"  min-width="130" align="center" >
            <template #default="{ row }" v-if="queryParams.language == '0'">

            <el-tag v-if="row.result === '1'" type="success">通过</el-tag>
            <el-tag v-else type="danger">失败</el-tag>
          </template>
            <template #default="{ row }" v-else-if="queryParams.language == '1'">

            <el-tag v-if="row.result === '1'" type="success">Pass</el-tag>
            <el-tag v-else type="danger">Fail</el-tag>
          </template> 
          <template #default="{ row }" v-else-if="queryParams.language == 'all'">

            <el-tag v-if="row.languageSelect === '0'" type="success">通过</el-tag>
            <el-tag v-else type="danger">失败</el-tag>
          </template>
            </el-table-column>
            <!-- 工时 -->
             <el-table-column label="工时(秒)" prop="testTime" min-width="120" align="center" />
             <el-table-column label="软件版本" prop="softVersion" min-width="120" align="center"  />
             <el-table-column label="模块序列号" prop="moduleSn" min-width="120" align="center"  />
          <el-table-column  label="订单号" prop="orderId" min-width="120" align="center" />
          <el-table-column label="成品代码"  prop="productSn" min-width="120" align="center"  />
          
          <el-table-column v-if="isColumnEnabled('companyName')" label="公司名称" prop="companyName" min-width="120" align="center" />
          <el-table-column v-if="isColumnEnabled('languageSelect')" label="语言" prop="languageSelect" min-width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.languageSelect === '0'" type="success">中文</el-tag>
              <el-tag v-else type="warning">英文</el-tag>
            </template>
          </el-table-column>
          <el-table-column v-if ="isColumnEnabled('testStartTime')"   label="测试开始时间" prop="testStartTime" min-width="120" align="center"  />
          <el-table-column v-if="isColumnEnabled('testEndTime')"     label="测试结束时间" prop="testEndTime" min-width="120" align="center"  />
          <el-table-column v-if="isColumnEnabled('orderNum')" label="订单数量" prop="orderNum" min-width="120" align="center" />
          <el-table-column v-if="isColumnEnabled('orderNum')" label="订单数量" prop="orderNum" min-width="120" align="center" />
          <el-table-column v-if="isColumnEnabled('toolName')" label="工具名称" prop="toolName" min-width="120" align="center" />
          <el-table-column
            v-if="isColumnEnabled('protocolVersion')"
            label="协议版本"
            prop="protocolVersion"
            min-width="120"
            align="center"
          />
          <el-table-column
            v-if="isColumnEnabled('macAddress')"
            label="mac地址"
            prop="macAddress"
            min-width="120"
            align="center"
          />
            <el-table-column label="详情"  min-width="120" align="center"  >
              <template #default="{row}" >
                <el-button link type="primary" @click="handleDetails(row)">详情</el-button>
              </template>
            </el-table-column>
            <el-table-column label="操作"  min-width="140" align="center"  >
              <template #default="{row}" >
                <el-button link type="primary" @click="handleEdit(row)">修改</el-button>
                <el-button link type="danger" @click="handledelete(row)">删除</el-button>
                <!-- <el-button type="primary" link @click="()=> {openUrlOpen = true;antherArr = row}">查看报告</el-button> -->
              </template>
            </el-table-column>
              </el-table>
              <Pagination
            :total="total"
            :page-size-arr="pageSizeArr"
            layout = "total, sizes, prev, pager, next, jumper"
            v-model:page="queryParams.pageNo"
            v-model:limit="queryParams.pageSize"
            @pagination="getList"/>
      </div> 

      <el-dialog v-model="detaildialogStatu" title="测试详情" width="90%" @closed="resetDetailDialog">
        <div class="detail-dialog__table">
          <el-table :data="detailTableData" class="table-style" border>
          <el-table-column prop="no" label="编号" width="80" align="center" />
          <el-table-column prop="testItem" label="测试项" min-width="140" align="center" />
          <el-table-column prop="testStep" label="测试步骤" min-width="140" align="center" />
          <el-table-column prop="testProcess" label="测试过程" min-width="200" />
          <el-table-column prop="testRequest" label="测试要求" min-width="160" />
          <el-table-column prop="testResult" label="结果" width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.testResult === 1 ? 'success' : 'danger'">
                {{ row.testResult === 1 ? '通过' : '失败' }}
              </el-tag>
            </template>
          </el-table-column>
          </el-table>
        </div>
        <div v-if="detailTotal > 0" class="detail-dialog__pagination">
          <Pagination
            class="detail-dialog__pagination-inner"
            :total="detailTotal"
            :page-size-arr="detailPageSizeArr"
            layout="total, sizes, prev, pager, next, jumper"
            v-model:page="detailQueryParams.pageNo"
            v-model:limit="detailQueryParams.pageSize"
            @pagination="handleDetailPagination"
          />
        </div>
      </el-dialog>

      <el-dialog v-model="dailogStatus" title="修改" width="90%" @closed="resetEditDialog">
        <el-form :model="editForm" label-width="90px">
          <el-row :gutter="12">
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="ID">
                <el-input v-model="editForm.id" disabled />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="订单号">
                <el-input v-model="editForm.orderId" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="模块序列号">
                <el-input v-model="editForm.moduleSn" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="成品代码">
                <el-input v-model="editForm.productSn" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="订单数量">
                <el-input-number v-model="editForm.orderNum" :min="0" style="width: 100%" controls-position="right" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="模块类型">
                <el-input v-model="editForm.moduleType" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="公司名称">
                <el-input v-model="editForm.companyName" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="客户名称">
                <el-input v-model="editForm.clientName" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="MAC地址">
                <el-input v-model="editForm.macAddress" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="工具名称">
                <el-input v-model="editForm.toolName" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="开始时间">
                <el-date-picker
                  v-model="editForm.testStartTime"
                  type="datetime"
                  style="width: 100%"
                  format="YYYY-MM-DD HH:mm:ss"
                  value-format="YYYY-MM-DD HH:mm:ss"
                />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="结束时间">
                <el-date-picker
                  v-model="editForm.testEndTime"
                  type="datetime"
                  style="width: 100%"
                  format="YYYY-MM-DD HH:mm:ss"
                  value-format="YYYY-MM-DD HH:mm:ss"
                />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="协议版本">
                <el-input v-model="editForm.protocolVersion" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="软件版本">
                <el-input v-model="editForm.softVersion" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="语言">
                <el-select v-model="editForm.languageSelect" style="width: 100%">
                  <el-option label="中文" value="0" />
                  <el-option label="英文" value="1" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-form-item label="测试结果">
                <el-select v-model="editForm.result" style="width: 100%">
                  <el-option label="通过" value="1" />
                  <el-option label="失败" value="0" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <!-- <div class="mb-12px flex items-center justify-between">
          <div class="text-14px text-gray-500">测试项：{{ editTotal }} 条</div>
          <el-button type="primary" @click="addEditRow">新增一行</el-button>
        </div> -->

        <div class="detail-dialog__table">
          <el-table :data="editTableData" class="table-style" border row-key="no">
            <el-table-column prop="no" label="编号" width="80" align="center" />
            <el-table-column prop="testItem" label="测试项" min-width="140">
              <template #default="{ row }">
                <el-input v-model="row.testItem" />
              </template>
            </el-table-column>
            <el-table-column prop="testStep" label="测试步骤" min-width="140">
              <template #default="{ row }">
                <el-input v-model="row.testStep" />
              </template>
            </el-table-column>
            <el-table-column prop="testProcess" label="测试过程" min-width="220">
              <template #default="{ row }">
                <el-input v-model="row.testProcess" type="textarea" :autosize="{ minRows: 1, maxRows: 3 }" />
              </template>
            </el-table-column>
            <el-table-column prop="testRequest" label="测试要求" min-width="200">
              <template #default="{ row }">
                <el-input v-model="row.testRequest" type="textarea" :autosize="{ minRows: 1, maxRows: 3 }" />
              </template>
            </el-table-column>
            <el-table-column prop="testResult" label="结果" width="140" align="center">
              <template #default="{ row }">
                <el-select v-model="row.testResult" style="width: 110px">
                  <el-option label="通过" :value="1" />
                  <el-option label="失败" :value="0" />
                </el-select>
              </template>
            </el-table-column>
            <!-- <el-table-column label="操作" width="100" align="center" fixed="right">
              <template #default="{ $index }">
                <el-button link type="danger" @click="removeEditRow(editPageStart + $index)">删除</el-button>
              </template>
            </el-table-column> -->
          </el-table>
        </div>

        <div v-if="editTotal > 0" class="detail-dialog__pagination">
          <Pagination
            class="detail-dialog__pagination-inner"
            :total="editTotal"
            :page-size-arr="detailPageSizeArr"
            layout="total, sizes, prev, pager, next, jumper"
            v-model:page="editQueryParams.pageNo"
            v-model:limit="editQueryParams.pageSize"
            @pagination="handleEditPagination"
          />
        </div>

        <template #footer>
          <el-button @click="dailogStatus = false">取消</el-button>
          <el-button type="primary" :loading="editSubmitting" @click="submitEdit">保存</el-button>
        </template>
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
  import router from '@/router'
  import { GettextPduApi } from '@/api/pdu/pdutext'
  import { ElMessage, ElMessageBox } from 'element-plus'
  const load = ref(true)
  const total = ref(0)
  const pageSizeArr = [15,30,50,100]
  const table = ref([])
  type PduTestDetail = {
    no?: string | number
    testItem?: string
    testProcess?: string
    testRequest?: string
    testResult?: number
    testStep?: string
  }
  type PduTestRecord = Record<string, any> & {
    testData?: PduTestDetail[] | string
  }
  const dailogStatus = ref(false)
  const detaildialogStatu = ref(false)
  const arr = ref<PduTestDetail[]>([])
  const editForm = ref<PduTestRecord>({})
  const editTestData = ref<PduTestDetail[]>([])
  const editTestDataAsString = ref(false)
  const editSubmitting = ref(false)
  const editQueryParams = reactive({
    pageNo: 1,
    pageSize: 10
  })
  const editTotal = computed(() => editTestData.value.length)
  const editPageStart = computed(() => (editQueryParams.pageNo - 1) * editQueryParams.pageSize)
  const editTableData = computed(() => {
    const start = editPageStart.value
    return editTestData.value.slice(start, start + editQueryParams.pageSize)
  })
  const detailPageSizeArr = [10, 20, 50, 100]
  const detailQueryParams = reactive({
    pageNo: 1,
    pageSize: 10
  })
  const detailTotal = computed(() => arr.value.length)
  const detailTableData = computed(() => {
    const start = (detailQueryParams.pageNo - 1) * detailQueryParams.pageSize
    return arr.value.slice(start, start + detailQueryParams.pageSize)
  })
  const queryParams = reactive({
  pageNo: 1,
  pageSize: 15,
  timeRange: undefined,
  clientName:undefined,
  orderId: undefined,
  productSn: undefined,
  language: '0',
  moduleSn: undefined,
  result: 'all',
  moduleType:undefined
  
})

const openUrlOpen = ref(false)
const antherArr = ref<any | null>(null)
const getRowIndex = (index: number) =>
  (queryParams.pageNo - 1) * queryParams.pageSize + index + 1

const optionalColumns = [
  { key: 'companyName', label: '公司名称' },
  { key: 'languageSelect', label: '语言' },
  { key: 'orderNum', label: '订单数量' },
  { key: 'toolName', label: '工具名称' },
  { key: 'protocolVersion', label: '协议版本' },
  { key : 'macAddress',label:'mac地址'},
  {key:'testStartTime' ,label:'测试开始时间'},
  {key: 'testEndTime' , label:'测试结束时间'}
] as const

const selectedOptionalColumns = ref<(typeof optionalColumns)[number]['key'][]>([])

const isColumnEnabled = (key: (typeof optionalColumns)[number]['key']) =>
  selectedOptionalColumns.value.includes(key)

 const getList = async()=>{
   const res = await GettextPduApi.moduleTest(queryParams)

  if(res.list){
    table.value = res.list
    total.value = res.total ?? 0
    console.log("table",table.value)
  }
}

const debounce = <T extends (...args: any[]) => void>(fn: T, wait = 300) => {
  let timer: ReturnType<typeof setTimeout> | undefined
  return (...args: Parameters<T>) => {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => fn(...args), wait)
  }
}

const debouncedGetList = debounce(() => {
  queryParams.pageNo = 1
  getList()
}, 400)

watch(
  () => [queryParams.result, queryParams.language, queryParams.orderId, queryParams.productSn, queryParams.timeRange,queryParams.clientName,queryParams.moduleType],
  () => debouncedGetList()
)

const handleOpenReport = async () => {



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

  const openExternalReport = (brand: 'clever' | 'legrand') => {
    const url = router.resolve({
      path: 'pdu/pduText',
      query: {
        productSN: String(productSN),
        orderId: String(orderId),
        moduleSN: String(moduleSN),
        brand,
      }
    }).href
    window.open(url, '_blank', 'noopener,noreferrer')
  }

  openUrlOpen.value = false
  try {
    await ElMessageBox.confirm('请选择外部出厂报告抬头：', '外部出厂报告', {
      confirmButtonText: '克莱沃',
      cancelButtonText: '罗格朗',
      distinguishCancelAndClose: true,
      showCancelButton: true,
      type: 'info',
    })
    openExternalReport('clever')
  } catch (action) {
    if (action === 'cancel') openExternalReport('legrand')
  }
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
    path: '/pdu/report',
    query: { moduleSN: String(moduleSN) }
  })
}

const normalizeTestData = (testData: unknown): PduTestDetail[] => {
  const normalizeList = (list: any[]): PduTestDetail[] =>
    list.map((item) => {
      const testResultNumber = Number(item?.testResult)
      return {
        ...item,
        testResult: Number.isFinite(testResultNumber) ? testResultNumber : item?.testResult
      }
    })

  if (Array.isArray(testData)) return normalizeList(testData)
  if (typeof testData === 'string') {
    try {
      const parsed = JSON.parse(testData) as unknown
      return Array.isArray(parsed) ? normalizeList(parsed) : []
    } catch {
      return []
    }
  }
  return []
}

const deepClone = <T,>(v: T): T => JSON.parse(JSON.stringify(v ?? {}))

const resetDetailDialog = () => {
  arr.value = []
  detailQueryParams.pageNo = 1
}





const resetEditDialog = () => {
  editForm.value = {}
  editTestData.value = []
  editTestDataAsString.value = false
  editQueryParams.pageNo = 1
}

const handleDetailPagination = () => {
  const maxPageNo = Math.max(1, Math.ceil(detailTotal.value / detailQueryParams.pageSize))
  if (detailQueryParams.pageNo > maxPageNo) detailQueryParams.pageNo = maxPageNo
}

const handleEditPagination = () => {
  const maxPageNo = Math.max(1, Math.ceil(editTotal.value / editQueryParams.pageSize))
  if (editQueryParams.pageNo > maxPageNo) editQueryParams.pageNo = maxPageNo
}

watch(arr, () => {
  detailQueryParams.pageNo = 1
})

watch(editTestData, () => {
  editQueryParams.pageNo = 1
})

const handleDetails = (v)=>{
  arr.value = normalizeTestData(v?.testData)
  detaildialogStatu.value = true
  console.log(arr.value)
}

const handleEdit =(v)=>{
  dailogStatus.value = true
  const cloned = deepClone(v ?? {})
  editTestDataAsString.value = typeof cloned.testData === 'string'
  editTestData.value = normalizeTestData(cloned.testData)
  delete cloned.testData
  editForm.value = cloned
}

const addEditRow = () => {
  const maxNo = editTestData.value.reduce((max, item) => {
    const n = Number(item?.no)
    return Number.isFinite(n) ? Math.max(max, n) : max
  }, 0)
  editTestData.value.push({
    no: String(maxNo + 1),
    testResult: 1
  })
  handleEditPagination()
}

const removeEditRow = (index: number) => {
  editTestData.value.splice(index, 1)
  handleEditPagination()
}

const handledelete = async(v)=>{
  console.log('id',v.id)
  const res = await GettextPduApi.deleteModuleTest(v.id)

  if(res){
    await getList()
  }
}

const submitEdit = async () => {
  editSubmitting.value = true
  try {
    const payload: PduTestRecord = {
      ...editForm.value,
      testData: editTestDataAsString.value ? JSON.stringify(editTestData.value) : editTestData.value
    }
    await GettextPduApi.updateModuleTest(payload)
    ElMessage.success('保存成功')
    dailogStatus.value = false
    await getList()
  } finally {
    editSubmitting.value = false
  }
}

  onMounted(()=>{
    load.value = false
    getList()
  })
</script>

<style scoped lang="scss">

  .table-style{
    :deep(.el-table__cell){
      padding: 6px 8px;
    }

    :deep(.el-table__header-wrapper th.el-table__cell){
      background: #f6f6f6;
      color: #111827;
      font-weight: 600;
    }
  }

  .detail-dialog__table {
    max-width: 100%;
    overflow-x: auto;
  }

  .detail-dialog__pagination {
    display: flex;
    justify-content: flex-end;
    flex-wrap: wrap;
  }

  :deep(.detail-dialog__pagination-inner) {
    float: none !important;
  }
</style>
