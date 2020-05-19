package Exceptions;

public class NonexistentArgumentException extends NumberFormatException {
    public NonexistentArgumentException() {
        super("\n Incorrect value, please enter you value again");
    }
}

