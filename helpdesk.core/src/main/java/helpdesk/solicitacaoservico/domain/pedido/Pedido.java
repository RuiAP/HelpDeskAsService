package helpdesk.solicitacaoservico.domain.pedido;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.execucaofluxo.domain.tarefas.FormularioPreenchido;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 */

@Entity
public class Pedido implements AggregateRoot<IdentificadorPedido> {

    @Version
    private Long version;

    @EmbeddedId
    private IdentificadorPedido identificador;

    @OneToOne
    private Colaborador solicitante;

    @OneToOne
    private Servico servico;

    @Column
    private LocalDateTime dataPedido;

    @Column
    private LocalDateTime dataResolucaoPretendida;

    @Column
    private UrgenciaPedido urgenciaPedido;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ficheiro> ficheiros;

    @OneToOne(cascade = CascadeType.ALL)
    private FormularioPreenchido formularioSolicitacaoPreenchido;

    public Pedido(String identificadorDoUltimoPedido, Servico servico, Colaborador solicitante) {

        //Preconditions.nonNull(identificadorDoUltimoPedido, "Codigo do último pedido não pode ser nulo");
        //Preconditions.nonEmpty(identificadorDoUltimoPedido, "Codigo do último pedido não pode ser vazio");
        Preconditions.nonNull(servico, "Serviço a solicitar não pode ser nulo");
        Preconditions.nonNull(solicitante, "O solicitante não pode ser nulo");

        this.identificador = new IdentificadorPedido(geraIdentificador(identificadorDoUltimoPedido));
        this.servico = servico;
        this.solicitante=solicitante;

        estadoPedido = EstadoPedido.DRAFT;
        ficheiros = new HashSet<>();
    }

    protected Pedido() {
        //for ORM
    }

    private String geraIdentificador(String identificadorAnterior) {

        LocalDateTime d = LocalDateTime.now();
        int currentYear = d.getYear();

        if(identificadorAnterior==null){
            return currentYear + "/00001";
        }else{
            int n = 0;
            int y = 0;
            try {
                String[] split = identificadorAnterior.split("/");
                y = Integer.parseInt(split[0]);
                n = Integer.parseInt(split[1]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Formato do código incompatível com as regras");
            }

            String codigo = "";
            if (currentYear > y) {
                codigo = currentYear + "/00001";
            } else {
                int l = ((n + 1) + "").length();
                String format = "%0"+(6 - l)+"d";
                String result = String.format(format, (n + 1));

                codigo = currentYear + "/" + result;
            }
            return codigo;
        }

    }

    public boolean submetePedido() {
        boolean res = alterarEstadoPedido(EstadoPedido.SUBMETIDO);
        if (res)
            dataPedido = LocalDateTime.now();

        return res;
    }

    private boolean alterarEstadoPedido(EstadoPedido estadoPedido) {

        Preconditions.nonNull(identificador, "Identificador não pode ser nulo");
        Preconditions.nonNull(solicitante, "Socitante não pode ser nulo");
        Preconditions.nonNull(servico, "Serviço a solicitar não pode ser nulo");
        Preconditions.nonNull(dataResolucaoPretendida, "Data de resolução não pode ser nula");
        Preconditions.nonNull(urgenciaPedido, "Urgência do pedido não pode ser nula");
        Preconditions.nonNull(formularioSolicitacaoPreenchido, "O formulário de solicitação não pode ser nulo");

        this.estadoPedido = estadoPedido;
        return  true;
    }

    public void concluirComSucesso(){
        this.alterarEstadoPedido(EstadoPedido.CONCLUIDO);
    }

    public void concluirSemSucesso(){
        this.alterarEstadoPedido(EstadoPedido.FALHOU_REALIZACAO);
    }

    public void rejeitar(){
        this.alterarEstadoPedido(EstadoPedido.REJEITADO);
    }

    public void iniciarAprovacao(){
        this.alterarEstadoPedido(EstadoPedido.EM_APROVACAO);
    }

    public void iniciarResolucao(){
        this.alterarEstadoPedido(EstadoPedido.EM_RESOLUCAO);
    }


    public List<AtributoPreenchido> atributosPreenchidosNaSolicitacao() {
        return formularioSolicitacaoPreenchido.getAtributosPreenchidos();
    }

    public boolean adicionarFicheiro(String filePath){
        return ficheiros.add(new Ficheiro(filePath));
    }

    public void defineDataResolucaoPretendida(LocalDateTime dataResolucaoPretendida) {
        this.dataResolucaoPretendida = dataResolucaoPretendida;
    }

    public void defineUrgenciaPedido(UrgenciaPedido urgenciaPedido) {
        this.urgenciaPedido = urgenciaPedido;
    }

    public void guardarDadosFormulario(List<AtributoPreenchido> atributosPreenchidos) throws Exception {
        this.formularioSolicitacaoPreenchido = new FormularioPreenchido(atributosPreenchidos);

        if( !formularioSolicitacaoPreenchido.validarDados(this.servico.getFormularioSolicitacao()).foiExecutado())
            throw new Exception("Dados do formulário não cumprem os requisitos");
    }

    public Set<Colaborador> getResponsaveisPorAprovar(){
        if(!this.servico.necessitaAprovacao()) {return new HashSet<>();}

        if(servico.responsavelAprovacao().equals(ResponsavelAprovacao.RESPONSAVELCATALOGO)){
            return servico.colaboradoresResponsaveis();

        }else {//responsável de aprovar é o responsável hierárquico de quem solicitou
            if (solicitante.responsavelHierarquico() != null) {
                Set<Colaborador> colabsResponsaveis = new HashSet<>();
                colabsResponsaveis.add(this.solicitante.responsavelHierarquico());
                return colabsResponsaveis;
            } else {//para lidar com a solicitante não ter responsável
                Set<Colaborador> colabsResponsaveis = new HashSet<>();
                colabsResponsaveis.add(this.solicitante);
                return colabsResponsaveis;
            }
        }
    }


    public boolean emAprovacao(){
        return this.estado().equals(EstadoPedido.EM_APROVACAO);
    }
    public boolean emResolucao(){
        return this.estado().equals(EstadoPedido.EM_RESOLUCAO);
    }
    public EstadoPedido estado(){
        return this.estadoPedido;
    }

    public Servico getServico(){
        return this.servico;
    }

    public Colaborador solicitante(){
        return this.solicitante;
    }


    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public IdentificadorPedido identity() {
        return this.identificador;
    }

    @Override
    public String toString() {
        return "Pedido " +identificador+
                " do serviço '"+this.servico.getTitulo()+" ("+this.servico.identity().toString()+")'" ;
    }
}
