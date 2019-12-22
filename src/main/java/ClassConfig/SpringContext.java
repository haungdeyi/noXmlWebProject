package ClassConfig;

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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//标注这是一个配置类
@Configuration
//导入其他配置类
@Import({MybatisConfig.class})
//@ImportResource(locations = {})导入xml配置文件
//指定扫描的包
@ComponentScan(basePackages = {"service"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value= EnableWebMvc.class)})
//使用配置类的时候开启缓存
@EnableCaching
//定义创建spring容器的java配置类（替代传统的xml文件）
@PropertySource("classpath:application.properties")
public class SpringContext {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocation(new ClassPathResource("application.properties"));
        return pspc;
    }

    //声明缓存管理器
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        //通过注入RedisConnectionFactory得到Redis缓存管理器（2.0版本之后）
        //RedisCacheManager.create(redisConnectionFactory)

        //Redis缓存管理器通过RedisTemplate操做redis数据库
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //秒为单位
        redisCacheManager.setDefaultExpiration(120);
        //指定缓存的有效时间
        Map<String,Long> userExpire = new HashMap<String,Long>();
        userExpire.put("user",Long.parseLong("60"));
        redisCacheManager.setExpires(userExpire);
        return redisCacheManager;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //指定序列化器
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return redisTemplate;
    }

    //Jedis连接工厂
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(@Value("${redis.host}")String host, @Value("${redis.port}")Integer port){
        //RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host,port);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        return jedisConnectionFactory;
    }
}
