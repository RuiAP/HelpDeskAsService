package persistence.impl.jpa;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.CodigoEquipa;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.persistence.PersistenceContext;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class JpaColaboradorRepositoryTest {

//    JpaColaboradorRepository repo =(JpaColaboradorRepository) PersistenceContext.repositories().colaboradores();

    @Test
    public void haMaisTempoSemTarefaAssignada() {

//        List<NrMecanografico> colabs = new ArrayList<>();
//        colabs.add(NrMecanografico.valueOf("1002"));
//        colabs.add(NrMecanografico.valueOf("1001"));
//        colabs.add(NrMecanografico.valueOf("1003"));
//
////        List<String> nrm = repo.teste(colabs);
////        nrm.forEach(System.out::println);
//
//        List<Colaborador> c = repo.haMaisTempoSemTarefaAssignada(colabs);
//        c.forEach(System.out::println);


    }

    @Test
    public void colaboradoresDaEquipa() {
//        List<Colaborador> colabs = repo.colaboradoresDaEquipa(CodigoEquipa.valueOf("TEAMFC"));
//        colabs.forEach(System.out::println);

    }
}