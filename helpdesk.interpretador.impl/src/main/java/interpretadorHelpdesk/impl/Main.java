package interpretadorHelpdesk.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String res="";
        try {

            boolean use_listener = true;

            LabeledExprLexer lexer = new LabeledExprLexer(new ANTLRInputStream("x=2;print(x);"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LabeledExprParser parser = new LabeledExprParser(tokens);

            AntlrErrorListener errorListener = new AntlrErrorListener();
            parser.addErrorListener(errorListener);
            ParseTree tree = parser.prog(); // parse

            if (use_listener) {
                EvalListener listener = new EvalListener();
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(listener,tree);
            } else {
                EvalVisitor eval = new EvalVisitor();
                eval.visit(tree);
            }

        }catch (Exception ex){
            res += ex.getMessage();
        }

        System.out.println(res);

    }

}


