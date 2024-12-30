package com.forum.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-18 08:52:12
 */
@Getter
@Setter
@ToString
@TableName("like_article")
public class LikeArticle implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@TableField("article_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long articleId;

	@TableField("user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	@TableField("like_user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long likeUserId;

	@TableField("status")
	private Byte status;

}
