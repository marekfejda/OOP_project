package culinarycompass.culinarycompass.interfaces;

/**
 * Rozhranie na manipuláciu s počtom lajkov.
 */
public interface Likeable {
    
    /**
     * Zvýši počet lajkov.
     */
    void incrementLikes();
    
    /**
     * Zníži počet lajkov.
     */
    void decrementLikes();
}
