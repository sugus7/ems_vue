package edu.zjg.service;

import edu.zjg.entity.Emp;

import java.util.List;

public interface EmpService {
     void delete(String id);
     List<Emp> findAll();
    void save(Emp emp);

    Emp findOne(String id);
}
