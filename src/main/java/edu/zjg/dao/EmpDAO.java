package edu.zjg.dao;

import edu.zjg.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpDAO {
    List<Emp> findAll();
    void save(Emp emp);
    void delete(String id);
    Emp findOne(String id);
}
