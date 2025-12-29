package cn.iocoder.yudao.module.system.service.clerk;

import cn.iocoder.yudao.module.system.dal.dataobject.clerk.FactoryReportClerk;

public interface ClerkService {
    FactoryReportClerk getFactoryReportClerk();


    int updateFactoryReportClerk(FactoryReportClerk factoryReportClerk);
}
