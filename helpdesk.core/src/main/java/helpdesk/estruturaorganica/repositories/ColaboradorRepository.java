package helpdesk.estruturaorganica.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.EmailInstitucional;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.CodigoEquipa;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends DomainRepository<NrMecanografico, Colaborador> {


    Optional<Colaborador> colaboradorByEmail(EmailInstitucional emailInstitucional);

    List<Colaborador> haMaisTempoSemTarefaAssignada(List<NrMecanografico> colaboradores);

    List<Colaborador> colaboradoresDaEquipa(CodigoEquipa codigoEquipa);
}
