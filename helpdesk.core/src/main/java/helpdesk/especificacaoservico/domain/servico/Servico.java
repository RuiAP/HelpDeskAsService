package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Servico
 * <p>
 * Esta classe representa um serviço. Implementa Domain Driven Design onde
 * esta classe é a própria base do seu agregado.
 *
 * Um serviço encontra-se completamente especificado quando:
 * 1. Toda a informação necessária para atender pedidos relativos ao mesmo já tiver sido introduzida pelo Gestor de Serviços (e.g. código, título, descrição breve e completa, ícone, palavras-chave, o catálogo a que pertence, o formulário de pedido, outros formulários associados a atividades manuais, a atividade de aprovação caso exista e a atividade de resolução).
 * 2. O Gestor de Serviços confirmar que a especificação está terminada.
 * Nota: o sistema não deve permitir que o Gestor diga que a especificação está terminada se alguma informação necessária ao correto funcionamento do sistema esteja em falta e/ou incongruente entre si.
 *
 * A informação mínima requerida por um formulário é: um identificador único no âmbito do serviço, um nome, pelo menos um atributo (todos os dados de um atributo são obrigatórios) e um script que permita proceder à sua validação.
 *
 * A informação mínima requerida para uma atividade depende do seu tipo: manual ou automática.
 *
 */

@Entity
public class Servico implements AggregateRoot<CodigoServico> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoServico codigoServico;

    @Column
    private Titulo tituloServico;

    @Column
    private Icone icone;

    @Column
    private DescricaoBreve descricaoBreve;

    @Column
    private DescricaoCompleta descricaoCompleta;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Keyword> keywords;

    @OneToOne(optional = true)
    private Catalogo catalogo;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Formulario formularioSolicitacao;

    @Column
    private boolean requerFeedBack;

    @OneToOne(cascade=CascadeType.ALL)
    private Atividade atividadeAprovacao;

    @OneToOne(cascade=CascadeType.ALL)
    private Atividade atividadeRealizacao;


    @Enumerated(EnumType.STRING)
    EstadoEspecificacao estadoEspecificacao;


    public Servico(String codigoServico, String titulo) {
        Preconditions.nonNull(codigoServico, "Codigo do Serviço não pode ser nulo");
        Preconditions.nonEmpty(codigoServico, "Codigo do Serviço não pode ser vazio");
        Preconditions.nonNull(titulo, "Titulo do Serviço não pode ser nulo");
        Preconditions.nonEmpty(titulo, "Titulo do Serviço não pode ser vazio");

        this.codigoServico = new CodigoServico(codigoServico);
        this.tituloServico = new Titulo(titulo);

        keywords = new HashSet<>();

        estadoEspecificacao = EstadoEspecificacao.INCOMPLETO;
    }

    protected Servico() {
        //for ORM
    }



    public String defineEspecificacaoComoCompleto(){

        String msg =  validaEspecificacao();
        if(msg.isEmpty())
            estadoEspecificacao = EstadoEspecificacao.COMPLETO;

        return msg;
    }

    private String validaEspecificacao() {

        String msg ="";

        if(icone==null)
            msg+="Falta definir icone \n";

        if(descricaoBreve==null)
            msg+="Falta definir descrição breve \n";

        if(descricaoCompleta==null)
            msg+="Falta definir descrição completa \n";

        if(keywords.size()==0)
            msg+="Falta definir pelo menos uma keyword \n";

        if(formularioSolicitacao ==null)
            msg+="Falta definir o formulário de solicitação \n";
        else if(!formularioSolicitacao.estaCompleto())
            msg+="Formulário de solicitação incompleto\n";

        if(catalogo==null)
            msg+="Falta definir o catálogo onde se insere o serviço \n";

        if(atividadeRealizacao==null)
            msg+="Falta definir uma atividade de realização \n";

        return  msg;
    }

    public void definirTitulo(String titulo) {

        Preconditions.nonNull(titulo, "Titulo do Serviço não pode ser nulo");
        Preconditions.nonEmpty(titulo, "Titulo do Serviço não pode ser vazio");

        this.tituloServico = new Titulo(titulo);
    }

    public void definirIcone(String icone) {
        this.icone = new Icone(icone);
    }

    public void definirDescricaoBreve(String descricaoBreve) {
        this.descricaoBreve = new DescricaoBreve(descricaoBreve);
    }

    public void definirDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = new DescricaoCompleta(descricaoCompleta);
    }

    public void feedBackObrigatorio(boolean obrigatorio) {
        this.requerFeedBack = obrigatorio;
    }

    public boolean adicionaKeyword(String keyword) {
        return keywords.add(new Keyword(keyword));
    }

    public void associaCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public void associaFormularioSolicitacao(Formulario formulario){
        this.formularioSolicitacao = formulario;
    }




    /**
     * Cria uma atividade de aprovação (atividade manual) e define tipo de responsável
     */
    public void associaAtividadeDeAprovacao(Atividade atividade)
    {
        atividadeAprovacao = atividade;
    }

    /**
     * Criar uma atividade de realização (atividade manual) e define o colaborador Reponsavel de a realizar
     */
    public void associaAtividadeDeRealizacao(Atividade atividade)
    {
        this.atividadeRealizacao = atividade;
    }


    /**
     * Para, durante a criação das Tarefas, validar se requer Atividade de Aprovação
     * @return true se requer atividade de aprovação
     *          false se não requer atividade de aprovação
     */
    public boolean necessitaAprovacao(){
        return this.atividadeAprovacao!=null;
    }

    /**
     * Devolve o responsavel por realizar a Tarefa de Realização
     * @return
     */
    public ResponsavelAprovacao responsavelAprovacao(){
        return this.atividadeAprovacao.getResponsavelPorAprovacao();
    }

    /**
     * Verifica se a Realização  da Tarefa de Realização foi assignada a um colaborador
     * @return true se tiver sido assignada a um Colaborador
     *          false se tiver sido assignada a uma Equipa
     */
    public boolean realizacaoFoiAssignadaAColaborador(){
        if(this.atividadeRealizacao.isAtividadeAutomatica()){
            return false;
        }else{
            return atividadeRealizacao.getReponsavelPorRealizacao().temColaboradorAssignado();
        }
  }

    /**
     * Verifica se a Realização da Tarefa de Realização foi originalmente assignada a uma Equipa (em vez de a um colaborador)
     * @return
     */
    public boolean realizacaoFoiAssignadaAEquipa(){
        if(this.atividadeRealizacao.isAtividadeAutomatica()){
            return false;
        }else {
            return atividadeRealizacao.getReponsavelPorRealizacao().foiAssignadaEquipa();
        }
    }

    /**
     * Devolve o reponsavel pela realização da Atividade de Realizaçao ( no caso de ser um Colaborador)
     * @return
     */
    public Colaborador responsavelRealizacaoColaborador(){
        if(!realizacaoFoiAssignadaAColaborador()){
            throw new IllegalArgumentException("Esta Atividade de Realização não foi assignada a nenhum colaborador.");
        }else{
            return atividadeRealizacao.getReponsavelPorRealizacao().getColaboradorResponsavel();
        }
    }

    /**
     * Devolve o reponsavel pela realização da Atividade de Realizaçao ( no caso de ser uma Equipa)
     * @return
     */
    public Equipa responsavelRealizacaoEquipa(){
      if(!realizacaoFoiAssignadaAEquipa()){
          throw new IllegalArgumentException("Esta Atividade de Realização foi assignada diretamente a um colaborador.");
      }else{
          return atividadeRealizacao.getReponsavelPorRealizacao().getEquipaResponsavel();
      }
    }

    public boolean requerRealizacaoManual(){
      return !atividadeRealizacao.isAtividadeAutomatica();
  }

    public String getScriptDeRealizacao(){
        if(requerRealizacaoManual()){
            throw new IllegalArgumentException("Não existe Script de Realização. O serviço tem uma Atividade de Realização Manual");
        }else{
            return atividadeRealizacao.script();

        }
    }

    public Set<Colaborador> colaboradoresResponsaveis(){
        return this.catalogo.getColaboradoresResponsaveis();
    }


    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoServico);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public CodigoServico identity() {
        return this.codigoServico;
    }

    @Override
    public String toString() {
        String toString =  tituloServico + " (" + codigoServico +")";
        if(estadoEspecificacao == EstadoEspecificacao.INCOMPLETO){
            toString += " *Disponível Brevemente*";
        }
        return toString;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public String getTitulo() {
        return tituloServico !=null? tituloServico.toString():"";
    }

    public String getIcone() {
        return icone!=null?icone.toString():"";
    }

    public String getDescricaoBreve() {
        return descricaoBreve!=null?descricaoBreve.toString():"";
    }

    public String getDescricaoCompleta() {
        return descricaoCompleta!=null?descricaoCompleta.toString():"";
    }

    public Formulario getFormularioSolicitacao() {
        return formularioSolicitacao;
    }

    public Formulario getFormularioAprovacao() {return this.atividadeAprovacao.getFormularioAtividade();}

    public Formulario getFormularioRealizacao() {return this.atividadeRealizacao.getFormularioAtividade();}

    public Atividade getAtividadeAprovacao() {
        return atividadeAprovacao;
    }

    public Atividade getAtividadeRealizacao() {
        return atividadeRealizacao;
    }

}
