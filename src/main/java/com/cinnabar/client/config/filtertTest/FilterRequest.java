package com.cinnabar.client.config.filtertTest;


import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName FilterRequest.java
 * @Description
 * @createTime 2021-11-03  11:14:00
 */
@Order(1)
public class FilterRequest implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init FilterRequest阶段");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("another filter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
