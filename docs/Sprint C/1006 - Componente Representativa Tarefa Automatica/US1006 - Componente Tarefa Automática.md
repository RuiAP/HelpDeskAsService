# US1006 - Componente Tarefa Automática
=======================================


# 1. Requisitos


**User Story 1006:** Como Gestor de Projeto, eu pretendo que seja desenvolvida a componente representativa de uma tarefa automática dedicada à execução de um script no âmbito de um pedido e que a mesma seja adicionada à biblioteca de atividades típicas do sistema para, dessa forma, poder ser usada na definição de fluxos de atividades.

**Critérios de aceitação:** n/a

**Caso de uso 4.3.3.b:** De momento, pretende-se adicionar ao sistema:
Componente representativo de uma tarefa automática a ser realizada pelo sistema e que
consiste na execução de um script especificado aquando da inclusão do componente num
fluxo. Salienta-se que para a execução correta de algumas partes do script (e.g., envio de
email) pode requerer a configuração única e centralizada de dados (e.g., o servidor/conta
de email por onde os emails são remetidos).

- - -
**NOTA:** No caderno de encargos são utilizados os termos "Tarefa" e "Atividade" como sinónimos, existindo separadamento o conceito de "componente representativo de uma tarefa".
No entanto, desde o Sprint B, foi adotado no nosso projeto o uso de "Atividade" para nos referirmos ao "componente representativo de uma tarefa", sendo o termo "Tarefa" a única designação válida para nos referirmos a uma tarefa. Como tal,, esta será a nomenclatura utilizada na documentação.
- - -

Foi interpretado que seria necessário criar a Atividade Automática durante a especificação do Serviço, para que possam ser introduzidas pelo GSH as caracteristicas/requisitos que definem o comportamento da Tarefa Automática (que será criada mais tarde a quando da solicitação do Serviço).

Por exemplo, a Atividade Automática irá conter a definição do script que será usado mais tarde para realizar automaticamente a Tarefa Automática correspondente.

# 2. Análise

Eslarecimento adicional por parte do cliente:

[moodle, 17/05/2021](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=8387)

	1. Estas US não devem ser vistas como casos de uso onde há um ator e uma UI para realizar a mesma. Pelo contrário, notem que quem aparece associado às US é o "Gestor de Projeto" que não é ator em nenhuma das aplicações em desenvolvimento.

	2. Assim, estas duas US correspondem a itens de trabalho de caracter mais técnico que visam permitir ao sistema como um todo (e não apenas à aplicação X ou Y) reconhecer e lidar com diferentes tipos de atividades. De momento, o sistema apenas precisa de reconhecer dois tipos de atividades (i) manual e (ii) automática baseada em script. Contudo, o sistema deve estar preparado para facilmente suportar outros tipos de atividades (e.g. executar uma bash file). 

	3. O conjunto de tipos de atividades reconhecidos pelo sistema é denominado de "biblioteca de atividades típicas". Portanto, cada uma destas US visa tecnicamente acrescentar um tipo de atividades a esta biblioteca.

	4. As atividades a incluir no fluxo de atividades de um serviço (no máximo duas) têm que ser de um tipo de de atividade existente nesta biblioteca. Neste caso ou serão do tipo "manual" ou do tipo "automática baseada em script". Contudo, notem que futuramente podem ser de outro tipo. O tipo de atividade determina a informação a solicitar para que a atividade fica especificada.

<br>


* A especificação da Atividade Automática está enquadrada na especificação do serviço, nomeadamente na especificação do método de realização do Serviço. 

* Quando for definido que a concretização do Serviço será levada a cabo pela realização de uma tarefa automática então terá de ser especificada a Atividade Automática correspondente (actualmente apenas será suportada a especificação de Atividade Automática baseada em Script).

* A especificação da atividade automática (baseada em script) tem de permitir a introdução de um script.




# 3. Design

* Como serão suportadas vários tipos de atividades?

	Como se pode ver no excerto seguinte, o modelo de domínio definido suporta esta possibilidade através de um mecanismo de herança, garantindo que outros tipos de atividade podem ser adicionados extendendo a Classe Atividade. Da mesma forma, a classe Tarefa permite o mesmo funcionamento.

	<img src="md_atividade_automatica.png" alt="Excerto modelo dominio Atividade" style="width:400px"/>

	<img src="md_tarefa_automatica.png" alt="Excerto modelo dominio Tarefa" style="width: 493px;"/>
	

<br>
<br>


## 3.1. Realização da Funcionalidade


### Fluxo:
1. Durante a especificação do serviço é solicitado ao GSH qual o modo de realização do Serviço.
2. O GSH seleciona a opção de realização automática (através de Script)
3. O sistema solicita a introdução do scrip de execução
4. O sistema valida que o script não contém erros léxicos ou semânticos e confirma a introdução
5. ...
6. Mais tarde, após o término da especificação do serviço e após uma solicitação do pedido correspondente, esse mesmo Script será utilizado para realizar a Tarefa Automática associada a esta Atividade Automática(no Executor de Tarefas Automáticas) e concluir a realização do pedido.


## 3.2. Padrões e principios aplicados

	Principio Open/Close -> Permitindo a adição de atividades diferentes sem ter de alterar o código já existente.
	Principio GRASP Information Expert -> Para atribuição da responsabilidade de criar objetos atividade ao Serviço já que é esta a classe que agrega/contém os objetos Atividade.


## 3.4. Testes 

**Teste 1:** Verificar que não é possivel criar uma Atividade Automática com o script a null.

	@Test(expected = IllegalArgumentException.class)
		public void verificarNuloNaoPermitido() {
		AtividadeAutomatica atividade = new AtividadeAutomatica(null);
	}

**Teste 2:** Verificar que não é possivel criar uma Atividade Automática com um script vazio.

	@Test(expected = IllegalArgumentException.class)
		public void verirficarVazioNaoPermitido() {
		AtividadeAutomatica atividade = new AtividadeAutomatica(new Script(""));
	}

**Teste 3:** Verificar que não é possivel criar uma Atividade Automática contendo um scrip com erros léxicos ou semânticos.

	@Test(expected = IllegalArgumentException.class)
		public void verirficarVazioNaoPermitido() {
		AtividadeAutomatica atividade = new AtividadeAutomatica(new Script("sript não válido"));
	}




<!--# 4. Implementação

 *Nesta secção a equipa deve providenciar, se necessário, algumas evidências de que a implementação está em conformidade com o design efetuado. Para além disso, deve mencionar/descrever a existência de outros ficheiros (e.g. de configuração) relevantes e destacar commits relevantes;*

*Recomenda-se que organize este conteúdo por subsecções.*

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.* -->



