package cn.iocoder.yudao.module.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("bus_file_info")
public class UsedOrderInfo implements Serializable{
    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String designNumber;

    private String designDate;

    private String architect;

    private String number;

    private String productCategory;

    private String productLine;

    private String deliveryDate;

    private String modeOfShipping;

    private String department;

    private String designer;

    private String customerCode;

    private String manufacturingShop;

    private String orderNumber;

    private String customerName;

    private String customerModel;

    private String deviceCode;

    private String deviceType;

    private String downloadUrl;

}
