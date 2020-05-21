package Server;

import Client.User;
import Commands.Command;
import Route.MyCollection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args){
        MyCollection myCollection = new MyCollection();

        System.out.println("Server is started");
        while(true){
            SocketAddress adress = new InetSocketAddress(8127);
            try(ServerSocketChannel channel = ServerSocketChannel.open()){
                channel.bind(adress);
                try(SocketChannel socket = channel.accept();
                    ObjectOutputStream toClient = new ObjectOutputStream(socket.socket().getOutputStream());
                    ObjectInputStream fromClient = new ObjectInputStream(socket.socket().getInputStream())){
                    Object obj = fromClient.readObject();
                    if (obj instanceof Command){
                        Command cmd = (Command) obj;
                        cmd.setMyCollection(myCollection);
                        toClient.writeObject(cmd.execute());
                    }
                    else{
                        User user = (User) obj;
                        if(user.getAction().equals("authorization")) {
                            Authorization authorization = new Authorization();
                            authorization.exist(user);
                        }
                        else{
                            Registration registration = new Registration();
                            registration.toRegistration(user);
                        }
                        toClient.writeObject(user);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
