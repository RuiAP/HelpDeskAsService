/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador.importarColaboradores;

import eapli.framework.util.TemplateMethod;
import helpdesk.estruturaorganica.application.colaborador.RegistarColaboradorController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class ImportarColaboradoresService {
       
    RegistarColaboradorController controller = new RegistarColaboradorController();
    
    private int colaboradoresImportados = 0;
    private int erros = 0;
    
    /**
     * This method reads the lines from the file and theneach line is used to instanciate 
     * an product.
     * The lines cointaining erros or products already in de database are inserted in errorFile
     * @param filePath
     * @param importer
     * @return array list where index 0 represents the sucess cases of importation ans index 1 insucess
     * @throws IOException 
     */

    @TemplateMethod
    public void importFile(String filePath, ImportarColaboradorStrategy importer) throws IOException {
                    
        List<String> qtLinhas = importer.readFile(filePath);
        
        try{
            qtLinhas.remove(0); 
        }catch(IndexOutOfBoundsException exception){
            throw new IndexOutOfBoundsException("Ficheiro vazio");
        }

        int qtColaboradoresImportar = qtLinhas.size();
        List<String> errorImportedLines = new ArrayList<>();
            
        for(String linha: qtLinhas){
            String[] colabInfo = importer.splitLine(linha);
            try{
                controller.RegistarColaboradorImportado(colabInfo[0], colabInfo[1], colabInfo[2], colabInfo[3], colabInfo[4], colabInfo[5],
                        colabInfo[6], colabInfo[7], colabInfo[8], colabInfo[9], colabInfo[10], colabInfo[11], Integer.parseInt(colabInfo[12]),
                        Integer.parseInt(colabInfo[13]), Integer.parseInt(colabInfo[14]), colabInfo[15]);   
            }catch(Exception ex){
                errorImportedLines.add(linha);
            }
        }
      
        colaboradoresImportados = qtColaboradoresImportar-errorImportedLines.size();
        erros = errorImportedLines.size();
        
        String erroNomeFicheiro = importer.getNameErrorFile(filePath);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(erroNomeFicheiro, false));
            for (String line: errorImportedLines){
                writer.append(line + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Ficheiro com erro nao pode ser escrito");
        }
    }
    
    public int getQtColaboradoresImportados(){
        return colaboradoresImportados;
    }
    /**
     * This method returns the number of importations which failed
     * @return 
     */
    public int getQtColaboradoresNaoImportados(){
        return erros;
    }
}
