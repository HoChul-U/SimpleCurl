package com.nhnacademy.client;

import com.beust.jcommander.JCommander;
import com.nhnacademy.commander.Commander;
import com.nhnacademy.exception.RedirectException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;


public class SimpleCurl {

    private int count = 0; // redirection 메세지를 출력하기 위한 메세지.

    public static void main(String[] argv) {
        SimpleCurl main = new SimpleCurl();
        Commander scurl = new Commander();
        JCommander jCommander = new JCommander(scurl);
        jCommander.setCaseSensitiveOptions(false);
        jCommander.parse(argv);

        if (!scurl.getIp().equals("empty")) {
            main.start(scurl.getIp(), 80, scurl);
        } else {
            main.start(scurl.getLocation(), 80, scurl);
        }
    }

    public String stringUrl(URL url, Commander commander) {
        commander.getCustomHeaders().add("Content-Length: " + commander.getBody().length());
        String result = "";
        if (!commander.getIp().equals("empty")) {
            result =
                commander.getMethod() + " /" + commander.getMethod().toLowerCase() + " HTTP/1.1"
                    + "\n" + "Host: " + url.getHost()
                    + "\n" + String.join("\n", commander.getCustomHeaders())
                    + "\n"
                    + "\n" + commander.getBody();
        }
        else if(commander.getFile().isFile()){
            result =
                "Content-type: multipart/form-data;";
        }
         else {
            result = commander.getMethod() + " /" + url.getPath() + " HTTP/1.1"
                + "\n" + "Host: " + url.getHost()
                + "\n" + String.join("\n", commander.getCustomHeaders())
                + "\n"
                + "\n" + commander.getBody();
        }
        return result;
    }

    public void start(String ip, int port, Commander commander) {
        try{
            String line = "";
            URL url = new URL(ip);
            var inetAddress = InetAddress.getByName(url.getHost());
            var clientSocket = new Socket(inetAddress, port);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var writer = new PrintStream((clientSocket.getOutputStream()));

            //요청 라인
            writer.print(commander.stringUrl(url));
            writer.println();

            //출력
            while ((line = reader.readLine()) != null) {
                checkLocation(line, url);
                if (!commander.isVerbose()) {
                    if (!line.equals("{")) {
                        continue;
                    } else {
                        commander.setVerbose(true);
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

    private void checkLocation(String line, URL url) throws MalformedURLException {
        if (line.contains("location:") || line.contains("Location:")) {
            String redirect = line.split(" ")[1];
            URL op = new URL("https://" + url.getHost() + redirect);
            String[] check = {"-v", "-L", String.valueOf(op)};
            count++;
            if (count >= 5) {
                throw new RedirectException();
            }
            main(check);
        }
    }
}