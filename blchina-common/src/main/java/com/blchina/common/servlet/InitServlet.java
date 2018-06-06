package com.blchina.common.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Properties;

public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

        Properties pro = new Properties();

        try {

            ServletContext sctx = getServletContext();
            InputStream is = sctx.getResourceAsStream("classpath:config/config.properties");

            pro.load(is);
            is.close();
            sctx.setAttribute("properties", pro);

        } catch (Exception ex) {

            System.out.print(ex.getStackTrace());
        }
    }





}
