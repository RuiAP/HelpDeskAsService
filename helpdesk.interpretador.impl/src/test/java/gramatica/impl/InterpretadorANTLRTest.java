package gramatica.impl;

import helpdesk.linguagem.ScriptResult;
import interpretadorHelpdesk.impl.InterpretadorANTLR;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class InterpretadorANTLRTest extends TestCase {

    InterpretadorANTLR interpretadorANTLR = new InterpretadorANTLR();


    public void testScriptServico1_1(){

        String script = "if(data_fim>=data_inicio) { \n" +
                        "\tif(tipo_ausencia==\"Justificada\"){\n" +
                        "\t\treturn justificacao.IsFilled; \n" +
                        "\t} \n" +
                        "\treturn true;\n" +
                        "}\n" +
                        "return false;\n";

        Map<String, Object> map = new  HashMap<String, Object>();

        map.put("tipo_ausencia", "Justificada");
        map.put("data_inicio", "2021-01-01");
        map.put("data_fim", "2021-01-02");
        map.put("justificacao", "teste");

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.executouComSucesso());
        assertTrue(res.foiExecutado());


        map = new  HashMap<String, Object>();

        map.put("tipo_ausencia", "Justificada");
        map.put("data_inicio", "2021-01-02");
        map.put("data_fim", "2021-01-01");
        map.put("justificacao", "teste");

        res = interpretadorANTLR.runScript(map, script);

        assertFalse(res.executouComSucesso());
        assertTrue(res.foiExecutado());

        map = new  HashMap<String, Object>();

        map.put("tipo_ausencia", "Não Justificada");
        map.put("data_inicio", "2021-01-01");
        map.put("data_fim", "2021-01-02");

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.executouComSucesso());
        assertTrue(res.foiExecutado());
    }

    public void testScriptServico1_2(){

        String script = "return fundamentacao.IsFilled;";

        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("fundamentacao", "");

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
        assertFalse(res.executouComSucesso());

        map = new  HashMap<String, Object>();
        map.put("fundamentacao", "preenchido");

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
        assertTrue(res.executouComSucesso());
    }

    public void testScriptServico1_3(){

        String script = "return ferias_totais==(ferias_gozadas_periodo+ferias_gozadas_ano);";

        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("ferias_gozadas_periodo", 5.0);
        map.put("ferias_gozadas_ano", 5.0);
        map.put("ferias_totais", 10.0);

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
        assertTrue(res.executouComSucesso());

        map = new  HashMap<String, Object>();
        map.put("ferias_gozadas_periodo", 5.0);
        map.put("ferias_gozadas_ano", 8.0);
        map.put("ferias_totais", 10.0);

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
        assertFalse(res.executouComSucesso());
    }

    public void testScriptServico2_1(){

        String script = "x = 0;\n" +
                "if(desconto_pct.IsFilled) {\n" +
                "    if(desconto_valor.IsFilled || (desconto_pct<=0))\n" +
                "        x=1;\n" +
                "}else{\n" +
                "     if(!desconto_valor.IsFilled || (desconto_valor<=0))\n" +
                "        x=1;\n" +
                "}\n" +
                "if(recorrencia==\"Única\" ){\n" +
                "    if(!nr_fatura.IsFilled)\n" +
                "        x=1;\n" +
                "}\n" +
                "else\n" +
                "{\n" +
                "    if(!data_limite.IsFilled)\n" +
                "        x=1;\n" +
                "}\n" +
                "return x==0;";


        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("desconto_pct", 0.5);
        map.put("desconto_valor", null);
        map.put("recorrencia", "Única");
        map.put("nr_fatura", "FR001");
        map.put("data_limite", null);

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
        assertTrue( res.executouComSucesso());
    }

    public void testScriptServico2_2(){


        String script = "if(confirma_desconto.IsFilled) {\n" +
                "   return !confirma_data.IsFilled;\n" +
                "}else{\n" +
                "   return confirma_data.IsFilled;\n" +
                "}";

        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("confirma_desconto", true);
        map.put("confirma_data", null);

        ScriptResult res = interpretadorANTLR.runScript(map, script);
        assertTrue(res.foiExecutado());
        assertTrue(res.executouComSucesso());
    }

    public void testScriptServico2_3(){


        String script = "if(decisao)\n" +
                        "    sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" foi aprovado.\" );\n" +
                        "else\n" +
                        "    sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" não foi aprovado.\" );";

        Map<String, Object> map = new  HashMap<>();
        map.put("decisao", true);
        map.put("emailcolaborador", "jose@gmail.com");
        map.put("codigopedido", "2021/00001");

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());

        map = new  HashMap<String, Object>();
        map.put("decisao", false);
        map.put("emailcolaborador", "jose@gmail.com");
        map.put("codigopedido", "2021/00001");

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
    }

    public void testScriptServico4_1(){


        String script = "if((quantidade%1)>0){\n" +
                " sub = getSubString(0,1,codigoproduto);"+
                "    return ((sub==20) || (sub==21));\n" +
                "}\n" +
                "return true;";

        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("quantidade", 20.05);
        map.put("codigoproduto", "200001");

        ScriptResult res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());

        map = new  HashMap<String, Object>();
        map.put("quantidade", 20.05);
        map.put("codigoproduto", "210001");

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());

        map = new  HashMap<String, Object>();
        map.put("quantidade", 20.05);
        map.put("codigoproduto", "190001");

        res = interpretadorANTLR.runScript(map, script);

        assertTrue(res.foiExecutado());
    }

    public void testScriptServico4_2(){

        String script = "item = findFirstRecord(tag \"id\", (tag \"id\") == codigoproduto, filepath);\n" +
                "preco = (item tag \"price\");\n" +
                "categoria = (item tag \"category\");\n" +
                "precoTotal = preco*quantidade;\n" +
                "if(precoTotal>500){\n" +
                "    desconto = 3/100;\n" +
                "}else{\n" +
                "    desconto = 1/100;\n" +
                "}\n" +
                "if(categoria == \"ABC\")\n" +
                "    desconto = desconto + 5/1000;\n" +
                "\n" +
                "valorDesconto =  desconto*precoTotal;\n" +
                "valorTotal = precoTotal - valorDesconto;\n" +
                "sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" custava \" $ precoTotal $ \" e com um desconto de \" $ valorDesconto $ \" ficou por \" $ valorTotal);";

        Map<String, Object> map = new  HashMap<String, Object>();
        map.put("filepath", "fileService4.xml");
        map.put("quantidade", 30);
        map.put("codigoproduto", "XYJ234");
        map.put("emailcolaborador", "jose@gmail.com");
        map.put("codigopedido", "2021/00001");

        ScriptResult res = interpretadorANTLR.runScript(map, script);
        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadXML_1() {

        String script = "list = allRecords(\"file1.xml\"); " +
                "forAll(item : list) " +
                "print(item tag \"name\"); ";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadXML_2() {


        String script = "list = allRecords(\"file1.xml\"); " +
                "forAll(item : list) if((item tag \"price\") < 30) " +
                "print((item tag \"name\") $ \" \" $ (item tag \"price\"));";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadXML_3() {

        String script = "items = findRecords(tag \"name\", (tag \"name\") == \"Product Two\", \"file1.xml\"); " +
                "forAll(item : items) print(\"Price: \" $ (item tag \"price\")) ;";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadXML_4() {

        String script = "items = findRecords(attribute \"id\", (attribute \"id\") == \"XYJ234\", \"file2.xml\"); " +
                "forAll(item : items) print(\"Price: \" $ (item tag \"price\")) ;";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadCSV_1() {

        String script = "list = allRecords(\"file.csv\"); " +
                "forAll(item : list) " +
                "print(item column 1); ";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadCSV_2() {

        String script = "list = allRecords(\"file.csv\"); " +
                "forAll(item : list) if((item column 3) < 30) " +
                "print((item column 1) $ \" \" $ (item column 3));";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_ReadCSV_3() {

        String script = "items = findRecords(column 1, (column 1) == \"Product Two\", \"file.csv\"); " +
                "forAll(item : items) print(\"Price: \" $ (item column 3)) ;";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testSendEmail_Pedido() {

        Map<String, Object> vars = new HashMap<>();

        vars.put("codigopedido", "2020/00001");
        vars.put("emailcolaborador", "teste@helpdesk.com");

        String script = "sendEmail(emailcolaborador, \"O pedido \" $ codigopedido $ \" esta a ser processado\");";

        ScriptResult res = interpretadorANTLR.runScript(vars, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_1() {

        String script = "a=2; b=3; c=5; if(b<a) return c>6; else return !(c>4);";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

//    public void testSleep() {
//
//        String script = "sleep(10000);";
//
//        ScriptResult res = interpretadorANTLR.runScript(null, script);
//
//        assertTrue(res.foiExecutado());
//    }

    public void testRunScript_2() {

        String script = "x=4; sendEmail(\"1234@hotmail.com\",\"email de teste\" $ x);";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_3() {

        String script = "x=4*4; sendEmail(\"1234@hotmail.com\",\"email de teste \" $ x);";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_4() {

        String script = "c=4; b=c+8; print(b); return b;";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
    }

    public void testRunScript_5() {

        String script = "x=1.88*3; c=\"teste\"; if(x==1.88*3) print(x $ \" \" $ c.IsFilled); return x;";

        ScriptResult res = interpretadorANTLR.runScript(null, script);

        assertTrue(res.foiExecutado());
        assertTrue(res.executouComSucesso());
        assertEquals("5.64", res.getMensagem());
    }


}