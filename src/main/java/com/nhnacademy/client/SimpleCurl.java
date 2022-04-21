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
    @Parameter(names = {"-L","Location"},
            description = "서버의 응답이 30x 계열이면 다음 응답을 따라갑니다.")
    private String location;

    //필수 조건
    @Parameter(description = "기본적으로 url 을입력하셔야합니다.")
    private String ip ="empty";

    @Parameter(names = {"-X", "Method"},
            description = "사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.")

    private String method = "GET";

    @Parameter(names = "-d",
            description = "POST, PUT 등에 데이타를 전송합니다.")

    private String data = "";

    @Parameter(names = {"-v", "verbose"},
            description = "verbose, 요청, 응답 헤더를 출력합니다.")

    private boolean verbose = false;

    @Parameter(names = {"-H", "Header"},
            description = "임의의 헤더를 서버로 전송합니다.")

    private List<String> customHeaders = new ArrayList<>();

    private int count = 0; // redirection 메세지를 출력하기 위한 메세지.

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

    public String getLocation() {
        return location;
    }

    public static void main(String[] argv) {
        SimpleCurl scurl = new SimpleCurl();
        JCommander jCommander = new JCommander(scurl);
        jCommander.setCaseSensitiveOptions(false);
        jCommander.parse(argv);
        if(!scurl.getIp().equals("empty")) scurl.start(scurl.getIp(), 80, argv);
        else scurl.start(scurl.getLocation(),80,argv);
    }

    public String stringUrl(URL url, String[] argc) {
        customHeaders.add("Content-Length: " + getBody().length());
        String result = "";
        if(!getIp().equals("empty")) {
            result = getMethod() + " /" + getMethod().toLowerCase() + " HTTP/1.1"
                    + "\n" + "Host: " + url.getHost()
                    + "\n" + String.join("\n", customHeaders)
                    + "\n"
                    + "\n" + getBody();
        }
        else {
            result = getMethod() + " /" + "status" + " HTTP/1.1"
                    + "\n" + "Host: " + url.getHost()
                    + "\n" + String.join("\n", customHeaders)
                    + "\n"
                    + "\n" + getBody();
        }
        return result;
    }

    public void run(String host, int port) {
        System.out.printf("%s %d", host, port);
    }

    //정렬된 ip 가 들어가야할 것같다. port 도 default 로 지정해주고 /
    public void start(String ip, int port, String[] argv) {
        try {
            String line = "";
            URL url = new URL(ip);
            var inetAddress = InetAddress.getByName(url.getHost());
            var clientSocket = new Socket(inetAddress, port);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var writer = new PrintStream((clientSocket.getOutputStream()));

            //요청 라인
            writer.print(stringUrl(url, argv));
            writer.println();

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