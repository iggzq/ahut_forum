package com.forum.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.article.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 存储文章数据 Mapper 接口
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:50:58
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

	List<Article> getArticleByPage(int page, int size);

	void addLikeNumber(Long id);

	void addCommentNumber(Long id);

	void subLikeNumber(Long id);

}
