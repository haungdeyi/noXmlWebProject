package controller;

import domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("linkToMainPage")
    public String linkToMainPage(){
        return "main";
    }

    @RequestMapping(path = "proxy")
    @ResponseBody
    public String proxy(HttpServletRequest request){
        //真实的请求IP
        String realIP = request.getHeader("X_Real_IP");
        //nginx代理主机的IP
        String nginxHostIP = request.getRemoteAddr();
        //提供服务的主机
        String serverMsg = request.getHeader("ServerMsg");
        return  "请求的IP是：" + realIP + "代理主机的IP是：" + nginxHostIP + "提供服务的主机是：" + serverMsg;
    }

    //使用自定义参数转换
    @RequestMapping("customParamConvter")
    @ResponseBody
    public User customParamConvter(User user){
        if(user != null){
            return user;
        }
        else{
            user = new User();
            user.setUsrename("用户不存在");
            return user;
        }
    }

    //使用自定义参数验证器
    @RequestMapping("customParamValidator")
    @ResponseBody
    public Object customParamValidator(@Validated User user, Errors errors){
        if(errors.hasErrors()){
            return errors.getFieldError("password").getDefaultMessage();
        }
        else{
            return user;
        }
    }
}
