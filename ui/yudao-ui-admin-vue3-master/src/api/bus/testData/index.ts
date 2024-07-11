import request from '@/config/axios'

export const TestDataApi = {

  getTestDataPage: async (params: any) => {
    return await request.get({ url: `/bus/testData-page`, params })
  },

  getFileListPage: async (params: any) => {
    return await request.get({ url: `/bus/fileList-page`, params })
  },

}
