package com.forum.article.feign;

import com.forum.common.entity.Article;
import com.forum.common.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SearchModFeign", url = "${search.service.url}")
public interface SearchModFeign {

    @PostMapping("/articleDocument/save")
    CommonResult<String> save(@RequestBody Article article);
}
