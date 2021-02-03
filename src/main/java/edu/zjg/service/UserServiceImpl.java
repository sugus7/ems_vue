package edu.zjg.service;

import edu.zjg.dao.UserDAO;
import edu.zjg.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;
    @Override
    public void register(User user) {
        //0.根据用户名查询用户是否存在
        User userDB = userDAO.findByUsername(user.getUsername());
        if (userDB==null){
            //1.生成用户状态
            user.setStatus("已激活");
            //2.设置用户的注册时间
            user.setRegisterTime(new Date());
            //3.调用dao
            userDAO.save(user);
        }else{
            throw new RuntimeException("用户名已经存在！");
        }

    }

    @Override
    public User login(User user) {
        User userDB = userDAO.findByUsername(user.getUsername());
        if (!ObjectUtils.isEmpty(userDB)){
            if (userDB.getPassword().equals(user.getPassword())){
                return userDB;
            }else{
                throw new RuntimeException("密码错误！");
            }
        }else{
            throw new RuntimeException("用户名不存在！");
        }

    }
}
