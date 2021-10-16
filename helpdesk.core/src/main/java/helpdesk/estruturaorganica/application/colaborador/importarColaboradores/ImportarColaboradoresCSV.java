/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador.importarColaboradores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class ImportarColaboradoresCSV implements ImportarColaboradorStrategy {
    public static final String SEPARATOR = ",";
     
    @Override
    public List<String> readFile(String filePath) throws IOException {
        Scanner read ;
        
        List<String> lines = new ArrayList<>();
        try {
            read = new Scanner(new File(filePath));
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("File " + filePath + " not found.");
        }
        while(read.hasNextLine()) {
            lines.add(read.nextLine());
        }
        read.close();
        
        return lines;
    }

    @Override
    public String[] splitLine(String line) {
        String[] colaboradorInfo = line.trim().split(SEPARATOR);
        return colaboradorInfo;
    }

    @Override
    public String getNameErrorFile(String filePath) {
        String fileNameWithOutExt = filePath.replaceFirst("[.][^.]+$", "");
        return fileNameWithOutExt+"_ErrorFile_.csv";
    }
    
}
