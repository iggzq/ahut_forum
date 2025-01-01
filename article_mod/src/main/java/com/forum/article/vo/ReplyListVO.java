package com.forum.article.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lituizi
 */
@Data
public class ReplyListVO implements Serializable {

	private Integer total;

	private List<ReplyVO> list;

}
