package com.blchina.common.controller;


import com.blchina.common.util.constant.Constant;
import com.blchina.common.util.cookie.CookieUtil;
import com.blchina.common.util.string.StringUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController extends HttpServlet {
    public final static String STATUS_ERROR = "0";
    public final static String STATUS_SUCCESS = "1";

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

    public void outWriteGBK(HttpServletResponse response, String mess) {
        response.setContentType("text/html;charset=gbk");
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

    public void setAdminUserToCookie(HttpServletResponse response, int userId) {
        CookieUtil.addCookie(response, Constant.COOKIE_ADMIN_USERID, String.valueOf(userId), Constant.COOKIE_ADMIN_TTL);
    }

    public void setAdminUserToCookie(HttpServletResponse response, int userId, String userName, String domain) {
        CookieUtil.addCookie(response, Constant.COOKIE_ADMIN_USERID, String.valueOf(userId), domain, Constant.COOKIE_ADMIN_TTL);
        CookieUtil.addCookie(response, Constant.COOKIE_ADMIN_USERNAME, userName, domain, Constant.COOKIE_ADMIN_TTL);
    }

    public void setTokenToCookie(HttpServletResponse response, String token, String domain) {
        CookieUtil.addCookie(response, Constant.COOKIE_ADMIN_TOKEN, token, domain, Constant.COOKIE_ADMIN_TTL);
    }

    public void clearCookieAdminUser(HttpServletResponse response) {
        CookieUtil.removeCookie(response, Constant.COOKIE_ADMIN_USERID);
        CookieUtil.removeCookie(response, Constant.COOKIE_ADMIN_USERNAME);
        CookieUtil.removeCookie(response, Constant.COOKIE_ADMIN_TOKEN);
    }

    public void clearCookieAdminUser(HttpServletResponse response, String domain) {
        CookieUtil.removeCookie(response, Constant.COOKIE_ADMIN_USERID, domain);
        CookieUtil.removeCookie(response, Constant.COOKIE_ADMIN_USERNAME, domain);
    }

    public int getAdminUserid(HttpServletRequest request) {
        String id = CookieUtil.getCookieValue(request, Constant.COOKIE_ADMIN_USERID);
        return StringUtil.isNullorEmpty(id) ? 0 : Integer.parseInt(id);
    }

    public String getAdminUserName(HttpServletRequest request) {
        return CookieUtil.getCookieValue(request, Constant.COOKIE_ADMIN_USERNAME);
    }

    public String errorStatus() {
        return buildStatus(STATUS_ERROR);
    }

    public String errorStatus(String status) {
        return buildStatus(status);
    }

    public String successStatus() {
        return buildStatus(STATUS_SUCCESS);
    }

    public String buildStatus(String status) {
        return "{\"status\":\"" + status + "\"}";
    }

    public String buildStatus(String status, String message) {
        return "{\"status\":\"" + status + "\",\"message\":\"" + message + "\"}";
    }

    public String successCount(int count) {
        return buildCount(count);
    }

    public String errorCount() {
        return buildCount(-1);
    }

    public String buildCount(int count) {
        return "{\"count\":\"" + count + "\"}";
    }

    public void setAdminUserToSession(HttpServletRequest request, int userId, String userName) {
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("userName", userName);
    }

    public void clearSessionAdminUser(HttpServletRequest request) {
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("userName");
    }

}
