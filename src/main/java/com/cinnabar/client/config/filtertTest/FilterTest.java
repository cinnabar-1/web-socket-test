package com.cinnabar.client.config.filtertTest;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName FilterTest.java
 * @Description
 * @createTime 2021-07-09  14:50:00
 */
@Order(2)
public class FilterTest implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init阶段");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start2 = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long time = System.currentTimeMillis() - start2;
        System.out.println("filter Test： " + time);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁了");
    }
}
