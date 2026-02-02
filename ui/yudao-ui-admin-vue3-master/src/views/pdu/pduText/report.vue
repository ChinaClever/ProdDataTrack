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
    <div ref="config" class="report-root">
      <div class="report-sheet" :style="reportStyleVars">
      <div v-if="qualityTestInternalReport != null" ref="qualitySection" class="report-section">
         <div class=" flex items-center justify-center">
        <!-- <h1>{{  qualityTestInternalReport?.productType}}功能检验报告</h1> -->
          <h1>PDU自动测试报告</h1>
        </div>
        <div class=" flex items-center justify-center">
          <h2>模块序列号：{{moduleTestInternalReport?.moduleSn}}</h2>
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
      <div v-if="moduleTestInternalReport != null" ref="moduleSection" class="report-section">
        <div class=" flex items-center justify-center">
        <!-- <h1>{{  moduleTestInternalReport?.moduleType }}模块调试报告</h1> -->
        <h1>参数配置与校准记录</h1>
      </div>
      <el-divider />
      <!-- 信息区：按截图两行左右布局 -->
      <!-- <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.orderNo }} {{  moduleTestInternalReport?.orderId }}</div>
          <div class="report-meta__item">{{ uiText.orderQty }} {{  moduleTestInternalReport?.orderNum }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right">
          <div class="report-meta__item">{{ uiText.productModel }} {{   moduleTestInternalReport?.moduleType  }}</div>
          <div class="report-meta__item">{{ uiText.inspectionDate }} {{  moduleTestInternalReport?.clientName }}</div>
        </div>
      </div> -->
      <!-- <el-divider /> -->
      <div class="report-meta">
        <div class="report-meta__col">
          <div class="report-meta__item">{{ uiText.moduelCode }} {{  moduleTestInternalReport?.moduleSn }}</div>
          <div class="report-meta__item">{{ uiText.inspectionStartTime }} {{  moduleTestInternalReport?.testStartTime }}</div>
          <div class="report-meta__item">{{ uiText.inspectionEndTime }} {{ moduleTestInternalReport?.testEndTime }}</div>
        </div>
        <div class="report-meta__col report-meta__col--right2">
          <div class="report-meta__item">{{ uiText.testItem }} 模块校准</div>
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
   

    
    
   
  </div> 
</template>


<script setup lang="ts">
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas'
import { GettextPduApi } from '@/api/pdu/pdutext'


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
  result: string
  testData: unknown[]
  testEndTime: string
  testStartTime: string
}
const resizeObserve = ref<ResizeObserver| null>(null)
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
const continerWidth = ref(0)

const REPORT_DESIGN_WIDTH_PX = 1600
const REPORT_PAD_INLINE = 'clamp(16px, 8vw, 200px)'
const REPORT_META_FONT_SIZE = '22px'
const REPORT_TABLE_FONT_SIZE = '16px'
const REPORT_TABLE_HEADER_FONT_SIZE = '16px'

const previewScale = computed(() => {
  if (continerWidth.value <= 0) return 1
  return Math.min(1, continerWidth.value / REPORT_DESIGN_WIDTH_PX)
})

const isNarrow = computed(() => continerWidth.value > 0 && continerWidth.value < REPORT_DESIGN_WIDTH_PX)
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


const reportStyleVars = computed(
  () =>
    ({
      width: `${REPORT_DESIGN_WIDTH_PX}px`,
      zoom: String(previewScale.value),
      '--report-pad-inline': REPORT_PAD_INLINE,
      '--report-meta-font-size': REPORT_META_FONT_SIZE,
      '--report-table-font-size': REPORT_TABLE_FONT_SIZE,
      '--report-table-header-font-size': REPORT_TABLE_HEADER_FONT_SIZE
    }) as any
)

const raf = () => new Promise<void>((resolve) => requestAnimationFrame(() => resolve()))

async function withExportWrapper<T>(
  sectionEl: HTMLElement,
  run: (target: HTMLElement) => Promise<T>
): Promise<T> {
  const wrapper = document.createElement('div')
  wrapper.style.position = 'fixed'
  wrapper.style.left = '-10000px'
  wrapper.style.top = '0'
  wrapper.style.width = `${REPORT_DESIGN_WIDTH_PX}px`
  wrapper.style.background = '#fff'
  wrapper.style.pointerEvents = 'none'
  wrapper.style.opacity = '1'

  wrapper.style.setProperty('--report-pad-inline', REPORT_PAD_INLINE)
  wrapper.style.setProperty('--report-meta-font-size', REPORT_META_FONT_SIZE)
  wrapper.style.setProperty('--report-table-font-size', REPORT_TABLE_FONT_SIZE)
  wrapper.style.setProperty('--report-table-header-font-size', REPORT_TABLE_HEADER_FONT_SIZE)

  const clone = sectionEl.cloneNode(true) as HTMLElement
  clone.style.width = '100%'
  clone.style.boxSizing = 'border-box'
  wrapper.appendChild(clone)

  document.body.appendChild(wrapper)
  try {
    await raf()
    await raf()
    return await run(wrapper)
  } finally {
    wrapper.remove()
  }
}





const exportSection = async (doc: jsPDF, el: HTMLElement, startOnNewPage: boolean) => {
  if (startOnNewPage) doc.addPage()

  const canvas = await withExportWrapper(el, (target) =>
    html2canvas(target, {
      scale: 2,
      useCORS: true,
      backgroundColor: '#fff',
      scrollX: 0,
      scrollY: 0
    })
  )

  const contentWidth = canvas.width
  const contentHeight = canvas.height

  const pageW = 595.28
  const heightValue = isZh.value ? 825.0 : 840.0
  const pageHeightPx = (contentWidth / 592.28) * heightValue
  let position = 0

  while (position < contentHeight) {
    let remainingHeight = pageHeightPx
    if (position + remainingHeight > contentHeight) {
      remainingHeight = contentHeight - position
    }

    const tempCanvas = document.createElement('canvas')
    tempCanvas.width = contentWidth
    tempCanvas.height = remainingHeight
    const ctx = tempCanvas.getContext('2d')!
    ctx.drawImage(canvas, 0, position, contentWidth, remainingHeight, 0, 0, contentWidth, remainingHeight)

    const imageData = tempCanvas.toDataURL('image/jpeg', 1.0)
    doc.addImage(imageData, 'JPEG', 0, 0, pageW, (592.28 / contentWidth) * remainingHeight)

    position += remainingHeight
    if (position < contentHeight) doc.addPage()
  }
}

const output = async () => {
  await nextTick()
  const doc = new jsPDF('p', 'pt', 'a4')

  if (qualitySection.value) await exportSection(doc, qualitySection.value, false)
  if (moduleSection.value) await exportSection(doc, moduleSection.value, true) // 第二块强制新页开始

  doc.save('PDU自动测试报告.pdf')
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

watch(
  config,
  (el,_prew,onCleanup)=>{
    if(!el) return 

    const ro  =  new ResizeObserver((entries)=>{
      const entry = entries[0]
      if(!entry) return 
      continerWidth.value = Math.floor(entry.contentRect.width)
    })

    ro.observe(el)
    continerWidth.value = Math.floor(el.getBoundingClientRect().width)
    resizeObserve.value = ro

    onCleanup(()=>{
      ro.disconnect()
      if(resizeObserve.value === ro) resizeObserve.value = null
    })
  },
{immediate:true})


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

.small-br {
  display: block;
  margin: 0;
  line-height: 0.5; /* 调整行高来控制间距 */
}

.report-meta__col--right,
.report-meta__col--right2 {
  align-items: flex-start;                 // 右列内容从“右列起始线”对齐（你要的y轴一致
  position: relative  ;
  left: 350px;
}

.report-meta__item {
  white-space: nowrap;
  text-align: left;
}
.table-class{
  padding: 0 20px 20px 20px;
  :deep(.el-table__header){
    th {
      // background-color:#f5f7fe ;
      color: #848485;
      font-weight: 600;
      font-size: 16px;
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
        // text-align: center;
        // font-size: 16px;
        // color: #848485;
        // padding: 12px 0;
        // border-bottom: 1px solid #ebeef5;
    //  font-family: 'SimSun', serif;
      // background-color:#f5f7fe ;
      color: #777779;
      font-weight: 600;
      font-size: 16px;
      text-align: center;
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

.report-meta {
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 24px;
  padding: 18px var(--report-pad-inline);
  font-size: var(--report-meta-font-size);
  font-weight: 700;
}

.report-meta__col {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 右列不再用 left 偏移 */
.report-meta__col--right,
.report-meta__col--right2 {
  align-items: flex-start;
}

.report-meta__item {
  white-space: nowrap;
  text-align: left;
}

/* 窄屏：一列 + 自动换行 */
.report-root-narrow {
  width: 100%;
  height: 100%;
  .report-meta {
    grid-template-columns: 1fr;
    row-gap: 10px;
    padding: 12px var(--report-pad-inline);
  }

  .report-meta__item {
    white-space: normal;
    word-break: break-word;
  }
}

/* 表格字体也跟着缩 */
.table-class {
  :deep(.el-table__header th) {
    font-size: var(--report-table-header-font-size);
  }
  :deep(.el-table__body td) {
    font-size: var(--report-table-font-size);
  }
}

.report-root{
  width: 100%;
  height: 100%;
}
</style>
