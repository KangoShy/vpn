package com.dachui.vpn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dachui.vpn.model.po.VpnComboPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VpnComboMapper extends BaseMapper<VpnComboPO> {
}
