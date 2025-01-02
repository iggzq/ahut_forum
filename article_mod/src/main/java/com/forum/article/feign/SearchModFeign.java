package com.forum.article.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SearchModFeign", url = "${search.service.url}")
public interface SearchModFeign {

}
