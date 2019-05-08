/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteservidor;
import java.rmi.RemoteException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Henrique Gaspar
 */
public class RMI extends UnicastRemoteObject implements Servico {

    Bank bd;

    public RMI() throws RemoteException {
        
        super();
        bd  = new Bank();
        
    }

    public static void main(String[] args) throws SQLException {
            
        try {
            RMI servidor = new RMI();            
            Registry registro = LocateRegistry.createRegistry(4000);
            registro.bind("TesteCurry1", servidor);
            System.out.println("Servidor Online");
            
            

        } catch (Exception ex) {
            System.out.println("Não foi possivel estabelecer conexao " + ex.getMessage());
        }

    }
    @Override
    public void sacar(String numConta, String valor) throws SQLException,RemoteException{
        bd.sacar(numConta, valor);
        transacoes( "Cliente sacou "+valor+" em sua conta => "+numConta+", no dia "+getDataHora());
    }
    @Override
    public void depositar(String numConta, String valor) throws SQLException,RemoteException{
    bd.depositar(numConta, valor);
     transacoes( "Cliente depositou "+valor+" em sua conta => "+numConta+", no dia "+getDataHora());
    
    }
    @Override
    public void editarDados(String num_conta,String saldo)throws RemoteException{
        try {
            bd.updateDados(num_conta, saldo);
        } catch (SQLException ex) {
            Logger.getLogger(RMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Atualizou");
    
    }
  
    @Override
    public void connectionBanc() throws RemoteException {
        try {
            bd.conexao();
        } catch (SQLException ex) {
            Logger.getLogger(RMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Conectado");

    }
    
    @Override
    public void desconectar()throws SQLException,RemoteException{
    bd.desconecta();
    }
    
    @Override
    public String getDataHora() throws RemoteException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return sdf.format(Calendar.getInstance().getTime());
    }


    @Override
    public void gravarDados(String nome, String numconta, String saldo, String senha,String agencia) throws RemoteException, SQLException {
        bd.escreverBanco(nome, numconta, saldo,senha,agencia);
        transacoes( "Cliente fez uma transacao de "+saldo+" para sua conta => "+numconta+", no dia "+getDataHora());

    }
    @Override
    public String pegarDados(String numConta)throws RemoteException{
        String valor = "";
        try {
            valor =  bd.pegarDados(numConta);
        } catch (SQLException ex) {
            Logger.getLogger(RMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    return valor;
    }
    @Override
    public String senha(String numConta) throws SQLException, RemoteException{
    return bd.pegarSenha(numConta);
    
    }
    @Override
    public String saldo(String numConta) throws SQLException,RemoteException{
    return bd.pegarSaldo(numConta);
    }
    @Override
    public String nome(String numConta) throws SQLException,RemoteException{
    return bd.pegarNome(numConta);
    }
    
     public void transacoes(String valor){
         System.out.println(valor);
     
     }   

    @Override
    public String returnAgencia(String num_conta) throws SQLException, RemoteException {
       return bd.returnAgencia(num_conta);
    }

    @Override
    public String TransferirConta(String numConta) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
        
}
