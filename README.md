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

## Como funciona a inicialização dos dados

A classe `DevDataInitialize` implementa `CommandLineRunner` e é marcada com `@Profile("dev")`. Quando o perfil `dev` está ativo, ela cria usuários e roles de demonstração, codifica as senhas com o `PasswordEncoder` e persiste os dados usando os repositórios JPA.

A rotina atual é voltada ao desenvolvimento. Antes de executá-la repetidamente, é necessário garantir que a inicialização seja idempotente para evitar registros duplicados ou conflitos de integridade.

## Limitações e segurança

- HTTP Basic envia as credenciais em cada requisição, codificadas em Base64 — **Base64 não é criptografia**.
- Em aplicações reais, HTTP Basic deve ser protegido por HTTPS e avaliado conforme o contexto de uso.
- Os dados e senhas de demonstração destinam-se apenas ao ambiente local.
- A carga de dados de desenvolvimento não deve ser ativada em produção.
- Este projeto é didático e não pretende cobrir todos os requisitos de uma solução de autenticação pronta para produção.

## Próximos passos

- [ ] Adicionar testes de integração para credenciais válidas e inválidas.
- [ ] Testar a autorização de `USER` e `ADMIN` em cada endpoint protegido.
- [ ] Tornar a inicialização de dados segura para execuções repetidas.
- [ ] Revisar logs para garantir que dados sensíveis não sejam expostos.
- [ ] Ampliar a documentação conforme novas funcionalidades forem adicionadas.

---

Projeto de estudo pessoal para praticar Java, Spring Boot e os fundamentos do Spring Security.
