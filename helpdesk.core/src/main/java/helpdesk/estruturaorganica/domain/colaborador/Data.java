package helpdesk.estruturaorganica.domain.colaborador;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class Data implements ValueObject {

    private Calendar dataNascimento;

    public Data(Calendar date) {
        dataNascimento = date;
    }

    protected Data() {
        //forORM
    }
}
