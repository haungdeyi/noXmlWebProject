package ClassConfig;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class MybatisConfig implements EnvironmentAware {
    //方式一：通过spring提供的运行环境获取配置文件的值
    //为什么不能AuotWrited呢？？？
    private Environment em;

    //方式二通过解析配置类加载并解析配置文件
    /*@Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocation(new ClassPathResource("application.properties"));
        return pspc;
    }*/

    //HikariDataSource数据库连接池
    @Bean
    public DataSource hikariDataSource(){
        String xxx = em.getProperty("jdbc.password");
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(em.getProperty("jdbc.Driver"));
        hikariDataSource.setJdbcUrl(em.getProperty("jdbc.url"));
        hikariDataSource.setUsername(em.getProperty("jdbc.user"));
        hikariDataSource.setPassword(em.getProperty("jdbc.password"));
        hikariDataSource.setMaximumPoolSize(Integer.parseInt(em.getProperty("jdbc.maximumPoolSize")));
        hikariDataSource.setConnectionTimeout(Integer.parseInt(em.getProperty("jdbc.connTimeout")));
        return hikariDataSource;
    }

    //配置sqlSessionFactory
    @Bean(name = "sessionFactory")
    public SqlSessionFactoryBean SqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //配置文件中要通过这种方式加载mybatis的配置文件
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis.xml"));
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    //通过扫描的方式配置生成mapper接口的动态代理对象(推荐使用)
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("dao");
        //要对应上面创建的SqlSessionFactoryBean的name
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sessionFactory");
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        return  mapperScannerConfigurer;
    }

    //配置事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
       DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
       dataSourceTransactionManager.setDataSource(hikariDataSource());
       return dataSourceTransactionManager;
    }

    public void setEnvironment(Environment environment) {
        this.em = environment;
    }
}
