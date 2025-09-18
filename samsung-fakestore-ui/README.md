# Samsung Fakestore UI 🔹

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8+-brightgreen)
![Spring](https://img.shields.io/badge/Spring-3.2-orange)
![PrimeFaces](https://img.shields.io/badge/PrimeFaces-5.1.0-purple)

Frontend da loja Samsung Fakestore, desenvolvido com **Spring Boot + PrimeFaces**.

**Desenvolvedor:** Gustavo Candido Pereira

---

## URL padrão

- [http://localhost:8082/index.xhtml](http://localhost:8082/index.xhtml)

---

## Funcionalidades
 
- Consulta de pedidos por cliente, número ou intervalo de datas  
- Visualização de detalhes de pedidos e produtos  

---

## Screenshots do Frontend

### Filtros de pesquisa
<img src="docs/screenshots/filtros.png" alt="Filtros de pesquisa" width="600">

### Lista de clientes
<img src="docs/screenshots/lista-clientes.png" alt="Lista de clientes" width="600">

### Filtro por cliente
<img src="docs/screenshots/busca-cliente.png" alt="Filtro por cliente" width="600">

### Filtro por pedido
<img src="docs/screenshots/busca-pedido.png" alt="Filtro por pedido" width="600">

### Filtro por período
<img src="docs/screenshots/busca-data.png" alt="Filtro por período" width="600">

---

## Rodar localmente

### Pela IDE
Execute a classe principal:

*com.samsung.fakestore.UiApplication*


### Pelo terminal com Maven
```
cd samsung-fakestore-ui
mvn spring-boot:run
```

### Pelo terminal via JAR (opcional)
```
mvn clean package
java -jar target/samsung-fakestore-ui-0.0.1-SNAPSHOT.jar
```


- O frontend roda direto pelo Spring Boot, não é necessário servidor externo.

---
 
### Configurações
Alterar porta no arquivo application.yml:

```
server:
  port: 8082
```

A porta padrão é 8082. Pode ser alterada conforme necessidade.

---

### Observações Técnicas
Consome endpoints do backend (http://localhost:8081/api)

Desenvolvido com Spring Boot + PrimeFaces

*Pode ser startado diretamente pela IDE ou pelo terminal*

*Backend e frontend podem rodar simultaneamente*
