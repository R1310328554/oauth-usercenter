package com.lk.learn.oauth.usercenter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lk.learn.oauth.usercenter.controller.BaseController;
import com.lk.learn.oauth.usercenter.entity.OauthUser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/weibo/user/")
public class WeiboController extends BaseController {

    /*
         目前配置的微博的授权回调页：   http://192.168.1.103:8999/v1/weibo/user/login
     */
    @RequestMapping("/login")
    public void login(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("微博授权码回调，code： " + code);
        String redirect = "https://api.weibo.com/oauth2/access_token?client_id=973886123&client_secret=3253f16e8324a73f6ede08c7405c0bad&grant_type=authorization_code&redirect_uri=http%3A%2F%2F192.168.1.103%3A8999%2Fv1%2Fweibo%2Fuser%2Flogin&code=";
        String accessTokenUrl = redirect + code;
        HttpResponse accessTokenResp = HttpRequest.post(accessTokenUrl).send();
        String text = accessTokenResp.bodyText();
        log.info("微博access_token回调： " + text);
        JSONObject jsonObject = JSON.parseObject(text);
        int statusCode = accessTokenResp.statusCode();
        if (statusCode != 200){
            int error_code = jsonObject.getInteger("error_code");
            //{"request":"/oauth2/access_token","error_code":21322,"error":"redirect_uri_mismatch","error_uri":"/oauth2/access_token"}
            response.sendRedirect(token_callback + "/err500");
            return;
        }

        String access_token = jsonObject.getString("access_token");
        String uid = jsonObject.getString("uid");
        String remind_in = jsonObject.getString("remind_in");
        String expires_in = jsonObject.getString("expires_in");
        OauthUser oauthUser = retrieveRemoteUser(uid, access_token);
        userService.saveUser(oauthUser);
        generateCookie(response, access_token, oauthUser, "weibo");

        // 返回浏览器端
        response.sendRedirect(token_callback);
    }


    @Override
    protected OauthUser retrieveRemoteUser(String userId, String access_token) {
        System.out.println("微博.获取用户: userId = [" + userId + "], jws_token = [" + access_token + "]");
        HttpRequest httpRequest = HttpRequest.get("https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + userId);
        // HttpRequest httpRequest = HttpRequest.get("https://api.weibo.com/2/users/show.json?uid="+uid);
        //3:刷新access_token (if need)
        //4:获取用户信息
        // String getUerinfoUrl = String.format("https://api.weibo.com/2/statuses/mentions.json?access_token=%s", access_token);
        /*httpRequest.contentType("application/json");
        httpRequest.accept("application/json");
        httpRequest.header("Authorization", "Bearer " + access_token); // 微博无需Authorization请求头
        */

        HttpResponse userinfoResp = httpRequest.send();
        String text = userinfoResp.charset("utf-8").bodyText();
        log.info("微博获取用户结果 = " + text);
        JSONObject wxUser = JSON.parseObject(text);
        wxUser.put("username", wxUser.getString("screen_name"));
        wxUser.put("userId", wxUser.getString("id"));
        wxUser.put("avatar", wxUser.getString("profile_image_url"));
        OauthUser oauthUser = wxUser.toJavaObject(OauthUser.class);
//        OauthUser oauthUser = JSON.parseObject(bodyText, OauthUser.class);
//        oauthUser.setAvatar(oauthUser.getProfile_image_url());
//        oauthUser.setUsername(oauthUser.getScreen_name());
//        oauthUser.setUserId(oauthUser.getId());
//        oauthUser.setUserInfo(bodyText);
        return oauthUser;
    }

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("微博 .cancel");
        return "cancel 123";
    }
}
