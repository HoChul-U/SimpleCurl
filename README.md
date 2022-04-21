# SimpleCurl

1. Java Socket을 이용하세요.
2. curl 프로그램이 있습니다. 이 프로그램과 유사하게 동작하는 simple-curl 을 작성합니다.
3. scurl 은 다음과 같이 동작합니다.
4. URL 을 입력 인자로 받아 요청을 보내고 응답을 화면에 출력합니다.
5. 옵션으로 GET 외에 다른 method(HEAD, POST, PUT, DELETE) 로 요청할 수 있습니다.
6. POST, PUT 등의 메소드를 사용할 때엔 전송할 데이타를 지정할 수 있습니다.
7. 기본적으로는 요청헤더와 응답헤더를 출력하지 않습니다만, 옵션에 따라 출력할 수 있도록 합니다.
8. 응답의 ContentType 을 확인하여, "text/*", "application/json" 만 화면에 출력합니다.
9. POST, PUT 의 경우 -H 로 Content-Type 이 지정되지 않으면 application/x-www-form-urlencoded 을 기본 타입으로 사용합니다.

```
Usage: scurl [options] url
Options:
-v                 verbose, 요청, 응답 헤더를 출력합니다.
-H <line>          임의의 헤더를 서버로 전송합니다.
-d <data>          POST, PUT 등에 데이타를 전송합니다.
-X <command>       사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.
-L                 서버의 응딥이 30x 계열이면 다음 응답을 따라 갑니다.
-F <name=content>  multipart/form-data 를 구성하여 전송합니다. content 부분에 @filename 을 사용할 수 있습니다.
```

문제중 6번까지 해결 File 형식은 해결하지 못했다.
