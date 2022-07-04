package com.lk.learn.oauth.usercenter.service;

import com.lk.learn.oauth.usercenter.entity.OauthUser;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */
    @Override
    public void saveUser(OauthUser user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param username
     * @return
     */
    @Override
    public OauthUser findUserByUserName(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        OauthUser user =  mongoTemplate.findOne(query , OauthUser.class);
        return user;
    }

    /**
     * 根据用户名查询对象
     * @param userId
     * @return
     */
    @Override
    public OauthUser findUserByUserId(String userId) {
        // Query query=new Query(Criteria.where("userId").is(userId));
        OauthUser user =  mongoTemplate.findById(userId, OauthUser.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public long updateUser(OauthUser user) {
        Query query=new Query(Criteria.where("userId").is(user.getUserId()));
        Update update= new Update().set("username", user.getUsername()).set("password", user.getPassword());
        //更新查询返回结果集的第一条
        UpdateResult result =mongoTemplate.updateFirst(query,update,OauthUser.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
        if (result != null) {
            return result.getMatchedCount();
        } else {
            return 0;
        }
    }

    /**
     * 删除对象
     * @param userId
     */
    @Override
    public void deleteUserById(Long userId) {
        Query query=new Query(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query,OauthUser.class);
    }
}
