# dscatalog
## Bootcamp Java+Spring 3.0 - DevSuperior<br>
## Professor: Nelio Alves

### <a href="https://dscatalog-sessao3-deploy-production.up.railway.app">Link para acesso a aplicação atraves do RailWay</a>
### <a href="https://drive.google.com/file/d/1rwyKUhLfAaJkDvGyrjWTvAPH59NdFnPl/view?usp=drive_link"> Link para download da configuração do Postman </a>

### DSCatalog (back-end)
Projeto que foi dividido em três partes sendo elas:<br>

1 - CRUD (Create, Read, Update, Delete)<br>
2 - Testes automatizados<br>
3 - Validação e segurança<br>

Neste projeto foi feito o cadastro e autenticação de usuarios, <br>
e esses usuarios tem a permissão de criar e editar categorias, <br>
e esses usuarios também podem criar e editar produtos, <br>
produtos esses que fazem parte de uma categoria.<br>

#### Stack Utilizada:
<p> Back-end</p>
Linguagem de programação: Java<br>
Banco de dados: H2<br>
Framework: SpringBoot<br>
<br>
<b>Outras ferramentas e recursos:</b><br>
Maven<br>
Postman<br>
Spring Data JPA<br>
JUnit<br>
Mockito<br>
MockBean<br>
Spring Security<br>
JWT<br>
Bean Validation<br>


### Modelo conceitual

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/BootCamp_Java%2BSpring_DSCatalog_Modelo_Conceitual.jpg">



## Testes no Postman

### 1 - Variaveis de ambiente
#### 1.1 Declaração das variaveis de ambiente no Postman

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/1%20-%20Login%20-%20environment.png">

### 2 - Authorization
#### 2.1 Declarando usuario e senha para o Postman se conectar com a aplicação no back-end

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/2%20-%20Login%20-%20credenciais%20da%20aplicacao.png">

#### 2.2 Declarando o usuario e a senha da pessoa que vai fazer o login na aplicação DSCatalog

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/3%20-%20Login%20-%20credenciais%20da%20pessoa.png">


### 3 - Category

#### 3.1 Categories paged

Carregando as categorias cadastradas

| Parâmetro      | Value       | Descrição                                |
|:---------------|:------------|:-----------------------------------------|
| `page`         | `integer`   | Página desejada.                         |
| `linesPerPage` | `integer`   | Quantidade de linhas por página.         |
| `direction`    | `integer`   | Descentende ou Ascendente.               |
| `orderBy`      | `integer`   | Ordena por ID as categorias encontradas. |

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/4%20-%20Category%20paged.png">


#### 3.2 Categories by id

Carregando uma categoria pelo o id

| Parâmetro   | Tipo        | Descrição                           |
|:------------|:------------|:------------------------------------|
| `id`        | `integer`   | O ID da categoria que você busca.   |

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/5-%20Category%20by%20id.png">


#### 3.3 Category post

Cadastrando uma nova categoria

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/6%20-%20Category%20post.png">


#### 3.4 Category update

Atualizando o nome de uma categoria

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/7%20-%20Category%20update.png">

#### 3.5 Category delete

Informando o id de uma categoria que queremos deletar

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/8%20-%20Category%20delete.png">


### 4 - Product
#### 4.1 Product paged

Carregando os produtos que estão cadastrados

| Parâmetro     | Tipo       | Descrição                                                                  |
|:--------------|:-----------|:---------------------------------------------------------------------------|
| `name`        | `string`   | Nome do produto.                                                           |
| `description` | `string`   | Descrição do produto.                                                      |
| `price`       | `float`    | Preço do produto.                                                          |
| `imgUrl`      | `string`   | Url da imagem do produto.                                                  |
| `date`        | `instant`  | Data de criação no formato _**2022-07-20T10:00:00Z**_.                     |
| `categories`  | `integer`  | Objeto **categories** passando uma List de ID, das respectivas categorias. |


<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/9%20-%20Products%20paged.png">


#### 4.2 Product by id

Carregando um produto conforme o id passado

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/10%20-%20Product%20by%20id.png">

#### 4.3 Product post

Cadastrando um produto

| Parâmetro     | Tipo       | Descrição                                                                  |
|:--------------|:-----------|:---------------------------------------------------------------------------|
| `name`        | `string`   | Nome do produto.                                                           |
| `description` | `string`   | Descrição do produto.                                                      |
| `price`       | `float`    | Preço do produto.                                                          |
| `imgUrl`      | `string`   | Url da imagem do produto.                                                  |
| `date`        | `instant`  | Data de criação no formato _**2022-07-20T10:00:00Z**_.                     |
| `categories`  | `integer`  | Objeto **categories** passando uma List de ID, das respectivas categorias. |

```json
    {
      "date": "2020-07-20T10:00:00Z",
      "description": "The new generation PS5 video game",
      "name": "PS5",
      "imgUrl": "",
      "price": 600.0,
      "categories": [
        {
          "id": 1
        },
        {
          "id": 3
        }
      ]
    }
```

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/11%20-%20Product%20post.png">


#### 4.4 Product update

Atualizando as informações de um produto


| Parâmetro     | Tipo  / Value | Descrição                              |
|:--------------|:--------------|:---------------------------------------|
| `id`          | `integer`     | O ID do produto que você quer alterar. |
| `name`        | `string`      | Novo nome do produto.                  |
| `description` | `string`      | Nova descrição do produto.             |
| `price`       | `float`       | Novo preço do produto.                 |
| `imgUrl`      | `string`      | Nova url da imagem do produto.         |
| `categories`  | `integer`     | Nova categoria(as) do produto.         |

```json
    {
  "name": "Alterando produto."
}
```

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/12%20-%20Product%20update.png">

#### 4.5 Product delete

Deletando um produto

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/13%20-%20Product%20delete.png">


### 5 Users

#### 5.1 Users paged

Carregando os usuarios que estão cadastrados

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/14%20-%20Users%20paged.png">



#### 5.2 User by id

Carregando um usuario pelo o id

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/15%20-%20User%20by%20id.png">


#### 5.3 User post

Cadastrando um usuario

```json
{
  "firstName": "Bob",
  "lastName": "Brown",
  "email": "bob10@gmail.com",
  "password": "bob123",
  "roles": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ]
}
```

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/16%20-%20User%20post.png">


#### 5.4 User update

Atualizando informações de um usuario

```json
{
  "firstName": "Bob",
  "lastName": "Brown",
  "email": "alex@gmail.com",
  "password": "bob123",
  "roles": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ]
}

```

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/17%20-%20User%20update.png">


#### 5.5 User delete

Deletando um usuario

<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Nelio_Alves/Bootcamp_Java_e_Spring_3.0/Sessao_1_2_3_DSCatalog/18%20-%20User%20delete.png">
