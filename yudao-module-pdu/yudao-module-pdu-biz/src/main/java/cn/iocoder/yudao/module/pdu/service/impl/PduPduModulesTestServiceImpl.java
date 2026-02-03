package cn.iocoder.yudao.module.pdu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.http.CRMUtils;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.InformText;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestPageVO;
import cn.iocoder.yudao.module.pdu.entity.PduModulesTestData;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.PduTestDataDetail;
import cn.iocoder.yudao.module.pdu.mapper.PduModulesTestMapper;
import cn.iocoder.yudao.module.pdu.service.PduModulesTestService;
import cn.iocoder.yudao.module.pdu.service.PduQueueService;
import cn.iocoder.yudao.module.pdu.util.convertor.DataTransitionUtil;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PduPduModulesTestServiceImpl implements PduModulesTestService {

    @Autowired
    private PduModulesTestMapper pduModulesTestMapper;

    @Autowired
    private PduQueueService pduQueueService;

    @Override
    public PageResult<PduModulesTestDataVo> getModulesTestPage(PduModulesTestPageVO pduModulesTestPageVO) {
        // 创建分页对象
        Page<PduModulesTestData> page = new Page<>(pduModulesTestPageVO.getPageNo(), pduModulesTestPageVO.getPageSize());

        // 构建查询条件
        QueryWrapper<PduModulesTestData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        if (pduModulesTestPageVO.getOrderId() != null) {
            queryWrapper.like("order_id", pduModulesTestPageVO.getOrderId());
        }
        if (pduModulesTestPageVO.getProductSn() != null) {
            queryWrapper.like("product_sn", pduModulesTestPageVO.getProductSn());
        }
        if (pduModulesTestPageVO.getModuleSn() != null) {
            queryWrapper.like("module_sn", pduModulesTestPageVO.getModuleSn());
        }
        if (pduModulesTestPageVO.getClientName() != null) {
            queryWrapper.like("client_name", pduModulesTestPageVO.getClientName());
        }
        if (pduModulesTestPageVO.getModuleType() != null) {
            queryWrapper.like("module_type", pduModulesTestPageVO.getModuleType());
        }
        if (!"all".equals(pduModulesTestPageVO.getResult())) {
            queryWrapper.like("result", pduModulesTestPageVO.getResult());
        }
        if (!"all".equals(pduModulesTestPageVO.getLanguage())) {
            queryWrapper.eq("language_select", pduModulesTestPageVO.getLanguage());
        }
        if (pduModulesTestPageVO.getTimeRange() != null && pduModulesTestPageVO.getTimeRange().length == 2) {
            queryWrapper.ge("test_end_time", pduModulesTestPageVO.getTimeRange()[0])
                    .le("test_end_time", pduModulesTestPageVO.getTimeRange()[1]);
        }

        try {
            // 执行分页查询
            IPage<PduModulesTestData> resultPage = pduModulesTestMapper.selectPage(page, queryWrapper);

            // 创建页面结果对象
            PageResult<PduModulesTestDataVo> pageResult = new PageResult<>();
            List<PduModulesTestDataVo> pduModulesTestDataVoList = new ArrayList<>();

            // 将查询结果转换为 PduModulesTestDataVo 对象
            for (PduModulesTestData record : resultPage.getRecords()) {
                PduModulesTestDataVo pduModulesTestDataVo = new PduModulesTestDataVo();
                BeanUtils.copyProperties(record, pduModulesTestDataVo);

                String testData = record.getTestData();
                List<PduTestDataDetail> pduTestDataDetails = Optional.ofNullable(testData)
                        .map(DataTransitionUtil::parsePduTestDataDetail)
                        .orElse(new ArrayList<>());

                pduModulesTestDataVo.setTestData(pduTestDataDetails);
                pduModulesTestDataVoList.add(pduModulesTestDataVo);
            }

            pageResult.setList(pduModulesTestDataVoList);
            pageResult.setTotal(resultPage.getTotal());

            return pageResult;
        } catch (Exception e) {
            log.error("Error occurred while fetching modules test data page", e);
            throw new RuntimeException("Error occurred while fetching modules test data page", e);
        }
    }

    @Override
    public void updateModuleTest(PduModulesTestData pduModulesTestData) {
        pduModulesTestMapper.updateById(pduModulesTestData);
    }

    @Override
    public void deleteModuleTest(Integer id) {
        pduModulesTestMapper.deleteById(id);
    }

    @Override
    public void savePduModulesTestData(PduModulesTestDataVo pduModulesTestDataVo) {
        try {
            PduModulesTestData pduModulesTestData = new PduModulesTestData();
            BeanUtils.copyProperties(pduModulesTestDataVo, pduModulesTestData);
            pduModulesTestData.setModuleSn(pduModulesTestDataVo.getModuleSn().replaceAll("\\s+", ""));
            pduModulesTestData.setProductSn(pduModulesTestDataVo.getProductSn());
            pduModulesTestData.setTestData(pduModulesTestDataVo.getTestData().toString());
            DateTime dateTime = new DateTime();
            String timeString = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            pduModulesTestData.setTestDate(timeString);
            Map<String, String> crmInfo = CRMUtils.getInfoByCRM(pduModulesTestDataVo.getOrderId(), pduModulesTestDataVo.getProductSn(), "pdu");
            String customerName = crmInfo.get("CUSTOMERNAME");
            String specification = crmInfo.get("MODELCODE");
            String quantity = crmInfo.get("QUANTITY");
            pduModulesTestData.setClientName(customerName);
            pduModulesTestData.setSpecification(specification);
            pduModulesTestData.setOrderNum(quantity.equals("0") ? pduModulesTestDataVo.getOrderNum() : quantity);
            pduModulesTestMapper.insert(pduModulesTestData);
            if ("0".equals(pduModulesTestDataVo.getLanguageSelect())) {
                InformText informText = new InformText();
                String msg = pduModulesTestData.getModuleType() + "：设备（" + pduModulesTestData.getModuleSn() + "）校准" + (pduModulesTestData.getResult().equals("0") ? "失败" : "完成");
                informText.setMessage(msg);
                informText.setTestDate(timeString);
                informText.setTitle("PDU校准");
                pduQueueService.addElementToQueue("pduModule", informText);
            }
        } catch (Exception e) {
            log.error("存储测试数据失败：", e);
        }
    }

}
