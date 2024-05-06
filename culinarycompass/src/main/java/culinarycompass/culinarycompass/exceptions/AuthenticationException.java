package culinarycompass.culinarycompass.exceptions;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
        System.out.println("custom error: Incorrect nickname or password.");
    }
}
