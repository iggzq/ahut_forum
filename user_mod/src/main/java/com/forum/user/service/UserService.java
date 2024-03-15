package com.forum.user.service;

import com.forum.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.user.vo.RegisterUserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 09:10:51
 */
public interface UserService extends IService<User> {

    public void registerSave(RegisterUserVo registerUserVo);
}
