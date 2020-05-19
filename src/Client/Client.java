package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {

    static String inputName(){
        System.out.println("Input your name");
        String name = new Scanner(System.in).nextLine();
        if (name.isEmpty()){
            name = inputName();
        }
        return name;
    }

    static String inputPassword(){
        System.out.println("Input your password");
        String password = new Scanner(System.in).nextLine();
        if (password.isEmpty()){
            password = inputPassword();
        }
        return password;
    }

    static void autorizated(){
        System.out.println("If you doesn't have account, input \"registration\"\nElse input \"sign in\"");
        String userAction = new Scanner(System.in).nextLine();
        switch (userAction){
            case "registration" :
                String nameR = inputName();
                String passwordR = inputPassword();
                User user = new User(nameR, passwordR);
                break;
            case "sign in" :
                System.out.println("Input your name");
                String nameS = new Scanner(System.in).nextLine();
                System.out.println("Input your password");
                String passwordS = new Scanner(System.in).nextLine();
                break;
            default :
                System.out.println("Wrong input, please try again");
                autorizated();
        }
    }

    public static void main(String[] args){
        CommandExecutor.getCommandExecutor();
        autorizated();
        while (true) {
            System.out.println("Enter you action, use \"help\" to get the list of all commands");
            System.out.print(">>>");
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String action = reader.readLine();
                if (!action.isEmpty()) {
                    CommandExecutor.getCommandExecutor().execute(action);
                    System.out.println();
                }
            }catch (IOException ignored){}
        }
    }
}

