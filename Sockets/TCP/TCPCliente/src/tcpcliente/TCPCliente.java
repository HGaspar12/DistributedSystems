/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpcliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Fábio Moreno
 */
public class TCPCliente {
 
    public static void main(String[] args) {   
        String endereco = "172.16.103.9";
        int porta = 12345;
        ObjectOutputStream saida;
        ObjectInputStream entrada;
        Socket conexao;
        Scanner ler = new Scanner(System.in);
        String mensagem = "";
        try {
            conexao = new Socket(endereco, porta);
            System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);
            System.out.println("Digite: FIM para encerrar a conexao");
 
            // ligando as conexoes de saida e de entrada
            saida = new ObjectOutputStream(conexao.getOutputStream());
            //saida.flush();
            entrada = new ObjectInputStream(conexao.getInputStream());
 
            //lendo a mensagem enviada pelo servidor
            mensagem = (String) entrada.readObject();
            System.out.println("Servidor>> "+mensagem);
 
            do {
                //Mensagem de saida
                System.out.print("..: ");
                mensagem = ler.nextLine();
                saida.writeObject(mensagem);                
                saida.flush();
                
                //lendo a mensagem enviada pelo servidor
                mensagem = (String) entrada.readObject();
                System.out.println("Servidor>> "+mensagem);
            } while (!mensagem.equals("FIM"));
 
            saida.close();
            entrada.close();
            conexao.close();
 
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }
}