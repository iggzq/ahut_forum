package com.forum.search.repository;

import com.forum.search.document.ArticleDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDocumentRepository extends ElasticsearchRepository<ArticleDocument, String> {

    @Query("{" +
            "  \"bool\": {" +
            "    \"should\": [" +
            "      {\"match\": {\"title\": \"?0\"}}," +
            "      {\"match\": {\"content\": \"?0\"}}" +
            "    ]" +
            "  }" +
            "}")
    List<ArticleDocument> searchByTitleOrContent(String keyword);
}
