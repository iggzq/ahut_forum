package com.forum.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.article.entity.CommentArticle;
import com.forum.article.vo.CommentArticleVO;

import java.util.List;

/**
 * <p>
 * 文章评论 服务类
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-21 09:44:10
 */
public interface CommentArticleService extends IService<CommentArticle> {
    List<CommentArticleVO> getCommentsById(String id);
}
