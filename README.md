# Hackaton_sub_FIAP

### Gerando Jar:
Usando Maven:
$ mvn clean install

Usando Jenkins:
-> procure a aba do mavem, depois procure por Lifecycle e selecione clean, clique com botão direito e selecione "Run Maven Build". Posteriormente, selecione package e clique com botão direito e selecione "Run Maven Build".

### Rodando a aplicação:
-> Para rodar a aplicação localmente, basta gerar o jar, ter o docker instalado e rodar o comando "docker-compose up --build

### **Observação.: Não consegui identificar por qual motivo, mas o disparo de email não esta ocorrendo executando na AWS Lambda, porem funciona perfeitamente rodando localhost.**

### **Descrição:**

Sistema de autenticação Token JWT (login, cadastro, alteração de senha, ativação de conta)
Criptografia de senha. 
Notificações via email utilizando API SendGrid. (ativação de conta, proposta de troca etc)

**Online Swagger UI**:

[**https://whoisagade.s3.us-east-1.amazonaws.com/index.html**](https://whoisagade.s3.us-east-1.amazonaws.com/index.html)

**Localhost Swagger UI:**

**→ http://localhost:8089/api/v1/swagger-ui/index.html**

### **Relatório Técnico:**

- Java 17
- Spring Boot 3
- Spring Security
- PostgreSQL 15
- Docker
- AWS Lambda
- AWS API Gateway

Um projeto desafiador, principalmente na parte que envolve AWS Lambda e API Gateway. Adquiri um conhecimento enorme sobre esses tecnologias.

Para executar o projeto localmente acesse a documentação no **GitHub**.

[**https://github.com/HernandesHD/Hackaton_sub_FIAP/blob/main/README.md#relatório-técnico**](https://github.com/HernandesHD/Hackaton_sub_FIAP/blob/main/README.md#relat%C3%B3rio-t%C3%A9cnico)

### **End-point da aplicação online:**

Aplicação esta executando na AWS utilizando AWS Lambda, API Gateway e banco de dados PostgreSQL. 

[**https://70no0ww6kd.execute-api.us-east-1.amazonaws.com/Prod**](https://70no0ww6kd.execute-api.us-east-1.amazonaws.com/Prod)

**Lista dos endpoints:**

- **Users:**
    - **GET → /users/{id}**
- **Authentication:**
    - **POST → /auth/reset-password**
    - **POST → /auth/register**
    - **POST → /auth/login**
    - **POST → /auth/forgot-password**
    - **GET → /auth/activate-account**
- **Exchanges:**
    - **POST → /exchanges/propose**
    - **PATCH → /exchanges/reject/{id}**
    - **PATCH → /exchanges/accept/{id}**
- **Games:**
    - **GET → /games/{id}**
    - **PUT → /games/{id}**
    - **DELETE → /games/{id}**
    - **POST → /games/add**
