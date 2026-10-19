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