package custom;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomInterceptor implements HandlerInterceptor {
    //进入控制前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截对主页面的请求");
        return true;
    }

    //执行控制器逻辑之后
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("主页面控制器逻辑已执行完毕");
    }

    //所有拦截器的preHandle或者postHandle处理完成之后，按拦截器的添加顺序执行
    //如果拦截器的preHandle返回false，那么排在他之后的拦截器的afterCompletion都不会执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
