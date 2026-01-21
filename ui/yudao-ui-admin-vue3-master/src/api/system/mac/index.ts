import request from '@/config/axios'


export const macApi = {
  getMac: async (data)=>{
    return await request.post({url:`/system/mac/getDistributionSituation`,data})
  },
  getAddress :async(data)=>{
    return await request.post({url:`/system/mac/getAddress`,data})
  }
}