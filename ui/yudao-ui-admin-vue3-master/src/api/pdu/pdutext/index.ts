import request from '@/config/axios'


export const GettextPduApi = {
  textPduText :async(params:any)=>{
    return await request.get({url:'/pdu/testData-page',params})
  },
  updateQualityTestData : async(data:any)=>{
    return await request.put({url:'/pdu/updateQualityTestData',data})
  },
  deleteTestData:async(id)=>{
    return await request.delete({url:'/pdu/deleteTestData',params:{id}})
  }

}