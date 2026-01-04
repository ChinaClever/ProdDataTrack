<template>
  <div class="p-[10px] bg-[f5f5f5]">
    <h1>配置</h1>

    <el-form ref="formRef" :model="form" label-width="120px" class="systemset-form">
      <el-form-item label="修改配置的类型" prop="identification">
        <el-select v-model="form.identification" placeholder="请选择" class="!w-[220px]" @change="handleChange">
          <el-option
            v-for="item in selectOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-col :span="6">
         <el-form-item label="检验员" prop="inspector">
        <el-input v-model="form.inspector" placeholder="请输入" clearable class="w-full" />
      </el-form-item>
      </el-col>
     
      <el-col :span="6">
        <el-form-item label="审核员" prop="auditor">
        <el-input v-model="form.auditor" placeholder="请输入" clearable class="w-full" />
      </el-form-item>
      </el-col>
      
      <el-col :span="6">
        <el-form-item label="批准" prop="signer">
        <el-input v-model="form.signer" placeholder="请输入" clearable class="w-full" />
      </el-form-item>
      </el-col>

      <el-form-item>
        <el-button type="primary" @click="formsave">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { systemapi } from '@/api/system/systemset'
import { ElMessage } from 'element-plus'

const formRef = ref()

const selectOptions = ref([
  { value: 'bus', label: '母线' },
  // { value: 'xxx', label: '...' },
])

const form = reactive({
  inspector:'克莱沃',
  auditor:'克莱沃',
  signer:'克莱沃',
  identification:'bus',
  id:0
})

const handleRequest = async()=>{
  const res = await systemapi.getFactoryReportClerk(form.identification)
  console.log("res",res)
  form.auditor = res.auditor
  form.inspector = res.inspector
  form.signer = res.signer
  form.id = res.id
  form.identification = res.identification
}

const handleChange = async(val)=>{
  console.log("val",val)
}


const formsave  = async (val) =>{
  console.log("form",form)
  const res = await systemapi.updateFactoryReportClerk(form)

  console.log("res",res)
  if(res){
    // ElMessage.success('保存成功')
    ElMessage.success('保存成功')
    handleRequest()
  }
}

onMounted(()=>{
  handleRequest()
})

</script>


<style lang="scss" scoped>

.form-item{
  display: flex;
  flex-direction: row;
}
</style>
