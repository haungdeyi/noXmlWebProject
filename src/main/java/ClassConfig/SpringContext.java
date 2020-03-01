package ClassConfig;

import domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

//标注这是一个配置类
@Configuration
//导入其他配置类（不能直接在配置类里面创建其他配置类的实例）
@Import({MybatisConfig.class})//,RedisHttpSessionConfiguration.class
//开启声明式事务
@EnableTransactionManagement
//导入xml配置文件
//@ImportResource(locations = {"classpath:spring.xml"})
//使用配置类的时候开启缓存功能
//@EnableCaching
//开启spring使用redis实现session共享功能
//@EnableRedisHttpSession
//定义properties文件的路径（方式三）
@PropertySource("classpath:application.properties")
//指定扫描的包
@ComponentScan(basePackages = {"service"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value= EnableWebMvc.class)})
//定义创建spring容器的java配置类（替代传统的xml文件）
public class SpringContext {

    /*//声明缓存管理器
    @Bean
    public CacheManager cacheManager(@Qualifier("cacheRedisTemplate") RedisTemplate redisTemplate) {
        //通过注入RedisConnectionFactory得到Redis缓存管理器（2.0版本之后）
        //RedisCacheManager.create(redisConnectionFactory)

        //Redis缓存管理器通过RedisTemplate操做redis数据库
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //秒为单位
        redisCacheManager.setDefaultExpiration(120);
        //指定缓存的有效时间
        Map<String, Long> userExpire = new HashMap<String, Long>();
        userExpire.put("user", Long.parseLong("60"));
        redisCacheManager.setExpires(userExpire);
        return redisCacheManager;
    }

    @Bean("cacheRedisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //指定key的序列化器（key不是String类型时不能使用StringRedisSerializer器）
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //指定vallue的序列化器
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return redisTemplate;
    }

    //Jedis连接工厂
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(@Value("${redis.host}") String host, @Value("${redis.port}") Integer port) {
        //RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host,port);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        return jedisConnectionFactory;
    }

    //使用默认的cookie序列化器
    @Bean
    public CookieSerializer defaultCookieSerializer(){
        DefaultCookieSerializer dc = new DefaultCookieSerializer();
        dc.setCookiePath("/");
        //只有通过该名访问携带过来的cookie对应的sessionID才能实现共享
        // spring session无法做到跨域（使用不同域名访问？？）session共享
        //dc.setDomainName("www.distribute.com");
        return dc;
    }

    @Bean
    public CookieHttpSessionStrategy cookieHttpSessionStrategy(CookieSerializer cookieSerializer){
             CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
             cookieHttpSessionStrategy.setCookieSerializer(cookieSerializer);
             return cookieHttpSessionStrategy;
    }*/
}