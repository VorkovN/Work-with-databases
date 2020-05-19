package Exceptions;

public class UnacceptableNumberException extends NumberFormatException {
    public UnacceptableNumberException() {
        super("\n Unacceptable value, please enter you value again");
    }
}
