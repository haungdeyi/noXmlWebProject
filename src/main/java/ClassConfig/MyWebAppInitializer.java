package ClassConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//自定义加载DispatcherServlet和springContext的类
//代替传统的web.xml文件，tomcat7.0版本之后可以在启动时扫描到这个类
// 最后实现在容器启动时加载和初始化DispatcherServlet和spring容器配置类
/**
 * 原理：容器启动时会在类路径在中查找ServletContainerInitializer的实现类，该实现类要配合@HandlerTypes使用
 *       SpringServletContainerInitializer实现了ServletContainerInitializer，并通过@HandlerTypes指定了配置
 *      类的顶级接口WebApplicationInitializer。这样就会将类路径下WebApplicationInitializer及其实现类的Class
 *      对象放到onStartUp(Set<Class<?>> webApplicationInitailizerClasses,ServletContext servletContext)的
 *      Set集合参数中，然后通过反射创建WebApplicationInitializer实现类的实例，由Spring去实现剩下的初始化逻辑
 *      使用的是AnnotationConfigWebApplicationContext（？？？？）容器加载器
 * */
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
    /*@Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new DelegatingFilterProxy()};
    }*/
}
