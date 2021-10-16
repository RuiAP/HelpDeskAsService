package helpdesk.execucaofluxo.domain.tarefas;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Embeddable
public class Delegacao {

    @OneToOne
    Colaborador colaboradorDelegante;

    @OneToOne
    Colaborador colaboradorDelegado;

    @Column
    LocalDateTime timestamp;

    @Column
    String justificacao;

    protected Delegacao() {
    }

    public Delegacao(Colaborador colaboradorDelegante, Colaborador colaboradorDelegado, String justificacao) {
        this.colaboradorDelegante = colaboradorDelegante;
        this.colaboradorDelegado = colaboradorDelegado;
        this.timestamp = LocalDateTime.now();
        this.justificacao = justificacao;
    }
}
