<template>
  <div class=" bg-white w-full">
    <div class="  p-5" >
     <el-row  :gutter="16" class="p-5">
        <el-col  :span="4" :lg="8" md="24" >
          <el-form-item label="客户名称">
            <el-input v-model="queryparams.clientName"  />
          </el-form-item>
        </el-col>
        <el-col :lg="8" md="12" >
          <el-form-item label="订单号">
            <el-input v-model="queryparams.orderId"  />
          </el-form-item>
        </el-col>
         <el-col :lg="8" md="12" >
          <el-form-item label="成品序列号">
            <el-input v-model="queryparams.productSn"  />
          </el-form-item>
        </el-col>
        <el-col :lg="8" md="12" >
          <el-form-item label="模块序列号">
            <el-input v-model="queryparams.moduleSn"  />
          </el-form-item>
        </el-col>
         <el-col :lg="8" md="12" >
          <el-form-item label="mac地址">
            <el-input v-model="queryparams.macAddress"  />
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class=" p-5">
      <el-table :data="table" class="config-table">
        <el-table-column type="index" label="序号" min-width="80" :index="getTableIndex" />
        <el-table-column  label="客户名称" prop="clientName"  />
        <el-table-column label="订单号" prop="orderId" />
        <el-table-column label="成品序列号" prop="productSn" />
        <el-table-column label="模块序列号" prop="moduleSn" />
        <el-table-column label="mac地址" prop="macAddress" />
      </el-table>
       <Pagination
      :total="total"
      :page-size-arr="pageSizeArr"
      layout = "total, sizes, prev, pager, next, jumper"
      v-model:page="queryparams.pageNo"
      v-model:limit="queryparams.pageSize"
      @pagination="handleMac"/>
    </div>

  </div>
</template>

<script lang="ts" setup>

import { macApi } from '@/api/system/mac';
import { debounce } from 'lodash-es';


type mactype ={
  clientName?: string | undefined
  orderId?: string | undefined
  productSn?: string | undefined
  moduleSn?: string | undefined
  macAddress?: string | undefined
  pageSize: number
  pageNo: number
}
const total = ref(0)
const pageSizeArr = ref([15,30,50,100])
const queryparams = reactive<mactype>({
  pageSize: 15,
  pageNo: 1,
  clientName: undefined,
  orderId: undefined,
  productSn: undefined,
  moduleSn: undefined,
  macAddress: undefined
})
const table = ref<any[]>([])

const getTableIndex = (index: number) => {
  const pageNo = Math.max(1, Number(queryparams.pageNo) || 1)
  const pageSize = Math.max(1, Number(queryparams.pageSize) || 15)
  return (pageNo - 1) * pageSize + index + 1
}



const handleMac = async()=>{
    const payload = {
      ...queryparams,
      pageNo: Math.max(1, Number(queryparams.pageNo) || 1),
      pageSize: Math.max(1, Number(queryparams.pageSize) || 15)
    }

    const res: any = await macApi.getMac(payload)
    if (Array.isArray(res)) {
      table.value = res
      total.value = res.length
      return
    }

    table.value = res?.list ?? []
    total.value = res?.total ?? 0
}

const debouncedHandleMac = debounce(() => {
  handleMac()
}, 300)

onMounted(() => {
  handleMac()
})

onBeforeUnmount(() => {
  debouncedHandleMac.cancel()
})

watch(
  () => [
    queryparams.clientName,
    queryparams.orderId,
    queryparams.productSn,
    queryparams.moduleSn,
    queryparams.macAddress
  ],
  () => {
    queryparams.pageNo = 1
    debouncedHandleMac()
  }
)
</script>


<style scoped lang="scss">
.config-table {
  :deep(.el-table__header) {
    th {
      background-color: #f5f7fa !important;
      color: #606266;
      font-weight: 600;
      font-size: 14px;
      text-align: center;
      border-bottom: 1px solid #ebeef5;
    }
  }

  :deep(.el-table__body) {
    tr {
      &:hover {
        background-color: #f5f7fa;
      }

      td {
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
