# study-webflux
crud webflux로 구현해보기!
<br/></br>

## 목차

1. [파일 구조](#파일-구조)
2. [DataBase](#DataBase)

### 파일 구조

```

		
```

## web flux란 무엇일까
WebFlux란 Spring 5에 새롭게 추가된 Reactive-stack의 웹 프레임워크로 클라이언트와 서버에서 리액티브 애플리케이션 개발을 위한 논블로킹 리액티브 스트림을 지원한다.                    
WebFlux 논블로킹으로 동작하는 웹 스택의 필요성 때문에 등장하게 되었다. 기존 SpringMVC의 Servlet API는 v3.1 부터 논블로킹 I/O를 위한 API를 제공했었다. 하지만, 이외의 동기적으로 처리하는 모듈과 블로킹 방식의 API들이 완벽한 논블로킹 환경의 개발을 할 수 없었고, 비동기 논블로킹 환경의 서버인 Netty와의 연동을 위해 Spring은 새로운 API가 필요했다. (그래서 웹 플럭스가 두두등장..)    





## 주의 사항


## DataBase
우리가 흔히 사용하는 JPA는 WebFlux에서 보통 사용하지 않는다. R2DBC를 사용한다! (JDBC는 블로킹이고, R2DBC가 논블로킹) JPA와 R2DBC의 성능 차이가 커서 JPA는 권장하지 않는다고 한다.<br/>

Webflux R2DBC vs JPA <br/>  https://technology.amis.nl/software-development/performance-and-tuning/spring-blocking-vs-non-blocking-r2dbc-vs-jdbc-and-webflux-vs-web-mvc/



