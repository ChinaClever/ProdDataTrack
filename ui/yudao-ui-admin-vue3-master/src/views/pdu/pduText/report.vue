<template>
  <div class=" w-full h-full overflow-auto ">
    <div class=" bg-white flex justify-between p-2">
      <div>
          <el-switch active-text="中文"  inactive-text="英文"  inactive-value="1" active-value="0"  class=" ml-3"   v-model="outLangues" />
          <el-switch  active-text="克莱沃"  inactive-text="罗格朗"  active-value="1" inactive-value="0"  class="ml-3" v-model="company"        />
      </div>
      <el-button type="primary" @click="output">{{ uiText.export }}</el-button>
    
    </div>
    <div ref="config" class=" w-full h-full mt-2 bg-white font-900 ">
      <div class=" flex items-center justify-center">
        <h1>{{ reportTitle }}</h1>
      </div>
      <el-divider />
      <!-- 信息区：按截图两行左右布局 -->
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.orderNo }} {{ orderNo || '-' }}</div>
          <div class="report-meta__item">{{ uiText.orderQty }} {{ orderQtyText }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right">
          <div class="report-meta__item">{{ uiText.productModel }} {{ productModel || '-' }}</div>
          <div class="report-meta__item">{{ uiText.inspectionDate }} {{ inspectionDateText }}</div>
        </div>
      </div>
      <el-divider />
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.productCode }} {{ productCode || '-' }}</div>
          <div class="report-meta__item">{{ uiText.inspectionStartTime }} {{ inspectionStartTimeText }}</div>
          <div class="report-meta__item">{{ uiText.inspectionEndTime }} {{ inspectionEndTimeText }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right2">
          <div class="report-meta__item">{{ uiText.testItem }} {{ uiText.testItemValue }}</div>
          <div class="report-meta__item">{{ uiText.inspectionType }} {{ uiText.inspectionTypeValue }}</div>
          <div class="report-meta__item">{{ uiText.inspectionResult }} {{ inspectionResultText }}</div>
        </div>
        
      </div>
      <el-divider />
      <el-table :data="table" class="table-class">
      <el-table-column label="序列号" />
      <el-table-column label="检验步骤" />
      <el-table-column label="检验项" />
      <el-table-column label="检验要求" />
      <el-table-column label="判定" />

    </el-table>
    </div>
    
  </div> 
</template>


<script setup lang="ts">
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas'
import { GettextPduApi } from '@/api/pdu/pdutext'
import dayjs from 'dayjs'


const table = ref([])
const outLangues = ref('0')
const config = ref<HTMLElement | null>(null)
const company = ref('1')
const route = useRoute()
const arr =ref([])
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



const output = async ()=>{
  await nextTick()
  const el = config.value
  if (!el) return

  const canvas = await html2canvas(el, {
    scale: 2,
    useCORS: true,
    backgroundColor: '#fff',
    width: el.scrollWidth,
    height: el.scrollHeight,
    scrollX: 0,
    scrollY: -window.scrollY
  })

  const imgData = canvas.toDataURL('image/jpeg', 1.0)
  const doc = new jsPDF('p', 'pt', 'a4')
  const pageW = doc.internal.pageSize.getWidth()
  const pageH = doc.internal.pageSize.getHeight()

  const imgW = pageW
  const imgH = (canvas.height * pageW) / canvas.width

  let offsetY = 0
  doc.addImage(imgData, 'JPEG', 0, 0, imgW, imgH)

  while (imgH - offsetY > pageH) {
    offsetY += pageH
    doc.addPage()
    doc.addImage(imgData, 'JPEG', 0, -offsetY, imgW, imgH)
  }

  doc.save('report.pdf')
}

const handleSn = async () => {
  if (!moduleSN.value) return
  try {
    const res = await GettextPduApi.Report({ moduleSN: String(moduleSN.value) })
    reportList.value = normalizeToArray(res)
  } catch (e) {
    console.error(e)
  }
}

watch(moduleSN, () => handleSn(), { immediate: true })

</script>

<style scoped lang="scss">
.report-meta {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  padding: 18px 200px;
  font-size: 22px;
  font-weight: 700;
}

.report-meta__col {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.report-meta__col--right {
  text-align: right;
}

.report-meta__col--right2 {
  text-align: right;
  margin-right: 27px;
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
