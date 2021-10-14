package com.dachui.vpn.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dachui.vpn.model.UserInfo;
import com.dachui.vpn.model.po.UcenterMember;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 15:17
 * @Description:
 */

public class TokenUtils {

    private static Logger log = LoggerFactory.getLogger(TokenUtils.class);

    //设置过期时间
    private static final long EXPIRE_DATE = 1000 * 60 * 5;
    //token秘钥
    private static final String TOKEN_SECRET = "";

    public static final int CALENDAR_INTERVAL = 60 * 2;

    //实现签名方法
    public static String getToken(UcenterMember userInfo) {

        String jwtToken;
        try {
            String JWT_SECRET = "r54eg5sae4r665ser54rg";
            Key key =
                    new SecretKeySpec(
                            JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName()); // 这里是加密解密的key。
            JwtBuilder jwtBuilder =
                    Jwts.builder() // 返回的字符串便是jwt串了
                            .setSubject(JsonUtil.toJsonFromObject(userInfo))
                            .signWith(SignatureAlgorithm.HS256, key) // 设置算法（必须）
                            .setExpiration(
                                    DateUtils.addMinutes(new Date(), CALENDAR_INTERVAL)); // 这个是全部设置完成后拼成jwt串的方法
            jwtToken = jwtBuilder.compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建token失败");
        }
        return jwtToken;
    }

    /**
     * @desc 验证token，通过返回true
     * @params [token]需要校验的串
     **/
    public static boolean verify(String token) throws IOException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.info("Token过期,需要重新登录！");
        }
        return false;
    }

    /**
     * 获取token中信息 userName
     *
     * @param token
     * @return
     */

    public static UserInfo getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String userInfoJson = jwt.getSubject();
            UserInfo userInfo = new UserInfo();
            if (StringUtil.isEmpty(userInfoJson)) {
                return userInfo;
            }
            userInfo = JsonUtil.toBeanFromStr(userInfoJson, UserInfo.class);
            return userInfo;
            //return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
