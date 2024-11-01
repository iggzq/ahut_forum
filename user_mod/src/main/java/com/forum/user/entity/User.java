package com.forum.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author LiTuiZi
 * @since 2024-03-15 04:20:58
 */
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField("name")
	private String name;

	@TableField("password")
	private String password;

	@TableField("admission_year")
	private Integer admissionYear;

	@TableField("profile_desc")
	private String profileDesc;

	@TableField("question")
	private String question;

	@TableField("answer")
	private String answer;

	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField("update_time")
	private LocalDateTime updateTime;

	@TableField("status")
	private Byte status;

}
