# UC - Como utilizador pretendo reinvindicar uma tarefa

## Formato Breve

- Como utilizador pretendo reinvindicar uma tarefa que esteja pendente

## Regras de Negócio

- Somente tarefas manuais podem ser reinvindicadas;
- As tarefas manuais podem ser realizadas por uma so pessoa;
- Dados minimos da tarefa devem estar especificados;
- A tarefa manual sera reinvindicada pelo colaborador de uma determinada equipa (ou seja, um colaborador nao tem acesso a TODAS as tarefas pendentes que nao estejam assignadas).

# 2. Análise

Excerto do Modelo de Domínio relevante para o Caso de Uso <br>
![Modelo de Dominio](./TarefaMD.svg)

## 3.1. Padrões Aplicados

* Repository -> Persistência das classes/dados
* Single Responsibility Principle e Information Expert -> Classes de domínio
* Controller -> Para ter a responsabilidade de interligar as camadas de apresentacao e de dominio
* Creator -> criação de instâncias de Colaborador e ademais Value Objects
* HC + LC -> delegação da criacao de um servico para listar os dados pretendidos
* Data Transfer Object -> Para apresentação dos objectos de dominio, de forma isolada do dominio em si, baixando o acoplamento.

## Plano de Testes

### 0. Comum a todos os testes:

- Autenticação por parte do Utilizador;
- Selecionar a opção X de pedido de consulta de todas as tarefas pendentes que lhe estao assignadas.

### 1. Utilizador nao tem qualquer tarefa pendente:

- Sistema devolve uma lista vazia e uma mensagem a indicar tal facto.

### 2. Utilizador nao tem qualquer tarefa assignada:

- Sistema devolve uma lista vazia e uma mensagem a indicar tal facto.

### 3. Utilizador tem pelo menos uma tarefa assignada pendente:

- Sistema devolve uma lista com os dados pedidos.

### 4. Utilizador pede para consultar uma das tarefas que lhe estao assignadas (sem sucesso), mas indica a cardinalidade de uma que na oexiste:

- Sistema informa que a consulta nao foi bem sucedida, indicando o erro, e pede ao Utilizador que indique novamente qual a tarefa a consultar.

### 5. Utilizador pede para consultar uma das tarefas que lhe estao assignadas (com sucesso):

- Sistema mostra a informacao da tarefa escolhida.

## Testes Unitários
