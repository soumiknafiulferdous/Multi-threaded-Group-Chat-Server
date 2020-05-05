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
public class MultiThreadChatServer {
    
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  private static final int maxClientsCount = 10;
  private static final clientThread[] threads = new clientThread[maxClientsCount];

  public static void main(String args[]) throws IOException {
    int portNumber = 6001;
    System.out.println("The Server has started.\nWaiting for the client... ");
    serverSocket = new ServerSocket(portNumber);
    
    while (true) {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) 
          {
            (threads[i] = new clientThread(clientSocket, threads)).start();
             break;
          }
        }
        
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server Full. Please try again later");
          os.close();
          clientSocket.close();
        }
    }
  }
}
class clientThread extends Thread {
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }

  public void run() {
      try {
          int maxClientsCount = this.maxClientsCount;
          clientThread[] threads = this.threads;
          
          is = new DataInputStream(clientSocket.getInputStream());
          os = new PrintStream(clientSocket.getOutputStream());
 
          os.println("Enter your name please: ");
          String name = is.readLine().trim();
          os.println("Welcome " + name+ "\nWrite a message:");
          for (int i = 0; i < maxClientsCount; i++)
          {
              if (threads[i] != null && threads[i] != this)
              {  
                  threads[i].os.println("Server Message: " + name+ " has joined the group chat");
              }
          }

          while (true) {
              String line = is.readLine();
              if (line.contains("leave")) { 
                  break;                       
              }
              
              for (int i = 0; i < maxClientsCount; i++) {
                  if (threads[i] != null) {
                      threads[i].os.println("" + name + ": " + line);
                  }
              }
          }
          
          for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i] != this) {
                  threads[i].os.println("Server Message: " + name + "has left the group chat");
              }
          }
          
          os.println("Bye" + name);
          for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] == this) {
                  threads[i] = null;
              }
          }
          
          is.close();
          os.close();
          clientSocket.close();
      } catch (IOException ex) {
          Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}