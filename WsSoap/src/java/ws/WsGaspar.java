/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import classes.Aluno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author henri
 */
@WebService(serviceName = "WsGaspar")
@Stateless()
public class WsGaspar {
    String driver = "org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost:5432/SoapBank";  
    String usuario = "postgres";  
    String password = "postgres"; 
    static Connection con;
    Statement stmt;

    /**
     * Função que verifica e retorna o saldo do cliente
     */
    @WebMethod(operationName = "retornaSaldo")
    public double verificaSaldo(@WebParam(name = "nome") String nome, @WebParam(name = "senha") String senha){
        double exibirSaldo = 0;
        String text;
        try{
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            this.con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conectou!");  
            Statement stm = con.createStatement();   
            
            //Função SQL que verifica o saldo do usuario e retorna o valor
            Statement stmt = null; 
            String sql = "SELECT saldo FROM cliente WHERE nome = " + "'" + nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){  //verifica o conteúdo da resposta
                text = rs.getString("saldo");
                exibirSaldo = Double.parseDouble(text);
                exibirSaldo = rs.getDouble("saldo");
            } 
        }catch(Exception e){
            System.err.print(e.getMessage());
            System.out.println("Falha no engano!");
        }
        return exibirSaldo;
    }
    
    /**
     * Função de depósito
     */
    @WebMethod(operationName = "Deposito")
    public boolean deposito(@WebParam(name = "valor") double valor, @WebParam(name = "nome") String nome) {
        double valorDeposito = valor;
        double saldoAtual = 0;
        try {
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conectou!");  
            Statement stm = con.createStatement();
            
            //Função SQL que realiza a interação com o banco e faz o depósito
            Statement stmt = null;
            String sql = "SELECT saldo FROM cliente WHERE nome = " + "'" + nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){ 
                saldoAtual = rs.getDouble("saldo");
            }
            System.out.println(saldoAtual);
            saldoAtual = saldoAtual + valorDeposito;
            System.out.println(saldoAtual);
            Statement stmt2 = null;
            String sql2 = "UPDATE cliente SET saldo ='" + String.valueOf(saldoAtual) + "' WHERE nome = " + "'" + nome +"'";
            stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while (rs2.next())
            {
                valorDeposito = rs.getDouble(sql2);
            }
        } catch (Exception e) {
            //System.err.print(e.getMessage());
            //System.out.println("Não funciona");
            //return false;
        }
        return true;
    }
    /**
     * Função que verifica o saldo e realiza o saque
     */
    @WebMethod(operationName = "Saque")
    public boolean saque(@WebParam(name = "valor") double valor, @WebParam(name = "nome") String nome) {
        double valorSaque = 0, valorAux = 0;
        double saldoAtual = 0, novoSaldo = 0;
        Banco b = new Banco();
        String text;
        try{
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conectou!");  
            Statement stm = con.createStatement();  

            //Função SQL que verifica o saldo do cliente e informa se pode sacar  
            Statement stmt = null;
            String sql = "SELECT saldo FROM clients WHERE nome = '" + nome + "'";
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    saldoAtual = rs.getDouble("saldo");
                }
                novoSaldo = saldoAtual - valor;
                if (novoSaldo < 0){
                    System.out.println("Impedido de Sacar");
                    return false;
                }else{
                    saldoAtual = saldoAtual - valor;
                    Statement stmt2 = null;
                    String sql2 = "UPDATE cliente SET saldo ='" + String.valueOf(saldoAtual) + "' WHERE nome = '"+ nome +"'";
                    stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()){
                        novoSaldo = rs.getDouble(sql2);
                    }
                }
        }catch(Exception e){
            System.err.print(e.getMessage());
            System.out.println("Saque bloqueado!");
            return false;
        }
        return true;
    }
    /**
     * Função que verifica o saldo e realiza o saque
     */
    @WebMethod(operationName = "cadastrarUsuario")
    public boolean cadastrarUser(@WebParam(name = "nome") String nome, @WebParam(name = "senha") String senha, @WebParam(name = "saldo") String saldo) {
        int cont = 0;
        try{
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conectou!");  
            Statement stm = con.createStatement(); 
            
            //Função SQL q verifica e insere um novo usuário
            Statement stmt = null;
            String sql = "INSERT INTO cliente VALUES(" + "'" + nome + "','" + senha + "'," + saldo + ")";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                cont++;
            }
        }catch(Exception e){
            System.err.print(e.getMessage());
            System.out.println("Impedido de Cadastrar!");
            //return false;
        }
        return true;
    }
    /**
     * Função que verifica e realiza a alteração do cadastro
     */
    @WebMethod(operationName = "alterarCadastro")
    public boolean alterCadastro(@WebParam(name = "nomeVelho") String nomeVelho, @WebParam(name = "novoNome") String novoNome, @WebParam(name = "senhaAntiga") String senhaAntiga, @WebParam(name = "novaSenha") String novaSenha) {
        try{
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conexão realizada com sucesso.");  
            Statement stm = con.createStatement(); 
                     
            //Função SQL que realiza a alteração da tabela cliente
            Statement stmt = null;
            String sql2 = "UPDATE clients SET nome = '" + novoNome + "'" + ", senha = '" + novaSenha + "'" + " WHERE nome = '" + nomeVelho + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql2);
            
        }catch(Exception e){
            System.err.print(e.getMessage());
            System.out.println("Impedido de alterar");
            //return false;
        }
        return true;
    }
    /**
     * Função que verifica e realiza a consulta do usuario
     */
    @WebMethod(operationName = "consultarUsuario")
    public String consultUser(@WebParam(name = "nome") String nome, @WebParam(name = "senha") String senha) {
        String resposta = "";
        double valor = 0;
        try {
            //Função que realiza a conexão com o banco
            System.setProperty("jdbc.Drivers", driver);
            this.con = DriverManager.getConnection(url, usuario, password);  
            System.out.println("Conectou!");  
            Statement stm = con.createStatement(); 
            
            String nomeUsuario = nome;
            String text;
            
            //Função SQL que realiza uma busca e retorna o nome do usuário e saldo
            Statement stmt = null;
            String sql = "SELECT saldo FROM clients WHERE nome = '" + nomeUsuario + "'";
            this.stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){  //verifica o conteúdo da resposta
                        text = rs.getString("saldo");
                        valor = Double.parseDouble(text);
                        valor = rs.getDouble("saldo");
                    }
        } catch(Exception e){
            System.out.print(e.getMessage());
            System.out.println("Impedido de Consultar");
            //return false;
        }
        resposta = "Nome = " + nome + ",   Senha = " + senha + ",   Saldo = " + valor; 
        return resposta;
    }
    /**
     * Operação de Web service
     */
    /*
    @WebMethod(operationName = "alunouenp")
    public Aluno alunouenp() {
        Aluno a = new Aluno();
        a.setRA("1234");
        a.setNome("1Berto");
        a.setIdade(20);
        //TODO write your implementation code here:
        return a;
    }
    
     @WebMethod(operationName = "listaaluno")
    public ArrayList<Aluno> listaaluno() {
        ArrayList<Aluno> lista = new ArrayList<>();
        Aluno a = new Aluno();
        a.setRA("1234");
        a.setNome("1Berto");
        a.setIdade(20);
        lista.add(a);
        Aluno b = new Aluno();
        a.setRA("12345");
        a.setNome("Jorge");
        a.setIdade(21);
        lista.add(b);
        
        return lista;
    }
    */
    
}
