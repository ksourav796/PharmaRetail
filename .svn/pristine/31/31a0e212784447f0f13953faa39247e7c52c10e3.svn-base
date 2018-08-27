package com.hyva.posretail.pos.posMapper;

import com.hyva.posretail.pos.posEntities.User;
import com.hyva.posretail.pos.posPojo.PosUserPojo;

public class PosUserMapper {

    public static User mapPojoToEntity(PosUserPojo posUserPojo) {
        User user = new User();
        user.setEmail(posUserPojo.getEmail());
        user.setUserName(posUserPojo.getUserName());
        user.setPasswordUser(posUserPojo.getPasswordUser());
        user.setFull_name(posUserPojo.getFull_name());
        user.setPhone(posUserPojo.getPhone());
        user.setSecurityAnswer(posUserPojo.getSecurityAnswer());
        user.setSecurityQuestion(posUserPojo.getSecurityQuestion());
        user.setStatus(posUserPojo.getStatus());
        return user;
    }

}
