package com.forum.article.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.forum.article.config.CustomDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lituizi
 */
@Data
public class CommentUserVO implements Serializable {

	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@TableField("article_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long articleId;

	@TableField("user_name")
	private String username;

	@TableField("content")
	private String content;

	@TableField("content")
	private String articleTitle;

	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date updateTime;

}
