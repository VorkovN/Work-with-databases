package Server;

import Route.MyCollection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    static ExecutorService executeIt = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        MyCollection myCollection = new MyCollection();
        SocketAddress adress = new InetSocketAddress(8127);
        while (true) {
            try (ServerSocketChannel channel = ServerSocketChannel.open()){
                channel.bind(adress);
                SocketChannel socket = channel.accept();
                if (socket.isOpen()) {
                    executeIt.execute(new Server(socket, myCollection));
                    System.out.print("Connection accepted.");
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


    /*public static void main(String[] args) {
        MyCollection myCollection = new MyCollection();
        System.out.println("Server is started");
        SocketAddress adress = new InetSocketAddress(8127);
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            channel.bind(adress);
            while (true) {
                try (SocketChannel socket = channel.accept();
                     ObjectOutputStream toClient = new ObjectOutputStream(socket.socket().getOutputStream());
                     ObjectInputStream fromClient = new ObjectInputStream(socket.socket().getInputStream())) {
                    Object obj = fromClient.readObject();
                    if (obj instanceof Command) {
                        User user = (User) fromClient.readObject();
                        Command cmd = (Command) obj;
                        cmd.setMyCollection(myCollection);
                        myCollection.setUser(user);
                        executeIt.execute(new Sender(socket, cmd.execute(), user));//
                    } else {
                        User user = (User) obj;
                        if (user.getAction().equals("authorization")) {
                            Authorization authorization = new Authorization();
                            authorization.exist(user);
                            if (user.getStatus()) {
                                myCollection.getIds(user);
                            }
                        } else {
                            Registration registration = new Registration();
                            registration.toRegistration(user);
                        }
                        System.out.println(user.getIds());
                        executeIt.execute(new Sender(socket, null, user));//
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
