package com.forum.article;

import cn.hutool.core.lang.id.NanoId;
import com.forum.article.entity.LikeArticle;
import com.forum.common.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {

	@Test
	public void TestNanoID() {
		String s = NanoId.randomNanoId();
		System.out.println(s);
	}

	@Test
	public void TestSet() {
		LikeArticle likeArticle = new LikeArticle();
		likeArticle.setArticleId(1L);
		Set<LikeArticle> likeArticleVOSet = new HashSet<>();
		likeArticleVOSet.add(likeArticle);
		likeArticle.setArticleId(2L);
		likeArticleVOSet.add(likeArticle);
		likeArticleVOSet.stream().iterator().forEachRemaining(System.out::println);
	}

	@Test
	public void TestGC() {
		Article article = new Article();
	}

}
