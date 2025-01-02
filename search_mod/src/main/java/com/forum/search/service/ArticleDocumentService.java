package com.forum.search.service;

import com.forum.search.document.ArticleDocument;
import com.forum.search.repository.ArticleDocumentRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleDocumentService {

    @Resource
    private ArticleDocumentRepository repository;


    public ArticleDocument save(ArticleDocument document) {
        return repository.save(document);
    }

    public List<ArticleDocument> findByContent(String content){
        return repository.searchByTitleOrContent(content);
    }
}
