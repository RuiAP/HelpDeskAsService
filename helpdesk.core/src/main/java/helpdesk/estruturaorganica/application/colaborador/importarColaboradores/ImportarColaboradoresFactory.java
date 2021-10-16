/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador.importarColaboradores;

/**
 *
 * @author Asus
 */
public final class ImportarColaboradoresFactory {
    public ImportarColaboradorStrategy build(FormatoFicheiro formato) {
        switch(formato) {
        case CSV:
            return new ImportarColaboradoresCSV();
        }
    throw new IllegalStateException("Formato desconhecido");
    }
}
