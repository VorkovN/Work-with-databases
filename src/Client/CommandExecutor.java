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
    private static CommandExecutor commandExecutor = null;

    private final String address;
    private final int port;

    public static CommandExecutor getCommandExecutor(){
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor();

            commandExecutor.addCommand("add", new AddCommand());
            commandExecutor.addCommand("clear", new ClearCommand());
            commandExecutor.addCommand("count_less_than_distance", new CountLessThanDistanceCommand());
            commandExecutor.addCommand("execute_script", new ExecuteScriptCommand());
            commandExecutor.addCommand("exit", new ExitCommand());
            commandExecutor.addCommand("filter_greater_than_distance", new FilterGreaterThanDistanceCommand());
            commandExecutor.addCommand("help", new HelpCommand());
            commandExecutor.addCommand("history", new HistoryCommand());
            commandExecutor.addCommand("info", new InfoCommand());
            commandExecutor.addCommand("remove_all_by_distance", new RemoveAllByDistanceCommand());
            commandExecutor.addCommand("remove_by_id", new RemoveByIdCommand());
            commandExecutor.addCommand("remove_first", new RemoveFirstCommand());
            commandExecutor.addCommand("remove_greater", new RemoveGreaterCommand());
            commandExecutor.addCommand("save", new SaveCommand());
            commandExecutor.addCommand("show", new ShowCommand());
            commandExecutor.addCommand("update", new UpdateCommand());
        }
        return commandExecutor;
    }


    private Map<String, Command> commands = new HashMap<>();
    public ArrayList<String> history = new ArrayList<>();

    public CommandExecutor(){
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
                                newRoute = new Initialization().initialization();
                            } catch (NumberFormatException e) {
                                System.out.println("\nWrong input, please enter your values again!");
                            }
                            command.setNewRoute(newRoute);
                        }
                        toServer.writeObject(command);
                        System.out.println(((MessageToServer) fromServer.readObject()).getStr());
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
                            Route newRoute = null;
                            try {
                                newRoute = new Initialization().initialization();
                            } catch (NumberFormatException e) {
                                System.out.println("\nWrong input, please enter your values again!");
                            }
                            command.setNewRoute(newRoute);
                        }
                        toServer.writeObject(command);
                        System.out.println(((MessageToServer) fromServer.readObject()).getStr());
                    }
                } else {
                    System.out.println("Commands.Command doesn't exist");
                }

            } else {
                System.out.println("Wrong command input");
            }
        }catch (IOException | ClassNotFoundException ignored){}
    }



    public void historyList(String command){
        if(history.size() > 6) {
            history.remove(0);
        }
        history.add(command);
    }
}