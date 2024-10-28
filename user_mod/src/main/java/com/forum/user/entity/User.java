package com.forum.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty("用户名")
	@TableField("name")
	private String name;

	@TableField("password")
	private String password;

	@TableField("admission_year")
	private Integer admissionYear;

	@ApiModelProperty("用户自我描述")
	@TableField("profile_desc")
	private String profileDesc;

	@ApiModelProperty("自定义安全问题")
	@TableField("question")
	private String question;

	@TableField("answer")
	private String answer;

	@ApiModelProperty("创建用户时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@ApiModelProperty("修改用户时间")
	@TableField("update_time")
	private LocalDateTime updateTime;

	@ApiModelProperty("用户账户状态")
	@TableField("status")
	private Byte status;

}
