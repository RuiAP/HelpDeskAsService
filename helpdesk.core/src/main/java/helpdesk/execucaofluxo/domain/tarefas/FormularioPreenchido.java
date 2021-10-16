package helpdesk.execucaofluxo.domain.tarefas;

import eapli.framework.domain.model.ValueObject;
import helpdesk.especificacaoservico.domain.servico.Formulario;
import helpdesk.linguagem.ScriptResult;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;

import javax.persistence.*;
import java.util.*;

@Entity
public class FormularioPreenchido implements ValueObject {

    @Id
    @GeneratedValue
    long pk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AtributoPreenchido> atributosPreenchidos;




    protected FormularioPreenchido() {
        //for ORM
    }

    public FormularioPreenchido(List<AtributoPreenchido> atributosPreenchidos) {
        this.atributosPreenchidos = atributosPreenchidos;
    }


    public ScriptResult validarDados(Formulario formulario){
        return formulario.validaPreenchimento(this.atributosPreenchidos);
    }

    public List<AtributoPreenchido> getAtributosPreenchidos() {
        return new LinkedList<>(atributosPreenchidos);
    }
}
