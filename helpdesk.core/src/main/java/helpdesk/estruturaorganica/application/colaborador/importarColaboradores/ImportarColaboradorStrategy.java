/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador.importarColaboradores;

import eapli.framework.util.Strategy;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Asus
 */
@Strategy
public interface ImportarColaboradorStrategy{
    /**
     * Read the file and saves each line in a List.
     *
     * @param filePath
     * @return List of lines with collaborators
     * @throws java.io.IOException
     */
    List<String> readFile(String filePath) throws IOException;
    
    String[] splitLine(String line);

    public String getNameErrorFile(String filePath);

}
