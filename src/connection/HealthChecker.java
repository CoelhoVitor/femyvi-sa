package connection;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class HealthChecker extends Thread {

    private int port;

    public HealthChecker(Ports port) {
        this.port = port.getValue();
    }

    @Override
    public void run() {
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "sakeystore.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "femyvi-sa");

        while (true) {
            try {
                System.out.println("Healthcheck iniciado na porta " + port);

                SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

                SSLServerSocket server = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port);
                SSLSocket client = (SSLSocket) server.accept();
                System.out.println("Cliente conectado do IP " + client.getInetAddress().getHostAddress());
                handleClient(client.getOutputStream());
                client.close();
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(HealthChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void handleClient(OutputStream os) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject("Server is up!");
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
