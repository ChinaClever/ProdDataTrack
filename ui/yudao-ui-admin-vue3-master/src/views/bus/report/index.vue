<template>
    <div style="width: 100%; height: 100%; overflow: auto;">
      <div class="custom-button">
        <el-switch
            v-model="out_language"
            active-text="中文"
            inactive-text="英文"
            @change="updateLanguage"
        />
        <el-button type="primary" class="container-button" @click="out_handleExport">导出 PDF</el-button>
      </div>

      <div v-if="out_language == true">
        <div id="page1Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
              <div style="padding-left: 60px; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                  <p>惠州市克莱沃电子有限公司 </p>
              </div>
          </div>
          <div class="horizontal-rule">
          <hr/>
          </div>
          <div style="padding-right: 60px;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">第1页，共2页</p>
          </div>
          <br/>
          <p v-if="out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">检 验 报 告</p>
          <p v-if="!out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div class="br-rule">
            <br/>
          </div>
          <div class="dialog-content">
            <div class="row">
              <div class="column left-column">
                <p >产品名称: {{ goods_dev_name }}</p>
                <p >规格型号: {{modelNumber}}</p>
                <p >生产日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >检验日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div class="column right-column">
                <p >客户名称: {{customerName}}</p>
                <p >检验类别: 出场检验</p>
                <p >生产数量: {{goods_order_num}}台</p>
                <p >检验数量: {{goods_test_num}}台</p>
              </div>
            </div>
          </div>
          <div class="br-rule2">
            <br/>
          </div>
          <div style="text-align: center; font-size: 40px;">
              <p>检验依据：GB/T7251.6-2015标准技术要求</p>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p> 检 验 结 论 </p>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>该批产品检验，所检项目符合GB/T7251.6-2015标准技术要求</p>
          </div>
          <div style="height: 120px">
              <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>说明：</p>
          </div>
          <div style="height: 30px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>1、产品特性及状态： 正常</p>
          </div>
          <div style="height: 30px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>2、检验环境条件：  （25±5）℃</p>
          </div>
          <div style="height: 60px">
            <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 30px">
            <br/>
          </div>
          <div style="text-align: right; font-size: 20px; padding-right: 30px;">
            <p>
              <span style="display: inline-block; margin-right: 50px;">年</span>
              <span style="display: inline-block; margin-right: 50px;">月</span>
              <span>日</span>
            </p>
          </div>
          <div style="height: 60px">
            <br/>
          </div>
          <div  class="column" style="display: flex; justify-content: space-between; font-size: 40px;">
            <div style="padding-left: 80px">
                  <span >检验员：</span>
            </div>
            <span>审核：</span>
            <div style="padding-right: 160px">
                <span >批准：</span>
            </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
        </div>
        <div id="page2Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
            <div style="padding-left: 60px; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                  <p>惠州市克莱沃电子有限公司 </p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="padding-right: 60px;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">第2页，共2页</p>
          </div>

          <br/>
          <p v-if="out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">检 验 报 告</p>
          <p v-if="!out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <br/>
            <div v-if="goods_dev_name === '母线槽'">
              <!--母线槽的报告排版-->
                <div >
                  <el-table :data="Busway" class="custom-table" style="width: 100%" border>
                      <el-table-column  prop="test_item" label="检验项目" :align="centerAlign" width="310"/>
                      <el-table-column prop="test_request" label="标准要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                  </el-table>
                </div>
            </div>

            <div  v-else-if="goods_dev_name === '插接箱'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：高温老化房、安规测试设备、母线质检测试系统等</p>
              </div>
                <div v-if="radio1 === '2'">
                  <el-table :data="JackBox" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="210"/>
                      <el-table-column  prop="test_request" label="标准要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                  </el-table>
                </div>
                <div v-else-if="radio1 === '1'">
                  <el-table :data="JackBox_jb" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="210"/>
                      <el-table-column  prop="test_request" label="标准要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                  </el-table>
                </div>
            </div>
            <div  v-else-if="goods_dev_name === '始端箱'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：高温老化房、安规测试设备、母线质检测试系统等</p>
              </div>
                <div v-if="radio1 === '2'">
                  <el-table :data="HeaBox" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="210"/>
                      <el-table-column  prop="test_request" label="标准要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                  </el-table>
                </div>
                <div v-if="radio1 === '1'">
                  <el-table :data="HeaBox_jb" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="210"/>
                      <el-table-column  prop="test_request" label="标准要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                  </el-table>
                </div>
       
            </div>

        </div>
        <div id="page3Content">
          <div v-if="goods_dev_name !== '母线槽' && radio1 !== '1'">
            <div class="custom-dialog-title" style="display: flex; align-items: center;">
              <div style="padding-left: 60px; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                  <p>惠州市克莱沃电子有限公司 </p>
              </div>
            </div>
            <div class="horizontal-rule">
              <hr/>
            </div>
            <br/>
            <br/>
          <div  v-if="goods_dev_name === '插接箱'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                  <p>附表1：产品功能测试</p>
              </div>
                  <div >
                    <el-table :data="JackBox_1" class="custom-table" :span-method="objectSpanMethod" style="width: 100%" border>
                        <el-table-column prop="num" label="" :align="centerAlign" width="120"/>
                        <el-table-column prop="test_item" label="测试项目" :align="centerAlign" width="190"/>
                        <el-table-column prop="test_request" label="测试要求" :header-align="centerAlign" width="750">
                          <template #item="{ scope }">
                              <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                          </template>
                        </el-table-column>
                        <el-table-column prop="test_process" label="测试结果"  :align="centerAlign"  width="500">
                            <template #item="{ scope }">
                              <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                          </template>
                        </el-table-column>
                        <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                    </el-table>
                  </div>
                <div style="padding-left: 50px; padding-top: 50px;text-align: left; font-size: 30px; flex: 1;">
                    <p>附表2：电流电压测试数据</p>
                </div>
                  <div >
                    <el-table :data="JackBox_2" class="custom-table"  style="width: 100%" border>
                        <el-table-column prop="A1" label="标准输入电流（A）" :align="centerAlign" width="180"/>
                        <el-table-column prop="A2" label="合格显示范围" :align="centerAlign" width="190"/>
                        <el-table-column prop="A3" label="分电流" :align="centerAlign" width="190"/>
                        
                        <el-table-column prop="A4" label="总电流"  :align="centerAlign"  width="190"/>
                        
                        <el-table-column prop="A5" label="标准输入电压（V）" :align="centerAlign" width="190"/>
                        
                        <el-table-column prop="A6" label="合格显示范围"  :align="centerAlign"  width="190"/>
                        
                        <el-table-column label="总电压"  :align="centerAlign"  width="570">
                            <el-table-column prop="L1" label="L1"  :align="centerAlign"  width="200"/>
                            
                            <el-table-column prop="L2" label="L2"  :align="centerAlign"  width="200"/>
                            
                            <el-table-column prop="L3" label="L3"  :align="centerAlign"  width="200"/>
                            
                        </el-table-column>
                    </el-table>
                  </div>
          </div>
          <div  v-else-if="goods_dev_name === '始端箱'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                <p>附表1：产品功能测试</p>
            </div>
                <div >
                  <el-table :data="HeaBox_1" class="custom-table" :span-method="objectSpanMethod" style="width: 100%" border>
                      <el-table-column prop="num" label="" :align="centerAlign" width="120"/>
                      
                      <el-table-column prop="test_item" label="测试项目" :align="centerAlign" width="190"/>
                      
                      <el-table-column prop="test_request" label="测试要求" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="测试结果"  :align="centerAlign"  width="500">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="200"/>
                      
                  </el-table>
                </div>

              <div style="padding-left: 50px; padding-top: 10px;text-align: left; font-size: 30px; flex: 1;">
                  <p>附表2：电流电压测试数据</p>
              </div>
                <div >
                  <el-table :data="HeaBox_2" class="custom-table"  style="width: 100%" border>
                      <el-table-column prop="A1" label="标准输入电流（A）" :align="centerAlign" width="220"/>
                      
                      <el-table-column prop="A2" label="合格显示范围" :align="centerAlign" width="220"/>
                      
                      <el-table-column prop="A4" label="总电流"  :align="centerAlign"  width="220"/>
                      
                      <el-table-column prop="A5" label="标准输入电压（V）" :align="centerAlign" width="220"/>
                      
                      <el-table-column prop="A6" label="合格显示范围"  :align="centerAlign"  width="220"/>
                      
                      <el-table-column label="总电压"  :align="centerAlign"  width="630">
                          <el-table-column prop="L1" label="L1"  :align="centerAlign"  width="210"/>
                          
                          <el-table-column prop="L2" label="L2"  :align="centerAlign"  width="210"/>
                          
                          <el-table-column prop="L3" label="L3"  :align="centerAlign"  width="210"/>
                          
                      </el-table-column>
                  </el-table>
                </div>
          </div>
          <div  class="column" style="display: flex; justify-content: space-between; font-size: 20px;">
            <div style="padding-left: 80px">
                  <span >OK：合格，符合测试要求。</span>
            </div>
            <span>NG：不合格，不符合测试要求。</span>
            <div style="padding-right: 80px">
                <span > NA：所测产品无此项功能。</span>
            </div>
          </div>
        </div>

        </div>
      </div>       

      <div v-else-if="out_language == false">
        <div id="page1Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
              <div style="padding-left: 60px; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                  <p>HUIZHOU CLEVER ELECTRONIC CO.,LTD</p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="padding-right: 60px;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">Page 1 of 2</p>
          </div>
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div class="br-rule">
            <br/>
          </div>
          <div class="dialog-content">
            <div class="row">
              <div class="column left-column">
                <p >Product Name: {{ goods_dev_name }}</p>
                <p >Sp. & Mod: {{modelNumber}}</p>
                <p >Production Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >Inspection Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div class="column right-column">
                <p >Customer Name: {{ customerName }}</p>
                <p >Inspection Category: Factory Inspection</p>
                <p >Production Quantity: {{goods_order_num}}units</p>
                <p >Inspection Quantity: {{goods_test_num}}units</p>
              </div>
            </div>
          </div>
          <div class="br-rule2">
            <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>Technical requirements according to GB/T 7251.6-2015 standard.</p>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p> Inspection Conclusion </p>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>The inspection of this batch of products concludes that the tested items comply with the technical requirements of GB/T 7251.6-2015 standard.</p>
          </div>
          <div style="height: 120px">
              <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 60px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>Notes：</p>
          </div>
          <div style="height: 30px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>1.Product Characteristics and Status: Normal</p>
          </div>
          <div style="height: 30px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 100px;">
              <p>2.Inspection Environmental Conditions: (25±5)°C</p>
          </div>
          <div style="height: 60px">
            <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 30px">
            <br/>
          </div>
          <div style="text-align: right; font-size: 20px; padding-right: 30px;">
            <p>
              <span>Date: _______/_______/_______</span>
            </p>
          </div>
          <div style="height: 60px">
            <br/>
          </div>
          <div  class="column" style="display: flex; justify-content: space-between; font-size: 40px;">
            <div style="padding-left: 80px">
                  <span >Inspector：</span>
            </div>
            <span>Review：</span>
            <div style="padding-right: 160px">
                <span >Approval：</span>
            </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
        </div>
        <div id="page2Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
            <div style="padding-left: 60px; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                  <p>HUIZHOU CLEVER ELECTRONIC CO.,LTD</p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="padding-right: 60px;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">Page 2 of 2</p>
          </div>
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <br/>
            <div v-if="goods_dev_name === 'Busway'">
              <!--母线槽的报告排版-->
                <div >
                  <el-table :data="Busway_EN" class="custom-table" style="width: 100%" border>
                      <el-table-column  prop="test_item" label="Inspection Items" :align="centerAlign" width="310"/>
                      
                      <el-table-column prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Inspection Results"  :align="centerAlign"  width="400">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Individual Conclusions" :align="centerAlign" width="300"/>
                      
                  </el-table>
                </div>
            </div>
            <div  v-else-if="goods_dev_name === 'Tap-off box'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection Instruments: High-temperature Aging Chamber, Safety Regulation Testing Equipment, Busbar Quality Inspection Testing System, etc.</p>
              </div>
                <div >
                  <el-table :data="JackBox_EN" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Inspection Items" :align="centerAlign" width="210"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Inspection Results"  :align="centerAlign"  width="400">
                          <template #item="{ scope }">
                            <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Individual Conclusions" :align="centerAlign" width="300"/>
                  </el-table>
                </div>
            </div>
            <div  v-else-if="goods_dev_name === 'Feeder box'">
              <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection Instruments: High-temperature Aging Chamber, Safety Regulation Testing Equipment, Busbar Quality Inspection Testing System, etc.</p>
              </div>
                <div >
                  <el-table :data="HeaBox_EN" class="custom-table" style="width: 100%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Inspection Items" :align="centerAlign" width="210"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="750">
                        <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Inspection Results"  :align="centerAlign"  width="400">
                          <template #item="{ scope }">
                            <div  class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Individual Conclusions" :align="centerAlign" width="300"/>
                      
                  </el-table>
                </div>
            </div>
        </div>
        <div id="page3Content">
            <div v-if="goods_dev_name !== 'Busway' && radio1 !== '1'">
                <div class="custom-dialog-title" style="display: flex; align-items: center;">
                <div style="padding-left: 60px; padding-top: 30px;">
                    <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
                </div>
                <div style="padding-top: 100px; padding-right: 150px; text-align: center; font-size: 20px; flex: 1;">
                    <p>HUIZHOU CLEVER ELECTRONIC CO.,LTD</p>
                </div>
                </div>
                <div class="horizontal-rule">
                  <hr/>
                </div>
                <br/>
                <br/>
              <div  v-if="goods_dev_name === 'Tap-off box'">
                <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                    <p>Attachment 1: Product Functionality Test</p>
                </div>
                    <div >
                      <el-table :data="JackBox_1_EN" class="custom-table" :span-method="objectSpanMethod" style="width: 100%" border>
                          <el-table-column prop="num" label="" :align="centerAlign" width="120"/>

                          <el-table-column prop="test_item" label="Test Items" :align="centerAlign" width="190"/>

                          <el-table-column prop="test_request" label="Test Requirements" :header-align="centerAlign" width="750">
                            <template #item="{ scope }">
                                <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                            </template>
                          </el-table-column>
                          <el-table-column prop="test_process" label="Test Results"  :align="centerAlign"  width="400">
                              <template #item="{ scope }">
                                <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                            </template>
                          </el-table-column>
                          <el-table-column prop="test_result" label="Individual Conclusions" :align="centerAlign" width="300"/>

                      </el-table>
                    </div>

                  <div style="padding-left: 50px; padding-top: 50px;text-align: left; font-size: 30px; flex: 1;">
                      <p>Attachment 2: Current and Voltage Test Data</p>
                  </div>
                    <div >
                      <el-table :data="JackBox_2" class="custom-table"  style="width: 100%" border>
                          <el-table-column prop="A1" label="Standard Input Current (A)" :align="centerAlign" width="180"/>
                          
                          <el-table-column prop="A2" label="Qualified Display Range" :align="centerAlign" width="190"/>
                          
                          <el-table-column prop="A3" label="Fractional Current" :align="centerAlign" width="200"/>
                          
                          <el-table-column prop="A4" label="Total Current"  :align="centerAlign"  width="190"/>
                          
                          <el-table-column prop="A5" label="Standard Input Voltage (V)" :align="centerAlign" width="180"/>
                          
                          <el-table-column prop="A6" label="Total Voltage"  :align="centerAlign"  width="190"/>
                          
                          <el-table-column label="总电压"  :align="centerAlign"  width="570">
                              <el-table-column prop="L1" label="L1"  :align="centerAlign"  width="200"/>
                              
                              <el-table-column prop="L2" label="L2"  :align="centerAlign"  width="200"/>
                              
                              <el-table-column prop="L3" label="L3"  :align="centerAlign"  width="200"/>
                              
                          </el-table-column>
                      </el-table>
                    </div>
            </div>
              <div  v-else-if="goods_dev_name === 'Feeder box'">
                  <div style="padding-left: 50px; text-align: left; font-size: 30px; flex: 1;">
                    <p>Product Functionality Test</p>
                </div>
                    <div >
                      <el-table :data="HeaBox_1_EN" class="custom-table" :span-method="objectSpanMethod" style="width: 100%" border>
                          <el-table-column prop="num" label="" :align="centerAlign" width="120"/>
                          
                          <el-table-column prop="test_item" label="Test Items" :align="centerAlign" width="190"/>
                          
                          <el-table-column prop="test_request" label="Test Requirements" :header-align="centerAlign" width="750">
                            <template #item="{ scope }">
                                <div class="cell-content-table" v-html="formatSkills(scope.row.test_request)" ></div>
                            </template>
                          </el-table-column>
                          <el-table-column prop="test_process" label="Test Results"  :align="centerAlign"  width="400">
                              <template #item="{ scope }">
                                <div class="cell-content-table" v-html="formatSkills(scope.row.test_process)" ></div>
                            </template>
                          </el-table-column>
                          <el-table-column prop="test_result" label="Individual Conclusions" :align="centerAlign" width="300"/>
      
                      </el-table>
                    </div>

                  <div style="padding-left: 50px; padding-top: 10px;text-align: left; font-size: 30px; flex: 1;">
                      <p>Current and Voltage Test Data</p>
                  </div>
                    <div >
                      <el-table :data="HeaBox_2" class="custom-table"  style="width: 100%" border>
                          <el-table-column prop="A1" label="Standard Input Current (A)" :align="centerAlign" width="220"/>
                          
                          <el-table-column prop="A2" label="Qualified Display Range" :align="centerAlign" width="220"/>
                          
                          <el-table-column prop="A4" label="Fractional Current"  :align="centerAlign"  width="220"/>
                          
                          <el-table-column prop="A5" label="Standard Input Voltage (V)" :align="centerAlign" width="220"/>
                          
                          <el-table-column prop="A6" label="Qualified Display Range"  :align="centerAlign"  width="220"/>
                          
                          <el-table-column label="Total Voltage"  :align="centerAlign"  width="630">
                              <el-table-column prop="L1" label="L1"  :align="centerAlign"  width="210"/>
                              
                              <el-table-column prop="L2" label="L2"  :align="centerAlign"  width="210"/>
                              
                              <el-table-column prop="L3" label="L3"  :align="centerAlign"  width="210"/>
                              
                          </el-table-column>
                      </el-table>
                    </div>
              </div>
              <div  class="column" style="display: flex; justify-content: space-between; font-size: 20px;">
                <div style="padding-left: 80px">
                      <span >OK：Pass, meets the test requirements.</span>
                </div>
                <span>NG：Fail, does not meet the test requirements.</span>
                <div style="padding-right: 80px">
                    <span > NA： Not AppltestStepIndicesicable, the tested product does not have this feature/function.</span>
                </div>
              </div>
          </div>

        </div>
      </div>
    </div>
</template>

<script setup lang="ts">
import { TestDataApi } from '@/api/bus/testData'
import { ElMessageBox } from 'element-plus'
import html2canvas from "html2canvas";
import JsPDF from "jspdf";

defineOptions({ name: 'BusReport' })
const Out_dialogVisible = ref(false);
const goods_SN_data = ref({});
// const goods_product_sn = ref(null);
// const goods_language_select = ref(null);
// const goods_tool_name = ref(null);
// const goods_soft_version = ref(null);
// const goods_test_type = ref(null);
const goods_start_time = ref<Date | null>(null);
const goods_end_time = ref<Date | null>(null);
// const goods_order_id = ref(null);
const goods_order_num = ref<number | null>(null);
const goods_test_num = ref<number | null>(null);
const goods_dev_name = ref<string | null>(null);
// const end_judgment = ref(null);
const centerAlign = 'center';
const fontSize = 60;
// const language = ref(true);
const out_language = ref(true);
const modelNumber = ref('');
const customerName = ref('');
const radio1 = ref('2');
const loading = ref(false) // 加载中
const queryParams = reactive({
  orderId: '' , // 订单号
  productSN: '',// 成品代码
  moduleSN: '', // 模块序列号
})


  //母线槽对外报告信息  -zh
  const Busway =  [
        {
          test_item: '耐高压检测',
          test_request: '对以下部位，施加2500VAC电压5S，不得出现闪络或击穿现象。；1、母线各相之间。；2、母线各相与PE。；3、母线各相与外壳。；',
          test_process: '符合标准要求',
          test_result:'合格'
        }, {
          test_item: '绝缘检测',
          test_request: '对以下部位施加500VDC电压5S后读数，其绝缘电阻值大于10MΩ，才能判为合格。；1、母线各相之间。；2、母线各相与PE。；3、母线各相与外壳。；',
          test_process: '符合标准要求',
          test_result:'合格'
        }, {
          test_item: '产品配置',
          test_request: '将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误；1、产品组件型号与BOM清单一致。；2、产品各组装件，组装位置，组装方向保持一致。；3、对照产品规格书对铝合金外壳、铜排规格进行检查，确认无误。；',
          test_process: '符合标准要求',
          test_result:'合格'
        }, {
          test_item: '外观检验',
          test_request: '检查产品外观，镀层均匀，无混色，无明显划伤、脏污、掉漆、露底、变形、开裂现象,均符合成品外观检验标准。；',
          test_process: '符合标准要求',
          test_result:'合格'
        }, {
          test_item: '产品标识',
          test_request: '1、应有如下标志：额定电流、额定电压、电源性质符号、生产厂或销售商的名称或商标、型号。；2、接地标识明显，位置正确。；3、应按标准规定使用符号。；4、标志应经久耐用，清晰可辨。；',
          test_process: '符合标准要求',
          test_result:'合格'
        }, {
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合标准要求',
          test_result:'合格'
        }
  ]
  //母线槽对外报告信息  -EN
  const Busway_EN = [
        {
          test_item: 'High voltage resistance test',
          test_request: 'Apply 2500VAC voltage for 5S to the following parts, and no flashover or breakdown shall occur;\n' +
              '1. Between the live wires of each phase of the busway;\n' +
              '2. Between the live wires of each phase of the busway and the neutral wire;\n' +
              '3. Between the live wires of each phase of the busway and PE;\n' +
              '4. Between the live wires of each phase of the busway and the shell;',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }, {
          test_item: 'Insulation Detection',
          test_request: 'Apply 500VDC voltage for 5S to the following parts and read the insulation resistance value. the insulation resistance value is more than 10MΩ to be judged as qualified:\n' +
              '1. Between the live wires of each phase of the busway;\n' +
              '2. Between the live wires of each phase of the busway and the neutral wire;\n' +
              '3. Between the live wires of each phase of the busway and PE;\n' +
              '4. Between the live wires of each phase of the busway and the shell;',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }, {
          test_item: 'Product Configuration',
          test_request: 'Check the assembled products with the tracking sheet and process drawings to ensure accurate production:\n' +
              '1. The product component model is consistent with the BOM list;\n' +
              '2. The position and assembly direction of each assembly part of the product are consistent;\n' +
              '3. The product specification is consistent with the specifications of the assembled aluminum alloy shell and copper busbar;',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }, {
          test_item: 'Visual inspection',
          test_request: 'Check the appearance of the product, the coating is uniform, there is no color mixing, no obvious scratches, dirt, paint peeling, exposed bottom, deformation, cracking, and all meet the finished product appearance inspection standards;',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }, {
          test_item: 'Product Identification',
          test_request: '1. The following signs should be present: rated current, rated voltage, power source symbol, name or trademark of the manufacturer or seller, and model number;\n' +
              '2. The grounding mark should be obvious and in the correct position;\n' +
              '3. Symbols should be used in accordance with the standard;\n' +
              '4. The mark should be durable and clearly identifiable;',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }, {
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet standard requirements',
          test_result:'OK'
        }
  ]
    //插接箱对外报告信息 -zh  智能型
  const JackBox = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '① 目测：；看线束是否压接到位，铜丝不能外露超过2mm；焊接是否有虚焊、漏焊、飞丝、松散等问题；内部走线是否整齐、美观。；② 手拉：用规定的拉力拉导线，是否会有松动、脱落现象。；③ 用扳手对螺钉紧固点进行检查，是否符合要求。；④ 对于发现的问题及时进行返工，重新连接。；注：对于无线产品前端有预留接线端子的，要对端子内接线螺钉进行检查。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '① 将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误。；② 对照产品规格书对电缆线、插头规格进行检查，确认无误。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '老化',
          test_request: '将电流表产品置于45℃的高温老化房空载通电老化24小时，结束后转移到25±20C的常温室内空载通电老化24小时。老化中检查显示屏、按键、指示灯是否正常工作和产品有无发生报警。',
          test_process: '显示屏、按键、指示灯正常工作、产品无报警',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '接地电阻检测',
          test_request: '对输入PE端与机壳各接地螺钉之间部位，施加25A电流3S，其接地电阻值小于100mΩ，才能判为合格。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '耐高压检测',
          test_request: '对火零线与地线和外壳之间施加2500VAC电压5S，不得出现闪络或击穿现象。;1、插接箱各相火线之间。；2、插接箱各相火线与PE；3、插接箱各相火线与零线。；',
          test_process: '无闪络或击穿现象',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: '绝缘检测',
          test_request: '对火零线与地线之间施加500VDC电压5S后读数，其绝缘电阻值大于5MΩ，才能判为合格。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示是否正常，判断火、零、地接线是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '功能测试',
          test_request: '对照产品规格书与产品说明书，对产品的各项功能进行测试，确认产品功能正常。',
          test_process: '见附表1',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '产品标识外观',
          test_request: '① 应有如下标志：额定电流、额定电压、生产厂或销售商的名称或商标、型号。；② 应按标准规定使用符号。；③ 标志应经久耐用，清晰可辨。；④ 检查产品外观，有没有混色及明显划伤等问题。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
  ]
  //插接箱对外报告信息 -EN  智能型
  const JackBox_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: '    ①  Visual inspection: See whether the wiring harness is pressed to the position, the copper wire can not be exposed more than 2mm; Whether there are problems such as virtual welding, missing welding, flying wire and loose welding; Whether the internal wiring is neat and beautiful.\n' +
              '    ②  Hand pull: Pull the wire with the specified tension, whether there will be loosening, falling off phenomenon.\n' +
              '    ③  Check the fastening point of the screw with a wrench to see if it meets the requirements.\n' +
              '    ④  If the problem is found, rework and reconnect it in time.\n' +
              'Note: For wireless products with reserved terminals at the front end, check the wiring screws in the terminals.\n',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: '①  Check the assembled products with the tracking list and process drawings to ensure the accuracy of the production;\n' +
              '    ②  Check the cable and plug specifications against the product specifications to confirm that they are correct.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: 'Ageing',
          test_request: 'Place the ammeter product in a high-temperature aging room at 45°C for 24 hours without load and then transfer it to a room temperature room at 25±20°C for 24 hours without load. During the aging process, check whether the display screen, buttons, and indicator lights are working properly and whether the product has any alarm.\n',
          test_process: 'The display screen, buttons and indicator lights are working properly.\n' +
              'There is no alarm on the product.',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: 'Ground resistance detection',
          test_request: 'Apply 25A current for 3S to the area between the input PE terminal and the grounding screws of the casing. The grounding resistance value must be less than 100mΩ to be considered qualified.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: 'High voltage resistance test\n',
          test_request: 'Apply 2500VAC voltage between the live and neutral wires, the ground wire and the housing for 5S, and no flashover or breakdown should occur.',
          test_process: 'No flashover or breakdown',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: 'Insulation testing',
          test_request: 'Apply 500VDC voltage between the live and neutral wires and the ground wire for 5S and read the value. The insulation resistance value must be greater than 5MΩ to be considered qualified.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: 'Polarity check',
          test_request: 'Connect the load to each socket of the product, check whether the indicator light of the polarity test module is normal, and determine whether the live, neutral and ground wiring are correct.\n',
          test_process: 'Meet standard\n' +
              'requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Function test',
          test_request: 'Test the various functions of the product against the product specifications and product manual to confirm that the product functions normally.',
          test_process: 'Appendix 1',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: 'Product logo\n' +
              'Appearance',
          test_request: '① The following markings should be present: rated current, rated voltage, name or trademark of the manufacturer or seller, and model number;\n' +
              '    ② Symbols should be used as specified in the standard;\n' +
              '    ③ The logo should be durable and legible;\n' +
              '    ④ Check the appearance of the product to see if there are any problems such as color mixing and obvious scratches.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
  ]
  //插接箱对外报告信息 -zh  基本型
  const JackBox_jb = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '① 目测：；看线束是否压接到位，铜丝不能外露超过2mm；焊接是否有虚焊、漏焊、飞丝、松散等问题；内部走线是否整齐、美观。；② 手拉：用规定的拉力拉导线，是否会有松动、脱落现象。；③ 用扳手对螺钉紧固点进行检查，是否符合要求。；④ 对于发现的问题及时进行返工，重新连接。；注：对于无线产品前端有预留接线端子的，要对端子内接线螺钉进行检查。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '① 将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误。；② 对照产品规格书对电缆线、插头规格进行检查，确认无误。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '接地电阻检测',
          test_request: '对输入PE端与机壳各接地螺钉之间部位，施加25A电流3S，其接地电阻值小于100mΩ，才能判为合格。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '耐高压检测',
          test_request: '对火零线与地线和外壳之间施加2500VAC电压5S，不得出现闪络或击穿现象。;1、插接箱各相火线之间。；2、插接箱各相火线与PE；3、插接箱各相火线与零线。；',
          test_process: '无闪络或击穿现象',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '绝缘检测',
          test_request: '对火零线与地线之间施加500VDC电压5S后读数，其绝缘电阻值大于5MΩ，才能判为合格。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示是否正常，判断火、零、地接线是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '断路器检查',
          test_request: '依次通断断路器，断路器断开，对应的绿色指示灯灭，对应插座无输出，断路器闭合，对应绿色指示灯亮，对应插座输出正常。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '产品标识外观',
          test_request: '① 应有如下标志：额定电流、额定电压、生产厂或销售商的名称或商标、型号。；② 应按标准规定使用符号。；③ 标志应经久耐用，清晰可辨。；④ 检查产品外观，有没有混色及明显划伤等问题。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
  ]
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
  //插接箱对外报告信息  附件2 -zh
  const JackBox_2 = [
          {
            A1:'0.0',
            A2:'0.0~0.1',
            A3:'OK',
            A4:'OK',
            A5:'220',
            A6:'215~225',
            L1:'OK',
            L2:'OK',
            L3:'OK',
          },
          {
            A1:'0.2',
            A2:'0.1~0.3',
            A3:'OK',
            A4:'OK',
            A5:'',
            A6:'',
            L1:'',
            L2:'',
            L3:'',
          },
          {
            A1:'8.0',
            A2:'7.8~8.2',
            A3:'OK',
            A4:'OK',
            A5:'',
            A6:'',
            L1:'',
            L2:'',
            L3:'',
          },
  ]
   //始端箱对外报告信息 -zh 智能型
  const HeaBox = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '① 目测：；看线束是否压接到位，铜丝不能外露超过2mm。；焊接是否有虚焊、漏焊、飞丝、松散等问题。；内部走线是否整齐、美观。；② 手拉：用规定的拉力拉导线，是否会有松动、脱落现象；③ 用扳手对螺钉紧固点进行检查，是否符合要求。；注：对于无线产品前端有预留接线端子的，要对端子内接线螺钉进行检查。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '① 将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误。；② 对照产品规格书对电缆线、插头规格进行检查，确认无误。；③ 对于结构的搭接、零部件和电器元件的保护接地应装有专用的接地垫圈或采取其它措施，且连接良好，有明显的接地标志。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '老化',
          test_request: '将始端箱电流表置于45℃的高温老化房空载通电老化24小时，结束后转移到25±5℃的常温室内空载通电老化24小时。；老化中检查显示屏、按键、指示灯是否正常工作和产品有无发生报警。',
          test_process: '显示屏、按键、指示灯正常工作、产品无报警',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '接地电阻检测',
          test_request: '对输入PE端与机壳各接地螺钉之间部位，施加25A电流3S，其接地电阻值小于100mΩ，才能判为合格。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '耐高压检测',
          test_request: '对火零线与地线和外壳之间施加2500VAC电压5S，不得出现闪络或击穿现象。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '无闪络或击穿现象',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: '绝缘检测',
          test_request: '对火零线与地线之间施加500VDC电压5S后读数，其绝缘电阻值大于5MΩ，才能判为合格。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示是否正常，判断火、零、地接线是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '功能测试',
          test_request: '对照产品规格书与产品说明书，对产品的各项功能进行测试，确认产品功能正常。',
          test_process: '见附表1',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '产品标识外观',
          test_request: '① 应有如下标志：额定电流、额定电压、生产厂或销售商的名称或商标、型号。；② 应按标准规定使用符号。；③ 标志应经久耐用，清晰可辨。；④ 检查产品外观，有没有混色及明显划伤等问题。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
  ]
  //始端箱对外报告信息 -EN 智能型
  const HeaBox_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: '    ①  Visual inspection: Check whether the wire harness is crimped in place, and the copper wire cannot be exposed more than 2mm; whether there are problems such as cold soldering, leaking soldering, flying wires, looseness, etc.; whether the internal wiring is neat and beautiful;\n' +
              '    ②  Hand pulling: Pull the wire with the specified pulling force to see if it will loosen or fall off;\n' +
              '    ③  Use a wrench to check the screw fastening points to see if they meet the requirements.\n' +
              'Note: For wireless products with reserved wiring terminals at the front end, the wiring screws inside the terminals must be checked.\n',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: '    ① Check the assembled products with the tracking sheet, process drawings, and wiring diagrams to ensure that the assembly is accurate;\n' +
              '    ② Check the specifications of the circuit breaker and copper busbar against the product specifications to confirm that they are correct.\n' +
              '    ③ For the overlapping of structures, protective grounding of parts and electrical components, special grounding washers or other measures should be installed, and the connection should be good and there should be obvious grounding marks;\n',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: 'Ageing',
          test_request: 'Place the feeder box ammeter in a high-temperature aging room at 45°C for 24 hours without load and then transfer it to a room at 25±50°C for 24 hours without load.\n' +
              'During the aging process, check whether the display, buttons, and indicator lights are working properly and whether the product has any alarm.',
          test_process: 'The display screen, buttons and indicator lights are working properly. There is no alarm on the product.',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: 'Ground resistance detection',
          test_request: 'Apply 25A current for 3S to the area between the input PE terminal and the grounding screws of the casing. The grounding resistance value must be less than 100mΩ to be considered qualified.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: 'High voltage resistance test',
          test_request: 'Apply 2500VAC voltage between the live and neutral wires, the ground wire and the casing for 5S, and no flashover or breakdown should occur.',
          test_process: 'No flashover or breakdown',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: 'Insulation testing',
          test_request: 'Apply 500VDC voltage between the live and neutral wires and the ground wire for 5S and read the value. The insulation resistance value must be greater than 10MΩ to be considered qualified.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: 'Polarity check',
          test_request: 'Connect the product to the load, check whether the indicator light of the polarity test module is normal, and determine whether the live, neutral and ground wiring are correct.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Function test',
          test_request: 'Test the various functions of the product against the product specifications and product manual to confirm that the product functions normally.\n',
          test_process: 'See Appendix 1',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: 'Product logo and appearance',
          test_request: '    ① The following markings should be present: rated current, rated voltage, name or trademark of the manufacturer or seller, and model number;\n' +
              '    ② Symbols should be used as specified in the standard;\n' +
              '    ③ The logo should be durable and legible;；\n' +
              '    ④ Check the appearance of the product to see if there are any problems such as color mixing and obvious scratches;\n',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet standard requirements',
          test_result:"OK"
        },
  ]
  //始端箱对外报告信息 -zh 基本型
  const HeaBox_jb = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '① 目测：；看线束是否压接到位，铜丝不能外露超过2mm。；焊接是否有虚焊、漏焊、飞丝、松散等问题。；内部走线是否整齐、美观。；② 手拉：用规定的拉力拉导线，是否会有松动、脱落现象；③ 用扳手对螺钉紧固点进行检查，是否符合要求。；注：对于无线产品前端有预留接线端子的，要对端子内接线螺钉进行检查。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '① 将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误。；② 对照产品规格书对电缆线、插头规格进行检查，确认无误。；③ 对于结构的搭接、零部件和电器元件的保护接地应装有专用的接地垫圈或采取其它措施，且连接良好，有明显的接地标志。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '接地电阻检测',
          test_request: '对输入PE端与机壳各接地螺钉之间部位，施加25A电流3S，其接地电阻值小于100mΩ，才能判为合格。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '耐高压检测',
          test_request: '对火零线与地线和外壳之间施加2500VAC电压5S，不得出现闪络或击穿现象。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '无闪络或击穿现象',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '绝缘检测',
          test_request: '对火零线与地线之间施加500VDC电压5S后读数，其绝缘电阻值大于5MΩ，才能判为合格。;1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示是否正常，判断火、零、地接线是否正确。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '断路器检查',
          test_request: '断路器合闸，绿色指示灯亮；\n' +
              '断路器跳闸，红色指示灯亮、蜂鸣器蜂鸣；\n' +
              '断路器分闸，绿色、红色指示灯都不亮；',
          test_process: '符合标准要求',
          test_result:"OK"
        },{
          num:'8',
          test_item: '产品标识外观',
          test_request: '① 应有如下标志：额定电流、额定电压、生产厂或销售商的名称或商标、型号。；② 应按标准规定使用符号。；③ 标志应经久耐用，清晰可辨。；④ 检查产品外观，有没有混色及明显划伤等问题。',
          test_process: '符合标准要求',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合标准要求',
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
  //始端箱对外报告信息  附件2 -zh
  const HeaBox_2 = [
          {
            A1:'0.0',
            A2:'0.0~0.1',
            A4:'OK',
            A5:'220',
            A6:'215~225',
            L1:'OK',
            L2:'OK',
            L3:'OK',
          },
          {
            A1:'0.2',
            A2:'0.1~0.3',
            A4:'OK',
            A5:'',
            A6:'',
            L1:'',
            L2:'',
            L3:'',
          },
          {
            A1:'8.0',
            A2:'7.8~8.2',
            A4:'OK',
            A5:'',
            A6:'',
            L1:'',
            L2:'',
            L3:'',
          },
  ]

  const formattedGoodsEndTime = (time) =>{
    if (time != null) {
        let date = new Date(time); 
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
      }
      return ''
    }
  
  // 使用正则表达式同时匹配中英文分号
  function formatSkills(skills): string{
      if (skills) {
        return skills.replace(/[;；]/g, '<br/>');
      }
      return '';
    }

  function groupNum(): Set<string> | string {
      if (Out_dialogVisible.value && goods_dev_name.value === '插接箱' && Array.isArray(JackBox_1)) {
        return new Set(JackBox_1.map((o) => o.num));
      } else if (Out_dialogVisible.value && goods_dev_name.value === '始端箱' && Array.isArray(HeaBox_1)) {
        return new Set(HeaBox_1.map((o) => o.num));
      } else if (Out_dialogVisible.value && goods_dev_name.value === 'Tap-off box' && Array.isArray(JackBox_1_EN)) {
        return new Set(JackBox_1_EN.map((o) => o.num));
      } else if (Out_dialogVisible.value && goods_dev_name.value === 'Feeder box' && Array.isArray(HeaBox_1_EN)) {
        return new Set(HeaBox_1_EN.map((o) => o.num));
      } else if (Array.isArray(goods_SN_data.value)) {
        return new Set(goods_SN_data.value.map((o) => o.test_step));
      } else {
        return '0';
      }
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
      else if(Array.isArray(goods_SN_data))
      {
        return goods_SN_data.filter((o) => o.test_step == testStep).length;
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

  const updateLanguage = () => {
    if(out_language.value !== true)
      {
        if(goods_dev_name.value == '母线槽'){
            goods_dev_name.value = 'Busway';
            console.log(goods_dev_name)
        }
        else if(goods_dev_name.value == '始端箱'){
            goods_dev_name.value = 'Feeder box';
        }
        else if(goods_dev_name.value == '插接箱'){
            goods_dev_name.value = 'Tap-off box';
        }
      }
    else {
        if(goods_dev_name.value == 'Busway')
        {
            goods_dev_name.value = '母线槽'
        }
        else if(goods_dev_name.value == 'Feeder box')
        {
            goods_dev_name.value = '始端箱'
        }
        else if(goods_dev_name.value == 'Tap-off box')
        {
            goods_dev_name.value = '插接箱'
        }
    }
  }

      //对外导出的pdf
  const OutExportToPDF = () =>{
  // 获取第一页内容
    const page1Element = document.getElementById('page1Content');

    // 创建新的 PDF 实例
    const PDF = new JsPDF('', 'pt', 'a4');

    // 处理每一页的通用函数
    const processPage = (element, pageNumber) => {
      // 更新替代元素
      return html2canvas(element)
        .then(canvas => {
          const contentWidth = canvas.width;
          const contentHeight = canvas.height;
          const pageHeight = (contentWidth / 592.28) * 841.89;
          let position = 0;

          // 添加页面（如果需要处理多页，这里可能需要调整）
          if (pageNumber > 1) {
            PDF.addPage();
          }

          while (position < contentHeight) {
            let remainingHeight = pageHeight;
            if (position + remainingHeight > contentHeight) {
              remainingHeight = contentHeight - position;
            }

            const tempCanvas = document.createElement('canvas');
            tempCanvas.width = contentWidth;
            tempCanvas.height = remainingHeight;
            const context = tempCanvas.getContext('2d');
            context?.drawImage(canvas, 0, position, contentWidth, remainingHeight, 0, 0, contentWidth, remainingHeight);

            const imageData = tempCanvas.toDataURL('image/jpeg', 1.0);
            PDF.addImage(imageData, 'JPEG', 0, 0, 595.28, (592.28 / contentWidth) * remainingHeight);

            position += remainingHeight;
          }
          return canvas;
        });
    };

    // 处理第一页
    processPage(page1Element, 1)
      .then(() => {
        // 假设我们有第二页内容需要处理，这里的ID应替换为实际的第二页元素ID
        const page2Element = document.getElementById('page2Content');
        return processPage(page2Element, 2); // 处理第二页
      })
      .then(() => {
        // 在这里检查条件
        if (goods_dev_name.value === '插接箱' || goods_dev_name.value === '始端箱' || goods_dev_name.value  === 'Feeder box' || goods_dev_name.value === 'Tap-off box') {
          // 处理第三页
          const page3Element = document.getElementById('page3Content');
          return processPage(page3Element, 3);
        } else {
          // 如果条件不满足，则跳过第三页，直接进行后续操作
          return Promise.resolve();
        }
      })
      .then(() => {
        // 完成后保存PDF
        let fileName;
        if(out_language.value  === true) {
            if(radio1.value  === '1'  && goods_dev_name.value  !== '母线槽')
            {
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name}-基本型出厂报告.pdf`;
            }
            else if(radio1.value === '2'  && goods_dev_name.value !== '母线槽')
            {
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name}-智能出厂报告.pdf`;
            }
            else{
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name}出厂报告.pdf`;
            }
        } else if(out_language.value === false) {
            if(radio1.value === '1'  && goods_dev_name.value !== 'Busway')
            {
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name}-base Ex-factory Report.pdf`;
            }
            else if(radio1.value === '2'  && goods_dev_name.value !== 'Busway')
            {
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name}-smart Ex-factory Report.pdf`;
            }
            else{
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name} Ex-factory Report.pdf`;
            }
        }
        PDF.save(fileName);
      })
      .catch(error => {
        console.error('导出PDF时出错：', error);
      });
    }
  const out_handleExport = () =>{
      if (!modelNumber || !customerName) {
          alert('请确保所有输入框都已填写！');
        } else {
          // const loading = loading({
          //   lock: true,
          //   text: '正在导出PDF...',
          //   spinner: 'el-icon-loading',
          //   background: 'rgba(0, 0, 0, 0.7)'
          // });
          loading.value = true;
          OutExportToPDF();
          setTimeout(() => {
            loading.value = false;
          }, 2000);
        }
    }

  // 获取出差报告数据
  const getReportData = async () => {
    loading.value = true;
    try {
      const res = await TestDataApi.getReportData(queryParams);
      if (res != null){
        goods_dev_name.value = res.devName
        customerName.value = res.customerName
        modelNumber.value = res.deviceType
        goods_start_time.value = res.productionDate
        goods_end_time.value = res.testDate
        goods_order_num.value = res.productionNum
        goods_test_num.value = res.passTestNum
        
      }
  } finally {
    loading.value = false;

    if (goods_order_num.value != null && goods_test_num.value != null && goods_order_num.value != goods_test_num.value){
      ElMessageBox.alert('检验暂未完成，生产数量：'+ goods_order_num.value + '台，当前检验数量：'+ goods_test_num.value + '台', 
      '订单号：'+ queryParams.orderId, 
      {
        showClose:false,
        showConfirmButton: false,
        showCancelButton: false,
      })
    }
    
  }
}

onMounted(() => { 
  const queryOrderId = useRoute().query.orderId as string;
  const queryProductSN = useRoute().query.productSN as string;
  const queryModuleSN = useRoute().query.moduleSN as string;

  if (queryOrderId == '' || queryProductSN == '' || queryModuleSN == ''){
    ElMessageBox.alert('出错了，数据缺失（订单号、成品代码、模块序列号）', 
      'Error', 
      {
        showClose:false,
        showConfirmButton: false,
        showCancelButton: false,
      })
      
  }else{
    queryParams.orderId = queryOrderId;
    queryParams.productSN = queryProductSN;
    queryParams.moduleSN = queryModuleSN;
    getReportData(); 
  }

})

    
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