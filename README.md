# AURASM API - Sistema de Gestão de Consultas Médicas

API RESTful desenvolvida com **Quarkus** para gerenciamento de consultas médicas, pacientes, médicos e usuários.

## 🚀 Tecnologias

- **Java 17**
- **Quarkus 3.2.9.Final**
- **Oracle Database** (JDBC)
- **JAX-RS** (RESTEasy Reactive)
- **Jackson** (JSON)
- **REST Client** (integração ViaCEP)
- **Maven**

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.8+
- Acesso ao banco Oracle (oracle.fiap.com.br)
- Credenciais: rm562027 / 230407

## 🏗️ Estrutura do Projeto

```
aurasm-api/
├── src/main/java/br/com/aurasm/
│   ├── config/
│   │   └── DatabaseConfig.java
│   ├── controller/
│   │   ├── AcompanhanteController.java
│   │   ├── CepController.java
│   │   ├── ConsultaController.java
│   │   ├── HistoricoConsultaController.java
│   │   ├── MedicoController.java
│   │   ├── PacienteController.java
│   │   ├── PessoaController.java
│   │   ├── TelefoneController.java
│   │   └── UsuarioController.java
│   ├── dao/
│   │   ├── AcompanhanteDAO.java
│   │   ├── ConsultaDAO.java
│   │   ├── HistoricoConsultaDAO.java
│   │   ├── MedicoDAO.java
│   │   ├── PacienteDAO.java
│   │   ├── PessoaDAO.java
│   │   ├── TelefoneDAO.java
│   │   └── UsuarioDAO.java
│   ├── model/
│   │   ├── Acompanhante.java
│   │   ├── Consulta.java
│   │   ├── HistoricoConsulta.java
│   │   ├── Medico.java
│   │   ├── Paciente.java
│   │   ├── Pessoa.java
│   │   ├── Telefone.java
│   │   ├── Usuario.java
│   │   └── dto/
│   │       └── CepResponse.java
│   ├── service/
│   │   ├── PessoaService.java
│   │   └── ViaCepService.java
│   └── exception/
│       └── BusinessException.java
└── src/main/resources/
    └── application.properties
```

## 🗄️ Banco de Dados

### Tabelas

- **T_ASM_PESSOA** - Dados pessoais (base para médicos, pacientes e usuários)
- **T_ASM_PACIENTE** - Informações de pacientes
- **T_ASM_MEDICO** - Informações de médicos
- **T_ASM_CONSULTA** - Consultas médicas
- **T_ASM_USUARIO** - Usuários do sistema
- **T_ASM_ACOMPANHANTE** - Acompanhantes de pacientes
- **T_ASM_TELEFONE** - Telefones de pessoas e acompanhantes
- **T_ASM_HISTORICO_CONSULTA** - Histórico de consultas

## 📡 Endpoints da API

### Pessoas
- `GET /pessoas` - Listar todas as pessoas
- `GET /pessoas/{id}` - Buscar pessoa por ID
- `POST /pessoas` - Criar nova pessoa
- `PUT /pessoas/{id}` - Atualizar pessoa
- `DELETE /pessoas/{id}` - Deletar pessoa

### Pacientes
- `GET /pacientes` - Listar todos os pacientes
- `GET /pacientes/{id}` - Buscar paciente por ID
- `POST /pacientes` - Criar novo paciente
- `PUT /pacientes/{id}` - Atualizar paciente
- `DELETE /pacientes/{id}` - Deletar paciente

### Médicos
- `GET /medicos` - Listar todos os médicos
- `GET /medicos/{id}` - Buscar médico por ID
- `POST /medicos` - Criar novo médico
- `PUT /medicos/{id}` - Atualizar médico
- `DELETE /medicos/{id}` - Deletar médico

### Consultas
- `GET /consultas` - Listar todas as consultas
- `GET /consultas/{id}` - Buscar consulta por ID
- `GET /consultas/paciente/{idPaciente}` - Listar consultas de um paciente
- `GET /consultas/medico/{idMedico}` - Listar consultas de um médico
- `POST /consultas` - Criar nova consulta
- `PUT /consultas/{id}` - Atualizar consulta
- `DELETE /consultas/{id}` - Deletar consulta

### Usuários
- `GET /usuarios` - Listar todos os usuários
- `GET /usuarios/{id}` - Buscar usuário por ID
- `POST /usuarios` - Criar novo usuário
- `PUT /usuarios/{id}` - Atualizar usuário
- `DELETE /usuarios/{id}` - Deletar usuário

### Acompanhantes
- `GET /acompanhantes` - Listar todos os acompanhantes
- `GET /acompanhantes/{id}` - Buscar acompanhante por ID
- `GET /acompanhantes/paciente/{idPaciente}` - Listar acompanhantes de um paciente
- `POST /acompanhantes` - Criar novo acompanhante
- `PUT /acompanhantes/{id}` - Atualizar acompanhante
- `DELETE /acompanhantes/{id}` - Deletar acompanhante

### Telefones
- `GET /telefones` - Listar todos os telefones
- `GET /telefones/pessoa/{idPessoa}` - Listar telefones de uma pessoa
- `GET /telefones/acompanhante/{idAcompanhante}` - Listar telefones de um acompanhante
- `POST /telefones` - Criar novo telefone
- `PUT /telefones/{id}` - Atualizar telefone
- `DELETE /telefones/{id}` - Deletar telefone

### Histórico de Consultas
- `GET /historico-consultas` - Listar todos os históricos
- `GET /historico-consultas/{id}` - Buscar histórico por ID
- `GET /historico-consultas/consulta/{idConsulta}` - Listar histórico de uma consulta
- `POST /historico-consultas` - Criar novo histórico
- `DELETE /historico-consultas/{id}` - Deletar histórico

### CEP (ViaCEP)
- `GET /cep/{cep}` - Buscar endereço por CEP

## 🔧 Configuração

### application.properties

```properties
# Database
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=rm562027
quarkus.datasource.password=230407
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL

# HTTP
quarkus.http.port=8080

# CORS
quarkus.http.cors=true
quarkus.http.cors.origins=*
quarkus.http.cors.methods=GET,POST,PUT,DELETE,OPTIONS,HEAD

# REST Client ViaCEP
quarkus.rest-client.viacep-api.url=https://viacep.com.br/ws
```

## 🚀 Como Executar

### Modo Desenvolvimento

```bash
./mvnw quarkus:dev
```

### Compilar

```bash
./mvnw clean package
```

### Executar JAR

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

## 📝 Exemplos de Requisições

### Criar Pessoa

```bash
curl -X POST http://localhost:8080/pessoas \
  -H "Content-Type: application/json" \
  -d '{
    "tpPessoa": "Paciente",
    "nmPessoa": "João da Silva",
    "dtNascimento": "1990-05-15",
    "nrCpf": "12345678901"
  }'
```

### Criar Paciente

```bash
curl -X POST http://localhost:8080/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "cdPessoa": 130,
    "nmPlanoSaude": "UNIMED"
  }'
```

### Buscar CEP

```bash
curl http://localhost:8080/cep/01310100
```

## ✅ Validações Implementadas

### Pessoa
- Nome obrigatório
- CPF obrigatório e único
- Tipo de pessoa: Médico, Paciente ou Usuário
- Data de nascimento obrigatória

### Médico
- CRM obrigatório e único
- Especialidade obrigatória
- Salário maior que zero

### Consulta
- Data de início anterior à data de fim
- Status: Agendada, Realizada, Cancelada, Remarcada

### Usuário
- Email obrigatório e único
- Senha mínima de 8 caracteres

### Telefone
- DDD entre 11 e 99

## 🔒 Constraints do Banco

- CPF único
- CRM único
- Email único
- Nome de usuário único
- Validação de datas (início < fim)
- Validação de tipos (Pessoa, Status, Parentesco)

## 🌐 CORS

CORS está habilitado para permitir requisições de qualquer origem, facilitando a integração com front-end.

## 📦 Dependências Principais

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-oracle</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-rest-client-jackson</artifactId>
</dependency>
```

## 👥 Equipe

Projeto desenvolvido para o Challenge ASM - FIAP 
integrantes : Lucca Ramos Mussumecci, rm: 562027 Pedro Peres Benitez, rm: 561792

## 📄 Licença

Este projeto é parte de um trabalho acadêmico.
