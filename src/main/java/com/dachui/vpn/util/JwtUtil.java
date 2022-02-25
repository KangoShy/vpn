package com.dachui.vpn.util;

import com.alibaba.fastjson.JSONObject;
import com.dachui.vpn.common.GlobalConstants;
import com.dachui.vpn.exception.UserCenterException;
import com.dachui.vpn.model.UserInfo;
import com.dachui.vpn.model.result.ServiceResponseStatus;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* *
 * 功能：
 * 版本：1.0
 * 创建/修改日期：2021/12/29
 * 说明：
 * 作者：root0day
 */
public class JwtUtil {

  private static final String JWT_SECRET = "12365465465465465";

  public JwtUtil() {}

  public static String createJwtToken(UserInfo userInfo) {
    String jwtToken;
    try {
      Key key = new SecretKeySpec(JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
      Calendar instance = Calendar.getInstance();
      instance.setTime(new Date());
      instance.add(Calendar.MINUTE, GlobalConstants.Config_Constants.TOKEN_TIMEOUT);
      Date expirationTime = instance.getTime();
      JwtBuilder jwtBuilder =
          Jwts.builder()
              .setSubject(JSONObject.toJSONString(userInfo))
              .signWith(SignatureAlgorithm.HS256, key)
              .setExpiration(expirationTime);
      jwtToken = jwtBuilder.compact();
      return jwtToken;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("创建token失败");
    }
  }

  public static UserInfo parseJwtToken(String jwtToken) throws Exception {
    if (StringUtil.isEmpty(jwtToken)) {
      throw new UserCenterException("参数异常", ServiceResponseStatus.SYSTEM_ERROR);
    } else {
      Key key = new SecretKeySpec(JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
      Map jwtClaims;
      try {
        jwtClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
      } catch (ExpiredJwtException var7) {
        throw new UserCenterException("token失效", ServiceResponseStatus.SYSTEM_ERROR);
      }
      String userInfoJson = ((Claims) jwtClaims).getSubject();
      UserInfo userInfo = null;
      if (userInfoJson != null) {
        try {
          userInfo = JSONObject.parseObject(userInfoJson, UserInfo.class);
        } catch (Exception ignore) {
          throw new UserCenterException("token解析异常", ServiceResponseStatus.SYSTEM_ERROR);
        }
      }
      return userInfo;
    }
  }
}
