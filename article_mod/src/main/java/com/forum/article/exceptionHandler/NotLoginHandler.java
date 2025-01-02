package com.forum.article.exceptionHandler;

import cn.dev33.satoken.exception.NotLoginException;
import com.forum.common.result.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NotLoginHandler {

	@ExceptionHandler(NotLoginException.class)
	@ResponseBody
	public CommonResult<String> notLoginExceptionHandler() {
		return CommonResult.NotLogin();
	}

}
