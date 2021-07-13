package com.cinnabar.client.config.authToken;

import com.alibaba.fastjson.JSONObject;
import com.cinnabar.client.config.CommonStatic;
import com.cinnabar.client.config.redisHelper.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author
 * @Description:
 * @ClassName: AuthorizationInterceptor
 * @see
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value(value = "${spring.redis.host}")
    private String redisUrl;

    @Value(value = "${spring.redis.port}")
    private Integer redisPort;

    Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    //存放鉴权信息的Header名称，默认是Authorization
    private String Authorization = "Authorization";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * 存放用户名称和对应的key
     */
    public static final String USER_KEY = "USER_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String token = request.getHeader(Authorization);
            log.info("获取到的token为: {} ", token);
            String account = null;
            if (token != null && token.length() != 0) {
                //从redis中根据键token来获取绑定的username
                account = RedisHelper.get(token);
            }
            //判断username不为空的时候
            if (account != null && !account.trim().equals("")) {
                RedisHelper.expire(account, CommonStatic.TOKEN_EXPIRE_TIME);
                RedisHelper.expire(token, CommonStatic.TOKEN_EXPIRE_TIME);
            }
            request.setAttribute(USER_KEY, account);
            return true;
        } else {
            JSONObject jsonObject = new JSONObject();

            PrintWriter out = null;
            try {
                response.setStatus(unauthorizedErrorCode);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                jsonObject.put("code", ((HttpServletResponse) response).getStatus());
                //鉴权失败后返回的错误信息，默认为401 unauthorized
                jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                out = response.getWriter();
                out.println(jsonObject);

                return false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }

        }
        request.setAttribute(USER_KEY, null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}