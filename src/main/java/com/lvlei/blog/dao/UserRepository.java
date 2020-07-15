package com.lvlei.blog.dao;

import com.lvlei.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username,String password);

}
