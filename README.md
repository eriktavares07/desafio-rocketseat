# Desafio Rocketseat

Projeto para o desafio do curso de Java da Rocketseat.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.4
- Maven
- H2 Database
- PostgreSQL
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Springdoc OpenAPI

## Configuração do Projeto

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.6.0 ou superior

### Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/efjunior_meli/desafio.git
    cd desafio
    ```

2. Compile e instale as dependências:
    ```sh
    mvn clean install
    ```

3. Execute a aplicação:
    ```sh
    mvn spring-boot:run
    ```

## Endpoints

### Cursos

- **GET** `/cursos` - Lista todos os cursos, com filtros opcionais por nome e categoria.
- **GET** `/cursos/{id}` - Obtém um curso pelo ID.
- **PATCH** `/cursos/{id}` - Atualiza parcialmente um curso (somente para administradores).
- **PUT** `/cursos/{id}` - Atualiza um curso (somente para administradores).
- **DELETE** `/cursos/{id}` - Deleta um curso (somente para administradores).

### Autenticação

- **POST** `/auth/login` - Autentica um usuário e retorna um token JWT.

## Segurança

A aplicação utiliza JWT para autenticação e autorização. Endpoints sensíveis são protegidos e requerem o papel de administrador.

## Documentação da API

A documentação da API está disponível em `/swagger-ui.html` e `/v3/api-docs`.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`).
4. Faça um push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a licença MIT.teste
