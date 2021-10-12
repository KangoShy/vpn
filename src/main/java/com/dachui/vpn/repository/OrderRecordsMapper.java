package com.dachui.vpn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dachui.vpn.model.po.OrderRecordsPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderRecordsMapper extends BaseMapper<OrderRecordsPO> {
}
