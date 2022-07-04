
package com.lk.learn.oauth.usercenter.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lk.learn.oauth.usercenter.entity.OauthUser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/github/user/")
public class GithubController extends BaseController {

    /*
         目前配置的Github的授权回调页：   http://192.168.1.103:8999/v1/github/user/login
     */
    @RequestMapping("/login")
    public void login(@RequestParam("code") String code, HttpServletResponse response) {
        log.info("Github .callback code " + code);
        String redirect = "https://github.com/login/oauth/access_token?client_id=ee0e0710193b7cac1e68&redirect_uri=http://192.168.1.103:8999/v1/github/user/login&client_secret=d544353486c9e083d9c7437187236e8f191c6632&code=";
//        String accessTokenUrl = String.format(redirect, code);
        String accessTokenUrl = redirect +  code;
        log.info("accessTokenUrl = " + accessTokenUrl);
        HttpRequest request = HttpRequest.post(accessTokenUrl);
        request.contentType("application/json");
        request.accept("application/json");
        HttpResponse accessTokenResp = request.send();
        String text = accessTokenResp.bodyText();
        log.info("HttpResponse text = " + text);

        JSONObject jsonObject = JSON.parseObject(text);
        log.info("jsonObject = " + jsonObject);
        if (!"".equals(jsonObject.getString("error"))) {
        }

        String access_token = jsonObject.getString("access_token");
        String scope = jsonObject.getString("scope");
        String token_type = jsonObject.getString("token_type");
        //3:刷新access_token (if need)
        //4:获取用户信息
        // {"message":"Must specify access token via Authorization header. https://developer.github.com/changes/2020-02-10-deprecating-auth-through-query-param","documentation_url":"https://docs.github.com/v3/#oauth2-token-sent-in-a-header"}
//        String getUerinfoUrl = String.format("https://api.github.com/user?access_token=%s", access_token);
        // Authorization: `Bearer ${access_token}`
        HttpRequest httpRequest = HttpRequest.get("https://api.github.com/user");
        httpRequest.contentType("application/json");
        httpRequest.accept("application/json");
        httpRequest.header("Authorization", "Bearer " + access_token);
        httpRequest.timeout(30 * 1000); // 超时 设置长一点， 因为“墙”
        HttpResponse userinfoResp = httpRequest.send();
        String bodyText = userinfoResp.charset("utf-8").bodyText();
        OauthUser oauthUser = JSON.parseObject(bodyText, OauthUser.class);
        oauthUser.setAvatar(oauthUser.getAvatar_url());
        oauthUser.setUserId(oauthUser.getId());
        oauthUser.setUsername(oauthUser.getLogin());
        oauthUser.setCreated_at(oauthUser.getCreateDate());
        oauthUser.setUserInfo(bodyText);
        log.info("GithubUser = " + oauthUser);
        userService.saveUser(oauthUser);

        generateCookie(response, access_token, oauthUser, "github");
        try {
            response.sendRedirect(token_callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject getUser(String access_token, String userId, String jws_token) {
        HttpRequest httpRequest = HttpRequest.get("https://api.github.com/user");
        httpRequest.contentType("application/json");
        httpRequest.accept("application/json");
        httpRequest.header("Authorization", "Bearer " + jws_token);
        HttpResponse userinfoResp = httpRequest.send();
        JSONObject wxUser = JSON.parseObject(userinfoResp.charset("utf-8").bodyText());
        log.info("GithubUser = " + wxUser);
        wxUser.put("username", wxUser.getString("login"));
        wxUser.put("userId", wxUser.getString("id"));
        wxUser.put("avatar", wxUser.getString("avatar_url"));
        return wxUser;
    }

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("Github .cancel");
        return "cancel 123";
    }
}
