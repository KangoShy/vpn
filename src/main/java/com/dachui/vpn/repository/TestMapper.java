package com.dachui.vpn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dachui.vpn.model.TestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestMapper extends BaseMapper<TestVO> {
}
