package com.forum.article.controller;

import com.forum.article.entity.Article;
import com.forum.article.entity.CommentArticle;
import com.forum.article.result.CommonResult;
import com.forum.article.service.ArticleService;
import com.forum.article.service.impl.TestServiceImpl;
import com.forum.article.vo.LikeArticleVO;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 存储文章数据 前端控制器
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:27:56
 */
@RestController
@RequestMapping("/article/")
public class ArticleController {
    private TestServiceImpl testService;

    @Resource
    private ArticleService articleService;

    @Autowired
    private TestServiceImpl testServiceImpl;

    @Resource
    private TestServiceImpl testServiceImpl2;

    @Autowired
    private void articleService(TestServiceImpl testService){
        this.testService = testService;
    }
    @PostMapping("saveArticle")
    public CommonResult<String> saveArticle(@RequestBody SaveArticleVO saveArticleVO) {
        Boolean b = articleService.saveArticle(saveArticleVO);
        if (b) {
            return CommonResult.success("保存成功");
        } else {
            return CommonResult.failed("保存失败,请重试");
        }
    }

    @GetMapping("getArticles")
    public CommonResult<List<Article>> getArticles(@RequestParam int page, @RequestParam int size) {
        System.out.println("");
        return CommonResult.success(articleService.getArticles(page, size));
    }

    @GetMapping("getArticleById")
    public CommonResult<Article> getArticleById(@RequestParam Long articleId) {
        return CommonResult.success(articleService.getById(articleId));
    }

    @PostMapping("likeArticle")
    public CommonResult<String> likeArticle(@RequestBody LikeArticleVO likeArticleVO) {
        Boolean b = articleService.likeArticle(likeArticleVO);
        if (b) {
            return CommonResult.success("点赞成功");
        } else {
            return CommonResult.failed("点赞失败,请重试");
        }
    }

    @PostMapping("commentArticle")
    public CommonResult<String> commentArticle(@RequestBody CommentArticle commentArticle) {
        Boolean b = articleService.commentArticle(commentArticle);
        if (b) {
            return CommonResult.success("评论成功");
        } else {
            return CommonResult.failed("评论失败,请重试");
        }
    }


}
