package com.nhnacademy.commander;

import com.beust.jcommander.Parameter;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Commander {
    @Parameter(names = {"-L", "Location"},
        description = "서버의 응답이 30x 계열이면 다음 응답을 따라갑니다.")
    private String location;

    @Parameter(names = {"-H", "Header"},
        description = "임의의 헤더를 서버로 전송합니다.")

    private List<String> customHeaders = new ArrayList<>();
    //필수 조건

    @Parameter(description = "기본적으로 url 을입력하셔야합니다.")
    private String ip = "empty";

    @Parameter(names = {"-X", "Method"},
        description = "사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.")

    private String method = "GET";

    @Parameter(names = "-d",
        description = "POST, PUT 등에 데이타를 전송합니다.")

    private String data = "";

    @Parameter(names = {"-v", "verbose"},
        description = "verbose, 요청, 응답 헤더를 출력합니다.")

    private boolean verbose = false;

    @Parameter(names = {"-f", "File"},
        description = "multipart/form-data 를 구성하여 전송합니다. content 부분에 @filename 을 사용할 수 있습니다.")

    private File file;

    public String stringUrl(URL url) {
        customHeaders.add("Content-Length: " + getBody().length());
        String result = "";
        if (!getIp().equals("empty")) {
            result = getMethod() + " /" + getMethod().toLowerCase() + " HTTP/1.1"
                + "\n" + "Host: " + url.getHost()
                + "\n" + String.join("\n", customHeaders)
                + "\n"
                + "\n" + getBody();
        } else {
            result = getMethod() + " /" + url.getPath() + " HTTP/1.1"
                + "\n" + "Host: " + url.getHost()
                + "\n" + String.join("\n", customHeaders)
                + "\n"
                + "\n" + getBody();
        }
        return result;
    }


    public String getMethod() {
        return method;
    }

    public List<String> getCustomHeaders() {
        return customHeaders;
    }

    public File getFile() {
        return file;
    }

    public String getBody() {
        return data;
    }

    public String getIp() {
        return ip;
    }

    public String getLocation() {
        return location;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }
}
