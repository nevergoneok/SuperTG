package com.supertg.supertg.rx;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:15:57
 */
public class HttpResult<T> {

    public String ret;
    //@SerializedName(value = "results", alternate = {"result"})
    public T errDesc;//T 是返回结果results的类型
}
