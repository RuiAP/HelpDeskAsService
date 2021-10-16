package helpdesk.app.portalUtilizador.httpServer;


import helpdesk.Application;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class StartupServer {

    public StartupServer() {

        System.setProperty("javax.net.ssl.keyStore", Application.settings().getProperty("httpServer.keystore.location"));
        System.setProperty("javax.net.ssl.keyStorePassword", Application.settings().getProperty("httpServer.keystore.password"));

        System.setProperty("javax.net.ssl.trustStore", Application.settings().getProperty("common.truststore.location"));
        System.setProperty("javax.net.ssl.trustStorePassword",Application.settings().getProperty("common.truststore.password"));


        //Iniciar servidor numa nova Thread
        Thread clientHtml = new Thread(new HttpServer());
        clientHtml.setDaemon(true);
        clientHtml.start();
    }

    static class HttpServer implements  Runnable{

        static private SSLServerSocket serverSocket;
        static final int PORT = Integer.parseInt( Application.settings().getProperty("httpServer.portNumber") );

        @Override
        public void run() {
            try {

                try {
                    serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(PORT);
                }
                catch(IOException ex) {
                    System.out.println("Failed to open local port " + PORT);
                    System.exit(1);
                }

                SSLSocket clientSock;
                while(true){
                    clientSock = (SSLSocket) serverSocket.accept();
                    new Thread(new HttpRequest(clientSock)).start();
//                    new HttpRequest(clientSock).run();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}