package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.estruturaorganica.domain.equipa.CodigoEquipa;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.repositories.EquipaRepository;

public class JpaEquipaRepository extends JpaAutoTxRepository<Equipa, CodigoEquipa, CodigoEquipa>
        implements EquipaRepository {

    public JpaEquipaRepository(TransactionalContext autoTx){
        super(autoTx, "codigoEquipa");
    }

    public JpaEquipaRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "codigoEquipa");
    }


}
