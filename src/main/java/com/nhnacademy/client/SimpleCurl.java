package com.nhnacademy.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class SimpleCurl {
    private Scanner scanner = new Scanner(System.in);
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
            String line = "";
            URL url = new URL(ip);
            URL obj = new URL("https://crunchify.com");
            var inetAddress = InetAddress.getByName(url.getHost());
            var clientSocket = new Socket(inetAddress, port);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            StringBuilder builder = new StringBuilder();
            while ( (line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
            var writer = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void print(URL obj) throws IOException {
//        URLConnection conn = obj.openConnection();
//        Map<String, List<String>> map = conn.getHeaderFields();
//
//        System.out.println("Printing All Response Header for URL: " + obj.toString() + "\n");
//        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        System.out.println("\nGet Response Header By Key ...\n");
//        List<String> contentLength = map.get("Content-Length");
//        if (contentLength == null) {
//            System.out.println("'Content-Length' doesn't present in Header!");
//        } else {
//            for (String header : contentLength) {
//                System.out.println("Content-Lenght: " + header);
//            }
//        }
//    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }
}


