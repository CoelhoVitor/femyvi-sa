
package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import model.UserMessage;

public class UserMessageSocket {
    
    public void sendUserMessage(Socket socket, UserMessage um) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(um);
    }
    
    public UserMessage receiveUserMessage(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        UserMessage um = (UserMessage) objectInputStream.readObject();
        return um;
    }
    
}
