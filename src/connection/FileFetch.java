package connection;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.File;
import java.io.IOException;
import java.security.Security;
import model.FileMessage;
import model.UserMessage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import util.FileUtils;

public class FileFetch extends Thread {

    private final int port;

    private final UserMessageSocket userMessageSocket = new UserMessageSocket();

    private final FileMessageSocket fileMessageSocket = new FileMessageSocket();

    public FileFetch(Ports port) {
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
                // receive user from SG
                SSLServerSocket server = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port);
                SSLSocket socket = (SSLSocket) server.accept();
                System.out.println("File Fetch iniciado na porta " + port);
                UserMessage um = userMessageSocket.receiveUserMessage(socket);
                System.out.println(um.toString());

                // read files
                String serverNum = port == Ports.FETCH_1.getValue() ? "1" : "2";
                String filePath = serverNum + "/" + um.getLogin() + "/";
                File folder = new File(filePath);
                File[] files = folder.listFiles();

                ArrayList<FileMessage> fileMessages = new ArrayList<>();
                if (files == null) {
                    folder.mkdirs();
                } else {
                    for (File f : files) {
                        fileMessages.add(FileUtils.fileToFileMessage(f));
                    }
                }
                fileMessageSocket.sendFileMessageList(socket, fileMessages);

                socket.close();
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
