package com.emily.infrastructure.test.service.impl;

import com.emily.infrastructure.test.mapper.mysql.MysqlMapper;
import com.emily.infrastructure.test.po.World;
import com.emily.infrastructure.test.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: spring-parent
 * @description:
 * @author: Emily
 * @create: 2021/05/13
 */
@Service
public class MysqlServiceImpl implements MysqlService {
    @Autowired
    private MysqlMapper mysqlMapper;

    @Override
    public List<World> getMysql() {
        return mysqlMapper.getMysql();
    }

    @Override
    public void insertMysql() {
        mysqlMapper.insertMysql("18321160687", "23");
    }
}
