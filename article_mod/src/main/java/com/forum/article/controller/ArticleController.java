package com.forum.article.controller;

import com.forum.article.dto.mysql.HotArticleDTO;
import com.forum.article.entity.CommentArticle;
import com.forum.article.result.CommonResult;
import com.forum.article.service.ArticleService;
import com.forum.article.vo.ArticleGetVo;
import com.forum.article.vo.ArticleVo;
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

	@PostMapping(path = "saveArticle")
	public CommonResult<String> saveArticle(@RequestBody SaveArticleVO saveArticleVO) {
		Boolean b = articleService.saveArticle(saveArticleVO);
		if (b) {
			return CommonResult.success("保存成功");
		}
		else {
			return CommonResult.failed("保存失败,请重试");
		}
	}

	@GetMapping("getArticles")
	public CommonResult<List<ArticleGetVo>> getArticles(@RequestParam int page, @RequestParam int size,
			@RequestParam Byte topicType) {
		return CommonResult.success(articleService.getArticles(page, size, topicType));
	}

	@GetMapping("getArticleById")
	public CommonResult<ArticleVo> getArticleById(@RequestParam Long articleId) {
		return CommonResult.success(articleService.getArticleById(articleId));
	}

	@PostMapping("likeArticle")
	public CommonResult<String> likeArticle(@RequestBody LikeArticleVO likeArticleVO) {
		Boolean b = articleService.likeArticle(likeArticleVO);
		if (b) {
			return CommonResult.success("点赞成功");
		}
		else {
			return CommonResult.failed("点赞失败,请重试");
		}
	}

	@PostMapping("commentArticle")
	public CommonResult<String> commentArticle(@RequestBody CommentArticle commentArticle) {
		Boolean b = articleService.commentArticle(commentArticle);
		if (b) {
			return CommonResult.success("评论成功");
		}
		else {
			return CommonResult.failed("评论失败,请重试");
		}
	}

	@GetMapping("getArticlesOrderByDate")
	public CommonResult<List<ArticleGetVo>> getArticlesOrderByDate(@RequestParam int page, @RequestParam int size,
			@RequestParam Byte topicType) {
		return CommonResult.success(articleService.getArticlesOrderByDate(page, size, topicType));
	}

	@GetMapping("getHotRank")
	public CommonResult<List<HotArticleDTO>> getHotRank(){
		List<HotArticleDTO> hotRank = articleService.getHotRank();
		return CommonResult.success(hotRank);
	}

}
