package com.lk.learn.oauth.usercenter.cfg;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.lk.learn.oauth.usercenter.GithubController;
import com.lk.learn.oauth.usercenter.OauthUser;
import io.jsonwebtoken.security.Keys;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;

@Component
@Slf4j
public class JwtUtils {


    private static String secretKey = "aXNsZWVfaGFoYQ==";

    private static String aa;//测试静态变量注入

    /**
     * 静态变量注入
     * 从配置文件读取jjwt.key属性
     * 注入key，set方法不能是static
     * @param secretKey
     */
    @Value("${jjwt.key}")
    public void setSecretKey(String secretKey) {
        JwtUtils.secretKey = secretKey;
    }

    /**
     * 静态实体变量注入
     * jjwtProperties需要配置：@ConfigurationProperties(prefix = "jjwt", ignoreUnknownFields = true)
     * @param jjwtProperties
     */
    @Autowired
    public void setSecretKey(JjwtProperties jjwtProperties) {
        JwtUtils.aa = jjwtProperties.getKey();
    }


    private static String KEY_CLAIMS = "key_claims";


    private JwtUtils(){

    }


    /**
     * 生成token
     * @return
     * @throws ParseException
     * @param SUBJECT
     */
    public static String getToken(String SUBJECT) throws ParseException {
        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("aa===" + aa);
        Date now = new Date();
        Date expirationDate = DateUtils.addHours(now, 2);//增加2分钟的过期时间，用于测试
//        log.info("now===" + DateUtils.formatDateTime(now));
//        log.info("expirationDate===" + DateUtils.formatDateTime(expirationDate));

        Map<String, Object> claims = new HashMap<String, Object>();
        OauthUser user = new OauthUser();
        user.setId("123");
        user.setName("张三");
        claims.put(KEY_CLAIMS, user);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts.builder()
                        .setClaims(claims)//必须放最前面，不然后面设置的东西都会没有：如setExpiration会没有时间
                        .setId(UUID.randomUUID().toString())
                        .setSubject(SUBJECT)
                        .setIssuedAt(now)
                        .setExpiration(expirationDate)//过期时间
                        // .signWith(SignatureAlgorithm.HS256, getSecretKey())
                        .signWith(key)
                        .compact();

        log.info("token===" + token);

        return token;
    }


    /**
     * 解析token，并返回User对象
     * @param token
     * @return
     * @throws ParseException
     */
    public static String parseToken(String token) throws ParseException {
        String msg = null;
        try {
            String access_token = "ghoeOftatPvQwgj24AEwgOHY4dth7qs7V3M1Vxo";
            String userId = "9073658";
            String subject = access_token + "|" + userId;
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key)
//                    .requireSubject(access_token)
//                    .build().parseClaimsJws(token);//.getBody().getSubject();
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(key)
                    .requireSubject(access_token)//校验必须有这个属性，可以省略这步
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();//Claims是一个Map

            log.info("claims===" +  (claims));
            log.info("claims.getIssuedAt()===" + claims.getIssuedAt());
            log.info("claims.getExpiration()===" + claims.getExpiration());
            return claims.get(KEY_CLAIMS)+"";

        }catch (SignatureException se) {
            msg = "密钥错误";
            log.error(msg, se);

        }catch (MalformedJwtException me) {
            msg = "密钥算法或者密钥转换错误";
            log.error(msg, me);

        }catch (MissingClaimException mce) {
            msg = "密钥缺少校验数据";
            log.error(msg, mce);

        }catch (ExpiredJwtException mce) {
            msg = "密钥已过期";
            log.error(msg, mce);

        }catch (JwtException jwte) {
            msg = "密钥解析错误";
            log.error(msg, jwte);
        }

        return null;
    }


    /**
     * 获取自定义密钥
     * @return
     */
    private static byte[] getSecretKey() {
        //log.info("secretKey = " + secretKey);
        if(StringUtil.isBlank(secretKey)) {
            throw new RuntimeException("jjwt配置的密钥不能为空");
        }
        return Decoders.BASE64.decode(secretKey);
    }


    public static void main(String[] args) throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setExpire(1000*3600*24*1L);
        jwtUtil.setExpire(5184000L);
        jwtUtil.setHeader("h1");
        jwtUtil.setHeader("Authorization");
        jwtUtil.setIssuer("lk");
        jwtUtil.setSecret("aXNsZWVfaGFoYQ==");
        jwtUtil.setSecret("99c2918fe19d30bce25abfac8a3733ec");

        String access_token = "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";
        String userId = "9073658";
        String subject = access_token + GithubController.TOKEN_SEPARATOP + userId;
        String token2 = jwtUtil.createToken(subject);
        System.out.println("token = " + token2);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaG9lT2Z0YXRQdlF3Z2oyNEFFd2dPSFk0ZHRoN3FzN1YzTTFWeG98OTA3MzY1OCIsImlhdCI6MTY1Njg1NTc2MiwiZXhwIjoxNjYyMDM5NzYyLCJpc3MiOiJsayJ9.-ieP8z2hKLEgIryDkPyN0TSwkKvRVla4g01-Ri1jeF4

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaG9fZU9mdGF0UHZRd2dqMjRBRXdnT0hZNGR0aDdxczdWM00xVnhvXzkwNzM2NTgifQ.sjrOXRwES-A4oy84G6D-wN-PVmPIBqMw945Abnw-Zmg";
        System.out.println("token = " + token);
        Claims claims = jwtUtil.parseToken(token2);
        String subject1 = claims.getSubject();
        System.out.println("subject1 = " + subject1);

    }
    public static void main3(String[] args) throws Exception {
        String access_token = "ghoeOftatPvQwgj24AEwgOHY4dth7qs7V3M1Vxo";
        String userId = "9073658";
        String subject = access_token + "|" + userId;

        String token = getToken(access_token);
        System.out.println("token = " + token);

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJrZXlfY2xhaW1zIjp7InVzZXJJZCI6bnVsbCwiaWQiOiIxMjMiLCJlbmFibGUiOm51bGwsImNyZWF0ZWRfYXQiOm51bGwsImNyZWF0ZURhdGUiOm51bGwsImxvZ2luIjpudWxsLCJ1c2VybmFtZSI6bnVsbCwic2NyZWVuX25hbWUiOm51bGwsIm5hbWUiOiLlvKDkuIkiLCJwYXNzd29yZCI6bnVsbCwiYXZhdGFyIjpudWxsLCJhdmF0YXJfdXJsIjpudWxsLCJwcm9maWxlX2ltYWdlX3VybCI6bnVsbCwiZW1haWwiOm51bGwsInVzZXJJbmZvIjpudWxsLCJsb2NhdGlvbiI6bnVsbCwic3RhdHVzIjpudWxsLCJsYXN0TG9naW5EYXRlIjpudWxsfSwianRpIjoiODRkYTY0ZTgtZjc3Ni00OGU4LWI4ZjItZWI2ZjY3NjM0YmNlIiwic3ViIjoiZ2hvZU9mdGF0UHZRd2dqMjRBRXdnT0hZNGR0aDdxczdWM00xVnhvfDkwNzM2NTgiLCJpYXQiOjE2NTY4NTQwMzUsImV4cCI6MTY1Njg2MTIzNX0.1rPEAKyG_LKh0yW2LRPVTVCp15aWgBtjIaixlgo-pLU";
        String  oauthUser = parseToken(token);

        //map转实体
        OauthUser user = JSON.parseObject(oauthUser, OauthUser.class);
        log.info("user===" +  (user));
        System.out.println("oauthUser = " + oauthUser);

    }


}
