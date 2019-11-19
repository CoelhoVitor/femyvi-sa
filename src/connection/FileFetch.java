package connection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.FileMessage;
import model.UserMessage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            while (true) {
                // receive user from SG
                ServerSocket server = new ServerSocket(port);
                System.out.println("File Fetch iniciado na porta " + port);
                Socket socket = server.accept();
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
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
