package com.rpc.protocol;

import lombok.Data;

@Data  //定义传输协议
public class InvokerProtocol {
    private String className; //类名
    private String methodName; //函数名称
    private Class<?>[] parames; //参数类型
    private Object[] values; //参数列表

}
