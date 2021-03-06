package zth.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPThreadPool {

    public static void main(String[] args){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

        try{
            ServerSocket listenSocket=new ServerSocket(8189);
            Socket socket=null;

            int count=0;
            System.out.println("Server listening at 8189");
            byte[] buffer = new byte[1024];
            while(true){
                socket = listenSocket.accept();
                count++;
                System.out.println("The total number of clients is " + count + ".");
                TCPThread tcpThread = new TCPThread(socket);
                executor.execute(tcpThread);

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}

class TCPThread implements Runnable {

    Socket socket = null;

    public TCPThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            String info=null;
            while((info=br.readLine())!=null){
                System.out.println("Message from client:"+info);
                pw.println(info);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
