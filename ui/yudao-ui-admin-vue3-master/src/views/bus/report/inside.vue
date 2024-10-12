<template>
  <ContentWrap>
    <el-form
        class="-mb-15px"
        ref="queryFormRef"
        :inline="true"
        label-width="auto">
        <el-form-item>
          <el-switch
            v-model="language"
            active-text="中文"
            inactive-text="英文"
            @change="changeLanguage" />
        </el-form-item>

        <el-form-item>
          <el-input
            placeholder="扫码枪扫码输入 产品SN号" 
            v-model="inputdata" 
            clearable
            class="!w-500px"
            @keyup.enter="getReport"
            @clear="clearSearchHandle"/>
        </el-form-item>
      
        <el-form-item>
          <el-button @click="getReport"><Icon icon="ep:search" class="mr-1px" /> 生成对内报告</el-button>
        </el-form-item> 

        <el-form-item style="float: right;">
          <el-button type="primary" class="container-button" @click="handleExport" >导出 PDF</el-button>
        </el-form-item> 
    </el-form>
  </ContentWrap>

    <div class="reportDetails" id="PDFcontent" v-if="dialogVisible" >
<!--                    中文-->
        <div v-if="language == true" >
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">New Busbar {{goods_dev_name}}功能检验报告</p>
          <hr/>
          <br/>
          <div>
            <el-row class="table-row">
              <div class="left-row">
                <!-- <el-col  class="table-label">客户名称：</el-col> -->
                <el-col  class="table-label-1">订单号：{{goods_order_id}}</el-col>
                <el-col  class="table-label-2">订单数量：{{goods_order_num}} 台</el-col>
              </div>
              <div class="right-row"  style= "padding-left: 895px;">
                <el-col  class="table-label">产品型号：{{goods_dev_name}}</el-col>
                <el-col  class="table-label-1">检验日期：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
                <!-- <el-col  class="table-label-2">检验数量：{{goods_test_num}}</el-col> -->
              </div>
            </el-row>
          </div>
            <hr/>
            <br/>
          <!-- <div>
            <el-row class="table-row">
              <el-col  class="table-label">产品型号：{{goods_dev_name}}</el-col>
              <el-col  class="table-label-1">检验日期：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
              <el-col  class="table-label-2">检验数量：{{goods_test_num}}</el-col>
            </el-row>
          </div> -->
            <!-- <hr/>
            <br/> -->
          <div>
            <el-row class="table-row">
              <div class="left-row" >
                <el-col  class="table-label">SN编号：{{goods_product_sn}}</el-col>
                <el-col  class="table-label-1">检验开始时间：{{goods_start_time}}</el-col>
                <el-col  class="table-label-2">检验结束时间：{{goods_end_time}}</el-col>
              </div>
              <div class="right-row" style= "padding-left: 700px;">
                <el-col  class="table-label">测试项目：成品功能</el-col>
                <el-col  class="table-label-1">检验类型：全检</el-col>
                <el-col  class="table-label-2">检验结果：{{displayEndJudgment(end_judgment)}}</el-col>
              </div>
            </el-row>
          </div>
            <hr/>
            <br/>
          <!-- <div>
            <el-row class="table-row">
              <el-col  class="table-label">测试项目：成品功能</el-col>
              <el-col  class="table-label-1">检验类型：全检</el-col>
              <el-col  class="table-label-2">检验结果：{{displayEndJudgment(end_judgment)}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/> -->

        <br/>
          <div class="table-wrapper"  style="padding-left: 2%;">
            <el-table :data="goods_SN_data" class="table-data2" style="width: 97%" border>
                <el-table-column prop="testStep" label="检验步骤" :align="centerAlign" width="200"/>
                <el-table-column prop="testItem" label="检验项" :align="centerAlign" width="300"/>
                <el-table-column prop="testRequest" label="检验要求" :align="centerAlign" width="550">
                  <template #default="{ row }">
                      <div v-html="formatSkills(row.testRequest)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="testProcess" label="检验结果"  :align="centerAlign"  width="350">
                    <template #default="{ row }">
                      <div v-html="formatSkills(row.testProcess)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="testResult" label="判定" :align="centerAlign" width="105">
                  <template #default="{ row }" >
                    <span v-if="row.testResult == 1" >通过</span>
                    <span v-else-if="row.testResult == 0" >失败</span>
                  </template>
                </el-table-column>
            </el-table>
          </div>
        </div>
<!--                    英文-->
        <div v-else-if="language == false">
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">New Busbar {{goods_dev_name}} Functional Inspection Report</p>
          <hr/>
          <br/>
          <div>
            <el-row class="table-row">
              <div class="left-row">
                <!-- <el-col  class="table-label">Customer Name：</el-col> -->
                <el-col  class="table-label-1">Order Number：{{goods_order_id}}</el-col>
                <el-col  class="table-label-2">Order Quantity：{{goods_order_num}} UNIT</el-col>
              </div>
              <div class="right-row" style= "padding-left: 590px;">
                <el-col  class="table-label">Product Model：{{goods_dev_name}}</el-col>
                <el-col  class="table-label-1">Inspection Date：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
              </div>
            </el-row>
          </div>
            <hr/>
            <br/>
          <!-- <div>
            <el-row class="table-row">
              <el-col  class="table-label">Product Model：{{goods_dev_name}}</el-col>
              <el-col  class="table-label-1">Inspection Date：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
              <el-col  class="table-label-2">Inspection Quantity：{{goods_test_num}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/> -->
          <div>
            <el-row class="table-row">
              <div class="left-row">
                <el-col  class="table-label">SN Number：{{goods_product_sn}}</el-col>
                <el-col  class="table-label-1">Inspection Start Time：{{goods_start_time}}</el-col>
                <el-col  class="table-label-2">Inspection End Time：{{goods_end_time}}</el-col>
            </div>
            <div class="right-row" style= "padding-left: 400px;">
                <el-col  class="table-label">Test Items：Finished product functions</el-col>
                <el-col  class="table-label-1">Inspection Type：Full inspection</el-col>
                <el-col  class="table-label-2">Inspection results：{{displayEndJudgment(end_judgment)}}</el-col>
            </div>
            </el-row>
          </div>
            <hr/>
            <br/>
          <!-- <div>
            <el-row class="table-row">
              <el-col  class="table-label">Test Items：Finished product functions</el-col>
              <el-col  class="table-label-1">Inspection Type：Full inspection</el-col>
              <el-col  class="table-label-2">Inspection results：{{displayEndJudgment(end_judgment)}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/> -->

        <br/>
          <div class="table-wrapper" style="padding-left: 2%;">
            <el-table :data="goods_SN_data" class="table-data2"  style="width: 97%" border>
                <el-table-column prop="testStep" label="Inspection Steps"  :align="centerAlign" width="200"/>
                <el-table-column prop="testItem" label="Inspection Items" :align="centerAlign" width="250"/>
                <el-table-column prop="testRequest" label="Inspection Requirements" :align="centerAlign" width="605">
                    <template #default="{ row }">
                      <div v-html="formatSkills(row.testRequest)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="testProcess" label="Inspection Results"  :align="centerAlign"  width="300">
                  <template #default="{ row }">
                      <div v-html="formatSkills(row.testProcess)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="testResult" label="Determine" :align="centerAlign" width="150">
                  <template #default="{ row }" >
                    <span v-if="row.testResult == 1" >PASS</span>
                    <span v-else-if="row.testResult == 0" >FAIL</span>
                  </template>
                </el-table-column>
            </el-table>
          </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import { TestDataApi } from '@/api/bus/testData'
// import { ElMessageBox } from 'element-plus'
import html2canvas from "html2canvas";
import JsPDF from "jspdf";
import { ElMessage } from 'element-plus'

const inputdata = ref('')
const dialogVisible= ref(false)      //控制报告弹窗的显示与隐藏  对内
const goods_SN_data = ref<Array<{ }>>([]) as any//获取到的总的数据
const ChineseItems = ref<Array<{ }>>([]) as any//获取到的总的数据 中文部分
const EnglishItems = ref<Array<{ }>>([]) as any//获取到的总的数据 英文部分
const goods_product_sn = ref(false)//产品SN号
// const goods_language_select = ref(null);//语言选择
const goods_tool_name = ref<string | null>(null);
const goods_soft_version = ref<string | null>(null);
const goods_test_type = ref<string | null>(null);
const goods_start_time = ref<Date | null>(null)
const goods_end_time = ref<Date | null>(null)
const goods_order_id = ref<number | null>(null);//订单号
const goods_order_num = ref<number | null>(5)//订单数量
const goods_test_num = ref<number | null>(null)//检验数量
const goods_dev_name = ref<string | null>(null)//测试设备的名字  插接箱  始端箱 母线槽
const end_judgment = ref()//综合判定
const centerAlign = 'center'//标题居中
const fontSize = 60;// 设置字体大小，可以根据需要调整
const language = ref(true)
// const modelNumber = ref('')
// const customerName = ref('')
// const radio1 = ref('2')
const loading = ref(false) // 加载中
const queryParams = reactive({
  orderId: '' , // 订单号
  productSN: '',// 成品代码
  moduleSN: ''// 模块序列号
})


  // 使用正则表达式同时匹配中英文分号
  const  formatSkills = (skills) =>{
    if (skills) {
      return skills.replace(/[;；]/g, '<br/>');
    }
      return '';
    }
  const exportToPDF = () => {
      // 获取需要导出的页面内容
      const element = document.getElementById('PDFcontent');
      // 使用 html2canvas 将内容转换为 canvas
      html2canvas(element)
        .then(canvas => {
          const contentWidth = canvas.width
          const contentHeight = canvas.height
          // 一页pdf显示html页面生成的canvas高度;
          // const pageHeight = (contentWidth / 592.28) * 841.89
          const pageHeight = (contentWidth / 592.28) * 835.89
          // 页面偏移
          let position = 0

          // 创建新的 PDF 实例
          const PDF = new JsPDF('', 'pt', 'a4')

          // 分页打印
          while (position < contentHeight) {
            // 当前页剩余可显示的高度
            let remainingHeight = pageHeight

            // 如果剩余内容不足以填满一页，则调整剩余高度
            if (position + remainingHeight > contentHeight) {
              remainingHeight = contentHeight - position
            }

            // 使用剩余高度创建一个新的 canvas
            const tempCanvas = document.createElement('canvas')
            tempCanvas.width = contentWidth
            tempCanvas.height = remainingHeight
            const context = tempCanvas.getContext('2d')
            context?.drawImage(canvas, 0, position, contentWidth, remainingHeight, 0, 0, contentWidth, remainingHeight)

            // 将临时 canvas 转换为图像
            const imageData = tempCanvas.toDataURL('image/jpeg', 1.0)

            // 将图像添加到 PDF 中
            PDF.addImage(imageData, 'JPEG', 0, 0, 595.28, (592.28 / contentWidth) * remainingHeight)

            // 更新位置
            position += remainingHeight
            
            // 如果还有剩余内容，则添加新的页面
            if (position < contentHeight) {
              PDF.addPage()
            }
          }

          // 导出 PDF
          if(language.value === true)
          {
             PDF.save(`产品SN号 ${inputdata.value} 出厂报告.pdf`)
          }
          else
          {
            PDF.save(`Product SN ${inputdata.value} Ex-factory Report.pdf`)
          }

          // 导出完隐藏隐藏测试步骤div 关闭弹窗
         dialogVisible.value = false
        })
        .catch(error => {
          console.log('导出PDF时出错：', error)
        })
    }
  const handleExport = () => {
    loading.value = true;
     exportToPDF();
      setTimeout(() => {
        loading.value = false;
      }, 2000);

    }

    const getReport = async () =>{
      goods_SN_data.value = []
      ChineseItems.value = []
      EnglishItems.value = []
      let values = inputdata.value.split('+');
      if (values.length == 0){
        return;
      }
      queryParams.orderId = values[0]
      queryParams.productSN = values[1]
      queryParams.moduleSN = values[2]
      loading.value = true
      try {
        const data = await TestDataApi.getInternalReport(queryParams)
        goods_SN_data.value = data
        if (goods_SN_data.value.length != 0 ){
          // 数据分成两部分
            ChineseItems.value = goods_SN_data.value.filter(item => item.languageSelect == 0);
            EnglishItems.value = goods_SN_data.value.filter(item => item.languageSelect == 1);
            console.log(EnglishItems.value)
          if(language.value === true){
            goods_SN_data.value = ChineseItems.value
          }
          else {
            goods_SN_data.value = EnglishItems.value
          }
          goods_product_sn.value = goods_SN_data.value[0].productSn;
          goods_tool_name.value = goods_SN_data.value[0].toolName;
          goods_soft_version.value = goods_SN_data.value.find(item=>item.softVersion !== '0.0.0').softVersion;
          goods_test_type.value = goods_SN_data.value[0].testType;
          goods_order_id.value = goods_SN_data.value[0].orderId;
          goods_order_num.value = goods_SN_data.value[0].orderNum;
          goods_dev_name.value = goods_SN_data.value[0].devName;
          if (goods_SN_data.value.length > 0 ){
            goods_start_time.value = goods_SN_data.value[goods_SN_data.value.length -1].startTime;
            goods_end_time.value = goods_SN_data.value[goods_SN_data.value.length -1].endTime;
            // end_judgment.value = goods_SN_data.value[goods_SN_data.value.length -1].testResult
          } else {
              goods_end_time.value = null;
              end_judgment.value = null;
          }
         let testResult = true;
          for(var i=0;i<goods_SN_data.value.length;i++){
              if(goods_SN_data.value[i].testResult == 0){
                testResult = false;
              }
        } 
        end_judgment.value = testResult;
          // const res = await TestDataApi.getReportData(queryParams)
          // goods_test_num.value = res.passTestNum
          dialogVisible.value = true;

        }else{
          ElMessage({
            message: '暂无数据',
            type: 'error'
          })
        }
          
      } finally {
        loading.value = false
      }
      
    }

    const clearSearchHandle=()=>{
      goods_SN_data.value = {};
    }

    
  const formattedGoodsStartTime = (time) =>{
    if (time != null) {
        let date = new Date(time); 
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
      }
      return ''
    }

  const displayEndJudgment=(value) =>{
    if(language.value == false){
      return value == 1 ? 'PASS' : 'FAIL';
    }
    else{
        return value == 1 ? '通过' : '失败';
    }
  }


  const changeLanguage = () =>{
    if(language.value === true){
      goods_SN_data.value = ChineseItems.value
    }
    else {
      goods_SN_data.value = EnglishItems.value
      console.log(goods_SN_data.value)
    }
    goods_dev_name.value = goods_SN_data.value[0].devName;
  }

</script>
<style scoped>
/* .container{
    display: flex;
    align-items: flex-start;

  }
  .container-top{
      display: flex;
      align-items: flex-start;
      margin-top: 10px;
      margin-bottom: 10px;

  } */
  .cell-content{
      white-space: pre-wrap;
  }
  .time-picker{
    flex: 1;
      margin-top: 20px;
      margin-bottom: 20px;
  }
  .date-picker{
      margin-right: 10px;
  }
  .reportDetails {
      padding-left: 20px;
      padding-right: 20px;
  }

:deep(.my-descriptions th),
:deep(.my-descriptions td)  {
  border: 5px solid #e4e7ed !important;
  padding: 12px 12px !important;
}
:deep(.my-descriptions th)  {
  font-size: 16px;
  background-color: #cbd1db !important;
}
.table-row {
  margin-bottom: 13px; /* 设置行与行之间的间距 */
  display: flex;
    /* text-align: left;
  justify-content: center; 
align-items: center;  */
}
.table-row2{
  margin-bottom: 13px; /* 设置行与行之间的间距 */
  display: flex;
}
.table-label{
  font-weight: bold; /* 设置标签的字体加粗 */
  font-size: 22px; /* 设置字体大小为16像素 */
  margin-left: 20px; /* 设置行与行之间的间距 */
    text-align: left;
}
.table-label-1{
  font-weight: bold; /* 设置标签的字体加粗 */
  font-size: 22px; /* 设置字体大小为16像素 */
  margin-left: 20px; /* 设置行与行之间的间距 */
  text-align: left;
}
.table-label-2{
  font-weight: bold; /* 设置标签的字体加粗 */
  font-size: 22px; /* 设置字体大小为16像素 */
  margin-left: 20px; /* 设置行与行之间的间距 */
  text-align: left;
}

.table-data2{
  font-size: 22px; /* 设置字体大小为16像素 */
  margin-right: 10px; /* 设置行与行之间的间距 */
}
.custom-table {
  font-size: 20px; /* 调整字体大小 */
  line-height: 4; /* 调整行高 */
  white-space: pre-wrap; /* 允许换行 */
  padding: 20px; /* 调整内边距 */
}

.container-button{
     margin-left: auto; /* 将按钮容器中的文本右对齐 */
}
.container-switch{
  text-align: left;
}
.custom-button {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
/* 使用 scoped 样式限制作用范围 */
.no-wrap .el-table__header th > .cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.full-width-input {
  white-space: normal !important; /* 允许文本换行 */
  overflow: visible !important; /* 显示溢出内容 */
  text-overflow: clip !important; /* 不使用省略号 */
}
.merged-cell {
  background-color: #f5f5f5;
  text-align: center;
  vertical-align: middle;
  display: flex;
  align-items: center;
  justify-content: center;
}
.custom-dialog-title {
  display: flex;
  align-items: center;
}
.horizontal-rule {
  width: 98%; /* 或者是任何你想要的百分比，用于决定分割线的长度 */
  margin: 0 auto; /* 这会使分割线居中 */
}
.br-rule{
    height: 60px;
}
.br-rule2{
    height: 700px;
}
.product-name {
  text-align: left;
  font-size: 40px;
  line-height: 45px;
}

.customer-label {
  text-align: right;
  font-size: 40px;
  line-height: 45px;
  margin-right: 10px;
}

.input-custom {
  font-size: 40px;
  height: 45px;
  margin-top: 5px;
  border: none;
  padding: 0;
  outline: none;
  background-color: transparent;
  font-family: inherit;
  color: inherit;
}
.dialog-content {
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 40px;
  line-height: 45px;
}
.column p:not(:last-child) { /* 为每个列内的非最后一个<p>元素添加间距 */
  margin-bottom: 60px; /* 调整此值以改变行间距 */
}

.left-column {
  padding-left: 100px; /* 根据需要调整这个数值 */
}
.right-column {
  align-items: flex-start; /* 或 'center' 依据需要调整 */
  padding-left: 100px; /* 根据需要调整这个数值 */
}

.cell-content-table{
  font-size: 23px;
  line-height: 1.5em;
  padding-top: 4px;
  padding-bottom: 4px;
}

.left-row {
  /* width: 45%; */
  padding-left: 100px; /* 根据需要调整这个数值 10%*/
}
.right-row {
  /* width: 45%; */
  /*align-items: flex-start;  或 'center'  依据需要调整 */
  /* padding-left: 700px;  */
  /* 根据需要调整这个数值70% */
}

/* 输入框样式可以根据需要调整 */
input {
  font-size: 40px;
  height: 50px;
  margin-top: 0px;
  border: none;
  padding: 0;
  outline: none;
  background-color: transparent;
  font-family: inherit;
  color: inherit;
  line-height: 1.25; /* 调整行高 */
}
.radio-buttons {
  display: flex;
  justify-content: flex-end;
  padding-left: 280px;
}
.radio-buttons-1{
  padding-left: 40px;
}
</style>