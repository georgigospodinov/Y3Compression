package main;

/**
 * Abstracts over the two compression techniques.
 * This way {@link ui.Handlers} does not need to know
 * what type of compression it is using.
 *
 * @see ui.Handlers
 * @version 1.1
 * @author 150009974
 */
public abstract class CompressionTechnique {
    public abstract String encode(String text);
    public abstract String decode(String coded);

    /**
     * Returns the coding for the specific symbol so that
     * it can be displayed in the GUI.
     * @param symbol the symbol
     * @return the symbol's encoding as String
     */
    public abstract String getCoding(char symbol);
}
