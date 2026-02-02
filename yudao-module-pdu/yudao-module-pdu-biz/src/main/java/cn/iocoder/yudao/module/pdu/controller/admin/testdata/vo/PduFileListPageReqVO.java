package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class PduFileListPageReqVO {
    private Integer pageNo;

    private Integer pageSize;

    private String keyword;
}
