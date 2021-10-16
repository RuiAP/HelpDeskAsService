package helpdesk.app.portalUtilizador.console.presentation.servicos;


import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.solicitacaoservico.application.pedido.ConsultarServicosController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class PesquisarServico extends AbstractUI {



    private final ConsultarServicosController controller = new ConsultarServicosController();


    @Override
    protected boolean doShow() {

        /*
        System.out.println("Teste pesquisa por keyword 'desp'(SUBFAT1 contem 'despesas'):");
        Iterable<Servico> servicos = controller.servicosByKeyword("desp");
        servicos.forEach(s->System.out.println("-- "+s));
        System.out.println("-------------------------------------------");


        System.out.println("Teste pesquisa por keyword 'keywordMutua'(SUBFAT1 + ALT_NIB1):");
        servicos = controller.servicosByKeyword("keywordMutua");
        servicos.forEach(s->System.out.println("-- "+s));
        System.out.println("-------------------------------------------");


        System.out.println("Teste pesquisa por Titulo 'Subm':");
        servicos = controller.servicosByTitulo("Subm");
        servicos.forEach(s->System.out.println("-- "+s));
        System.out.println("-------------------------------------------");
        */


        Servico servicoSelecionado = null;

        while(servicoSelecionado == null){

            System.out.println(">> Pesquisar Serviços por:");
            System.out.println("1. Keyword");
            System.out.println("2. Título");
            System.out.println("3. Código");
            System.out.println("0. Voltar");
            int option = Console.readOption(1,3,0);
            if(option == 0){
                return false;
            }

            String input;
            Iterable<Servico> resultados;



            switch (option){
                case 1:
                    input = Console.readNonEmptyLine("Introduza a keyword a procurar:", "Keyword vazia!");
                    resultados = controller.servicosPermitidosByKeyword(input);
                    break;
                case 2:
                    input = Console.readNonEmptyLine("Introduza o título a procurar:", "Título vazio!");
                    resultados = controller.servicosPermitidosByTitulo(input);

                    break;
                case 3:
                    input = Console.readNonEmptyLine("Introduza o código a procurar:", "Código vazio!");
                    Set<Servico> s = new HashSet<>();
                    controller.servicoById(input).ifPresent(s::add);
                    resultados = s;
                    break;
                default:
                    return false;
            }
            servicoSelecionado = solicitarEscolha(resultados, input);
        }


        SolicitarServicoUI solicitarUi = new SolicitarServicoUI();
        solicitarUi.escolheServico(servicoSelecionado);
        solicitarUi.show();
        return true;

    }



    private Servico solicitarEscolha(Iterable<Servico> listaServicos, String input){
        if(!listaServicos.iterator().hasNext()){
            System.out.println("-- Sem resultados para \""+input+"\" --\n\n");
            return null;
        }else{
            final SelectWidget<Servico> selectorServicos = new SelectWidget("Selecione o serviço a solicitar:", listaServicos);
            selectorServicos.show();
            return selectorServicos.selectedElement();
        }

    }




    @Override
    public String headline() {
        return "Pesquisar Serviços";
    }
}
