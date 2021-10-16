package helpdesk.app.backoffice.console.presentation.catalogo;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.application.catalogo.CriarCatalogoController;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;

import java.util.HashSet;
import java.util.Set;

public class CriarCatalogoUI extends AbstractUI {

    CriarCatalogoController controller;

    public CriarCatalogoUI(){
        this.controller = new CriarCatalogoController();
    }


    @Override
    protected boolean doShow() {


        System.out.println("Introduza os dados para criar catálogo:");

        final String identificador = Console.readLine("Identificador");

        final String titulo = Console.readLine("Titulo Catálogo");
        final String descricaoBreve = Console.readLine("Descrição Breve");
        final String descricaoCompleta = Console.readLine("Descrição Completa");
        final String icone = Console.readLine("Icone");

        controller.atribuiParametros(identificador, titulo, descricaoBreve, descricaoCompleta, icone);

        solicitaColaboradoresResponsaveis();
        solicitaEquipasComAcesso();

        Iterable<NivelCriticidade> niveis = controller.todosNiveisCriticidade();
        final SelectWidget<NivelCriticidade> selectorCatalogo = new SelectWidget<NivelCriticidade>("Selecione o nível de criticidade\n", niveis);
        selectorCatalogo.show();
        NivelCriticidade nivelCriticidade = selectorCatalogo.selectedElement();
        controller.atribuiNivelCriticidade(nivelCriticidade);

        Catalogo catalogo = controller.build();
        catalogo = controller.guardaCatalogo();
        System.out.println("Catálogo criado com sucesso:");
        System.out.println(catalogo);

        return controller.guardaCatalogo() != null;

    }

    private void solicitaColaboradoresResponsaveis(){

        final Iterable<Colaborador> todosColaboradores = this.controller.listarColaboradores();
        Set<Colaborador> colaboradoresDisponiveis = new HashSet<>();
        todosColaboradores.forEach(colaboradoresDisponiveis::add);

        boolean adicionarOutroColab;
        do{
            final SelectWidget<Colaborador> selectorColaboradores = new SelectWidget<>("Selecione um colaborador responsável:", colaboradoresDisponiveis,
                    new ColaboradorPrinter());
            selectorColaboradores.show();
            Colaborador colaboradorSelecionado = selectorColaboradores.selectedElement();
            if(colaboradorSelecionado != null){
                controller.atribuiColaboradorResponsavel(colaboradorSelecionado);
                colaboradoresDisponiveis.remove(colaboradorSelecionado);
            }

            adicionarOutroColab = Console.readBoolean("Deseja adicionar outro colaborador responsável? (s)Sim ou (n)Não");
        }
        while(adicionarOutroColab);

    }


    private void solicitaEquipasComAcesso(){

        final Iterable<Equipa> todasEquipas = this.controller.listarEquipas();
        Set<Equipa> equipasDisponiveis = new HashSet<>();
        todasEquipas.forEach(equipasDisponiveis::add);


        boolean adicionarOutraEquipa;
        do{

            final SelectWidget<Equipa> selectorEquipas = new SelectWidget<>("Selecione uma equipa com acesso ao catálogo:", todasEquipas,
                    new EquipaPrinter());
            selectorEquipas.show();
            final Equipa equipaSelecionada = selectorEquipas.selectedElement();
            if(equipaSelecionada != null){
                equipasDisponiveis.remove(equipaSelecionada);
                controller.atribuiAcessoAEquipa(equipaSelecionada);
            }

            adicionarOutraEquipa = Console.readBoolean("Deseja dar acesso a outra equipa?");
        }
        while(adicionarOutraEquipa);

    }




    @Override
    public String headline() {
        return "Criar Novo Catálogo";
    }


}
