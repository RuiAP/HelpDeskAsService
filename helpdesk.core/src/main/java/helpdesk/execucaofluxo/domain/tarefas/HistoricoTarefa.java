package helpdesk.execucaofluxo.domain.tarefas;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class HistoricoTarefa {

    @OneToOne
    private Colaborador colaboradorInterveniente;

    @Column
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
     private EstadoTarefa estadoTarefa;



    protected HistoricoTarefa() {
    }

    public HistoricoTarefa(Colaborador colaboradorInterveniente, EstadoTarefa estadoTarefa) {
        this.colaboradorInterveniente = colaboradorInterveniente;
        this.timestamp = LocalDateTime.now();
        this.estadoTarefa = estadoTarefa;
    }

    public EstadoTarefa getEstadoTarefa() {
        return estadoTarefa;
    }
}
