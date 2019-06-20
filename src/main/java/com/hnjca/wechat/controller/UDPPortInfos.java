package com.hnjca.wechat.controller;

/**
 * Description:
 * User: Ellison
 * Date: 2019-06-17
 * Time: 16:16
 * Modified:
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
class UDPPortInfos {
    public static void main(String[] args)throws IOException{
        DatagramSocket client = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int port = 8002;
        byte[] sendBuf;
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("请输要发送的内容：");
            String str  = sc.nextLine();
            sendBuf = str.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf , sendBuf.length , addr , port );
            client.send(sendPacket);
            if(str.endsWith("q")|| str.endsWith("quit")){
                break;
            }
        }
        client.close();
    }
}