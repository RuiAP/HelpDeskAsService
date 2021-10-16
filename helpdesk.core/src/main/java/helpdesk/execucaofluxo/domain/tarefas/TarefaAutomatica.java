package helpdesk.execucaofluxo.domain.tarefas;

import helpdesk.especificacaoservico.domain.servico.Script;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class TarefaAutomatica extends Tarefa{



    @Embedded
    private Script scriptRealizacao;

    protected TarefaAutomatica(){
    }

    public TarefaAutomatica(Pedido pedido){
        super(pedido);


        this.scriptRealizacao = new Script(pedido.getServico().getScriptDeRealizacao());
    }

    @Override
    public void darPorRealizada(boolean comSucesso){
        alterarEstado(EstadoTarefa.CONCLUIDA, null);
        defineResultado(comSucesso? ResultadoTarefa.REALIZADO: ResultadoTarefa.NAO_REALIZADO);
    }

    @Override
    public boolean concluidaComSucesso(){
        return this.resultado().equals(ResultadoTarefa.REALIZADO);
    }

    public String scriptDeRealizacao(){
        return this.scriptRealizacao.value();
    }



}
