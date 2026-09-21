# Gerador de QR Code

Aplicação web para gerar QR Codes em formato PNG a partir de textos, links, URLs ou informações simples. A solução combina uma API REST em Java com um frontend estático em HTML, CSS e JavaScript, permitindo criar imagens de QR Code de forma rápida e prática.

## O que é esta aplicação?

Este projeto permite transformar qualquer conteúdo em um QR Code visualmente pronto para uso, como:

- URLs e links
- Mensagens curtas
- Dados de contato
- Informações que possam ser lidas por celular

A geração acontece em memória, sem persistência de dados, e a imagem resultante é retornada diretamente para o navegador ou para uso em chamadas HTTP.

## Funcionalidades

- Geração de QR Code a partir de texto livre
- Resposta em formato PNG
- Interface web simples e responsiva
- Download do QR Code gerado
- API REST para integração com outros sistemas

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.1
- Maven
- Google ZXing (biblioteca para geração de QR Code)
- HTML5
- CSS3
- JavaScript

## Estrutura do projeto

```text
qrcode.generator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/matheusleffa/qrcode/generator/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── service/
│   │   │       └── Application.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── static/frontend/
│   │   │       ├── index.html
│   │   │       ├── css/
│   │   │       └── js/
│   └── test/
│       └── java/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Pré-requisitos

Antes de rodar a aplicação, certifique-se de que o ambiente possui:

- Java 21 ou superior
- Maven 3.9+
- Git (opcional, apenas para clonar o repositório)

## Como rodar localmente

### 1) Acesse a pasta do projeto

```bash
cd qrcode.generator/qrcode.generator
```

### 2) Execute a aplicação

Usando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

No Windows PowerShell ou CMD:

```bash
mvnw.cmd spring-boot:run
```

### 3) Acesse a interface web

Abra no navegador:

```text
http://localhost:8080/frontend/index.html
```

### 4) Gere o QR Code

- Digite um texto ou link no campo da página
- Clique em "Gerar QR Code"
- O QR Code será exibido e poderá ser baixado em PNG

## API REST

A API principal está disponível em:

```text
POST http://localhost:8080/qrcode
```

### Exemplo de corpo JSON

```json
{
  "content": "https://example.com"
}
```

### Exemplo com curl

```bash
curl -X POST http://localhost:8080/qrcode \
  -H "Content-Type: application/json" \
  -d '{"content":"https://example.com"}' \
  --output qrcode.png
```

A resposta será uma imagem PNG do QR Code gerado.

## Build da aplicação

Para gerar o arquivo JAR executável:

```bash
./mvnw clean package
```

Em seguida, execute:

```bash
java -jar target/qrcode.generator-0.0.1-SNAPSHOT.jar
```

## Observações

- A aplicação gera os QR Codes em tempo real, sem salvar imagens no disco.
- O conteúdo do QR Code pode ser qualquer texto, mas textos muito longos podem reduzir a legibilidade do código.
- O frontend é servido como conteúdo estático do Spring Boot.

## Contribuição

Sinta-se à vontade para abrir issues, propor melhorias ou ajustar a interface e a API conforme necessário.

## Licença

Este projeto foi desenvolvido para fins de estudo e demonstração de uso de Java + Spring Boot + ZXing.
