# fassto-webflux
crud webflux로 구현해보기!
<br/></br>


### DataBase
우리가 흔히 사용하는 JPA는 WebFlux에서 보통 사용하지 않는다. R2DBC를 사용한다! (JDBC는 블로킹이고, R2DBC가 논블록이라서) 그래서 JPA 쓰려면 WebFlux 쓰지말라고 하더라구여..<br/>
(Webflux R2DBC vs JPA 효율성)https://technology.amis.nl/software-development/performance-and-tuning/spring-blocking-vs-non-blocking-r2dbc-vs-jdbc-and-webflux-vs-web-mvc/



