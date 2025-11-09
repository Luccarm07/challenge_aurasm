# 🚀 Como Executar o Projeto AURASM API

## ✅ Pré-requisitos

- **JDK 17** ou superior instalado
- **Acesso ao banco Oracle** da FIAP (oracle.fiap.com.br)
- **Credenciais:** rm562027 / 230407

> **Nota:** Você **NÃO precisa** ter Maven instalado! O projeto já vem com o **Maven Wrapper (mvnw)**.

---

## 📦 Passo 1: Descompactar o Projeto

```bash
unzip aurasm-api-projeto-completo.zip
cd aurasm-api
```

---

## 🔧 Passo 2: Compilar o Projeto

### No Linux/Mac:
```bash
./mvnw clean package -DskipTests
```

### No Windows:
```cmd
mvnw.cmd clean package -DskipTests
```

**Tempo estimado:** 1-2 minutos na primeira execução (baixa dependências)

---

## ▶️ Passo 3: Executar a API

### Opção 1: Modo Desenvolvimento (Recomendado)

**Linux/Mac:**
```bash
./mvnw quarkus:dev
```

**Windows:**
```cmd
mvnw.cmd quarkus:dev
```

**Vantagens do modo dev:**
- ✅ Hot reload (recarrega automaticamente ao salvar arquivos)
- ✅ Dev UI disponível em http://localhost:8080/q/dev
- ✅ Logs detalhados

### Opção 2: Executar o JAR Compilado

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

---

## 🧪 Passo 4: Testar a API

Após iniciar, a API estará disponível em: **http://localhost:8080**

### Teste 1: Verificar se está rodando
```bash
curl http://localhost:8080/pessoas
```

**Resposta esperada:** Lista de pessoas (pode estar vazia `[]`)

### Teste 2: Buscar CEP (ViaCEP)
```bash
curl http://localhost:8080/cep/01310100
```

**Resposta esperada:**
```json
{
  "cep": "01310-100",
  "logradouro": "Avenida Paulista",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

### Teste 3: Criar uma Pessoa
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

**Resposta esperada:** Status 201 Created com os dados da pessoa criada

---

## 🐳 Opção Alternativa: Docker

Se preferir usar Docker:

### 1. Compilar o projeto
```bash
./mvnw clean package -DskipTests
```

### 2. Construir a imagem Docker
```bash
docker build -t aurasm-api .
```

### 3. Executar o container
```bash
docker run -p 8080:8080 aurasm-api
```

---

## 📝 Importar no IntelliJ IDEA

### Passo a Passo:

1. **Abrir IntelliJ IDEA**

2. **File → Open**

3. **Selecionar a pasta** `aurasm-api`

4. **Aguardar** o IntelliJ indexar e baixar dependências

5. **Executar:**
   - Localizar a classe principal (qualquer Controller)
   - Clicar com botão direito → **Run 'Quarkus Dev Mode'**
   
   Ou usar o terminal integrado:
   ```bash
   ./mvnw quarkus:dev
   ```

---

## 🔍 Verificar Logs

Quando a API iniciar com sucesso, você verá:

```
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (Quarkus Main Thread) aurasm-api 1.0.0 on JVM started in 2.345s. Listening on: http://0.0.0.0:8080
INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, hibernate-validator, jdbc-oracle, rest-client-reactive-jackson, resteasy-reactive, resteasy-reactive-jackson, smallrye-context-propagation, vertx]
```

---

## 🛠️ Solução de Problemas

### Erro: "Java version not found"
**Solução:** Instale o JDK 17
```bash
# Ubuntu/Debian
sudo apt install openjdk-17-jdk

# Mac (Homebrew)
brew install openjdk@17

# Windows
Baixe em: https://adoptium.net/
```

### Erro: "Connection refused" ao acessar banco
**Solução:** Verifique se está conectado à rede da FIAP ou VPN

### Erro: "Port 8080 already in use"
**Solução:** Altere a porta no `application.properties`:
```properties
quarkus.http.port=8081
```

### Erro: "mvnw: Permission denied"
**Solução:** Dê permissão de execução:
```bash
chmod +x mvnw
```

---

## 📊 Endpoints Disponíveis

Após iniciar, acesse a documentação completa em:
- **ENDPOINTS.md** (no projeto)
- **Dev UI:** http://localhost:8080/q/dev (modo dev)

### Principais Endpoints:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /pessoas | Listar pessoas |
| GET | /pacientes | Listar pacientes |
| GET | /medicos | Listar médicos |
| GET | /consultas | Listar consultas |
| GET | /usuarios | Listar usuários |
| GET | /acompanhantes | Listar acompanhantes |
| GET | /telefones | Listar telefones |
| GET | /historico-consultas | Listar históricos |
| GET | /cep/{cep} | Buscar CEP (ViaCEP) |

**Total:** 45+ endpoints implementados!

---

## 🎯 Próximos Passos

1. ✅ Testar todos os endpoints
2. ✅ Integrar com seu front-end
3. ✅ Ajustar validações se necessário
4. ✅ Adicionar mais funcionalidades

---

## 📞 Suporte

Consulte os arquivos:
- **README.md** - Documentação geral
- **ENDPOINTS.md** - Documentação de endpoints
- **ENTREGA_FINAL.md** - Informações do projeto

---

## ✨ Dicas

- Use **modo dev** (`./mvnw quarkus:dev`) durante desenvolvimento
- Acesse **Dev UI** em http://localhost:8080/q/dev para ver endpoints
- Use **Postman** ou **Insomnia** para testar endpoints
- Consulte logs para debug

**Projeto pronto para uso! 🚀**
