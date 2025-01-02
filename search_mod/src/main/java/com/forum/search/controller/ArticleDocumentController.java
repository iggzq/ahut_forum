package com.forum.search.controller;

import com.forum.common.result.CommonResult;
import com.forum.search.document.ArticleDocument;
import com.forum.search.service.ArticleDocumentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article/search/")
public class ArticleDocumentController {

	@Resource
	private ArticleDocumentService articleDocumentService;

	@GetMapping("byKey")
	public CommonResult<List<ArticleDocument>> searchByKey(@RequestParam String key) {
		List<ArticleDocument> articleDocuments = articleDocumentService.findByContent(key);
		return CommonResult.success(articleDocuments);
	}


}
