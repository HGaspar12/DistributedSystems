package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final int porta;
    private final ServerSocket servidor;
    private final Data base;

    public Server(int porta, int tamFila) throws IOException {
        this.porta = porta;
        base = new Data();
        servidor = new ServerSocket(porta, tamFila);
    }

    public Server(int porta) throws IOException {
        this.porta = porta;
        base = new Data();
        // fila de tamanho 50 por padrão
        servidor = new ServerSocket(porta);
        System.out.println("Ouvindo na porta: " + porta);
    }

    public int getPorta() {
        return porta;
    }

    public void executar() {
        Thread inicial = new Thread(new Conect(servidor, this));
        inicial.start();
    }

    public Boolean processarRequisicao(String mensagem) {
        if (!mensagem.matches("'.+',[0-9]+")) {
            return false;
        }
        String[] partes = mensagem.split(",");
        return base.buscar(partes[0] + partes[1]);
    }
}
