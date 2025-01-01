package com.forum.article.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lituizi
 */
@Data
public class ReplyVO implements Serializable {

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long parentId;

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(as = Long.class)
	private Long uid;

	private String content;

	private Integer likes;

	private LocalDateTime createTime;

	private UserVO user;

}
