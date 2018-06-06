package com.blchina.common.util.cookie;


import com.blchina.common.util.string.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {

    public static final int MAX_AGE = 365 * 24 * 60 * 60;
    public static final String DEFAULT_ENCODING = "UTF-8";

    public static void addCookie(HttpServletResponse response, String key, String value) {
        addCookie(response, key, value, "", MAX_AGE);
    }

    /**
     *
     * @param response
     * @param key
     * @param value
     * @param maxAge  最大有效期，秒单位
     */
    public static void addCookie(HttpServletResponse response, String key, String value, int maxAge) {
        addCookie(response, key, value, "", maxAge);
    }

    public static void addCookie(HttpServletResponse response, String key, String value, String domain) {
        addCookie(response, key, value, domain, MAX_AGE);
    }

    public static void addCookie(HttpServletResponse response, String key, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(key, encode(value));
        cookie.setPath("/");// 这个要设置。不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        if (maxAge >= 0) cookie.setMaxAge(maxAge);
        if (!StringUtil.isNullorEmpty(domain)) cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) return null;

        for(Cookie temp : cookies){
            if(temp.getName().equals(key)){
                return temp ;
            }
        }
        return null ;
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        addCookie(response, key, null, 0);
    }

    public static void removeCookie(HttpServletResponse response, String key, String domain) {
        addCookie(response, key, null, domain, 0);
    }

    /**
     * 获得cookie对应的值
     * @param key 对应的键
     * @param request 请求
     * @return 返回值
     */
    public static String getCookieValue(HttpServletRequest request, String key){
        Cookie cookie = getCookie(request, key);
        if (cookie != null) {
            return decode(cookie.getValue());
        }
        return null;
    }

    private static String encode(String value) {
        if (value == null) return null;

        try {
            return URLEncoder.encode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        encode("");
    }

}
