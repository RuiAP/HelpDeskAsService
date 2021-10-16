package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

@Entity
public class Keyword implements ValueObject {

    @Id
    @GeneratedValue // (strategy = GenerationType.AUTO)
    private Long pk;

    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    protected Keyword() {
        //for ORM
    }

    public static Keyword valueOf(String keyword){
        return new Keyword(keyword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword)) return false;
        Keyword keyword1 = (Keyword) o;
        return Objects.equals(keyword, keyword1.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword);
    }

    @Override
    public String toString() {
        return keyword;
    }
}
