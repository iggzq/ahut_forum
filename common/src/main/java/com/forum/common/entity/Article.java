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
public class Article implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long userId;

	private String userName;

	private Integer admissionYear;

	private String title;

	private String content;

	private Byte topicType;

	private Integer likeCount;

	private Integer commentCount;

	private LocalDateTime updateTime;

	private LocalDateTime createTime;

	private Byte status;

}
