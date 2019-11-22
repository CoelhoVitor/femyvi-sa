package connection;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import model.FileMessage;

public class FileRemove extends Thread {

    private final int port;

    private final FileMessageSocket fileMessageSocket = new FileMessageSocket();

    public FileRemove(Ports port) {
        this.port = port.getValue();
    }

    @Override
    public void run() {

        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "sakeystore.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "femyvi-sa");
        System.setProperty("javax.net.ssl.trustStore", "sakeystore.ks");
        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        while (true) {
            try {
                // receive file from SG
                SSLServerSocket server = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port);
                System.out.println("File Remove iniciado na porta " + port);
                SSLSocket socket = (SSLSocket) server.accept();
                FileMessage fm = fileMessageSocket.receiveFileMessage(socket);
                System.out.println(fm.toString());

                socket.close();
                server.close();

                // remove file
                String serverNum = port == Ports.REMOVE_1.getValue() ? "1" : "2";
                String filePath = serverNum + "/" + fm.getOwner() + "/" + fm.getFilename() + "." + fm.getFileType();
                File f = new File(filePath);
                Files.delete(Paths.get(f.getAbsolutePath()));
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
