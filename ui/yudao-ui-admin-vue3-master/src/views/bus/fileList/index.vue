<template>
  <ContentWrap>
    <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="auto">
        <el-form-item label="">
          <el-input 
            placeholder="客户名称、产品类别、成品系列、部门、设计人、受订单号..."
            v-model="queryParams.keyword" 
            clearable
            class="!w-450px"/>
        </el-form-item>
        <el-form-item >
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-1px" /> 搜索</el-button>
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
      <template v-for="column in tableColumns">
        <el-table-column :key="column.prop" :label="column.label" :align="column.align" :prop="column.prop" :width="column.width" v-if="column.istrue">
        <template #default="{ row }">
          <div v-if="column.slot === 'downloadUrl'">
            <el-link type="primary" download :href="row.downloadUrl" :underline="false" target="_blank">下载</el-link>
          </div>
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
</template>

<script setup lang="ts">
import { TestDataApi } from '@/api/bus/testData'
const loading = ref(true)
const list = ref<Array<{ }>>([]) as any; 
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 15,
  keyword: undefined,
})
const pageSizeArr = ref([15,30,50,100])

const tableColumns = ref([
  { label: '规格书', slot: 'downloadUrl' , align: 'center', prop: 'downloadUrl', istrue: true, width: '100px'},
  { label: '客户名称', align: 'center', prop: 'customerName', istrue: true, width: '200px'},
  { label: '交货日期', align: 'center', prop: 'deliveryDate', istrue: true, width: '200px'},
  { label: '产品类别', align: 'center', prop: 'productCategory', istrue: true, width: '150px'},
  { label: '产品系列', align: 'center', prop: 'productLine', istrue: true, width: '200px'},
  { label: '数量', align: 'center', prop: 'number' , istrue: true, width: '150px'},
  { label: '部门', align: 'center', prop: 'department', istrue: true, width: '200px'},
  { label: '设计人', align: 'center', prop: 'designer', istrue:true, width: '200px'},  
  { label: '受订单号', align: 'center', prop: 'orderNumber' , istrue: true, width: '200px'},
  { label: '成品代码', align: 'center', prop: 'deviceCode' , istrue: true, width: '230px'},
  { label: '规格型号', align: 'center', prop: 'deviceType', istrue: true, width: '200px'},  
  { label: '生产车间', align: 'center', prop: 'manufacturingShop', istrue: true, width: '200px'},
  { label: '设计编号', align: 'center', prop: 'designNumber' , istrue: true, width: '230px'},
  { label: '编制日期', align: 'center', prop: 'designDate' , istrue: true, width: '100px'},
  { label: '设计单位', align: 'center', prop: 'architect' , istrue: true, width: '100px'},
  { label: '运输方式', align: 'center', prop: 'modeOfShipping', istrue: true, width: '200px'},
  { label: '客户编码', align: 'center', prop: 'customerCode', istrue: true, width: '200px'},
  { label: '客户型号', align: 'center', prop: 'customerModel', istrue: true, width: '200px'},
]);

/** 搜索按钮操作 */
const handleQuery = () => {
    queryParams.pageNo = 1
    getList()
}

/** 初始化数据 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TestDataApi.getFileListPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>