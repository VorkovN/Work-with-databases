package Msg;

import java.io.Serializable;

public class MessageToServer implements Serializable {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

}
