/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.tipoEquipa;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

/**
 *
 * @author danie
 */

@Embeddable
public class Cor implements ValueObject {

    private int rgb;

    public Cor(int rgb) {
        this.rgb = rgb;
    }

    public Cor() {
        //for ORM
    }
}
