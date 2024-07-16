package com.pcs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /*状态码*/
    private Integer code;
    /*信息*/
    private String msg;
    /*数据*/
    private Object data;

    public static Result success() {
        return new Result(200, "操作成功", null);
    }

    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result fail(String msg) {
        return new Result(500, msg, null);
    }

    public static Result fail(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result fail() {
        return new Result(500, "请联系管理员处理！", null);
    }

}
