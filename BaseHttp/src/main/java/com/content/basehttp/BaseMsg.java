package com.content.basehttp;

/**
 * Created by gqh on 2017/9/21.
 */

public class BaseMsg<T> {

    public static final String CODE_OK = "0";
    public static final String CODE_ERROR = "1";


    protected String msg;
    protected String code;
    protected T data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    @Override
//    public String toString() {
//        return "BaseMsg{" +
//                "msg='" + msg + '\'' +
//                ", code='" + code + '\'' +
//                ", data=" + data +
//                '}';
//    }
}
