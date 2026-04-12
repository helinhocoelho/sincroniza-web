# SyncStock - Sincroniza Web

## Status do Projeto

Em desenvolvimento

## Tecnologias Utilizadas

- **Java** – Linguagem de programação para desenvolvimento backend
- **MySQL** – Banco de dados relacional para armazenamento de dados
- **Git** – Controle de versionamento
- **GitHub** – Plataforma para repositório do código versionado

## Time de Desenvolvedores

- Hélio Coelho

## Objetivo do Software

O SyncStock tem como objetivo centralizar e otimizar a gestão de materiais em instituições de ensino, eliminando o uso de planilhas e proporcionando:

- Maior controle de estoque entre unidades;
- Relatórios organizados e seguros;
- Redução de desperdícios;
- Backup diário automático;
- Envio de relatórios por e-mail para acompanhamento e tomada de decisão.

## Funcionalidades do Sistema

### Cadastros

- Usuários
- Permissões
- Unidades
- Setores
- Produtos
- Categorias de Produtos
- Fornecedores
- Pedidos
- Estoque (entradas e saídas)

### Requisitos Funcionais

- **RF001 – CRUD de Usuário**: criação, leitura, atualização e exclusão de usuários, com validação de login e senha.
- **RF002 – CRUD de Unidade**: gerenciamento de unidades (nome, endereço, telefone, e-mail).
- **RF003 – CRUD de Setor**: gerenciamento de setores vinculados a uma unidade.
- **RF004 – CRUD de Produto**: cadastro de produtos (nome, descrição, unidade de medida, categoria, fornecedor).
- **RF005 – CRUD de Categoria de Produto**: gerenciamento de categorias de produtos.
- **RF006 – CRUD de Fornecedor**: cadastro de fornecedores (nome, CNPJ, endereço, telefone, e-mail).
- **RF007 – Gestão de Pedidos**: criação, aprovação, envio, recebimento e cancelamento de pedidos.
- **RF008 – Registro de Entrada e Saída de Estoque**: controle de movimentação de produtos.
- **RF009 – Relatórios**: geração de relatórios de estoque, pedidos, movimentações e produtos com estoque abaixo do mínimo.
- **RF010 – Autenticação de Usuário**: login com verificação de credenciais e controle de permissões (Administrador, Gerente, Solicitante).

### Requisitos Não Funcionais

- **RNF001 – Desempenho**: respostas rápidas às requisições.
- **RNF002 – Escalabilidade**: suporte a múltiplas unidades e usuários.
- **RNF003 – Usabilidade**: interface intuitiva e organizada.
- **RNF004 – Segurança**: criptografia de senhas e controle de acesso.
- **RNF005 – Disponibilidade**: sistema disponível 24/7.
- **RNF006 – Backup**: backup automático diário do banco de dados.
- **RNF007 – Envio de E-mails**: relatórios e notificações por e-mail.
- **RNF008 – Responsividade**: interface adaptável a desktop, tablets e smartphones.

### Regras de Negócio

- Controle de estoque mínimo com alertas para administradores/gerentes;
- Validação de saldo antes de saídas de estoque;
- Fluxo de aprovação de pedidos por administradores;
- Controle de acesso por unidade (gerentes restritos à sua unidade);
- Garantia de consistência nas unidades de medida.
