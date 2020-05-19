package Route;

import java.io.Serializable;

public class Location2 implements Serializable {
    private int xl2;
    private Float yl2; //Поле не может быть null
    private String namel2; //Длина строки не должна быть больше 968, Поле может быть null

    public int getXl2() {
        return xl2;
    }

    public Float getYl2() {
        return yl2;
    }

    public String getNamel2() {
        return namel2;
    }

    public void setXl2(int xl2) {
        this.xl2 = xl2;
    }

    public void setYl2(Float yl2) {
        this.yl2 = yl2;
    }

    public void setNamel2(String namel2) {
        this.namel2 = namel2;
    }

    @Override
    public String toString() {
        return "xl2 = " + getXl2() + "; yl2 = " + getYl2() + "; namel2: " + getNamel2();
    }
}
