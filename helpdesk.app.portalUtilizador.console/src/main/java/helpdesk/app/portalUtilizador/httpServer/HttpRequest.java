package helpdesk.app.portalUtilizador.httpServer;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequest implements Runnable {
	private final SSLSocket clientSocket;
	private DataOutputStream sOut;
	private DataInputStream sIn;
	private final String BASE_FOLDER = "helpdesk.app.portalUtilizador.console/src/main/java/helpdesk/app/portalUtilizador/httpServer/files";

	public HttpRequest(SSLSocket clientSocket) { this.clientSocket =clientSocket; }

	public void run(){



		try {
			sOut = new DataOutputStream(clientSocket.getOutputStream());
			sIn = new DataInputStream(clientSocket.getInputStream());
			}
		catch(IOException ex) { System.out.println("Data Stream IOException"); }

		try {

			HTTPmessage request = new HTTPmessage(sIn);
			HTTPmessage response = new HTTPmessage();
//			 System.out.println(request.getURI());

			if(request.getMethod().equals("GET")) {
				// Landing page
				if (request.getURI().equals("/index.html") || request.getURI().equals("/")) {
					if (response.setContentFromFile(BASE_FOLDER + "/index.html")) {
						response.setResponseStatus("200 Ok");
					} else {
						response.setContentFromString(
								"<html><body><h1>404 File not found</h1></body></html>",
								"text/html");
						response.setResponseStatus("404 Not Found");
					}

					// GET /tarefas
				} else if (request.getURI().equals("/tarefas")) {
					String r = construirTabelaTarefas();
					if (r == null) {return;}
					response.setContentFromString(construirTabelaTarefas(), "text/html");
					response.setResponseStatus("200 Ok");
				}

				// GET /<<nome ficheiro>>
				else {
					String fullname = BASE_FOLDER + request.getURI();
					if (response.setContentFromFile(fullname)) {
						response.setResponseStatus("200 Ok");
					}
					else {
						response.setContentFromString(
								"<html><body><h1>404 File not found</h1></body></html>",
								"text/html");
						response.setResponseStatus("404 Not Found");
					}
				}
			}
			else {
				//se não for um metodo GET retorna erro(para já)
				response.setContentFromString(
						"<html><body><h1>ERROR: 405 Method Not Allowed</h1></body></html>",
						"text/html");
				response.setResponseStatus("405 Method Not Allowed");
				}
			response.send(sOut);

		}
		catch(IOException ex) {
//			System.out.println("Thread error when reading request");
		}


		try { clientSocket.close();}
		catch(IOException ex) { System.out.println("CLOSE IOException"); }
	}






	public String construirTabelaTarefas(){

		StringBuilder tabela = new StringBuilder("<div id=\"tarefas\">\n" +
				"    <table>\n" +
				"        <tr>\n" +
				"            <th>Código da Tarefa</th>\n" +
				"            <th>Estado</th>\n" +
				"            <th>Prioridade</th>\n" +
				"        </tr>\n");



		String msg =  MotorFluxoClient.getInstance().infoTarefas();

		if(msg == null){
			return null;
		}

		List<String> dados;

		if( msg.isEmpty()){
			tabela.append("</table><h4>Sem tarefas a mostrar.</h4></td></div>");
		}else{

			List<String> tarefas = new ArrayList<>(Arrays.asList(msg.split(";")));
			for(String t : tarefas)
				if(t != null || !t.isEmpty()){

					tabela.append("<tr>");

					dados = new ArrayList<>( (Arrays.asList(t.split("--"))) );
					for(String d : dados){
						tabela.append("<td>").append(d).append("</td>");
					}

					tabela.append("</tr>");
				}
			tabela.append("</table></div><p>Última atualização: ")
					.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
					.append("<p>");
		}

		return tabela.toString();
	}


}