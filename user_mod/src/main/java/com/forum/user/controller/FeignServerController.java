package com.forum.user.controller;

import com.forum.user.service.UserService;
import com.forum.user.vo.UserNameVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lituizi
 */
@RequestMapping("/feign/UserInfo")
public class FeignServerController {
    @Resource
    UserService userService;

    @RequestMapping("/getUserNames")
    List<UserNameVo> getUserNames(@RequestBody List<Long> ids) {
        return userService.getUserNames(ids);
    }

}
