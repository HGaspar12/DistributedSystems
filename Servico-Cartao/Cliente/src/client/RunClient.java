package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class RunClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String endereco = "localhost";
        int porta = 12345;
        try {
            Socket conexao;
            conexao = new Socket(endereco, porta);
            System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);
            ObjectOutputStream saida;
            ObjectInputStream entrada;
            saida = new ObjectOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());
            String mensagem;
            
            //lendo a mensagem enviada pelo servidor
            mensagem = (String) entrada.readObject();
            System.out.println("Servidor: " + mensagem);
            
            //Mensagem de saida
            Scanner ler = new Scanner(System.in);
            System.out.println("Cliente (entre ' '):");
            mensagem = ler.nextLine();
            
            System.out.println("Numero da Conta:");
            mensagem += "," + ler.nextLine();
            
            System.out.println("CPF");
            mensagem  += "," + ler.nextLine();
            saida.writeObject(mensagem);
            saida.flush();
            
            //lendo a mensagem enviada pelo servidor
            mensagem = (String) entrada.readObject();
            System.out.println("Servidor: " + mensagem);
            saida.close();
            entrada.close();
            conexao.close();
        } catch (Exception e) {
            System.err.println("Conexão indisponível " + e.toString());
        }
    }

}
