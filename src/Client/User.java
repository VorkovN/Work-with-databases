package Client;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private final String name;
    private final String password;
    private boolean status;
    private String action;
    private Set<Integer> ids;;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        ids = new HashSet<Integer>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void addId(int id){ ids.add(id); }

    public void removeId(int id){ ids.remove(id); }

    public Set<Integer> getIds() { return ids; }
}
