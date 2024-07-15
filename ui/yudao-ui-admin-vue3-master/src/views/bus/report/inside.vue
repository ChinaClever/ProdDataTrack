<template>
  <div class="container">
    <el-input
      class="date-picker"
      clearable
      v-model="inputdata"
      placeholder="扫码枪扫码输入 产品SN号"
      @keyup.enter="handleEnterKey"
      @clear="clearSearchHandle"/>
      <el-button @click="showDialog">生成对内报告</el-button>
  </div>

  <el-dialog title="对内生产报告" :visible="dialogVisible"  width="100%" @close="dialogVisible=false">
    <div class="custom-button">
        <el-switch
            v-model="language"
            active-text="中文"
            inactive-text="英文"
        />
        <!-- @change="updateDate" -->
        <el-button type="primary" class="container-button" @click="handleExport">导出 PDF</el-button>
      </div>
    <div class="reportDetails" id="PDFcontent">
<!--                    中文-->
        <div v-if="language == true" >
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">New Busbar {{goods_dev_name}}功能检验报告</p>
          <hr/>
          <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">客户名称：</el-col>
              <el-col  class="table-label-1">订单号：{{goods_order_id}}</el-col>
              <el-col  class="table-label-2">订单数量：{{goods_order_num}} 台</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">产品型号：{{goods_dev_name}}</el-col>
              <el-col  class="table-label-1">检验日期：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
              <el-col  class="table-label-2">检验数量：{{goods_test_num}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">SN编号：{{goods_product_sn}}</el-col>
              <el-col  class="table-label-1">检验开始时间：{{goods_start_time}}</el-col>
              <el-col  class="table-label-2">检验结束时间：{{goods_end_time}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">测试项目：成品功能</el-col>
              <el-col  class="table-label-1">检验类型：全检</el-col>
              <el-col  class="table-label-2">检验结果：{{displayEndJudgment(end_judgment)}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>

        <br/>
          <div class="table-wrapper">
            <el-table :data="goods_SN_data" class="table-data2" :span-method="objectSpanMethod" style="width: 100%" border>
                <el-table-column prop="test_step" label="检验步骤" :align="centerAlign" width="200"/>
                <el-table-column prop="test_item" label="检验项" :align="centerAlign" width="300"/>
                <el-table-column prop="test_request" label="检验要求" :header-align="centerAlign" width="550">
                  <template #item="{ scope }">
                      <div v-html="formatSkills(scope.row.test_request)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="test_process" label="检验结果"  :header-align="centerAlign"  width="500">
                    <template #item="{ scope }">
                      <div v-html="formatSkills(scope.row.test_process)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="test_result" label="判定" :align="centerAlign" width="200">
                  <template #item="{ scope }" >
                    <span v-if="scope.row.test_result == 1" >通过</span>
                    <span v-else-if="scope.row.test_result == 0" >失败</span>
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
              <el-col  class="table-label">Customer Name：</el-col>
              <el-col  class="table-label-1">Order Number：{{goods_order_id}}</el-col>
              <el-col  class="table-label-2">Order Quantity：{{goods_order_num}} UNIT</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">Product Model：{{goods_dev_name}}</el-col>
              <el-col  class="table-label-1">Inspection Date：{{formattedGoodsStartTime(goods_end_time)}}</el-col>
              <el-col  class="table-label-2">Inspection Quantity：{{goods_test_num}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">SN Number：{{goods_product_sn}}</el-col>
              <el-col  class="table-label-1">Inspection Start Time：{{goods_start_time}}</el-col>
              <el-col  class="table-label-2">Inspection End Time：{{goods_end_time}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>
          <div>
            <el-row class="table-row">
              <el-col  class="table-label">Test Items：Finished product functions</el-col>
              <el-col  class="table-label-1">Inspection Type：Full inspection</el-col>
              <el-col  class="table-label-2">Inspection results：{{displayEndJudgment(end_judgment)}}</el-col>
            </el-row>
          </div>
            <hr/>
            <br/>

        <br/>
          <div class="table-wrapper">
            <el-table :data="goods_SN_data" class="table-data2" :span-method="objectSpanMethod" style="width: 100%" border>
                <el-table-column prop="test_step" label="Inspection Steps"  :align="centerAlign" width="200"/>
                <el-table-column prop="test_item" label="Inspection Items" :align="centerAlign" width="300"/>
                <el-table-column prop="test_request" label="Inspection Requirements" :header-align="centerAlign" width="550">
                    <template #item="{ scope }">
                      <div v-html="formatSkills(scope.row.test_request)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="test_process" label="Inspection Results"  :header-align="centerAlign"  width="500">
                  <template #item="{ scope }">
                      <div v-html="formatSkills(scope.row.test_process)" ></div>
                  </template>
                </el-table-column>
                <el-table-column prop="test_result" label="Determine" :align="centerAlign" width="200">
                  <template #item="{ scope }" >
                    <span v-if="scope.row.test_result == 1" >PASS</span>
                    <span v-else-if="scope.row.test_result == 0" >FAIL</span>
                  </template>
                </el-table-column>
            </el-table>
          </div>
        </div>

    </div>
  </el-dialog>
</template>

<script setup lang="ts">
// import { TestDataApi } from '@/api/bus/testData'
// import { ElMessageBox } from 'element-plus'
import html2canvas from "html2canvas";
import JsPDF from "jspdf";

const inputdata = ref('')
const Out_dialogVisible= ref(false) 
const dialogVisible= ref(false)      //控制报告弹窗的显示与隐藏  对内
const goods_SN_data = ref({})//获取到的总的数据
const  goods_product_sn = ref(false)//产品SN号
// const goods_language_select = ref(null);//语言选择
     // const goods_tool_name = ref(null);
// const goods_soft_version = ref(null);
// const goods_test_type = ref(null);
const goods_start_time = ref<Date | null>(null)
const goods_end_time = ref<Date | null>(null)
const goods_order_id = ref<number | null>(null);//订单号
const goods_order_num = ref<number | null>(5)//订单数量
const goods_test_num = ref<number | null>(null)//检验数量
const goods_dev_name = ref<string | null>(null)//测试设备的名字  插接箱  始端箱 母线槽
const end_judgment = ref(null)//综合判定
const centerAlign = 'center'//标题居中
const fontSize = 60;// 设置字体大小，可以根据需要调整
const language = ref(true)
// const modelNumber = ref('')
// const customerName = ref('')
// const radio1 = ref('2')
const loading = ref(false) // 加载中
// const queryParams = reactive({
//   orderId: '' , // 订单号
//   productSN: '',// 成品代码
//   moduleSN: ''// 模块序列号
// })

  //插接箱对外报告信息 -EN  基本型
  // const JackBox_jb_EN = [
  //       {
  //         num:'1',
  //         test_item: 'Internal Connections',
  //         test_request: '    ①  Visual inspection: See whether the wiring harness is pressed to the position, the copper wire can not be exposed more than 2mm; Whether there are problems such as virtual welding, missing welding, flying wire and loose welding; Whether the internal wiring is neat and beautiful.\n' +
  //             '    ②  Hand pull: Pull the wire with the specified tension, whether there will be loosening, falling off phenomenon.\n' +
  //             '    ③  Check the fastening point of the screw with a wrench to see if it meets the requirements.\n' +
  //             '    ④  If the problem is found, rework and reconnect it in time.\n' +
  //             'Note: For wireless products with reserved terminals at the front end, check the wiring screws in the terminals.\n',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'2',
  //         test_item: 'Product configuration',
  //         test_request: '①  Check the assembled products with the tracking list and process drawings to ensure the accuracy of the production;\n' +
  //             '    ②  Check the cable and plug specifications against the product specifications to confirm that they are correct.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },

  //         {
  //         num:'3',
  //         test_item: 'Ground resistance detection',
  //         test_request: 'Apply 25A current for 3S to the area between the input PE terminal and the grounding screws of the casing. The grounding resistance value must be less than 100mΩ to be considered qualified.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'4',
  //         test_item: 'High voltage resistance test\n',
  //         test_request: 'Apply 2500VAC voltage between the live and neutral wires, the ground wire and the housing for 5S, and no flashover or breakdown should occur.',
  //         test_process: 'No flashover or breakdown',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'5',
  //         test_item: 'Insulation testing',
  //         test_request: 'Apply 500VDC voltage between the live and neutral wires and the ground wire for 5S and read the value. The insulation resistance value must be greater than 5MΩ to be considered qualified.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'6',
  //         test_item: 'Polarity check',
  //         test_request: 'Connect the load to each socket of the product, check whether the indicator light of the polarity test module is normal, and determine whether the live, neutral and ground wiring are correct.\n',
  //         test_process: 'Meet standard\n' +
  //             'requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'7',
  //         test_item: 'Breaker inspection',
  //         test_request: 'Turn on and off the circuit breaker successively:\n' +
  //             '    ①  The circuit breaker is off, the corresponding green indicator is off, the corresponding socket has no output.\n' +
  //             '    ②  The circuit breaker is closed, the corresponding green indicator is on, the corresponding socket output is normal.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },

  //         {
  //         num:'8',
  //         test_item: 'Product logo\n' +
  //             'Appearance',
  //         test_request: '① The following markings should be present: rated current, rated voltage, name or trademark of the manufacturer or seller, and model number;\n' +
  //             '    ② Symbols should be used as specified in the standard;\n' +
  //             '    ③ The logo should be durable and legible;\n' +
  //             '    ④ Check the appearance of the product to see if there are any problems such as color mixing and obvious scratches.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'9',
  //         test_item: 'Accessory inspection',
  //         test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  // ]
  //插接箱对外报告信息  附件1 -zh
  const JackBox_1 = [
        {
          num:'硬件设置',
          test_item: '上电检查',
          test_request: '上电蜂鸣，指示灯绿色闪烁，设备进入正常工作模式。',
          test_process: '开机正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '温度检查',
          test_request: '上电检查L1/L2/L3/N温度值，所有的值在平均值的±5℃范围内',
          test_process: '符合标准要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '显示屏检查',
          test_request: '显示屏居中、背光匀称，无异常亮点，显示切换不闪烁。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '显示屏检查',
          test_request: '接入移除负载，显示屏电气参数、设备信息显示和实际一致。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '指示灯检查',
          test_request: '电压、电流、温度超过阀值时，指示灯红色闪烁；解除告警，指示灯恢复绿色闪烁。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '蜂鸣器检查',
          test_request: '蜂鸣器开：产生告警，蜂鸣器声音响亮、清脆；解除告警，蜂鸣器声音停止；蜂鸣器关：产生告警，蜂鸣器声音停止。',
          test_process: '功能正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: '功能按键：设置功能键（进入设置模式，设置后保存退出）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'UP按键：向上翻页功能键，翻页查看显示屏内容（设置模式时为向上设置功能）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'DOWN按键：向下翻页功能键，翻页查看显示屏内容（设置模式时长按为定位功能，短按为向下设置功能）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'RESET按键：复位重启键，按一下设备重启。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '接口检查',
          test_request: 'IN、OUT: RS485串口通讯正常。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '级联检查',
          test_request: 'IN、OUT: 级联治具连接插接箱IN和OUT接口，通讯正常',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电压检查',
          test_request: '分别通断各插座对应的回路断路器，对应回路电压数据一致：',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电流检查',
          test_request: '接入负载，分别断开负载端三相断路器，对应回路电流为0A；',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '硬件监测',
          test_request: '接入移除负载，显示屏电气参数实际一致。',
          test_process: '见附表2',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '硬件监测',
          test_request: '接入移除负载，串口读取电气参数实际一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: '软件版本',
          test_request: '检查产品规格书和产品显示软件版本一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: '输出回路',
          test_request: '输出回路数量与规格书要求一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: '输出插座',
          test_request: '输出插座类型单相或三相与规格书要求一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: 'Modbus',
          test_request: '使用Modbus协议读取设备所有参数信息。',
          test_process: '符合要求',
          test_result:"OK"
        },

  ]
  //插接箱对外报告信息  附件1 -EN
  const JackBox_1_EN = [
        {
          num:'Hardware Setup',
          test_item: 'Power-on check',
          test_request: 'When powered on, a buzzer sounds and the indicator light flashes green, and the device enters normal working mode.',
          test_process: 'Normal startup',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Temperature Check',
          test_request: 'Check the L1/L2/L3/N temperature values after power on. All values are within ±5℃ of the average value.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Display screen inspection',
          test_request: 'The display is centered, the backlight is evenly distributed, there are no abnormal bright spots, and the display switches without flickering.',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Display screen inspection',
          test_request: 'After the load is connected and removed, the electrical parameters and device information displayed on the display screen are consistent with the actual situation.',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Indicator light check',
          test_request: 'When the voltage, current, or temperature exceeds the thr/eshold, the indicator light flashes red; when the alarm is cleared, the indicator light returns to flashing green.',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Buzzer check',
          test_request: 'Buzzer on: generates an alarm, and the buzzer sounds loud and clear; clears the alarm and the buzzer stops;\n' +
              'Buzzer off: generates an alarm and the buzzer stops.',
          test_process: 'Functioning normally',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'Function key: Setting function key (enter setting mode, save and exit after setting)',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'UP button: page up function button, page up to view the display content (upward setting function in setting mode)',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'DOWN button: page down function button, page down to view the display content (in setting mode, long press for positioning function, short press for downward setting function)',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'RESET button: reset button, press it to restart the device.',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Interface Check',
          test_request: 'IN/OUT: RS485 Serial communication is normal.\n',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Cascade Check',
          test_request: 'IN/OUT: The cascade fixture is connected to the IN and OUT interfaces of the tap-off box, and the communication is normal',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Voltage Check',
          test_request: 'Switch on and off the circuit breakers corresponding to each socket respectively, and the corresponding circuit voltage data are consistent;',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Current check',
          test_request: 'Connect the load and disconnect the thr/ee-phase circuit breakers at the load end, and the corresponding loop current is 0A;',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Hardware Monitoring\n',
          test_request: 'After the load is connected and removed, the electrical parameters of the display screen are actually the same.',
          test_process: 'Appendix 2',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Hardware Monitoring\n',
          test_request: 'After the load is connected and removed, the electrical parameters read thr/ough the serial port are actually consistent.',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Software version',
          test_request: 'Check that the product specification and product display software versions are consistent.',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Output circuit',
          test_request: 'The number of output circuits is consistent with the requirements of the specification.',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Output socket',
          test_request: 'The output socket type is single-phase or thr/ee-phase and is consistent with the requirements of the specification.',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Modbus',
          test_request: 'Use Modbus protocol to read all parameter information of the device.',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Modbus',
          test_request: 'Use the Modbus protocol to modify device parameter information.\n',
          test_process: 'Meet the requirements\n',
          test_result:"OK"
        },

  ]

  //始端箱对外报告信息 -EN 基本型
  // const HeaBox_jb_EN = [
  //       {
  //         num:'1',
  //         test_item: 'Internal Connections',
  //         test_request: '    ①  Visual inspection: Check whether the wire harness is crimped in place, and the copper wire cannot be exposed more than 2mm; whether there are problems such as cold soldering, leaking soldering, flying wires, looseness, etc.; whether the internal wiring is neat and beautiful;\n' +
  //             '    ②  Hand pulling: Pull the wire with the specified pulling force to see if it will loosen or fall off;\n' +
  //             '    ③  Use a wrench to check the screw fastening points to see if they meet the requirements.\n' +
  //             'Note: For wireless products with reserved wiring terminals at the front end, the wiring screws inside the terminals must be checked.\n',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'2',
  //         test_item: 'Product configuration',
  //         test_request: '    ① Check the assembled products with the tracking sheet, process drawings, and wiring diagrams to ensure that the assembly is accurate;\n' +
  //             '    ② Check the specifications of the circuit breaker and copper busbar against the product specifications to confirm that they are correct.\n' +
  //             '    ③ For the overlapping of structures, protective grounding of parts and electrical components, special grounding washers or other measures should be installed, and the connection should be good and there should be obvious grounding marks;\n',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'3',
  //         test_item: 'Ground resistance detection',
  //         test_request: 'Apply 25A current for 3S to the area between the input PE terminal and the grounding screws of the casing. The grounding resistance value must be less than 100mΩ to be considered qualified.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'4',
  //         test_item: 'High voltage resistance test',
  //         test_request: 'Apply 2500VAC voltage between the live and neutral wires, the ground wire and the casing for 5S, and no flashover or breakdown should occur.',
  //         test_process: 'No flashover or breakdown',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'5',
  //         test_item: 'Insulation testing',
  //         test_request: 'Apply 500VDC voltage between the live and neutral wires and the ground wire for 5S and read the value. The insulation resistance value must be greater than 10MΩ to be considered qualified.\n',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'6',
  //         test_item: 'Polarity check',
  //         test_request: 'Connect the product to the load, check whether the indicator light of the polarity test module is normal, and determine whether the live, neutral and ground wiring are correct.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'7',
  //         test_item: 'Breaker inspection',
  //         test_request: 'Breaker closes, the green indicator light is on;\n' +
  //             'Breaker trips, the red indicator lamp lights and the buzzer beeps;Breaker tripping, green and red indicator lamps do not light up;',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'8',
  //         test_item: 'Product logo and appearance',
  //         test_request: '    ① The following markings should be present: rated current, rated voltage, name or trademark of the manufacturer or seller, and model number;\n' +
  //             '    ② Symbols should be used as specified in the standard;\n' +
  //             '    ③ The logo should be durable and legible;；\n' +
  //             '    ④ Check the appearance of the product to see if there are any problems such as color mixing and obvious scratches;\n',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  //         {
  //         num:'9',
  //         test_item: 'Accessory inspection',
  //         test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
  //         test_process: 'Meet standard requirements',
  //         test_result:"OK"
  //       },
  // ]
  //始端箱对外报告信息  附件1  -zh
  const HeaBox_1 = [
        {
          num:'硬件设置',
          test_item: '上电检查',
          test_request: '上电蜂鸣，指示灯绿色闪烁，设备进入正常工作模式。',
          test_process: '开机正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '温度检查',
          test_request: '上电检查L1/L2/L3/N温度值，所有的值在平均值的±5℃范围内。',
          test_process: '符合标准要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '显示屏检查',
          test_request: '显示屏居中、背光匀称，无异常亮点，显示切换不闪烁。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '显示屏检查',
          test_request: '接入移除负载，显示屏电气参数、设备信息显示和实际一致。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '指示灯检查',
          test_request: '电压、电流、温度超过阀值时，指示灯红色闪烁。；解除告警，指示灯恢复绿色闪烁。',
          test_process: '显示正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '蜂鸣器检查',
          test_request: '蜂鸣器开：产生告警，蜂鸣器声音响亮、清脆。；解除告警，蜂鸣器声音停止。；蜂鸣器关：产生告警，蜂鸣器声音停止。',
          test_process: '功能正常',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: '功能按键：设置功能键（进入设置模式，设置后保存退出）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'UP按键：向上翻页功能键，翻页查看显示屏内容（设置模式时为向上设置功能）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'DOWN按键：向下翻页功能键，翻页查看显示屏内容（设置模式时长按为定位功能，短按为向下设置功能）',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'RESET按键：复位重启键，按一下设备重启。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '接口检查',
          test_request: 'NET： 网络接口通讯功能正常。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '接口检查',
          test_request: 'IN、OUT: RS485串口通讯正常。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '级联检查',
          test_request: 'IN、OUT: 级联治具连接插接箱IN和OUT接口，通讯正常',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电压检查',
          test_request: '分别通断各插座对应的回路断路器，对应回路电压数据一致：',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电流检查',
          test_request: '接入负载，分别断开负载端三相断路器，对应回路电流为0A；',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '硬件监测',
          test_request: '接入移除负载，显示屏电气参数实际一致。',
          test_process: '见附表2',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '硬件监测',
          test_request: '接入移除负载，串口读取电气参数实际一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: '软件版本',
          test_request: '检查产品规格书和产品显示软件版本一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: 'Modbus',
          test_request: '使用Modbus协议读取设备所有参数信息。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'软件检查',
          test_item: 'SNMP',
          test_request: '使用SNMP协议读取和修改设备参数信息。',
          test_process: '符合要求',
          test_result:"OK"
        },

  ]
   //始端箱对外报告信息  附件1  -EN
  const HeaBox_1_EN = [
        {
          num:'Hardware Setup',
          test_item: 'Power-on check',
          test_request: 'When powered on, a buzzer sounds and the indicator light flashes green, and the device enters normal working mode.',
          test_process: 'Normal startup',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Temperature Check',
          test_request: 'Check the L1/L2/L3/N temperature values ​​after power on. All values ​​are within ±5℃ of the average value.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Display screen inspection',
          test_request: 'The display is centered, the backlight is evenly distributed, there are no abnormal bright spots, and the display switches without flickering.\n',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Display screen inspection',
          test_request: 'After the load is connected and removed, the electrical parameters and device information displayed on the display screen are consistent with the actual situation.',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Indicator light check',
          test_request: 'When the voltage, current, or temperature exceeds the thr/eshold, the indicator light flashes red; when the alarm is cleared, the indicator light returns to flashing green.',
          test_process: 'Display normal',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Buzzer check',
          test_request: 'Buzzer on: generates an alarm, and the buzzer sounds loud and clear; clears the alarm and the buzzer stops;\n' +
              'Buzzer off: generates an alarm and the buzzer stops.',
          test_process: 'Functioning normally',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'Function key: Setting function key (enter setting mode, save and exit after setting)',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'UP button: page up function button, page up to view the display content (upward setting function in setting mode)',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'DOWN button: page down function button, page down to view the display content (in setting mode, long press for positioning function, short press for downward setting function)\n',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'RESET button: reset button, press it to restart the device.',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Interface Check',
          test_request: 'NET: The network interface communication function is normal.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Interface Check',
          test_request: 'IN /OUT:  RS485 serial port communication is normal.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Voltage Check',
          test_request: 'Disconnect the A/B/C circuit breakers at the input end respectively, and the corresponding phase voltage data is 0V;',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Current check',
          test_request: 'Connect the load, disconnect the thr/ee-phase circuit breakers at the load end, and the corresponding phase current is 0A;',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Hardware Monitoring',
          test_request: 'After the load is connected and removed, the electrical parameters of the display screen are actually the same.',
          test_process: 'Appendix 2',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Hardware Monitoring',
          test_request: 'After the load is connected and removed, the electrical parameters read thr/ough the serial port are actually consistent.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Software version',
          test_request: 'Check that the product specification and product display software versions are consistent.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'Modbus',
          test_request: 'Use Modbus protocol to read and modify device parameter information',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Software Check',
          test_item: 'SNMP',
          test_request: 'Use SNMP protocol to read and modify device parameter information',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
  ]


const testStepGroup = (testStep) => {
      //获取相同名称的长度
      if(Out_dialogVisible.value === true && goods_dev_name.value === '插接箱'  && Array.isArray(JackBox_1))
      {
          return JackBox_1.filter((o) => o.num == testStep).length;
      }
      else if(Out_dialogVisible.value === true && goods_dev_name.value === '始端箱' && Array.isArray(HeaBox_1))
      {
          return HeaBox_1.filter((o) => o.num == testStep).length;
      }
      else if(Out_dialogVisible.value === true && goods_dev_name.value === 'Tap-off box'  && Array.isArray(JackBox_1_EN))
      {
          return JackBox_1_EN.filter((o) => o.num == testStep).length;
      }
      else if(Out_dialogVisible.value === true && goods_dev_name.value === 'Feeder box' && Array.isArray(HeaBox_1_EN))
      {
          return HeaBox_1_EN.filter((o) => o.num == testStep).length;
      }
      else if(Array.isArray(goods_SN_data.value))
      {
        return goods_SN_data.value.filter((o) => o.test_step == testStep).length;
      }
      else {
        return  0;
      }
  }

  const testStepLen = (name) => {
    const tmp = Array.from(groupNum());
      let index = tmp.indexOf(name);
      let len = 0;
      for(let i = 0; i < index; i++)
      {
        len += testStepGroup(tmp[i]);
      }
      return len;
  }

  const objectSpanMethod = ({row, rowIndex, columnIndex}) => {
    if(Out_dialogVisible.value === true && (goods_dev_name.value === '插接箱' || goods_dev_name.value === '始端箱' || goods_dev_name.value === 'Feeder box' || goods_dev_name.value === 'Tap-off box'))
        {
          if(columnIndex === 0)
          {
            const len = testStepGroup(row.num);
            const lenName = testStepLen(row.num);
            if(rowIndex === lenName)
            {
              return {
                rowspan:len,
                colspan:1,
              };
            }else {
              return {
                rowspan:0,
                colspan:0,
              };
            }
          }
      }
    else{
        if(columnIndex === 0)
        {
          const len = testStepGroup(row.test_step);
          const lenName = testStepLen(row.test_step);
          if(rowIndex === lenName)
          {
            return {
              rowspan:len,
              colspan:1,
            };
          }else {
            return {
              rowspan:0,
              colspan:0,
            };
          }
        }
    }
  }
   
  // const updateLanguage = () => {
  //   if(out_language.value !== true)
  //     {
  //       if(goods_dev_name.value == '母线槽'){
  //           goods_dev_name.value = 'Busway';
  //           console.log(goods_dev_name)
  //       }
  //       else if(goods_dev_name.value == '始端箱'){
  //           goods_dev_name.value = 'Feeder box';
  //       }
  //       else if(goods_dev_name.value == '插接箱'){
  //           goods_dev_name.value = 'Tap-off box';
  //       }
  //     }
  //   else {
  //       if(goods_dev_name.value == 'Busway')
  //       {
  //           goods_dev_name.value = '母线槽'
  //       }
  //       else if(goods_dev_name.value == 'Feeder box')
  //       {
  //           goods_dev_name.value = '始端箱'
  //       }
  //       else if(goods_dev_name.value == 'Tap-off box')
  //       {
  //           goods_dev_name.value = '插接箱'
  //       }
  //   }
  // }

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
          const pageHeight = (contentWidth / 592.28) * 841.89
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

    const showDialog = () =>{
       dialogVisible.value = true;
    }

    const handleEnterKey=()=>{
     showDialog()
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