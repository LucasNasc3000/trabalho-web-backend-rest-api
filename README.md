# Task REST API
![Java](https://img.shields.io/badge/Java-orange?style=flat&logo=openjdk)
![Java17](https://img.shields.io/badge/Java%20version-17-yellow?logo=java)
![SpringBoot](https://img.shields.io/badge/Spring%20Boot%203.5-6DB33F?style=flat&logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/-Hibernate-59666C?logo=Hibernate&logoColor=FFF)
![SpringDataJPA](https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=flat&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/MAVEN-000000?style=flat&logo=apachemaven&logoColor=blue)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=flat&logo=swagger&logoColor=white)
<br>
API REST simples para gerenciamento de tarefas, desenvolvida na disciplina de Desenvolvimento Web Back-End

## 📌 Status
✔️ Finalizado

## 🛠️ Stack
- Java 17
- Spring Boot 3.5 (Spring Web, Spring Data JPA/Hibernate)
- MySQL
- Maven Wrapper

## ⚙️ Instalação e Configuração
1. Baixe o repositório: `git clone https://github.com/LucasNasc3000/trabalho-web-backend-rest-api`
2. Crie o banco de dados: `CREATE DATABASE db_rest;`
3. Vá em application.properties: `trabalho-web-backend-rest-api/src/main/resources/application.properties` e adicione usuário e senha do banco de dados nas seguintes linhas

```
// ...
spring.datasource.username=<seu_usuario>
spring.datasource.password=<sua_senha_se_nao_houver_deixe_vazio>
// ...
```

4. Execute no terminal: `./mvnw spring-boot:run` (linux) `mvnw.cmd spring-boot:run` (windows) para iniciar o servidor em http://localhost:8080
5. Para acessar o swagger

## 📚 Endpoints
`GET /task · GET /task/{id} · POST /task · PATCH /task/{id} · DELETE /task{id}`
