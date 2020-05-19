package Route;

import java.io.Serializable;

public class Location1 implements Serializable {

    private Long xl1; //Поле не может быть null
    private Double yl1; //Поле не может быть null
    private long zl1;

    public Long getXl1() {
        return xl1;
    }

    public Double getYl1() {
        return yl1;
    }

    public long getZl1() {
        return zl1;
    }

    public void setXl1(Long xl1) {
        this.xl1 = xl1;
    }

    public void setYl1(Double yl1) {
        this.yl1 = yl1;
    }

    public void setZl1(long zl1) {
        this.zl1 = zl1;
    }

    @Override
    public String toString() {
        return "xl1 = " + getXl1() + "; yl1 = " + getYl1() + "; zl1 = " + getZl1();
    }
}
