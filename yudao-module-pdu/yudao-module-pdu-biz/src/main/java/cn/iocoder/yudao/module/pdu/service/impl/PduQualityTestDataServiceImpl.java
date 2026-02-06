package cn.iocoder.yudao.module.pdu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.http.CRMUtils;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.*;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduQualityTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduTestDataPageReqVO;
import cn.iocoder.yudao.module.pdu.entity.PduModulesTestData;
import cn.iocoder.yudao.module.pdu.mapper.PduModulesTestMapper;
import cn.iocoder.yudao.module.pdu.service.PduQueueService;
import cn.iocoder.yudao.module.pdu.util.convertor.DataTransitionUtil;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.PduTestDataDetail;
import cn.iocoder.yudao.module.pdu.entity.PduUsedOrderInfo;
import cn.iocoder.yudao.module.pdu.mapper.PduQualityTestDataMapper;
import cn.iocoder.yudao.module.pdu.mapper.PduUsedOrderInfoMapper;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileConfigDO;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import cn.iocoder.yudao.module.infra.service.file.FileConfigService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import cn.iocoder.yudao.module.pdu.entity.PduQualityTestData;
import cn.iocoder.yudao.module.pdu.service.PduQualityTestDataService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;


@Service
@Slf4j
public class PduQualityTestDataServiceImpl implements PduQualityTestDataService {
    @Resource
    private FileService fileService;
    @Resource
    private FileConfigService fileConfigService;
    @Autowired
    private PduUsedOrderInfoMapper usedOrderInfoMapper;
    @Autowired
    private PduQualityTestDataMapper pduQualityTestDataMapper;
    @Autowired
    private PduQueueService pduQueueService;
    @Autowired
    private PduModulesTestMapper pduModulesTestMapper;
    private static int dataNum = 0;
    private static final int QR_CODE_SIZE = 200;
    private static boolean existsCode = false;
    private static boolean existsOrder = false;
    public final static String qrcodePath = "/";


    @Override
    public void savePduQualityTestData(PduQualityTestDataVo pduQualityTestDataVo) {
        try {
            PduQualityTestData pduQualityTestData = new PduQualityTestData();
            BeanUtils.copyProperties(pduQualityTestDataVo, pduQualityTestData);
            pduQualityTestData.setProductSn(pduQualityTestDataVo.getProductSN());
            pduQualityTestData.setModuleSn(pduQualityTestDataVo.getModuleSn().replaceAll("\\s+", ""));
            pduQualityTestData.setOutletSn(pduQualityTestDataVo.getOutletSN() != null ? pduQualityTestDataVo.getOutletSN().replaceAll("\\s+", "") : null);
            pduQualityTestData.setTestData(pduQualityTestDataVo.getTestData().toString());
            DateTime dateTime = new DateTime();
            String timeString = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            pduQualityTestData.setTestDate(timeString);
            Map<String, String> crmInfo = CRMUtils.getInfoByCRM(pduQualityTestDataVo.getOrderId(), pduQualityTestDataVo.getProductSN(), "pdu");
            String customerName = crmInfo.get("CUSTOMERNAME");
            String specification = crmInfo.get("MODELCODE");
            String quantity = crmInfo.get("QUANTITY");
            pduQualityTestData.setClientName(customerName);
            pduQualityTestData.setSpecification(specification);
            pduQualityTestData.setOrderNum(quantity.equals("0") ? pduQualityTestDataVo.getOrderNum() : quantity);
            pduQualityTestDataMapper.insert(pduQualityTestData);
            if ("0".equals(pduQualityTestDataVo.getLanguageSelect())) {
                InformText informText = new InformText();
                String msg = pduQualityTestData.getProductType() + "：设备（" + pduQualityTestData.getModuleSn() + "）质检" + (pduQualityTestData.getResult().equals("0") ? "失败" : "完成");
                informText.setMessage(msg);
                informText.setTestDate(timeString);
                informText.setTitle("PDU质检");
                pduQueueService.addElementToQueue("pduQuality", informText);
            }
        } catch (Exception e) {
            log.error("存储测试数据失败：", e);
        }
    }

    /**
     * 获取测试数据的分页结果
     *
     * @param pageReqVO 分页查询请求参数
     * @return 分页结果
     */
    @Override
    public PageResult<PduQualityTestDataVo> getTestDataPage(PduTestDataPageReqVO pageReqVO) {
        // 创建分页对象
        Page<PduQualityTestData> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());

        // 创建查询条件对象
        QueryWrapper<PduQualityTestData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        // 根据请求参数设置查询条件
        Optional.ofNullable(pageReqVO.getOrderId()).ifPresent(orderId -> queryWrapper.like("order_id", orderId));
        Optional.ofNullable(pageReqVO.getProductSN()).ifPresent(productSN -> queryWrapper.like("product_sn", productSN));
        Optional.ofNullable(pageReqVO.getModuleSn()).ifPresent(moduleSn -> queryWrapper.like("module_sn", moduleSn));
        Optional.ofNullable(pageReqVO.getProductType()).ifPresent(productType -> queryWrapper.like("product_type", productType));
        Optional.ofNullable(pageReqVO.getClientName()).ifPresent(clientName -> queryWrapper.like("client_name", clientName));

        if (pageReqVO.getResult() != null && !pageReqVO.getResult().equals("all")) {
            queryWrapper.eq("result", pageReqVO.getResult());
        }

        if (pageReqVO.getLanguage() != null && !pageReqVO.getLanguage().equals("all")) {
            queryWrapper.eq("language_select", pageReqVO.getLanguage());
        }

        if (pageReqVO.getTimeRange() != null) {
            queryWrapper.ge("test_end_time", pageReqVO.getTimeRange()[0]).le("test_end_time", pageReqVO.getTimeRange()[1]);
        }

        // 执行分页查询
        IPage<PduQualityTestData> resultPage;
        try {
            resultPage = pduQualityTestDataMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            log.error("Error executing query for page request: {}", pageReqVO, e);
            throw new RuntimeException("查询数据时发生错误", e);
        }

        // 构建分页结果对象
        PageResult<PduQualityTestDataVo> pageResult = new PageResult<>();
        pageResult.setTotal(resultPage.getTotal());
        List<PduQualityTestData> records = resultPage.getRecords();
        List<PduQualityTestDataVo> pduQualityTestDataVos = new ArrayList<>();
        for (PduQualityTestData record : records) {
            PduQualityTestDataVo pduQualityTestDataVo = new PduQualityTestDataVo();
            BeanUtils.copyProperties(record, pduQualityTestDataVo);
            pduQualityTestDataVo.setProductSN(record.getProductSn());
            pduQualityTestDataVo.setOutletSN(record.getOutletSn());
            String testData = record.getTestData();
            List<PduTestDataDetail> pduTestDataDetails = DataTransitionUtil.parsePduTestDataDetail(testData);
            pduQualityTestDataVo.setTestData(pduTestDataDetails);
            pduQualityTestDataVos.add(pduQualityTestDataVo);
        }
        pageResult.setList(pduQualityTestDataVos);
        return pageResult;
    }

    @Override
    public PageResult<PduUsedOrderInfo> getFileListPage(PduFileListPageReqVO pageReqVO) {
        Page<PduUsedOrderInfo> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());

        LambdaQueryWrapper<PduUsedOrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(PduUsedOrderInfo::getId);

        // 模糊查询
        if (pageReqVO.getKeyword() != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(PduUsedOrderInfo::getOrderNumber, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getDeviceCode, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getCustomerName, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getProductCategory, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getProductLine, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getDesigner, pageReqVO.getKeyword())
                    .or()
                    .like(PduUsedOrderInfo::getDepartment, pageReqVO.getKeyword()));
        }
        IPage<PduUsedOrderInfo> resultPage = usedOrderInfoMapper.selectPage(page, queryWrapper);

        PageResult<PduUsedOrderInfo> pageResult = new PageResult<>();
        pageResult.setList(resultPage.getRecords());
        pageResult.setTotal(resultPage.getTotal());
        return pageResult;
    }

    @Override
    public PduUsedOrderInfo fileUpload(MultipartFile file) throws IOException {
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID() + extension;
        //上传文件
        String save_path = fileService.createFile(uniqueFileName, uniqueFileName, IoUtil.readBytes(file.getInputStream()));
        // 获取文件所在本地路径
        FileClient fileClient = fileConfigService.getMasterFileClient();
        FileConfigDO fileConfigDO = fileConfigService.getFileConfig(fileClient.getId());
        LocalFileClientConfig localFileClientConfig = (LocalFileClientConfig) fileConfigDO.getConfig();
        String baseUrl = localFileClientConfig.getBasePath();
        String Url = baseUrl + '\\' + uniqueFileName;

        PduUsedOrderInfo usedOrderInfo = new PduUsedOrderInfo();
        try {
            FileInputStream fis = new FileInputStream(Url);
            XWPFDocument document = new XWPFDocument(fis);

            // 获取所有表格
            List<XWPFTable> tables = document.getTables();

            // 遍历每个表格
            for (XWPFTable table : tables) {
                // 读取表格每一行
                table.getRows().forEach(row -> {
                    // 读取每行的每个单元
                    dataNum = 0;
                    String[] rowData = new String[10];
                    row.getTableCells().forEach(cell -> {
                        // 获取单元格文本
                        String text = cell.getText();
                        rowData[dataNum] = text;
                        dataNum += 1;
                    });
                    getOrderInfo(rowData, usedOrderInfo, baseUrl);
                });
            }
            XWPFTable tablerow = document.getTables().get(0);
            XWPFTableCell firstCell = tablerow.getRow(0).getCell(0);

            String imgFile = baseUrl + qrcodePath + usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode() + ".jpeg";
            // 插入图片
            insertQRPicture(firstCell, imgFile, 50, 50, 0);

            // docPi
            FileOutputStream out = new FileOutputStream(Url);
            document.write(out);

            document.close();
            fis.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 文档文件重命名
        File oldNameFile = new File(Url);
        String newName = baseUrl + "\\" + usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode() + extension;
        FileUtil.rename(oldNameFile, newName, true);

        // 生成文件下载url 例如http://127.0.0.1:48088/admin-api/infra/file/23/get/88be287d-543a-4afe-9636-b65ffd8a4d0b.docx
        String newNamePath = usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode() + extension;
        String downloadUrl = StrUtil.format("{}/admin-api/infra/file/{}/get/{}", localFileClientConfig.getDomain(), fileClient.getId(), newNamePath);
        usedOrderInfo.setDownloadUrl(downloadUrl);
        // 保存到mysql
        usedOrderInfoMapper.insert(usedOrderInfo);

        return usedOrderInfo;
    }

    @Override
    public void getOrderInfo(String[] dataStr, PduUsedOrderInfo usedOrderInfo, String baseUrl) {
        String relaseQR;

        for (int i = 0; i < dataStr.length; i++) {
            if (dataStr[i] != null) {
                switch (dataStr[i]) {
                    case "成品代码":
                        usedOrderInfo.setDeviceCode(dataStr[++i]);
                        existsCode = true;
                        break;
                    case "受订单号":
                        usedOrderInfo.setOrderNumber(dataStr[++i]);
                        existsOrder = true;
                        break;
                    case "设计编号":
                        usedOrderInfo.setDesignNumber(dataStr[++i]);
                        break;
                    case "编制日期":
                        usedOrderInfo.setDesignDate(dataStr[++i]);
                        break;
                    case "设计单位":
                        usedOrderInfo.setArchitect(dataStr[++i]);
                        break;
                    case "数量":
                        usedOrderInfo.setNumber(dataStr[++i]);
                        break;
                    case "产品类别":
                        usedOrderInfo.setProductCategory(dataStr[++i]);
                        break;
                    case "产品系列":
                        usedOrderInfo.setProductLine(dataStr[++i]);
                        break;
                    case "交货日期":
                        usedOrderInfo.setDeliveryDate(dataStr[++i]);
                        break;
                    case "运输方式":
                        usedOrderInfo.setModeOfShipping(dataStr[++i]);
                        break;
                    case "部门":
                        usedOrderInfo.setDepartment(dataStr[++i]);
                        break;
                    case "设计人":
                        usedOrderInfo.setDesigner(dataStr[++i]);
                        break;
                    case "客户编码":
                        usedOrderInfo.setCustomerCode(dataStr[++i]);
                        break;
                    case "生产车间":
                        usedOrderInfo.setManufacturingShop(dataStr[++i]);
                        break;
                    case "客户名称":
                        usedOrderInfo.setCustomerName(dataStr[++i]);
                        break;
                    case "客户型号":
                        usedOrderInfo.setCustomerModel(dataStr[++i]);
                        break;
                    case "规格型号":
                        usedOrderInfo.setDeviceType(dataStr[++i]);
                        break;
                }
            }
        }

        if (existsCode && existsOrder) {
            existsCode = false;
            existsOrder = false;
            System.out.println("成品代码:" + usedOrderInfo.getDeviceCode() + ";" + "受订单号:" + usedOrderInfo.getOrderNumber());
            relaseQR = usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode();
            System.out.println("usedOrderInfo:" + usedOrderInfo);
            try {
                generateQRCode(relaseQR, baseUrl);
            } catch (Exception e) {
                System.out.println(relaseQR);
            }
        }
    }

    @Override
    public void generateQRCode(String text, String baseUrl) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE);
        Path path = FileSystems.getDefault().getPath(baseUrl + qrcodePath + text + ".jpeg");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("二维码已生成：" + path);
    }

    @Override
    public CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject,
                                         String deskFileName, int width, int height,
                                         int leftOffset, int topOffset, boolean behind) {

        String anchorXML = "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                + "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"" + ((behind) ? 1 : 0)
                + "\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                + "<wp:simplePos x=\"0\" y=\"0\"/>"
                + "<wp:positionH relativeFrom=\"column\">"
                + "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
                + "</wp:positionH>"
                + "<wp:positionV relativeFrom=\"paragraph\">"
                + "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
                "</wp:positionV>"
                + "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
                + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
                + "<wp:wrapNone/>"
                + "<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\"" + deskFileName + "\"/><wp:cNvGraphicFramePr/>"
                + "</wp:anchor>";

        CTDrawing drawing = null;
        try {
            drawing = CTDrawing.Factory.parse(anchorXML);
        } catch (XmlException e) {
            e.printStackTrace();
        }
        CTAnchor anchor = drawing.getAnchorArray(0);
        anchor.setGraphic(ctGraphicalObject);
        return anchor;
    }

    @Override
    public void insertQRPicture(XWPFTableCell cell, String imgFile, int width, int height, int units)
            throws IOException, InvalidFormatException {
        FileInputStream fis = null;
        try {
            // 创建一个运行来插入图片
            XWPFRun run = cell.getParagraphArray(0).createRun();

            // 在运行中插入图片
            fis = new FileInputStream(imgFile);
            run.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(width),
                    Units.toEMU(height));

            CTDrawing drawing = run.getCTR().getDrawingArray(0);
            CTGraphicalObject graphicalObject = drawing.getInlineArray(0).getGraphic();
            CTAnchor anchor1 = getAnchorWithGraphic(graphicalObject, "Seal",
                    Units.toEMU(50), Units.toEMU(50), // 图片大小
                    Units.toEMU(480), Units.toEMU(0), true);// 相对当前段落位置及偏移
            drawing.setAnchorArray(new CTAnchor[]{anchor1});// 添加浮动属性
            drawing.removeInline(0);// 删除行内属性
        } finally {
            if (fis != null) {
                try {
                    fis.close(); // 关闭文件流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public PduReportRespVO getReportInfo(PduReportReqVO reqVO) {
        PduReportRespVO respVO = new PduReportRespVO();
        QueryWrapper<PduQualityTestData> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.orderByDesc("id")
                .eq("order_id", reqVO.getOrderId())
                .eq("product_sn", reqVO.getProductSN())
                .eq("language_select", "0");

        List<PduQualityTestData> pduQualityTestDataList = pduQualityTestDataMapper.selectList(queryWrapper1);
        respVO.setProductionNum(Integer.valueOf(pduQualityTestDataList.get(0).getOrderNum()));
        respVO.setCustomerName(pduQualityTestDataList.get(0).getClientName());
        respVO.setDeviceType(pduQualityTestDataList.get(0).getSpecification());

        List<String> passTestModuleList = new ArrayList<>();
        List<String> failTestModuleList = new ArrayList<>();
        List<String> moduleSnList = new ArrayList<>();
        boolean skipFlag = false;
        // 区分不同模块
        String nowModule = null;
        // 区分同模块不同次检测 通过同一模块不同检测的检测开始时间区分
        String nowStartTime = null;

        // 查到的数据已经按时间倒序排序 最新一次在前面
        for (PduQualityTestData pduQualityTestData : pduQualityTestDataList) {
            // 初始化当前循环在哪个模块哪一次 第一次循环才执行
            if (nowModule == null) {
                nowModule = pduQualityTestData.getModuleSn();
                nowStartTime = pduQualityTestData.getTestStartTime();
            }

            if (!moduleSnList.contains(pduQualityTestData.getModuleSn())) {
                moduleSnList.add(pduQualityTestData.getModuleSn());
            }
            // 不跳过,同时属于同模块同一次, 不在passTestModuleList和failTestModuleList说明是最新那次
            //需要判断!failTestModuleList.contains(testData.getModule_sn())是为了避免模块最新那次失败了，后续循环到旧检测还执行后续判断，应跳过
            if (!skipFlag && Objects.equals(pduQualityTestData.getModuleSn(), nowModule) && !passTestModuleList.contains(pduQualityTestData.getModuleSn())
                    && !failTestModuleList.contains(pduQualityTestData.getModuleSn())) {

                // 同一模块的同一检测次 出现失败 则这个模块失败 直接跳过到下一模块
                if (Objects.equals(pduQualityTestData.getResult(), "0")) {
                    failTestModuleList.add(nowModule);
                    skipFlag = true;
                    continue;
                }
            }
            // 到了下一个模块执行 或者同一模块旧一次检测
            if (!Objects.equals(pduQualityTestData.getModuleSn(), nowModule) || !Objects.equals(nowStartTime, pduQualityTestData.getTestStartTime())
                    && !passTestModuleList.contains(pduQualityTestData.getModuleSn())
                    && !failTestModuleList.contains(pduQualityTestData.getModuleSn())) {
                // 如果skipFlag == false  说明上一个模块通过测试 需加入passTestModuleList
                if (!skipFlag && !passTestModuleList.contains(pduQualityTestData.getModuleSn())) {
                    passTestModuleList.add(nowModule);
                }
                skipFlag = false;
                nowModule = pduQualityTestData.getModuleSn();
                nowStartTime = pduQualityTestData.getTestStartTime();
                // 看本项测试是否失败 如果失败就继续跳过到下一个模块
                if (Objects.equals(pduQualityTestData.getResult(), "0")) {
                    failTestModuleList.add(nowModule);
                    skipFlag = true;
                }
            }
        }

        if (moduleSnList.size() == 1 && passTestModuleList.isEmpty()) {
            passTestModuleList.add(moduleSnList.get(0));
        }

        System.out.println("passTestModuleList" + Arrays.toString(passTestModuleList.toArray()));
        System.out.println("failTestModuleList" + Arrays.toString(failTestModuleList.toArray()));
        if (!pduQualityTestDataList.isEmpty()) {
            respVO.setDevName(pduQualityTestDataList.get(0).getProductType());
            respVO.setPassTestNum(passTestModuleList.size());
            respVO.setTestDate(pduQualityTestDataList.get(0).getTestEndTime());
        }

        return respVO;
    }

    @Override
    public InternalReportVo getInternalReport(PduReportReqVO reqVO) {
        InternalReportVo internalReportVo = new InternalReportVo();
        // 处理成品质检
        QualityTestInternalReport qualityTestInternalReport = new QualityTestInternalReport();
        QueryWrapper<PduQualityTestData> pduQualityTestDataQueryWrapper = new QueryWrapper<>();
        pduQualityTestDataQueryWrapper.eq("module_sn", reqVO.getModuleSN());
        PduQualityTestData pduQualityTestData = pduQualityTestDataMapper.selectOne(pduQualityTestDataQueryWrapper);
        BeanUtils.copyProperties(pduQualityTestData, qualityTestInternalReport);
        String testData = pduQualityTestData.getTestData();
        List<PduTestDataDetail> pduTestDataDetails = DataTransitionUtil.parsePduTestDataDetail(testData);
        pduTestDataDetails.removeIf(vo -> vo.getTestItem() == null || vo.getTestItem().isEmpty());
        List<PduTestDataDetail> details = processPduTestDataDetail(pduTestDataDetails);
        details.sort(Comparator.comparing(PduTestDataDetail::getNo).reversed());
        int index = 1;
        for (PduTestDataDetail pduTestDataDetail : details) {
            pduTestDataDetail.setNo(String.valueOf(index));
            index++;
        }
        qualityTestInternalReport.setTestData(details);
        internalReportVo.setQualityTestInternalReport(qualityTestInternalReport);

        // 处理半成品调试
        ModuleTestInternalReport moduleTestInternalReport = new ModuleTestInternalReport();
        QueryWrapper<PduModulesTestData> pduModulesTestDataQueryWrapper = new QueryWrapper<>();
        pduModulesTestDataQueryWrapper.eq("module_sn", reqVO.getModuleSN());
        PduModulesTestData pduModulesTestData = pduModulesTestMapper.selectOne(pduModulesTestDataQueryWrapper);
        if (pduModulesTestData != null) {
            BeanUtils.copyProperties(pduModulesTestData, moduleTestInternalReport);
            String moduleTestData = pduModulesTestData.getTestData();
            List<PduTestDataDetail> pduModuleTestDataDetails = DataTransitionUtil.parsePduTestDataDetail(moduleTestData);
            pduModuleTestDataDetails.removeIf(vo -> vo.getTestItem() == null || vo.getTestItem().isEmpty());
            List<PduTestDataDetail> moduleDetails = processPduTestDataDetail(pduModuleTestDataDetails);
            moduleDetails.sort(Comparator.comparing(PduTestDataDetail::getNo).reversed());
            int moduleIndex = 1;
            for (PduTestDataDetail testDataDetail : moduleDetails) {
                testDataDetail.setNo(String.valueOf(moduleIndex));
                moduleIndex++;
            }
            moduleTestInternalReport.setTestData(moduleDetails);
            internalReportVo.setModuleTestInternalReport(moduleTestInternalReport);
        }

        return internalReportVo;
    }

    @Override
    public void updateTestData(PduQualityTestData pduQualityTestData) {
        pduQualityTestDataMapper.updateById(pduQualityTestData);
    }

    @Override
    public void deleteTestData(Integer id) {
        pduQualityTestDataMapper.deleteById(id);
    }

    private List<PduTestDataDetail> processPduTestDataDetail(List<PduTestDataDetail> pduTestDataDetails) {
        List<PduTestDataDetail> processList = new ArrayList<>(pduTestDataDetails.size());
        Set<String> processedTestRequests = new HashSet<>();
        pduTestDataDetails.forEach(i -> {
            String testRequest = i.getTestRequest();
            if (!processedTestRequests.contains(testRequest)) {
                processedTestRequests.add(testRequest);
                processList.add(i);
            }
        });
        return processList;
    }
}
