<template>
  <div class=" bg-white w-full">
    <div class="  p-5" >
     <el-row  :gutter="16" class="p-5">
        <el-col  :span="12" :lg="12" md="24" >
          <el-form-item label="客户名称">
            <el-input v-model="queryparams.clientName"  />
          </el-form-item>
        </el-col>
        <el-col :lg="12" md="12" >
          <el-form-item label="订单号">
            <el-input v-model="queryparams.orderId"  />
          </el-form-item>
        </el-col>
         <el-col :lg="12" md="12" >
          <el-form-item label="成品序列号">
            <el-input v-model="queryparams.productSn"  />
          </el-form-item>
        </el-col>
        <el-col :lg="12" md="12" >
          <el-form-item label="模块序列号">
            <el-input v-model="queryparams.moduleSn"  />
          </el-form-item>
        </el-col>
         <el-col :lg="12" md="12" >
          <el-form-item label="mac地址">
            <el-input v-model="queryparams.macAddress"  />
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class=" p-5">
      <el-table :data="table"   class="config-table">
        <el-table-column  label="客户名称" prop="clientName"  />
        <el-table-column label="订单号" prop="orderId" />
        <el-table-column label="成品序列号" prop="productSn" />
        <el-table-column label="模块序列号" prop="moduleSn" />
        <el-table-column label="mac地址" prop="macAddress" />
      </el-table>
    </div>

  </div>
</template>

<script lang="ts" setup>

import { macApi } from '@/api/system/mac';
import { debounce } from 'lodash-es';


type mactype ={
  clientName ? : undefined | string ,
  orderId ?: undefined | string,
  productSn ?: undefined | string,
  moduleSn ?:undefined | string,
  macAddress ?: undefined | string
}

const queryparams = reactive<mactype>({
  
})
const table = ref([])



const handleMac = async()=>{

  const payload = Object.fromEntries(
    Object.entries(toRaw(queryparams)).filter(([,v])=> v !== undefined && v !== '')
  )
    const res = await macApi.getMac(payload)
    if(res){
      table.value = res
    }
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
  queryparams,
  () => {
    debouncedHandleMac()
  },
  { deep: true }
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
