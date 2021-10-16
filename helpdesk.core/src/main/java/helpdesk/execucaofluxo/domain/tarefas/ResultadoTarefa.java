package helpdesk.execucaofluxo.domain.tarefas;

import javax.persistence.Embeddable;


public enum ResultadoTarefa {
    APROVADO, REJEITADO, REALIZADO, NAO_REALIZADO;

    ResultadoTarefa() {
    }
}
