# 2002 / 2003 - Especificar, Pausar e Continuar a Especificar o Servico
=======================================


# 1. Requisitos

Como GSH, eu pretendo proceder à especificação de um novo serviço, devendo o sistema permitir que a mesma fique incompleta e seja, posteriomente, retomada.
Um serviço possui:

* Um código único;
* Um título;
* Uma descrição breve;
* Uma descrição completa;
* Um ícone;
* Um conjunto de palavras-chave (keywords);
* O catálogo onde é disponibilizado; 
* Um formulário de solicitação do serviço;
* Atividade de aprovação (podendo ser requerida ou não);
* Atividade de realização
* Requer feedback;

Cada formulário tem:

* Um identificador único no âmbito do serviço;
* Um nome;
* Um lista de atributos a solicitar ao helpdesk.estruturaorganica.domain.colaborador;

Um atributo caracteriza-se por:

* Um nome de variável;
* Uma etiqueta (label) de apresentação;
* Uma descrição de ajuda;
* Um tipo de dados base (e.g., numérico, texto, data, booleano, seleção de valores pré-definidos);
* Obrigatoriedade de preenchimento;
* Uma expressão regular que permita a sua validação local. 
* Um script que permita proceder à sua validação;

Para a atividade de aprovação do serviço apenas é necessária contemplar a aprovação pelo responsável hierárquico direto do
solicitante e/ou pelo responsável do serviço em causa. Ou seja, não se pretende
despender esforços na delegação de tarefas e/ou na solicitação de opinião de terceiros.
Note-se que uma atividade destas tem um formulário associado;

A atividade de realização do serviço pode ser do tipo manual ou do tipo
automática. Note-se que uma atividade manual tem um formulário associado enquanto
que uma tarefa automática tem associado um script na linguagem a definir pela equipa.

        Nota:
        Para persistir o serviço é apenas necessária a introdução do código e título (https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=7772#p10147).
        O serviço só deve ficar disponivel para solicitação quando a sua especificação ficar completa e válida.
        Apenas informação base do serviço (código, descrição, catálogo onde será disponibilizado, formulários, requer feedback). 
        Exemplo de tipos de dados a considerar nos formulários: Inteiro, String, Bool, Data, Ficheiro, ListaDeValores (Enumerado).


# 2. Análise

Excerto do Modelo de Domínio relevante para o Caso de Uso <br>
![Modelo de Dominio](./EspecificacaoServicoMD.png)

Tendo em consideração a continuação da especificação do serviço e a inserção de atividades no mesmo, o modelo de domínio sofreu as seguintes alterções face ao sprint anterior:
* Remoção da classe ServicoBuilder
* Remoção do agregado Formulário
* Remoção do agregado Atividade
* Inserção das classes dos dois agregados no agregado serviço.
Com estas alterações é cumprida a boa prática de cada caso de uso alterar apenas um agregado. 

Por simplificação, foi removida classe ListaKeywords que se associava a atividade. 


## 2.1. A ter em consideração

Um serviço encontra-se completamente especificado quando:
1. Toda a informação necessária para atender pedidos relativos ao mesmo já tiver sido introduzida pelo Gestor de Serviços (e.g. código, título, descrição breve e completa, ícone, palavras-chave, o catálogo a que pertence, o formulário de pedido, outros formulários associados a atividades manuais, a atividade de aprovação caso exista e a atividade de resolução).
2. O Gestor de Serviços confirmar que a especificação está terminada.
Nota: o sistema não deve permitir que o Gestor diga que a especificação está terminada se alguma informação necessária ao correto funcionamento do sistema esteja em falta e/ou incongruente entre si.

A informação mínima requerida por um formulário é: um identificador único no âmbito do serviço, um nome, pelo menos um atributo (todos os dados de um atributo são obrigatórios) e um script que permita proceder à sua validação.

A informação mínima requerida para uma atividade depende do seu tipo: manual ou automática.
(https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=8238#p10775)


## 2.2. Fluxo de realização

Actor: Gestor de Serviços de Help desk (GSH)

Comum

* Login  
* #########
* Ver especificação ou continuar a especificar
* #########
* O sistema solicita as palavras-chave (keywords)
* O sistema apresenta uma lista de catálogos
* O utilizador seleciona o catálogo no qual o serviço se vai enquadrar 
* O utilizador especificar um formulário de solicitação do serviço:
  * O utilizador especifica identificador único no âmbito do serviço, nome
  * O utilizador preenche uma lista de atributos a solicitar ao helpdesk.estruturaorganica.domain.colaborador, para cada atributo preenche:
    * Nome de variável, etiqueta (label), descrição de ajuda
    * Escolhe um tipo de dados base
    * Define expressão regular que permita a sua validação local. 
    * Define um script que permita proceder à sua validação;
* O utilizador define se o serviço dará origem a um feedback;
* O utililizador define uma atividade de aprovação (caso pretenda)
    * Obrigatóriamente do tipo manual, tendo que especificar um formulário;
    * Decide qual a entidade que aprova o serviço (responsável do colaborador ou do catálogo onde se insere o serviço)
* O utlizador define uma atividade de realização
    * Pode ser do tipo manual ou do tipo automática
        * A atividade automática tem um script associado
        * Caso seja manual, deve indicar a equipa reponsavel pela sua realização, ou colaborador 

Especificação
* O utilizador especifica informação base (código, título, descrição, descrição, ícone)

Continuar a especificar
* O sistema apresenta uma lista de serviços incompletos
* O utilizador seleciona qual o serviço a especificar
* O utilizador especifica informação base (título, descrição, descrição, ícone)


            A qualquer altura, quando o utilizador sair, o sistema pergunta se pretende gravar. Caso o seviço apresente os dados mínimos requeridos para garantir a sua persistência este é guardado. 
            O Formulário e as atividades só são persistidos se estiverem completos. 


# 3. Design

O serviço deve ser persistido, tendo sido assim considerado como entidade e como root do seu próprio agregado. <br>
Os atributos desta entidade têm regras especificas inerentes ao negócio. Assim em cada objecto serão especificas todas as regras e validações necesárias.<br>


## 3.1. Padrões Aplicados

* Repository -> Persistência das classes
* Single Responsibility Principle e Information Expert -> Classes de domínio
* Service -> Tratar informação do repositório
* Data Transfer Object -> Ainda não aplicado mas já foi prevista a sua implementação


## 3.2. Testes 

**Teste 1:** Verificar que não é possível criar uma instância da classe Servico com o nome e o identificador com valores nulos.

	@Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed() throws Exception {
        Servico servico = new ServicoBuilder().build();
    }

**Teste 2:** Verificar que o serviço se encontra completo quando todos os seus objetos não são nulos, à exeção do EntidadeAprovação e AtividadeAprovação e que os formulários integrantes nos diferentes objetos do serviço estão completos

**Teste 3:** Verificar que não é possível instanciar um Formulario com os seus atributos a nulo

**Teste 4:** Verificar que o formulário se encontra completo quando todos os seus atributos são diferentes de nulo e a sua lista de atributos tem pelo menos um atributo

**Teste 5:** Verificar que não é possivel instanciar uma atividade com atributos nulos

**Teste 6:** Verificar que dois serviços são iguais se tiverem código igual

**Teste 7:** Verificar que dois formulários são iguais se tiverem o mesmo código

**Teste 8:** Verificar que duas atividades são iguais se forem do mesmo tipo apresentarem atributos iguais, no caso de atividade manual o formulário ser igual e no caso de atividade automática o script ser igual
	

# 4. Observações

.
