package Commands;

import Client.CommandExecutor;
import Msg.MessageToServer;
import Route.MyCollection;
import Route.Route;

public class HistoryCommand implements Command {

    MyCollection myCollection = null;
    String arg = null;
    Route newRoute = null;

    CommandExecutor commandExecutor;
    public HistoryCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public MessageToServer execute(){
        commandExecutor.history.forEach(System.out::println);
        return null;
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
