package Server;

import Client.User;
import Commands.Command;
import Msg.MessageToServer;
import Route.MyCollection;

import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler implements Runnable  {

    SocketChannel socket =null;
    MyCollection myCollection = null;
    Command command = null;
    User user = null;
    ObjectOutputStream toClient;
    static ExecutorService executeIt = Executors.newCachedThreadPool();

    public MessageHandler(SocketChannel socket, MyCollection myCollection, Command command, User user) {
        this.socket = socket;
        this.myCollection = myCollection;
        this.command = command;
        this.user = user;
        this.toClient = toClient;
    }

    public MessageHandler(SocketChannel socket, MyCollection myCollection, User user) {
        this.socket = socket;
        this.myCollection = myCollection;
        this.user = user;
    }

    @Override
    public void run() {
        if (command != null) {
            command.setMyCollection(myCollection);
            myCollection.setUser(user);
            MessageToServer msg = command.execute();
            executeIt.execute(new Sender(socket, msg, user));//
        }
        else{
            if (user.getAction().equals("authorization")) {
                Authorization authorization = new Authorization();
                authorization.exist(user);
                if (user.getStatus()) {
                    myCollection.getIds(user);
                }
                executeIt.execute(new Sender(socket, user));//
            } else {
                Registration registration = new Registration();
                registration.toRegistration(user);
                executeIt.execute(new Sender(socket, user));//
            }

        }
    }
}
