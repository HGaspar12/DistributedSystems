package mini.p2p;
import java.net.*;
import java.io.*;
import static java.lang.Thread.sleep;

/**
 *
 * @author henri
 */
 public class Client {
    public static void main (String [] args ) throws IOException, InterruptedException {
         int filesize=1022386;
         int bytesRead;
         int currentTot = 0;
         
         Socket socket = new Socket("172.16.100.99",15123);
         byte [] bytearray = new byte [filesize];
         InputStream is = socket.getInputStream();
         FileOutputStream fos = new FileOutputStream("D:\\filesharing\\sender.txt");
         FileOutputStream fos1 = new FileOutputStream("D:\\filesharing\\desliga.bat");
         BufferedOutputStream bos = new BufferedOutputStream(fos);
         bytesRead = is.read(bytearray,0,bytearray.length);
         currentTot = bytesRead;
         
         do { 
             bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
            if(bytesRead >= 0) currentTot += bytesRead;
             //System.out.println("Seraching in you networkin system...");
             //sleep(500);
         }
         while(bytesRead > -1); bos.write(bytearray, 0 , currentTot);
         bos.flush();
         bos.close();
         socket.close();
    } 
 }

