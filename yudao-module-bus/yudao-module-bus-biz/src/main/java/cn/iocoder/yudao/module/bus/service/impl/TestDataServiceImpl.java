package cn.iocoder.yudao.module.bus.service.impl;

import org.springframework.data.mongodb.core.query.Query;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.PageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.repository.TestDataRepository;
import cn.iocoder.yudao.module.bus.service.TestDataService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class TestDataServiceImpl implements TestDataService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public PageResult<TestData> getTestDataPage(PageReqVO pageReqVO) throws IOException {
        Query query = new Query();
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        query.with(pageable);
        List<TestData> testDataList = mongoTemplate.find(query, TestData.class, "testdata");

        System.out.println("testDataList:"+testDataList);
        PageResult<TestData> pageResult = new PageResult<>();
        if ( testDataList.size() == 0 ){
            return pageResult;
        }
        pageResult.setList(testDataList);
        pageResult.setTotal((long) testDataList.size());
        return pageResult;
    }
}
