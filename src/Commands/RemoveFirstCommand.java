package Commands;
import Msg.MessageToServer;
import Route.MyCollection;
import Route.Route;

public class RemoveFirstCommand implements Command {

    MyCollection myCollection = null;
    String arg = null;
    Route newRoute = null;

    public MessageToServer execute(){
        MessageToServer msg = new MessageToServer();
        try{
            msg.setStr(myCollection.removeFirst());
            return msg;
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            msg.setStr("First element doesn't exist");
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
