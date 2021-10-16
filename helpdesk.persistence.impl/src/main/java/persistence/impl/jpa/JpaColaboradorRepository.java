package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.EmailInstitucional;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.CodigoEquipa;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class JpaColaboradorRepository extends JpaAutoTxRepository<Colaborador, NrMecanografico, NrMecanografico>
        implements ColaboradorRepository {


    public JpaColaboradorRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "nrMecanografico");
    }

    public JpaColaboradorRepository(final TransactionalContext autoTx) {
        super(autoTx, "nrMecanografico");
    }

    @Override
    public Optional<Colaborador> colaboradorByEmail(EmailInstitucional emailInstitucional) {
        final Map<String, Object> params = new HashMap<>();
        params.put("email", emailInstitucional);
        return matchOne("e.emailInstitucional =: email", params);
    }

    @Override
    public List<Colaborador> haMaisTempoSemTarefaAssignada(List<NrMecanografico> colaboradores) {

        final TypedQuery<Colaborador> query = createQuery(
                "select distinct  colab from Colaborador colab where colab in ("+
                                "select c from  " +
                                " TarefaManual tm "+
                                "join tm.historicoTarefa ht " +
                                "join tm.colaboradorResponsavel c "+
                                "where c.nrMecanografico in :colabs "+
                                "and ht.estadoTarefa = 'ASSIGNADA' "+
                                "order by ht.timestamp "+
                    " ) "
                    , Colaborador.class);

        query.setParameter("colabs",colaboradores);

        return query.getResultList();

    }

    @Override
    public List<Colaborador> colaboradoresDaEquipa(CodigoEquipa codigo) {
        final TypedQuery<Colaborador> query = createQuery(
                "select c from Colaborador c" +
                        " join c.setEquipas s " +
                        "where s.codigoEquipa = :codigo"
                , Colaborador.class);

        query.setParameter("codigo",codigo);

        return query.getResultList();
    }

//    public List<String> teste(List<NrMecanografico> colaboradores){
//        EntityManager em = entityManager();
//        String query =  "SELECT TAREFA_HISTORICOTAREFA.COLABORADORINTERVENIENTE_NRMECANOGRAFICO FROM TAREFA_HISTORICOTAREFA " +
//                "join tarefamanual on TAREFA_HISTORICOTAREFA.tarefa_codigotarefa=tarefamanual.codigotarefa "+
//                "where estadotarefa='ASSIGNADA'" +
//                "and colaboradorinterveniente_nrmecanografico = 1002 " +
//                "group by colaboradorinterveniente_nrmecanografico";
//
//        Query q = em.createNativeQuery(query);
//        return q.getResultList();
//    }




}
