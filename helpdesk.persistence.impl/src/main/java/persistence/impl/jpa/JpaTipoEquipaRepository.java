package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.estruturaorganica.domain.tipoEquipa.CodigoUnicoTipoEquipa;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;

public class JpaTipoEquipaRepository extends JpaAutoTxRepository<TipoEquipa, CodigoUnicoTipoEquipa, CodigoUnicoTipoEquipa>
        implements TipoEquipaRepository {


    public JpaTipoEquipaRepository(TransactionalContext autoTx){
        super(autoTx, "codigoUnicoTipoEquipa");
    }

    public JpaTipoEquipaRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "codigoUnicoTipoEquipa");
    }


}
