package com.forum.article.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.forum.article.config.CustomDateSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章评论
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-21 09:44:10
 */

@TableName("comment_article")
@Data
public class CommentArticle implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@TableField("u_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long uid;

	@TableField("user_name")
	private String username;

	@TableField("parentId")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@TableField("to_user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long toUserId;

	@TableField("article_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long articleId;

	@TableField("content")
	private String content;

	@TableField("likes")
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer likes;

	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;

	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date updateTime;

	@TableField("status")
	@JsonSerialize(using = ToStringSerializer.class)
	private Byte status;

}
