package Server;

import Commands.*;
import Route.MyCollection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MyCollection myCollection = new MyCollection();

        System.out.println("Server is started");
        while(true){
            SocketAddress adress = new InetSocketAddress(8127);
            try(ServerSocketChannel channel = ServerSocketChannel.open()){
                channel.bind(adress);
                try(SocketChannel socket = channel.accept();
                    ObjectOutputStream toClient = new ObjectOutputStream(socket.socket().getOutputStream());
                    ObjectInputStream fromClient = new ObjectInputStream(socket.socket().getInputStream())){
                    Command cmd = (Command) fromClient.readObject();
                    cmd.setMyCollection(myCollection);
                    toClient.writeObject(cmd.execute());
                }
            } catch (ClassNotFoundException | IOException ignored) {}
        }
    }
}
