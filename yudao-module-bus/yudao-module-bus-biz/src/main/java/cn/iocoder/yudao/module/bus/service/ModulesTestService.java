package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ModulesTestPageVO;
import cn.iocoder.yudao.module.bus.entity.ModulesTest;

public interface ModulesTestService {

    PageResult<ModulesTest> getModulesTestPage(ModulesTestPageVO modulesTestPageVO);
}
