package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("preHandle");
        //
        HandlerMethod method = (HandlerMethod) handler;
        String className = method.getBean().getClass().getSimpleName();
        String methodName = method.getMethod().getName();

        // 排除掉登陆请求
        if(StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
            log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
            return true;
        }

        // log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}",className,methodName,requestParamBuffer.toString());

        User user = (User) httpServletRequest.getSession(true).getAttribute(Const.CURRENT_USER);
        if(user == null || (user.getRole() != Const.Role.ROLE_ADMIN)) {
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter out = httpServletResponse.getWriter();

            if(user == null) {
                /*Map result = Maps.newHashMap();
                result.put("status", ResponseCode.ERROR.getCode());
                result.put("msg", "用户未登录");*/
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请求被拦截，用户未登录")));
            } else {
                /*Map result = Maps.newHashMap();
                result.put("status", ResponseCode.ERROR.getCode());
                result.put("msg", "请登陆管理员");*/
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("请求被拦截，用户无权限")));
            }
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
