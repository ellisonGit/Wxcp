package com.hnjca.wechat.controller;

/**
 * Description:
 * User: Ellison
 * Date: 2019-06-17
 * Time: 16:00
 * Modified:
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String args[]) throws IOException {
        //为了简单起见，所有的异常信息都往外抛
        try{
            //创建一个服务器对象，端口3333
            ServerSocket serverSocket=new ServerSocket(8002);
            //创建一个客户端对象，这里的作用是用作多线程，必经服务器服务的不是一个客户端
            Socket client=null; boolean flag=true; while(flag){
                System.out.println("服务器已启动，等待客户端请求。。。。");
            //accept是阻塞式方法，对新手来说这里很有可能出错，下面的注意事项我会说到
                client=serverSocket.accept();
                //创建一个线程，每个客户端对应一个线程
                System.out.println("输出");
                new Thread(new EchoThread(client)).start();
            }
            client.close();
            serverSocket.close();
            System.out.println("服务器已关闭。");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
class EchoThread implements Runnable{
    private Socket client;
    public EchoThread(Socket client){
        this.client=client;

    }
public void run(){
    //run不需要自己去执行，好像是线程器去执行了来着，可以去看api
    try {
        BufferedReader in=null;
        String br=null;
        boolean flag=true;
        while(flag==true){
            //Java流的操作没意见吧
            in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            br=in.readLine();
            System.out.println("++:"+br);
            recordMsg(br+"\r\n");
            //写入到文件
            }
    } catch (IOException e1) {
        // TODO 自动生成的 catch 块
        e1.printStackTrace();
    }catch (Exception e) {
        // TODO: handle exception
        System.out.println("error");
    }
}
public void recordMsg(String br) throws IOException{
    File file=new File("test.data");
    if(!file.exists()){ file.createNewFile();
    } FileWriter writer=new FileWriter(file,true);
    writer.write(br+"\r\n"); writer.close();
}
}
