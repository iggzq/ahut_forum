package com.forum.article.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.forum.article.config.CustomDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CommentArticle对象", description = "文章评论")
@Data
public class CommentArticle implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("文章评论id")
	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@ApiModelProperty("评论人id")
	@TableField("u_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long uid;

	@ApiModelProperty("评论人姓名")
	@TableField("user_name")
	private String username;

	@ApiModelProperty("被评论人评论id")
	@TableField("parentId")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@ApiModelProperty("被评论人的id")
	@TableField("to_user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long toUserId;

	@ApiModelProperty("文章id")
	@TableField("article_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long articleId;

	@ApiModelProperty("文章评论内容")
	@TableField("content")
	private String content;

	@TableField("likes")
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer likes;

	@ApiModelProperty("评论创建时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;

	@ApiModelProperty("修改时间")
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date updateTime;

	@ApiModelProperty("文章状态")
	@TableField("status")
	@JsonSerialize(using = ToStringSerializer.class)
	private Byte status;

}
