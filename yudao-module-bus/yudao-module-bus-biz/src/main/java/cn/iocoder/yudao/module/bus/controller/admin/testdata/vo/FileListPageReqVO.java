package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class FileListPageReqVO {
    private Integer pageNo;

    private Integer pageSize;

    private String keyword;
}
