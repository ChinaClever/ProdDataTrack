<template>
  <div class="  w-full bg-white">
      <el-col :xl="24"  :xs="24"  :lg="24"  >
        
           <el-card  class=" flex-col flex-1" >
            <el-skeleton animated :loading="load">
              <el-form inline>
                <el-form-item label="结果">
                  <el-select style="width:160px" />
                </el-form-item>
                <el-form-item label="语言">
                  <el-select style="width:160px" />
                </el-form-item>
                <el-form-item label="订单号" >
                  <el-input style="width:160px" />
                </el-form-item>
                <el-form-item label="成品代码">
                    <el-input style="width:160px" />
                </el-form-item>
                <el-form-item label="时间段">
                  <el-date-picker style="width:160px" type="datetimerange"    />
                </el-form-item>
              </el-form>
            </el-skeleton>
          </el-card>
      
       
      </el-col>
      
  </div>
  <div class="mt-6">
        <el-table v-model="table" class=" table-style" border>
          <el-table-column  label="内容" />
        </el-table>
      </div> 
</template>


<script setup lang="ts">
  const load = ref(true)
  const table = ref([])
  const options  = ref([
    {
      value:'0',
      label:'全部',
      
    },
    {
          value:'1',
          label:'成功'
        },
           
        {
          value:'2',
          label:'失败'
        }
  ])
  const parmas = reactive({

  })
  onMounted(()=>{
    load.value = false
  })
</script>

<style scoped lang="scss">

  .table-style{
    :deep(.el-table__cell){
      padding: 6px 8px;
    }

    :deep(.el-table__header-wrapper th.el-table__cell){
      background: #f6f6f6;
      color: #111827;
      font-weight: 600;
    }
  }
</style>
