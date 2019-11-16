package connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HealthChecker extends Thread {
    
    @Override
    public void run() {
        try {
            int port = Ports.HEALTHCHECK.getValue();
            ServerSocket server = new ServerSocket(port);
            System.out.println("Healthcheck iniciado na porta " + port);
            
            do {
                Socket client = server.accept();
                System.out.println("Cliente conectado do IP " + client.getInetAddress().getHostAddress());
                handleClient(client.getOutputStream());
                client.close();
            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(HealthChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleClient(OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("Server is up!\n");
        bw.flush();
    }
    
}
