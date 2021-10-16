package interpretadorHelpdesk.impl;

import helpdesk.servicoEmails.EmailService;
import helpdesk.servicoEmails.impl.EmailServiceFactoryImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EvalListener extends LabeledExprBaseListener {
    /*** "memory"  ***/
    private Map<String, Object> memory = new HashMap<>();

    public EvalListener() {
        super();
    }

    @Override
    public void exitPrintExpression(LabeledExprParser.PrintExpressionContext ctx) {
        System.out.println(memory.get(ctx.expression().getText()));
    }

    @Override
    public void enterVariableAssignmentExpression(LabeledExprParser.VariableAssignmentExpressionContext ctx) {
        memory.put(ctx.identifier().getText(), ctx.expression().getText());
    }

}