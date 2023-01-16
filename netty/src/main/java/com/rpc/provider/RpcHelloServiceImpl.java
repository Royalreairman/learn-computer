package com.rpc.provider;

import com.rpc.api.IRpcHelloService;


public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
      return "hell0"+name+":";
    }

}
