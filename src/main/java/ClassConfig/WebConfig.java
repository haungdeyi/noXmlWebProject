package ClassConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

//自定义的springMvc配置类（替代传统的xml文件）
//默认已经注册HttpRequestHandlerMapping和HttpRquestHandlerAdapter
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

    //在容器中注册jsp的视图解析器的bean
    @Bean
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEN-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    //注册json格式数据的HttpMessageConvert（消息转换器）
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.parseMediaType("application/json;charset=UTF-8"));
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
        return mappingJackson2HttpMessageConverter;
    }

    //配置处理静态资源的servlet

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //对静态资源的请求转发到容器缺省的servlet,而不使用 DispatcherServlet
        configurer.enable();
    }
}
