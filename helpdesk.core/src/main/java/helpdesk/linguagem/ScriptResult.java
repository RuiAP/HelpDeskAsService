package helpdesk.linguagem;

public class ScriptResult {

    private final boolean foiExecutado;
    private boolean resultado;
    private final String mensagem;

    public ScriptResult(boolean foiExecutado,boolean resultado, String mensagem) {
        this.foiExecutado = foiExecutado;
        this.resultado = resultado;
        this.mensagem = mensagem;
    }


    public boolean foiExecutado() {
        return foiExecutado;
    }

    public boolean executouComSucesso(){
        return resultado;
    }


    public String getMensagem() {
        if (mensagem == null){
            return "";
        }
        return mensagem;
    }


}
