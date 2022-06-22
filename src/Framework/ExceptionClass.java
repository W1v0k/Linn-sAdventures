package Framework;

public class ExceptionClass extends Exception {

    public ExceptionClass(String message) {
        super(message);
    }
    public ExceptionClass(String message, Throwable cause){super(message,cause);}
}
