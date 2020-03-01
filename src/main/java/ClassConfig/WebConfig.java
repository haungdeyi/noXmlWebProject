package ClassConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import custom.CustomInterceptor;
import custom.CustomParamConvter;
import custom.CustomParamValidator;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//自定义的springMvc配置类（替代传统的xml文件）
//默认已经注册HttpRequestHandlerMapping和HttpRquestHandlerAdapter
@Configuration
@EnableWebMvc
//避免重复扫描
@ComponentScan(basePackages = {"controller"},excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class)})
//混合使用xml配置文件
//@ImportResource(locations = {"classpath:springmvc.xml"})
//在javaConfig类中获取bean有两种方式：1、直接通过调用产生该bean的方法获取（不推荐），2、通过传入参数的形式获取
public class WebConfig extends WebMvcConfigurerAdapter {

    //在容器中注册jsp的视图解析器的bean
    @Bean
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    //注册json格式数据的HttpMessageConvert（消息转换器）
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        //设置json格式的转换
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);

        //设置日期格式的转换
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        return mappingJackson2HttpMessageConverter;
    }

    //添加拦截器（请求第一步）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        InterceptorRegistration ir = registry.addInterceptor(new CustomInterceptor());
        //设置拦截的路径
        ir.addPathPatterns("/linkToMainPage");
    }

    //添加参数转换器（请求第二步）
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(new CustomParamConvter());
    }

    //添加参数验证器（请求第三步）
    @Override
    public Validator getValidator() {
        return new CustomParamValidator();
    }

    //添加消息转换器（响应）
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //将自定义的json消息转换器添加到消息转换器集中才生效
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    //配置处理静态资源的servlet
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //对静态资源的请求转发到容器缺省的servlet,而不使用 DispatcherServlet
        configurer.enable();
    }

}
