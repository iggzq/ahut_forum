package com.forum.article.controller;

import com.forum.article.result.CommonResult;
import com.forum.article.service.ArticleService;
import com.forum.article.vo.SaveArticleVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
