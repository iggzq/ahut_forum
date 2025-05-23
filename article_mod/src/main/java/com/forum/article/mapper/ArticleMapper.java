package com.forum.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.article.dto.mysql.HotArticleDTO;
import com.forum.article.vo.ArticleGetVo;
import com.forum.common.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	List<ArticleGetVo> getArticleByPage(int page, int size, Byte topicType);

	void addLikeNumber(Long id);

	void addCommentNumber(Long id);

	void subLikeNumber(Long id);

	List<ArticleGetVo> getArticleByPageAndDateOrder(int page, int size, Byte topicType);

	List<HotArticleDTO> getHotArticleContent(@Param("list") List<Long> ids);

}
