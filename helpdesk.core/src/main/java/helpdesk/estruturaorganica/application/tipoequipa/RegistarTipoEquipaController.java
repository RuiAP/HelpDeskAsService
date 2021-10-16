/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.tipoequipa;

import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;
import helpdesk.persistence.PersistenceContext;

import java.awt.*;

/**
 *
 * @author danie
 */
public class RegistarTipoEquipaController {
    
        TipoEquipaRepository repo = PersistenceContext.repositories().tipoEquipas();

    
    public TipoEquipa TipoEquipa(String codigo, String descricao, Color cor) {

        TipoEquipa tipoEquipa = new TipoEquipa(codigo, descricao, cor);
        return repo.save(tipoEquipa);
    }

}
