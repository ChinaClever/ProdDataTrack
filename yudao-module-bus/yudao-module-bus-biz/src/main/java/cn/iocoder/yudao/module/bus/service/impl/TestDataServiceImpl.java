package cn.iocoder.yudao.module.bus.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.bus.entity.UsedOrderInfo;
import cn.iocoder.yudao.module.bus.mapper.UsedOrderInfoMapper;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileConfigDO;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import cn.iocoder.yudao.module.infra.service.file.FileConfigService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.PageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.service.TestDataService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import java.util.List;
import java.util.UUID;


@Service
public class TestDataServiceImpl implements TestDataService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private FileService fileService;

    @Resource
    private FileConfigService fileConfigService;
    @Autowired
    private UsedOrderInfoMapper usedOrderInfoMapper;

    private static int dataNum = 0;
    private static final int QR_CODE_SIZE = 200;
    private static boolean existsCode = false;
    private static boolean existsOrder = false;
    public final static String qrcodePath = "/qrcode/";

    @Override
    public PageResult<TestData> getTestDataPage(PageReqVO pageReqVO) throws IOException {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(pageReqVO.getOrderId() != null){
            criteria.and("order_id").is(pageReqVO.getOrderId());
        }
        if(pageReqVO.getProductSN() != null){
            criteria.and("product_sn").is(pageReqVO.getProductSN());
        }
        if(!pageReqVO.getTestResult().equals("all")){
            criteria.and("test_result").is(pageReqVO.getTestResult());
        }
        if(!pageReqVO.getLanguage().equals("all")){
            criteria.and("language_select").is(pageReqVO.getLanguage());
        }
        if(pageReqVO.getTimeRange() != null){
            criteria.and("end_time").gte(pageReqVO.getTimeRange()[0]).lte(pageReqVO.getTimeRange()[1]);
        }
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Order.desc("end_time")))
                .skip((long)(pageReqVO.getPageNo() - 1) * pageReqVO.getPageSize())
                .limit(pageReqVO.getPageSize());
        List<TestData> testDataList = mongoTemplate.find(query, TestData.class);
        // 查询总数
        long total = mongoTemplate.count(query, TestData.class);
        PageResult<TestData> pageResult = new PageResult<>();

        pageResult.setList(testDataList);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public UsedOrderInfo fileUpload(MultipartFile file) throws IOException {
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

        UsedOrderInfo usedOrderInfo = new UsedOrderInfo();
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

            String imgFile = baseUrl+ qrcodePath + usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode() + ".jpeg";
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
    public void getOrderInfo(String[] dataStr, UsedOrderInfo usedOrderInfo, String baseUrl) {
        String relaseQR;

        for (int i = 0; i < dataStr.length; i++) {
            if (dataStr[i] != null) {
                if (dataStr[i].equals("成品代码")) {
                    usedOrderInfo.setDeviceCode(dataStr[++i]);
                    existsCode = true;
                } else if (dataStr[i].equals("受订单号")) {
                    usedOrderInfo.setOrderNumber(dataStr[++i]);
                    existsOrder = true;
                } else if (dataStr[i].equals("设计编号")) {
                    usedOrderInfo.setDesignNumber(dataStr[++i]);
                }else if (dataStr[i].equals("编制日期")) {
                    usedOrderInfo.setDesignDate(dataStr[++i]);
                }else if (dataStr[i].equals("设计单位")) {
                    usedOrderInfo.setArchitect(dataStr[++i]);
                }else if (dataStr[i].equals("数量")) {
                    usedOrderInfo.setNumber(dataStr[++i]);
                }else if (dataStr[i].equals("产品类别")) {
                    usedOrderInfo.setProductCategory(dataStr[++i]);
                }else if (dataStr[i].equals("产品系列")) {
                    usedOrderInfo.setProductLine(dataStr[++i]);
                }else if (dataStr[i].equals("交货日期")) {
                    usedOrderInfo.setDeliveryDate(dataStr[++i]);
                }else if (dataStr[i].equals("运输方式")) {
                    usedOrderInfo.setModeOfShipping(dataStr[++i]);
                }else if (dataStr[i].equals("部门")) {
                    usedOrderInfo.setDepartment(dataStr[++i]);
                }else if (dataStr[i].equals("设计人")) {
                    usedOrderInfo.setDesigner(dataStr[++i]);
                }else if (dataStr[i].equals("客户编码")) {
                    usedOrderInfo.setCustomerCode(dataStr[++i]);
                }else if (dataStr[i].equals("生产车间")) {
                    usedOrderInfo.setManufacturingShop(dataStr[++i]);
                }else if (dataStr[i].equals("客户名称")) {
                    usedOrderInfo.setCustomerName(dataStr[++i]);
                }else if (dataStr[i].equals("客户型号")) {
                    usedOrderInfo.setCustomerModel(dataStr[++i]);
                }else if (dataStr[i].equals("规格型号")) {
                    usedOrderInfo.setDeviceType(dataStr[++i]);
                }
            }
        }

        if (existsCode && existsOrder) {
            existsCode = false;
            existsOrder = false;
            System.out.println("成品代码:" + usedOrderInfo.getDeviceCode() + ";" + "受订单号:" + usedOrderInfo.getOrderNumber());
            relaseQR = usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode();
            System.out.println("usedOrderInfo:"+usedOrderInfo);
            try{
                generateQRCode(relaseQR, baseUrl);
            }catch (Exception e) {
                System.out.println(relaseQR);
            }
        }
    }

    @Override
    public void generateQRCode(String text, String baseUrl) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE);
        Path path = FileSystems.getDefault().getPath(baseUrl + qrcodePath + text +".jpeg");
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
            drawing.setAnchorArray(new CTAnchor[] { anchor1 });// 添加浮动属性
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


}
