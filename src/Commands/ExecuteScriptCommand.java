package Commands;
import Client.CommandExecutor;
import Msg.MessageToServer;
import Route.MyCollection;
import Route.Route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExecuteScriptCommand implements Command {

    MyCollection myCollection = null;
    String arg = null;
    Route newRoute = null;

    CommandExecutor commandExecutor;
    public ExecuteScriptCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    private ArrayList<String> scripts = new ArrayList<String>();

    public MessageToServer execute() {
        MessageToServer msg = new MessageToServer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arg))) {
            String line;
            if (!scripts.contains("execute_script " + arg)) {
                scripts.add("execute_script " + arg);
            }
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    System.out.println(">>>" + line);
                }
                if (!scripts.contains(line)) {
                    if (line.split(" ")[0].equals("execute_script")) {
                        scripts.add(line);
                    }
                    commandExecutor.execute(line);
                } else {
                    System.out.println("script " + line + " has already done");
                }
            }
            scripts.remove(scripts.size() - 1);
            System.out.println();
        } catch (IOException e) {
            System.out.println("File not found, please, input existent file");
        }
        return msg;
    }

    public void setMyCollection(MyCollection myCollection) {
        this.myCollection = myCollection;
    }

    public MyCollection getMyCollection() {
        return myCollection;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getArg() {
        return arg;
    }

    public void setNewRoute(Route newRoute) {
        this.newRoute = newRoute;
    }

    public Route getNewRoute() {
        return newRoute;
    }

}
