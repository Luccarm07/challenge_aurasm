# 📡 Documentação de Endpoints - AURASM API

## Base URL
```
http://localhost:8080
```

---

## 👤 Pessoas

### Listar todas as pessoas
```http
GET /pessoas
```

**Resposta 200 OK:**
```json
[
  {
    "cdPessoa": 100,
    "tpPessoa": "Paciente",
    "nmPessoa": "Ana Silva Santos",
    "dtNascimento": "1990-05-15",
    "nrCpf": "12345678901"
  }
]
```

### Buscar pessoa por ID
```http
GET /pessoas/{id}
```

### Criar nova pessoa
```http
POST /pessoas
Content-Type: application/json

{
  "tpPessoa": "Paciente",
  "nmPessoa": "João da Silva",
  "dtNascimento": "1990-05-15",
  "nrCpf": "12345678901"
}
```

**Validações:**
- `tpPessoa`: Deve ser "Médico", "Paciente" ou "Usuário"
- `nmPessoa`: Obrigatório
- `nrCpf`: Obrigatório e único
- `dtNascimento`: Obrigatório

### Atualizar pessoa
```http
PUT /pessoas/{id}
Content-Type: application/json

{
  "nmPessoa": "João da Silva Atualizado",
  "dtNascimento": "1990-05-15"
}
```

### Deletar pessoa
```http
DELETE /pessoas/{id}
```

---

## 🏥 Pacientes

### Listar todos os pacientes
```http
GET /pacientes
```

### Buscar paciente por ID
```http
GET /pacientes/{id}
```

### Criar novo paciente
```http
POST /pacientes
Content-Type: application/json

{
  "cdPessoa": 130,
  "nmPlanoSaude": "UNIMED BASICO"
}
```

**Validações:**
- `cdPessoa`: Obrigatório (deve existir na tabela Pessoa)
- `nmPlanoSaude`: Obrigatório

### Atualizar paciente
```http
PUT /pacientes/{id}
Content-Type: application/json

{
  "cdPessoa": 130,
  "nmPlanoSaude": "UNIMED PREMIUM"
}
```

### Deletar paciente
```http
DELETE /pacientes/{id}
```

---

## 👨‍⚕️ Médicos

### Listar todos os médicos
```http
GET /medicos
```

### Buscar médico por ID
```http
GET /medicos/{id}
```

### Criar novo médico
```http
POST /medicos
Content-Type: application/json

{
  "cdPessoa": 131,
  "nrCrm": "12345SP",
  "dsEspecialidade": "Cardiologia",
  "vlSalario": 15000.00
}
```

**Validações:**
- `cdPessoa`: Obrigatório
- `nrCrm`: Obrigatório e único
- `dsEspecialidade`: Obrigatório
- `vlSalario`: Obrigatório e maior que zero

### Atualizar médico
```http
PUT /medicos/{id}
Content-Type: application/json

{
  "dsEspecialidade": "Cardiologia Pediátrica",
  "vlSalario": 18000.00
}
```

### Deletar médico
```http
DELETE /medicos/{id}
```

---

## 📅 Consultas

### Listar todas as consultas
```http
GET /consultas
```

### Buscar consulta por ID
```http
GET /consultas/{id}
```

### Listar consultas de um paciente
```http
GET /consultas/paciente/{idPaciente}
```

### Listar consultas de um médico
```http
GET /consultas/medico/{idMedico}
```

### Criar nova consulta
```http
POST /consultas
Content-Type: application/json

{
  "cdPaciente": 1,
  "cdMedico": 50,
  "dtInicio": "2025-11-10T10:00:00",
  "dtFim": "2025-11-10T11:00:00",
  "dsObservacao": "Consulta de rotina",
  "dsStatus": "Agendada"
}
```

**Validações:**
- `cdPaciente`: Obrigatório
- `cdMedico`: Obrigatório
- `dtInicio`: Obrigatório e deve ser anterior a `dtFim`
- `dtFim`: Obrigatório
- `dsStatus`: Deve ser "Agendada", "Realizada", "Cancelada" ou "Remarcada"

### Atualizar consulta
```http
PUT /consultas/{id}
Content-Type: application/json

{
  "dsObservacao": "Consulta remarcada",
  "dsStatus": "Remarcada"
}
```

### Deletar consulta
```http
DELETE /consultas/{id}
```

---

## 👥 Usuários

### Listar todos os usuários
```http
GET /usuarios
```

### Buscar usuário por ID
```http
GET /usuarios/{id}
```

### Criar novo usuário
```http
POST /usuarios
Content-Type: application/json

{
  "cdPessoa": 132,
  "dsEmail": "usuario@email.com",
  "dsSenha": "senha12345",
  "nmUsuario": "usuario123"
}
```

**Validações:**
- `cdPessoa`: Obrigatório
- `dsEmail`: Obrigatório e único
- `dsSenha`: Obrigatório, mínimo 8 caracteres
- `nmUsuario`: Obrigatório e único

### Atualizar usuário
```http
PUT /usuarios/{id}
Content-Type: application/json

{
  "dsEmail": "novoemail@email.com",
  "dsSenha": "novasenha123"
}
```

### Deletar usuário
```http
DELETE /usuarios/{id}
```

---

## 👨‍👩‍👧 Acompanhantes

### Listar todos os acompanhantes
```http
GET /acompanhantes
```

### Buscar acompanhante por ID
```http
GET /acompanhantes/{id}
```

### Listar acompanhantes de um paciente
```http
GET /acompanhantes/paciente/{idPaciente}
```

### Criar novo acompanhante
```http
POST /acompanhantes
Content-Type: application/json

{
  "cdPaciente": 1,
  "nmAcompanhante": "Maria Silva",
  "dsParentesco": "Mãe"
}
```

**Validações:**
- `cdPaciente`: Obrigatório
- `nmAcompanhante`: Obrigatório
- `dsParentesco`: Deve ser "Pai", "Mãe", "Filho", "Filha", "Cônjuge" ou "Outro"

### Atualizar acompanhante
```http
PUT /acompanhantes/{id}
Content-Type: application/json

{
  "nmAcompanhante": "Maria Silva Santos",
  "dsParentesco": "Cônjuge"
}
```

### Deletar acompanhante
```http
DELETE /acompanhantes/{id}
```

---

## 📞 Telefones

### Listar todos os telefones
```http
GET /telefones
```

### Listar telefones de uma pessoa
```http
GET /telefones/pessoa/{idPessoa}
```

### Listar telefones de um acompanhante
```http
GET /telefones/acompanhante/{idAcompanhante}
```

### Criar novo telefone
```http
POST /telefones
Content-Type: application/json

{
  "cdPessoa": 100,
  "cdAcompanhante": null,
  "ddd": 11,
  "nmTelefone": 987654321
}
```

**Validações:**
- `cdPessoa` ou `cdAcompanhante`: Pelo menos um deve ser informado
- `ddd`: Obrigatório, deve estar entre 11 e 99
- `nmTelefone`: Obrigatório

### Atualizar telefone
```http
PUT /telefones/{id}
Content-Type: application/json

{
  "ddd": 11,
  "nmTelefone": 912345678
}
```

### Deletar telefone
```http
DELETE /telefones/{id}
```

---

## 📋 Histórico de Consultas

### Listar todos os históricos
```http
GET /historico-consultas
```

### Buscar histórico por ID
```http
GET /historico-consultas/{id}
```

### Listar histórico de uma consulta
```http
GET /historico-consultas/consulta/{idConsulta}
```

### Criar novo histórico
```http
POST /historico-consultas
Content-Type: application/json

{
  "cdConsulta": 1000,
  "cdMedico": 50,
  "nmMedico": "Dr. Carlos Souza",
  "dtInicio": "2025-09-15T14:30:00",
  "dtFim": "2025-09-15T15:00:00",
  "dsConsulta": "Consulta de cardiologia"
}
```

**Validações:**
- `cdConsulta`: Obrigatório
- `cdMedico`: Obrigatório
- `nmMedico`: Obrigatório
- `dtInicio`: Obrigatório
- `dtFim`: Obrigatório
- `dsConsulta`: Obrigatório

### Deletar histórico
```http
DELETE /historico-consultas/{id}
```

---

## 🏠 CEP (ViaCEP)

### Buscar endereço por CEP
```http
GET /cep/{cep}
```

**Exemplo:**
```http
GET /cep/01310100
```

**Resposta 200 OK:**
```json
{
  "cep": "01310-100",
  "logradouro": "Avenida Paulista",
  "complemento": "",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP",
  "ibge": "3550308",
  "gia": "1004",
  "ddd": "11",
  "siafi": "7107"
}
```

**Validações:**
- CEP deve conter 8 dígitos
- Aceita formato com ou sem hífen (01310-100 ou 01310100)

---

## 📊 Códigos de Status HTTP

- **200 OK** - Requisição bem-sucedida
- **201 Created** - Recurso criado com sucesso
- **204 No Content** - Recurso deletado com sucesso
- **400 Bad Request** - Dados inválidos ou faltando
- **404 Not Found** - Recurso não encontrado
- **500 Internal Server Error** - Erro interno do servidor

---

## 🔧 Testando com cURL

### Exemplo completo: Criar pessoa e paciente

```bash
# 1. Criar pessoa
curl -X POST http://localhost:8080/pessoas \
  -H "Content-Type: application/json" \
  -d '{
    "tpPessoa": "Paciente",
    "nmPessoa": "João da Silva",
    "dtNascimento": "1990-05-15",
    "nrCpf": "98765432100"
  }'

# 2. Criar paciente (usando ID da pessoa criada)
curl -X POST http://localhost:8080/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "cdPessoa": 130,
    "nmPlanoSaude": "UNIMED"
  }'

# 3. Buscar CEP
curl http://localhost:8080/cep/01310100

# 4. Listar todos os pacientes
curl http://localhost:8080/pacientes
```

---

## 🌐 CORS

A API está configurada para aceitar requisições de qualquer origem, facilitando a integração com front-end.

**Headers permitidos:**
- origin
- content-type
- accept
- authorization
- x-requested-with

**Métodos permitidos:**
- GET
- POST
- PUT
- DELETE
- OPTIONS
- HEAD
