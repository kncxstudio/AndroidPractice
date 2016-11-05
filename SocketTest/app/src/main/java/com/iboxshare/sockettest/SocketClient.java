package com.iboxshare.sockettest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by KN on 16/10/30.
 */

public class SocketClient {
    static Socket client;

    public SocketClient(String site, int port){
        try{
            client = new Socket(site,port);
            System.out.println("Client is created! site:"+site+" port:"+port);
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String sendMsg(String msg){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(msg);
            out.flush();
            Log.e("Flush","完成");
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }
    public String getMsgFromTcpServer() throws IOException {
        InputStream is = client.getInputStream();

        byte[] buffer = new byte[1024];

        int length = is.read(buffer);

        String str = new String(buffer, 0, length);

        System.out.println(str);
        return str;
    }
    public void closeSocket(){
        try{
            client.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
