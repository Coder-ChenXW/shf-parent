package com.atguigu.interceptor;

import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端登录拦截器
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class LoginInterceptor implements HandlerInterceptor {


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
            throws Exception {

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model) throws Exception {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object) throws Exception {

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo == null){
            // 未登录
            Result<String> result = Result.build("还没有登录", ResultCodeEnum.LOGIN_AUTH);
            // 将result响应到前端
            WebUtil.writeJSON(response,result);
            return false;
        }
        return true;
    }

}