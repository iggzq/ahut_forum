package com.forum.user.controller;

import org.ltz.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-14 10:17:52
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @GetMapping("login")
    public CommonResult<String> login(){
        return CommonResult.success("登录成功");
    }
}
