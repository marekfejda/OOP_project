package culinarycompass.culinarycompass.exceptions;

/**
 * Vynimka pre chyby autentifikacie pri zadani zlych prihlasovacich udajov.
 */
public class AuthenticationException extends Exception {

    /**
     * Vytvori novu autentifikacnu vynimku so zadanou spravou.
     *
     * @param message Sprava, ktora popisuje chybu.
     */
    public AuthenticationException(String message) {
        super(message);
        System.out.println("custom error: Incorrect nickname or password.");
    }
}
