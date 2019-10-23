package ClassConfig;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//标注这是一个配置类
@Configuration
//@Import({})导入其他配置类
//@ImportResource(locations = {})导入xml配置文件
//指定扫描的包
@ComponentScan(basePackages = {"service"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value= EnableWebMvc.class)})
//定义创建spring容器的java配置类（替代传统的xml文件）
public class SpringContext {

}
