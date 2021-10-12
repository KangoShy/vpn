package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dachui.vpn.repository.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public Object getData() {
        return testMapper.selectOne(new QueryWrapper());
    }
}
