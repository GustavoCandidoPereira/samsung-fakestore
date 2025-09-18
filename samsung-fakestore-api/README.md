# Samsung Fakestore API 🔹

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8+-brightgreen)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.1.6-orange)

Backend da loja Samsung Fakestore, desenvolvido em **Spring Boot 3.1.6**.

**Desenvolvedor:** Gustavo Candido Pereira

---

### Endpoints Disponíveis

- **Listar clientes**  `GET /api/clientes`  

- **Listar pedidos por cliente** `GET /api/pedidos?clienteId=1`  

- **Buscar pedido pelo número do pedido** `GET /api/pedidos?orderNumber=2`  

- **Buscar pedidos por intervalo de datas** `GET /api/pedidos?dateFrom=2020-01-01&dateTo=2020-03-31`  


*Obs: /api/pedidos exige pelo menos um filtro: clienteId OU orderNumber OU dateFrom + dateTo.*

*Se nenhum filtro for informado, retorna 400 Bad Request.*

---

### Rodar localmente
### Pela IDE

Execute a classe principal:

*com.samsung.fakestore.Application*

### Pelo terminal com Maven
```
cd samsung-fakestore-api

mvn spring-boot:run
```

### Pelo terminal via JAR (opcional)
```
mvn clean package

java -jar target/samsung-fakestore-api-0.0.1-SNAPSHOT.jar
```

---

### Configurações

Alterar porta no arquivo application.yml:

```
server:
  port: 8081
```


A porta padrão é 8081. Pode ser alterada conforme necessidade.
(http://localhost:8081/api)

---

### Observações Técnicas

Consome dados do FakeStoreAPI  (https://fakestoreapi.com/)

Faz parse das datas enviadas como query params (yyyy-MM-dd), pois a API externa retorna datas no formato ISO com horário

*Pode ser startado diretamente pela IDE ou terminal*