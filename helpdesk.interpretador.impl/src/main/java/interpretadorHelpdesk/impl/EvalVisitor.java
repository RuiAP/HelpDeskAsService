package interpretadorHelpdesk.impl;

import helpdesk.servicoEmails.EmailService;
import helpdesk.servicoEmails.impl.EmailServiceFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Thread.sleep;

public class EvalVisitor extends LabeledExprBaseVisitor<Object> {
    /**
     * Hashmap que mapeia a key que representa a variavel e o objecto respetivo
     */
    private Map<String, Object> memory = new HashMap<>();
    private Object res = null;


    public EvalVisitor() {
        super();
    }

    public EvalVisitor(Map<String, Object> variaveis) {
        super();
        this.memory = variaveis;
    }

    public Object getResult() {
        return res;
    }


    private Object converterParaVariavel(Object value) {

        Object x = null;

        try {

            if (value.toString().equals("true") || value.toString().equals("false")) {
                x = Boolean.parseBoolean(value.toString());
                return x;
            }


            x = Double.parseDouble(value.toString());
            return x;
        } catch (Exception ex) {

        }

        if (value.getClass().getSimpleName().equals(String.class.getSimpleName()))
            return value.toString().replaceAll("^\"|\"$", "");

        return value;
    }

    protected List<String> readFromCsv(String filePath) throws Exception {

        try {
            List<String> csvLines = new ArrayList<>();

            Scanner scanner = null;
            File file = new File(filePath);
            if (!file.exists()) {
                if (filePath.contains("\\")) {
                    filePath = filePath.replace("\\", "/");
                } else {
                    filePath = filePath.replace("/", "\\");
                }
            }
            file = new File(filePath);

            scanner = new Scanner(file);

            while (scanner.hasNext()) {
                csvLines.add(scanner.nextLine());
            }
            scanner.close();

            return csvLines;
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File not found");
        }
    }

    protected List<Node> readFromXML(String filePath) throws FileNotFoundException {

        try {

            File fXmlFile = new File(filePath);
            if (!fXmlFile.exists()) {
                if (filePath.contains("\\")) {
                    filePath = filePath.replace("\\", "/");
                } else {
                    filePath = filePath.replace("/", "\\");
                }
            }
            fXmlFile = new File(filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            List<Node> all = new ArrayList<>();
            NodeList nList = doc.getDocumentElement().getChildNodes();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    all.add(nNode);
                }
            }

            return all;
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    private String getFileExtension(String filePath) {
        String p[] = filePath.split("\\.");
        if (p != null && p.length > 1)
            return p[p.length - 1];
        else
            return "";
    }


    @Override
    public Object visitAllRecordsExpression(LabeledExprParser.AllRecordsExpressionContext ctx) {
        Object filePath = visit(ctx.expression());
        String extension = getFileExtension(filePath.toString());

        try {
            if (extension.equals("csv")) {
                return readFromCsv(filePath.toString());
            } else if (extension.equals("xml")) {
                return readFromXML(filePath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitFindFirstRecordExpression(LabeledExprParser.FindFirstRecordExpressionContext ctx) {

        Object filePath = visit(ctx.expression(2));
        String extension = getFileExtension(filePath.toString());

        try {
            if (extension.equals("csv")) {

                String str = visit(ctx.expression(0)).toString();
                String itemName[] = str.split("_");
                int index = ((Double) converterParaVariavel(itemName[itemName.length - 1])).intValue();

                List<String> lines = readFromCsv(filePath.toString());
                for (String s : lines) {
                    String values[] = s.split(";");
                    memory.put(str, values[index]);
                    if ((boolean) visit(ctx.expression(1))) {
                        return s;
                    }
                    memory.remove(str);
                }

            } else if (extension.equals("xml")) {

                List<Node> nodes = readFromXML(filePath.toString());

                for (Node nNode : nodes) {

                    Element eElement = (Element) nNode;

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        String str = visit(ctx.expression(0)).toString();
//                        if (memory.containsKey(str)) {
//                            memory.remove(str);
//                        }
                        String itemName[] = str.split("_");

                        if (itemName[0].equals("attribute")) {
                            String att = eElement.getAttribute(itemName[1]);
                            memory.put(str, att);
                        } else if (itemName[0].equals("tag")) {
                            String tag = eElement.getElementsByTagName(itemName[1])
                                    .item(0).getTextContent();
                            memory.put(str, tag);
                        }
                        if ((boolean) visit(ctx.expression(1))) {
                            return nNode;
                        }
                        memory.remove(str);
                    }
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitFindRecordExpression(LabeledExprParser.FindRecordExpressionContext ctx) {

        Object filePath = visit(ctx.expression(2));
        String extension = getFileExtension(filePath.toString());

        try {
            if (extension.equals("csv")) {

                String str = visit(ctx.expression(0)).toString();
                String itemName[] = str.split("_");
                int index = ((Double) converterParaVariavel(itemName[itemName.length - 1])).intValue();

                List<String> found = new ArrayList<>();
                List<String> lines = readFromCsv(filePath.toString());
                for (String s : lines) {
                    String values[] = s.split(";");
                    memory.put(str, values[index]);
                    if ((boolean) visit(ctx.expression(1))) {
                        found.add(s);
                    }
                    memory.remove(str);
                }
                return found;

            } else if (extension.equals("xml")) {

                List<Node> found = new ArrayList<>();
                List<Node> nodes = readFromXML(filePath.toString());

                for (Node nNode : nodes) {

                    Element eElement = (Element) nNode;

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        String str = visit(ctx.expression(0)).toString();
//                        if (memory.containsKey(str)) {
//                            memory.remove(str);
//                        }
                        String itemName[] = str.split("_");

                        if (itemName[0].equals("attribute")) {
                            String att = eElement.getAttribute(itemName[1]);
                            memory.put(str, att);
                        } else if (itemName[0].equals("tag")) {
                            String tag = eElement.getElementsByTagName(itemName[1])
                                    .item(0).getTextContent();
                            memory.put(str, tag);
                        }
                        if ((boolean) visit(ctx.expression(1))) {
                            found.add(nNode);
                        }
                        memory.remove(str);
                    }
                }
                return found;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitMemoryAccessObjectXMLExpression(LabeledExprParser.MemoryAccessObjectXMLExpressionContext ctx) {

        Object identificador = ctx.identifier().getText();

        Object tag = ctx.TAG() != null ? ctx.TAG().getText() : null;
        Object attribute = ctx.ATTRIBUTE() != null ? ctx.ATTRIBUTE().getText() : null;
        Object field = visit(ctx.expression());

        try {
            Object res = memory.get(identificador);
            Node nNode = (Node) res;
            Element eElement = (Element) nNode;

            if (attribute != null) {
                String result = eElement.getAttribute(field.toString());
                return converterParaVariavel(result);
            } else if (tag != null) {
                String result = eElement.getElementsByTagName(field.toString())
                        .item(0).getTextContent();
                return converterParaVariavel(result);
            }
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitObjectXMLExpression(LabeledExprParser.ObjectXMLExpressionContext ctx) {
        Object tag = ctx.TAG() != null ? ctx.TAG().getText() : null;
        Object atributo = ctx.ATTRIBUTE() != null ? ctx.ATTRIBUTE().getText() : null;
        Object value = visit(ctx.expression());

        String key = (tag == null ? atributo.toString() : tag.toString()) + "_" + value.toString();

        if (memory.containsKey(key))
            return converterParaVariavel(memory.get(key));

        return key;
    }

    @Override
    public Object visitMemoryAccessColumnCSVExpression(LabeledExprParser.MemoryAccessColumnCSVExpressionContext ctx) {
        String identificador = ctx.identifier().getText();
        //Object columnName = "column_csv_file_" + visit(ctx.expression());
        Object c = visit(ctx.expression());
        try {
            String str = memory.get(identificador).toString();
            String values[] = str.split(";");
            return converterParaVariavel(values[((Double) c).intValue()]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public Object visitColumnCSVExpression(LabeledExprParser.ColumnCSVExpressionContext ctx) {
        Object number = visit(ctx.expression());

        String key = "column_csv_file_" + "_" + number.toString();

        if (memory.containsKey(key))
            return converterParaVariavel(memory.get(key));

        return key;
    }

    @Override
    public Object visitSleepExpression(LabeledExprParser.SleepExpressionContext ctx) {
        try {
            for (int i = 0; i < ((Double) visit(ctx.expression())).intValue(); i = i + 1000) {
                System.out.println(".");
                Thread.sleep(1000);
            }
            System.out.print("\n");
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Object visitWhileExpression(LabeledExprParser.WhileExpressionContext ctx) {
        String var = ctx.identifier(0).getText();
        List<Object> list = (List<Object>) visit(ctx.identifier(1));

        for (Object obj : list) {
            memory.put(var, obj);
            visit(ctx.statement());
        }

        return null;
    }

    @Override
    public Object visitVariableAssignmentExpression(LabeledExprParser.VariableAssignmentExpressionContext ctx) {

        String identificador = ctx.identifier().getText();
        Object func = ctx.expression() == null ? visit(ctx.statement()) : visit(ctx.expression());


        if (memory.containsKey(identificador)) {
            memory.remove(identificador);
        }
        Object value = converterParaVariavel(func);
        memory.put(identificador, value);

        return null;
    }

    @Override
    public Object visitPrintExpression(LabeledExprParser.PrintExpressionContext ctx) {
        System.out.println(visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitIfElseExpression(LabeledExprParser.IfElseExpressionContext ctx) {
        Object res = visit(ctx.expression());
        if (res.equals(true))
            return visit(ctx.statement(0));
        else if (ctx.statement(1) != null)
            return visit(ctx.statement(1));

        return null;
    }

    @Override
    public Object visitReturnExpression(LabeledExprParser.ReturnExpressionContext ctx) {
        Object newRes = visit(ctx.expression());
        if (res == null)
            res = newRes;
        return newRes;
    }

    @Override
    public Object visitGetSubStringExpression(LabeledExprParser.GetSubStringExpressionContext ctx) {
        Object startIndex = visit(ctx.expression(0));
        Object endIndex = visit(ctx.expression(1));
        Object string = visit(ctx.expression(2));

        if (string.toString().length() < ((Double) endIndex).intValue() - 1) {
            return "";
        }

        if (string.toString().length() < ((Double) startIndex).intValue() - 1) {
            return "";
        }

        String res = "";
        for (int i = ((Double) startIndex).intValue(); i <= ((Double) endIndex).intValue(); i++) {
            res += string.toString().charAt(i);
        }
        return converterParaVariavel(res);
    }

    @Override
    public Object visitParenthesesExpression(LabeledExprParser.ParenthesesExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitSendEmailExpression(LabeledExprParser.SendEmailExpressionContext ctx) {
        EmailService emailService = new EmailServiceFactoryImpl().emailService();
        String assunto = "Em resposta ao seu pedido ";
        if (memory.containsKey("codigopedido")) {
            assunto += memory.get("codigopedido");
        }
        emailService.enviarEmail(visit(ctx.expression(0)).toString(), assunto, visit(ctx.expression(1)).toString());
//        System.out.println("Send email to: " + visit(ctx.expression(0)) + "\nContent: " + visit(ctx.expression(1)));
        return null;
    }

    @Override
    public Object visitConcatExpression(LabeledExprParser.ConcatExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        return left + right;
    }

    @Override
    public Object visitFilledExpression(LabeledExprParser.FilledExpressionContext ctx) {
        Object identificador = visit(ctx.identifier());
        return identificador != null && !identificador.toString().isEmpty();
    }

    @Override
    public Object visitIdentifier(LabeledExprParser.IdentifierContext ctx) {
        if (memory.containsKey(ctx.getText())) {
            return memory.get(ctx.getText());
        } else {
            throw new NullPointerException("A variável " + ctx.getText() + " não existe na memória.");
        }
    }

    @Override
    public Object visitGreaterthanExpression(LabeledExprParser.GreaterthanExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        int cmp = ctx.EQ() == null ? 1 : 0;
        return left.compareTo(right) >= cmp;
    }

    @Override
    public Object visitNotExpression(LabeledExprParser.NotExpressionContext ctx) {
        Object expr = visit(ctx.expression());
        return !((boolean) converterParaVariavel(expr.toString()));
    }

    @Override
    public Object visitDivExpression(LabeledExprParser.DivExpressionContext ctx) {

        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        Double res = (Double) v1 / (Double) v2;
        return res;
    }

    @Override
    public Object visitBoolLitExpression(LabeledExprParser.BoolLitExpressionContext ctx) {
        String expr = ctx.getText().toString();
        return converterParaVariavel(expr);
    }


    @Override
    public Object visitOrExpression(LabeledExprParser.OrExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        return (boolean) v1 || (boolean) v2;
    }

    @Override
    public Object visitAndExpression(LabeledExprParser.AndExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        return (boolean) v1 && (boolean) v2;
    }

    @Override
    public Object visitStringExpression(LabeledExprParser.StringExpressionContext ctx) {
        return converterParaVariavel(ctx.getText());
    }

    @Override
    public Object visitAddExpression(LabeledExprParser.AddExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        Double res = (Double) v1 + (Double) v2;
        return res;
    }

    @Override
    public Object visitModExpression(LabeledExprParser.ModExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        Double res = (Double) v1 % (Double) v2;
        return res;
    }

    @Override
    public Object visitLessThanExpression(LabeledExprParser.LessThanExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        int cmp = ctx.EQ() == null ? 0 : -1;
        return left.compareTo(right) <= cmp;
    }

    @Override
    public Object visitEqualityExpression(LabeledExprParser.EqualityExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        return left.equals(right);
    }

    @Override
    public Object visitCharacterExpression(LabeledExprParser.CharacterExpressionContext ctx) {
        return converterParaVariavel(ctx.getText());
    }

    @Override
    public Object visitIntegerLitExpression(LabeledExprParser.IntegerLitExpressionContext ctx) {
        String v = ctx.getText();
        return converterParaVariavel(ctx.getText());
    }

    @Override
    public Object visitSubExpression(LabeledExprParser.SubExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        Double res = (Double) v1 - (Double) v2;
        return res;
    }

    @Override
    public Object visitMulExpression(LabeledExprParser.MulExpressionContext ctx) {
        String left = visit(ctx.expression(0)).toString();
        String right = visit(ctx.expression(1)).toString();

        Object v1 = converterParaVariavel(left);
        Object v2 = converterParaVariavel(right);

        Double res = (Double) v1 * (Double) v2;
        return res;
    }
}