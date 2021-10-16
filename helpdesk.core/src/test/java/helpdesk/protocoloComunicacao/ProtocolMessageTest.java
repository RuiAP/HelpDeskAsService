package helpdesk.protocoloComunicacao;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ProtocolMessageTest {



    //https://lipsum.com/
    //90 words, 60 bytes
    private String msg600 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras nec mollis massa." +
            "In nec diam sed metus blandit laoreet. Nunc purus turpis, congue sit amet neque sed," +
            "accumsan varius dolor. Vivamus augue elit, scelerisque vitae vestibulum eget, elementum" +
            "vitae eros. In dictum odio rhoncus libero sodales tempus. Aenean ipsum risus, vulputate" +
            "nec turpis a, commodo finibus risus. Vivamus rhoncus ipsum ac convallis tincidunt. Aenean" +
            "massa eros, aliquam non sollicitudin sed, suscipit sed massa. Proin quis lobortis eros." +
            "Donec nisl eros, ultricies quis posuere eget, ornare ac magna. Morbi eleifend id.";

    //41words, 255 bytes
    private String msg255 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ligula velit," +
            " volutpat eu risus in, euismod porta ante. Donec elementum enim orci, et vulputate diam malesuada ac." +
            " Mauris nec ante purus. Nunc et odio ac mi euismod sodales congue nec sed.";

    //17 words, 120 bytes
    private String msg120 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vehicula, " +
            "odio vel eleifend viverra, odio magna eleifend.";


    @Test
    public void caracteresPortugueses() throws IOException {

        String mensagem = "ã mensagem de teste �çç";
        byte[] result = ProtocolMessage.construirResposta(22, mensagem);

        ByteArrayInputStream inS= new ByteArrayInputStream(result);
        DataInputStream in = new DataInputStream(inS);
        String[] res = ProtocolMessage.receberMensagem(in);

        assertEquals(res[1],mensagem);

    }

    @Test
    public void construirResposta() {
        //bytes to string
        //https://onlineutf8tools.com/convert-utf8-to-bytes
        //hex to decimal
        //https://tomeko.net/online_tools/hex_to_dec.php?lang=en
        byte[] result = ProtocolMessage.construirResposta(22, "mensagem de teste");
        byte[] expectedResult = new byte[]{0, 22, 17,
                109, 101, 110, 115,  97, 103, 101, 109, 32, 100,
                101,  32, 116, 101, 115, 116,
                101};

        assertArrayEquals(expectedResult,result);

       result = ProtocolMessage.construirResposta(50, "codigoIdentificador e numeros 678");
       expectedResult = new byte[]{0, 50, 33,
                99, 111, 100, 105, 103, 111,  73, 100, 101, 110,
                116, 105, 102, 105,  99,  97, 100, 111, 114,  32,
                101,  32, 110, 117, 109, 101, 114, 111, 115,  32,
                54,  55,  56};

        assertArrayEquals(expectedResult,result);
    }

    @Test
    public void enviarMensagemUnica() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(os);

        byte[] result;
        byte[] segmento;
        int versao, codigo, tamanho;

        ProtocolMessage.enviarMensagem(75, msg255, out);

        result = os.toByteArray();

        versao= Byte.toUnsignedInt(result[0]);
        codigo= Byte.toUnsignedInt(result[1]);
        tamanho= Byte.toUnsignedInt(result[2]);
        assertEquals(0, versao);
        assertEquals(75, codigo);
        assertEquals(255, tamanho);
        segmento = Arrays.copyOfRange(result,3, 3+255);
        assertEquals(msg255, new String(segmento));
        //System.out.println(new String(segmento));

    }

    @Test
    public void enviarMensagemSegmentada() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(os);

        byte[] conteudoDoOutputStream;
        byte[] segmento;
        int versao, codigo, tamanho;

        ProtocolMessage.enviarMensagem(30, msg255+msg120, out);


        //total escrito para a stream
        conteudoDoOutputStream = os.toByteArray();


        //primeira mensagem (0 até 258) --------- "from" é inclusive, "to" é exclusive
        byte[] resultadoParcial = Arrays.copyOfRange(conteudoDoOutputStream,0,3+255);

        versao= Byte.toUnsignedInt(resultadoParcial[0]);
        codigo= Byte.toUnsignedInt(resultadoParcial[1]);
        tamanho= Byte.toUnsignedInt(resultadoParcial[2]);
        assertEquals(0, versao);
        assertEquals(255, codigo);
        assertEquals(255, tamanho);
        segmento = Arrays.copyOfRange(resultadoParcial,3, 3+255);
        assertEquals(msg255, new String(segmento));
        //System.out.println(new String(segmento));

        //segunda mensagem (258 até 381(258+3+120) ) --------- "from" é inclusive, "to" é exclusive
        resultadoParcial = Arrays.copyOfRange(conteudoDoOutputStream,258,382);

        versao= Byte.toUnsignedInt(resultadoParcial[0]);
        codigo= Byte.toUnsignedInt(resultadoParcial[1]);
        tamanho= Byte.toUnsignedInt(resultadoParcial[2]);
        assertEquals(0, versao);
        assertEquals(30, codigo);
        assertEquals(120, tamanho);
        segmento = Arrays.copyOfRange(resultadoParcial,3, 3+120);
        assertEquals(msg120, new String(segmento));
        //System.out.println(new String(segmento));

    }


    @Test
    public void receberMensagemUnica() throws IOException {

        String mensagemDeTeste = "codigoIdentificador e numeros 678";
        int codigo = 50;
        byte[] mensagem = new byte[]{0, Byte.parseByte(""+codigo), 33,
                99, 111, 100, 105, 103, 111,  73, 100, 101, 110,
                116, 105, 102, 105,  99,  97, 100, 111, 114,  32,
                101,  32, 110, 117, 109, 101, 114, 111, 115,  32,
                54,  55,  56};


        ByteArrayInputStream inS= new ByteArrayInputStream(mensagem);
        DataInputStream in = new DataInputStream(inS);
        String[] result = ProtocolMessage.receberMensagem(in);
        assertEquals(codigo, Integer.parseInt(result[0]));
        assertEquals(mensagemDeTeste, result[1]);

    }


    @Test
    public void receberMensagemSegmentada() throws IOException {
        int codigo = 99;
        int sizeMsg2 = 120;

        byte[] msg = new byte[]{0, -1, -1,//codigo 255 -> -1, tamanho 255 -> -1
                76, 111, 114, 101, 109,  32, 105, 112, 115, 117, 109,  32, 100, 111, 108, 111, 114,  32, 115, 105,
                116,  32,  97, 109, 101, 116,  44,  32,  99, 111, 110, 115, 101,  99, 116, 101, 116, 117, 114,  32,
                97, 100, 105, 112, 105, 115,  99, 105, 110, 103,  32, 101, 108, 105, 116,  46,  32,  67, 117, 114,
                97,  98, 105, 116, 117, 114,  32, 108, 105, 103, 117, 108,  97,  32, 118, 101, 108, 105, 116,  44,
                32, 118, 111, 108, 117, 116, 112,  97, 116,  32, 101, 117,  32, 114, 105, 115, 117, 115,  32, 105,
                110,  44,  32, 101, 117, 105, 115, 109, 111, 100,  32, 112, 111, 114, 116,  97,  32,  97, 110, 116,
                101,  46,  32,  68, 111, 110, 101,  99,  32, 101, 108, 101, 109, 101, 110, 116, 117, 109,  32, 101,
                110, 105, 109,  32, 111, 114,  99, 105,  44,  32, 101, 116,  32, 118, 117, 108, 112, 117, 116,  97,
                116, 101,  32, 100, 105,  97, 109,  32, 109,  97, 108, 101, 115, 117,  97, 100,  97,  32,  97,  99,
                46,  32,  77,  97, 117, 114, 105, 115,  32, 110, 101,  99,  32,  97, 110, 116, 101,  32, 112, 117,
                114, 117, 115,  46,  32,  78, 117, 110,  99,  32, 101, 116,  32, 111, 100, 105, 111,  32,  97,  99,
                32, 109, 105,  32, 101, 117, 105, 115, 109, 111, 100,  32, 115, 111, 100,  97, 108, 101, 115,  32,
                99, 111, 110, 103, 117, 101,  32, 110, 101,  99,  32, 115, 101, 100,  46
        };

        byte[] msg2 = new byte[]{0, Byte.parseByte(""+codigo), Byte.parseByte(""+sizeMsg2) ,
                76, 111, 114, 101, 109,  32, 105, 112, 115, 117, 109,  32, 100, 111, 108, 111, 114,  32, 115, 105,
                116,  32,  97, 109, 101, 116,  44,  32,  99, 111, 110, 115, 101,  99, 116, 101, 116, 117, 114,  32,
                97, 100, 105, 112, 105, 115,  99, 105, 110, 103,  32, 101, 108, 105, 116,  46,  32,  70, 117, 115,
                99, 101,  32, 118, 101, 104, 105,  99, 117, 108,  97,  44,  32, 111, 100, 105, 111,  32, 118, 101,
                108,  32, 101, 108, 101, 105, 102, 101, 110, 100,  32, 118, 105, 118, 101, 114, 114,  97,  44,  32,
                111, 100, 105, 111,  32, 109,  97, 103, 110,  97,  32, 101, 108, 101, 105, 102, 101, 110, 100,  46
        };

        byte[] ambasMensagens = new byte[msg.length + msg2.length];
        System.arraycopy(msg,0, ambasMensagens, 0, msg.length);
        System.arraycopy(msg2,0,ambasMensagens,msg.length, msg2.length);

        ByteArrayInputStream inS= new ByteArrayInputStream(ambasMensagens);
        DataInputStream in = new DataInputStream(inS);
        String[] result = ProtocolMessage.receberMensagem(in);
        assertEquals(codigo, Integer.parseInt(result[0]));
        assertEquals(msg255+msg120, result[1]);

    }
}