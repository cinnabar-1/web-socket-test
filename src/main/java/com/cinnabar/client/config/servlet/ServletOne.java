package com.cinnabar.client.config.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName ServletOne.java
 * @Description
 * @createTime 2021-11-04  13:28:00
 */
@WebServlet(name = "firstServlet", urlPatterns = "/welcome")
public class ServletOne extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("servlet one");
    }
}
