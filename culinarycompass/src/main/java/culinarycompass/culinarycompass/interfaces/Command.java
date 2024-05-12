package culinarycompass.culinarycompass.interfaces;

/**
 * Rozhranie prikazu, ktore ma byt implementovane pre vykonanie akcie.
 */
public interface Command {
    
    /**
     * Vykona prikaz.
     */
    void execute();
}
