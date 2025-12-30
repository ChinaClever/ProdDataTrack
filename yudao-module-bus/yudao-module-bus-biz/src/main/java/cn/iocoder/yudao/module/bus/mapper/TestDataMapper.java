package cn.iocoder.yudao.module.bus.mapper;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.entity.TestData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {
    default int deleteBatchTestData(String moduleSn){
        return  delete(new LambdaQueryWrapperX<TestData>()
                .eq(TestData::getModuleSn,moduleSn));
    }

}
