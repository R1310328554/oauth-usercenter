
package com.lk.learn.oauth.usercenter.controller;
import com.alibaba.fastjson.JSONObject;
import com.lk.learn.oauth.usercenter.entity.OauthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/my/user/")
public class MyOauthController extends BaseController {


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

    @RequestMapping("/cancel")
    public String cancel() {
        log.info("WebController.cancel");
        return "cancel 123";
    }
}
