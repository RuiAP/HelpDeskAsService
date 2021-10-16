package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;

import javax.persistence.TypedQuery;


public class JpaServicosRepository extends JpaAutoTxRepository<Servico, CodigoServico, CodigoServico>
        implements ServicoRepository {

    public JpaServicosRepository(final TransactionalContext autoTx) {
        super(autoTx, "codigoServico");
    }

    public JpaServicosRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "codigoServico");
    }

    @Override
    public Iterable<Servico> findByEstado(EstadoEspecificacao estadoEspecificacao) {
        final TypedQuery<Servico> query = createQuery(
                "select s from Servico s " +
                        "where s.estadoEspecificacao = :estadoE "
                , Servico.class);

        query.setParameter("estadoE",estadoEspecificacao);
        return query.getResultList();
    }

    @Override
    public Iterable<Servico> findByEstado(EstadoEspecificacao estadoEspecificacao, NrMecanografico nrMecanografico) {
        final TypedQuery<Servico> query = createQuery(
                "select s from Servico s " +
                        "join s.catalogo.equipasComAcesso eq " +
                        "where s.estadoEspecificacao = :estadoE " +
                        "and eq IN (select e from Colaborador x join x.setEquipas e where x.nrMecanografico = :numero)"
                , Servico.class);

        query.setParameter("estadoE",estadoEspecificacao);
        query.setParameter("numero", nrMecanografico);
        return query.getResultList();
    }

    @Override
    public Iterable<Servico> findByCatalogo(Identificador idCatalogo, NrMecanografico nrMecanografico) {
        final TypedQuery<Servico> query = createQuery(
                "select s from Servico s " +
                        "join s.catalogo.equipasComAcesso eq " +
                        "where s.catalogo.identificador = :idCat " +
                        "and eq IN (select e from Colaborador x join x.setEquipas e where x.nrMecanografico = :numero)"
                , Servico.class);

        query.setParameter("idCat",idCatalogo);
        query.setParameter("numero", nrMecanografico);
        return query.getResultList();
    }

    @Override
    public Iterable<Servico> findByKeyword(Keyword keyword, NrMecanografico nrMecanografico) {
        final TypedQuery<Servico> query = createQuery(
                "select s from Servico s " +
                        "join s.keywords k " +
                        "join s.catalogo.equipasComAcesso eq " +
                        "where k.keyword like :keyword " +
                        "and eq IN (select e from Colaborador x join x.setEquipas e where x.nrMecanografico = :numero)"
                , Servico.class);

        query.setParameter("keyword", "%"+ keyword.toString()+"%");
        query.setParameter("numero", nrMecanografico);
        return query.getResultList();
    }



    @Override
    public Iterable<Servico> findByTitulo(Titulo titulo, NrMecanografico nrMecanografico) {
        final TypedQuery<Servico> query = createQuery(
                "select s from Servico s " +
                        "join s.catalogo.equipasComAcesso eq " +
                        "where s.tituloServico like :titulo " +
                        "and eq IN (select e from Colaborador x join x.setEquipas e where x.nrMecanografico = :numero)"
                , Servico.class);

        query.setParameter("titulo", titulo);
        query.setParameter("numero", nrMecanografico);

        return query.getResultList();
    }


}
