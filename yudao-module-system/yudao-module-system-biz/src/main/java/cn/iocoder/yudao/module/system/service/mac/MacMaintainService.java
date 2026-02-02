package cn.iocoder.yudao.module.system.service.mac;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainPageReqVo;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainReqVo;
import cn.iocoder.yudao.module.system.dal.dataobject.mac.MacMaintain;

import java.util.List;

public interface MacMaintainService {

    String getAddress(MacMaintainReqVo macMaintainReqVo);

    PageResult<MacMaintain> macAddressDistributionSituation(MacMaintainPageReqVo macMaintainPageReqVo);
}
