package com.blchina.common.filter;


import com.blchina.common.util.constant.Constant;
import com.blchina.common.util.cookie.CookieUtil;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.number.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;


public class LoginFilter implements Filter {
    @Autowired
    protected Properties systemConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        if (isLogin((HttpServletRequest) request)) {
            filterChain.doFilter(request, response);
        } else {
            outWriteUTF8((HttpServletResponse) response, "{\n" +
                    "\"statusCode\":\"301\",\n" +
                    "\"message\":\"\\u4f1a\\u8bdd\\u8d85\\u65f6\\uff0c\\u8bf7\\u91cd\\u65b0\\u767b\\u5f55\\u3002\",\n" +
                    "\"navTabId\":\"\",\n" +
                    "\"callbackType\":\"\",\n" +
                    "\"forwardUrl\":\"\"\n" +
                    "}");

        }
    }

    public void outWriteUTF8(HttpServletResponse response, String mess) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(mess);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    private boolean isLogin(HttpServletRequest request) {
        String userId = CookieUtil.getCookieValue(request, Constant.COOKIE_ADMIN_USERID);
        String token = CookieUtil.getCookieValue(request, Constant.COOKIE_ADMIN_TOKEN);
        return isUserIdAndTokenOK(userId, token) || request.getRequestURI().contains("userLogin.action");
    }

    private boolean isUserIdAndTokenOK(String userId, String token) {
        return NumberUtil.isNumber(userId) && authTokenSuccess(userId, token);
    }

    private boolean authTokenSuccess(String userId, String token) {
        return getUserToken(userId).equals(token);
    }

    private String getUserToken(String userId) {
        return EncodeUtil.getMD5For32(userId + Constant.TOKEN_SIGN);
    }

    @Override
    public void destroy() {
    }


}
