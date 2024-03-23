package com.forum.article.controller;

import com.forum.article.result.CommonResult;
import com.forum.article.service.CommentArticleService;
import com.forum.article.vo.CommentArticleVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章评论 前端控制器
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-21 09:44:10
 */
@RestController
@RequestMapping("/comment/")
public class CommentArticleController {

    @Resource
    private CommentArticleService commentArticleService;

    @GetMapping("getCommentById")
    public CommonResult<List<CommentArticleVO>> getCommentsById(@RequestParam String id) {
        List<CommentArticleVO> commentsById = commentArticleService.getCommentsById(id);
        return CommonResult.success(commentsById);
    }
}
