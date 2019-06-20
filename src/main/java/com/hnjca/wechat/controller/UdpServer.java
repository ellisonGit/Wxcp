package com.hnjca.wechat.controller;

/**
 * Description:
 * User: Ellison
 * Date: 2019-06-17
 * Time: 16:23
 * Modified:
 */
import java.io.*;
import java.net.*;

class UdpServer{
    public static void main(String[] args)throws IOException{
        DatagramSocket  server = new DatagramSocket(8002);
        byte[] recvBuf = new byte[100];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        while (true) {
            server.receive(recvPacket);
            String recvStr = new String(recvPacket.getData(), 0,
                    recvPacket.getLength());

            System.out.println("收到:" + recvStr.getBytes("utf-8"));
            System.out.println("getAddress:"+recvPacket.getAddress()+"getOffset:"+recvPacket.getOffset()+
                    " getPort:"+recvPacket.getPort()+"getSocketAddress:"+recvPacket.getSocketAddress());
           System.out.println("ellison>>>>>>>>>>>"+ recvStr.getBytes().hashCode());
            if(recvStr.endsWith("q")|| recvStr.endsWith("quit")){
                break;
            }
        }
        server.close();
    }
}