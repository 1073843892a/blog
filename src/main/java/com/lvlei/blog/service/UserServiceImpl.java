package com.lvlei.blog.service;

import com.lvlei.blog.dao.UserRepository;
import com.lvlei.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User checckUser(String username, String password) {
        User user= userRepository.findUserByUsernameAndPassword(username,password);
        return user;
    }
}
