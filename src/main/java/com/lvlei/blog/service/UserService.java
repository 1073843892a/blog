package com.lvlei.blog.service;

import com.lvlei.blog.po.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface UserService {
    User checckUser(String username,String password);
}
