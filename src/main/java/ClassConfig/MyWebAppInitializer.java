package ClassConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//自定义加载DispatcherServlet和springContext的类
//代替传统的web.xml文件，实现在容器启动时加载和初始化DispatcherServlet和spring容器配置类
public class MyWebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {SpringContext.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        //返回spring容器的Class对象
        return new Class<?>[] {WebConfig.class};
    }

    //定义DispatcherServlet拦截请求的模式
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
