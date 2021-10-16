package helpdesk.execucaofluxo.domain.tarefas;

import javax.persistence.*;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Tarefa implements AggregateRoot<CodigoTarefa> {


    @EmbeddedId
    private CodigoTarefa codigoTarefa;


    @Version
    private long version;



    @OneToOne(fetch = FetchType.LAZY)
    private Pedido pedido;


    @Enumerated(EnumType.STRING)
    private ResultadoTarefa resultadoTarefa;

    @Embedded
    private PrioridadeTarefa prioridadeTarefa;

    @Transient
    private final int PRIORIDADE_DEFAULT = 6;


    @ElementCollection
    @CollectionTable()
    private List<HistoricoTarefa> historicoTarefa;


    protected Tarefa() {
    }

    protected Tarefa(Pedido pedido){
        Preconditions.nonNull(pedido,"Não é possível criar uma Tarefa sem estar associada a um pedido válido.");
        this.codigoTarefa = new CodigoTarefa();
        this.prioridadeTarefa = new PrioridadeTarefa(PRIORIDADE_DEFAULT);
        this.pedido = pedido;
        this.historicoTarefa = new ArrayList<>();
        this.historicoTarefa.add(new HistoricoTarefa(null, EstadoTarefa.PENDENTE));

    }


    public void alterarEstado(EstadoTarefa estadoTarefa, Colaborador colaborador){
        this.historicoTarefa.add(new HistoricoTarefa(colaborador, estadoTarefa));
    }

    public void darPorRealizada(boolean comSucesso){
        if(this.pedido.emAprovacao()){
            alterarEstado(EstadoTarefa.CONCLUIDA,null);
            defineResultado(comSucesso? ResultadoTarefa.APROVADO : ResultadoTarefa.REJEITADO);

        }else if(this.pedido.emResolucao()) {
            alterarEstado(EstadoTarefa.CONCLUIDA, null);
            defineResultado(comSucesso? ResultadoTarefa.REALIZADO: ResultadoTarefa.NAO_REALIZADO);

        }else{
            throw new IllegalArgumentException("A Tarefa não pode ser realizada ,o pedido está em estado "+ this.pedido.estado());
        }
    }

    public void defineResultado(ResultadoTarefa resultadoTarefa){
        this.resultadoTarefa = resultadoTarefa;
    }

    public void atribuiPrioridade(int prioridade){
        this.prioridadeTarefa = new PrioridadeTarefa(prioridade);
    }
    public int prioridade(){
        return this.prioridadeTarefa.value();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public EstadoTarefa estado(){
       return  historicoTarefa.get(historicoTarefa.size()-1).getEstadoTarefa();
    }

    public ResultadoTarefa resultado(){
        return this.resultadoTarefa;
    }

    public String dadosDashboard(){
        String separador = "--";
        StringBuilder sb = new StringBuilder();

        sb.append(codigoTarefa);
        sb.append(separador);
        EstadoTarefa estado = historicoTarefa.get(historicoTarefa.size()-1).getEstadoTarefa();
        sb.append(estado);
        sb.append(separador);
        sb.append(prioridadeTarefa);
        return sb.toString();
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public CodigoTarefa identity() {
        return this.codigoTarefa;
    }

    public boolean concluidaComSucesso(){
        return this.resultado().equals(ResultadoTarefa.APROVADO)
                || this.resultado().equals(ResultadoTarefa.REALIZADO);
    }

    public String infoBasica(){
        String s =  "Código: "+codigoTarefa + " - ";
        if(pedido.emAprovacao()){
            s += "Tarefa de aprovação.";
        }else{
            s += "Tarefa de realização.";
        }
        return s+= " Estado: "+this.estado()+". Prioridade: "+this.prioridadeTarefa+
                ". Pedido "+this.pedido.identity().toString()+
                " do serviço "+ this.pedido.getServico().identity().toString() +".";

    }

    @Override
    public String toString() {
        return "Tarefa "+ this.codigoTarefa;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(codigoTarefa, tarefa.codigoTarefa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoTarefa);
    }
}
