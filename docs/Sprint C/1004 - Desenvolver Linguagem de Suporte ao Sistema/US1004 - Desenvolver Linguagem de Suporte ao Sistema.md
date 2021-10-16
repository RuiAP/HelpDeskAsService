# US1004 - Desenvolver Linguagem de Suporte ao Sistema
=======================================


# 1. Requisitos

**User Story 1004:** Como Gestor de Projeto, eu pretendo que seja desenvolvida uma linguagem/gramática de suporte geral ao sistema para expressar, entre outras coisas, validações de formulários e atividades automáticas.  

**Critérios de aceitação:** 

Pode ser uma ou mais linguagens de suporte a todo o sistema. 

Caso seja mais do que uma, pretende-se que haja coerência/consistência sintática e semântica entre as linguagens.

Neste sprint apenas é requerida a validação sintática e semântica das expressões suportadas pela linguagem.

<br>

**Interpretação:**

Deve ser desenvolvida uma linguagem (ou duas, caso seja preferíve) que permita a validação dos valores introduzidos nos Formulários, a validação geral do formulário, e a realização de tarefas automáticas por script (para o sprint C, a validação sintática e semântica das expressões é suficiente). A linguagem deve ser simples pois vai ser utilizada pelo GSH (durante o processo de especificação de Serviços), mas deve permitri
 
<br>

# 2. Análise

**Caderno de Encargos, página 4/5:**

	Relativamente às tarefas automáticas, pretende-se que a linguagem usada na especificação de um 	script seja simples, adequada aos (reduzidos) conhecimentos técnicos dos utilizadores do sistema responsáveis pela definição dos catálogos de serviços e orientada às necessidades de negócio. Neste

	contexto, esta linguagem deve suportar:

	• A consulta/obtenção de informação constante num determinado ficheiro local partindo de dados 	existentes no contexto do pedido em causa. Por exemplo, com base no número de cliente indicado no pedido consultar um ficheiro XML com informação sobre clientes para obter o escalão de rapel
	que lhe está associado. Outro exemplo, com base no identificador de um produto consultar um outro ficheiro para obter o preço base de comercialização desse produto;

	• A realização de cálculos matemáticos baseados em informação disponível tanto no contexto do pedido como em informação obtida durante a realização do script (cf. ponto anterior). Por	exemplo, com base numa quantidade indicada no pedido e no preço base do produto obtido
	através de uma consulta, calcula-se um valor total (i.e., quantidade * preço) e a partir daí aplica se um desconto de rapel (e.g., se valor total for menor que X não há direito a desconto, entre X e Y aplica-se 2% desconto e superior a Y aplica-se 5% de desconto);

	• O envio de emails cujo remetente(s), assunto e corpo da mensagem estão estaticamente definidos no script. Contudo, estes podem conter algumas expressões que precisam de ser previamente computadas e substituídas pelo seu resultado de forma a que o email seja corretamente enviado.
	Por exemplo, no corpo do email pode constar o texto “Valor Total: {{$vt}}€”, onde “{{$vt}}” representa uma expressão que precisa de ser computada e substituída pelo seu resultado (e.g., “350.00”) de modo a que no corpo do email enviado conste “Valor Total: 350.00€”;

	• A integração e combinação das funcionalidades descritas nos pontos anteriores num único script.


**Esclarecimento adicional por parte do cliente:**

[moodle, 17/05/2021](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=8388)

	"...

	Prevê-se que possam existir formulários simples e outros mais complexos. Como tal, parece fazer sentido uma linguagem mais geral onde se possa verificar, por exemplo:
	Se um atributo está preenchido ou não;
	Verificar se o valor de um atributo está em conformidade com uma determinada expressão regular;
	Aplicar operações lógicas sobre os atributos em conformidade com o seu tipo de dados (numéricas vs. texto);
	Definir diferentes fluxos de validação com base nas operações lógicas realizadas sobre os valores dos atributos;
	Combinação de operações lógicas simples para formar operações lógicas mais complexas; 
	(outras)
	O resultado do script de validação de formulário de ser OK ou NotOK e no caso de ser NotOK deve incluir uma ou mais mensagens de erro.

	No contexto de validações de formulários, o suporte a estruturas repetitivas aparenta ser desnecessário.

	Exemplo (expresso em português e não na linguagem a ser desenvolvida):

	Se CampoA tem o valor "X" então (i) o CampoB tem que estar preenchido e ter um mínimo de 10 caracteres e (ii) os valores admissíveis do CampoC variam entre 0 e 10 (inteiros). Caso o valor do CampoA seja "Y" o campo B pode não ser preenchido mas os valores admissíveis do CampoC variam entre 0 e 20 desde que sejam pares. Caso o valor do Campo A seja diferente de "X", "Y" e "Z" o valor do campoD tem obrigatoriamente que ser maior do que o valor do CampoE e caso o produto dos campo D e E seja superior a K então o campo K tem que ser preenchido (apenas e só neste cenário).

	Acho que se a linguagem desenvolvida suportar a especificação de cenários destes e a combinação destas cenários terá a flexibilidade necessária à sua função.

	..."

<br>

**Scripts tipo a executar**

* Enviar um email ao solicitante com o identificador do pedido a dizer que este se encontra em execução

		enviarEmail("O pedido " $ codigopedido $ " encontra-se em fase de execução", emailcolaborador);

* Ler um ficheiro com emails e códigos de pedido e enviar um email aos colaboradores com os códigos dos pedidos respetivos

		registos = todosRegistos("filepath.csv");
	ou  registos = todosRegistos("filepath.xml");	
	ou  registos = todosRegistos("filepath.xml");	

		paraTodos(registo : registos) 
			if(registo coluna3=="sim")
				enviarEmail("O pedido " $ registo coluna1 $ " encontra-se em fase de execução", registo coluna2);

* Procurar registo onde codigo do produto é igual a "xx" e devolver o seu preço.

		registo = procurarRegisto(coluna1, coluna1 == "12345", "filepath.csv");
	ou 	registo = procurarRegisto(tag.codigo = "12345", "filepath.xml");
	ou 	registo = procurarRegisto(atributo.codigo = "12345", "filepath.xml");

		print(registo coluna1);
	ou	print(registo tag preco);	
	ou	print(registo tag preco);		

# 3. Design

A solução adotada passou pela utilização do ANTLR4. 

Tendo em consideração a análise do caso de uso foram reservadas as seguintes palavras como variaveis reservadas ao sistema:
* codigopedido
* emailcolaborador	

Foi construido um módulo independente ao projeto que comunica com o Formulario através de uma interface definida pelo core. 

# 6. Observações



