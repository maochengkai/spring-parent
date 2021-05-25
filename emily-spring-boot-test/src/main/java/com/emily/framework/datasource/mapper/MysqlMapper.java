package com.emily.framework.datasource.mapper;


import com.emily.framework.datasource.annotation.TargetDataSource;
import com.emily.framework.datasource.po.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Emily
 * @Description: Description
 * @Version: 1.0
 */
@Mapper
@TargetDataSource("mysql1")
public interface MysqlMapper {
    /**
     * 查询接口
     */
    String findLocks(String lockName);

    /**
     * 新增接口
     * @param schedName
     * @param lockName
     */
    @TargetDataSource("mysql")
    void insertLocks(String schedName, String lockName);

    void delLocks(String lockName);
}
