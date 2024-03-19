package com.forum.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.article.entity.CommentArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章评论 Mapper 接口
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-19 01:42:45
 */
@Mapper
public interface CommentArticleMapper extends BaseMapper<CommentArticle> {

}
