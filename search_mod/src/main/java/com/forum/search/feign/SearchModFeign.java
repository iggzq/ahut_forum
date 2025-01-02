package com.forum.search.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SearchModFeign", url = "http://localhost:8084/feign/")
public class SearchModFeign {

}
