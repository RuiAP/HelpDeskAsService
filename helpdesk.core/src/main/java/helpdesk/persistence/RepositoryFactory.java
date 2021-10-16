/**
 *
 */
package helpdesk.persistence;

import helpdesk.clientusermanagement.repositories.ClientUserRepository;

import helpdesk.clientusermanagement.repositories.SignupRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    CriticidadeRepository criticidades();
    CriticidadeRepository criticidades(TransactionalContext autoTx);

    PedidoRepository pedidos();
    PedidoRepository pedidos(TransactionalContext autoTx);

    ServicoRepository servicos();
    ServicoRepository servicos(TransactionalContext autoTx);

    CatalogoRepository catalogos();
    CatalogoRepository catalogos(TransactionalContext autoTx);

    TarefaRepository tarefas();
    TarefaRepository tarefas(TransactionalContext autoTx);

    FuncaoRepository funcoes();
    FuncaoRepository funcoes(TransactionalContext autoTx);

    ColaboradorRepository colaboradores(TransactionalContext autoTx);
    ColaboradorRepository colaboradores();

	EquipaRepository equipas();
	EquipaRepository equipas(TransactionalContext autoTx);

    TipoEquipaRepository tipoEquipas();
    TipoEquipaRepository tipoEquipas(TransactionalContext autoTx);

}
