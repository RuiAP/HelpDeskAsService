package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.estruturaorganica.domain.funcao.Designacao;
import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;

public class JpaFuncaoRepository extends JpaAutoTxRepository<Funcao, Designacao, Designacao>
        implements FuncaoRepository {

    public JpaFuncaoRepository(TransactionalContext autoTx){
        super(autoTx, "designacao");
    }

    public JpaFuncaoRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "designacao");
    }


}
