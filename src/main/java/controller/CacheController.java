package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

//@Controller
@RequestMapping("/")
public class CacheController {

    @Autowired
    private UserService userService;

    //从数据库查询用户
    @RequestMapping("getUserById")
    @ResponseBody
    //应该是因为spring的root容器已经配置成不扫描Controller，所在无法在这里使用@Cacheable
    public User getUserById(Long id) throws Exception {
        User user = userService.getUserById(id);
        return user;
    }

    //清除缓存
    @RequestMapping("clearCache")
    @ResponseBody
    public String clearCache(){
        return userService.clearCache();
    }
}
