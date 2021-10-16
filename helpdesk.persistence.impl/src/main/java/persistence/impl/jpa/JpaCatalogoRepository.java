package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

public class JpaCatalogoRepository extends JpaAutoTxRepository<Catalogo, Identificador, Identificador>
        implements CatalogoRepository {

    public JpaCatalogoRepository(TransactionalContext autoTx){
        super(autoTx, "identificador");
    }

    public JpaCatalogoRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "identificador");
    }


    @Override
    public Iterable<Catalogo> catalogosDisponiveisPorEquipa(Equipa equipa) {


        final TypedQuery<Catalogo> query = createQuery(
                "select c from Catalogo c join c.equipasComAcesso e where e.codigoEquipa=:codigo"
                , Catalogo.class);

        query.setParameter("codigo", equipa.identity());

        return query.getResultList();
    }

    public Iterable<Catalogo> catalogosPorColaborador(NrMecanografico nrMecanografico){
        final TypedQuery<Catalogo> query = createQuery(
                "select c from Catalogo c join c.equipasComAcesso eq where eq IN (select s from Colaborador x join x.setEquipas s where x.nrMecanografico = :numero)"
                , Catalogo.class);

        query.setParameter("numero", nrMecanografico);

        return query.getResultList();
    }


//    public Iterable<Equipa> equipasDoColaborador(String nrMecanografico){
//        final TypedQuery<Equipa> query = createQuery(
//                "select s from Colaborador x join x.setEquipas s where x.nrMecanografico = '1002'"
//                , Equipa.class);
//
//        return query.getResultList();
//    }
}
