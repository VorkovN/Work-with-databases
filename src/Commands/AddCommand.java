package Commands;
import Route.MyCollection;
import Msg.MessageToServer;
import Route.Route;

public class AddCommand implements Command{

    MyCollection myCollection = null;
    String arg = null;
    Route newRoute = null;

    public MessageToServer execute() {
        MessageToServer msg = new MessageToServer();
        System.out.println(newRoute.getNamel2());
        msg.setStr(myCollection.add(newRoute));
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
