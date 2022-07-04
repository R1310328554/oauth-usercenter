package com.lk.learn.oauth.usercenter.dao;

import com.lk.learn.oauth.usercenter.entity.OauthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<OauthUser, String> {

}
