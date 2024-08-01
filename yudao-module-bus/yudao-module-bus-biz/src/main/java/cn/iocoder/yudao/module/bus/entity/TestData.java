package cn.iocoder.yudao.module.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("test_data")
public class TestData {
    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String product_sn;

    private String module_sn;

    private String soft_version;

    private String language_select;

    private String order_id;

    private String order_num;

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

    private String all_data;
}
