import request from '@/config/axios'



export const systemapi = {
  updateFactoryReportClerk:async(data)=>{
    return  await request.put({ url:'/system/factoryReportClerk/updateFactoryReportClerk', data})  
  },
  getFactoryReportClerk:async(params)=>{
    return await request.get({url:`/system/factoryReportClerk/getFactoryReportClerk/?identification=${params}`})
  }
}