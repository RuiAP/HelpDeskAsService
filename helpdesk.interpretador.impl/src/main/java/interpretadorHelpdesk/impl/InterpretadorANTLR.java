package interpretadorHelpdesk.impl;

import helpdesk.linguagem.Interpretador;
import helpdesk.linguagem.ScriptResult;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class InterpretadorANTLR implements Interpretador {


    @Override
    public ScriptResult runScriptRealizacao(Map<String, String> variaveis, String script) {
        Map<String, Object> variaveisObj = convertMap(variaveis);
        return runScript(variaveisObj, script);
    }


    @Override
    public ScriptResult runScript(Map<String, Object> variaveis, String script) {

        if(variaveis==null)
            variaveis =  new HashMap<>();

        try {
            LabeledExprLexer lexer = new LabeledExprLexer(new ANTLRInputStream(script));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LabeledExprParser parser = new LabeledExprParser(tokens);

            AntlrErrorListener errorListener = new AntlrErrorListener();
            parser.addErrorListener(errorListener);

            ParseTree tree = parser.prog(); // parse
            EvalVisitor eval = new EvalVisitor(variaveis);
            eval.visit(tree);

            if (errorListener.getMsg().isEmpty()) {
                if(eval.getResult() != null && eval.getResult().toString().split(" ")[0].equals("false")){
                    return new ScriptResult(true, false, eval.getResult().toString() );
                }else{
                   return new ScriptResult(true, true, eval.getResult()==null? "" :eval.getResult().toString());
                    }

            } else {
                return new ScriptResult(false, false, errorListener.getMsg());
            }

        } catch (Exception ex) {
            return new ScriptResult(false, false,  ex.getMessage() );
        }

    }



    private Map<String, Object> convertMap(Map<String, String> variaveis){
        Map<String, Object> variaveisObj = new HashMap<>();
        Object obj;
        String value;

        for(Map.Entry<String, String> entry : variaveis.entrySet()){
            obj=null;
            value = entry.getValue();

            try {
                if (value.equals("true") || value.equals("false")) {
                    obj = Boolean.parseBoolean(value);
                }
                else if (value.matches("^d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$")){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    obj= LocalDate.parse(value, formatter).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                }else{
                    obj = Double.parseDouble(value);

                }
            }catch (Exception ex) {
                //ignore
            }

            if (value.getClass().getSimpleName().equals(String.class.getSimpleName()))
                obj =  value.replaceAll("^\"|\"$", "");

            variaveisObj.put(entry.getKey(), obj);
        }
        return variaveisObj;
    }



}

