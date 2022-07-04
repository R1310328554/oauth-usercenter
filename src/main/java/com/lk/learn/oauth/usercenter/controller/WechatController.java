
package com.lk.learn.oauth.usercenter.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lk.learn.oauth.usercenter.entity.WxUser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
@RequestMapping("/wechat/user/")
public class WechatController extends BaseController{

//    private static final String  APPID = "wx230aa1ef053ece4a"; // 努力奋斗的猿
//    private static final String  APPSECRET = "d848e2fb2f579fc9169ecfddf1956549";
    private static final String  APPID = "wxa38d59927914a088";// 觉醒的码农
    private static final String  APPSECRET = "d848e2fb2f579fc9169ecfddf1956549";
    private static final String  REDIRECT_URI = "http://192.168.1.103:8082/login/oauth2/code/";
    private static final String  RESPONSE_TYPE = "code";
    private static final String  SCOPE = "snsapi_userinfo"; // userinfo
    private static final String  STATE = "test123-STATE";

    // 访问/securedPage， 会被转发到 ： http://localhost:8081/auth/oauth/authorize?response_type=code&client_id=R2dpxQ3vPrtfgF72&scope=user_info&state=0zaP816-tXgvmkK1O4gVuUVA8n9Q0OOlCNwpY5ukgkY%3D&redirect_uri=http://localhost:8082/login/oauth2/code/
    @RequestMapping("/securedPage")
    public String securedPage(Model model, Principal principal) {
        log.info("微信 .securedPage");
         return "securedPage";
    }


    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        log.info("微信 .index");
        return "index";
    }


    String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";
    @GetMapping(value = "login")
    public void wx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1:重定向到授权页面: 引导用户进入授权页面同意授权,获取code
        String codeUrl = String.format(AUTHORIZE_URL, APPID, URLEncoder.encode(REDIRECT_URI, "utf-8"), RESPONSE_TYPE, SCOPE, STATE);
        response.sendRedirect(codeUrl);
    }

    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    String GET_UERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    @GetMapping(value = "callback")
    public ModelAndView wxcallback(@RequestParam("code") String code) throws IOException {
        //2:使用code换取access_token
        String accessTokenUrl = String.format(ACCESS_TOKEN_URL, APPID, APPSECRET, code);
        HttpResponse accessTokenResp = HttpRequest.get(accessTokenUrl).send();
        JSONObject jsonObject = JSON.parseObject(accessTokenResp.bodyText());
        String token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        //3:刷新access_token (if need)
        //4:获取用户信息
        String getUerinfoUrl = String.format(GET_UERINFO_URL, token, openid);
        HttpResponse userinfoResp = HttpRequest.get(getUerinfoUrl).send();
        WxUser wxUser = JSON.parseObject(userinfoResp.charset("utf-8").bodyText(), WxUser.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userinfo", wxUser);
        modelAndView.setViewName("wx");
        return modelAndView;
    }

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("微信 .cancel");
        return "cancel 123";
    }
}
