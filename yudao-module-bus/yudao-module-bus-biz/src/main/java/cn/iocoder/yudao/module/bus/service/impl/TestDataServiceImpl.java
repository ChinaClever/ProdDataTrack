package cn.iocoder.yudao.module.bus.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.module.bus.entity.UsedOrderInfo;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileConfigDO;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import cn.iocoder.yudao.module.infra.service.file.FileConfigService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.alibaba.fastjson2.JSONWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
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
import java.io.FileInputStream;
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

    private static int dataNum = 0;
    private static final int QR_CODE_SIZE = 200;

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
    public String fileUpload(MultipartFile file) throws IOException {
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
        String Url = baseUrl+'\\'+uniqueFileName;

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
//                    for (String cell : rowData) {
//                        System.out.println(cell);
//                    }
                    getOrderInfo(rowData);
                });
            }
            XWPFTable tableRow = tables.get(0);
            List<XWPFTableCell> tableCells = tableRow.getRow(0).getTableCells();
//            for (XWPFTableCell cell : tableCells) {
//                System.out.println(cell.getText());
//            }
            document.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return save_path;
    }

    @Override
    public void getOrderInfo(String[] dataStr) {
        String relaseQR;
        UsedOrderInfo usedOrderInfo = new UsedOrderInfo();
        for (int i = 0; i < dataStr.length; i++) {
            if (dataStr[i] != null) {
                if (dataStr[i].equals("成品代码")) {
                    usedOrderInfo.setDeviceCode(dataStr[++i]);
                    usedOrderInfo.setExistsCode(true);
                } else if (dataStr[i].equals("受订单号")) {
                    usedOrderInfo.setOrderNumber(dataStr[++i]);
                    usedOrderInfo.setExistsOrder(true);
                }
            }
        }
        System.out.println("isExistsOrder:"+usedOrderInfo.isExistsOrder());
        System.out.println("isExistsCode:"+usedOrderInfo.isExistsCode());

        if (usedOrderInfo.isExistsOrder() && usedOrderInfo.isExistsCode()) {
            usedOrderInfo.setExistsCode(false);
            usedOrderInfo.setExistsOrder(false);
            System.out.println("成品代码:" + usedOrderInfo.getOrderNumber() + ";" + "受订单号:" + usedOrderInfo.getDeviceCode());
            relaseQR = usedOrderInfo.getOrderNumber() + "+" + usedOrderInfo.getDeviceCode();
            System.out.println("usedOrderInfo:"+usedOrderInfo);
            try{
                generateQRCode(relaseQR);
            }catch (Exception e) {
                System.out.println(relaseQR);
            }
        }
    }

    @Override
    public void generateQRCode(String text) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE);
        Path path = FileSystems.getDefault().getPath("./test_read_data/"+ text +".jpeg");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("二维码已生成：" + path);
    }

}
