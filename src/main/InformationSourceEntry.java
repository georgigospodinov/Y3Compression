package main;

/**
 * Represents a single entry in the Information source.
 * Maps Symbol to Probability and can be compared, sorted, printed.
 *
 * @see InformationSourceEntry#compareTo(Object)
 * @see InformationSourceEntry#toString()
 * @see InformationSourceEntry#toHTML()
 * @version 1.7
 * @author 150009974
 */
public class InformationSourceEntry implements Comparable {
        
    private static final String HTML_START_TABS = "<html>&emsp;&emsp;&emsp;&emsp;'";
    
    private final char symbol;
    private double probability;

    InformationSourceEntry(char symbol, double probability) throws Exception {
        if (probability > 1) probability /= 100.0;
        if (probability <= 0 || probability > 1)
            throw new Exception("Invalid Probability! " + probability +" is not within (0;1]!");

        this.symbol = symbol;
        this.probability = probability;
    }

    public char getSymbol() {
        return this.symbol;
    }

    void setProbability(double probability) {
        this.probability = probability;
    }
    
    double getProbability() {
        return this.probability;
    }

    public String toHTML() {
        return HTML_START_TABS + this.symbol
                + "'&emsp;&emsp;&emsp;&emsp;&emsp;"
                + String.format("%.3f", this.probability);
    }

    @Override
    public String toString() {
        return this.symbol + "\t" + this.probability;
    }

    @Override
    public boolean equals(Object obj) {

        return obj != null &&
                getClass() == obj.getClass() &&
                this.symbol == ((InformationSourceEntry) obj).symbol;

    }

    /**
     * Compares the probabilities of the two {@link InformationSourceEntry}s.
     * @param other the {@link InformationSourceEntry} to compare this to
     * @return return -1, 0, or 1 if the probability of this is less than,
     *          equal to, or greater than the other one's
     */
    @Override
    public int compareTo(Object other) {
        return Double.compare(((InformationSourceEntry) other).probability, this.probability);
    }

    public static int positionOfSymbolInHTML() {
        return HTML_START_TABS.length();
    }
        
}