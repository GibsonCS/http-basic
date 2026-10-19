# HTTP Basic Authentication with Spring Security

Projeto de estudo para demonstrar autenticação HTTP Basic e autorização baseada em roles usando Java e Spring Boot.

> **Objetivo:** praticar os fundamentos do Spring Security, incluindo autenticação de usuários persistidos, codificação de senhas e restrição de endpoints por perfil de acesso. HTTP Basic é utilizado aqui para fins didáticos; não representa, por si só, uma solução completa de autenticação para produção.

## Tecnologias

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- Maven
- JUnit e Spring Security Test (para testes)

## Funcionalidades

- Autenticação HTTP Basic usando e-mail como nome de usuário.
- Persistência de usuários e roles.
- Senhas codificadas com um `PasswordEncoder`.
- Autorização baseada em roles.
- Endpoints de exemplo para usuários comuns e administradores.
- Inicialização de dados de demonstração no perfil `dev`.

## Controle de acesso

A aplicação utiliza roles para diferenciar permissões. Os dados de desenvolvimento são preparados pela classe `DevDataInitialize`.

| Usuário de demonstração | Roles |
|---|---|
| `gibson@example.com` | `USER` |
| `admin@example.com` | `ADMIN`, `USER` |

A senha desses usuários é definida pela propriedade `app.security.password`. Configure uma senha local para desenvolvimento; não armazene senhas reais ou segredos no repositório.

Os endpoints de exemplo são:

| Endpoint | Finalidade |
|---|---|
| `/api/user` | Recurso protegido para usuários autenticados com permissão de usuário |
| `/api/admin` | Recurso protegido destinado a administradores |

> Os caminhos e permissões devem corresponder à configuração de segurança presente no código. Consulte as classes de configuração para confirmar as regras efetivamente aplicadas.

## Como executar

### Pré-requisitos

- JDK 17
- Maven, ou o Maven Wrapper incluído no projeto

### Configurar o ambiente de desenvolvimento

Ative o perfil `dev` e configure a propriedade `app.security.password` com uma senha de teste.

No PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE = "dev"
$env:APP_SECURITY_PASSWORD = "senha-local-de-teste"
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
export SPRING_PROFILES_ACTIVE=dev
export APP_SECURITY_PASSWORD='senha-local-de-teste'
./mvnw spring-boot:run
```

Se estiver usando Maven instalado, substitua `./mvnw` ou `.\mvnw.cmd` por `mvn`.

A propriedade `APP_SECURITY_PASSWORD` é a forma por variável de ambiente da propriedade Spring `app.security.password`. A rotina de dados de demonstração só é executada quando o perfil `dev` está ativo.


## Testar a autenticação

Com a aplicação em execução, uma requisição HTTP Basic pode ser feita com `curl`:

```bash
curl -i -u gibson@example.com:SUA_SENHA http://localhost:8080/api/user
```

Para testar o recurso de administração:

```bash
curl -i -u admin@example.com:SUA_SENHA http://localhost:8080/api/admin
```

Substitua `SUA_SENHA` pelo valor configurado em `APP_SECURITY_PASSWORD`. Os resultados dependem das regras definidas na configuração de segurança e dos endpoints existentes na aplicação.

obs: Há um arquivo "application_example.yaml" dentro de resources caso queira definir o perfil e as credenciais de forma manual pelo próprio arquivo, basta renomea-lo para application.yaml.

## Executar os testes

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS:

```bash
./mvnw test
```

Ou, com Maven instalado:

```bash
mvn test
```

## Inicialização de dados de desenvolvimento

A classe `DevDataInitialize` é responsável por popular o banco de dados com dados iniciais para
facilitar o desenvolvimento e os testes da aplicação.

Ela implementa a interface `CommandLineRunner`, permitindo que a inicialização seja executada após o
início do contexto da aplicação Spring.

### Perfil de execução

A classe é anotada com `@Profile("dev")`, portanto, só é ativada quando o perfil `dev` está
habilitado.

Essa restrição evita que os dados de demonstração sejam carregados automaticamente em outros
ambientes, como produção.

### Dados inicializados

A classe cria dois usuários para demonstrar o controle de acesso baseado em roles:

| E-mail               | Roles           |
|----------------------|-----------------|
| `gibson@example.com` | `USER`          |
| `admin@example.com`  | `ADMIN`, `USER` |

As senhas são obtidas por meio da propriedade `app.security.password` e codificadas com o
`PasswordEncoder` antes de serem persistidas.

Dessa forma, a senha não é armazenada diretamente em texto puro no banco de dados. O valor utilizado
nos testes deve ser configurado no ambiente local e não deve conter credenciais reais.

### Fluxo de execução

1. O Spring inicializa o contexto da aplicação com o perfil `dev` ativo.
2. A propriedade `app.security.password` é carregada.
3. São criados os objetos `User` e `Role` e persistidos no banco h2.

### Configuração

A aplicação precisa receber a propriedade `app.security.password` para executar essa inicialização.

Configure-a por variável de ambiente ou por um arquivo de configuração local não versionado. Não
inclua senhas reais no repositório.

### Observação

Esta classe é destinada exclusivamente ao desenvolvimento e à demonstração da autenticação. A carga
inicial deve ser idempotente; isto é, poder ser executada novamente sem criar registros duplicados
ou provocar erros de integridade. Essa garantia depende da implementação da rotina de inicialização
e das restrições de unicidade do banco de dados.

## Limitações e segurança

- HTTP Basic envia as credenciais em cada requisição, codificadas em Base64 — **Base64 não é criptografia**.
- Em aplicações reais, HTTP Basic deve ser protegido por HTTPS e avaliado conforme o contexto de uso.
- Os dados e senhas de demonstração destinam-se apenas ao ambiente local.
- A carga de dados de desenvolvimento não deve ser ativada em produção.
- Este projeto é didático e não pretende cobrir todos os requisitos de uma solução de autenticação pronta para produção.


Projeto de para prática pessoal.
