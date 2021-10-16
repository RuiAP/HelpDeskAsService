package helpdesk.solicitacaoservico.domain.pedido;

import javax.persistence.Embeddable;

public enum EstadoPedido {
    DRAFT,
    SUBMETIDO,
    EM_APROVACAO,
    APROVADO,
    REJEITADO,
    EM_RESOLUCAO,
    CONCLUIDO,
    FALHOU_REALIZACAO
}
