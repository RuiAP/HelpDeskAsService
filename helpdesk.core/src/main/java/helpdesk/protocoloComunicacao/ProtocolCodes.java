package helpdesk.protocoloComunicacao;

public enum ProtocolCodes {

    Teste(0),
    Fim(1),
    Entendido(2),
    RealizadoComSucesso(3),
    ErroAoRealizarPedido(4),
    MensagemNaoReconhecida(5),
    CodigoInvalido(6),
    ErroDeFormatacao(7),
    ErroDeConteudo(8),
    ErroDoServidor(9),

    PedidoSubmetido(50),
    InformacaoDeTarefas(53),
    RespostaInformacaoDeTarefas(54),
    TarefaConcluida(55),
    RealizarTarefaAutomatica(56),
    DadosParaRealizacao(57),

    FeedbackRealizacaoTarefa(58),
    EstadoExecutorTarefas(59),
    Segmento(255);



    private final int codigo;

    ProtocolCodes(int codigo) {
        this.codigo = codigo;
    }


    public int codigo(){
        return this.codigo;
    }

}
