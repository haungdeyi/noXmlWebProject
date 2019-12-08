package custom;

import domain.User;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义参数转换器
 * 不使用xml文件时不用把它放到spring容器，直接把它注册到参数转换器中
 * 当控制器的参数类型为User（匹配）时，SpringMvc会找到这个转换器进行转换
 */
public class CustomParamConvter implements Converter<String, User> {
    //在这里写自定义参数转换器的转换逻辑
    public User convert(String source) {
        String[] userbody = source.split("-");
        if(userbody.length == 3) {
            User user = new User();
            user.setId(Long.parseLong(userbody[0]));
            user.setUsrename(userbody[1]);
            user.setPassword(userbody[2]);
            return user;
        }
        return null;
    }
}
