package com.lk.learn.oauth.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    public static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);


    /**
     * 应用到所有被@RequestMapping注解的方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("words", "hello world");
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        log.error("系统异常： ", ex);
        Map map = new HashMap();
//        map.put("code", 500);
//        map.put("msg", ex.getMessage());
//        JSONObject ret = new JSONObject();
        map.put("msg", ex.getMessage());
        map.put("data", ex);
        map.put("code", 500);
        return map;
    }

}
