package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.execucaofluxo.domain.tarefas.CodigoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.EstadoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;


public class JpaTarefaRepository extends JpaAutoTxRepository<Tarefa, CodigoTarefa, CodigoTarefa>
        implements TarefaRepository {


    public JpaTarefaRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),"codigoTarefa");
    }

    public JpaTarefaRepository(TransactionalContext autoTx) {
        super(autoTx, "codigoTarefa");
    }



    @Override
    public Iterable<Tarefa> todasTarefasDoColaborador(NrMecanografico nrMecanografico) {

        final TypedQuery<Tarefa> query = createQuery(
                "select t from TarefaManual t " +
                        "where t.colaboradorResponsavel.nrMecanografico =:numero"
                , Tarefa.class);

        query.setParameter("numero", nrMecanografico);

        Iterable<Tarefa> ret =query.getResultList();

        //super.entityManager().close();

        return ret;
    }

    @Override
    public Iterable<Tarefa> tarefasPorEstadoEColaboradorResponsavel(NrMecanografico nrMecanografico, EstadoTarefa estadoTarefa) {
        final TypedQuery<Tarefa> query = createQuery(
                "select distinct t from TarefaManual t " +
                            "join t.historicoTarefa ht " +
                            "where t.colaboradorResponsavel.nrMecanografico =:numero " +
                            "and ht.estadoTarefa = :estadoT " +
                            "and ht.timestamp = (select max(f.timestamp) from t.historicoTarefa f) "
                , Tarefa.class);

        query.setParameter("numero", nrMecanografico);
        query.setParameter("estadoT", estadoTarefa);

        return query.getResultList();

    }

    @Override
    public Iterable<Tarefa> tarefaDisponiveisParaReivindicar(NrMecanografico nrMecanografico) {
        final TypedQuery<Tarefa> query = createQuery(
                "select distinct t from TarefaManual t " +
                        "join t.historicoTarefa ht " +
                        "where t.colaboradorResponsavel IS NULL " + //nao foi reivindicada

                        //redundante -> ver se o ultimo estado é pendente
                        "and ht.estadoTarefa = :estadoT " +
                        "and ht.timestamp = (select max(f.timestamp) from t.historicoTarefa f) "+

                        //verifica que o colaborador pertence a equiparesponsavel por realizar a Tarefa OU  que esse colaborador existe na lista de colaboradoresPossiveis
                        "and ( " +
                        " t.possiveisResponsaveis.equipaPossivel IN (Select e from Colaborador c join c.setEquipas e where c.nrMecanografico = :numero ) "+
                        " OR "+
                        " EXISTS (select ColabPossivel from t.possiveisResponsaveis.colaboradoresPossiveis ColabPossivel  where ColabPossivel.nrMecanografico = :numero)"+
                        " ) "
                , Tarefa.class);

        query.setParameter("numero", nrMecanografico);
        query.setParameter("estadoT", EstadoTarefa.PENDENTE);

        return query.getResultList();
    }

    @Override
    public Optional<TarefaManual> tarefaAprovacaoPorPedido(IdentificadorPedido identificadorPedido) {

        final TypedQuery<TarefaManual> query = createQuery(
                "select distinct t from TarefaManual t where t.pedido.identificador = :idPedido"

        , TarefaManual.class);
        query.setParameter("idPedido", identificadorPedido);

        try {
            return Optional.of( query.getSingleResult() );
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }




    public Iterable<Tarefa> teste(NrMecanografico nrMecanografico) {
        final TypedQuery<Tarefa> query = createQuery(
                "select distinct t from TarefaManual t " +
                        "join t.historicoTarefa ht " +
                        "where t.colaboradorResponsavel IS NULL " + //nao foi reivindicada

                        //redundante -> ver se o ultimo estado é pendente
                        "and ht.estadoTarefa = :estadoT " +
                        "and ht.timestamp = (select max(f.timestamp) from t.historicoTarefa f) "+

                        //verifica que o colaborador pertence a equiparesponsavel por realizar a Tarefa OU  que esse colaborador existe na lista de colaboradoresPossiveis
                        "and ( " +
                        " t.possiveisResponsaveis.equipaPossivel IN (Select e from Colaborador c join c.setEquipas e where c.nrMecanografico = :numero ) "+
                            " OR "+
                        " EXISTS (select ColabPossivel from t.possiveisResponsaveis.colaboradoresPossiveis ColabPossivel  where ColabPossivel.nrMecanografico = :numero)"+
                        " ) "
                , Tarefa.class);

        query.setParameter("numero", nrMecanografico);
        query.setParameter("estadoT", EstadoTarefa.PENDENTE);

        return query.getResultList();
    }




}
