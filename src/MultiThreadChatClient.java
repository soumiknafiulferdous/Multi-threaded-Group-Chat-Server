import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SOUMIK
 */
public class MultiThreadChatClient implements Runnable {
  private static Socket clientSocket = null;
  private static PrintStream os = null;
  private static DataInputStream is = null;
  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) throws IOException {
    int portNumber = 6001;
    String host = "localhost";
    
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
      
    if (clientSocket != null && os != null && is != null) {
        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {             
          os.println(inputLine.readLine().trim());
        }
        os.close();
        is.close();
        clientSocket.close();
    }
  }
  
  public void run() {  
      try {
          String responseLine;
          while ((responseLine = is.readLine()) != null) {
              System.out.println(responseLine);
              if (responseLine.indexOf("Bye") != -1)
                  break;
          }
          closed = true;
      } catch (IOException ex) {
          Logger.getLogger(MultiThreadChatClient.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}