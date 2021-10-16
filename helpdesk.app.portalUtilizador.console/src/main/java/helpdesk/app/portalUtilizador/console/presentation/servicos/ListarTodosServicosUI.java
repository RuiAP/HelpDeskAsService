package helpdesk.app.portalUtilizador.console.presentation.servicos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.solicitacaoservico.application.pedido.ConsultarServicosController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ListarTodosServicosUI extends AbstractUI {


    private final ConsultarServicosController controller = new ConsultarServicosController();

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected boolean doShow() {
//        String servicosResourceUrl
//                = "http://localhost:8080/api/servicos";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(servicosResourceUrl , String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = null;
//        try {
//            root = mapper.readTree(response.getBody());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        JsonNode name = root.path("name");
//        assertThat(name.asText(), notNullValue());


        Iterable<Catalogo> catalogosPermitidos = controller.catalogosPermitidos();
        Set<Servico> servicosPermitidos = new LinkedHashSet<>();
        for(Catalogo catalogo : catalogosPermitidos){
            Iterable<Servico> servicos = controller.servicosPermitidosByCatalogo(catalogo);
            servicos.forEach(servicosPermitidos::add);
        }

        // ordernar por algum critéirio. Alfabeticamente?

        final SelectWidget<Servico> selectorServicos = new SelectWidget<>("Selecione o serviço a solicitar:", servicosPermitidos);
        selectorServicos.show();
        Servico servicoSelecionado = selectorServicos.selectedElement();

        if(servicoSelecionado == null) {
            System.out.println("Operação cancelada");
            return false;
        }else{
            SolicitarServicoUI solicitarUi = new SolicitarServicoUI();
            solicitarUi.escolheServico(servicoSelecionado);
            solicitarUi.show();
            return true;
        }

    }

    @Override
    public String headline() {
        return "Todos os serviços disponíveis";
    }
}
