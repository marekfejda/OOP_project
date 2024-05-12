package culinarycompass.culinarycompass.interfaces;

/**
 * Rozhranie na manipulaciu s poctom lajkov.
 */
public interface Likeable {
    
    /**
     * Zvysi pocet lajkov.
     */
    void incrementLikes();
    
    /**
     * Znizi pocet lajkov.
     */
    void decrementLikes();
}
