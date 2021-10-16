package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;
import helpdesk.linguagem.InterpretadorContext;
import helpdesk.linguagem.ScriptResult;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Servico
 * <p>
 * Esta classe representa um formulario.
 */

@Entity
public class Formulario implements ValueObject {

    @Id
    @GeneratedValue
    long pk;

    @Embedded
    private Identificador identificador;

    @Column
    private Titulo titulo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AtributoFormulario> atributosFormulario;

    @Column
    private Script script;


    public Formulario(String identificador, String titulo, List<AtributoFormulario> atributosFormulario, String script) {

        if (identificador == null || titulo == null)
            throw new IllegalArgumentException();

        this.identificador = new Identificador(identificador);
        this.titulo = new Titulo(titulo);

        this.atributosFormulario = atributosFormulario;
        this.script = new Script(script);

    }


    protected Formulario() {
        //for ORM
    }


    protected boolean estaCompleto() {
        return identificador != null && titulo != null && atributosFormulario.size() > 0 && script != null;
    }

    public ScriptResult validaPreenchimento(List<AtributoPreenchido> listaAtributosPreenchidos) {
        Map<String,Object> mapaVariaveis = new HashMap<>();

        //Para cada atributo do formulário verifica se existe o atributo preenchido correspondente
        // e converte-o de String para o tipo correto (definido ao solicitar formulario)
        //Por fim, recolhe o resultado no mapaVariaveis (nomeAtributo, valor) para enviar para o interpretador
        for(AtributoPreenchido atributoPreenchido : listaAtributosPreenchidos) {
            for (AtributoFormulario atributoFormulario : atributosFormulario) {
                if (atributoFormulario.getEtiquetaApresentacao().toString()
                        .equals(
                                atributoPreenchido.getEtiquetaApresentacao().toString())) {

                    if(atributoFormulario.isPreenchimentoObrigatorio()){
                        if(atributoPreenchido.getValue() == null || atributoPreenchido.getValue().isEmpty()){
                            return new ScriptResult(false, false,"O atributo "+atributoFormulario.getEtiquetaApresentacao()+ " deve ser preenchido.");
                        }
                    }else{//variavel nao obrigatorio e nao preenchida
                        if(atributoPreenchido.getValue() == null || atributoPreenchido.getValue().isEmpty()){
                            mapaVariaveis.put(atributoFormulario.getNomeVariavel().toString(), null);
                            continue;
                        }
                    }

                    if(!atributoFormulario.getExpressaoRegular().toString().trim().isEmpty()){
                        if(!atributoPreenchido.getValue().matches(atributoFormulario.getExpressaoRegular().toString())){
                            //return new ScriptResult(false, false,"O atributo "+atributoFormulario.getEtiquetaApresentacao()+ " não respeita a expressão regular.");
                        }
                    }


                    Object obj = null;

                        if (atributoFormulario.getTipoDadosBase().equals(TipoDadosBase.BOOLEANO)) {
                            obj = Boolean.parseBoolean(atributoPreenchido.getValue());
                        } else if (atributoFormulario.getTipoDadosBase().equals(TipoDadosBase.DATA)) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            obj = LocalDate.parse(atributoPreenchido.getValue(), formatter).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                        } else if (atributoFormulario.getTipoDadosBase().equals(TipoDadosBase.TEXTO)) {
                            obj = atributoPreenchido.getValue();
                        } else if (atributoFormulario.getTipoDadosBase().equals(TipoDadosBase.NUMERICO)) {
                            obj = Double.parseDouble(atributoPreenchido.getValue());
                        } else if (atributoFormulario.getTipoDadosBase().equals(TipoDadosBase.SELECAOVALORES)) {
                            obj = atributoPreenchido.getValue();
                        }

                    mapaVariaveis.put(atributoFormulario.getNomeVariavel().toString(),obj);
                }
            }
        }

        return InterpretadorContext.interpretador().runScript(mapaVariaveis, this.script.value());
    }


    public List<AtributoFormulario> getAtributosFormulario() {
        return atributosFormulario;
    }

    public String getIdentificador() {
        return identificador.toString();
    }

    public String getTitulo() {
        return titulo != null ? titulo.toString() : "";
    }

    public String getScript() {
        return script != null ? script.toString() : "";
    }


}
