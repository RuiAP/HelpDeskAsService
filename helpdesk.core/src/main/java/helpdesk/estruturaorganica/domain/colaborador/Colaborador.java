package helpdesk.estruturaorganica.domain.colaborador;

import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.*;


@Entity
public class Colaborador implements AggregateRoot<NrMecanografico>{


    @Version
    private Long version;
    
    @Column
    private NomeBreve nomeCurto;
    
    @Column
    private NomeCompleto nomeCompleto;
    
    @Column
    private ContactoTelefonico contactoTelefonico;
    
    @Column
    private EmailInstitucional emailInstitucional;
    
    @Column
    private EstadoCivil estadoCivil;
    
    @Column
    private Nif nif;
    
    @Column
    private Nib nib;
    
    @EmbeddedId
    private NrMecanografico nrMecanografico;
    
    @OneToOne
    private Funcao funcao;

    @OneToOne (optional = true)
    private Colaborador colaboradorResponsavel;
    
    @Column
    private Morada morada;
    
    @Column
    private Data dataNascimento;
    
    @Column
    private Password password;
    
    @ManyToMany
    private Set<Equipa> setEquipas;
    
    public Colaborador(String nomeCurto, String nomeCompleto, String contactoTelefonico, String emailInstitucional, String estadoCivil,
                       String nif, String nib, String nrMecanografico, Funcao funcao, Colaborador responsavel, String distrito,
                       Calendar dataNascimento, String password) {


        this.nomeCurto = new NomeBreve(nomeCurto);
        this.nomeCompleto = new NomeCompleto(nomeCompleto);
        this.contactoTelefonico = new ContactoTelefonico(contactoTelefonico);
        this.emailInstitucional = new EmailInstitucional(emailInstitucional);
        this.estadoCivil = new EstadoCivil(estadoCivil);
        this.nif = new Nif(nif);
        this.nib = new Nib(nib);
        this.nrMecanografico = new NrMecanografico(nrMecanografico);
        this.funcao = funcao;
        this.colaboradorResponsavel = responsavel;
        this.morada = new Morada(distrito);
        this.dataNascimento = new Data(dataNascimento);
        this.password = new Password(password);
    }
    
    protected Colaborador() {
        //form ORM
    }
    
    public boolean addEquipa(Equipa equipa) {
        return this.setEquipas.add(equipa);
    }
    
    public boolean removeEquipa(Equipa equipa) {
        return this.setEquipas.remove(equipa);
    }

    public Iterable<Equipa> getSetEquipas() {
        return new HashSet<Equipa>(this.setEquipas);

    }

    public boolean pertenceAEquipa(Equipa equipa){
        if (equipa == null){
            return false;
        }else{
            return setEquipas.contains(equipa);
        }
    }

    public EmailInstitucional email(){
        return this.emailInstitucional;
    }

    public Colaborador responsavelHierarquico() {
        return colaboradorResponsavel;
    }

    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }
    
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }


    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public NrMecanografico identity() {
        return this.nrMecanografico;
    }


    @Override
    public String toString() {
        return   nomeCompleto +" ("+ nrMecanografico + ") - "+ funcao;
    }

}