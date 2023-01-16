package com.netty;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, GPServlet> servletMappings = new HashMap<String, GPServlet>();
    private Properties webxml = new Properties();

    private void init() {
        //加载web.xml文件 同时初始化ServletMapping对象
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            webxml.load(fis);
            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.equals(".url")) {
                    String servletName = key.replace("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    //单实例，多线程
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMappings.put(url, obj);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        init();
        try {
            server = new ServerSocket(this.port);
            System.out.println("GPTomcat已启动，监听的端口是：" + this.port);
            //2.等待用户请求，用一个死循环来等待用户请求
            while (true) {
                Socket client = server.accept();
                //3.HTTP请求，发送的数据就是字符串---有规律的字符串（HTTP)
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();
        //4.Request(InputStream)/(Response(OutputStream)
        GPRequest request = new GPRequest(is);
        GPResponse response = new GPResponse(os);
        //5.从协议内容中获取URL，把相应的Servlet用反射机械能实例化
        String url = request.getUrl();
        if (servletMappings.containsKey(url)) {
            //6.调用实例化对象的service（）方法，执行具体的逻辑doGet()doPost方法
            servletMappings.get(url).service(request, response);
        } else {
            response.write("404-Not Found");

        }
        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new GPTomcat().start();
    }
}
