package zth.socket;


import java.io.*;
import java.net.*;


public class EchoClient {
    public static void main(String[] args) throws Exception{
        String userInput = null;
        String echoMessage = null;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("127.0.0.1",8189);
        System.out.println("connected to echo server");
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(outputStream);
        while ((userInput = stdIn.readLine())!=null){
            out.println(userInput);
            out.flush();
            echoMessage = in.readLine();
            System.out.println("echo from server"+echoMessage);
        }
        socket.close();
    }


}
