package helpdesk.infrastructure.bootstrapers;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Invariants;
import helpdesk.especificacaoservico.application.catalogo.ListarCatalogoService;
import helpdesk.especificacaoservico.application.servico.AtividadeBuilder;
import helpdesk.especificacaoservico.application.servico.EspecificarServicoController;
import helpdesk.especificacaoservico.application.servico.FormularioBuilder;
import helpdesk.especificacaoservico.application.servico.ListarServicoService;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.ResponsavelAprovacao;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.especificacaoservico.domain.servico.TipoDadosBase;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;


public class EspecificacaoServicoBootstrapper implements Action {

    private final EspecificarServicoController servicoController = new EspecificarServicoController();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    private final ListarCatalogoService srvListarCatalogos = new ListarCatalogoService();
    private final ListarServicoService srvListarServicos = new ListarServicoService();

    private final ListarEquipaService listarEquipasService = new ListarEquipaService();

    private FormularioBuilder formularioBuilder;
    private AtividadeBuilder atividadeBuilder;

    @Override
    public boolean execute() {

        authenticateForBootstrapping();

        System.out.println("\nServicos especificados:");


        especificarServicoPEDFALTA();
        especificarServicoAUTDESC();
        //especificarServicoALTRES();
        especificarServicoVENDGROSSO();
        //especificarServicoSUBFAT1();
        //especificarServicoIncompleto();//ALT_NIB1

        return true;
    }


    private void especificarServicoPEDFALTA() {

        Optional<Catalogo> catalogo = srvListarCatalogos.catalogoByIdentificador("CAT2021-3");
        if (catalogo.isEmpty()) {
            System.out.println("Nao existem catalogos para poder especificar o servico!");
            return;
        }

        //Dados Gerais
        Servico servico = servicoController.criaServico("PEDFALTA", "Pedido de Ausência Futura");
        servico.definirDescricaoBreve("Informacao de ausência futura");
        servico.definirDescricaoCompleta("Informacao de ausência futura");
        servico.feedBackObrigatorio(false);
        servico.adicionaKeyword("falta");
        servico.definirIcone("ico.ico");

        //Associa Catalogo
        servico.associaCatalogo(catalogo.get());

        //Associa Formulario Solicitacao
        formularioBuilder = new FormularioBuilder();
        formularioBuilder = formularioBuilder.withParametros("FORMFR1_S", "Formulario de Pedido de Ausência",
                "if(data_fim>=data_inicio) { \n" +
                        "\tif(tipo_ausencia==\"Justificada\"){\n" +
                        "\t\treturn justificacao.IsFilled; \n" +
                        "\t} \n" +
                        "\treturn true;\n" +
                        "}\n" +
                        "return false;\n");
        formularioBuilder.withAtributoFormulario("data_inicio",
                "Data de Inicio (dd/mm/aaaa)", "", TipoDadosBase.DATA,
                "", true);
        formularioBuilder.withAtributoFormulario("data_fim",
                "Data de Fim (dd/mm/aaaa)", "", TipoDadosBase.DATA,
                "", true);
        Set<String> optionsJust = new HashSet<>();
        optionsJust.add("Ferias");
        optionsJust.add("Justificada");
        optionsJust.add("Nao Justificada");
        formularioBuilder.withAtributoFormulario("tipo_ausencia",
                "Justificacao", "", TipoDadosBase.SELECAOVALORES,
                "(Ferias|Justificada|Nao Justificada)", false, optionsJust);
        formularioBuilder.withAtributoFormulario("justificacao",
                "Justificacao", "", TipoDadosBase.TEXTO,
                "", false);
        servico.associaFormularioSolicitacao(formularioBuilder.buildFormulario(true));

        //Associa Atividade de Aprovacao
        atividadeBuilder = new AtividadeBuilder();
        formularioBuilder = new FormularioBuilder();
        formularioBuilder.withParametros("FORMFR1_AA", "Formulario de Aprovacao do Servico", "return fundamentacao.IsFilled;");
        formularioBuilder.withAtributoFormulario("decisao",
                "Aprova as faltas? (s) Sim (n) Nao", "", TipoDadosBase.BOOLEANO,
                "", true);
        formularioBuilder.withAtributoFormulario("fundamentacao",
                "Fundamentacao", "", TipoDadosBase.TEXTO,
                "", false);
        servico.associaAtividadeDeAprovacao(atividadeBuilder.buildAtividadeManual(ResponsavelAprovacao.RESPONSAVELCOLABORADOR, formularioBuilder.buildFormulario(false)));

        //Associa Atividade de Realizacao
        atividadeBuilder = new AtividadeBuilder();
        formularioBuilder = new FormularioBuilder();
        formularioBuilder.withParametros("FORMFR1_AR", "Formulario de Realizacao do Servico", "return ferias_totais==(ferias_gozadas_periodo+ferias_gozadas_ano);");
        formularioBuilder.withAtributoFormulario("ferias_gozadas_ano",
                "Dias de ferias ja gozados no ano", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("ferias_gozadas_periodo",
                "Dias de ferias gozados do periodo solicitado", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("ferias_totais",
                "Dias de ferias totais", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_just_ano",
                "Dias de falta justificadas ja ocorridas no ano", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_just_periodo",
                "Dias de faltas justificadas do periodo solicitado", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_just_totais",
                "Dias de faltas justificadas totais", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_n_just_ano",
                "Dias de falta nao justificadas ja ocorridas no ano", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_n_just_periodo",
                "Dias de faltas nao justificadas do periodo solicitado", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("faltas_n_just_totais",
                "Dias de faltas nao justificadas totais", "", TipoDadosBase.NUMERICO,
                "", true);
        formularioBuilder.withAtributoFormulario("comentario",
                "Comentario", "", TipoDadosBase.TEXTO,
                "", false);
        Optional<Equipa> opEquipaSW = listarEquipasService.equipaByCodigoEquipa("TEAMSW");
        servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeManual(opEquipaSW.get(), formularioBuilder.buildFormulario(false)));

        servicoController.terminaEspecificacaoServico(servico);
        servicoController.guardaServico(servico);

        System.out.println(srvListarServicos.servicoById("PEDFALTA").get());
    }

    private void especificarServicoAUTDESC() {

        Optional<Catalogo> catalogo = srvListarCatalogos.catalogoByIdentificador("CAT2021-4");
        if (catalogo.isEmpty()) {
            System.out.println("Nao existem catalogos para poder especificar o servico!");
            return;
        }

        //Dados Gerais
        Servico servico = servicoController.criaServico("AUTDESC", "Autorizacao para Aplicacao de Desconto");
        servico.definirDescricaoBreve("Autorizacao");
        servico.definirDescricaoCompleta("Autorizacao para Aplicacao de Desconto - Colaboradores departamento financeiro (vendas)");
        servico.feedBackObrigatorio(false);
        servico.adicionaKeyword("desconto");
        servico.definirIcone("ico.ico");

        //Associa Catalogo
        servico.associaCatalogo(catalogo.get());

        //Associa Formulario Solicitacao
        formularioBuilder = new FormularioBuilder();
        formularioBuilder = formularioBuilder.withParametros("FORMFR1_S5", "Formulario de autorizacao de desconto",
                "x = 0;\n" +
                        "if(desconto_pct.IsFilled) {\n" +
                        "    if(desconto_valor.IsFilled || (desconto_pct<=0))\n" +
                        "        x=1;\n" +
                        "}else{\n" +
                        "     if(!desconto_valor.IsFilled || (desconto_valor<=0))\n" +
                        "        x=1;\n" +
                        "}\n" +
                        "if(recorrencia==\"Unica\" ){\n" +
                        "    if(!nr_fatura.IsFilled)\n" +
                        "        x=1;\n" +
                        "}\n" +
                        "else\n" +
                        "{\n" +
                        "    if(!data_limite.IsFilled)\n" +
                        "        x=1;\n" +
                        "}\n" +
                        "return x==0;");
        formularioBuilder.withAtributoFormulario("codigo_cliente",
                "Codigo Interno Cliente", "", TipoDadosBase.TEXTO,
                "^[a-zA-Z]{3}\\d{3}$", true);
        formularioBuilder.withAtributoFormulario("nome",
                "Nome", "", TipoDadosBase.TEXTO,
                "", true);
        formularioBuilder.withAtributoFormulario("tipo_desconto",
                "Tipo de Desconto", "", TipoDadosBase.TEXTO,
                "", true);
        Set<String> options = new HashSet<>();
        options.add("Unica");
        options.add("Ate Data Limite");
        formularioBuilder.withAtributoFormulario("recorrencia",
                "Recorrência", "", TipoDadosBase.SELECAOVALORES,
                "(Unica|Ate Data Limite)", true, options);
        formularioBuilder.withAtributoFormulario("desconto_pct",
                "Percentagem de Desconto", "", TipoDadosBase.NUMERICO,
                "", false);
        formularioBuilder.withAtributoFormulario("desconto_valor",
                "Valor de Desconto", "", TipoDadosBase.NUMERICO,
                "", false);
        formularioBuilder.withAtributoFormulario("nr_fatura",
                "Identificacao da Fatura", "", TipoDadosBase.TEXTO,
                "", false);
        formularioBuilder.withAtributoFormulario("data_limite",
                "Data Limite (dd/mm/aaaa)", "", TipoDadosBase.DATA,
                "", false);
        formularioBuilder.withAtributoFormulario("fundamentacao",
                "Fundamentacao do Pedido", "", TipoDadosBase.TEXTO,
                "", true);
        servico.associaFormularioSolicitacao(formularioBuilder.buildFormulario(true));

        //Associa Atividade de Aprovacao
        atividadeBuilder = new AtividadeBuilder();
        formularioBuilder = new FormularioBuilder();
        formularioBuilder.withParametros("FORMFR1_AA2", "Formulario de Aprovacao do Servico",
                "if(confirma_desconto.IsFilled) {\n" +
                        "   return !confirma_data.IsFilled;\n" +
                        "}else{\n" +
                        "   return confirma_data.IsFilled;\n" +
                        "}");
        formularioBuilder.withAtributoFormulario("confirma_desconto",
                "Confirma desconto? (s) Sim (n) Nao", "", TipoDadosBase.BOOLEANO,
                "", false);
        formularioBuilder.withAtributoFormulario("confirma_data",
                "Confirma data limite? (s) Sim (n) Nao", "", TipoDadosBase.BOOLEANO,
                "", false);
        formularioBuilder.withAtributoFormulario("fundamentacao",
                "Fundamentacao", "", TipoDadosBase.TEXTO,
                "", true);
        //formularioBuilder.withAtributoDecisao(); desde que haja um atributo com nome "decisao" e seja boolean serve
        formularioBuilder.withAtributoFormulario("decisao",
                "Aprova a aplicacao do desconto? (s) Sim (n) Nao", "", TipoDadosBase.BOOLEANO,
                "", true);
        servico.associaAtividadeDeAprovacao(atividadeBuilder.buildAtividadeManual(ResponsavelAprovacao.RESPONSAVELCATALOGO, formularioBuilder.buildFormulario(false)));

        //Associa Atividade de Realizacao Automatica
//        servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeAutomatica(
//                "sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" foi aprovado?\" $ decisao $\" com o desconto de \" $ );"));
        servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeAutomatica(

                "if(decisao)\n" +
                        "    sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" foi aprovado.\" );\n" +
                        "else\n" +
                        "    sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" nao foi aprovado.\" );"));

        servicoController.terminaEspecificacaoServico(servico);
        servicoController.guardaServico(servico);

        System.out.println(srvListarServicos.servicoById("AUTDESC").get());
    }

    private void especificarServicoALTRES() {

        Optional<Catalogo> catalogo = srvListarCatalogos.catalogoByIdentificador("CAT2021-3");
        if (catalogo.isEmpty()) {
            System.out.println("Nao existem catalogos para poder especificar o servico!");
            return;
        }

        //Dados Gerais
        Servico servico = servicoController.criaServico("ALTRES", "Comunicacao de Alteracao de Residência");
        servico.definirDescricaoBreve("Alteracao Residência");
        servico.definirDescricaoCompleta("Comunicacao de Alteracao de Residência - Colaboradores departamento financeiro (vendas)");
        servico.feedBackObrigatorio(false);
        servico.adicionaKeyword("residencia");
        servico.adicionaKeyword("morada");
        servico.definirIcone("ico.ico");

        //Associa Catalogo
        servico.associaCatalogo(catalogo.get());

        //Associa Formulario Solicitacao
        formularioBuilder = new FormularioBuilder();
        formularioBuilder = formularioBuilder.withParametros("FORMFR1_S", "Formulario de Comunicacao de Residência",
                "");

        formularioBuilder.withAtributoFormulario("morada",
                "Morada", "", TipoDadosBase.TEXTO,
                "", true);
        formularioBuilder.withAtributoFormulario("codigo_postal",
                "Codigo Postal", "", TipoDadosBase.TEXTO,
                "^\\d{4}-\\d{3}$", true);
        servico.associaFormularioSolicitacao(formularioBuilder.buildFormulario(true));


        //Associa Atividade de Realizacao
        atividadeBuilder = new AtividadeBuilder();
        formularioBuilder = new FormularioBuilder();
        formularioBuilder.withParametros("FORMFR1_AR", "Formulario de Realizacao do Servico", "");

        formularioBuilder.withAtributoFormulario("observacoes",
                "Observacões", "", TipoDadosBase.TEXTO,
                "", true);
        Optional<Equipa> opEquipaRh = listarEquipasService.equipaByCodigoEquipa("TEAMRH");
        servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeManual(opEquipaRh.get(), formularioBuilder.buildFormulario(false)));


        servicoController.terminaEspecificacaoServico(servico);
        servicoController.guardaServico(servico);

        System.out.println(srvListarServicos.servicoById("ALTRES").get());
    }

    private void especificarServicoVENDGROSSO() {

        Optional<Catalogo> catalogo = srvListarCatalogos.catalogoByIdentificador("CAT2021-4");
        if (catalogo.isEmpty()) {
            System.out.println("Nao existem catalogos para poder especificar o servico!");
            return;
        }

        //Dados Gerais
        Servico servico = servicoController.criaServico("VENDGROSSO", "Requerer cotação para venda por grosso");
        servico.definirDescricaoBreve("Venda por grosso");
        servico.definirDescricaoCompleta("Requerer cotação para venda por grosso");
        servico.feedBackObrigatorio(false);
        servico.adicionaKeyword("grosso");
        servico.adicionaKeyword("venda");
        servico.definirIcone("ico.ico");

        //Associa Catalogo
        servico.associaCatalogo(catalogo.get());

        //Associa Formulario Solicitacao
        formularioBuilder = new FormularioBuilder();
        formularioBuilder = formularioBuilder.withParametros("FORMFR1_S", "Formulario de Requerer cotacao para Venda",
                "if((quantidade%1)>0){\n" +
                        " sub = getSubString(0,1,codigoproduto);" +
                        "    return ((sub==20) || (sub==21));\n" +
                        "}\n" +
                        "return true;");

        formularioBuilder.withAtributoFormulario("codigoproduto",
                "Codigo do produto", "", TipoDadosBase.TEXTO,
                "", true);
        formularioBuilder.withAtributoFormulario("quantidade",
                "Quantidade pretendida", "", TipoDadosBase.NUMERICO,
                "^\\d{4}-\\d{3}$", true);
        Set<String> options = new HashSet<>();
        options.add("Nacional");
        options.add("Europeu");
        options.add("Resto do Mundo");
        formularioBuilder.withAtributoFormulario("tipocliente",
                "Tipo de cliente", "", TipoDadosBase.SELECAOVALORES,
                "", true, options);
        servico.associaFormularioSolicitacao(formularioBuilder.buildFormulario(true));

        //Associa Atividade de Realizacao
        Random gerador = new Random();
        int number = gerador.nextInt() % 10000;
        int n = number < 0 ? number * -1 + 10000: number+ 10000;
        servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeAutomatica(
                "item = findFirstRecord(tag \"id\", (tag \"id\") == codigoproduto, \"helpdesk.interpretador.impl\\fileService4.xml\");\n" +
                        "preco = (item tag \"price\");\n" +
                        "categoria = (item tag \"category\");\n" +
                        "precoTotal = preco*quantidade;\n" +
                        "if(precoTotal>500){\n" +
                        "    desconto = 3/100;\n" +
                        "}else{\n" +
                        "    desconto = 1/100;\n" +
                        "}\n" +
                        "if(categoria == \"ABC\")\n" +
                        "    desconto = desconto + 5/1000;\n" +
                        "\n" +
                        "valorDesconto =  desconto*precoTotal;\n" +
                        "valorTotal = precoTotal - valorDesconto;\n" +
                        "sleep("+n+");" +
                        "sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" custava \" $ precoTotal $ \" e com um desconto de \" $ valorDesconto $ \" ficou por \" $ valorTotal);"));


        servicoController.terminaEspecificacaoServico(servico);
        servicoController.guardaServico(servico);

        System.out.println(srvListarServicos.servicoById("VENDGROSSO").get());
    }

//    private void especificarServicoSUBFAT1() {
//
//        Optional<Catalogo> catalogo2 = srvListarCatalogos.catalogoByIdentificador("CAT2021-2"); //Salarios e opcões financeiras
//        if (catalogo2.isPresent()) {
//
//            //Dados Gerais
//            Servico servico = servicoController.criaServico("SUBFAT1", "Submeter faturas");
//            servico.definirDescricaoBreve("Despesas colaboradores em viagens");
//            servico.definirDescricaoCompleta("Submeter faturas relativas às despesas dos colaboradores em viagens de trabalho");
//            servico.feedBackObrigatorio(true);
//            servico.adicionaKeyword("faturas");
//            servico.adicionaKeyword("despesas");
//            servico.adicionaKeyword("keywordMutua");
//            servico.definirIcone("FR1");
//
//            //Associa Catalogo
//            servico.associaCatalogo(catalogo2.get());
//
//            //Associa Formulario Solicitacao
//            formularioBuilder = new FormularioBuilder();
//            formularioBuilder = formularioBuilder.withParametros("FORMFR1_S", "Formulario de Solicitacao do Servico", "return (nFaturas>0 && total>5);");
//            formularioBuilder.withAtributoFormulario("total",
//                    "Total pago", "", TipoDadosBase.NUMERICO,
//                    "regex", true);
//            formularioBuilder.withAtributoFormulario("nFaturas",
//                    "Nº de Faturas", "", TipoDadosBase.NUMERICO,
//                    "regex", true);
//            formularioBuilder.withAtributoFormulario("faturas",
//                    "Faturas a submeter", "", TipoDadosBase.SELECAOVALORES,
//                    "regex", true);
//            formularioBuilder.withAtributoFormulario("data",
//                    "Data da ultima fatura", "", TipoDadosBase.DATA,
//                    "regex", true);
//            servico.associaFormularioSolicitacao(formularioBuilder.buildFormulario(true));
//
//            //Associa Atividade de Aprovacao
//            atividadeBuilder = new AtividadeBuilder();
//            formularioBuilder = new FormularioBuilder();
//            formularioBuilder.withParametros("FORMFR1_AA", "Formulario de Aprovacao do Servico", "x=2; y=3; return y>x;");
//
//
//
//            servico.associaAtividadeDeAprovacao(atividadeBuilder.buildAtividadeManual(ResponsavelAprovacao.RESPONSAVELCOLABORADOR, formularioBuilder.buildFormulario(false)));
//
//            //Associa Atividade de Realizacao Automatica
//            servico.associaAtividadeDeRealizacao(atividadeBuilder.buildAtividadeAutomatica("return true;"));
//
//            servicoController.terminaEspecificacaoServico(servico);
//            servicoController.guardaServico(servico);
//
//        } else {
//            System.out.println("Nao existem catalogos para poder especificar o servico!");
//        }
//        System.out.println(srvListarServicos.servicoById("SUBFAT1").get());
//    }


    protected void especificarServicoIncompleto() {
        Optional<Catalogo> catalogo2 = srvListarCatalogos.catalogoByIdentificador("CAT2021-2"); //Salarios e opcões financeiras
        if (catalogo2.isPresent()) {
            Servico servico = servicoController.criaServico("ALT_NIB1", "Alterar NIB");
            servico.definirDescricaoBreve("Alteracao conta ordenado");
            servico.definirDescricaoCompleta("Alterar o NIB associado ao perfil do colaborador, de modo a receber o seu ordenado noutra conta bancaria.");
            servico.feedBackObrigatorio(true);
            servico.adicionaKeyword("nib");
            servico.adicionaKeyword("salario");
            servico.adicionaKeyword("keywordMutua");
            servico.definirIcone("FR2");
            servico.associaCatalogo(catalogo2.get());

            servicoController.terminaEspecificacaoServico(servico);
            servicoController.guardaServico(servico);

        } else {
            System.out.println("Nao existem catalogos para poder especificar o servico!");
        }
        System.out.println(srvListarServicos.servicoById("ALT_NIB1").get());
    }


    protected void authenticateForBootstrapping() {
        authenticationService.authenticate("gsh", "Password1");
        Invariants.ensure(authz.hasSession());
    }
}
