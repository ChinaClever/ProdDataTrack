<template>
    <div style="width: 100%; height: 100%; overflow: auto;">
      <div class="custom-button"  style="padding-left: 50px;">
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
              <div style="padding-left: 5%; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 11%; text-align: center; font-size: 20px; flex: 1;">
                  <p>惠州市克莱沃电子有限公司 </p>
              </div>
          </div>
          <div class="horizontal-rule">
          <hr/>
          </div>
          <!-- <div style="padding-right: 5%;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">第1页，共2页</p>
          </div> -->
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
              <div v-if="goods_dev_name !== '母线槽'" class="column left-column">
                <p v-if="radio2  ">产品名称: {{ goods_dev_name + '（智能型）'}} </p>
                <p v-if="!radio2 ">产品名称: {{ goods_dev_name + '（基本型）'}} </p>
                <p >规格型号: {{modelNumber}}</p>
                <p >生产日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >检验日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div v-if="goods_dev_name === '母线槽'" class="column left-column">
                <p >产品名称: {{ goods_dev_name }} </p>             
                <p >规格型号: {{modelNumber}}</p>
                <p >生产日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >检验日期: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div class="column right-column">
                <p >客户名称: {{customerName}}</p>
                <p >检验类别: 出厂检验</p>
                <p >生产数量: {{goods_order_num}}台</p>
                <p >检验数量: {{goods_test_num}}台</p>
              </div>
            </div>
          </div>
          <div class="br-rule2">
            <br/>
          </div>
          <div style="text-align: center; font-size: 40px;">
              <p>检验依据：GB/T7251.6-2015 标准技术要求和产品使用说明书</p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p> 检 验 结 论 </p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>该批产品检验，所检项目符合 GB/T7251.6-2015 标准技术要求和产品使用说明书要求。</p>
          </div>
          <div style="height: 6cap">
              <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 15%;">
              <p>说明：</p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 15%;">
              <p>1、产品特性及状态： 正常</p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 15%;">
              <p>2、检验环境条件：  （25±5）℃</p>
          </div>
          <div style="height: 20px">
            <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 30px">
            <br/>
          </div>
          <div style="text-align: right; font-size: 20px; padding-right: 5%;">
            <p>
              <span style="display: inline-block; margin-right: 50px;">年</span>
              <span style="display: inline-block; margin-right: 50px;">月</span>
              <span>日</span>
            </p>
          </div>
          <div style="height: 40px">
            <br/>
          </div>
          <div  class="column" style="display: flex; justify-content: space-between; font-size: 40px;">
            <div style="padding-left: 5%">
                  <span >检验员：</span>
            </div>
            <span>审核：</span>
            <div style="padding-right: 15%">
                <span >批准：</span>
            </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
        </div>
        <div id="page2Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
            <div style="padding-left: 5%; padding-top: 30px;">
                  <img src="@/assets/logo1.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 11%; text-align: center; font-size: 20px; flex: 1;">
                  <p>惠州市克莱沃电子有限公司 </p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <!-- <div style="padding-right: 5%;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">第2页，共2页</p>
          </div> -->

          <br/>
          <p v-if="out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">检 验 报 告</p>
          <p v-if="!out_language " :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <br/>
            <div v-if="goods_dev_name === '母线槽'" style="padding-left: 6%;">
              <!--母线槽的报告排版-->
                <div >
                  <el-table :data="Busway" class="custom-table" style="width: 95%" border>
                      <el-table-column  prop="test_item" label="检验项目" :align="centerAlign" width="299"/>
                      <el-table-column prop="test_request" label="标准要求" :header-align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="202"/>
                  </el-table>
                </div>
            </div>

            <div  v-else-if="goods_dev_name === '插接箱'" style="padding-left: 6%;">             
                <div v-if="radio1 === '2'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：高温老化房、安规测试设备、母线质检测试系统</p>
                </div>
                  <el-table :data="JackBox" class="custom-table" style="width: 95%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="200"/>
                      <el-table-column  prop="test_request" label="标准要求" :align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="299">
                          <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="202"/>
                  </el-table>
                </div>
                <div v-else-if="radio1 === '1'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：安规测试设备、母线质检测试系统</p>
              </div>
                  <el-table :data="JackBox_jb" class="custom-table" style="width: 95%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="200"/>
                      <el-table-column  prop="test_request" label="标准要求" :align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="299">
                          <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="202"/>
                  </el-table>
                </div>
            </div>
            <div  v-else-if="goods_dev_name === '始端箱'" style="padding-left: 6%;">
                <div v-if="radio1 === '2'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：高温老化房、安规测试设备、母线质检测试系统等</p>
                </div>
                  <el-table :data="HeaBox" class="custom-table" style="width: 95%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="200"/>
                      <el-table-column  prop="test_request" label="标准要求" :align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="299">
                          <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="202"/>
                  </el-table>
                </div>
                <div v-if="radio1 === '1'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>检验仪器：安规测试设备、母线质检测试系统等</p>
                </div>
                  <el-table :data="HeaBox_jb" class="custom-table" style="width: 95%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      <el-table-column prop="test_item" label="检验项目" :align="centerAlign" width="200"/>
                      <el-table-column  prop="test_request" label="标准要求" :header-align="centerAlign" width="799">
                        <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="检验结果"  :align="centerAlign"  width="400">
                          <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="单项结论" :align="centerAlign" width="202"/>
                  </el-table>
                </div>
       
            </div>

        </div>
      </div>       

      <div v-else-if="out_language == false">
        <div id="page1Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
              <div style="padding-left: 5%; padding-top: 30px;">
                  <img src="@/assets/logo2.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 27%; text-align: center; font-size: 20px; flex: 1;">
                  <p>Clever Electronic </p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <!-- <div style="padding-right: 5%;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">Page 1 of 2</p>
          </div> -->
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
              <div v-if="goods_dev_name !== 'Busway'" class="column left-column" style="padding-left: 10%;">
                <p v-if="radio2 ">Product Name: {{ goods_dev_name +'(Intelligent)'}}</p>
                <p v-if="!radio2 ">Product Name: {{ goods_dev_name +'(Basic)'}}</p>
                <p >Specification Model: {{modelNumber}}</p>
                <p >Production Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >Inspection Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div v-if="goods_dev_name === 'Busway'" class="column left-column" style="padding-left: 10%;">
                <p >Product Name: {{ goods_dev_name }}</p>              
                <p >Specification Model: {{modelNumber}}</p>
                <p >Production Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
                <p >Inspection Date: {{ formattedGoodsEndTime(goods_end_time) }}</p>
              </div>
              <div class="column right-column" style="padding-right: 5%;">
                <p >Customer Name: {{ customerName }}</p>
                <p >Inspection Category: Factory Inspection</p>
                <p >Production Quantity: {{goods_order_num}}PCS</p>
                <p >Inspection Quantity: {{goods_test_num}}PCS</p>
              </div>
            </div>
          </div>
          <div class="br-rule2">
            <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>Inspection basis: IEC 61439-6: 2012 standard technical requirements and product instructions</p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p> Test Conclusion </p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div  style="text-align: center; font-size: 40px;">
              <p>This batch of products was inspected and the items tested were in compliance with the technical requirements of IEC 61439-6: 2012  standard and the requirements of the product instructions.</p>
          </div>
          <div style="height: 20px">
              <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 10px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 10%;">
              <p>Illustrate:</p>
          </div>
          <div style="height: 10px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 10%;">
              <p>1.Product Characteristics and Status: Normal</p>
          </div>
          <div style="height: 10px">
              <br/>
          </div>
          <div  style="text-align: left; font-size: 40px; padding-left: 10%;">
              <p>2. Test environment conditions: (25±5)℃</p>
          </div>
          <div style="height: 3cqmax">
            <br/>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <div style="height: 10px">
            <br/>
          </div>
          <div style="text-align: right; font-size: 20px; padding-right: 5%;">
            <p>
              <span>Date: _______/_______/_______</span>
            </p>
          </div>
          <div style="height: 10px">
            <br/>
          </div>
          <div  class="column" style="display: flex; justify-content: space-between; font-size: 40px;">
            <div style="padding-left: 5cm">
                  <span >Inspector:</span>
            </div>
            <span>Audit:</span>
            <div style="padding-right: 15%">
                <span >Approve: </span>
            </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
        </div>
        <div id="page2Content">
          <div class="custom-dialog-title" style="display: flex; align-items: center;">
            <div style="padding-left: 5%; padding-top: 30px;">
                  <img src="@/assets/logo2.png" alt="左上角图片" style="height: 100px; width: auto; margin-right: 10px;"/>
              </div>
              <div style="padding-top: 100px; padding-right: 27%; text-align: center; font-size: 20px; flex: 1;">
                  <p>Clever Electronic</p>
              </div>
          </div>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <!-- <div style="padding-right: 5%;">
              <p :style="{ textAlign: 'right', fontSize: 20 + 'px' }">Page 2 of 2</p>
          </div> -->
          <br/>
          <p :style="{ textAlign: 'center', fontSize: fontSize + 'px' }">TEST REPORT</p>
          <div class="horizontal-rule">
            <hr/>
          </div>
          <br/>
            <div v-if="goods_dev_name === 'Busway'" style="padding-left: 6%;">
              <!--母线槽的报告排版-->
                <div >
                  <el-table :data="Busway_EN" class="custom-table" style="width: 95%" border>
                      <el-table-column  prop="test_item" label="Test items" :align="centerAlign" width="350"/>
                      
                      <el-table-column prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Test result"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Single conclusion" :align="centerAlign" width="150"/>
                      
                  </el-table>
                </div>
            </div>
            <div  v-else-if="goods_dev_name === 'Tap-off box'" style="padding-left: 6%;">
                <div v-if="radio1 === '2'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection instruments: high temperature aging room, safety test equipment, busbar quality inspection and testing system, etc.</p>
                </div>
                  <el-table :data="JackBox_EN" class="custom-table" style="width: 97%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Test items" :align="centerAlign" width="250"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Test result"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Single conclusion" :align="centerAlign" width="187"/>
                  </el-table>
                </div>
                  <div v-if="radio1 === '1'">
                    <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection instruments: safety test equipment, busbar quality inspection and testing system, etc.</p>
                </div>
                  <el-table :data="JackBox_jb_EN" class="custom-table" style="width: 97%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Test items" :align="centerAlign" width="250"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Test result"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Single conclusion" :align="centerAlign" width="187"/>
                      
                  </el-table>
                
                </div>
            </div>
            <div  v-else-if="goods_dev_name === 'Feeder box'" style="padding-left: 6%;">              
              <div v-if="radio1 === '2'">
                <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection instruments: high temperature aging room, safety test equipment, busbar quality inspection and testing system, etc.</p>
              </div>
                  <el-table :data="HeaBox_EN" class="custom-table" style="width: 97%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Test items" :align="centerAlign" width="250"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Test result"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Single conclusion" :align="centerAlign" width="187"/>
                      
                  </el-table>
                </div>
                <div v-if="radio1 === '1'">
                  <div style="padding-left: 10px; text-align: left; font-size: 30px; flex: 1;">
                  <p>Inspection instruments: safety test equipment, busbar quality inspection and testing system, etc.</p>
              </div>
                  <el-table :data="HeaBox_jb_EN" class="custom-table" style="width: 97%" border>
                      <el-table-column prop="num" label="No." :align="centerAlign" width="100"/>
                      
                      <el-table-column prop="test_item" label="Test items" :align="centerAlign" width="250"/>
                      
                      <el-table-column  prop="test_request" label="Standard Requirements" :header-align="centerAlign" width="900">
                        <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_request)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_process" label="Test result"  :align="centerAlign"  width="300">
                          <template #default="{ row }">
                            <div  class="cell-content-table" v-html="formatSkills(row.test_process)" ></div>
                        </template>
                      </el-table-column>
                      <el-table-column prop="test_result" label="Single conclusion" :align="centerAlign" width="187"/>
                      
                  </el-table>
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
const Out_dialogVisible = ref(true);
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
const radio2 = ref(false);
const loading = ref(false) // 加载中
const queryParams = reactive({
  orderId: '' , // 订单号
  productSN: '',// 成品代码
  moduleSN: '', // 模块序列号
})


  //母线槽对外报告信息  -zh
  const Busway =  [
        {
          test_item: '外观检验',
          test_request: '检查产品外观，镀层均匀，无混色，无明显划伤、脏污、掉漆、露底、变形、开裂现象,均符合成品外观检验标准。',
          test_process: '符合要求',
          test_result:'OK'
        }, {
          test_item: '产品配置',
          test_request: '将装配完的产品与跟踪单、工艺图纸进核对，确保制作准确无误：；；1、产品组件型号与BOM清单一致；2、产品各组装件位置，组装方向保持一致；3、产品规格书与组装铝合金外壳、铜排规格一致；',
          test_process: '符合要求',
          test_result:'OK'
        }, {
          test_item: '产品标识',
          test_request: '产品标识正确、清晰。；',
          test_process: '符合要求',
          test_result:'OK'
        }, {
          test_item: '绝缘检测',
          test_request: '参照标准要求，对火零线与外壳之间进行绝缘测试，其绝缘电阻值大于10MΩ，才能判为合格。；',
          test_process: '符合要求',
          test_result:'OK'
        }, {
          test_item: '耐高压检测',
          test_request: '参照标准要求，对火零线与外壳之间进行耐压测试， 不得出现闪络或击穿现象。；',
          test_process: '符合要求',
          test_result:'OK'
        } ,{
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合要求',
          test_result:'OK'
        }
  ]
  //母线槽对外报告信息  -EN
  const Busway_EN = [
        {
          test_item: 'Visual inspection',
          test_request: 'Check the appearance of the product, the coating is uniform, there is no color mixing, no obvious scratches, dirt, paint peeling, exposed bottom, deformation, cracking, and all meet the finished product appearance inspection standards.',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }, {
          test_item: 'Product Configuration',
          test_request: 'Check the assembled products with the tracking sheet and process drawings to ensure accurate production:;;' +
              '1. The product component model is consistent with the BOM list.;' +
              '2. The position and assembly direction of each assembly part of the product are consistent.;' +
              '3. The product specification is consistent with the specifications of the assembled aluminum alloy shell and copper busbar.;',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }, {
          test_item: 'Product Identification',
          test_request: 'Product labels are correct and clear.',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }, {
          test_item: 'Insulation Detection',
          test_request: 'Referring to the standard requirements, conduct an insulation test between the live neutral wire and the shell. Only when the insulation resistance value is greater than 10MΩ can it be judged qualified.;',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }, {
          test_item: 'High voltage resistance test',
          test_request: 'Referring to the standard requirements, conduct a voltage withstand test between the fire neutral line and the outer casing, and no flashover or breakdown should occur.;',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }, {
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet the requirements',
          test_result:'OK'
        }
  ]
    //插接箱对外报告信息 -zh  智能型
  const JackBox = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '参考接线图纸要求，目测内部走线正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '对照产品规格书，产品装配配置正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '老化',
          test_request: '电流表老化24小时过程中正常工作, 没有发生报警。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '接地电阻检测',
          test_request: 'PE端与机壳接地螺钉之间，接地电阻值应小于100mΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },         
          {
          num:'5',
          test_item: '绝缘检测',
          test_request: '参照标准要求，火零线与地线之间绝缘测试，其绝缘电阻值应≥10MΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
        {
          num:'6',
          test_item: '耐高压检测',
          test_request: '参照标准要求，火零线与地线和外壳之间耐压测试，不得出现闪络或击穿现象。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示应正常，火、零、地接线应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '功能测试',
          test_request: '对照产品规格书与产品说明书，对产品的各项功能进行测试，确认产品功能正常。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '产品标识外观',
          test_request: '产品标识正确、清晰，产品外观无混色及明显划伤等。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
  ]
  //插接箱对外报告信息 -EN  智能型
  const JackBox_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: 'Refer to the wiring drawing requirements and visually check that the internal wiring is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: 'According to the product specification, the product assembly configuration is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: 'Ageing',
          test_request: 'The ammeter worked normally during the 24-hour aging process and no alarm occurred.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: 'Ground resistance detection',
          test_request: 'The grounding resistance between the PE terminal and each grounding screw of the chassis should be less than 100mΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
        {
          num:'5',
          test_item: 'Insulation testing',
          test_request: 'According to the standard requirements, the insulation resistance value of the insulation test between the live neutral wire and the ground wire should be ≥10MΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: 'High voltage resistance test',
          test_request: 'According to the standard requirements, the voltage test between the live and neutral wires, the ground wire and the casing shall not show flashover or breakdown.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          
          {
          num:'7',
          test_item: 'Polarity check',
          test_request: 'Connect the load to each socket of the product, check that the indicator light of the polarity test module should be normal, and the live, neutral and ground wiring should be correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Function test',
          test_request: 'Test the various functions of the product against the product specifications and product manual to confirm that the product functions normally.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: 'Product logo Appearance',
          test_request: 'The product labeling is correct and clear, and the product appearance has no mixed colors or obvious scratches.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
  ]
  //插接箱对外报告信息 -zh  基本型
  const JackBox_jb = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '参考接线图纸要求，目测内部走线正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '对照产品规格书，产品装配配置正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '接地电阻检测',
          test_request: 'PE端与机壳接地螺钉之间，接地电阻值应小于100mΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
        {
          num:'4',
          test_item: '绝缘检测',
          test_request: '参照标准要求，火零线与地线之间绝缘测试，其绝缘电阻值应≥10MΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '耐高压检测',
          test_request: '参照标准要求，火零线与地线和外壳之间耐压测试，不得出现闪络或击穿现象。', //；1、插接箱各相火线之间。；2、插接箱各相火线与PE。；3、插接箱各相火线与零线。；
          test_process: '符合要求',
          test_result:"OK"
        }, 
          {
          num:'6',
          test_item: '极性检查',
          test_request: '产品各插座接入负载，检查极性测试模块指示灯显示应正常，火、零、地接线应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: '产品标识外观',
          test_request: '产品标识正确、清晰，产品外观无混色及无明显划伤等。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号是否正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
  ]
  //插接箱对外报告信息 -EN  基本型
  const JackBox_jb_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: ' Refer to the wiring drawing requirements and visually check that the internal wiring is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: 'According to the product specification, the product assembly configuration is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },

          {
          num:'3',
          test_item: 'Ground resistance detection',
          test_request: 'According to the product specification, the product assembly configuration is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
        {
          num:'4',
          test_item: 'Insulation testing',
          test_request: 'According to the standard requirements, the insulation resistance value of the insulation test between the live neutral wire and the ground wire should be ≥10MΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: 'High voltage resistance test\n',
          test_request: 'According to the standard requirements, the voltage test between the live and neutral wires, the ground wire and the casing shall not show flashover or breakdown.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          
          {
          num:'6',
          test_item: 'Polarity check',
          test_request: 'Connect the load to each socket of the product, check that the indicator light of the polarity test module should be normal, and the live, neutral and ground wiring should be correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },       
          {
          num:'7',
          test_item: 'Product logo\n' +
              'Appearance',
          test_request: 'The product labeling is correct and clear, and the product appearance has no mixed colors or obvious scratches.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
  ]



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
          test_request: '功能按键：设置功能键（进入设置模式，设置后保存退出）。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'UP按键：向上翻页功能键，翻页查看显示屏内容（设置模式时为向上设置功能）。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'DOWN按键：向下翻页功能键，翻页查看显示屏内容（设置模式时长按为定位功能，短按为向下设置功能）。',
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
          test_request: 'IN、OUT: 级联治具连接插接箱IN和OUT接口，通讯正常。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电压检查',
          test_request: '分别通断各插座对应的回路断路器，对应回路电压数据一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电流检查',
          test_request: '接入负载，分别断开负载端三相断路器，对应回路电流为0A。',
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
          test_request: 'Buzzer on: generates an alarm, and the buzzer sounds loud and clear; clears the alarm and the buzzer stops;' +
              'Buzzer off: generates an alarm and the buzzer stops.',
          test_process: 'Functioning normally',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'Function key: Setting function key (enter setting mode, save and exit after setting).',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'UP button: page up function button, page up to view the display content (upward setting function in setting mode).',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'DOWN button: page down function button, page down to view the display content (in setting mode, long press for positioning function, short press for downward setting function).',
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
          test_request: 'IN/OUT: RS485 Serial communication is normal.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Cascade Check',
          test_request: 'IN/OUT: The cascade fixture is connected to the IN and OUT interfaces of the tap-off box, and the communication is normal.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Voltage Check',
          test_request: 'Switch on and off the circuit breakers corresponding to each socket respectively, and the corresponding circuit voltage data are consistent.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Current check',
          test_request: 'Connect the load and disconnect the thr/ee-phase circuit breakers at the load end, and the corresponding loop current is 0A.',
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
            A1:'8.0',
            A2:'7.9~8.1',
            A3:'OK',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'',
            L3:'',
            L:'L1',
          },
          {
            A1:'8.0',
            A2:'7.9~8.1',
            A3:'OK',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'',
            L3:'',
            L:'L2',
          },
          {
            A1:'8.0',
            A2:'7.9~8.1',
            A3:'OK',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'',
            L3:'',
            L:'L3',
          },
  ]
   //始端箱对外报告信息 -zh 智能型
  const HeaBox = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '参考接线图纸要求，目测内部走线正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '对照产品规格书，产品装配配置正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '老化',
          test_request: '电流表老化24小时过程中正常工作，没有发生报警。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: '接地电阻检测',
          test_request: 'PE端与机壳各接地螺钉之间，接地电阻值应小于100mΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
        {
          num:'5',
          test_item: '绝缘检测',
          test_request: '参照标准要求，火零线与地线之间绝缘测试，其绝缘电阻值应≥10MΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: '耐高压检测',
          test_request: '参照标准要求，火零线与地线和外壳之间耐压测试，不得出现闪络或击穿现象。',
          test_process: '符合要求',
          test_result:"OK"
        },
          
          {
          num:'7',
          test_item: '极性检查',
          test_request: '检查极性测试模块指示灯显示应正常，火、零、地接线应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '功能测试',
          test_request: '对照产品规格书与产品说明书，对产品的各项功能进行测试，确认产品功能正常。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: '产品标识外观',
          test_request: '产品标识正确、清晰，产品外观无混色及明显划伤等。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
  ]
  //始端箱对外报告信息 -EN 智能型
  const HeaBox_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: 'Refer to the wiring drawing requirements and visually check that the internal wiring is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: 'According to the product specification, the product assembly configuration is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: 'Ageing',
          test_request: 'The ammeter worked normally during the 24-hour aging process and no alarm occurred.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'4',
          test_item: 'Ground resistance detection',
          test_request: 'The grounding resistance between the PE terminal and each grounding screw of the chassis should be less than 100mΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
        {
          num:'5',
          test_item: 'Insulation testing',
          test_request: 'According to the standard requirements, the insulation resistance value of the insulation test between the live neutral wire and the ground wire should be ≥10MΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: 'High voltage resistance test',
          test_request: 'According to the standard requirements, the voltage test between the live and neutral wires, the ground wire and the casing shall not show flashover or breakdown.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          
          {
          num:'7',
          test_item: 'Polarity check',
          test_request: 'Check that the indicator light on the polarity test module should be normal and the live, neutral and ground connections should be correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Function test',
          test_request: 'Test the various functions of the product against the product specifications and product manual to confirm that the product functions normally.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'9',
          test_item: 'Product logo and appearance',
          test_request: 'The product labeling is correct and clear, and the product appearance has no mixed colors or obvious scratches.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'10',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
  ]
  //始端箱对外报告信息 -zh 基本型
  const HeaBox_jb = [
        {
          num:'1',
          test_item: '内连线',
          test_request: '参考接线图纸要求，目测内部走线正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: '产品配置',
          test_request: '对照产品规格书，产品装配配置正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: '接地电阻检测',
          test_request: 'PE端与机壳各接地螺钉之间，接地电阻值应小于100mΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
        {
          num:'4',
          test_item: '绝缘检测',
          test_request: '参照标准要求，火零线与地线之间绝缘测试，其绝缘电阻值应≥10MΩ。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: '耐高压检测',
          test_request: '参照标准要求，火零线与地线和外壳之间耐压测试，不得出现闪络或击穿现象。',
          test_process: '符合要求',
          test_result:"OK"
        },
          
          {
          num:'6',
          test_item: '极性检查',
          test_request: '检查极性测试模块指示灯显示应正常，火、零、地接线应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
        {
          num:'7',
          test_item: '产品标识外观',
          test_request: '产品标识正确、清晰，产品外观无混色及明显划伤等。',
          test_process: '符合要求',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: '附件检查',
          test_request: '核对附件要求，检查数量及规格型号应正确。',
          test_process: '符合要求',
          test_result:"OK"
        },
  ]
  //始端箱对外报告信息 -EN 基本型
  const HeaBox_jb_EN = [
        {
          num:'1',
          test_item: 'Internal Connections',
          test_request: 'Refer to the wiring drawing requirements and visually check that the internal wiring is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'2',
          test_item: 'Product configuration',
          test_request: 'According to the product specification, the product assembly configuration is correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'3',
          test_item: 'Ground resistance detection',
          test_request: 'The grounding resistance between the PE terminal and each grounding screw of the chassis should be less than 100mΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
        {
          num:'4',
          test_item: 'Insulation testing',
          test_request: 'According to the standard requirements, the insulation resistance value of the insulation test between the live neutral wire and the ground wire should be ≥10MΩ.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'5',
          test_item: 'High voltage resistance test',
          test_request: 'According to the standard requirements, the voltage test between the live and neutral wires, the ground wire and the casing shall not show flashover or breakdown.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'6',
          test_item: 'Polarity check',
          test_request: 'Check that the indicator light on the polarity test module should be normal and the live, neutral and ground connections should be correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'7',
          test_item: 'Breaker inspection',
          test_request: 'The product labeling is correct and clear, and the product appearance has no mixed colors or obvious scratches.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
          {
          num:'8',
          test_item: 'Accessory inspection',
          test_request: 'Check the accessory requirements and check whether the quantity and specifications are correct.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },
  ]
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
          test_request: '功能按键：设置功能键（进入设置模式，设置后保存退出）。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'UP按键：向上翻页功能键，翻页查看显示屏内容（设置模式时为向上设置功能）。',
          test_process: '操作成功',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '按键检查',
          test_request: 'DOWN按键：向下翻页功能键，翻页查看显示屏内容（设置模式时长按为定位功能，短按为向下设置功能）。',
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
          test_request: 'IN、OUT: 级联治具连接插接箱IN和OUT接口，通讯正常。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电压检查',
          test_request: '分别通断各插座对应的回路断路器，对应回路电压数据一致。',
          test_process: '符合要求',
          test_result:"OK"
        },{
          num:'硬件设置',
          test_item: '电流检查',
          test_request: '接入负载，分别断开负载端三相断路器，对应回路电流为0A。',
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
          test_request: 'Buzzer on: generates an alarm, and the buzzer sounds loud and clear; clears the alarm and the buzzer stops.;' +
              'Buzzer off: generates an alarm and the buzzer stops.',
          test_process: 'Functioning normally',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'Function key: Setting function key (enter setting mode, save and exit after setting).',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'UP button: page up function button, page up to view the display content (upward setting function in setting mode).',
          test_process: 'Successful operation',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Key Check',
          test_request: 'DOWN button: page down function button, page down to view the display content (in setting mode, long press for positioning function, short press for downward setting function).',
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
          test_request: 'Disconnect the A/B/C circuit breakers at the input end respectively, and the corresponding phase voltage data is 0V.',
          test_process: 'Meet the requirements',
          test_result:"OK"
        },{
          num:'Hardware Setup',
          test_item: 'Current check',
          test_request: 'Connect the load, disconnect the thr/ee-phase circuit breakers at the load end, and the corresponding phase current is 0A.',
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
            A1:'120.0',
            A2:'119.0~121.1',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'OK',
            L3:'OK',
            L:'L1',
          },
          {
            A1:'120.0',
            A2:'119.0~121.1',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'OK',
            L3:'OK',
            L:'L2',
          },
          {
            A1:'120.0',
            A2:'119.0~121.1',
            A4:'OK',
            A5:'220',
            A6:'219~221',
            L1:'OK',
            L2:'OK',
            L3:'OK',
            L:'L3',
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
  // const formatSkills = (skills)=>{
  function formatSkills(skills): string{
    console.log("skills:"+skills)
      if (skills) {
        console.log("skills:"+skills)
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
            // console.log("len:"+len)
            // console.log("lenName"+lenName)
            // console.log("rowIndex"+rowIndex)
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
        // 完成后保存PDF
        let fileName;
        if(out_language.value  === true) {
            if(radio1.value  === '1'  && goods_dev_name.value  !== '母线槽')
            {
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name.value}-基本型出厂报告.pdf`;
            }
            else if(radio1.value === '2'  && goods_dev_name.value !== '母线槽')
            {
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name.value}-智能出厂报告.pdf`;
            }
            else{
                fileName = `订单号 ${queryParams.orderId}${goods_dev_name.value}出厂报告.pdf`;
            }
        } else if(out_language.value === false) {
            if(radio1.value === '1'  && goods_dev_name.value !== 'Busway')
            {
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name.value}-base Ex-factory Report.pdf`;
            }
            else if(radio1.value === '2'  && goods_dev_name.value !== 'Busway')
            {
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name.value}-smart Ex-factory Report.pdf`;
            }
            else{
                fileName = `Order Number ${queryParams.orderId} ${goods_dev_name.value} Ex-factory Report.pdf`;
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

  // 获取出厂报告数据
  const getReportData = async () => {
    loading.value = true;
    try {
      const res = await TestDataApi.getReportInfo(queryParams);
      if (res != null){
        goods_dev_name.value = res.devName
        customerName.value = res.customerName
        modelNumber.value = res.deviceType
        goods_start_time.value = res.productionDate
        goods_end_time.value = res.testDate
        goods_order_num.value = res.productionNum
        goods_test_num.value = res.passTestNum
      }else{
        ElMessageBox.alert('暂无数据！')
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
    // 成品代码第四个字母判断类型 B为基本型，值为1； 其他字母为智能型，值为2
    radio1.value = queryProductSN[3] == 'B'? '1' : '2';
    if(radio1.value === '2'){
      radio2.value= true;
    }else if(radio1.value === '1'){
      radio2.value = false;
    }
  }
 // 切换语言
 out_language.value = true;
  getReportData(); 
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
  font-size: 22px; /* 调整字体大小 */
  line-height: 10; /* 调整行高 */
  white-space: pre-wrap; /* 允许换行 */
  padding: 0px; /* 调整内边距 */
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
  width: 100%; /* 或者是任何你想要的百分比，用于决定分割线的长度 */
  margin: 0 auto; /* 这会使分割线居中 */
  /* padding-left: 5%;
  padding-right: 5%; */
}
.br-rule{
    height: 60px;
}
.br-rule2{
    height: 200px;
}
.product-name {
  text-align: left;
  font-size: 40px;
  line-height: 45px;
}

.customer-label {
  text-align: right;
  font-size: 40px;
  line-height: 40px;
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
  padding-left: 15%; /* 根据需要调整这个数值 */
}
.right-column {
  align-items: flex-start; /* 或 'center'  依据需要调整 */
  padding-right: 15%; /* 根据需要调整这个数值 */
}

.cell-content-table{
  font-size: 22px;
  line-height: 25px;
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