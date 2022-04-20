package com.nhnacademy.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SimpleCurl {
    //필수 조건
    @Parameter(required = true)
    private String ip;

    @Parameter(names = {"-X", "METHOD"})
    private String method = "GET";

    @Parameter(names = "-d")
    private String data = "";

    @Parameter(names = {"-v", "verbose"})
    private boolean verbose = false;

    @Parameter(names = {"-H", "Header"})
    private List<String> customHeaders = new ArrayList<>();

    public String getMethod() {
        return method;
    }

    private boolean used = false;

    public String getBody() {
        return data;
    }

    public String getIp() {
        return ip;
    }

    public static void main(String[] argv) {
        SimpleCurl scurl = new SimpleCurl();
        JCommander jCommander = new JCommander(scurl);
        jCommander.setCaseSensitiveOptions(false);
        jCommander.parse(argv);

        scurl.start(scurl.getIp(), 80, argv);
    }

    public String stringUrl(URL url, String[] argc) {
        customHeaders.add("Content-Length: " + getBody().length());
        return getMethod() + " /" + getMethod().toLowerCase() + " HTTP/1.1"
            + "\n" + "Host: " + url.getHost()
            + "\n" + String.join("\n", customHeaders)
            + "\n"
            + "\n" + getBody();
    }

    public void run(String host, int port) {
        System.out.printf("%s %d", host, port);
    }

    //정렬된 ip 가 들어가야할 것같다. port 도 default 로 지정해주고 /
    public void start(String ip, int port, String[] argc) {
        try {
            String line = "";
            URL url = new URL(ip);
            var inetAddress = InetAddress.getByName(url.getHost());
            var clientSocket = new Socket(inetAddress, port);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var writer = new PrintStream((clientSocket.getOutputStream()));

            //요청 라인
            writer.print(stringUrl(url, argc));
            //헤더 정보

            //출력
            while ((line = reader.readLine()) != null) {
                if (!verbose) {
                    if (!line.equals("{")) {
                        continue;
                    } else {
                        verbose = true;
                    }
                }
                System.out.println(line);
            }
            reader.close();
            writer.close();
            clientSocket.close();
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