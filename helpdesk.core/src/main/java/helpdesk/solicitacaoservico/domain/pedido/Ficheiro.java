package helpdesk.solicitacaoservico.domain.pedido;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ficheiro implements ValueObject {

    @Id
    @GeneratedValue
    private Long pk;

    @Column
    private String filePath;

    protected Ficheiro(String filePath) {
        Preconditions.nonNull("O caminho do ficheiro não pode ser nulo");
        this.filePath = this.filePath;
    }

    protected Ficheiro() {
        // for ORM
    }

    public static Ficheiro valueOf(String filePath){
        return new Ficheiro(filePath);
    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ficheiro file = (Ficheiro) o;
        return Objects.equals(filePath, file.filePath);

    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }

}