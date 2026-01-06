import request from '@/config/axios'

export const ModuleTestApi = {

  getModuleTestPage: async (params: any) => {
    return await request.get({ url: `/bus/moduleTest-page`, params })
  },
  // 更新模块质检数据
  updateModuleTest: async(data:any)=>{
    return await request.put({url:`/bus/updateModuleTest`,data})
  },
  // 删除模块质检数据
  deleteModuleTest:async(id)=>{
    return await request.delete({url:`/bus/deleteModuleTest`,params:{id}})
  },
  // 批量删除模块质检数据
  deleteBatchModuleTest: async (moduleSn: string) => {
    return await request.delete({url:`/bus/deleteBatchModuleTest`,params:{moduleSn}})
  },

}
