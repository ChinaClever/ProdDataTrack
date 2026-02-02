package cn.iocoder.yudao.module.pdu.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestPageVO;
import cn.iocoder.yudao.module.pdu.entity.PduModulesTestData;

public interface PduModulesTestService {

    PageResult<PduModulesTestDataVo> getModulesTestPage(PduModulesTestPageVO pduModulesTestPageVO);

    void updateModuleTest(PduModulesTestData pduModulesTestData);

    void deleteModuleTest(Integer id);

    void savePduModulesTestData(PduModulesTestDataVo pduModulesTestDataVo);
}
