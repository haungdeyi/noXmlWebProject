package ClassConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//web容器启动时可以加载多个初始化器
public class SecondWebAppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        //添加过滤器
        FilterRegistration.Dynamic filter = servletContext.addFilter("httpSession",new DelegatingFilterProxy());
        //设置过滤器的映射路径
        filter.addMappingForUrlPatterns(null,false,"/*");
    }
}
