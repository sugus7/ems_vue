package edu.zjg.dao;

import edu.zjg.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    void save(User user);

    User findByUsername(String username);
}
