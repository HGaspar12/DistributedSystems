package clienteservidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.Random;

public class Bank2 {

    static Statement stm;
    static PreparedStatement stmt;
    static ResultSet rs;
    String driver = "org.postgresql.Driver";
    String caminho = "jdbc:postgresql://localhost:5432/agenciaTest";
    String user = "postgres";
    String senha = "postgres";    
    static Connection con;

    //Função que realiza a conexão com o BD no POSTGRESQL
    public void conexao() throws SQLException {

        try {
            System.setProperty("jdbc.Drivers", driver);
            this.con = DriverManager.getConnection(caminho, user, senha);
            System.out.println("BD 2 conectado");
        } catch (SQLException sqex) {
            System.out.println("Falha na conexão " + sqex.getMessage());

        }

    }
    
    //Retorna a agencia do cliente desejado
    public String returnAgencia(String num_conta) throws SQLException{
        String agencia = "";
        //slq faz a busca da agencia do cliente pelo numero da conta
        String sql = "SELECT agencia FROM cliente_agencia2 WHERE num_conta ='"+num_conta+"'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            agencia = this.rs.getString("agencia");
            return agencia;
        }
    return agencia;
    }
     
    //Função de depósito
    public void depositar(String numConta, String valor) throws SQLException {
        String saldo = "";
        String novoSaldo = "";
        //Busca o saldo do cliente desejado e realiza a gravação do novo valor
        String sql = "SELECT saldo FROM cliente_agencia2 WHERE num_conta = '" + numConta + "'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            saldo = this.rs.getString("saldo");
        }
        System.out.println("valor do saldo eh de: "+saldo);
        novoSaldo = Integer.toString(Integer.parseInt(saldo) + Integer.parseInt(valor));
        updateDados(numConta, novoSaldo);

    }
    
    //Função que realiza o saque
    public void sacar(String numConta, String valor) throws SQLException {
        String saldo = "";
        String novoSaldo = "";
        //slq faz a consulta do saldo do cliente pelo num_conta e pergunta a quantia desejada
        String sql = "SELECT saldo FROM cliente_agencia2 WHERE num_conta = '" +numConta+ "'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            saldo = this.rs.getString("saldo");
        }
        System.out.println(saldo);

        if (Integer.parseInt(saldo) < Integer.parseInt(valor)) {
            JOptionPane.showMessageDialog(null, "Valor a ser sacado passa do valor dentro da conta");
        } else {
            novoSaldo = Integer.toString(Integer.parseInt(saldo) - Integer.parseInt(valor));
            updateDados(numConta, novoSaldo);

        }

    }
    
    //Função q desconecta o banco
    public void desconecta() {
        try {
            this.con.close();
        } catch (SQLException esx) {
            System.out.println("Falha na desconexão");

        }

    }
    
    //Função que vai retornar os dados do cliente (num_conta, nome, saldo)
    public String pegarDados(String numConta) throws SQLException {
        String valor = "";
        String sql = "SELECT * FROM cliente_agencia2 WHERE num_conta = '" +numConta+ "'";
        Statement stm = (Statement) this.con.createStatement();
        this.rs = stm.executeQuery(sql);
        while (this.rs.next()) {
            valor = this.rs.getString("num_conta") + " / " + this.rs.getString("saldo") + " / " + "Nome => " + this.rs.getString("nome");
        }

        return valor;

    }
    public String toString(char[] valores){
        String temp ="";
        for(int i =0;i<valores.length;i++){
        temp+=valores[i];        
        }
        return temp;
    }
    
    //Função que cadastra um novo cliente 
    public void escreverBanco(String nome, String num_conta, String saldo, String senha, String agencia) {
        try {
            String sql = "insert into cliente_agencia2 (nome,num_conta,saldo,senha,cod_cli,agencia)" + "values(?,?,?,?,?,?)";
            this.stmt = this.con.prepareStatement(sql);
            this.stmt.setString(1, nome);
            this.stmt.setString(2, num_conta);
            this.stmt.setString(3, saldo);
            this.stmt.setString(4, senha);           
            Random random = new Random();
            int valor = random.nextInt();
            if (valor < 0) {
                valor = valor * (-1);
                this.stmt.setString(5, Integer.toString(valor));
            } else {
                this.stmt.setString(5, Integer.toString(valor));
            }
             this.stmt.setString(6, agencia);

            this.stmt.execute();
            this.stmt.close();

           
        } catch (SQLException ex) {
            System.out.println("algo deu errado" + ex.getMessage());

        }

    }
    
    //Função que atualiza o saldo do cliente
    public void updateDados(String num_conta, String saldo) throws SQLException {
        String sql = "update cliente_agencia2 "
                + "set saldo = ? where  num_conta = '" + num_conta + "'";
        this.stmt = this.con.prepareStatement(sql);
        this.stmt.setString(1, saldo);
        this.stmt.execute();
        this.stmt.close();

    }
    
    //Função que faz a verificação da senha inserida com o banco
    public String pegarSenha(String numConta) throws SQLException {
        String valor = "";
        String sql = "SELECT senha FROM cliente_agencia2 WHERE num_conta = '" +numConta + "'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            valor = this.rs.getString("senha");
        }

        return valor;
       

    }
    
    //Função que retora o saldo do cliene
    public String pegarSaldo(String numConta) throws SQLException{
        String valor = "";
        String sql = "SELECT saldo FROM cliente_agencia2 WHERE num_conta = '" +numConta + "'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            valor = this.rs.getString("saldo");
        }

        return valor;
    }
    
    //Função que retorna qual é o cliente, pelo num_conta
    public String pegarNome(String numConta) throws SQLException{
        String valor = "";
        String sql = "SELECT nome FROM cliente_agencia2 WHERE num_conta = '" + numConta + "'";
        this.stm = (Statement) this.con.createStatement();
        this.rs = this.stm.executeQuery(sql);
        while (this.rs.next()) {
            valor = this.rs.getString("nome");
        }

        return valor;
    }

    Connection Conexao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
        
}
