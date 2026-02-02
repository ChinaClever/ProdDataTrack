package cn.iocoder.yudao.module.pdu.service;
//
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.*;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduQualityTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduTestDataPageReqVO;
import cn.iocoder.yudao.module.pdu.entity.PduQualityTestData;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.pdu.entity.PduUsedOrderInfo;
import com.google.zxing.WriterException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PduQualityTestDataService {
    PageResult<PduQualityTestDataVo> getTestDataPage(PduTestDataPageReqVO pageReqVO);

    PageResult<PduUsedOrderInfo> getFileListPage(PduFileListPageReqVO pageReqVO);

    PduUsedOrderInfo fileUpload(MultipartFile file) throws IOException;

    void getOrderInfo(String[] dataStr, PduUsedOrderInfo usedOrderInfo, String baseUrl);

    void generateQRCode(String text, String baseUrl) throws IOException, WriterException;

    CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject, String deskFileName, int width,
                                  int height, int leftOffset, int topOffset, boolean behind);

    void insertQRPicture(XWPFTableCell cell, String imgFile, int width, int height, int units)
            throws IOException, InvalidFormatException;

    PduReportRespVO getReportInfo(PduReportReqVO reqVO);

    InternalReportVo getInternalReport(PduReportReqVO reqVO);

    void updateTestData(PduQualityTestData pduQualityTestData);

    void deleteTestData(Integer id);

    void savePduQualityTestData(PduQualityTestDataVo pduQualityTestDataVo);
}
