package com.forum.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 存储文章数据
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-16 09:50:58
 */
@Data
@TableName("article")
public class Article implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long id;

	@TableField("user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long userId;

	@TableField("user_name")
	private String userName;

	@TableField("admission_year")
	private Integer admissionYear;

	@TableField("title")
	private String title;

	@TableField("content")
	private String content;

	@TableField("topic_type")
	private Byte topicType;

	@TableField("like_count")
	private Integer likeCount;

	@TableField("comment_count")
	private Integer commentCount;

	@TableField("update_time")
	private LocalDateTime updateTime;

	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField("status")
	private Byte status;

}
