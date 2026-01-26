<template>
  <div class=" w-full h-full overflow-auto ">
    <div class=" bg-white flex justify-between p-2">
      <div>
          <el-switch active-text="中文"  inactive-text="英文"  inactive-value="1" active-value="0"  class=" ml-3"   v-model="outLangues" />
          <el-switch  active-text="克莱沃"  inactive-text="罗格朗"  active-value="1" inactive-value="0"  class="ml-3" v-model="company"        />
          <!-- <el-switch  active-text="质量报告"  inactive-text="成品报告"  active-value="1" inactive-value="0"  class="ml-3" v-model="pduReport"        /> -->
      </div>
      <el-button type="primary" @click="output">{{ uiText.export }}</el-button>
    
    </div>
    <div ref="config" class=" w-full h-full mt-2 bg-white font-900 ">
      <div ref="qualitySection" class="report-section">
         <div class=" flex items-center justify-center">
        <h1>{{  qualityTestInternalReport?.productType}}功能检验报告</h1>
      </div>
      <el-divider />
      <!-- 信息区：按截图两行左右布局 -->
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.orderNo }} {{ qualityTestInternalReport?.orderId  }}</div>
          <div class="report-meta__item">{{ uiText.orderQty }} {{qualityTestInternalReport?.orderNum }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right">
          <div class="report-meta__item">{{ uiText.productModel }} {{   qualityTestInternalReport?.productType  }}</div>
          <div class="report-meta__item">{{ uiText.inspectionDate }} {{ qualityTestInternalReport?.clientName}}</div>
        </div>
      </div>
      <el-divider />
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.productCode }} {{ qualityTestInternalReport?.productSn  }}</div>
          <div class="report-meta__item">{{ uiText.inspectionStartTime }} {{ qualityTestInternalReport?.testStartTime  }}</div>
          <div class="report-meta__item">{{ uiText.inspectionEndTime }} {{ qualityTestInternalReport?.testEndTime  }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right2">
          <div class="report-meta__item">{{ uiText.testItem }} 成品质检</div>
          <div class="report-meta__item">{{ uiText.inspectionType }} 全检</div>
          <div class="report-meta__item">{{ uiText.inspectionResult }} {{  qualityTestInternalReport?.result === '1'? '通过' : '失败' }}</div>
        </div>
        
      </div>
      <el-divider />
      <el-table :data=" qualityTestInternalReport?.testData" class="table-class">
      <el-table-column label="序列号" prop="no" min-width="50" />
      <el-table-column label="检验步骤" prop="testStep" min-width="50"/>
      <el-table-column label="检验项" prop="testItem" />
      <el-table-column label="检验要求" prop="testRequest" min-width="180" />
      <el-table-column label="检测过程" prop="testProcess" min-width="180"/>
      <el-table-column label="测试结果"  prop="testResult" >
        <template #default="{row}">
          <div v-if="row.testResult === 1">通过</div>
          <div v-else>失败</div>
        </template>
        </el-table-column>

    </el-table>
      </div>
      <div ref="moduleSection" class="report-section">
        <div class=" flex items-center justify-center">
        <h1>{{  moduleTestInternalReport?.moduleType }}模块调试报告</h1>
      </div>
      <el-divider />
      <!-- 信息区：按截图两行左右布局 -->
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.orderNo }} {{  moduleTestInternalReport?.orderId }}</div>
          <div class="report-meta__item">{{ uiText.orderQty }} {{  moduleTestInternalReport?.orderNum }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right">
          <div class="report-meta__item">{{ uiText.productModel }} {{   moduleTestInternalReport?.moduleType  }}</div>
          <div class="report-meta__item">{{ uiText.inspectionDate }} {{  moduleTestInternalReport?.clientName }}</div>
        </div>
      </div>
      <el-divider />
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.moduelCode }} {{  moduleTestInternalReport?.moduleSn }}</div>
          <div class="report-meta__item">{{ uiText.inspectionStartTime }} {{  moduleTestInternalReport?.testStartTime }}</div>
          <div class="report-meta__item">{{ uiText.inspectionEndTime }} {{ moduleTestInternalReport?.testEndTime }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right2">
          <div class="report-meta__item">{{ uiText.testItem }} 模块质检</div>
          <div class="report-meta__item">{{ uiText.inspectionType }} 全检</div>
          <div class="report-meta__item">{{ uiText.inspectionResult }} {{ moduleTestInternalReport?.result === '1'? '通过' : '失败'}}</div>
        </div>
        
      </div>
      <el-divider />
      <el-table :data=" moduleTestInternalReport?.testData" class="table-class">
      <el-table-column label="序列号" prop="no" min-width="50" />
      <el-table-column label="检验步骤" prop="testStep" min-width="50"/>
      <el-table-column label="检验项" prop="testItem" />
      <el-table-column label="检验要求" prop="testRequest" min-width="180" />
      <el-table-column label="检测过程" prop="testProcess" min-width="180"/>
      <el-table-column label="测试结果"  prop="testResult" >
        <template #default="{row}">
          <div v-if="row.testResult === 1">通过</div>
          <div v-else>失败</div>
        </template>
        </el-table-column>

    </el-table>
      </div>
     
    
    </div>
   

    
    
   
  </div> 
</template>


<script setup lang="ts">
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas'
import { GettextPduApi } from '@/api/pdu/pdutext'
import dayjs from 'dayjs'

type ModuleTestInternalReportType = {
  clientName: string
  moduleSn: string
  moduleType: string
  orderId: string
  orderNum: string
  productSn: string
  result: string
  testData: unknown[]
  testEndTime: string
  testStartTime: string
}
type qualityTestInternalReportType = {
  clientName: string
  productSn: string
  moduleSn:string
  productType: string
  orderId: string
  orderNum: string
  productSn: string
  result: string
  testData: unknown[]
  testEndTime: string
  testStartTime: string
}
const resizeObserve = ref()
const moduleTestInternalReport = ref<ModuleTestInternalReportType | null>(null)
const qualityTestInternalReport = ref<qualityTestInternalReportType | null>(null)
const pduReport = ref('1')
const table = ref([])
const outLangues = ref('0')
const config = ref<HTMLElement | null>(null)
const company = ref('1')
const route = useRoute()
const arr =ref([])
const qualitySection = ref<HTMLElement | null>(null)
const moduleSection = ref<HTMLElement | null>(null)
const toStr = (v: unknown): string => {
  if (v == null) return ''
  if (Array.isArray(v)) return String(v[0] ?? '').trim()
  return String(v).trim()
}

const isZh = computed(() => outLangues.value === '0')

const uiText = computed(() => {
  if (isZh.value) {
    return {
      export: '导出',
      orderNo: '订单号：',
      productModel: '产品型号：',
      orderQty: '订单数量：',
      inspectionDate: '客户名称：',
      productCode: '成品代码：',
      moduelCode:'模块序列号：',
      inspectionStartTime: '检验开始时间：',
      inspectionEndTime: '检验结束时间：',
      testItem: '测试项目：',
      inspectionType: '检验类型：',
      inspectionResult: '检验结果：',
      testItemValue: '成品功能',
      inspectionTypeValue: '全检',
      qtyUnit: '台',
      pass: '通过',
      fail: '失败'
    }
  }
    return {
      export: 'Export',
      orderNo: 'Order No.:',
      productModel: 'Product Model:',
      orderQty: 'Order Qty:',
      moduelCode:'Moduel Code：',
      inspectionDate: 'Customer Name:',
      productCode: 'Product Code:',
      inspectionStartTime: 'Inspection Start Time:',
      inspectionEndTime: 'Inspection End Time:',
      testItem: 'Test Item:',
      inspectionType: 'Inspection Type:',
    inspectionResult: 'Inspection Result:',
    testItemValue: 'Finished Product Function',
    inspectionTypeValue: 'Full Inspection',
    qtyUnit: 'pcs',
    pass: 'PASS',
    fail: 'FAIL'
  }
})


const title = computed(()=>{
    const v1 = route.query.productType
    const v2 = route.query.moduleTpye

    if(v1){
      return v1
    } else {
      return v2
    }
  })

const reportTitle = computed(() => {
  const t = toStr(title.value) || 'PDU'
  return isZh.value ? `${t}功能检验报告` : `${t} Functional Inspection Report`
})

const moduleSN = computed(()=>{
  return toStr(route.query.moduleSN ?? route.query.moduleSn)
})

const reportList = ref<any[]>([])
const normalizeToArray = (v: unknown): any[] => (Array.isArray(v) ? v : [])
const firstRow = computed(() => reportList.value[0] ?? {})
const lastRow = computed(() => reportList.value[reportList.value.length - 1] ?? {})

const orderNo = computed(() => toStr(route.query.orderId ?? route.query.orderNo ?? firstRow.value?.orderId))
const productModel = computed(() =>
  toStr(route.query.productModel ?? route.query.productType ?? firstRow.value?.devName ?? title.value)
)
const orderQty = computed(() =>
  toStr(route.query.orderQty ?? route.query.orderNum ?? firstRow.value?.orderNum ?? firstRow.value?.productionNum)
)
const productCode = computed(() =>
  toStr(route.query.productSN ?? route.query.productSn ?? firstRow.value?.productSn ?? firstRow.value?.productSN)
)

const formatDate = (v: unknown) => {
  const s = toStr(v)
  if (!s) return ''
  const d = dayjs(s)
  return d.isValid() ? d.format('YYYY-MM-DD') : s
}

const formatDateTime = (v: unknown) => {
  const s = toStr(v)
  if (!s) return ''
  const d = dayjs(s)
  return d.isValid() ? d.format('YYYY-MM-DD HH:mm:ss') : s
}

const inspectionDateText = computed(() => {
  const customerName = toStr(
    route.query.clientName ?? route.query.customerName ?? firstRow.value?.clientName ?? firstRow.value?.customerName
  )
  if (customerName) return customerName

  const v = lastRow.value?.endTime ?? firstRow.value?.endTime ?? route.query.inspectionDate
  return formatDate(v) || dayjs().format('YYYY-MM-DD')
})

const inspectionStartTimeText = computed(() => {
  const v = firstRow.value?.startTime ?? route.query.startTime
  return formatDateTime(v) || '-'
})

const inspectionEndTimeText = computed(() => {
  const v = lastRow.value?.endTime ?? route.query.endTime
  return formatDateTime(v) || '-'
})

const orderQtyText = computed(() => {
  if (!orderQty.value) return '-'
  return `${orderQty.value} ${uiText.value.qtyUnit}`
})

const inspectionResultText = computed(() => {
  if (!reportList.value.length) return '-'
  const pass = reportList.value.every((item) => Number(item?.testResult ?? item?.result ?? 1) !== 0)
  return pass ? uiText.value.pass : uiText.value.fail
})



const exportSection = async (doc: jsPDF, el: HTMLElement, startOnNewPage: boolean) => {
  if (startOnNewPage) doc.addPage()

  const canvas = await html2canvas(el, {
    scale: 2,
    useCORS: true,
    backgroundColor: '#fff'
  })

  const pageW = doc.internal.pageSize.getWidth()
  const pageH = doc.internal.pageSize.getHeight()

  const pageHeightPx = Math.floor((pageH * canvas.width) / pageW) // A4高度对应的像素高度
  let y = 0

  while (y < canvas.height) {
    const sliceHeightPx = Math.min(pageHeightPx, canvas.height - y)

    const sliceCanvas = document.createElement('canvas')
    sliceCanvas.width = canvas.width
    sliceCanvas.height = sliceHeightPx

    const ctx = sliceCanvas.getContext('2d')!
    ctx.drawImage(canvas, 0, y, canvas.width, sliceHeightPx, 0, 0, canvas.width, sliceHeightPx)

    const imgData = sliceCanvas.toDataURL('image/jpeg', 1.0)
    const sliceHeightPt = (sliceHeightPx * pageW) / canvas.width

    doc.addImage(imgData, 'JPEG', 0, 0, pageW, sliceHeightPt)

    y += sliceHeightPx
    if (y < canvas.height) doc.addPage()
  }
}

const output = async () => {
  await nextTick()
  const doc = new jsPDF('p', 'pt', 'a4')

  if (qualitySection.value) await exportSection(doc, qualitySection.value, false)
  if (moduleSection.value) await exportSection(doc, moduleSection.value, true) // 第二块强制新页开始

  doc.save('report.pdf')
}

const handleSn = async () => {
  if (!moduleSN.value) return
  try {
    const res = await GettextPduApi.Report({ moduleSN: String(moduleSN.value) })
    reportList.value = normalizeToArray(res)
    moduleTestInternalReport.value = res.moduleTestInternalReport
    qualityTestInternalReport.value = res.qualityTestInternalReport
  } catch (e) {
    console.error(e)
  }
}

const handleReport = async()=>{

}

watch(moduleSN, () => handleSn(), { immediate: true })
watch(()=>pduReport.value , ()=>handleReport,{ immediate:true})
</script>

<style scoped lang="scss">
.report-meta {
  display: grid;
  grid-template-columns: 1fr 1fr;         // 两列永远各占一半，所以右列起始x一致
  column-gap: 24px;
  padding: 18px clamp(16px, 8vw, 200px);  // 宽度变时自动缩放，不会挤爆
  font-size: 22px;
  font-weight: 700;
}


.report-meta__col {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

// .report-meta__col--right {
//   text-align: right;
// }


.report-meta__col--right,
.report-meta__col--right2 {
  align-items: flex-start;                 // 右列内容从“右列起始线”对齐（你要的y轴一致
  position: relative  ;
  left: 150px;
}

.report-meta__item {
  white-space: nowrap;
  text-align: left;
}
.table-class{
  padding: 0 20px 20px 20px;
  :deep(.el-table__header){
    th {
      background-color:#f5f7fe ;
      color: #606266;
      font-weight: 600;
      font-size: 14px;
      text-align: center;
      border-bottom: 1px solid #ebeef5;
    }
  }

  :deep(.el-table__body){
    tr {
      &:hover{
        background-color: #f5f7fa;

      }
      td{
        text-align: center;
        font-size: 14px;
        color: #606266;
        padding: 12px 0;
        border-bottom: 1px solid #ebeef5;

      }
    }

  }

  :deep(.el-table--striped) {
    .el {
      align-items: flex-start;
    }

    .el-input {
      width: 100% !important;
    }
  }

  .exception-config {
    gap: 12px;
  }

}
</style>
