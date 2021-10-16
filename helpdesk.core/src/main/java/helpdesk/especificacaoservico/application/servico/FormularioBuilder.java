package helpdesk.especificacaoservico.application.servico;

import helpdesk.especificacaoservico.domain.servico.*;


import java.util.*;

public class FormularioBuilder {

    List<AtributoFormulario> atributos;
    String identificador;
    String titulo;
    String script;

    public FormularioBuilder() {
        atributos = new LinkedList<>();
    }

    public FormularioBuilder withParametros(String identificador, String titulo, String script) {
        this.identificador = identificador;
        this.titulo = titulo;
        this.script = script;
        return this;
    }

    public boolean withAtributoFormulario(String nomeVariavel, String etiquetaApresentacao,
                                          String descricaoAjuda, TipoDadosBase tipoDadosBase,
                                          String expressaoRegular, boolean preenchimentoObrigatorio) {

        return atributos.add(new AtributoFormulario(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase, expressaoRegular, preenchimentoObrigatorio));
    }

    public boolean withAtributoFormulario(String nomeVariavel, String etiquetaApresentacao,
                                          String descricaoAjuda, TipoDadosBase tipoDadosBase,
                                          String expressaoRegular, boolean preenchimentoObrigatorio, Set<String> options) {

        return atributos.add(new AtributoFormulario(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase, expressaoRegular, preenchimentoObrigatorio,options));
    }


    public Formulario buildFormulario(boolean isFormularioSolicitacao) {
        if(identificador==null || titulo==null || atributos==null || script==null)
            return null;

        if(!isFormularioSolicitacao){
           adicionaAtributoDecisao();
        }

        return new Formulario(identificador, titulo, atributos, script);
    }

    /**
     * Adiciona atributo decisão(tipo boolean) caso não exista já um;
     */
    private void adicionaAtributoDecisao(){
        for(AtributoFormulario atributo : atributos){
            if(atributo.getNomeVariavel().toString().equals("decisao") && atributo.getTipoDadosBase().equals(TipoDadosBase.BOOLEANO)){
                return;
            }
        }
        atributos.add(new AtributoFormulario(
                "decisao",
                "Tarefa aprovada/realizada? (s) Sim (n) Não",
                "",
                TipoDadosBase.BOOLEANO, "s|n|S|N", true) );
    }

}
