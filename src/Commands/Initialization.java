package Commands;

import Client.User;
import Exceptions.UnacceptableNumberException;
import Route.Route;

import java.time.LocalDate;
import java.util.Scanner;

public class Initialization {
    public Route initialization(User user) {
        Route newRoute = new Route();
        Scanner sc = new Scanner(System.in);

        date(newRoute);
        name(newRoute, sc);
        x(newRoute, sc);
        y(newRoute, sc);
        xl1(newRoute, sc);
        yl1(newRoute, sc);
        zl1(newRoute, sc);
        xl2(newRoute, sc);
        yl2(newRoute, sc);
        namel2(newRoute, sc);
        distance(newRoute, sc);
        user(newRoute, sc, user);
        return newRoute;
    }

    public void name(Route newRoute, Scanner sc) {
        try {
            System.out.print("(String) name = ");
            newRoute.setName(sc.nextLine());
        } catch (NumberFormatException e) {
            name(newRoute, sc);
        }
    }

    public void x(Route newRoute, Scanner sc) {
        try{
            System.out.print("(Float) x = ");
            float x = Float.parseFloat(sc.nextLine());
            if (!((Float.MIN_VALUE < Math.abs(x)) && (Math.abs(x) < Float.MAX_VALUE))) {
                x(newRoute, sc);
                System.out.println("Wrong input");
            } else {
                newRoute.setX(x);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            x(newRoute, sc);
        }
    }

    public void y(Route newRoute, Scanner sc){
        try{
            System.out.print("(Double) y = ");
            double y = Double.parseDouble(sc.nextLine());
            if (!((Double.MIN_VALUE < Math.abs(y)) && (Math.abs(y) < Double.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setY(y);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            y(newRoute, sc);
        }
    }

    public void date(Route newRoute) {
        LocalDate date = LocalDate.now();
        newRoute.setDate(date.toString());
        System.out.println("(LocalDatedate) = " + date);
    }

    public void xl1(Route newRoute, Scanner sc) {
        try{
            System.out.print("(Long) xl1 = ");
            long xl1 = Long.parseLong(sc.nextLine());
            if (!((Long.MIN_VALUE < xl1) && (xl1 < Long.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setXl1(xl1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            xl1(newRoute, sc);
        }
    }

    public void yl1(Route newRoute, Scanner sc) {
        try{
            System.out.print("(Double) yl1 = ");
            double yl1 = Double.parseDouble(sc.nextLine());
            if (!((Double.MIN_VALUE < Math.abs(yl1)) && (Math.abs(yl1) < Double.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setYl1(yl1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            yl1(newRoute, sc);
        }
    }

    public void zl1(Route newRoute, Scanner sc) {
        try{
            System.out.print("(long) zl1 = ");
            long zl1 = Long.parseLong(sc.nextLine());
            if (!((Long.MIN_VALUE < zl1) && (zl1 < Long.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setZl1(zl1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            zl1(newRoute, sc);
        }
    }

    public void xl2(Route newRoute, Scanner sc) {
        try{
            System.out.print("(int) xl2 = ");
            int xl2 = Integer.parseInt(sc.nextLine());
            if (!((Integer.MIN_VALUE < xl2) && (xl2 < Integer.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setXl2(xl2);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            xl2(newRoute, sc);
        }
    }

    public void yl2(Route newRoute, Scanner sc) {
        try{
            System.out.print("(Float) yl2 = ");
            float yl2 = Float.parseFloat(sc.nextLine());
            if (!((Float.MIN_VALUE < Math.abs(yl2)) && (Math.abs(yl2) < Float.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setYl2(yl2);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            yl2(newRoute, sc);
        }
    }

    public void namel2(Route newRoute, Scanner sc) {
        try{
            System.out.print("(String) namel2 = ");
            String name = sc.nextLine();
            if (name.length() > 968) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setNamel2(name);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            namel2(newRoute, sc);
        }
    }

    public void distance(Route newRoute, Scanner sc) {
        try{
            System.out.print("(float) distance = ");
            float dist = (Float.parseFloat(sc.nextLine()));
            if (!((1 < Math.abs(dist)) && (Math.abs(dist) < Float.MAX_VALUE))) {
                throw new UnacceptableNumberException();
            } else {
                newRoute.setDistance(dist);
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            distance(newRoute, sc);
        }
    }

    public void user(Route newRoute, Scanner sc, User user) {
        System.out.println("User = " + user.getName());
        newRoute.setUser(user.getName());
    }
}
