package controller;

import domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class CustomSpringMvcController {

    //使用自定义参数转换
    @RequestMapping("customParamConvter")
    @ResponseBody
    public User customParamConvter(User user){
        if(user != null){
            return user;
        }
        else{
            user = new User();
            user.setUsername("用户不存在");
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
