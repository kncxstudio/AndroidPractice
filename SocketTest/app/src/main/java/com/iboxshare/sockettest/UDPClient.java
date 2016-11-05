package com.iboxshare.sockettest;

import android.util.Log;

import com.iboxshare.sockettest.API.API;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by KN on 16/10/30.
 */

public class UDPClient {
    int MAX_DATA_PACKET_LENGTH = 1024;
    DatagramSocket udpSocket = new DatagramSocket();
    DatagramPacket datagramPacket;

    public UDPClient() throws SocketException {
    }


    public void sendUdpMsg(String str) throws IOException {
        InetAddress serverAddress = InetAddress.getByName(API.SERVER_IP);
        byte[] out = str.getBytes();
        datagramPacket = new DatagramPacket(out
                ,out.length
                ,serverAddress
                ,API.SERVER_UDP_PORT);
        udpSocket.send(datagramPacket);
    }

    public String receiveUdpMsg() {
        byte[] buffer = new byte[1024];
        DatagramPacket DP = new DatagramPacket(buffer,buffer.length);
        try {
            udpSocket.receive(DP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(DP.getData(),0,DP.getLength());
    }
}
