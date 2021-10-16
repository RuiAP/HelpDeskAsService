package helpdesk.protocoloComunicacao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ProtocolMessage {

    private static final Logger LOGGER = LogManager.getLogger(ProtocolMessage.class);

    private static final int VERSAO_DO_PROTOCOLO = 0;
    private static final int MESSAGE_MAX_LENGTH = 255;


    public ProtocolMessage() {
    }


    public static String[] receberMensagem(DataInputStream in) throws IOException {
        boolean mensagemCompleta = true;
        StringBuilder mensagem = new StringBuilder();
        int codigo, tamanho;
        byte[] dados;
        do {
            int versao = in.read();
            if (versao != VERSAO_DO_PROTOCOLO) {
                throw new IllegalArgumentException("Versão do protocolo não suportada. Use a versão 0 (zero).");
            }
            codigo = in.read();
            tamanho = in.read();
            dados = in.readNBytes(tamanho);
            mensagem.append(new String(dados, StandardCharsets.UTF_8));
            mensagemCompleta = (codigo != ProtocolCodes.Segmento.codigo());

        } while (!mensagemCompleta);


        return new String[]{"" + codigo, mensagem.toString()};
    }


    public static byte[] construirResposta(int codigo, String mensagem) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeByte(VERSAO_DO_PROTOCOLO);
            dos.writeByte(codigo);
            byte[] msg = mensagem.getBytes(StandardCharsets.UTF_8);
            dos.writeByte(msg.length);
            dos.write(msg);
            dos.flush();
            return os.toByteArray();
        } catch (IOException e) {
            LOGGER.error(e);
            return new byte[]{0, 0, 0, 0};
        }

    }

    public static void enviarMensagem(byte[] resposta, DataOutputStream out) throws IOException {
        enviarMensagem(resposta[1], new String(Arrays.copyOfRange(resposta, 3, resposta.length)), out);
    }

    public static void enviarMensagem(int codigo, String conteudo, DataOutputStream out) throws IOException {

        byte[] conteudoEmBytes = conteudo.getBytes(StandardCharsets.UTF_8);

        if (conteudoEmBytes.length <= MESSAGE_MAX_LENGTH) {
            byte[] mensagem = construirResposta(codigo, conteudo);
            out.write(mensagem);

        } else {
            byte[] segmento;
            int index = 0;

            do {
                segmento = Arrays.copyOfRange(conteudoEmBytes, index, Math.min(index + MESSAGE_MAX_LENGTH, (conteudoEmBytes.length - 1)));
                index += MESSAGE_MAX_LENGTH;


                segmento = construirResposta(ProtocolCodes.Segmento.codigo(), new String(segmento));
                out.write(segmento);
            } while ((conteudoEmBytes.length - index) > MESSAGE_MAX_LENGTH);

            segmento = Arrays.copyOfRange(conteudoEmBytes, index, Math.min(index + MESSAGE_MAX_LENGTH, conteudoEmBytes.length));
            segmento = construirResposta(codigo, new String(segmento));
            out.write(segmento);
        }

    }
}
