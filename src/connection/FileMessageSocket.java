package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import model.FileMessage;

public class FileMessageSocket {

    public void sendFileMessage(Socket socket, FileMessage fm) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(fm);
    }

    public void sendFileMessageList(Socket socket, ArrayList<FileMessage> fms) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(fms);
    }

    public FileMessage receiveFileMessage(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        FileMessage fm = (FileMessage) objectInputStream.readObject();
        return fm;
    }

}
