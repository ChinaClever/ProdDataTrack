package cn.iocoder.yudao.module.bus.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ModulesTestPageVO;
import cn.iocoder.yudao.module.bus.entity.ModulesTest;
import cn.iocoder.yudao.module.bus.mapper.ModulesTestMapper;
import cn.iocoder.yudao.module.bus.service.ModulesTestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModulesTestServiceImpl implements ModulesTestService {

    @Autowired
    private ModulesTestMapper modulesTestMapper;

    @Override
    public PageResult<ModulesTest> getModulesTestPage(ModulesTestPageVO modulesTestPageVO) {

        System.out.println(modulesTestPageVO);
        Page<ModulesTest> page = new Page<>(modulesTestPageVO.getPageNo(), modulesTestPageVO.getPageSize());

        QueryWrapper<ModulesTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        if(modulesTestPageVO.getOrderId() != null){
            queryWrapper.eq("order_id", modulesTestPageVO.getOrderId());
        }
        if(modulesTestPageVO.getProductSN() != null){
            queryWrapper.eq("product_sn", modulesTestPageVO.getProductSN());
        }
        if(modulesTestPageVO.getModuleSN() != null){
            queryWrapper.eq("module_sn", modulesTestPageVO.getModuleSN());
        }
        if(!modulesTestPageVO.getJudgeResult().equals("all")){
            queryWrapper.eq("judge_result", modulesTestPageVO.getJudgeResult());
        }
        if(!modulesTestPageVO.getLanguage().equals("all")){
            queryWrapper.eq("language_select", modulesTestPageVO.getLanguage());
        }
        if(modulesTestPageVO.getTimeRange() != null){
            queryWrapper.ge("date_time", modulesTestPageVO.getTimeRange()[0]).le("date_time", modulesTestPageVO.getTimeRange()[1]);
        }

        IPage<ModulesTest> resultPage = modulesTestMapper.selectPage(page, queryWrapper);
        PageResult<ModulesTest> pageResult = new PageResult<>();
        pageResult.setList(resultPage.getRecords());
        pageResult.setTotal(resultPage.getTotal());
        return pageResult;
    }
}
