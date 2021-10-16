package helpdesk.especificacaoservico.application.servico;

import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public class AtividadeBuilder {

    public AtividadeBuilder() {

    }

    public Atividade buildAtividadeAutomatica(String script) {
        return  new Atividade(script);
    }

    public Atividade buildAtividadeManual(ResponsavelAprovacao responsavelPorAprovacao, Formulario formularioAtividade) {
        return  new Atividade(responsavelPorAprovacao, formularioAtividade);
    }

    public Atividade buildAtividadeManual(Colaborador c, Formulario formularioAtividade) {
        return  new Atividade(new ResponsavelRealizacao(c), formularioAtividade);
    }

    public Atividade buildAtividadeManual(Equipa e, Formulario formularioAtividade) {
        return  new Atividade(new ResponsavelRealizacao(e), formularioAtividade);
    }

}
