package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * The {@link InformationSource} contains the alphabet and the probabilities of each symbol.
 * They are stored as {@link InformationSourceEntry}-s in a {@link PriorityQueue}.
 *
 * @see InformationSourceEntry
 * @see InformationSource#entries
 * @version 2.3
 * @author 150009974
 */
public class InformationSource {

    /**
     * Adding doubles in Java sometimes results in precision lost.
     * This constant is used to account for said loss.
     */
    private static final double PRECISION = 0.000000000001;
    
    private static final int CANNOT = 0;
    private static final int CAN_IS_NOT = 1;
    private static final int CAN_IS = 2;
    private static int canBeEOD(char potential, String text, HashMap<Character, Integer> counts) {
        Integer occurrences = counts.get(potential);
        if (occurrences == null || occurrences == 0) return CAN_IS_NOT;
        if (occurrences > 1) return CANNOT;
        // occurrences == 1
        return text.charAt(text.length()-1) == potential ? CAN_IS : CANNOT;
    }
    
    public static InformationSource estimateFromText(String text) {
        HashMap<Character, Integer> counts = new HashMap<>();
        for (Character c : text.toCharArray()) {
            Integer soFar = counts.get(c);
            counts.put(c, 1 + (soFar == null ? 0 : soFar));
        }
        
        char endOfData = 0;
        int canBe = canBeEOD(endOfData, text, counts);
        // Find unused character.
        while (canBe == CANNOT && endOfData < Character.MAX_VALUE) {
            endOfData ++;
            canBe = canBeEOD(endOfData, text, counts);
        }
        
        // Better not reach this case.
        if (canBe == CANNOT)  // When all Characters occur in the text more than once.
            System.err.println("No character can be used as EOD.");
        
        counts.remove(endOfData);
        if (canBe == CAN_IS_NOT)
            text = text + endOfData;
        
        final int length = text.length();
        InformationSource expected = new InformationSource();
        counts.forEach((symbol, occurrences) -> {
            try {
                expected.addEntry(symbol, 1.0*occurrences/length);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("The estimation was not supposed to throw exceptions.");
            }
        });

        if (canBe != CANNOT) expected.setEOD(endOfData);

        return expected;
    }
    
    /**
     * Contains all the entries in the information source. 
     * the {@link PriorityQueue} is sorted most common to least common,
     * so that text generation is faster. 
     * When compressing, copy into a {@link LinkedList}.
     *
     * @see InformationSourceEntry
     */
    private final PriorityQueue<InformationSourceEntry> entries = new PriorityQueue<>();
    /**
     * Contains references to the same objects as in {@link InformationSource#entries} but
     * adding always pushes to the front of the list and popping pops from the front, thus
     * simulating a stack.
     *
     * @see InformationSource#entries
     * @see InformationSourceEntry
     */
    private final LinkedList<InformationSourceEntry> entriesReverseOrder = new LinkedList<>();
    private double sum = 0;
    private boolean lastEntryWasUpdate = false;
    private InformationSourceEntry eod;
    private boolean lastEntryWasEOD = false;
    private Character lastEOD;
    private Character existingToEOD;

    /**
     * Copies the elements of the {@link InformationSource#entries} in a {@link LinkedList},
     * such that the {@link InformationSourceEntry}s in the returned list are in the same order
     * as are the ones in the {@link InformationSource#entries} {@link PriorityQueue}.
     *
     * @return a {@link LinkedList} of {@link InformationSourceEntry}s
     */
    public LinkedList<InformationSourceEntry> toLinkedList() {
        LinkedList<InformationSourceEntry> orderedList = new LinkedList<>();

        while (this.size() != 0)
            orderedList.addLast(this.pollEntry());

        orderedList.forEach((entry) -> {
            // This iteration preserves the order. Thus, insertion time is constant.
            try {
                this.addEntry(entry.getSymbol(), entry.getProbability());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Copied elements were supposed to be valid!");
            }
        });
        
        return orderedList;
    }

    private double getSum() {
        return this.sum;
    }
    
    public int size() {
        return this.entries.size();
    }
    
    public boolean lastEntryWasUpdate() {
        return this.lastEntryWasUpdate;
    }
    
    public boolean lastEntryWasEOD() {
        return this.lastEntryWasEOD;
    }
    
    public InformationSourceEntry addEntry(char symbol, double probability) throws Exception {
        
        this.lastEntryWasUpdate = this.removeEntryFor(symbol);
        if (eod != null && eod.getSymbol() == symbol) {
            this.lastEntryWasUpdate = true;
            lastEOD = eod.getSymbol();
            eod = null;
        }
        InformationSourceEntry latest = new InformationSourceEntry(symbol, probability);
        
        if (this.sum + latest.getProbability() > 1 + PRECISION)
            throw new Exception("Adding Entry: Attempting to increase probability sum beyond 1!");
        
        this.entriesReverseOrder.push(latest);
        this.entries.add(latest);
        this.sum += latest.getProbability();
        this.lastEntryWasEOD = false;
        
        return latest;
    }

    public InformationSourceEntry setEOD(char eod) {
        // An existing symbol becoming EOD:
        existingToEOD = this.removeEntryFor(eod) ? eod : null;
        
        // An existing EOD being removed:
        lastEOD = (this.eod != null) ? this.eod.getSymbol() : null;
        
        lastEntryWasUpdate = (existingToEOD != null) || (lastEOD != null);
        try {
            this.eod = new InformationSourceEntry(eod, Double.MIN_VALUE);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Static Entry Creation should be valid");
        }
        this.lastEntryWasEOD = true;
        return this.eod;
    }
    
    public InformationSourceEntry getEOD() {
        return this.eod;
    }

    public Character getLastEOD() {
        return lastEOD;
    }

    public Character getExistingToEOD() {
        return existingToEOD;
    }
    
    public InformationSourceEntry getEntryFor(char symbol) {
        if (eod != null && symbol == eod.getSymbol()) return eod;
        
        for (InformationSourceEntry entry : this.entriesReverseOrder)
            if (entry.getSymbol() == symbol) return entry;
        
        return null;
    }

    private InformationSourceEntry pollEntry() {
        InformationSourceEntry entry = this.entries.poll();
        if (entry != null) {
            this.entriesReverseOrder.remove(entry);
            this.sum -= entry.getProbability();
        }
        return entry;
    }
    
    public char removeLatestEntry() {
        if (lastEntryWasEOD) {
            char e = eod.getSymbol();
            eod = null;
            
            return e;
        }
        try {
            InformationSourceEntry entry = this.entriesReverseOrder.pop();
            this.entries.remove(entry);
            this.sum -= entry.getProbability();
            return entry.getSymbol();
        }
        catch (NoSuchElementException e) {
            return 0;
        }
    }
    
    public boolean removeEntryFor(char symbol) {
        InformationSourceEntry entry = getEntryFor(symbol);
        
        if (entry != null) {
            if (entry == eod) {
                eod = null;
                return true;  // return early
            }
            
            this.entriesReverseOrder.remove(entry);
            this.entries.remove(entry);
            this.sum -= entry.getProbability();
            return true;
        }
        return false;
    }
    
    private char getSymbolFromRoll(LinkedList<InformationSourceEntry> list, double roll) throws Exception {
        
        // Accounts for precision loss. See end of function.
        if (1 - this.getSum() > PRECISION)
            throw new Exception("Sum of probabilities " + this.getSum() + " is not 1!\n"+
                    "Add more symbols with a sum of probabilities " + (1-this.getSum()));
        
        if (roll > 1) roll /= 100.0;
        if (roll < 0 || roll >= 1)
            throw new Exception("Invalid Probability! " + roll +" is not within [0; 1)");

        // Add EOD to the list.
        if (this.eod != null) list.addLast(this.eod);

        for (InformationSourceEntry entry : list) {
            if (entry.getProbability() >= roll) {
                // Remove inserted EOD from the list.
                if (this.eod != null) list.pollLast();
                return entry.getSymbol();
            }
            roll -= entry.getProbability();
        }

        if (this.eod != null) list.pollLast();
        throw new NoSuchElementException("");

    }
    
    public String generateText(LinkedList<InformationSourceEntry> list, int len) throws Exception {
        StringBuilder text = new StringBuilder();
        Random r = new Random();
        
        for (int i = 0; i < len; i++)
            text.append(this.getSymbolFromRoll(list, r.nextDouble()));
            
        return text.toString() + (eod != null ? eod.getSymbol() : "");
    }

    public double calculateEntropy(long base) {
        double h = 0.0;
        for (InformationSourceEntry entry : entries) {
            double p = entry.getProbability();
            double log = Math.log(1.0/p);
            h += p*log;
        }
        h /= Math.log(base);
        return h;
    }
    
}

