package ClassConfig;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//标注这是一个配置类
@Configuration
//@Import({})导入其他配置类
//@ImportResource(locations = {})导入xml配置文件
//指定扫描的包
@ComponentScan(basePackages = {"service"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value= EnableWebMvc.class)})
//使用配置类的时候开启缓存
@EnableCaching
//定义创建spring容器的java配置类（替代传统的xml文件）
public class SpringContext {

    //声明缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        //通过注入RedisConnectionFactory得到Redis缓存管理器
        return RedisCacheManager.create(redisConnectionFactory);
    }

    /*@Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return  redisTemplate;
    }*/

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("192.168.43.66",6379);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;
    }


}
