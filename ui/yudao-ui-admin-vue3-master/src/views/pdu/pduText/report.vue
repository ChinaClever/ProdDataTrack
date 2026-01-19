<template>
  <div class=" w-full h-full overflow-auto ">
    <div class=" bg-white flex justify-between p-2">
      <div>
          <el-switch active-text="中文"  inactive-text="英文"  inactive-value="1" active-value="0"  class=" ml-3"   v-model="outLangues" />
          <el-switch  active-text="克莱沃"  inactive-text="罗格朗"  active-value="1" inactive-value="0"  class="ml-3" v-model="company"        />
      </div>
      <el-button type="primary"    @click="output" >导出</el-button>
    
    </div>
    <div ref="config" class=" w-full h-full mt-2 bg-white font-900 ">
      测试pdf功能
    </div>
  </div> 
</template>


<script setup lang="ts">
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas'

const outLangues = ref('0')
const config = ref<HTMLElement | null>(null)
const company = ref('1')

const output = async ()=>{
  await nextTick()
  const el = config.value
  if (!el) return

  const canvas = await html2canvas(el, {
    scale: 2,
    useCORS: true,
    backgroundColor: '#fff',
    width: el.scrollWidth,
    height: el.scrollHeight,
    scrollX: 0,
    scrollY: -window.scrollY
  })

  const imgData = canvas.toDataURL('image/jpeg', 1.0)
  const doc = new jsPDF('p', 'pt', 'a4')
  const pageW = doc.internal.pageSize.getWidth()
  const pageH = doc.internal.pageSize.getHeight()

  const imgW = pageW
  const imgH = (canvas.height * pageW) / canvas.width

  let offsetY = 0
  doc.addImage(imgData, 'JPEG', 0, 0, imgW, imgH)

  while (imgH - offsetY > pageH) {
    offsetY += pageH
    doc.addPage()
    doc.addImage(imgData, 'JPEG', 0, -offsetY, imgW, imgH)
  }

  doc.save('report.pdf')
}


</script>

<style scoped lang="scss">
  
</style>
