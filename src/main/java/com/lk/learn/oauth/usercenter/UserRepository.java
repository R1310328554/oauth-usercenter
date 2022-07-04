package com.lk.learn.oauth.usercenter;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<OauthUser, String> {

}
