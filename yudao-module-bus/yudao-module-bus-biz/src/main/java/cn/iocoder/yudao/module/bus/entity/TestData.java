package cn.iocoder.yudao.module.bus.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "testdata")
public class TestData {
    @Id
    private String id;

    private String product_sn;

    private String module_sn;

    private String soft_version;

    private String language_select;

    private String order_id;

    private String order_num;

    private String test_num;

    private String dev_name;

    private String tool_name;

    private String test_result;

    private String start_time;

    private String test_step;

    private String test_item;

    private String test_cfg;

    private String test_request;

    private String test_process;

    private String end_time;
}
