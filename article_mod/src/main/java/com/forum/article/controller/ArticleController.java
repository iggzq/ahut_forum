package com.forum.article.controller;

import com.forum.article.entity.Article;
import com.forum.article.result.CommonResult;
import com.forum.article.service.ArticleService;
import com.forum.article.vo.LikeArticleVO;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
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

    @Resource
    private ArticleService articleService;

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
        return CommonResult.success(articleService.getArticles(page, size));
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


}
