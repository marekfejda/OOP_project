package culinarycompass.culinarycompass.exceptions;

/**
 * Výnimka pre chyby autentifikácie pri zadaní zlých prihlasovacích údajov.
 */
public class AuthenticationException extends Exception {

    /**
     * Vytvorí novú autentifikačnú výnimku so zadanou správou.
     *
     * @param message Správa, ktorá popisuje chybu.
     */
    public AuthenticationException(String message) {
        super(message);
        System.out.println("custom error: Incorrect nickname or password.");
    }
}
