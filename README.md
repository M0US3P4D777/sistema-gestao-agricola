# Sistema de Gestão Agrícola

Sistema de gestão agrícola desenvolvido como projeto de estudo e portfólio, com foco na aplicação de conceitos de desenvolvimento de software, programação orientada a objetos, APIs REST e persistência de dados.

## Objetivo

O projeto tem como objetivo desenvolver uma aplicação para auxiliar no gerenciamento de informações relacionadas às atividades agrícolas.

A aplicação será construída de forma incremental, permitindo a implementação e validação das funcionalidades em diferentes etapas.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Git e GitHub

## Estado atual

**Em desenvolvimento — configuração inicial do backend.**

A estrutura inicial do projeto foi criada, incluindo:

- Configuração do Spring Boot.
- Configuração da conexão com o MySQL.
- Criação de usuário específico para acesso ao banco de dados.
- Configuração inicial do JPA e Hibernate.
- Inicialização do versionamento com Git.

As funcionalidades de negócio e os endpoints REST ainda serão implementados.

## Funcionalidades planejadas

- Cadastro e gerenciamento de propriedades agrícolas.
- Cadastro de culturas agrícolas.
- Gerenciamento de safras.
- Registro de atividades agrícolas.
- Consultas e relatórios de informações cadastradas.

As funcionalidades poderão ser ajustadas durante a evolução do projeto.

## Configuração do ambiente

### Pré-requisitos

- JDK 21
- MySQL Server
- Git
- Maven Wrapper incluído no projeto

### Banco de dados

O ambiente de desenvolvimento utiliza um banco MySQL denominado `gestao_agricola`.

A aplicação utiliza o usuário `gestao_app`, com permissões específicas sobre o banco.

As credenciais não são armazenadas diretamente no código-fonte.

### Variável de ambiente

A senha do banco de dados é fornecida por meio da variável `DB_PASSWORD`.

No PowerShell, a variável pode ser definida para a sessão atual com:

```powershell
$env:DB_PASSWORD = Read-Host "Digite a senha do banco"
```

### Execução do backend

Após configurar o banco e a variável de ambiente, a aplicação pode ser iniciada no Windows com:

```powershell
.\mvnw.cmd spring-boot:run
```

Por padrão, o servidor HTTP utiliza a porta `8080`.

**Observação:** a aplicação ainda não possui endpoints de negócio. Portanto, uma resposta HTTP 404 na rota principal é esperada nesta etapa.

## Estrutura inicial

```text
backend/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Próximas etapas

1. Definição das entidades do domínio agrícola.
2. Implementação da camada de persistência.
3. Implementação das regras de negócio.
4. Desenvolvimento dos endpoints REST.
5. Criação de testes automatizados.
6. Integração futura com uma interface frontend.

## Finalidade

O projeto possui finalidade educacional e busca demonstrar a aplicação prática de tecnologias utilizadas no desenvolvimento de sistemas corporativos.

## Autor

Desenvolvido por [M0US3P4D777](https://github.com/M0US3P4D777).
