/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Fábio Moreno
 */
public class UDPServer {

    /**
     * @param args the command line arguments
     */
	public static void main(String args[]) throws Exception {
 
		int porta = 9876;
		int numConn = 1;
		
                //Datagram Socket e a porta que está configurada para receber dados do cliente
		DatagramSocket serverSocket = new DatagramSocket(porta);
 
                //Bytes para envio e retorno das mensagens
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
 
		while (true) {
                        //Pacote
                        //DatagramPacket para receber: dois argumentos que especificam um array	onde a mensagem
                        //e o seu tamanho em bytes serão armazenados.	 
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("Esperando por datagrama UDP na porta " + porta);
			
                        //DatagramSocket utiliza o DatagramPacket vazio. Argumento: mensagem, tamanho e origem.	
                        serverSocket.receive(receivePacket);                                                
			System.out.print("Datagrama UDP [" + numConn + "] recebido...");
 
                        //Pega a mensagem do Pacote e transforma em string (Byte para String)
			String sentence = new String(receivePacket.getData());
			System.out.println(sentence);
			
                        //Pega o endereço do socket destinatário
			InetAddress IPAddress = receivePacket.getAddress();
 
                        //Pega a porta utilizada pelo socket destinatário
			int port = receivePacket.getPort();
 
                       //Transforma a mensagem original em texto de caixa alta
                       //e converte para String
			String capitalizedSentence = sentence.toUpperCase();                        
			
                        //Transforma a mensagem em String para Bytes
                        sendData = capitalizedSentence.getBytes();
 
                        //Prepara o Pacote para envio - DatagramPacket
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);			
			System.out.print("Enviando " + capitalizedSentence + "...");
 
                        //Utiliza o DatagramSocket para enviar o Pacote DatagramPacket
			serverSocket.send(sendPacket);
			System.out.println("OK\n");
		}
	}
    
}
