package com.cinnabar.client.config.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName ServletTwo.java
 * @Description
 * @createTime 2021-11-04  15:36:00
 */
@WebServlet(name = "secondServlet", urlPatterns = "/welcome")
public class ServletTwo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("servlet two");
    }
}
