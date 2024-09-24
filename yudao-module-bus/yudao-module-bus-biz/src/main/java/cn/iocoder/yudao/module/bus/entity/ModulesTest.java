package cn.iocoder.yudao.module.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("module_test")
public class ModulesTest {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderId;

    private String productSn;

    private String moduleSn;

    private String moduleType;

    private String softVersion;

    private String languageSelect;

    private String testStep;

    private String testItem;

    private String testRequire;

    private String testResult;

    private String judgeResult;

    private String testTime;

    private String allData;

}
