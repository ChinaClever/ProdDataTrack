package cn.iocoder.yudao.module.system.service.mac;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.http.CRMUtils;
import cn.iocoder.yudao.framework.common.util.mac.MacAddressUtils;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainPageReqVo;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainReqVo;
import cn.iocoder.yudao.module.system.dal.dataobject.mac.MacMaintain;
import cn.iocoder.yudao.module.system.dal.mysql.mac.MacMaintainMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MacMaintainServiceImpl implements MacMaintainService {

    @Autowired
    private MacMaintainMapper macMaintainMapper;

    @Transactional
    @Override
    public String getAddress(MacMaintainReqVo reqVo) {
        // 构建查询条件
        QueryWrapper<MacMaintain> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("client_name")
                .isNull("order_id")
                .isNull("product_sn")
                .isNull("module_sn")
                .orderByDesc("id");

        // 查询符合条件的最后一个 MAC 地址
        MacMaintain macMaintain = macMaintainMapper.selectOne(queryWrapper);
        if (macMaintain == null) {
            throw new IllegalStateException("No available MAC address found");
        }

        // 更新现有记录
        Map<String, String> crm = CRMUtils.getInfoByCRM(reqVo.getOrderId(), reqVo.getProductSn(), "pdu");
        macMaintain.setClientName(crm.get("CUSTOMERNAME"));
        macMaintain.setOrderId(reqVo.getOrderId());
        macMaintain.setProductSn(reqVo.getProductSn());
        macMaintain.setModuleSn(reqVo.getModuleSn());
        macMaintainMapper.updateById(macMaintain);

        // 获取现有 MAC 地址并生成新的 MAC 地址
        String macAddress = macMaintain.getMacAddress();
        String newMacAddress = MacAddressUtils.incrementMacAddress(macAddress);

        // 插入新的记录
        MacMaintain newMacMaintain = new MacMaintain();
        newMacMaintain.setMacAddress(newMacAddress);
        macMaintainMapper.insert(newMacMaintain);

        return macAddress;
    }

    @Override
    public PageResult<MacMaintain> macAddressDistributionSituation(MacMaintainPageReqVo pageReqVo) {
        QueryWrapper<MacMaintain> queryWrapper = new QueryWrapper<>();
        Page<MacMaintain> page = new Page<>(pageReqVo.getPageNo(), pageReqVo.getPageSize());
        if (pageReqVo.getMacAddress() != null) {
            queryWrapper.like("mac_address", pageReqVo.getMacAddress());
        }
        if (pageReqVo.getClientName() != null) {
            queryWrapper.like("client_name", pageReqVo.getClientName());
        }
        if (pageReqVo.getModuleSn() != null) {
            queryWrapper.like("module_sn", pageReqVo.getModuleSn());
        }
        if (pageReqVo.getProductSn() != null) {
            queryWrapper.like("product_sn", pageReqVo.getProductSn());
        }
        if (pageReqVo.getOrderId() != null) {
            queryWrapper.like("order_id", pageReqVo.getOrderId());
        }
//        queryWrapper.isNotNull("order_id");

        IPage<MacMaintain> resultPage;
        resultPage = macMaintainMapper.selectPage(page, queryWrapper);
        // 构建分页结果对象
        PageResult<MacMaintain> pageResult = new PageResult<>();
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setList(resultPage.getRecords());
        return pageResult;
    }
}
