package edu.zjg.service;

import com.sun.org.apache.regexp.internal.RE;
import edu.zjg.dao.EmpDAO;
import edu.zjg.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Max;
import java.util.List;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpDAO empDAO;

    @Override
    public void delete(String id) {
     empDAO.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Emp> findAll(){
        return empDAO.findAll();
    }


    @Override
    public void save(Emp emp) {
        empDAO.save(emp);

    }

    @Override
    public Emp findOne(String id) {
        return empDAO.findOne(id);
    }


}
