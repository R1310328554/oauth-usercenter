
package com.lk.learn.oauth.usercenter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/weibo/user/")
public class WeiboController extends BaseController {


    // 访问/securedPage， 会被转发到 ： http://localhost:8081/auth/oauth/authorize?response_type=code&client_id=R2dpxQ3vPrtfgF72&scope=user_info&state=0zaP816-tXgvmkK1O4gVuUVA8n9Q0OOlCNwpY5ukgkY%3D&redirect_uri=http://localhost:8082/login/oauth2/code/
    @RequestMapping("/securedPage")
    public String securedPage(Model model, Principal principal) {
        log.info("微博 .securedPage");
         return "securedPage";
    }

    /*
         目前配置的微博的授权回调页：   http://192.168.1.103:8999/v1/weibo/user/login
     */
    @RequestMapping("/login")
    public void login(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("微博 .callback");
        String redirect = "https://api.weibo.com/oauth2/access_token?client_id=973886123&client_secret=3253f16e8324a73f6ede08c7405c0bad&grant_type=authorization_code&redirect_uri=http%3A%2F%2F192.168.1.103%3A8999%2Fv1%2Fweibo%2Fuser%2Flogin&code=";
        String accessTokenUrl = redirect + code;
        HttpResponse accessTokenResp = HttpRequest.post(accessTokenUrl).send();
        JSONObject jsonObject = JSON.parseObject(accessTokenResp.bodyText());
        log.info("jsonObject = " + jsonObject);

        int statusCode = accessTokenResp.statusCode();
        if (statusCode != 200){
            int error_code = jsonObject.getInteger("error_code");
            //{"request":"/oauth2/access_token","error_code":21322,"error":"redirect_uri_mismatch","error_uri":"/oauth2/access_token"}
            response.sendRedirect("http://192.168.1.103:8080/err500");
            return;
        }
        String access_token = jsonObject.getString("access_token");
        String uid = jsonObject.getString("uid");
        String remind_in = jsonObject.getString("remind_in");
        String expires_in = jsonObject.getString("expires_in");
        //3:刷新access_token (if need)
        //4:获取用户信息
        // String getUerinfoUrl = String.format("https://api.weibo.com/2/statuses/mentions.json?access_token=%s", access_token);
        HttpResponse userinfoResp = HttpRequest.get("https://api.weibo.com/2/users/show.json?access_token="+access_token+ "&uid="+uid).send();
        String bodyText = userinfoResp.charset("utf-8").bodyText();
        log.info("bodyText = " + bodyText);
        OauthUser oauthUser = JSON.parseObject(bodyText, OauthUser.class);
        oauthUser.setAvatar(oauthUser.getProfile_image_url());
        oauthUser.setUsername(oauthUser.getScreen_name());
        oauthUser.setUserId(oauthUser.getId());
        log.info("weiboUser = " + oauthUser);
        oauthUser.setUserInfo(bodyText);
        userService.saveUser(oauthUser);
        generateCookie(response, access_token, oauthUser, "weibo");

        response.sendRedirect("http://192.168.1.103:8080");
    }


    @Override
    protected JSONObject getUser(String access_token, String userId, String jws_token) {
        HttpRequest httpRequest = HttpRequest.get("https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + userId);
        // HttpRequest httpRequest = HttpRequest.get("https://api.weibo.com/2/users/show.json?uid="+uid);
        httpRequest.contentType("application/json");
        httpRequest.accept("application/json");
        httpRequest.header("Authorization", "Bearer " + jws_token);
        HttpResponse userinfoResp = httpRequest.send();
        JSONObject wxUser = JSON.parseObject(userinfoResp.charset("utf-8").bodyText());
        log.info("weiboUser = " + wxUser);
        wxUser.put("username", wxUser.getString("login"));
        wxUser.put("userId", wxUser.getString("id"));
        wxUser.put("avatar", wxUser.getString("avatar_url"));
        return wxUser;
    }

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("微博 .cancel");
        return "cancel 123";
    }
}
