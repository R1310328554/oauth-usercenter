
package com.lk.learn.oauth.usercenter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/my/user/")
public class MyOauthController extends BaseController {

    // 访问/securedPage， 会被转发到 ： http://localhost:8081/auth/oauth/authorize?response_type=code&client_id=R2dpxQ3vPrtfgF72&scope=user_info&state=0zaP816-tXgvmkK1O4gVuUVA8n9Q0OOlCNwpY5ukgkY%3D&redirect_uri=http://localhost:8082/login/oauth2/code/
    @RequestMapping("/securedPage")
    public String securedPage(Model model, Principal principal) {
        log.info("WebController.securedPage");
         return "securedPage";
    }


    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        log.info("WebController.index");
        return "index";
    }

    @RequestMapping("/testOAuth2")
    public String testOAuth2(Model model, Principal principal) {
        log.info("model = [" + model + "], principal = [" + principal + "]");
        return "testOAuth2 ++ \"Welcome \" + SecurityContextHolder.getContext().getAuthentication();";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public JSONObject login(@RequestBody OauthUser oauthUser, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1:重定向到授权页面: 引导用户进入授权页面同意授权,获取code
        log.info("oauthUser = " + oauthUser);
        String username = oauthUser.getUsername();
        JSONObject ret = new JSONObject();
        String errmsg = "";
        if (StringUtils.isNotEmpty(username)) {
            oauthUser = userService.findUserByUserName(username);
            if (oauthUser != null && StringUtils.isNotEmpty(oauthUser.getUserId())) {
                generateCookie(response, username + "", oauthUser, "my");
                ret.put("msg", "ok");
                ret.put("data", oauthUser);
                ret.put("code", 0);
                return ret;
            } else {
                errmsg = "找不到用户名或密码错误！";
            }
        } else {
            // log.info("参数有误！~~ ");
            errmsg = "参数有误！";
        }
        ret.put("msg", errmsg);
        ret.put("data", errmsg);
        ret.put("code", 400);
        return ret;
    }


    @Override
    protected JSONObject getUser(String access_token, String userId, String jws_token) {
        return null;
    }

    String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

//    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
//    String GET_UERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
//    @GetMapping(value = "callback")
//    public ModelAndView wxcallback(@RequestParam("code") String code) throws IOException {
//        //2:使用code换取access_token
//        String accessTokenUrl = String.format(ACCESS_TOKEN_URL, APPID, APPSECRET, code);
//        HttpResponse accessTokenResp = HttpRequest.get(accessTokenUrl).send();
//        JSONObject jsonObject = JSON.parseObject(accessTokenResp.bodyText());
//        String token = jsonObject.getString("access_token");
//        String openid = jsonObject.getString("openid");
//        //3:刷新access_token (if need)
//        //4:获取用户信息
//        String getUerinfoUrl = String.format(GET_UERINFO_URL, token, openid);
//        HttpResponse userinfoResp = HttpRequest.get(getUerinfoUrl).send();
//        WxUser wxUser = JSON.parseObject(userinfoResp.charset("utf-8").bodyText(), WxUser.class);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("userinfo", wxUser);
//        modelAndView.setViewName("wx");
//        return modelAndView;
//    }
//
//    @RequestMapping("/callback")
//    public String wbcallback(@RequestParam("code") String code) throws IOException {
//        log.info("WebController.callback");
//        String redirect = "https://api.weibo.com/oauth2/access_token?client_id=973886123&client_secret=3253f16e8324a73f6ede08c7405c0bad&grant_type=authorization_code&redirect_uri=http%3A%2F%2F192.168.1.103%3A8082%2FsecuredPage&code=%s";
//        String accessTokenUrl = String.format(redirect, code);
//        HttpResponse accessTokenResp = HttpRequest.get(accessTokenUrl).send();
//        JSONObject jsonObject = JSON.parseObject(accessTokenResp.bodyText());
//        log.info("jsonObject = " + jsonObject);
//
//        String access_token = jsonObject.getString("access_token");
//        String remind_in = jsonObject.getString("remind_in");
//        String expires_in = jsonObject.getString("expires_in");
//        //3:刷新access_token (if need)
//        //4:获取用户信息
//        String getUerinfoUrl = String.format("https://api.weibo.com/2/statuses/mentions.json?access_token=%s", access_token);
//        HttpResponse userinfoResp = HttpRequest.get(getUerinfoUrl).send();
//        JSONObject wxUser = JSON.parseObject(userinfoResp.charset("utf-8").bodyText());
//        log.info("wxUser = " + wxUser);
//
//        return "securedPage";
//    }

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("WebController.cancel");
        return "cancel 123";
    }
}
