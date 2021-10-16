/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador.importarColaboradores;

import eapli.framework.application.UseCaseController;

import java.io.IOException;

/**
 *
 * @author Asus
 */
@UseCaseController
public class ImportarColaboradoresController {
    
    private final ImportarColaboradoresFactory colabFactory = new ImportarColaboradoresFactory();
    private final ImportarColaboradoresService colabService = new ImportarColaboradoresService();
    
    public void importFile(String filePath, String extension) throws IOException {
        ImportarColaboradorStrategy importer = null;
        
        try{
            FormatoFicheiro format = FormatoFicheiro.valueOf(extension);
            importer = colabFactory.build(format);

        } catch(Exception ex){ 
            throw new IllegalArgumentException("Unknown format.");
        }
        
        if(importer != null){
            colabService.importFile(filePath, importer);
        }           
    }
    public int getQtColaboradoresImportados(){
        return colabService.getQtColaboradoresImportados();
    }
    
    public int getQtColaboradoresNaoImportados(){
        return colabService.getQtColaboradoresNaoImportados();
    }
}
