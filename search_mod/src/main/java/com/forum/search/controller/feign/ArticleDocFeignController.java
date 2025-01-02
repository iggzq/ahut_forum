package com.forum.search.controller.feign;

import com.forum.common.entity.Article;
import com.forum.common.result.CommonResult;
import com.forum.search.document.ArticleDocument;
import com.forum.search.service.ArticleDocumentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/articleDocument/")
public class ArticleDocFeignController {

    @Resource
    private ArticleDocumentService articleDocumentService;

    @PostMapping("save")
    public CommonResult<String> save(@RequestBody Article article) {
        ArticleDocument save = articleDocumentService.save(article);
        return CommonResult.success();
    }


}
