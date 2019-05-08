/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClienteUDP {
    public static void main(String args[]) throws Exception {
        
        //Leitura do Usuário
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //Cria um socket para envio e receber mensagens
        DatagramSocket clientSocket = null;

        try {
            clientSocket = new DatagramSocket();
            String servidor = "172.16.103.9";
            int porta = 9876;

            //IP do Socket servidor - Formata para envio no DatagramPacket
            InetAddress IPAddress = InetAddress.getByName(servidor);

            //Bytes para envio e recebimento das mensagens
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            //Dados digitados pelo usuário
            System.out.println("Digite o texto a ser enviado ao servidor: ");
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();

            //Pacote
            //DatagramPacket Cria instância com um array de bytes 
            //contendo a mensagem, o comprimento da mensagem, o endereço IP e o número da porta destino do socket. 
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);

            System.out.println("Enviando pacote UDP para " + servidor + ":" + porta);

            //O DatagramSocket envia a mensagem : instância da classe DatagramPacket contendo uma mensagem e seu destino.             
            clientSocket.send(sendPacket);

            //Recebendo o Pacote
            //DatagramPacket Recebendo: dois argumentos
            //especificam um array onde a mensagem e o seu tamanho em bytes serão armazenados.
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //DatagramSocket Recebe DatagramPacket vazio no qual serão inseridos a mensagem, seu tamanho e sua origem. 
            clientSocket.receive(receivePacket);
            System.out.println("Pacote UDP recebido...");

            //Transforma a mensagem recebida em String
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("Texto recebido do servidor:" + modifiedSentence);

            //Fecha Conexão Socket
            clientSocket.close();
            System.out.println("Socket cliente fechado!");        
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }finally {
            if(clientSocket != null) 
                clientSocket.close();
        } 
    }
}


