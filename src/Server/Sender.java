package Server;

import Client.User;
import Msg.MessageToServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class Sender implements Runnable {

    SocketChannel socket = null;
    MessageToServer msg = null;
    User user = null;

    public Sender(SocketChannel socket, MessageToServer msg, User user) {
        this.socket = socket;
        this.msg = msg;
        this.user = user;
    }

    public Sender(SocketChannel socket, User user) {
        this.socket = socket;
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("send");
        try {
            try(ObjectOutputStream toClient = new ObjectOutputStream(socket.socket().getOutputStream())){
                if (msg != null){
                    toClient.writeObject(msg);
                    toClient.writeObject(user);
                }
                else{
                    toClient.writeObject(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
