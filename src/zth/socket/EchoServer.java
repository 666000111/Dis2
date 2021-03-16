package zth.socket;

import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws Exception{
        Socket clientSocket = null;
        ServerSocket listenSocket = new ServerSocket(8189);
        clientSocket = listenSocket.accept();
        System.out.println("Accepted connection from client");
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(outputStream);
        String line = null;
        while((line = in.readLine())!=null){
            System.out.println("Message from CLient:"+line);
            out.println("from server"+line);
            out.flush();
        }
        clientSocket.close();
        listenSocket.close();
    }
}
