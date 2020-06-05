package Route;

import Client.User;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class MyCollection implements Serializable {

    ExecutorService executor = Executors.newCachedThreadPool();
    private List<Route> arr = new ArrayList<Route>();
    ReadWriteLock lock = new ReentrantReadWriteLock();

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "s284775";
    private static final String PASS = "zrj839";
    private User user;
    private int countId;

    public MyCollection() {
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement())

        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM routes");
            while (resultSet.next()){
                Route newRoute = new Route();
                newRoute.setId(resultSet.getInt("id")-1);
                newRoute.setName(resultSet.getString("name"));
                newRoute.setX(resultSet.getFloat("x"));
                newRoute.setY(resultSet.getDouble("y"));
                newRoute.setDate(resultSet.getString("date"));
                newRoute.setXl1(resultSet.getLong("xl1"));
                newRoute.setYl1(resultSet.getDouble("yl1"));
                newRoute.setZl1(resultSet.getLong("zl1"));
                newRoute.setXl2(resultSet.getInt("xl2"));
                newRoute.setYl2(resultSet.getFloat("yl2"));
                newRoute.setNamel2(resultSet.getString("namel2"));
                newRoute.setDistance(resultSet.getFloat("distance"));
                newRoute.setUser(resultSet.getString("client"));
                arr.add(newRoute);
            }
            countId = arr.size();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String help() {
        return "help : вывести справку по доступным командам \n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) \n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении \n" +
                "add {element} : добавить новый элемент в коллекцию \n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному \n" +
                "remove_by_id id : удалить элемент из коллекции по его id \n" +
                "clear : очистить коллекцию \n" +
                "save : сохранить коллекцию в файл \n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме. \n" +
                "remove_first : удалить первый элемент из коллекции \n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный \n" +
                "history : вывести последние 7 команд (без их аргументов) \n" +
                "remove_all_by_distance distance : удалить из коллекции все элементы, значение поля distance которого эквивалентно заданному \n" +
                "count_less_than_distance distance : вывести количество элементов, значение поля distance которых меньше заданного \n" +
                "filter_greater_than_distance distance : вывести элементы, значение поля distance которых больше заданного \n" +
                "exit : завершить программу (без сохранения в файл) \n";

    }

    public String info() throws IndexOutOfBoundsException, NoSuchElementException {
        lock.readLock().lock();
        try {
            return "type: Roue\n"
                    + "Дата инициализации: " + arr.get(arr.stream().findFirst().get().getId()).getDate() + '\n'
                    + "Количество элементов: " + arr.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public String show() throws NoSuchElementException {
        lock.readLock().lock();
        try {
            StringBuilder s = new StringBuilder();
            arr.forEach(route -> s.append(route.toString()).append("\n"));
            return s.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public String add(Route newRoute) {
        lock.writeLock().lock();
        try {
            newRoute.setId(++countId);
            arr.add(newRoute);
            user.addId(newRoute.getId());
            return "Your values saved";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String update(Route newRoute, String arg) throws NumberFormatException {
        lock.writeLock().lock();
        try {
            int id = Integer.parseInt(arg);
            for (int i = 0; i < arr.size(); ++i) {
                if (arr.get(i).getId() == id) {
                    arr.set(i, newRoute);
                }
            }
            return "Input your values";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String removeById(String arg) throws NumberFormatException {
        lock.writeLock().lock();
        try {
            int id = Integer.parseInt(arg);
            //arr = arr.stream().filter(route -> route.getId() != id || !user.getName().equals(route.getUser())).collect(Collectors.toList());
            for (int i = 0; i < arr.size(); ++i) {
                if (arr.get(i).getId() == id) {
                    if (user.getName().equals(arr.get(id).getUser())) {
                        arr.remove(i);
                        user.removeId(id);
                        return "Element is removed";
                    }
                    return "This element isn't belong to user";
                }
            }
            return " No such element";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String clear() {
        lock.writeLock().lock();
        try {
            for (int i = arr.size(); i > 0; --i) {
                if (user.getName().equals(arr.get(i).getUser())) {
                    arr.remove(i);
                    user.removeId(arr.get(i).getId());
                }
            }
            return "List was cleared";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String save() {
        lock.readLock().lock();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO routes (name, x, y, date, xl1, yl1, zl1, xl2, yl2, namel2, distance, client) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))
        {
            statement.executeUpdate("TRUNCATE routes");
            for (Route route : arr) {
                preparedStatement.setString(1, route.getName());
                preparedStatement.setFloat(2, route.getX());
                preparedStatement.setDouble(3, route.getY());
                preparedStatement.setString(4, route.getDate());
                preparedStatement.setLong(5, route.getXl1());
                preparedStatement.setDouble(6, route.getYl1());
                preparedStatement.setLong(7, route.getZl1());
                preparedStatement.setInt(8, route.getXl2());
                preparedStatement.setFloat(9, route.getYl2());
                preparedStatement.setString(10, route.getNamel2());
                preparedStatement.setFloat(11, route.getDistance());
                preparedStatement.setString(12, route.getUser());
                int rows = preparedStatement.executeUpdate();
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
        lock.readLock().unlock();
        }
        return "Saved";
    }

    public String removeFirst() throws NumberFormatException {
        lock.writeLock().lock();
        try {
        if (!arr.get(0).getUser().equals(user.getName())){
            user.removeId(arr.get(0).getId());
            arr.remove(0);
            return "First element is removed";
        }
        return "First element isn't belong to user";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String removeGreater(String arg) throws NumberFormatException{
        lock.writeLock().lock();
        try {
        int id = Integer.parseInt(arg);
        arr = arr.stream().filter(route -> route.getId() < id ).collect(Collectors.toList());
        for (int i = arr.size()-1; i > -1; i--) {
            if (arr.get(i).getId() > id && user.getName().equals(arr.get(i).getUser())) {
                arr.remove(i);
                user.removeId(arr.get(i).getId());
            }
        }
        return "Removed";
    } finally {
        lock.writeLock().unlock();
    }
    }


    public String removeAllByDistance(String arg) throws NumberFormatException {
        lock.writeLock().lock();
        try {
        int distance = Integer.parseInt(arg);
        arr = arr.stream().filter(route -> route.getDistance() != distance).collect(Collectors.toList());
        for (int i = arr.size()-1; i > -1; i--) {
            if (arr.get(i).getDistance() == distance && user.getName().equals(arr.get(i).getUser())) {
                arr.remove(i);
                user.removeId(arr.get(i).getId());
            }
        }
        return "Removed";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String countLessThanDistance(String arg) throws NumberFormatException {
        lock.readLock().lock();
        try {
        int distance = Integer.parseInt(arg);
        return "Number of elements: " + arr.stream().filter(route -> route.getDistance() < distance).count();
    } finally {
        lock.readLock().unlock();
    }
    }

    public String filterGreaterThanDistance(String arg) throws NumberFormatException, NoSuchElementException  {
        lock.readLock().lock();
        try {
        int distance = Integer.parseInt(arg);
        StringBuilder s = new StringBuilder();
        arr.stream().filter(route -> route.getDistance() > distance).forEach(route -> s.append(route.toString()).append("\n"));
        return s.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void getIds(User user){
        lock.readLock().lock();
        try {
        for (Route route: arr) {
            if (route.getUser().equals(user.getName())){
                user.addId(route.getId());
            }
        }
        System.out.println(user.getIds());
    } finally {
        lock.readLock().unlock();
    }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
