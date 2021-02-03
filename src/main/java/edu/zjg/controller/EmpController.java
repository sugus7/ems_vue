package edu.zjg.controller;
import edu.zjg.entity.Emp;
import edu.zjg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("emp")
@CrossOrigin
@Slf4j
public class EmpController {
    @Autowired
    private EmpService empService;
    @Value("${photo.dir}")
    private String realPath;
    //保存员工信息
    @PostMapping("save")
    public Map<String,Object> save(Emp emp, MultipartFile photo) throws IOException {
        log.info("emp",emp.toString());
        log.info("photos",photo.getOriginalFilename());
        Map<String,Object> map = new HashMap<>();
        try {
            //头像保存
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
            photo.transferTo(new File(realPath,newFileName));
            //设置头像地址
            emp.setPath(newFileName);
            empService.save(emp);
            map.put("state",true);
            map.put("msg","员工信息保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","员工信息保存失败！");
        }
        return map;
    }
    //获取员工列表
    @GetMapping("findAll")
    public List<Emp> findAll(){
        return empService.findAll();
    }
    //删除员工信息
    @GetMapping("delete")
    public Map<String,Object> delete(String id){
        log.info("删除员工的id[{}]",id);
        Map<String,Object> map = new HashMap<>();
        try {
            //首先删除员工头像
            Emp emp = empService.findOne(id);
            File file = new File(realPath,emp.getPath());
            if (file.exists()){
                file.exists();
            }
            empService.delete(id);
            map.put("state",true);
            map.put("msg","删除员工成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","删除员工信息失败！");
        }
        return map;
    }

}
