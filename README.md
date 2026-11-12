# Sistema de Gestão Agrícola

API REST para gerenciamento de propriedades rurais, talhões, culturas agrícolas e plantios, desenvolvida com Java 21, Spring Boot e MySQL.

O projeto foi criado com finalidade educacional e de portfólio, aplicando conceitos de programação orientada a objetos, arquitetura em camadas, persistência de dados e implementação de regras de negócio.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Git e GitHub

## Funcionalidades implementadas

### Propriedades rurais

- Cadastro, listagem, consulta, atualização e exclusão.
- Validação de nome, município e área total.
- Impedimento de redução da área total abaixo da soma das áreas dos talhões.
- Proteção contra exclusão de propriedades com talhões vinculados.

### Talhões

- Cadastro e gerenciamento de talhões vinculados a propriedades rurais.
- Validação de nome, área e propriedade.
- Controle da soma das áreas dos talhões em relação à área da propriedade.
- Impedimento de redução da área abaixo da área de plantios vinculados.
- Proteção contra exclusão de talhões com plantios.

### Culturas agrícolas

- Cadastro, listagem, consulta, atualização e exclusão.
- Validação de nome obrigatório e único.
- Proteção contra exclusão de culturas utilizadas em plantios.

### Plantios

- Cadastro e gerenciamento de plantios vinculados a talhões e culturas.
- Registro de área plantada, data prevista, data realizada e status.
- Status disponíveis: `PLANEJADO`, `REALIZADO` e `CANCELADO`.
- Validação da área plantada em relação à área do talhão.
- Validação de consistência entre status e data realizada.
- Impedimento de dois plantios planejados simultaneamente no mesmo talhão.

## Arquitetura

O backend utiliza uma arquitetura organizada em camadas:

- **Controller:** recebe requisições HTTP e retorna respostas da API.
- **Service:** implementa as regras de negócio e validações.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Entity:** representa as entidades persistidas no banco de dados.

## Endpoints REST

| Método | Endpoint             | Descrição             |
| ------ | -------------------- | --------------------- |
| GET    | `/propriedades`      | Listar propriedades   |
| GET    | `/propriedades/{id}` | Consultar propriedade |
| POST   | `/propriedades`      | Cadastrar propriedade |
| PUT    | `/propriedades/{id}` | Atualizar propriedade |
| DELETE | `/propriedades/{id}` | Excluir propriedade   |
| GET    | `/talhoes`           | Listar talhões        |
| GET    | `/talhoes/{id}`      | Consultar talhão      |
| POST   | `/talhoes`           | Cadastrar talhão      |
| PUT    | `/talhoes/{id}`      | Atualizar talhão      |
| DELETE | `/talhoes/{id}`      | Excluir talhão        |
| GET    | `/culturas`          | Listar culturas       |
| GET    | `/culturas/{id}`     | Consultar cultura     |
| POST   | `/culturas`          | Cadastrar cultura     |
| PUT    | `/culturas/{id}`     | Atualizar cultura     |
| DELETE | `/culturas/{id}`     | Excluir cultura       |
| GET    | `/plantios`          | Listar plantios       |
| GET    | `/plantios/{id}`     | Consultar plantio     |
| POST   | `/plantios`          | Cadastrar plantio     |
| PUT    | `/plantios/{id}`     | Atualizar plantio     |
| DELETE | `/plantios/{id}`     | Excluir plantio       |

## Como executar

### Pré-requisitos

- JDK 21
- MySQL Server
- Git
- Maven Wrapper incluído no projeto

### Configuração do banco

Crie um banco de dados MySQL:

```sql
CREATE DATABASE IF NOT EXISTS gestao_agricola;
```

Configure o usuário e suas permissões para acessar esse banco.

O arquivo `application.properties` utiliza:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestao_agricola
spring.datasource.username=gestao_app
spring.datasource.password=${DB_PASSWORD}
```

Ajuste o usuário e a URL conforme seu ambiente.

### Variável de ambiente

No PowerShell:

```powershell
$env:DB_PASSWORD = Read-Host "Digite a senha do banco"
```

### Inicialização

Na pasta `backend`, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em:

`http://localhost:8080`

Exemplo de consulta:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/propriedades" -Method GET
```

## Validação e testes

Os endpoints foram exercitados manualmente por meio de requisições HTTP no PowerShell.

Foram verificados cenários de cadastro, consulta, atualização, exclusão, validações de dados e proteção de relacionamentos.

A suíte de testes automatizados ainda não foi concluída.

## Estado do projeto

**Backend funcional — versão inicial em revisão final.**

As principais funcionalidades previstas para o escopo inicial estão implementadas. O projeto permanece aberto a melhorias de testes, documentação e manutenção.

## Autor

Desenvolvido por [M0US3P4D777](https://github.com/M0US3P4D777).
