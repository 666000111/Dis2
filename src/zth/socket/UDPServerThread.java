package zth.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerThread {
    public static void main(String[] args) {
        int port = 6789;
        int threadCount = 0;
        try{
            DatagramSocket udpSocket = new DatagramSocket(port);
            byte[] buffer = new byte[1024];
            while(true){
                DatagramPacket req = new DatagramPacket(buffer,buffer.length);
                udpSocket.receive(req);
                UDPThread udpThread = new UDPThread(udpSocket,req);
                udpThread.run();
                threadCount++;
                System.out.println("The total number of clients is " + threadCount + ".");


            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}



class UDPThread extends Thread {
    static int threadCount = 0;
    DatagramSocket udpSocket = null;
    DatagramPacket req = null;//接受的udp包

    public UDPThread(DatagramSocket udpSocket, DatagramPacket req) {
        this.udpSocket = udpSocket;
        this.req = req;
    }

    public void run() {
        try {

            DatagramPacket reply = new DatagramPacket(req.getData(),
                    req.getLength(), req.getAddress(),
                    req.getPort());
            udpSocket.send(reply);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
        }


    }
}
