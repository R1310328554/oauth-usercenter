package com.lk.learn.oauth.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.lk.learn.oauth.usercenter.entity.OauthUser;
import com.lk.learn.oauth.usercenter.service.UserService;
import com.lk.learn.oauth.usercenter.cfg.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseController {

    public static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public static final String TOKEN_SEPARATOP = "___";
    @Autowired
    protected UserService userService;
    @Autowired
    protected JwtUtil jwtUtil;

    @Value("${oauth.token.callback}")
    public String token_callback;

    protected void generateCookie(HttpServletResponse response, String access_token, OauthUser oauthUser, String thirdTypeStr) throws Exception {
        String jws_token = jwtUtil.createToken(access_token + TOKEN_SEPARATOP + oauthUser.getUserId());
        Cookie access_tokenCookie = new Cookie("aimee-test-token", jws_token);
        access_tokenCookie.setMaxAge(1000 * 60 * 60 * 24);
        access_tokenCookie.setPath("/");
        Cookie thirdType = new Cookie("thirdType", thirdTypeStr);
        thirdType.setMaxAge(1000 * 60 * 60 * 24);
        thirdType.setPath("/");
        response.addCookie(thirdType);
        response.addCookie(access_tokenCookie);
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public JSONObject getUserInfo(@CookieValue(value = "aimee-test-token", required = true)String access_token
            , @CookieValue(value = "thirdType", required = false)String thirdType
            , @CookieValue(value = "uid", required = false)String uid) throws Exception {
        log.info("??????????????? " + thirdType + "," + access_token);
        String jws_token = "";
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jws_token = jwtUtil.parseToken(access_token).getSubject();
        //OK, we can trust this JWT
        String[] split = jws_token.split(TOKEN_SEPARATOP);
        OauthUser oauthUser = new OauthUser();
        if (split.length == 2) {
            String userId = split[1];
            oauthUser = userService.findUserByUserId(userId);
            if (oauthUser == null) {
                oauthUser = retrieveRemoteUser(userId, jws_token);// ???????????????????????????????????????
                userService.saveUser(oauthUser);
            }
        }
        JSONObject ret = new JSONObject();
        ret.put("msg", "ok");
        ret.put("data", oauthUser);
        ret.put("code", 0);
        return ret;
    }

    protected OauthUser retrieveRemoteUser(String userId, String jws_token) {
        return null;
    }


    /*
        ???????????????
     */
    @RequestMapping("/callback")
    public String wbcallback(@RequestParam("code") String code) throws IOException {
        return "callback";
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JSONObject logout(@CookieValue(value = "aimee-test-token", required = false)String access_token
            , @CookieValue(value = "thirdType", required = false)String thirdType, HttpServletResponse response) throws Exception{
        log.info("??????????????? access_token = [" + access_token + "], thirdType = [" + thirdType + "], response = [" + response + "]");
        Cookie access_tokenCookie = new Cookie("aimee-test-token", "");
        access_tokenCookie.setMaxAge(1000*60*60*24);
        access_tokenCookie.setPath("/");
        Cookie thirdTypeCookie = new Cookie("thirdType", "");
        thirdTypeCookie.setMaxAge(1000*60*60*24);
        thirdTypeCookie.setPath("/");
        response.addCookie(thirdTypeCookie);
        response.addCookie(access_tokenCookie);
        JSONObject ret = new JSONObject();
        ret.put("msg", "ok");
        ret.put("code", 0);
        return ret;
    }

}
