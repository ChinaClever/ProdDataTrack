import request from '@/config/axios'

export const TestDataApi = {

  getTestDataPage: async (params: any) => {
    return await request.get({ url: `/bus/testData-page`, params })
  },

  getFileListPage: async (params: any) => {
    return await request.get({ url: `/bus/fileList-page`, params })
  },

  getReportInfo: async (data: any) => {
    return await request.post({ url: `/bus/report`, data })
  },

  getInternalReport: async (params: any) => {
    return await request.get({ url: `/bus/internal-report`, params })
  },
  // 单个成品删除
  deleteTestData: async (id: number | string) => {
    return await request.delete({ url: `/bus/deleteTestData`, params: { id } })
  },
  // 批量删除
  deleteBatchTestData: async (moduleSn: string) => {
    return await request.delete({ url: `/bus/deleteBatchTestData`, params: { moduleSn } })
  },
  // 更新成品质检数据
  updateTestData: async (data: any) => {
    return await request.put({ url: `/bus/updateTestData`, data })
  }
}
