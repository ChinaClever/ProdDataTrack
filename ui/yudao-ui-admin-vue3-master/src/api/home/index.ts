import request from '@/config/axios'


export const homeApi = {
  shipmentNum:async()=>{
    return request.get({url:'/system/home/shipmentNum'})
  }
}