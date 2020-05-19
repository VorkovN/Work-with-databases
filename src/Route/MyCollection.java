package Route;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MyCollection implements Serializable {

    private List<Route> arr = new ArrayList<Route>();
    private List<Route> arrBase = new ArrayList<Route>();

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "s284775";
    private static final String PASS = "zrj839";

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
                newRoute.setId(resultSet.getInt("id"));
                newRoute.setName(resultSet.getString("name"));
                newRoute.setX(resultSet.getFloat("x"));
                newRoute.setY(resultSet.getDouble("y"));
                newRoute.setDate(resultSet.getString("date"));
                newRoute.setZl1(resultSet.getLong("xl1"));
                newRoute.setYl1(resultSet.getDouble("yl1"));
                newRoute.setZl1(resultSet.getLong("zl1"));
                newRoute.setXl2(resultSet.getInt("xl2"));
                newRoute.setYl2(resultSet.getFloat("yl2"));
                newRoute.setNamel2(resultSet.getString("namel2"));
                newRoute.setDistance(resultSet.getFloat("distance"));
                arr.add(newRoute);
            }

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
        return "type: Roue\n"
                + "Дата инициализации: " + arr.get(arr.stream().findFirst().get().getId()).getDate() + '\n'
                + "Количество элементов: " + arr.size();
    }

    public String show() throws NoSuchElementException {
        StringBuilder s = new StringBuilder();
        arr.forEach(route -> s.append(route.toString()).append("\n"));
        return s.toString();
    }

    public String add(Route newRoute) {
        newRoute.setId(arr.size());
        arr.add(newRoute);
        return "Your values saved";
    }

    public String update(Route newRoute,String arg) throws NumberFormatException{
        int id = Integer.parseInt(arg);
            arr.set(id, newRoute);
        return "Input your values";
    }

    public String removeById(String arg) throws NumberFormatException{
        int id = Integer.parseInt(arg);
        arr = arr.stream().filter(route -> route.getId() != id).collect(Collectors.toList());
        return "Element is removed";
    }

    public String clear() {
        arr.clear();
        return "List was cleared";
    }

    public String save() {

        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO routes (name, x, y, date, xl1, yl1, zl1, xl2, yl2, namel2, distance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))
        {
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
                int rows = preparedStatement.executeUpdate();
                System.out.println(rows);
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        /*try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.json"))) {
            for (Route route : arr) {
                JSONObject out = new JSONObject();
                out.put("id", route.getId());
                out.put("name", route.getName());
                JSONObject coordinates = new JSONObject();
                coordinates.put("x", route.getX());
                coordinates.put("y", route.getY());
                out.put("Route.Coordinates", coordinates);
                JSONObject location1 = new JSONObject();
                location1.put("xl1", route.getXl1());
                location1.put("yl1", route.getYl1());
                location1.put("zl1", route.getZl1());
                out.put("Route.Location1", location1);
                JSONObject location2 = new JSONObject();
                location2.put("xl2", route.getXl2());
                location2.put("yl2", route.getYl2());
                location2.put("namel2", route.getNamel2());
                out.put("Route.Location2", location2);
                out.put("distance", route.getDistance());
                bufferedWriter.write(out.toJSONString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "Saved";
    }

    public String removeFirst() throws NumberFormatException {
        arr.remove(arr.stream().findFirst().get().getId());
        return "First element is removed";
    }

    public String removeGreater(String arg) throws NumberFormatException{
        int id = Integer.parseInt(arg);
        arr = arr.stream().filter(route -> route.getId() < id ).collect(Collectors.toList());
        return "Removed";
    }


    public String removeAllByDistance(String arg) throws NumberFormatException {
        int distance = Integer.parseInt(arg);
        arr = arr.stream().filter(route -> route.getDistance() != distance).collect(Collectors.toList());
        return "Removed";
    }

    public String countLessThanDistance(String arg) throws NumberFormatException {
        int distance = Integer.parseInt(arg);
        return "Number of elements: " + arr.stream().filter(route -> route.getDistance() < distance).count();
    }

    public String filterGreaterThanDistance(String arg) throws NumberFormatException, NoSuchElementException  {
        int distance = Integer.parseInt(arg);
        StringBuilder s = new StringBuilder();
        arr.stream().filter(route -> route.getDistance() > distance).forEach(route -> s.append(route.toString()).append("\n"));
        return s.toString();
    }
}
