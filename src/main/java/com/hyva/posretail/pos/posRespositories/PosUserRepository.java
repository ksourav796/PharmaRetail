package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.User;
import org.springframework.data.repository.CrudRepository;


public interface PosUserRepository extends CrudRepository<User, Long> {

    User findByUserNameAndAndPasswordUser(String  userName, String Password);

    User findByEmail(String Email);

    User findByUserName(String name);
}
