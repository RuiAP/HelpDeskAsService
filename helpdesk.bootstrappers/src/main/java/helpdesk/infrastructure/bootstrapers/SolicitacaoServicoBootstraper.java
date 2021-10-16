package helpdesk.infrastructure.bootstrapers;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Invariants;
import helpdesk.especificacaoservico.application.servico.ListarServicoService;
import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.solicitacaoservico.application.pedido.SolicitarServicoController;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.domain.pedido.UrgenciaPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SolicitacaoServicoBootstraper implements Action {

    public SolicitacaoServicoBootstraper() {
    }

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    private final SolicitarServicoController solicitarController = new SolicitarServicoController();
    private final ListarServicoService srvListarServicos = new ListarServicoService();


    @Override
    public boolean execute() {
        System.out.println("\nServicos solicitados:");

//        authenticateForBootstrapping("1001");
//        solicitarServicoSUBFAT1();

        authenticateForBootstrapping("1002");
        solicitarServicoPEDFALTA();

        authenticateForBootstrapping("1001");
        solicitarServicoAUTDESC();

//        authenticateForBootstrapping("1001");
//        solicitarServicoALTRES();

        authenticateForBootstrapping("1001");
        for(int i = 0; i< 10; i++){
            solicitarServicoVENDGROSSO();
        }

        return true;
    }


    protected void solicitarServicoSUBFAT1(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("SUBFAT1").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0), "1500");//total pago
        solicitarController.preencheAtributoFormulario(atributos.get(1), "2");//numero faturas
        solicitarController.preencheAtributoFormulario(atributos.get(2), "imagem faturas");//faturas
        solicitarController.preencheAtributoFormulario(atributos.get(3), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));//data
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-07-03T11:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.URGENTE);
        pedido.adicionarFicheiro("./randomFile.txt");
        this.submeterPedido(pedido);
    }




    protected void solicitarServicoPEDFALTA(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("PEDFALTA").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0),"28/06/2021" );
        solicitarController.preencheAtributoFormulario(atributos.get(1),"30/06/2021" );
        solicitarController.preencheAtributoFormulario(atributos.get(2),"Justificada" );
        solicitarController.preencheAtributoFormulario(atributos.get(3),"Tem dias de ferias suficientes." );
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-12-12T11:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.URGENTE);
        this.submeterPedido(pedido);
    }

    protected void solicitarServicoAUTDESC(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("AUTDESC").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0),"CLT223" );//codigo cliente
        solicitarController.preencheAtributoFormulario(atributos.get(1),"Miguel Antunes" );//nome
        solicitarController.preencheAtributoFormulario(atributos.get(2),"Desconto por quantidade" );//tipo
        solicitarController.preencheAtributoFormulario(atributos.get(3),"Unica" );//recorrencia
        solicitarController.preencheAtributoFormulario(atributos.get(4),"20" );//percentagem desconto
        solicitarController.preencheAtributoFormulario(atributos.get(5),"" );//valor desconto
        solicitarController.preencheAtributoFormulario(atributos.get(6),"FR4658" );//id da fatura
        solicitarController.preencheAtributoFormulario(atributos.get(7),"07/07/2021" );//data limite de apilcacao
        solicitarController.preencheAtributoFormulario(atributos.get(8),"Compra superior a 20mil unidades" );//fundamentacao
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-07-05T12:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.MODERADA);
        this.submeterPedido(pedido);
    }

    protected void solicitarServicoALTRES(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("ALTRES").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0),"Rua das Flores, n121" );//morada
        solicitarController.preencheAtributoFormulario(atributos.get(1),"4050-266" );//codigo postal
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-06-27T18:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.REDUZIDA);
        this.submeterPedido(pedido);
    }

    protected void solicitarServicoVENDGROSSO(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("VENDGROSSO").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0),"XYJ234" );//codigo produto
        solicitarController.preencheAtributoFormulario(atributos.get(1),"35" );//quantidade pretendida
        solicitarController.preencheAtributoFormulario(atributos.get(2), "Nacional" );//tipo cliente (Nacional/Europeu/Resto do Mundo)
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-09-20T18:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.REDUZIDA);
        this.submeterPedido(pedido);
    }

    protected void solicitarVarianteSUBFAT1_1(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("SUBFAT1").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0), "100");//total pago
        solicitarController.preencheAtributoFormulario(atributos.get(1), "1");//numero faturas
        solicitarController.preencheAtributoFormulario(atributos.get(2), "imagem faturas");//faturas
        solicitarController.preencheAtributoFormulario(atributos.get(3), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));//data
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-07-10T11:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.MODERADA);
        pedido.adicionarFicheiro("./randomFile.txt");
        this.submeterPedido(pedido);
    }


    protected void solicitarVarianteSUBFAT1_2(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("SUBFAT1").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0), "777");//total pago
        solicitarController.preencheAtributoFormulario(atributos.get(1), "5");//numero faturas
        solicitarController.preencheAtributoFormulario(atributos.get(2), "imagem faturas");//faturas
        solicitarController.preencheAtributoFormulario(atributos.get(3), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));//data
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-08-03T11:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.URGENTE);
        pedido.adicionarFicheiro("./randomFile.txt");
        this.submeterPedido(pedido);
    }


    protected void solicitarVarianteSUBFAT1_3(){
        Pedido pedido = solicitarController.escolheServico(srvListarServicos.servicoById("SUBFAT1").get());
        List<AtributoFormulario> atributos = solicitarController.dadosFormularioAPreencher();
        solicitarController.preencheAtributoFormulario(atributos.get(0), "5000");//total pago
        solicitarController.preencheAtributoFormulario(atributos.get(1), "3");//numero faturas
        solicitarController.preencheAtributoFormulario(atributos.get(2), "imagem faturas");//faturas
        solicitarController.preencheAtributoFormulario(atributos.get(3), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));//data
        pedido.defineDataResolucaoPretendida(LocalDateTime.parse("2021-12-12T11:00:00"));
        pedido.defineUrgenciaPedido(UrgenciaPedido.REDUZIDA);
        this.submeterPedido(pedido);
    }


    protected void submeterPedido(Pedido pedido){
        try{
            Pedido p = solicitarController.submetePedido(pedido);
            if(p != null){
                p=solicitarController.pedidoPorId(p.identity().toString()).get();
                System.out.println(p+" esta no estado "+p.estado());
            }

        }catch(Exception e){
            System.out.println("<< Erro ao submeter o pedido!! >>");
            System.out.println(e.getMessage());
        }
    }

    protected void authenticateForBootstrapping(String nrMecanografico) {
        authenticationService.authenticate(nrMecanografico, "Password1");
        Invariants.ensure(authz.hasSession());
    }






}
