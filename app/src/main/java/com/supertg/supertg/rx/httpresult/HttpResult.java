package com.supertg.supertg.rx.httpresult;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:15:57
 */
public class HttpResult<T> {

    //返回格式是和服务端共同商讨的
//    private int code;//错误码
//    private String message;//提示信息
    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;//T 是返回结果results的类型
}
