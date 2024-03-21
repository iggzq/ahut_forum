package com.forum.article.controller;

import com.forum.article.entity.CommentArticle;
import com.forum.article.result.CommonResult;
import com.forum.article.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment/")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("getCommentById")
    public CommonResult<List<CommentArticle>> getCommentById(@RequestParam String id) throws InterruptedException {
        Thread.sleep(3000);
        List<CommentArticle> commentsById = commentService.getCommentsById(id);
        return CommonResult.success(commentsById);
    }
}
