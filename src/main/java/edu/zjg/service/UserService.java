package edu.zjg.service;

import edu.zjg.entity.User;

public interface UserService {
    //用户注册方法
    void register(User user);
    //用户登录的功能
    User login(User user);

}
