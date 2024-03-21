package com.forum.article.result;

import lombok.Data;

@Data
public class CommonResult<T> {
    //返回码
    private Integer code;
    //返回描述
    private String desc;
    //返回的数据
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(200, "success", data);
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(200, "success", null);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(500, "failed", null);
    }

    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<T>(500, msg, null);
    }

    public static <T> CommonResult<T> failed(Integer code, String msg) {
        return new CommonResult<T>(code, msg, null);
    }






}
