# study-webflux
crud webflux로 구현해보기!
<br/></br>

## 목차

1. [파일 구조](#파일-구조)
2. [DataBase](#DataBase)

### 파일 구조

```

		
```

## DataBase
우리가 흔히 사용하는 JPA는 WebFlux에서 보통 사용하지 않는다. R2DBC를 사용한다! (JDBC는 블로킹이고, R2DBC가 논블로킹) JPA와 R2DBC의 성능 차이가 커서 JPA는 권장하지 않는다고 한다.<br/>

Webflux R2DBC vs JPA <br/>  https://technology.amis.nl/software-development/performance-and-tuning/spring-blocking-vs-non-blocking-r2dbc-vs-jdbc-and-webflux-vs-web-mvc/



