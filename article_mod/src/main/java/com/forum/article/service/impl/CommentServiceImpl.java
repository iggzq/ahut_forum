package com.forum.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.article.entity.CommentArticle;
import com.forum.article.mapper.CommentArticleMapper;
import com.forum.article.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentArticleMapper, CommentArticle> implements CommentService {

    @Resource
    private CommentArticleMapper commentArticleMapper;

    @Override
    public List<CommentArticle> getCommentsById(String id) {
        LambdaQueryWrapper<CommentArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommentArticle::getArticleId, id);
        return commentArticleMapper.selectList(lambdaQueryWrapper);
    }
}
