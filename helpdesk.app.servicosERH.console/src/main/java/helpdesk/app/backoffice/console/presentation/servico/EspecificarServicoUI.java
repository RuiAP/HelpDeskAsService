package helpdesk.app.backoffice.console.presentation.servico;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.application.servico.AtividadeBuilder;
import helpdesk.especificacaoservico.application.servico.EspecificarServicoController;
import helpdesk.especificacaoservico.application.servico.FormularioBuilder;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EspecificarServicoUI extends AbstractUI {

    Servico servico;
    EspecificarServicoController controller;
    FormularioBuilder formularioBuilder;
    AtividadeBuilder atividadeBuilder;
    boolean editar;

    public EspecificarServicoUI(boolean editar) {
        this.controller = new EspecificarServicoController();
        this.editar = editar;
    }

    @Override
    protected boolean doShow() {

        try {
            System.out.println("Introduza os dados para criar serviço: (Para sair escreva quit)");

            Servico servico = especificarServico();
            AskForSave();

            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return true;
        }
    }

    private Servico especificarServico() throws Exception {


        if (editar) {
            servico = escolherServico();
            final String titulo = readNonemptyLine("Titulo", servico.getTitulo());
            servico.definirTitulo(titulo);
        } else {
            final String codigo = readNonemptyLine("Código", "");
            final String titulo = readNonemptyLine("Titulo", "");
            servico = controller.criaServico(codigo, titulo);
        }

        final String descricaoBreve = readNonemptyLine("Descrição Breve", servico == null ? "" : servico.getDescricaoBreve());
        servico.definirDescricaoBreve(descricaoBreve);
        final String descricaoCompleta = readNonemptyLine("Descrição Completa", servico == null ? "" : servico.getDescricaoCompleta());
        servico.definirDescricaoCompleta(descricaoCompleta);

        final String icone = readNonemptyLine("Icone", servico == null ? "" : servico.getIcone());
        servico.definirIcone(icone);

        final boolean feedback = Console.readBoolean("Requer feedback? (s)Sim ou (n)Não");
        servico.feedBackObrigatorio(feedback);

        while (Console.readBoolean("Adicionar keyword? (s)Sim ou (n)Não")) {
            final String keyword = readNonemptyLine("Keyword", "");
            servico.adicionaKeyword(keyword);
        }

        associarCatalogo();

        Formulario form = null;
        if (Console.readBoolean("Deseja criar/editar formulário de solitação? (s)Sim ou (n)Não")) {
            form = especificarFormulario(servico == null ? null : servico.getFormularioSolicitacao(), true);
            servico.associaFormularioSolicitacao(form);
        }

        atividadeBuilder = new AtividadeBuilder();
        if (Console.readBoolean("Deseja criar/editar atividade de aprovação? (s)Sim ou (n)Não")) {
            final SelectWidget<ResponsavelAprovacao> selector = new SelectWidget<ResponsavelAprovacao>("Selecione a entidade que aprova o serviço\n", Arrays.asList(ResponsavelAprovacao.values()));
            selector.show();
            ResponsavelAprovacao entidade = selector.selectedElement();
            if (Console.readBoolean("Deseja criar/editar formulário de atividade de aprovação? (s)Sim ou (n)Não")) {

                form = especificarFormulario(servico != null && servico.getAtividadeAprovacao() != null ? servico.getAtividadeAprovacao().getFormularioAtividade() : null, false);
                servico.associaAtividadeDeAprovacao(atividadeBuilder.buildAtividadeManual(entidade, form));
            }
        }

        if (Console.readBoolean("Deseja criar/editar atividade de realização? (s)Sim ou (n)Não")) {
            ArrayList<String> tiposAtividade = new ArrayList<>();
            tiposAtividade.add("Atividade Manual");
            tiposAtividade.add("Atividade Automática");
            final SelectWidget<String> selector = new SelectWidget<String>("Selecione o tipo de atividade\n", tiposAtividade);
            selector.show();
            String tipo = selector.selectedElement();
            if (tipo.equals(tiposAtividade.get(0))) {
                form = especificarFormulario(servico != null && servico.getAtividadeRealizacao() != null ? servico.getAtividadeRealizacao().getFormularioAtividade() : null,false);


                ArrayList<String> realizadores = new ArrayList<>();
                realizadores.add("Colaborador");
                realizadores.add("Equipa");
                final SelectWidget<String> selectorRealizador = new SelectWidget<String>("Selecione quem vai realizar a atividade\n", realizadores);
                selectorRealizador.show();
                String realizador = selectorRealizador.selectedElement();

                Colaborador c = null;
                Equipa e = null;
                if (realizador.equals(realizadores.get(0))) {
                    c = escolherColaborador();
                    servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeManual(c, form));
                } else if (realizador.equals(realizadores.get(1))) {
                    e = escolherEquipa();
                    servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeManual(e, form));
                }
            } else if (tipo.equals(tiposAtividade.get(1))) {
                final String s = readNonemptyLine("Insira Script para realização da atividade", "");
                servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeAutomatica(s));
            }
        }

        return servico;
    }

    private Equipa escolherEquipa() {
        Iterable<Equipa> equipas = controller.todasEquipas();
        final SelectWidget<Equipa> selector = new SelectWidget<Equipa>("Selecione a equipa responsável por executar a atividade\n", equipas);
        selector.show();
        Equipa equipa = selector.selectedElement();
        return equipa;
    }


    private Colaborador escolherColaborador() {
        Iterable<Colaborador> colaboradores = controller.todosColaboradores();
        final SelectWidget<Colaborador> selector = new SelectWidget<Colaborador>("Selecione o colaborador que irá executar a atividade\n", colaboradores);
        selector.show();
        Colaborador colaborador = selector.selectedElement();
        return colaborador;
    }

    private void associarCatalogo() {
        Iterable<Catalogo> catalogos = controller.todosCatalogos();
        final SelectWidget<Catalogo> selectorCatalogo = new SelectWidget<Catalogo>("Selecione o catálogo onde se insere\n", catalogos);
        selectorCatalogo.show();
        Catalogo catalogo = selectorCatalogo.selectedElement();
        servico.associaCatalogo(catalogo);
    }

    private Servico escolherServico() throws Exception {
        Iterable<Servico> servicos = controller.servicosIncompletos();
        final SelectWidget<Servico> selector = new SelectWidget<Servico>("Selecione o serviço para continuar a especificação\n", servicos);
        selector.show();
        return selector.selectedElement();
    }

    private Formulario especificarFormulario(Formulario formulario, boolean isFormularioSolicitacao) throws Exception {

        formularioBuilder = new FormularioBuilder();
        String codigoForm = "";
        String tituloForm = "";
        String script = "";
        if (formulario != null) {
            codigoForm = formulario.getIdentificador();
            tituloForm = formulario.getTitulo();
            script = formulario.getScript();
        } else {
            codigoForm = readNonemptyLine("Identificador do formulário", codigoForm);
        }
        tituloForm = readNonemptyLine("Título do formulário", tituloForm);
        script = readNonemptyLine("Script", script);
        formularioBuilder = formularioBuilder.withParametros(codigoForm, tituloForm, script);

        while (Console.readBoolean("Adicionar atributo ao formulário? (s)Sim ou (n)Não")) {
            final String nomeVariavel = readNonemptyLine("Nome da variável", "");
            final String etiquetaApresentacao = readNonemptyLine("Etiqueta de apresentação", "");
            final String descricaoAjuda = readNonemptyLine("Descrição de ajuda", "");
            final SelectWidget<TipoDadosBase> selector = new SelectWidget<TipoDadosBase>("Selecione o tipo de dados\n", Arrays.asList(TipoDadosBase.values()));
            selector.show();
            TipoDadosBase tipoDadosBase = selector.selectedElement();
            Set<String> options = new HashSet<String>();
            if (tipoDadosBase.equals(TipoDadosBase.SELECAOVALORES)) {
                while (Console.readBoolean("Adicionar opção de resposta ao campo de formulário? (s)Sim ou (n)Não")) {
                    String opt = readNonemptyLine("Insira a opção", "");
                    options.add(opt);
                }
            }
            final String expressaoRegular = readNonemptyLine("Expressão de validação", "");
            final boolean preenchimentoObrigatorio = Console.readBoolean("Preenchimento obrigatório? (s)Sim ou (n)Não");

            if(options.size()>0){
                if (!formularioBuilder.withAtributoFormulario(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase, expressaoRegular, preenchimentoObrigatorio, options))
                    System.out.println("Atributo já existente!");
            }else {
                if (!formularioBuilder.withAtributoFormulario(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase, expressaoRegular, preenchimentoObrigatorio))
                    System.out.println("Atributo já existente!");
            }


        }
        return formularioBuilder.buildFormulario(isFormularioSolicitacao);
    }

    private void AskForSave() throws Exception {

        boolean completa = Console.readBoolean("Deseja dar especificação como completa para publicar? (s)Sim ou (n)Não");
        if (completa) {
            String msg = controller.terminaEspecificacaoServico(servico);
            if (msg.isEmpty())
                System.out.println("Especificação completa");
            else
                System.out.println("Especificação não completa \n" + msg);
        }

        boolean save = Console.readBoolean("Deseja guardar o serviço? (s)Sim ou (n)Não");
        if (save) {
            if (controller.guardaServico(servico) == null)
                throw new Exception("Serviço não guardado.");
            else
                throw new Exception("Serviço guardado.");
        } else {
            throw new Exception("Sair da especificação sem guardar.");
        }
    }

    @Override
    public String headline() {
        return "Especificar Serviço";
    }

    private String readNonemptyLine(String prompt, String original) throws Exception {

        String res = "";
        if (original.isEmpty())
            res = Console.readNonEmptyLine(prompt, "O campo não pode ser vazio.");
        else
            res = Console.readLine(prompt + " (" + original + ")");

        if (res.equals("quit")) {
            AskForSave();
        }

        if (!res.trim().isEmpty() && !res.equals(original))
            return res;
        else
            return original;
    }


}
