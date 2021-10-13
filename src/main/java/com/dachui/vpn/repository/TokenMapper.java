package com.dachui.vpn.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dachui.vpn.login.domain.TokenDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:26
 * @Description:
 */
@Mapper
public interface TokenMapper extends BaseMapper<TokenDomain> {

}
