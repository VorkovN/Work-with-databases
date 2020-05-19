package Commands;
import Msg.MessageToServer;
import Route.MyCollection;
import Route.Route;

import java.util.NoSuchElementException;

public class InfoCommand implements Command {

    MyCollection myCollection = null;
    String arg = null;
    Route newRoute = null;

    public MessageToServer execute(){
        MessageToServer msg = new MessageToServer();
        try{
            msg.setStr(myCollection.info());
            return msg;
        }catch (NumberFormatException | IndexOutOfBoundsException | NoSuchElementException e){
            msg.setStr("List doesn't have any elements");
            return msg;
        }
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
