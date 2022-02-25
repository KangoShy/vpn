package com.dachui.vpn.enums;
/* *
 * 功能：
 * 版本：1.0
 * 创建/修改日期：2021/12/26
 * 说明：
 * 作者：root0day
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LoginStatusEnum {

  LOGIN_SUCCESS("200", "登录成功"),
  LOGIN_TIMEOUT("403", "未登录,请重新登录"),
  LOGIN_PASSWORD_ERROR("401", "账号或密码错误"),
  LOGIN_ACCOUNT_DISABLED("401", "账户被禁用"),
  LOGIN_FAIL("401", "登录失败"),
  LOGIN_NO_PERMISSION("405", "权限不足"),
  LOGIN_OUT("200", "退出成功");

  private String code;
  private String desc;

  public static Map<String, Object> makeResultMap(LoginStatusEnum statusEnum) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("code", statusEnum.getCode());
    map.put("msg", statusEnum.getDesc());
    return map;
  }

}
