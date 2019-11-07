package com.example.chat;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Ccc {

    /**
     * @param args
     */

    Socket socket;
    MainActivity ma;
    final TextView textView;

    public Ccc(MainActivity mainActivity) {
        ma = mainActivity;
        textView = ma.findViewById(R.id.textView11);
    }

    void start() {
        Runnable rr = () -> {

            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.15.28", 9000));
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

        while (true) {

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
                        textView.append("\n" + "Message : " + message);
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
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    public void send(String msg) {
        Runnable rr = () -> {

            try {

                // 서버 접속

                //textView.setText(textView.getText().toString()+"\n"+msg+(socket.getInetAddress()));

                // Server에 보낼 데이터

                OutputStream bufWriter = (socket.getOutputStream());

                bufWriter.write(msg.getBytes("UTF-8"));

                bufWriter.flush();

            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        Thread t = new Thread(rr);
        t.start();
    }

}

