package mini.p2p;
import java.net.*;
import java.io.*;
/**
 *
 * @author henri
 */
public class Server {
       public static void main (String [] args ) throws IOException {
          ServerSocket serverSocket = new ServerSocket(15123);
          Socket socket = serverSocket.accept();
          System.out.println("Accepted connection : " + socket);
          
          File transferFile = new File ("W:\\filesharing\\sender.txt");
          File transferFile1 = new File ("W:\\filesharing\\desliga.bat");
          byte [] bytearray = new byte [(int)transferFile.length()];
          FileInputStream fin = new FileInputStream(transferFile);
          BufferedInputStream bin = new BufferedInputStream(fin);
          bin.read(bytearray,0,bytearray.length);
          
          OutputStream os = socket.getOutputStream();
          System.out.println("Sending Files...");
          
          os.write(bytearray,0,bytearray.length);
          os.flush();
          socket.close();
          System.out.println("File transfer complete"); 
      }
   
}
