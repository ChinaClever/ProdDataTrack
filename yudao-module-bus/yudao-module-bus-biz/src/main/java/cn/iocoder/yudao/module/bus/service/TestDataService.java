package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.PageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import java.io.IOException;

public interface TestDataService {
    PageResult<TestData> getTestDataPage(PageReqVO pageReqVO) throws IOException;
}
