package com.forum.article.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章评论
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-19 01:42:45
 */
@Getter
@Setter
@TableName("comment_article")
@ApiModel(value = "CommentArticle对象", description = "文章评论")
public class CommentArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章评论id")
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("评论人id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("评论人姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("被评论人评论id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("to_user_comment_id")
    private Long toUserCommentId;

    @ApiModelProperty("被评论人的id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("to_user_id")
    private Long toUserId;

    @ApiModelProperty("被评论人姓名")
    @TableField("to_user_name")
    private String toUserName;

    @ApiModelProperty("文章id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty("文章评论内容")
    @TableField("comment")
    private String comment;

    @ApiModelProperty("评论创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("文章状态")
    @TableField("status")
    private Byte status;
}
