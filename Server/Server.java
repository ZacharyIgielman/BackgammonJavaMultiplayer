import java.io.*; 
import java.util.*; 
import java.net.*; 
  
public class Server { 
  
    // Vector to store active clients 
    static Vector<ClientHandler> ar = new Vector<>();
  
    public static void main(String[] args) throws IOException  {
        ServerSocket ss = new ServerSocket(1234); 
        Socket s; 
          
        while (true)  {
            s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
            ClientHandler mtch = new ClientHandler(s, dis, dos); 
            Thread t = new Thread(mtch);
            t.start();
            ar.add(mtch);
        } 
    } 
} 

class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in);
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean isloggedin; 
      
    // constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos;
        this.s = s;
        this.isloggedin=true;
    } 

    @Override
    public void run() { 
        String received; 
        while (true) { 
            try {
                received = dis.readUTF();

                for (ClientHandler mc : Server.ar) {
                    mc.dos.writeUTF(received);
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        }
    } 
}