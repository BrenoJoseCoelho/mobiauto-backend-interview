# Projeto Mobiauto Backend

**Observação**: Este projeto foi desenvolvido em um prazo curto, concluído em apenas dois dias. Então pode ser encontrado inconsistências

## Introdução

O projeto Mobiauto Backend é uma aplicação desenvolvida para gerenciar oportunidades de vendas entre revendedores de veículos. Ele oferece funcionalidades para gerenciar usuários, revendas e oportunidades, integrando-se com o Spring Security para garantir segurança e controle de acesso.

## Visão Geral

### Pré-requisitos

- Java 11
- Spring Boot 2.7.0
- Maven 3.8.x
- H2 Database
- Hibernate
- Spring Data JPA
- Spring Security

### Arquitetura

A aplicação é desenvolvida com base na arquitetura Spring Boot, utilizando o padrão MVC (Model-View-Controller). Os principais componentes incluem:
- **Controllers**: Responsáveis por receber e responder às requisições HTTP.
- **Services**: Lógica de negócio e integração com o banco de dados.
- **Repositories**: Comunicação com o banco de dados para operações CRUD.
- **Security Config**: Configurações de segurança usando Spring Security para autenticação e autorização.

## Configuração e Variáveis de Ambiente

### Execução Local

Para executar a aplicação localmente sem Docker, siga os passos abaixo:

1. **Clonar o Repositório:**

   ```bash
   git clone https://github.com/BrenoJoseCoelho/mobiauto-backend-interview.git
   cd src

2. **Compilar e Executar com Maven:**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ou buildar o projeto e rodalo
   Acesso ao Swagger:

3. **Acesso ao Swagger:**
   Após iniciar a aplicação, você pode acessar a interface Swagger para explorar e testar os endpoints:

   [Swagger UI](http://localhost:8080/swagger-ui/index.html)

   **Credenciais do Usuário Admin:**
   - **Usuário:** admin
   - **Senha:** adminPassword


4. **Testes:**   
    ### Tecnologias Utilizadas para Testes

   - JUnit 5
   - Mockito
   - Spring Boot Test

   ### Executar Testes com Maven:
    ```bash
    Para executar os testes unitários e de integração com Maven, utilize o seguinte comando:
    - mvn test 

5. **Variáveis de Ambiente:**
    ```bash
    A aplicação não requer variáveis de ambiente específicas para execução em ambiente de desenvolvimento.

6. **Suporte e Contato:**   
    ```bash
    Email: breno.jose.coelho@gmail.com
    Linkedin: https://www.linkedin.com/in/breno-jose-coelho/
