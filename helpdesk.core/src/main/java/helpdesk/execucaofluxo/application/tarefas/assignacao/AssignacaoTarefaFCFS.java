package helpdesk.execucaofluxo.application.tarefas.assignacao;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AssignacaoTarefaFCFS implements AlgoritmoAssignacao {

    private static final Logger LOGGER = LogManager.getLogger(AssignacaoTarefaFCFS.class);


    ColaboradorRepository colaboradorRepo = PersistenceContext.repositories().colaboradores();
    TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();

    @Override
    public TarefaManual assignarTarefa(TarefaManual tarefa) {

        try{

            List<Colaborador> colaboradoresPossiveis = possiveisRealizadores(tarefa);
            if(colaboradoresPossiveis.isEmpty()){
                throw new IllegalStateException("Não é possivel assignar a tarefa "+tarefa.identity().toString()+" a nenhum colaborador");
            }
            List<Colaborador> colaboradoresOrdenados = colaboradorHaMaisTempoSemAssignacao(colaboradoresPossiveis);

            Colaborador selecionado = null;
            //se ja foi atribuida uma tarefa a cada colaboradorPossivel entao atribui-se ao que esta
            //ha mais tempo sem receber uma
            if(colaboradoresOrdenados.containsAll(colaboradoresPossiveis)
            && !colaboradoresPossiveis.isEmpty()){
                selecionado = colaboradoresOrdenados.get(0);
            }else{
                //se ainda nao foi atribuida uma a cada, entao atribui-se a um dos que ainda nao recebeu
                for(Colaborador c : colaboradoresPossiveis){
                    if(!colaboradoresOrdenados.contains(c)){
                        selecionado = c;
                        break;
                    }
                }
            }
            tarefa.assignarTarefa(selecionado);
            return tarefa;

        }catch(Exception e){
            LOGGER.error(e);
            return null;
        }

    }

    private List<Colaborador> possiveisRealizadores(TarefaManual tarefa){
        if(!tarefa.assignadaAEquipa()){
            return tarefa.possiveisRealizadores();
        }else{
            Equipa equipa = tarefa.possivelEquipaRealizadora();
            return colaboradorRepo.colaboradoresDaEquipa(equipa.identity());
        }
    }

    private List<Colaborador> colaboradorHaMaisTempoSemAssignacao(List<Colaborador> colaboradores){
        List<NrMecanografico> colabs = new ArrayList<>();
        colaboradores.forEach((c)->colabs.add(c.identity()));
        return colaboradorRepo.haMaisTempoSemTarefaAssignada(colabs);

    }


}
