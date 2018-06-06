/*
 * @(#)TokenInterceptor.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package  com.blchina.boutique.intercept;
import com.blchina.boutique.datamodel.CBPConstant;
import com.blchina.boutique.datamodel.weixin.WeixinResult;
import com.blchina.common.util.blchina.BlchinaUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *整体token拦截
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
@Autowired
private RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String origin = request.getHeader("Access-Control-Allow-Origin");
        if(origin == null || "".equals(origin)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "MSESSIONID,Content-Type");
        }
        try {
            String url = request.getRequestURI();
            String token = request.getHeader("MSESSIONID");
            if(StringUtils.isEmpty(token)){
                return  false;
            }else{
                if(redisUtil.exists(token)){
                    redisUtil.expire(token, BlchinaUtil.TTL_WENXI_USER);
                    return super.preHandle(request, response, handler);
                }else{
                    WeixinResult base=new WeixinResult();
                    base.setCode(CBPConstant.CODE_TOKEN_INVALID);
                    base.setMessage("token无效");
                    out(response,base);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
    }

    private static final void out(HttpServletResponse response, WeixinResult base) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(objectMapper.writeValueAsString(base));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
