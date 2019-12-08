package custom;

import domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CustomParamValidator implements Validator {
    /**
     * 参数clazz是控制器对应参数的类型的Class对象
     * @param clazz
     * @return
     */
    //是否需要校验
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    //校验规则
    public void validate(Object target, Errors errors) {
          User user = (User)target;
          if(!"250".equals(user.getPassword())){
              errors.rejectValue("password","000","二哈的password必须为250");
          }
    }
}
