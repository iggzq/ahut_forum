package com.forum.article.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "articleEs")
public class ArticleDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, index = false)
    private String articleId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Keyword, index = false)
    private Byte topicType;

    @Field(type = FieldType.Keyword, index = false)
    private Integer likeCount;

    @Field(type = FieldType.Keyword, index = false)
    private Integer commentCount;

    @Field(type = FieldType.Keyword, index = false)
    private LocalDateTime createTime;

}