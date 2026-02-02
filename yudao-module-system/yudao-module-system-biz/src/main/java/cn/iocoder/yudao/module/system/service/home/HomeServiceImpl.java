package cn.iocoder.yudao.module.system.service.home;

import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.mapper.TestDataMapper;
import cn.iocoder.yudao.module.pdu.entity.PduQualityTestData;
import cn.iocoder.yudao.module.pdu.mapper.PduQualityTestDataMapper;
import cn.iocoder.yudao.module.system.controller.admin.home.vo.ShipmentNumVo;
import cn.iocoder.yudao.module.system.util.dateUtil.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    private PduQualityTestDataMapper pduQualityTestDataMapper;

    @Autowired
    private TestDataMapper testDataMapper;

    private static int applyAsInt(PduQualityTestData testData) {
        try {
            return Integer.parseInt(testData.getOrderNum());
        } catch (NumberFormatException e) {
            log.info("Invalid order number: {}", testData.getOrderNum());
            return 0;
        }
    }

    private static int applyAsInt(TestData testData) {
        try {
            return Integer.parseInt(testData.getOrderNum());
        } catch (NumberFormatException e) {
            log.info("Invalid order number: {}", testData.getOrderNum());
            return 0;
        }
    }

    private void processPduData(List<PduQualityTestData> data, ShipmentNumVo vo, String part1, String part2, int type) {
        List<PduQualityTestData> filteredData = data.stream()
                .filter(testData -> testData.getProductType().contains(part1) && (part2 == null || testData.getProductType().contains(part2)))
                .collect(Collectors.toList());

        if (!filteredData.isEmpty()) {
            int totalOrderNum = filteredData.stream()
                    .filter(testData -> testData.getOrderNum() != null && !testData.getOrderNum().isEmpty())
                    .mapToInt(HomeServiceImpl::applyAsInt)
                    .sum();
            switch (type) {
                case 1:
                    vo.setMonth(totalOrderNum);
                    break;
                case 2:
                    vo.setYear(totalOrderNum);
                    break;
                case 3:
                    vo.setLastYear(totalOrderNum);
                    break;
            }
        }
    }

    private void processBusData(List<TestData> data, ShipmentNumVo vo, int type) {
        int totalOrderNum = data.stream()
                .filter(testData -> testData.getOrderNum() != null && !testData.getOrderNum().isEmpty())
                .mapToInt(HomeServiceImpl::applyAsInt)
                .sum();
        switch (type) {
            case 1:
                vo.setMonth(totalOrderNum);
                break;
            case 2:
                vo.setYear(totalOrderNum);
                break;
            case 3:
                vo.setLastYear(totalOrderNum);
                break;
        }
    }

    @Override
    public List<ShipmentNumVo> getShipmentNum() {
        Map<String, String> startAndEndOfMonth = DateUtils.getStartAndEndOfMonth();
        Map<String, String> startAndEndOfYear = DateUtils.getStartAndEndOfYear();
        Map<String, String> startAndEndOfLastYear = DateUtils.getStartAndEndOfLastYear();

        List<ShipmentNumVo> shipmentNumVos = new ArrayList<>();
        ShipmentNumVo mPduPro = new ShipmentNumVo("MPDU-Pro");
        ShipmentNumVo iPPdu = new ShipmentNumVo("IP-PDU");
        ShipmentNumVo bMPdu = new ShipmentNumVo("BM-PDU");
        ShipmentNumVo zPdu = new ShipmentNumVo("Z-PDU");
        ShipmentNumVo sIPdu = new ShipmentNumVo("SI-PDU");
        ShipmentNumVo busWay = new ShipmentNumVo("Bus-Way");

        // 获取所有PDU数据（语言为中文且通过质检）
        QueryWrapper<PduQualityTestData> pduQueryWrapper = new QueryWrapper<>();
        pduQueryWrapper
                .eq("language_select", "0")
                .eq("result", "1");
        List<PduQualityTestData> allPduData = pduQualityTestDataMapper.selectList(pduQueryWrapper);

        // 获取所有Bus数据（语言为中文且通过质检）
        QueryWrapper<TestData> busQueryWrapper = new QueryWrapper<>();
        busQueryWrapper
                .eq("language_select", "0")
                .eq("test_result", "1");
        List<TestData> allBusData = testDataMapper.findLatestByProductSnWithConditions();
//        List<TestData> allBusData = testDataMapper.selectList(busQueryWrapper);

        // 本月数据处理
        processShipmentData(allPduData, allBusData, startAndEndOfMonth, 1, mPduPro, iPPdu, bMPdu, zPdu, sIPdu, busWay);

        // 今年数据处理
        processShipmentData(allPduData, allBusData, startAndEndOfYear, 2, mPduPro, iPPdu, bMPdu, zPdu, sIPdu, busWay);

        // 上年数据处理
        processShipmentData(allPduData, allBusData, startAndEndOfLastYear, 3, mPduPro, iPPdu, bMPdu, zPdu, sIPdu, busWay);

        shipmentNumVos.add(mPduPro);
        shipmentNumVos.add(iPPdu);
        shipmentNumVos.add(bMPdu);
        shipmentNumVos.add(zPdu);
        shipmentNumVos.add(sIPdu);
        shipmentNumVos.add(busWay);

        return shipmentNumVos;
    }

    private void processShipmentData(List<PduQualityTestData> allPduData, List<TestData> allBusData, Map<String, String> dateRange, int type, ShipmentNumVo mPduPro, ShipmentNumVo iPPdu, ShipmentNumVo bMPdu, ShipmentNumVo zPdu, ShipmentNumVo sIPdu, ShipmentNumVo busWay) {
        LocalDate start = DateUtils.parseDate(dateRange.get("start"));
        LocalDate end = DateUtils.parseDate(dateRange.get("end"));

        // 过滤PDU数据
        List<PduQualityTestData> data = allPduData.parallelStream()
                .filter(testData -> isWithinDateRange(testData.getTestEndTime(), start, end))
                .collect(Collectors.toList());

        if (!data.isEmpty()) {
            // part 设置匹配条件
            processPduData(data, mPduPro, "M", "Pro", type);
            processPduData(data, iPPdu, "IP", null, type);
            processPduData(data, bMPdu, "BM", null, type);
            processPduData(data, zPdu, "Z", null, type);
            processPduData(data, sIPdu, "SI", null, type);
        }

        // 过滤Bus数据
        List<TestData> busData = allBusData.parallelStream()
                .filter(testData -> isWithinDateRange(testData.getEndTime(), start, end))
                .collect(Collectors.toList());
        processBusData(busData, busWay, type);
//        if (!busData.isEmpty()) {
//            // 剔除重复的数据（母线是一个测试项目一条数据 pdu是所有测试项目为一条数据）
//            List<TestData> uniqueTestDataList = busData.stream()
//                    .collect(Collectors.collectingAndThen(Collectors.toMap(TestData::getModuleSn, item -> item, (item1, item2) -> item1), map -> new ArrayList<>(map.values())));
//            processBusData(uniqueTestDataList, busWay, type);
//        }
    }

    private boolean isWithinDateRange(String dateString, LocalDate start, LocalDate end) {
        try {
            LocalDate date = DateUtils.parseDate(dateString);
            return !date.isBefore(start) && !date.isAfter(end);
        } catch (Exception e) {
            log.warn("Failed to parse date: {}", dateString, e);
            return false;
        }
    }
}
