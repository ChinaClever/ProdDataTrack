import request from '@/config/axios'

export const ModuleTestApi = {

  getModuleTestPage: async (params: any) => {
    return await request.get({ url: `/bus/moduleTest-page`, params })
  },

}
