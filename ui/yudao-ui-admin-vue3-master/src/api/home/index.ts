import request from '@/config/axios'



export const homeApi = {
  shipmentNum:async()=>{
    return request.get({url:'/system/home/shipmentNum'})
  },
  pduQueueTake:async()=>{
    return request.get({url:`/pdu/queueTake`})
  },
  busQueueTake:async()=>{
    return request.get({url:'/bus/queueTake'})
  }
}