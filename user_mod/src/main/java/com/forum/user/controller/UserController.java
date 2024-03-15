package com.forum.user.controller;

import com.forum.user.service.UserService;
import com.forum.user.vo.LoginUserVo;
import com.forum.user.vo.RegisterUserVo;
import jakarta.annotation.Resource;
import org.ltz.result.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 08:46:53
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册
     * @param registerUserVo
     * @return 注册结果
     */
    @PostMapping("/register")
    public CommonResult<String> register(@RequestBody RegisterUserVo registerUserVo) {
        userService.registerSave(registerUserVo);
        return CommonResult.success("注册成功");
    }

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody LoginUserVo loginUserVo){
        Boolean login = userService.login(loginUserVo);
        return login ? CommonResult.success("登录成功") : CommonResult.failed("登录失败");
    }
}

