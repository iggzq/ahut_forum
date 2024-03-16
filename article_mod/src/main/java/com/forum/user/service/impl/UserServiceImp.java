package com.forum.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.user.entity.User;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 04:53:14
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
