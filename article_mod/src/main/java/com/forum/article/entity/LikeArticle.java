package com.forum.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("like_article")
@ApiModel(value = "LikeArticle对象", description = "点赞文章对象")
public class LikeArticle implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@ApiModelProperty("文章id")
	@TableField("article_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long articleId;

	@ApiModelProperty("发布人id")
	@TableField("user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	@ApiModelProperty("点赞人的id")
	@TableField("like_user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long likeUserId;

	@TableField("status")
	private Byte status;

}
