package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Client {
    CommandExecutor commandExecutor;

    public Client() {
        commandExecutor = new CommandExecutor();
    }

    public String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

        while( md5Hex.length() < 32 ){
            md5Hex.insert(0, "0");
        }

        return md5Hex.toString();
    }

    private String inputName(){
        System.out.println("Input your name");
        String name = new Scanner(System.in).nextLine();
        if (name.isEmpty()){
            name = inputName();
        }
        return name;
    }

    private String inputPassword(){
        System.out.println("Input your password");
        String password = new Scanner(System.in).nextLine();
        if (password.isEmpty()){
            password = inputPassword();
        }
        return md5Custom(password);
    }

    public void autorization(){
        System.out.println("If you doesn't have account, input \"registration\"\nElse input \"sign in\"");
        String userAction = new Scanner(System.in).nextLine();
        switch (userAction){
            case "registration" :
                String nameR = inputName();
                String passwordR = inputPassword();
                User userR = new User(nameR, passwordR);
                userR.setAction("registration");
                commandExecutor.setUser(userR);
                if (commandExecutor.registrationAuthorization(userR)){
                    System.out.println("Successful registration");
                }
                else{
                    System.out.println("User with name: " + nameR + " already exist");
                }
                autorization();
                break;
            case "sign in" :
                String nameS = inputName();
                String passwordS = inputPassword();
                User userS = new User(nameS, passwordS);
                userS.setAction("authorization");
                commandExecutor.setUser(userS);
                if (commandExecutor.registrationAuthorization(userS)){
                    System.out.println("Successful authorization");
                }
                else{
                    System.out.println("Wrong Login or password");
                    autorization();
                }
                break;
            default :
                System.out.println("Wrong input, please try again");
                autorization();
        }
    }


    void connection(){
        autorization();
        while (true) {
            System.out.println("Enter you action, use \"help\" to get the list of all commands");
            System.out.print(">>>");
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String action = reader.readLine();
                if (!action.isEmpty()) {
                    commandExecutor.execute(action);
                    System.out.println();
                }
            }catch (IOException ignored){}
        }
    }
}
