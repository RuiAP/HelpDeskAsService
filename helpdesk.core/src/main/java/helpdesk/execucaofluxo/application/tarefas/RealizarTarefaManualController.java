package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.linguagem.ScriptResult;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;

import java.util.Map;

public class RealizarTarefaManualController {

    private final ListarTarefaService tarefaService = new ListarTarefaService();
    private final TarefaRepository  tarefaRepo = PersistenceContext.repositories().tarefas();

    public RealizarTarefaManualController() {
    }



    public Iterable<Tarefa> tarefasAssignadasAoColabAtual(){
        NrMecanografico numero = new ListarColaboradorService().colaboradorAtual().identity();
        return tarefaService.tarefasNaoConcluidasByColaborador(numero.toString());

    }

    public ScriptResult validarDadosPreenchidos(Map<AtributoFormulario, String> valoresPreenchidos, TarefaManual tarefaManual) {
        tarefaManual.guardaDadosFormulario(valoresPreenchidos);
        return validarDados(tarefaManual);
    }


    private ScriptResult validarDados(TarefaManual tarefa){
        if(tarefa.getPedido().emAprovacao()){
            return tarefa.validarDadosPreenchidos( tarefa.getPedido().getServico().getFormularioAprovacao() );
        }else{
            return tarefa.validarDadosPreenchidos( tarefa.getPedido().getServico().getFormularioRealizacao() );

        }
    }

    public String concluirRealizacao(Tarefa tarefa, boolean comSucesso){
        tarefa.darPorRealizada(comSucesso);
        Tarefa t =  tarefaRepo.save(tarefa);
        return avisarMotorFluxo(t);
    }

    private String avisarMotorFluxo(Tarefa tarefa){
        return MotorFluxoClient.getInstance().realizarTarefa(tarefa.identity().toString()) ;
    }



}
