package helpdesk.execucaofluxo.domain.tarefas;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CodigoTarefa implements ValueObject, Comparable<CodigoTarefa> {


    private int codigoTarefa;

    protected CodigoTarefa() {
        this.codigoTarefa= (int) (Math.random()*Integer.MAX_VALUE);
    }

    protected CodigoTarefa(int codigoTarefa){
        this.codigoTarefa = codigoTarefa;
    }

    public static CodigoTarefa valueOf(int codigoTarefa){
        return new CodigoTarefa(codigoTarefa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoTarefa)) return false;
        CodigoTarefa that = (CodigoTarefa) o;
        return codigoTarefa == that.codigoTarefa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoTarefa);
    }

    @Override
    public int compareTo(CodigoTarefa o) {
        return this.codigoTarefa - o.codigoTarefa;
    }

    @Override
    public String toString() {
        return "" +codigoTarefa;
    }
}
