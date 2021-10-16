package persistence.impl.inmemory;

import helpdesk.clientusermanagement.repositories.ClientUserRepository;
import helpdesk.clientusermanagement.repositories.SignupRequestRepository;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.infrastructure.bootstrapers.BaseBootstrapper;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

	static {
		// only needed because of the in memory persistence
		new BaseBootstrapper().execute();
	}

	@Override
	public UserRepository users(final TransactionalContext tx) {
		return new InMemoryUserRepository();
	}

	@Override
	public UserRepository users() {
		return users(null);
	}


	@Override
	public ClientUserRepository clientUsers(final TransactionalContext tx) {

		return new InMemoryClientUserRepository();
	}

	@Override
	public ClientUserRepository clientUsers() {
		return clientUsers(null);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return signupRequests(null);
	}

	@Override
	public CriticidadeRepository criticidades() {
		return null;
	}

	@Override
	public CriticidadeRepository criticidades(TransactionalContext autoTx) {
		return null;
	}

	@Override
	public ServicoRepository servicos() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServicoRepository servicos(TransactionalContext autTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PedidoRepository pedidos() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PedidoRepository pedidos(TransactionalContext autTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CatalogoRepository catalogos() {
		throw new UnsupportedOperationException();
	}

	@Override
	public CatalogoRepository catalogos(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TarefaRepository tarefas() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TarefaRepository tarefas(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FuncaoRepository funcoes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FuncaoRepository funcoes(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ColaboradorRepository colaboradores(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ColaboradorRepository colaboradores() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EquipaRepository equipas() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EquipaRepository equipas(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TipoEquipaRepository tipoEquipas() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TipoEquipaRepository tipoEquipas(TransactionalContext autoTx) {
		return null;
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext tx) {
		return new InMemorySignupRequestRepository();
	}




	@Override
	public TransactionalContext newTransactionalContext() {
		// in memory does not support transactions...
		return null;
	}

}
