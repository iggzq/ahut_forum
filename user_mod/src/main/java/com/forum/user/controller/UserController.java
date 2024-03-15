package com.forum.user.controller;

import cn.dev33.satoken.secure.SaBase64Util;
import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.forum.user.entity.User;
import com.forum.user.service.UserService;
import com.forum.user.vo.RegisterUserVo;
import jakarta.annotation.Resource;
import org.ltz.result.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

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
        User user = new User();
        //VO转实体
        BeanUtils.copyProperties(registerUserVo, user);
        //设置id和其他相关参数
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        LocalDateTime now = LocalDateTime.now();
        user.setId(snowflakeNextId);
        user.setCreateTime(now);
        user.setStatus((byte) 0);
        user.setUpdateTime(now);
        userService.save(user);
        //返回结果
        return CommonResult.success("注册成功");
    }
}
