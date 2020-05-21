package Route;

import java.io.Serializable;

public class Route implements Serializable {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null
    private String date; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location1 from = new Location1(); //Поле не может быть null
    private Location2 to = new Location2(); //Поле может быть null
    private Float distance; //Значение поля должно быть больше 1
    private String user = null;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getX() {
        return coordinates.getX();
    }

    public Double getY() {
        return coordinates.getY();
    }

    public String getDate() {
        return date;
    }

    public Long getXl1() {
        return from.getXl1();
    }

    public Double getYl1() {
        return from.getYl1();
    }

    public long getZl1() {
        return from.getZl1();
    }

    public int getXl2() {
        return to.getXl2();
    }

    public Float getYl2() {
        return to.getYl2();
    }

    public String getNamel2() {
        return to.getNamel2();
    }

    public float getDistance() {
        return distance;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Location1 getFrom() {
        return from;
    }

    public Location2 getTo() {
        return to;
    }

    public String getUser() { return user; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(Float x) {
        coordinates.setX(x);
    }

    public void setY(Double y) {
        coordinates.setY(y);
    }

    public void setDate(String date) { this.date = date; }

    public void setXl1(Long xl1) {
        from.setXl1(xl1);
    }

    public void setYl1(Double yl1) {
        from.setYl1(yl1);
    }

    public void setZl1(long zl1) {
        from.setZl1(zl1);
    }

    public void setXl2(int xl2) {
        to.setXl2(xl2);
    }

    public void setYl2(Float yl2) {
        to.setYl2(yl2);
    }

    public void setNamel2(String namel2) {
        to.setNamel2(namel2);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setUser(String user) { this.user = user; }

    @Override
    public String toString() {
        return "\nid = " + getId() + "\nName:  " + getName() + "\nRoute.Coordinates:   " + getCoordinates() + "\nDate:   " + getDate() + "\nRoute.Location1:   " + getFrom() + "\nRoute.Location2:   " + getTo() + "\ndistance = " + getDistance();
    }

}
