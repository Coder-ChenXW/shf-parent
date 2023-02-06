package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.UserInfoService;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/userInfo")
public class UserInfoController {


    @Reference
    private UserInfoService userInfoService;


    /**
     * @description: 发送验证码
     * @author: ChenXW
     * @date: 2023/2/6 21:14
     */
//    @RequestMapping("/sendCode/{phone}")
//    public Result sendCode(@PathVariable("phone") String phone, HttpServletRequest request) {
//        // 设置验证码为8888
//        String code = "8888";
//
//        // 将验证码放入session中
//        request.getSession().setAttribute("code", code);
//
//        // 将验证码响应到前端
//        return Result.ok(code);
//    }
    @GetMapping("/sendCode/{moble}")
    public Result sendCode(@PathVariable String moble, HttpServletRequest request) {
        String code = "1111";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }


    /**
     * @description: 注册
     * @author: ChenXW
     * @date: 2023/2/6 21:23
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //验证码
        String currentCode = (String) request.getSession().getAttribute("CODE");
        if (!code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (null != userInfo) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }


    /**
     * @description: 登录
     * @author: ChenXW
     * @date: 2023/2/6 22:57
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session) {
        // 获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        // 校验参数
        if (StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码
        if (!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        // 登录成功
        session.setAttribute("user", userInfo);

        Map map = new HashMap<>();
        map.put("nickName", userInfo.getNickName());
        map.put("phone", phone);

        return Result.ok(map);

    }

    /**
     * @description: 登出
     * @author: ChenXW
     * @date: 2023/2/6 23:12
     */
    @RequestMapping("logout")
    public Result logout(HttpSession session) {
        // 将session域中的userInfo对象移除
        session.removeAttribute("user");
        return Result.ok();
    }

}
