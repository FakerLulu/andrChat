package com.example.chat;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Ccc {



    Socket socket;
    ChatActivity ma;
    final TextView textView;
    static private String serverIP;
    private ConnectionEnum ce = ConnectionEnum.ServerIP;

    public static void setMyName(String myName) {
        Ccc.myName = myName;
    }

    private static String myName;

    public Ccc(ChatActivity chatActivity) {
        ma = chatActivity;
        textView = ma.findViewById(R.id.textView11);
        serverIP = ce.getIp();
//        myName = ""+(int)(Math.random()*100);
    }

    void start() {
        if(socket != null && socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Runnable rr = () -> {

            try {

                socket = new Socket();
                socket.connect(new InetSocketAddress(serverIP, 9000));
                makeToast( "서버 - "+socket.getInetAddress().getHostAddress()+":"+socket.getPort()+" 접속");

            } catch (IOException e) {
                e.printStackTrace();
            }
            recieve();
        };
        Thread t = new Thread(rr);
        t.start();
    }

    /**
     *
     */
    private void recieve() {
    boolean isend = false;
        while (!isend) {

            try {
                InputStream bufReader = (socket.getInputStream());

                byte[] bb = new byte[1024];
                int readByteSize = bufReader.read(bb);

                String message;
                message = new String(bb, 0, readByteSize, "UTF-8");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 사용하고자 하는 코드
                        textView.append("\n"+ message);
                        final int scrollAmount = textView.getLayout().getLineTop(textView.getLineCount()) - textView.getHeight();
                        // if there is no need to scroll, scrollAmount will be <=0
                        if (scrollAmount > 0)
                            textView.scrollTo(0, scrollAmount);
                        else
                            textView.scrollTo(0, 0);
                    }
                }, 0);


            } catch (Exception e) {
                e.printStackTrace();
                textView.setText(textView.getText() + "\n서버끊어짐");
                try {
                    socket.close();
                    isend= true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    public void send(String msg) {
        Runnable rr = () -> {

            try {
    if(socket == null){
        makeToast("접속 먼저 하세요");
    }
                // 서버 접속

                //textView.setText(textView.getText().toString()+"\n"+msg+(socket.getInetAddress()));

                // Server에 보낼 데이터

                OutputStream bufWriter = (socket.getOutputStream());

                bufWriter.write((myName+" : "+msg).getBytes("UTF-8"));

                bufWriter.flush();

            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        Thread t = new Thread(rr);
        t.start();
    }

    public void sendPhoto(File img) {
        Runnable rr;
        rr = () -> {



            try {
                Socket pSocket = new Socket();

                pSocket.connect(new InetSocketAddress(serverIP,7777));
                if(pSocket == null){
                    makeToast("접속 먼저 하세요");
                }
                // 서버 접속

                //textView.setText(textView.getText().toString()+"\n"+msg+(socket.getInetAddress()));

                // Server에 보낼 데이터

                File file = img;
                if (!file.exists()) {
                    System.out.println("File not Exist.");
                    System.exit(0);
                }

                BufferedOutputStream toServer = new BufferedOutputStream(pSocket.getOutputStream());
                DataOutputStream dos = new DataOutputStream(pSocket.getOutputStream());
                dos.writeUTF(new String(myName.getBytes(), "UTF-8"));
                dos.writeUTF(new String(file.getName().getBytes(), "UTF-8"));
                dos.writeUTF("" + file.length());

                OutputStream outputStream = pSocket.getOutputStream();

                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);

                byte[] dataBuff = new byte[(int) file.length()];
                int length = fileInputStream.read(dataBuff);
                while (length != -1) {
                    outputStream.write(dataBuff, 0, length);
                    length = fileInputStream.read(dataBuff);
                }
                System.out.println("전송 성공");

                byte[] buf = new byte[4096]; //buf 생성합니다.
                int theByte = 0;
                while ((theByte = bis.read(buf)) != -1) // BufferedInputStream으로
                {
                    toServer.write(buf,0,theByte);
                }

                toServer.flush();
                toServer.close();
                bis.close();
                fileInputStream.close();
                pSocket.close();
            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        Thread t = new Thread(rr);
        t.start();
    }

    private void makeToast(String tm) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ma,tm,Toast.LENGTH_SHORT).show();;
            }
        }, 0);
    }

}

