# Task REST API
![Java](https://img.shields.io/badge/Java-orange?style=flat&logo=openjdk)
![SpringBoot](https://img.shields.io/badge/Spring%20Boot%203.5-6DB33F?style=flat&logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/-Hibernate-59666C?logo=Hibernate&logoColor=FFF)
![SpringDataJPA](https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=flat&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/MAVEN-000000?style=flat&logo=apachemaven&logoColor=blue)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-85EA2D?style=flat&logo=swagger&logoColor=white)
<br>
API REST simples para gerenciamento de tarefas, desenvolvida na disciplina de Desenvolvimento Web Back-End

## 📌 Status
✔️ Finalizado

## 🛠️ Stack
- Java 17
- Spring Boot 3.5 (Spring Web, Spring Data JPA/Hibernate)
- MySQL
- Maven Wrapper

## ✅ Pré-requisitos
- Java 17
- MySQL em execução (porta 3306)
- Internet na primeira execução (download das dependências)

## ⚙️ Instalação e Configuração
1. Baixe o repositório: `git clone https://github.com/LucasNasc3000/trabalho-web-backend-rest-api`
2. Crie o banco de dados: `CREATE DATABASE db_rest;`
3. Vá em application.properties: `trabalho-web-backend-rest-api/src/main/resources/application.properties` e adicione usuário e senha do banco de dados nas seguintes linhas

```
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:}
```

4. Execute no terminal: `./mvnw spring-boot:run` (linux) `mvnw.cmd spring-boot:run` (windows) para iniciar o servidor em http://localhost:8080
5. Para acessar o swagger entre em: `http://localhost:8080/swagger-ui/index.html#/`

## 📚 Endpoints
| Método | Rota | Descrição | Respostas |
|---|---|---|---|
| GET | `/task` | Lista todas as tarefas | 200 |
| GET | `/task/{id}` | Busca uma tarefa | 200, 404 |
| POST | `/task` | Cria uma tarefa | 201, 400 |
| PATCH | `/task/{id}` | Atualiza apenas os campos enviados | 200, 400, 404 |
| DELETE | `/task/{id}` | Remove uma tarefa | 204, 404 |

Exemplo de corpo do `POST` (todos os campos obrigatórios, data em `dd/MM/yyyy`):
```json
{
  "name": "Lavar o carro",
  "assignmentDueDate": "30/01/2027",
  "responsible": "Mônica Menezes"
}
```
## 🧯 Problemas comuns
- **`BUILD SUCCESS` e o terminal volta ao prompt:** a API não subiu. Verifique se o MySQL está em execução e busque o erro nos logs caso a causa seja outra.
- **`Port 8080 was already in use`:** outro programa está usando a porta (por exemplo, o Tomcat do XAMPP). Feche-o ou defina `server.port=8081`.
- **`Permission denied` no `./mvnw`:** rode `chmod +x mvnw`.
