package server;

import java.io.IOException;

public class RunServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Server server = new Server(12345);
            server.executar();
        } catch (IOException ex) {
            System.out.println("Servidor não pode ser iniciado! " + ex.toString());
        }
    }

}
