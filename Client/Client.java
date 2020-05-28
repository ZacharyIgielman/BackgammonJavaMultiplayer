// Java implementation for multithreaded chat client 
// Save file as Client.java 
  
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
public class Client  
{ 
    final static int ServerPort = 1234; 
  
    public static void main(String args[]) throws UnknownHostException, IOException  
    { 
        Scanner scn = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, ServerPort);
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
  

        Thread sendMessage = new Thread(new Runnable() { 
            @Override
            public void run() { 
                try {
                    while (true) {
                        String msg = scn.nextLine(); 
                        dos.writeUTF(msg); 
                    }
                } catch (IOException e) {
                    e.printStackTrace(); 
                    scn.close();
                    try {
                        s.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(1);
                }
            } 
        }); 

        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() {  
                try {
                    while (true) { 
                        String msg = dis.readUTF(); 
                        System.out.println(msg);
                    }
                } catch (IOException e) { 
                    e.printStackTrace(); 
                    scn.close();
                    try {
                        s.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(1);
                } 
            } 
        });

        sendMessage.start(); 
        readMessage.start(); 
    } 
}