# E-commerce com Padrões GOF

Sistema de loja virtual desenvolvido em Java com Spring Boot para demonstração e aplicação dos padrões de projeto Gang of Four (GOF).

## Sobre o Sistema

Sistema de e-commerce que permite clientes navegarem por produtos, fazerem pedidos online, escolherem forma de pagamento e receberem em casa. O sistema gerencia catálogo de produtos organizados por categorias, cadastro de clientes com múltiplos endereços, criação e acompanhamento de pedidos com diferentes status (pendente, confirmado, processando, enviado, entregue), controle de estoque automático e cálculo de valores (subtotal, frete, descontos).

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL** (banco de dados local)
- **Lombok** (redução de boilerplate)
- **ModelMapper** (conversão entre entidades e DTOs)
- **SpringDoc OpenAPI** (documentação Swagger)
- **Maven** (gerenciamento de dependências)

## 📋 Estrutura do Projeto

```
src/main/java/com/ecommerce/
├── model/              # Entidades JPA
├── dto/                # Data Transfer Objects
├── repository/         # Interfaces JPA Repository
├── service/            # Lógica de negócio
├── controller/         # Controllers REST
├── config/             # Configurações
└── exception/          # Tratamento de exceções
```

## 🏗️ Entidades Principais

1. **Product** - Produtos da loja
2. **Customer** - Clientes
3. **Address** - Endereços dos clientes
4. **Order** - Pedidos
5. **OrderItem** - Itens dos pedidos

## 🔧 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior

### Configuração do Banco de Dados

1. **Criar o banco de dados no MySQL**
```sql
CREATE DATABASE ecommercedb;
```

2. **Configurar credenciais no application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommercedb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Passos

1. **Clone ou navegue até o diretório do projeto**

2. **Compile o projeto**
```bash
mvn clean install
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

4. **Acesse a aplicação**
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## 📡 Endpoints Principais

### Products
- `GET /api/products` - Listar todos os produtos
- `GET /api/products/{id}` - Buscar produto por ID
- `POST /api/products` - Criar novo produto
- `PUT /api/products/{id}` - Atualizar produto
- `DELETE /api/products/{id}` - Deletar produto
- `GET /api/products/category/{category}` - Buscar por categoria
- `GET /api/products/search?name=` - Buscar por nome

### Customers
- `GET /api/customers` - Listar todos os clientes
- `GET /api/customers/{id}` - Buscar cliente por ID
- `POST /api/customers` - Criar novo cliente
- `PUT /api/customers/{id}` - Atualizar cliente
- `DELETE /api/customers/{id}` - Deletar cliente

### Addresses
- `GET /api/addresses/customer/{customerId}` - Listar endereços do cliente
- `POST /api/addresses` - Criar novo endereço
- `PUT /api/addresses/{id}` - Atualizar endereço
- `DELETE /api/addresses/{id}` - Deletar endereço

### Orders
- `GET /api/orders` - Listar todos os pedidos
- `GET /api/orders/{id}` - Buscar pedido por ID
- `GET /api/orders/customer/{customerId}` - Listar pedidos do cliente
- `POST /api/orders` - Criar novo pedido
- `PATCH /api/orders/{id}/status?status=` - Atualizar status do pedido
- `DELETE /api/orders/{id}` - Cancelar pedido

## 🎨 Padrões GOF a Serem Implementados

### Padrões Criacionais
- [ ] **Factory Method** - Criação de diferentes tipos de produtos
- [ ] **Abstract Factory** - Famílias de produtos relacionados
- [ ] **Builder** - Construção complexa de pedidos
- [ ] **Prototype** - Clonagem de produtos/pedidos
- [ ] **Singleton** - Configurações da aplicação

### Padrões Estruturais
- [ ] **Adapter** - Integração com gateways de pagamento
- [ ] **Bridge** - Separação de tipos de produto e suas representações
- [ ] **Composite** - Estrutura de categorias de produtos
- [ ] **Decorator** - Adição de recursos a produtos (embalagem, garantia)
- [ ] **Facade** - Simplificação do processo de checkout
- [ ] **Flyweight** - Compartilhamento de dados de produtos
- [ ] **Proxy** - Cache e lazy loading

### Padrões Comportamentais
- [ ] **Chain of Responsibility** - Validação e processamento de pedidos
- [ ] **Command** - Operações de pedidos (undo/redo)
- [ ] **Iterator** - Navegação em coleções
- [ ] **Mediator** - Comunicação entre componentes
- [ ] **Memento** - Histórico de estados
- [ ] **Observer** - Notificações de mudanças de status
- [ ] **State** - Estados do pedido
- [ ] **Strategy** - Estratégias de cálculo (frete, desconto)
- [ ] **Template Method** - Processo de pagamento
- [ ] **Visitor** - Operações sobre diferentes tipos de pedidos

## 📝 Exemplos de Uso (cURL)

### Criar um Produto
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Dell",
    "description": "Notebook Dell Inspiron 15",
    "price": 3500.00,
    "stockQuantity": 10,
    "category": "ELECTRONICS",
    "weight": 2.5,
    "width": 35,
    "height": 25,
    "depth": 2
  }'
```

### Criar um Cliente
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123",
    "phone": "(11) 98765-4321",
    "cpf": "123.456.789-00"
  }'
```

### Criar um Endereço
```bash
curl -X POST http://localhost:8080/api/addresses \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "street": "Rua das Flores",
    "number": "123",
    "complement": "Apto 45",
    "neighborhood": "Centro",
    "city": "São Paulo",
    "state": "SP",
    "zipCode": "01234-567",
    "isDefault": true,
    "type": "HOME"
  }'
```

### Criar um Pedido
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "shippingAddressId": 1,
    "paymentMethod": "CREDIT_CARD",
    "items": [
      {
        "productId": 1,
        "quantity": 2
      }
    ],
    "shippingCost": 15.00,
    "discount": 0.00
  }'
```

## 🧪 Testes

Para executar os testes:
```bash
mvn test
```

## 📚 Próximos Passos

1. Implementar os padrões GOF de forma incremental
2. Adicionar autenticação e autorização (Spring Security)
3. Implementar testes unitários e de integração
4. Adicionar documentação detalhada de cada padrão

## 📄 Licença

Projeto educacional.
