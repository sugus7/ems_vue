package edu.zjg.controller;
import edu.zjg.entity.User;
import edu.zjg.service.UserService;
import edu.zjg.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin  //前后端分离，允许跨域
@RequestMapping("user")
@Slf4j
public class UserController {
    String state=null;
    @Autowired
    UserService userService;
    /*
     * 用来生成用户注册的方法
     * */
    @PostMapping("register")
    public Map<String,Object> register(@RequestBody User user,String code,HttpServletRequest request){
        log.info("用户信息：[{}]",user.toString());
        log.info("用户输入的验证码信息：[{}]",code);
        Map<String,Object> map = new HashMap<>();
            //1.调用业务层方法
            try {
                String key = (String) request.getServletContext().getAttribute("code");
                if (key.equalsIgnoreCase(code)){
                    userService.register(user);
                    state = "提示：注册成功！"+new Date();
                    map.put("state",true);
                    map.put("msg","提示：注册成功！");
                }
                else{
                        state = "验证码出现错误！";
                        throw new RuntimeException("验证码出现错误");

                }

            }catch (Exception e){
                state = "注册失败！";
                e.printStackTrace();
                map.put("state",false);
                map.put("msg","提示：注册失败！"+e.getMessage());
            }
            System.out.println(state);
            return map;
    }
    /*
     * 生成验证码图片
     * */
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //1.使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.将验证码放入servletcontext作用域
        request.getServletContext().setAttribute("code",code);
        //3.将图片转为base64处理
        ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120,30,byteArrayOutputStream,code);
        String s = "data:image/png;base64,"+Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
        return s;
    }
    /*
    *用户登录的功能
     *  */
    @PostMapping("login")
    public Map<String,Object> login(@RequestBody User user){
        log.info("当前的登录信息:[{}]",user.toString());
        Map<String,Object> map = new HashMap<>();
        try{
            User userDB = userService.login(user);
            map.put("state",true);
            map.put("msg","登陆成功!");
            map.put("user",userDB);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

}
