package ClassConfig;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

//自定义加载DispatcherServlet和springContext的类
//代替传统的web.xml文件，tomcat7.0版本之后可以在启动时扫描到这个类
// 最后实现在容器启动时加载和初始化DispatcherServlet和spring容器配置类
public class MyWebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {
    //返回spring容器的class对象
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {SpringContext.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        //返回springmvc容器的Class对象
        return new Class<?>[] {WebConfig.class};
    }

    //定义DispatcherServlet拦截请求的模式
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    //重写方法，添加过滤器(默认映射到DispatcherServlet的路径上)
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new DelegatingFilterProxy()};
    }
}
