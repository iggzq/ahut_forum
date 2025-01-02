package com.forum.search.service;

import com.forum.common.entity.Article;
import com.forum.search.document.ArticleDocument;
import com.forum.search.repository.ArticleDocumentRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleDocumentService {

    @Resource
    private ArticleDocumentRepository repository;


    public ArticleDocument save(Article article) {
        ArticleDocument articleDocument = new ArticleDocument();
        articleDocument.setArticleId(article.getId());
        articleDocument.setTitle(article.getTitle());
        articleDocument.setContent(article.getContent());
        articleDocument.setTopicType(article.getTopicType());
        articleDocument.setLikeCount(article.getLikeCount());
        articleDocument.setCommentCount(article.getCommentCount());
        articleDocument.setCreateTime(LocalDate.from(article.getCreateTime()));
        return repository.save(articleDocument);
    }

    public List<ArticleDocument> findByContent(String content){
        return repository.searchByTitleOrContent(content);
    }
}
