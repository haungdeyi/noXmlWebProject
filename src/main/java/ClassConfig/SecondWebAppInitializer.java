package ClassConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//web容器启动时可以加载多个实现了WebApplicationInitializer的初始化器
public class SecondWebAppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        //添加过滤器
        FilterRegistration.Dynamic filter = servletContext.addFilter("springSessionRepositoryFilter",DelegatingFilterProxy.class);
        //设置过滤器的映射路径
        filter.addMappingForUrlPatterns(null,false,"/*");
        //DelegatingFilterProxy是一个filter代理类，它在spring容器创建之前就已经创建
        //filtername是它所代理的一个filter实例（spring容器中的bean），因此启动tomcat会报错
        //所以需要设置"targetFilterLifecycle"为true，表示由spring容器来管理其生命周期的(默认由tomcat容器管理）
        //跟配置shiroFilter一样
        filter.setInitParameter("targetFilterLifecycle","true");
    }
}
