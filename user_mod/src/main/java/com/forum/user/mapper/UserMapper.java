package com.forum.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 04:20:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
