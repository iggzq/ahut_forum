package com.forum.article.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.forum.article.config.CustomDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lituizi
 */
@Data
public class CommentUserVO implements Serializable {

    @ApiModelProperty("评论人姓名")
    @TableField("user_name")
    private String username;

    @ApiModelProperty("文章评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("文章评论内容")
    @TableField("content")
    private String articleTitle;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateTime;
}
