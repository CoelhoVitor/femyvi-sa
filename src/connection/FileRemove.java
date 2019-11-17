package connection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FileMessage;

public class FileRemove extends Thread {

    private final int port;

    private final FileMessageSocket fileMessageSocket = new FileMessageSocket();

    public FileRemove(Ports port) {
        this.port = port.getValue();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // receive file from SG
                ServerSocket server = new ServerSocket(port);
                System.out.println("File Remove iniciado na porta " + port);
                Socket socket = server.accept();
                FileMessage fm = fileMessageSocket.receiveFileMessage(socket);
                System.out.println(fm.toString());

                socket.close();
                server.close();

                // remove file
                String serverNum = port == Ports.UPLOAD_1.getValue() ? "1" : "2";
                String filePath = serverNum + "/" + fm.getOwner() + "/" + fm.getFilename() + "_" + serverNum;
                File f = new File(filePath);
                Files.delete(Paths.get(f.getAbsolutePath()));
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
