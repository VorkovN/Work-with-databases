package Client;

import Commands.*;
import Msg.MessageToServer;
import Route.Route;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {

    private final String address;
    private final int port;
    private User user;



    private final Map<String, Command> commands = new HashMap<>();
    public ArrayList<String> history = new ArrayList<>();

    public CommandExecutor(){
        addCommand("add", new AddCommand());
        addCommand("clear", new ClearCommand());
        addCommand("count_less_than_distance", new CountLessThanDistanceCommand());
        addCommand("execute_script", new ExecuteScriptCommand(this));
        addCommand("exit", new ExitCommand());
        addCommand("filter_greater_than_distance", new FilterGreaterThanDistanceCommand());
        addCommand("help", new HelpCommand());
        addCommand("history", new HistoryCommand(this));
        addCommand("info", new InfoCommand());
        addCommand("remove_all_by_distance", new RemoveAllByDistanceCommand());
        addCommand("remove_by_id", new RemoveByIdCommand());
        addCommand("remove_first", new RemoveFirstCommand());
        addCommand("remove_greater", new RemoveGreaterCommand());
        addCommand("save", new SaveCommand());
        addCommand("show", new ShowCommand());
        addCommand("update", new UpdateCommand());

        System.out.println("Input host");
        address = new Scanner(System.in).nextLine();
        System.out.println("Host is " + address);
        System.out.println("Input port");
        port = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("Port is " + port);
    }


    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void execute(String action) {
        try(Socket socket = new Socket(address, port);
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream())) {
            String[] actionParts = action.split(" ");
            if (action.isEmpty()) {
                return;
            }
            if (actionParts.length == 1) {
                Command command = commands.get(actionParts[0]);
                if (command != null) {
                    historyList(actionParts[0]);
                    if (command instanceof HistoryCommand || command instanceof ExitCommand) {
                        command.execute();
                    } else {
                        if (command instanceof AddCommand) {
                            Route newRoute = null;
                            try {
                                newRoute = new Initialization().initialization(user);
                            } catch (NumberFormatException e) {
                                System.out.println("\nWrong input, please enter your values again!");
                            }
                            command.setNewRoute(newRoute);
                        }
                        toServer.writeObject(command);
                        toServer.writeObject(user);
                        System.out.println(((MessageToServer) fromServer.readObject()).getStr());
                        user = (User) fromServer.readObject();
                    }
                } else {
                    System.out.println("Commands.Command doesn't exist");
                }
                } else if (actionParts.length == 2) {
                    Command command = commands.get(actionParts[0]);
                    String arg = actionParts[1];
                    if (command != null) {
                        historyList(actionParts[0]);
                        command.setArg(arg);
                        if (command instanceof ExecuteScriptCommand) {
                            toServer.close();
                            fromServer.close();
                            command.execute();
                        } else {
                            if (command instanceof UpdateCommand) {
                                System.out.println(checkId(user, arg));
                                if (checkId(user, arg)){
                                    Route newRoute = null;
                                    try {
                                        newRoute = new Initialization().initialization(user);
                                    } catch (NumberFormatException e) {
                                        System.out.println("\nWrong input, please enter your values again!");
                                    }
                                    command.setNewRoute(newRoute);
                                    toServer.writeObject(command);
                                    toServer.writeObject(user);
                                    System.out.println(((MessageToServer) fromServer.readObject()).getStr());
                                    user = (User) fromServer.readObject();
                                }
                                else{
                                    System.out.println("This element isn't belongs to you");
                                }
                            }else{
                                toServer.writeObject(command);
                                toServer.writeObject(user);
                                System.out.println(((MessageToServer) fromServer.readObject()).getStr());
                                user = (User) fromServer.readObject();
                            }
                        }
                    } else {
                        System.out.println("Commands.Command doesn't exist");
                    }

                } else {
                    System.out.println("Wrong command input");
                }
            }catch(IOException | ClassNotFoundException ignored){
            }
        }


    public void historyList(String command){
        if(history.size() > 6) {
            history.remove(0);
        }
        history.add(command);
    }

    public User registrationAuthorization(User user){
        try(Socket socket = new Socket(address, port);
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream())) {
            Thread.sleep(200);
            toServer.writeObject(user);
            user = (User)fromServer.readObject();
            System.out.println(user.getIds());
        }
        catch (IOException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();
        }
        return user;
    }

    public boolean checkId(User user, String arg){
        System.out.println(user.getIds());
        int a = Integer.parseInt(arg);
        for (int i: user.getIds()) {
            if (i == a){
                return true;
            }
        }
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }
}