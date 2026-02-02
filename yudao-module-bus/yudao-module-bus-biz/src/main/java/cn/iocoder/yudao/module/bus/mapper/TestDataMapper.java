package cn.iocoder.yudao.module.bus.mapper;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.entity.TestData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {
    default int deleteBatchTestData(String moduleSn){
        return  delete(new LambdaQueryWrapperX<TestData>()
                .eq(TestData::getModuleSn,moduleSn));
    }

    @Select("WITH ranked_data AS (" +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY module_sn ORDER BY id DESC) AS rn " +
            "    FROM test_data " +
            "    WHERE test_result = '1' AND language_select = '0'" +
            ") SELECT * FROM ranked_data WHERE rn = 1")
    List<TestData> findLatestByProductSnWithConditions();

}
