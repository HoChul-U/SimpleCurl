package com.nhnacademy.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Scanner;


public class SimpleCurl {
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner = new Scanner(System.in);
    private String inputData = "";
    private long count = 0L;
    private boolean used = false;

    public static void main(String[] argc) {
        SimpleCurl scurl = new SimpleCurl();
        scurl.compareArgv(argc);
        scurl.start(argc[1],80);
    }

    public void compareArgv(String[] argc){
        for (String s : argc) {
            System.out.printf(s+" ");
            if(s.equals("scurl")) setUsed(true);
        }
        if(!isUsed()) throw new IllegalArgumentException();
    }

    public void run(String host, int port) {
        System.out.printf("%s %d", host, port);
    }

    //정렬된 ip 가 들어가야할 것같다. port 도 default 로 지정해주고 /
    public void start(String ip, int port) {
        try {
            String temp = "https://"+ip; // --> default 로 따로 설정해야겠다
            String line = "";
            URL url = new URL(temp);
            var inetAddress = InetAddress.getByName(url.getHost());
            System.out.println(inetAddress.getAddress().toString());
            var clientSocket = new Socket(inetAddress, port);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder builder = new StringBuilder();
            if(reader != null){
                int count;
                while((count = reader.read()) != - 1){
                    builder.append((char) count);
                }
            }
            System.out.println(builder.toString());

            do
            {
                line = reader.readLine();
                if (line == "") break;
                System.out.println(line);
                builder.append(line);
            }
            while (true);
            writer = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

}

//v    public static String crunchifyGETCallURLUtil(String crunchifyURL) {
//        crunchifyPrint("Requested URL:" + crunchifyURL);
//
//        // A mutable sequence of characters. This class provides an API compatible with StringBuffer,
//        // but with no guarantee of synchronization.
//        StringBuilder crunchifyStringBuilder = new StringBuilder();
//        URLConnection crunchifyURLConnection = null;
//        InputStreamReader in = null;
//        try {
//        URL url = new URL(crunchifyURL);
//        crunchifyURLConnection = url.openConnection();
//        if (crunchifyURLConnection != null)
//        // Set 5 second Read timeout
//        crunchifyURLConnection.setReadTimeout(5 * 1000);
//
//        if (crunchifyURLConnection != null && crunchifyURLConnection.getInputStream() != null) {
//        in = new InputStreamReader(crunchifyURLConnection.getInputStream(),
//        Charset.defaultCharset());
//        BufferedReader bufferedReader = new BufferedReader(in);
//
//        if (bufferedReader != null) {
//        int cp;
//        while ((cp = bufferedReader.read()) != -1) {
//        crunchifyStringBuilder.append((char) cp);
//        }
//        bufferedReader.close();
//        }
//        }
//        in.close();
//        } catch (Exception e) {
//        throw new RuntimeException("Exception while calling URL:" + crunchifyURL, e);
//        }
//
//        return crunchifyStringBuilder.toString();
//        }
//
//private static void crunchifyPrint(String print) {
//        System.out.println(print);
//        }

