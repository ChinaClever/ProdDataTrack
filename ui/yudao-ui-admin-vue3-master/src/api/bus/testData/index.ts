import request from '@/config/axios'

export const TestDataApi = {

  getTestDataPage: async (params: any) => {
    return await request.get({ url: `/bus/testData-page`, params })
  },

  getFileListPage: async (params: any) => {
    return await request.get({ url: `/bus/fileList-page`, params })
  },

  getReportData: async (data: any) => {
    return await request.post({ url: `/bus/report`, data })
  },

  getInternalReport: async (params: any) => {
    return await request.get({ url: `/bus/internal-report`, params })
  },

  // 导出某个序列号下交流耐压测试/绝缘测试/接地测试的耗时
  exportTestDuration: async (params: any) => {
    return await request.download({ url: `/bus/test-duration-export`, params })
  },

}
