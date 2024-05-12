package culinarycompass.culinarycompass.interfaces;

/**
 * Rozhranie príkazu, ktoré má byť implementované pre vykonanie akcie.
 */
public interface Command {
    
    /**
     * Vykoná príkaz.
     */
    void execute();
}
