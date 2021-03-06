package com.lk.learn.oauth.usercenter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明：微信用户数据
 * 作者：luo
 * 时间：2022-7-3 18:56:02
 */
@Data
@Document("OauthUser")
public class OauthUser implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;

    //UID，用户标志
    @Id
    private String userId;

    private String id;

    /**
     * 第三方类型： weibo、 githut、wechat、..
     */
    private String thirdType;

    //用户是否接收消息，也就是是否打开了消息开关
    private Boolean enable;

    //用户关注应用的时间
    private String  created_at;

    //用户关注应用的时间
    private String createDate;

    //Github 昵称
    private String login;

    //昵称
    private String username;

    //微博昵称
    private String screen_name;

    //昵称
    private String name;

    //
    private String password;

    //头像
    private String avatar;

    //头像
    private String avatar_url;

    // github 头像
    private String profile_image_url;

    //
    private String email;

    //
    private String userInfo;

    //
    private String location;

    //
    private String status;

    //
    private Date lastLoginDate;


}
