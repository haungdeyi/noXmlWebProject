package service;

import dao.UserDao;
import domain.User;
import org.apache.log4j.helpers.ThreadLocalMap;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    //测试事务
    @Transactional
    public String addUser(User user){
        dao.addUser(user);
        int i = 1/0;
        return "xxx";
    }

    //value指定缓存的名字，key指定对应缓存中的键（#id表示通过spring的SpEL的表达式将参数id作为键）
    //@Cacheable(value="user",key="#id")
    public User getUserById(Long id) throws Exception {
        return dao.getUserById(id);
    }

    //清除缓存（如果指定value，则需指定删除的key或者指定allEntries删除所有key，否则不会删除对应缓存的任何key）
    //CacheEvict(value = "user",allEntries = true)
    public String clearCache(){
        return "clear cache is successfully!!!";
    }
}
