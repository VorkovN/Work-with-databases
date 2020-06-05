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

