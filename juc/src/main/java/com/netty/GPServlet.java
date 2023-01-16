package com.netty;


public abstract class GPServlet {
    public void service(GPRequest request, GPResponse response) throws Exception {
        //由service（）方法决定是调用 doGet()还是调用doPost（）
        if ("GET".equals(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public abstract void doPost(GPRequest request, GPResponse response) throws Exception;


    public abstract void doGet(GPRequest request, GPResponse response) throws Exception;
}
