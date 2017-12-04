package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static java.lang.Math.pow;

/**
 * The Arithmetic coding algorithm.
 * Stores the elements of an {@link InformationSource} in reverse order.
 * Creates {@link Interval}s to which the symbols belong.
 * The code is constructed by continuously subdividing an interval
 * and finally taking the lower bound (the start of the interval).
 *
 * @see CompressionTechnique
 * @see Interval
 * @see InformationSource
 * @see InformationSourceEntry
 * @see Arithmetic#sourceLL
 * @author 150009974
 */
public class Arithmetic extends CompressionTechnique {

    /**
     * Represents an interval of numbers.
     * The bounds of the interval are 64 bit numbers.
     *
     * @see Arithmetic
     * @see Interval#encodeSub(LinkedList, char)
     * @see Interval#decodeSub(LinkedList, long)
     */
    private static class Interval {
        
        long start, size, end;
        char associatedSymbol;

        Interval(long start, long end) {
            this.start = start;
            this.end = end;
            this.size = end - start +1;
        }

        long getStart() {
            return this.start;
        }

        long getEnd() {
            return this.end;
        }
        
        boolean contains(long value) {
            return this.start <= value && value <= this.end;
        }
        
        private Interval setAssociatedSymbol(char c) {
            this.associatedSymbol = c;
            return this;
        }
        char getAssociatedSymbol() {
            return associatedSymbol;
        }

        @Override
        public String toString() {
            return "start = " + start + "\tend=" + end;
        }

        /**
         * Given a list of {@link InformationSourceEntry}s and a symbol,
         * creates an {@link Interval} for that symbol.
         * The created interval is a subinterval of this one.
         * The ratio between the size of the subinterval and the size of this interval
         * is equal to the probability of the symbol in the given list.
         * Returns null if the symbol does not appear in the given list.
         * This method is the core of {@link Arithmetic} coding.
         *
         * @param sourceList A {@link LinkedList} of {@link InformationSourceEntry}s,
         *                   ordered from highest probability to lowest
         * @param symbol the symbol to encode (find an interval for)
         * @return the subinterval for the symbol or null if the symbol does not appear in the list
         *
         * @see InformationSourceEntry
         * @see Arithmetic
         */
        Interval encodeSub(LinkedList<InformationSourceEntry> sourceList, char symbol) {
            double probabilitySum = 0;
            double probabilitySumWithCurrent = 0;
            
            for (InformationSourceEntry entry : sourceList) {
                if (entry.getSymbol() == symbol) {
                    probabilitySumWithCurrent = probabilitySum + entry.getProbability();
                    break;
                }
                probabilitySum += entry.getProbability();
            
            }
            if (probabilitySumWithCurrent == 0)
                throw new NoSuchElementException("No entry for symbol: '" + symbol+"'");
            
            long lower = this.start + (long)(this.size*probabilitySum);
            long upper = this.start + (long)(this.size*probabilitySumWithCurrent)-1;
            if (upper > this.end) upper = end;

            return new Interval(lower, upper);
        }

        /**
         * Given a list of {@link InformationSourceEntry}s and a value,
         * creates an {@link Interval} containing the value.
         * The created interval is a subinterval of this one.
         * The subinterval is associated with a symbol from the list of InformationSourceEntries.
         * This method loops through the given list creating an interval for each probability
         * until a created interval contains the given value. The intervals do not overlap.
         * The returned interval is the only interval that contains the given value.
         * This method is the core of {@link Arithmetic} decoding.
         *
         * @param sourceList A {@link LinkedList} of {@link InformationSourceEntry}s,
         *                   ordered from highest probability to lowest.
         * @param value the value to look for
         * @return the subinterval containing the given value,
         *         or null if the value is outside the range of this interval
         *
         * @see InformationSourceEntry
         * @see Arithmetic
         */
        Interval decodeSub(LinkedList<InformationSourceEntry> sourceList, long value) {

            double probabilitySum = 0;
            double probabilitySumWithCurrent = 0;

            if (!this.contains(value)) return null;

            for (InformationSourceEntry entry : sourceList) {
                probabilitySumWithCurrent += entry.getProbability();
                long lower = this.start + (long)(this.size*probabilitySum);
                long upper = this.start + (long)(this.size*probabilitySumWithCurrent)-1;
                
                Interval i = new Interval(lower, upper);
                if (i.contains(value))
                    return i.setAssociatedSymbol(entry.getSymbol());
                probabilitySum += entry.getProbability();
            }
            return null;
        }

    }
    
    private static final int BITS_TO_LOOK_AT = Long.SIZE -1;
    /**
     * This {@link LinkedList} contains the elements of an {@link InformationSource}.
     * It can be iterated in the correct order
     * unlike the {@link InformationSource#entries} {@link java.util.PriorityQueue}
     */
    private LinkedList<InformationSourceEntry> sourceLL;
    private HashMap<Character, String> symbolRange = new HashMap<>();
    
    private String tag = "";  // For both encoding and decoding.
    private long rescaleCounter = 0;  // For encoding.
    private int nextBitOfTag = 0;  // For decoding.
    private InformationSourceEntry eod;  // For decoding.
    private long value;  // For decoding.
    
    private static Interval getDefaultInterval() {
        return new Interval(0, (long)pow(2, BITS_TO_LOOK_AT)-1);
    }

    public Arithmetic(LinkedList<InformationSourceEntry> source, InformationSourceEntry eod) throws Exception {
        
        this.sourceLL = source;
        this.eod = eod;
        if (eod == null)
            throw new Exception("Can not do Arithmetic Coding without End Of Data Symbol!");

        // Smallest probability/2
        double smallestProbability = sourceLL.peekLast().getProbability();
        int N = sourceLL.size();
        sourceLL.addLast(eod);  // List size is now N +1
        eod.setProbability(smallestProbability/sourceLL.size());
        
        // Sum of probabilities is now 1+smallest/(N+1).
        double delta = eod.getProbability() / (N + 1);
        
        double p = 0.0;
        for (InformationSourceEntry entry : sourceLL) {
            String range = "["+ String.format("%.2f", p);
            // Reduce all N+1 probabilities by delta.
            entry.setProbability(entry.getProbability()- delta);
            p+= entry.getProbability();
            range += ":" + String.format("%.2f", p) + ")";
            symbolRange.put(entry.getSymbol(), range);
        }

        eod.setProbability(eod.getProbability()+1.0-p);

        // Back to probability sum of 1.
    }
    
    @Override
    public String getCoding(char symbol) {
        return symbolRange.get(symbol);
    }
    
    private char getBit(long number, int i) {
        return (char) ('0' + (number >> (BITS_TO_LOOK_AT -i))%2);
    }
    
    private char notBit(char b) {
        return b == '0' ? '1' : '0';
    }
    
    private long getLastBits(long number) {
        long endWithZeros = (-1); 
        for (int i = 0; i < BITS_TO_LOOK_AT; i++)
            endWithZeros <<= 1;// 11...100...0
        long endWithOnes = ~endWithZeros;  // 00...011...1
        return number&endWithOnes;
    }
    
    private Interval encodeRescale(Interval in) {
        long lower = in.getStart();
        long upper = in.getEnd();
        while (true) {
            // Rescale A:
            char MSB = getBit(lower, 1);
            char notMSB = notBit(MSB);
            if (MSB == getBit(upper, 1)) {
                tag += MSB;
                for (; rescaleCounter > 0; rescaleCounter --) tag += notMSB;

                lower = getLastBits(lower<<1);
                upper = getLastBits(upper<<1);
                upper |= 1;

            }
            
            // Rescale B:
            else if (getBit(lower, 2) == '1' && getBit(upper, 2) == '0') {

                lower = getLastBits(lower<<1);
                upper = getLastBits(upper<<1);
                upper |= 1;
                
                // Flip MSB
                lower ^= (long)pow(2,BITS_TO_LOOK_AT-1);
                upper ^= (long)pow(2,BITS_TO_LOOK_AT-1);
                
                rescaleCounter ++;

            }
            
            else return new Interval(lower, upper);
        }
    }
    
    private void finishEncoding(long lower) {
        char MSB = getBit(lower, 1);
        char notMSB = notBit(MSB);
        tag += MSB;
        for (; rescaleCounter > 0; rescaleCounter --) tag += notMSB;

        long B_1 = BITS_TO_LOOK_AT-1;
        long ones = (long)pow(2,B_1)-1;
        String lastBits = Long.toBinaryString(lower&ones);
        for (int i = BITS_TO_LOOK_AT-1 - lastBits.length(); i > 0; i --)
            tag += "0";
        tag += lastBits;
    }
    
    @Override
    public String encode(String text) {
        Interval current = getDefaultInterval();
        tag = "";
        char c;
        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            current = encodeRescale(current.encodeSub(sourceLL, c));
        }
        finishEncoding(current.getStart());
        return tag;
    }
    
    private Interval decodeRescale(Interval in) {
        long lower = in.getStart();
        long upper = in.getEnd();
        while (true) {
            // Rescale A:
            char MSB = getBit(lower, 1);
            if (MSB == getBit(upper, 1)) {

                lower = getLastBits(lower<<1);
                upper = getLastBits(upper<<1);
                upper |= 1;
                
                value = getLastBits(value<<1);
                value |= (tag.charAt(nextBitOfTag++) - '0');

            }
            
            // Rescale B:
            else if (getBit(lower, 2) == '1' && getBit(upper, 2) == '0') {

                lower = getLastBits(lower<<1);
                upper = getLastBits(upper<<1);
                upper |= 1;
                value = getLastBits(value<<1);
                value |= (tag.charAt(nextBitOfTag++) - '0');
                
                // Flip MSB
                lower ^= (long)pow(2,BITS_TO_LOOK_AT-1);
                upper ^= (long)pow(2,BITS_TO_LOOK_AT-1);
                value ^= (long)pow(2,BITS_TO_LOOK_AT-1);
                
                rescaleCounter ++;

            }
            
            else return new Interval(lower, upper);
        }
    }
    
    @Override
    public String decode(String text) {
        
        value = Long.parseLong(tag.substring(0, BITS_TO_LOOK_AT), 2);
        tag = text;
        nextBitOfTag = BITS_TO_LOOK_AT;
        StringBuilder output = new StringBuilder();
        Interval current = getDefaultInterval();
        
        while (true) {

            current = current.decodeSub(sourceLL, value);
            char symbol = current.getAssociatedSymbol();
            output.append(symbol);
            if (symbol == eod.getSymbol()) break;
            current = decodeRescale(current);
            if (nextBitOfTag == tag.length()) break;

        }
        
        return output.toString();
        
    }

}
