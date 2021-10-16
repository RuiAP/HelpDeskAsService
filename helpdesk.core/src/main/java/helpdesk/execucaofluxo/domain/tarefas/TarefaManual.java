package helpdesk.execucaofluxo.domain.tarefas;

import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.especificacaoservico.domain.servico.Formulario;
import helpdesk.especificacaoservico.domain.servico.TipoDadosBase;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.linguagem.ScriptResult;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import javax.persistence.*;
import java.util.*;

@Entity
public class TarefaManual extends Tarefa{



    @Embedded
    private PossiveisResponsaveis possiveisResponsaveis;

    @OneToOne
    private Colaborador colaboradorResponsavel;

    @OneToOne(cascade = CascadeType.ALL)
    private FormularioPreenchido formularioPreenchido;

    @ElementCollection
    @CollectionTable(name="delegacoes", joinColumns=@JoinColumn(name="codigoTarefa"))
    private List<Delegacao> delegacoes;


    protected TarefaManual(){

    }

    public TarefaManual(Pedido pedido, boolean isAprovacao){
        super(pedido);

        if(isAprovacao){
            Set<Colaborador> colabs = new HashSet<>( pedido.getResponsaveisPorAprovar() );
            this.possiveisResponsaveis = new PossiveisResponsaveis(colabs);

        }else{//tarefa de realização
            if(pedido.getServico().realizacaoFoiAssignadaAColaborador()){
                Set<Colaborador> colabs = new HashSet<>();
                colabs.add( pedido.getServico().responsavelRealizacaoColaborador() );
                this.possiveisResponsaveis = new PossiveisResponsaveis(colabs);
            }else {
                Equipa equipa =  pedido.getServico().responsavelRealizacaoEquipa();
                this.possiveisResponsaveis = new PossiveisResponsaveis(equipa);
            }
        }

        this.delegacoes = new ArrayList<>();
    }


    public ScriptResult validarDadosPreenchidos(Formulario formulario){
        return this.formularioPreenchido.validarDados(formulario);
    }

    public List<AtributoPreenchido> atributosPreenchidos(){
        return this.formularioPreenchido.getAtributosPreenchidos();
    }


    @Override
    public void darPorRealizada(boolean comSucesso){
        if(getPedido().emAprovacao()){
            alterarEstado(EstadoTarefa.CONCLUIDA,colaboradorResponsavel );
            defineResultado(comSucesso? ResultadoTarefa.APROVADO : ResultadoTarefa.REJEITADO);
        }else if(getPedido().emResolucao()) {
            alterarEstado(EstadoTarefa.CONCLUIDA, colaboradorResponsavel);
            defineResultado(comSucesso? ResultadoTarefa.REALIZADO: ResultadoTarefa.NAO_REALIZADO);
        }else{
            throw new IllegalArgumentException("A Tarefa não pode ser realizada, o pedido está em estado "+ getPedido().estado());
        }
    }


    public boolean delegarTarefa(Colaborador colaboradorDelegante, Colaborador colaboradorDelegado, String justificacao){
        //verificar na Atividade se aceita delegação
        if(delegacoes == null){
            delegacoes = new ArrayList<>();
        }
        return delegacoes.add(new Delegacao(colaboradorDelegante, colaboradorDelegado, justificacao));
    }

    public boolean isColaboradorResponsavel(NrMecanografico nrmecanografico) {
        return this.colaboradorResponsavel.identity().equals(nrmecanografico);
    }

    public boolean estaAssignada(){
        return this.colaboradorResponsavel != null;
    }

    public boolean assignadaAEquipa(){
        return this.possiveisResponsaveis.assignadaAEquipa();
    }

    public List<Colaborador> possiveisRealizadores(){
        List<Colaborador> colaboradores = new LinkedList<>();
        colaboradores.addAll(this.possiveisResponsaveis.getColaboradoresPossiveis());
        return colaboradores;
    }

    public Equipa possivelEquipaRealizadora(){
        return this.possiveisResponsaveis.getEquipaPossivel();
    }

    public void assignarTarefa(Colaborador colaborador){

        if(colaboradorResponsavel != null){
            throw new IllegalArgumentException("Esta Tarefa já se econtra assignada. Para mudar responsável da tarefa, esta tem de ser delegada.");

        }else if(this.possiveisResponsaveis.assignadaAEquipa()){
            boolean pertence = colaborador.pertenceAEquipa(this.possivelEquipaRealizadora());
            if(pertence){
                this.colaboradorResponsavel = colaborador;
                this.alterarEstado(EstadoTarefa.ASSIGNADA,colaborador);
            }else{
                throw new IllegalArgumentException("Colaborador não pertence à equipa reponsável por realizar esta Tarefa (Ou não foi definida uma equipa).");
            }

        }else if(possiveisResponsaveis.getColaboradoresPossiveis().contains(colaborador)){
            this.colaboradorResponsavel = colaborador;
            this.alterarEstado(EstadoTarefa.ASSIGNADA, colaborador);
        }else{
            throw new IllegalArgumentException("Colaborador não pertence à equipa responsável pela realização nem é responsável pelo Serviço.");
        }

    }

    public void guardaDadosFormulario(Map<AtributoFormulario, String> valoresPreenchidos) {
        String temp="";
        List<AtributoPreenchido> listaAtributos = new LinkedList<>();
        for(Map.Entry<AtributoFormulario, String> entry : valoresPreenchidos.entrySet()){

            temp = entry.getValue();
            if(entry.getKey().getTipoDadosBase().equals(TipoDadosBase.BOOLEANO)){
                if(temp.matches("s|S|v|V"))
                    temp="true";
                if(temp.matches("n|N|f|F"))
                    temp="false";
            }
            listaAtributos.add(new AtributoPreenchido(entry.getKey().getNomeVariavel(), entry.getKey().getEtiquetaApresentacao(),
                    entry.getKey().getTipoDadosBase(), temp));
        }
        this.formularioPreenchido = new FormularioPreenchido(listaAtributos);
    }



}


