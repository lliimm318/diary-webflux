# study-webflux
간단한 일기장 crud를 webflux로 구현해보자!
<br/></br>

## 목차

1. [파일 구조](#파일-구조)
2. [WebFLux란](#WebFlux란-무엇일까)
3. [MVC WebFlux로 전환](#MVC-WebFlux로-전환)
4. [주의 사항](#주의-사항)
5. [DataBase](#DataBase)
<br/></br>
### 파일 구조

```
📦
└─ src
   └─ main
      ├─ java
      │  └─ com
      │     └─ study
      │        └─ webflux
      │           ├─ WebfluxApplication.java
      │           ├─ controller
      │           ├─ domain
      │           │  └─ service
      │           ├─ entity
      │           ├─ exception
      │           ├─ payload
      │           │  ├─ request
      │           │  ├─ response
      │           └─  repository
      └─ resources
         └─ application.yml		
```

## WebFlux란 무엇일까
Reactive-stack의 웹 프레임워크로 클라이언트와 서버에서 리액티브 애플리케이션 개발을 위한 논블로킹 리액티브 스트림을 지원항한다              

#### 왜 web flux가 등장했을까?
 논블로킹으로 동작하는 웹 스택의 필요성 때문에 등장하게 되었다. 기존 SpringMVC의 Servlet API v3.1 부터 논블로킹 I/O를 위한 API를 제공했었다. 하지만, 이외의 동기적으로 처리하는 모듈과 블로킹 방식의 API들이 완벽한 논블로킹 환경의 개발을 할 수 없었고, 비동기 논블로킹 환경의 서버인 Netty와의 연동을 위해 Spring은 새로운 API가 필요했다. (그래서 웹 플럭스가 두두등장 해버렷다아..)    

#### MVC vs WebFlux      
MVC는 하나의 요청에 대해 하나의 스레드가 쓰인다. 그래서 다수의 요청을 대비하여 미리 스레드 풀을 생성해놓으며, 각 요청마다 스레드를 할당하여 처리한다. 
이 방식은 아래와 같은 문제가 생긴다.
- **블로킹 콜**          
동작중인 스레드가 블로킹 상태가 되면 다른 스레드에서 CPU 사용을 위해 컨텍스트 스위칭이 일어나게 된다. 만약 서버의 대부분이 이렇게 구성되어 있다면, 많은 컨텍스트 스위칭 일어나게 되고, 결국 내부 블로킹 콜이 차지하는 비중만큼 컨텍스트 스위칭의 오버헤드가 발생한다
- **많은 요청량**         
요청량이 증가한다면 스레드 수도 이에 비례하여 증가한다. 스레드 수가 증가한다면 서버가 감당해내지 못할 만큼의 메모리를 먹을 수 있다.
<br/></br>
**웹플럭스는 MVC의 이러한 단점을 해결한다!**

리액티브 프로그래밍은 논블로킹과 고정된 스레드 수 만으로 모든 요청을 처리함으로 위의 문제들을 해결한다.<br/>       
서버는 스레드 한 개로 운영하며, 디폴트로 CPU 코어 수 개수의 스레드를 가진 워커 풀을 생성하여 해당 워커 풀 내의 스레드로 모든 요청을 처리한다. 제약이 있다면 논블로킹으로 동작하여야만 하며, 블로킹 라이브러리가 필수적으로 사용되어야 한다면, 워커 스레드가 아닌 외부 별도의 스레드로 요청을 처리해야한다
<br/></br>

## MVC WebFlux로 전환
#### 전환시 생각할점
- **내부 작업의 유형**
스프링 가이드에서는 내부 하드한 연산들이 로직이 많다면 MVC보다 성능적인 향상을 내지 못한다고 한다. 전환 한다면 내부 로직들이 어떠한 연산으로 이루어져 있는지 생각할 필요가 있다. 내부 연산보다는 블로킹 콜,  외부 API 호출, db 조회 등과 같은 작업들로 이루어져 있다면 WebFlux로의 전환이 성능적인 개선을 가져올 수 있을 것이다!

- **리액티브 라이브러리 유무**
사용하는 라이브러리들이 블로킹이라면 논블로킹으로 전환하기 어려울 수 있다. 워커풀이 아닌 별도의 외부 스레드풀을 생성하여 논블로킹으로 처리할 수 있겠지만, 스프링 가이드는 최대한 이러한 방법도 별로라고 한다...

#### 코드
- **리턴 타입 변경**                         
Reactor는 리액티브 스트림을 지원하는 Mono와 Flux 사용을 위해 리턴 타입도 Mono, Flux로 전환되어야 한다. 직접 데이터를 생성한다면 just() 메서드로 감싸서 전달 한다.
- **flatMap**           
내부엔 블로킹이 존재하지 않아서 작업들은 비동기적으로 처리된다. 라인 순서에 따른 실제 코드의 동작 순서는 일치하지 않게된다. flatMap을 통해 이전 비동기 작업이 끝난 후 다음 로직들이 처리되도록 순서를 보장시켜줄 수 있다. flatMap에 전달하는 함수의 리턴 타입은 Mono나 Flux와 같은 리액티브 API이다.
- **예외 처리**           
WebFlux에서 리턴 타입은 Mono나 Flux로 구성된다. 예외를 던져야할 때 throw 대신 Mono.error API를 사용한다
<br/></br>

## 주의 사항
**Mono와 Flux에서 block() 호출 금지.**     
Mono의 block() 메서드는 현재 동작중인 쓰레드를 차단합니다. 따라서 이후 프로그램이 더 이상 반응하지 않는다..
Flux는 blockFirst(), blockFirst(Duration timeout) 및 blockLast() , blockLast(Duration timeout)을 사용하지 않는게 좋다
<br/></br>

## DataBase
우리가 흔히 사용하는 JPA는 WebFlux에서 보통 사용하지 않는다. R2DBC를 사용한다! (JDBC는 블로킹이고, R2DBC가 논블로킹) JPA와 R2DBC의 성능 차이가 커서 JPA는 권장하지 않는다고 한다.<br/>

Webflux R2DBC vs JPA <br/>  https://technology.amis.nl/software-development/performance-and-tuning/spring-blocking-vs-non-blocking-r2dbc-vs-jdbc-and-webflux-vs-web-mvc/



