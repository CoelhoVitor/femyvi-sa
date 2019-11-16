package connection;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    
    @Override
    public void run() {
        try {
            int port = Ports.DOWNLOAD.getValue();
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor iniciado na porta " + port);  
            
            do {
                Socket client = server.accept();
                System.out.println("Cliente conectado do IP " + client.getInetAddress().getHostAddress());
                OutputStream os = client.getOutputStream();
                InputStream is = client.getInputStream();
                handleClient(is, 6022386);
                client.close();
            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleClient(InputStream input, Integer fileSize) throws IOException {
        int bytesRead;
        int current = 0;
        byte[] mybytearray = new byte[fileSize];

        FileOutputStream fos = new FileOutputStream("def");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        bytesRead = input.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = input.read(mybytearray, current,
                    (mybytearray.length - current));
            if (bytesRead >= 0)
                current += bytesRead;
        } while (bytesRead > -1);

        bos.write(mybytearray, 0, current);
        bos.flush();
        bos.close();
    }
    
}
