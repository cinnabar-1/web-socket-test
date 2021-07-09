package com.cinnabar.client.config.filtertTest;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName FilterTest.java
 * @Description
 * @createTime 2021-07-09  14:50:00
 */
public class FilterTest implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init阶段");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器执行了");
        long start2 = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long time = System.currentTimeMillis() - start2;
        System.out.println("过滤器执行的时间是 ：" + time);
        System.out.println("过滤器执行结束");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁了");
    }
}
