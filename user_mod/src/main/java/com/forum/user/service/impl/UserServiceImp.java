package com.forum.user.service.impl;

import com.forum.user.entity.User;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 08:46:53
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
