package com.lk.learn.oauth.usercenter.service;

import com.lk.learn.oauth.usercenter.entity.OauthUser;

public interface UserService {
    void saveUser(OauthUser user);

    OauthUser findUserByUserName(String userName);

    OauthUser findUserByUserId(String userId);

    long updateUser(OauthUser user);

    void deleteUserById(Long id);
}
