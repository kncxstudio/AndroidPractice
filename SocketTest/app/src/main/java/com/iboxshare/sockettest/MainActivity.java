package com.iboxshare.sockettest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.iboxshare.sockettest.API.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainAvtivity";
    TextView contentFromServerTV;
    EditText sendToServerET;
    Button sendMsgBtn,TCPBtn;
    ScrollView scrollView;
    Socket TcpSocket;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,"handler");
            contentFromServerTV.append(msg.getData().getString("data")
                    + "\n");
            int y = scrollView.getHeight();
            scrollView.smoothScrollTo(0,y+100);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void bindView(){
        contentFromServerTV = (TextView) findViewById(R.id.activity_main_receive_content);
        sendToServerET = (EditText) findViewById(R.id.activity_main_send_content);
        sendMsgBtn = (Button) findViewById(R.id.send_btn);
        TCPBtn = (Button) findViewById(R.id.activity_main_TCP_btn);
        scrollView = (ScrollView) findViewById(R.id.activity_main_scroll);
    }

    void init() throws IOException {
        final UDPClient udpClient = new UDPClient();

        //建立TCP接收线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TcpSocket = new Socket(API.SERVER_IP,API.SERVER_TCP_PORT);
                    while (true){
                        InputStream is = TcpSocket.getInputStream();

                        byte[] buffer = new byte[2048];

                        int length = is.read(buffer);

                        String str = new String(buffer, 0, length);

                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();




        //建立UDP接收线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String UdpMsg = udpClient.receiveUdpMsg();
                    Log.e(TAG,UdpMsg);
                    Bundle bundle = new Bundle();
                    bundle.putString("data",UdpMsg);
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        }).start();



        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            udpClient.sendUdpMsg(sendToServerET.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        TCPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //SocketClient tcpClient = new SocketClient(API.SERVER_IP,API.SERVER_TCP_PORT);
                        //tcpClient.sendMsg("GET CUR TIME\0");
                        try {
                            PrintWriter os = new PrintWriter(TcpSocket.getOutputStream());
                            os.println("GET CUR TIME\0");
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
