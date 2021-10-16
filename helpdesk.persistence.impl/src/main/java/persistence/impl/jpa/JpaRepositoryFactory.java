package persistence.impl.jpa;


import helpdesk.clientusermanagement.repositories.SignupRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.persistence.RepositoryFactory;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public UserRepository users(final TransactionalContext autoTx) {
		return new JpaAutoTxUserRepository(autoTx);
	}

	@Override
	public UserRepository users() {
		return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}


	@Override
	public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
		return new JpaClientUserRepository(autoTx);
	}

	@Override
	public JpaClientUserRepository clientUsers() {
		return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
		return new JpaSignupRequestRepository(autoTx);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public CriticidadeRepository criticidades() {
		return new JpaCriticidadesRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public CriticidadeRepository criticidades(TransactionalContext autoTx) {
		return new JpaCriticidadesRepository(autoTx);
	}




	@Override
	public ServicoRepository servicos() {
		return new JpaServicosRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public ServicoRepository servicos(final TransactionalContext autTx) {
		return new JpaServicosRepository(autTx);
	}

	@Override
	public JpaCatalogoRepository catalogos() {
		return new JpaCatalogoRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public JpaCatalogoRepository catalogos(TransactionalContext autoTx) {
		return new JpaCatalogoRepository(autoTx);
	}

	@Override
	public PedidoRepository pedidos() {
		return new JpaPedidosRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public PedidoRepository pedidos(TransactionalContext autoTx) {
		return new JpaPedidosRepository(autoTx);
	}

	@Override
	public TarefaRepository tarefas() {
		return new JpaTarefaRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public TarefaRepository tarefas(TransactionalContext autoTx) {
		return new JpaTarefaRepository(autoTx);
	}

	@Override
	public FuncaoRepository funcoes() {
		return new JpaFuncaoRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public FuncaoRepository funcoes(TransactionalContext autoTx) {
		return new JpaFuncaoRepository(autoTx);
	}


	@Override
	public ColaboradorRepository colaboradores(final TransactionalContext autoTx) {
		return new JpaColaboradorRepository(autoTx);
	}
	@Override
	public ColaboradorRepository colaboradores() {
		return new JpaColaboradorRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public EquipaRepository equipas() {
		return new JpaEquipaRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public EquipaRepository equipas(TransactionalContext autoTx) {
		return new JpaEquipaRepository(autoTx);
	}

	@Override
	public TipoEquipaRepository tipoEquipas() {
		return new JpaTipoEquipaRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public TipoEquipaRepository tipoEquipas(TransactionalContext autoTx) {
		return new JpaTipoEquipaRepository(autoTx);
	}

	@Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}


}
