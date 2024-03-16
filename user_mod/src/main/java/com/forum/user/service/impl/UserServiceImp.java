package com.forum.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.user.entity.User;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.forum.user.vo.LoginUserVo;
import com.forum.user.vo.RegisterUserVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 09:10:51
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void registerSave(RegisterUserVo registerUserVo) {
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
        userMapper.insert(user);
    }

    @Override
    public Boolean login(LoginUserVo loginUserVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, loginUserVo.getName()).eq(User::getPassword, loginUserVo.getPassword());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            StpUtil.login(user.getId());
            return true;
        } else {
            return false;
        }
    }
}
