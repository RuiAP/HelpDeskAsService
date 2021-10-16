package interpretadorHelpdesk.impl;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class AntlrErrorListener extends BaseErrorListener {

    static String msg = "";

    public static final AntlrErrorListener INSTANCE = new AntlrErrorListener();

    public AntlrErrorListener() {
    }

    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        this.msg += "line " + line + ":" + charPositionInLine + " " + msg + " \n";
    }

    public String getMsg() {
        return msg;
    }

}
