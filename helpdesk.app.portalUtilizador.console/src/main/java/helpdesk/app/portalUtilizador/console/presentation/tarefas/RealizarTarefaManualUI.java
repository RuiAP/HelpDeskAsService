package helpdesk.app.portalUtilizador.console.presentation.tarefas;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.especificacaoservico.domain.servico.TipoDadosBase;
import helpdesk.execucaofluxo.application.tarefas.RealizarTarefaManualController;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.linguagem.ScriptResult;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;
;

import java.util.*;


public class RealizarTarefaManualUI extends AbstractUI {

    private final RealizarTarefaManualController controller = new RealizarTarefaManualController();


    @Override
    protected boolean doShow() {

        Tarefa tarefaSelecionada;
        ScriptResult resultadoScript;
        boolean comSucesso = false;

        do {

            Iterable<Tarefa> tarefas = controller.tarefasAssignadasAoColabAtual();
            List<Tarefa> listaTarefas = new ArrayList<>();
            tarefas.forEach(listaTarefas::add);
            listaTarefas.sort(Comparator.comparingInt(Tarefa::prioridade));

            final SelectWidget<Tarefa> selectorTarefa = new SelectWidget<>("Selecione a Tarefa a Realizar:\n", listaTarefas, new TarefaPrinter());
            selectorTarefa.show();
            tarefaSelecionada = selectorTarefa.selectedElement();

            if (tarefaSelecionada == null) {
                return false;
            }

            List<AtributoPreenchido> atributoPreenchidos = tarefaSelecionada.getPedido().atributosPreenchidosNaSolicitacao();
            System.out.println("\n Formulário de Solicitação");
            System.out.println(tarefaSelecionada.getPedido().getServico().getFormularioSolicitacao().getTitulo());
            for (AtributoPreenchido a : atributoPreenchidos) {
                System.out.println(a.getEtiquetaApresentacao() + ": " + a.getValue());
            }
            System.out.println();
            System.out.println();

            List<AtributoFormulario> setAtributos;
            if (tarefaSelecionada.getPedido().emAprovacao()) {
                setAtributos = tarefaSelecionada.getPedido().getServico().getFormularioAprovacao().getAtributosFormulario();
                System.out.println("Preencher Formulário de Aprovação \n");
            } else if (tarefaSelecionada.getPedido().emResolucao()) {
                setAtributos = tarefaSelecionada.getPedido().getServico().getFormularioRealizacao().getAtributosFormulario();
                System.out.println("Preencher Formulário de Realização \n");
            } else {
                System.out.println("Pedido selecionada encontra-se em estado " + tarefaSelecionada.getPedido().estado() + ". Operação cancelada.");
                return false;
            }

            final Map<AtributoFormulario, String> valoresPreenchidos = new HashMap<>();
            String value = "";
            for (AtributoFormulario atributo : setAtributos) {

                if (!atributo.getTipoDadosBase().equals(TipoDadosBase.SELECAOVALORES)) {
                    value = Console.readLine(atributo.getEtiquetaApresentacao().toString() + ": " + atributo.getDescricaoAjuda());
                    if (atributo.getNomeVariavel().toString().equals("decisao")) {
                        comSucesso = value.equalsIgnoreCase("s");
                    }
                } else {
                    final SelectWidget<String> selector = new SelectWidget<String>("Selecione uma das opções:\n", atributo.getOptionValues());
                    selector.show();
                    value = selector.selectedElement();
                }
                valoresPreenchidos.put(atributo, value);
            }


            resultadoScript = controller.validarDadosPreenchidos(valoresPreenchidos, (TarefaManual) tarefaSelecionada);
            if (!resultadoScript.foiExecutado()) //nao executado
                System.out.println("Erro ao executar script de validação.");
            else if (!resultadoScript.executouComSucesso()) { //executado mas não aprovado
                System.out.println("Erro de preenchimento: " + resultadoScript.getMensagem());
            } else {//executado com sucesso
                System.out.println("Script de validação executado com sucesso.");
            }

        }
        while (!resultadoScript.foiExecutado() || !resultadoScript.executouComSucesso());


        try {
            //a realizacao com sucesso (ou não) é determinada pelo conteúdo do atributo "decisão"
            controller.concluirRealizacao(tarefaSelecionada, comSucesso);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao realizar Tarefa. Operação cancelada.");
            return false;
        }

    }


    @Override
    public String headline() {
        return "Realizar Tarefa";
    }
}

