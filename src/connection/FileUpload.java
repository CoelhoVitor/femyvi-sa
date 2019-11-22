package connection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import model.FileMessage;

public class FileUpload extends Thread {

    private final int port;

    private final FileMessageSocket fileMessageSocket = new FileMessageSocket();

    public FileUpload(Ports port) {
        this.port = port.getValue();
    }

    @Override
    public void run() {
        System.setProperty("javax.net.ssl.keyStore", "sakeystore.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "femyvi-sa");
        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        while (true) {
            try {

                // receive file from SG
                SSLServerSocket server = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port);
                System.out.println("File Upload iniciado na porta " + port);
                SSLSocket socket = (SSLSocket) server.accept();
                FileMessage fm = fileMessageSocket.receiveFileMessage(socket);
                System.out.println(fm.toString());

                socket.close();
                server.close();

                // write file
                String serverNum = port == Ports.UPLOAD_1.getValue() ? "1" : "2";
                String filePath = serverNum + "/" + fm.getOwner() + "/" + fm.getFilename() + "." + fm.getFileType();
                File f = new File(filePath);
                f.getParentFile().mkdirs();
                f.createNewFile();
                Files.write(Paths.get(f.getAbsolutePath()), fm.getContent());
            } catch (IOException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
