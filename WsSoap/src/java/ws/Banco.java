package ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author henri
 */
class Banco {
   public String url = "jdbc:postgresql://localhost:5432/SoapBank";  
   public String usuario = "postgres";  
   public String senha = "postgres";  
   
   public Connection Conexao() throws SQLException
   {
        Connection conex;   
        conex = DriverManager.getConnection(url, usuario, senha);    
        System.out.println("Conectou!");  
        return conex; 
   }
}
