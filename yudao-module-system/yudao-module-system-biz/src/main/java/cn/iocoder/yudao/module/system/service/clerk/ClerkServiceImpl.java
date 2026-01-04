package cn.iocoder.yudao.module.system.service.clerk;

import cn.iocoder.yudao.module.system.dal.dataobject.clerk.FactoryReportClerk;
import cn.iocoder.yudao.module.system.dal.mysql.clerk.ClerkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClerkServiceImpl implements ClerkService {

    @Autowired
    private ClerkMapper clerkMapper;

    @Override
    public FactoryReportClerk getFactoryReportClerk(String identification) {
        return clerkMapper.selectOne("identification", identification);
    }

    @Override
    public int updateFactoryReportClerk(FactoryReportClerk factoryReportClerk) {
        factoryReportClerk.setId(factoryReportClerk.getId());
        return clerkMapper.updateById(factoryReportClerk);
    }
}
