package ui;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.*;
import main.*;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import static ui.GUI.LENGTH_EQUALS_STRINGS;

/**
 * Handler functions triggered when the user interacts with the GUI.
 * Separate from the GUI class for better readability.
 *
 * @version 3.6
 * @author 150009974
 */
class Handlers {
    
    private static final String TABS = "&emsp;&emsp;&emsp;&emsp;";
    private static final int PARSE_FAILED = -1;
    
    private static InformationSource source = new InformationSource();
    private static LinkedList<InformationSourceEntry> sourceLL;
    private static String inputText = "";
    private static double entropy;
    
    /**
     * Mapping between symbol and its description in the GUI list.
     * Used to find the indexes in the GUI List and then update that index.
     */
    private static HashMap<Character, String> guiListEntries = new HashMap<>();
    
    private static CompressionTechnique huffmanUsed;
    private static CompressionTechnique arithmeticUsed;
    
    private static double parseFraction(String fraction) {
        int fractionIndex = fraction.indexOf('/');
        if (fractionIndex < 0) throw new NumberFormatException();
        
        String numeratorT = fraction.substring(0, fractionIndex);
        String denominatorT = fraction.substring(fractionIndex+1);
        double numerator = Double.parseDouble(numeratorT);
        double denominator = Double.parseDouble(denominatorT);
        
        return numerator/denominator;
    }
    
    private static void updateFields(GUI gui) {
        // source linked list
        sourceLL = source.toLinkedList();

        // Entropy
        if (source.size() == 0) {
            gui.getEntropyLabel().setText("Entropy = ");
            return;
        }
        
        long base = parseBase(gui);
        entropy = source.calculateEntropy(base);
        gui.getEntropyLabel().setText("Entropy = " + String.format("%.3f",entropy));
    }
    
    private static String capitalizeFirst(String line) {
        char firstUpper = Character.toUpperCase(line.charAt(0));
        return firstUpper + line.substring(1);
    }
    
    private static void setLengthsDisplayed(GUI gui, String techniqueName, double time) {
        // All lengths are in bits!
        
        long textLength = gui.getInputTextTextArea().getText().length();
        double entropyLength = textLength*entropy;
        
        String textToDisplay = LENGTH_EQUALS_STRINGS.get("entropy") + String.format("%.2f", entropyLength);
        gui.getCodingLengthLabel("entropy").setText(textToDisplay);
        
        long techniqueLength = gui.getCodedTextArea(techniqueName).getText().length();
        textToDisplay = LENGTH_EQUALS_STRINGS.get(techniqueName) + techniqueLength;
        gui.getCodingLengthLabel(techniqueName).setText(textToDisplay);
        
        // Do not save data on N-ary Huffman!
        if (techniqueName.equals("huffman") && parseBase(gui) != 2) return;
        
        // Only chart binary encodings.
        long textLengthInBits = textLength*Character.BYTES*8;
        ChartGenerator.addDataPoint("Entropy", textLengthInBits, (long)entropyLength);
        
        String tL = capitalizeFirst(techniqueName) + " Length";
        ChartGenerator.addDataPoint(tL, textLengthInBits, techniqueLength);
        String tT = capitalizeFirst(techniqueName) + " Time";
        ChartGenerator.addDataPoint(tT, textLengthInBits, (long) (time*1000));
    }

    private static void removeOld(DefaultListModel lmodel, char symbol) {

        // If there was no update, nothing to do.
        if (!source.lastEntryWasUpdate()) return;

        // Updating EOD -> potentially 2 removals.
        if (source.lastEntryWasEOD()) {
            lmodel.removeElement(guiListEntries.remove(source.getExistingToEOD()));
            lmodel.removeElement(guiListEntries.remove(source.getLastEOD()));
        }
        else lmodel.removeElement(guiListEntries.remove(symbol));
    }

    static void addToSource(GUI gui) {
        
        String s = gui.getInputSymbolTextField().getText();
        String p = gui.getInputProbabilityTextField().getText();
        
        String errorMessage = "";
        boolean error = false;
        if (s.length() != 1) {
            errorMessage += "Please enter exactly one symbol!\n";
            error = true;
        }
        
        double probability = -1.0;
        try {
            probability = Double.parseDouble(p);
        }
        catch (NumberFormatException retry) {
            try {
                probability = parseFraction(p);
            }
            catch (NumberFormatException nfe) {
                errorMessage += "Please enter a number as the probability!\n";
                error = true;
            }
        }
        
        if (error) {
            JOptionPane.showMessageDialog(gui,
                                          errorMessage,
                                          "Input Error!",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        char symbol = s.charAt(0);
        InformationSourceEntry entry;
        boolean addingEOD = gui.getIsEODCheckBox().isSelected();
        DefaultListModel lmodel = gui.getInformationSourceEntriesListModel();
        try {
            if (addingEOD) entry = source.setEOD(symbol);
            else entry = source.addEntry(symbol, probability);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                                          ex.getMessage(),
                                          "Input Error!",
                                          JOptionPane.ERROR_MESSAGE);
            removeOld(lmodel, symbol);
            return;
        }

        gui.getInputProbabilityTextField().setText("");
        gui.getInputSymbolTextField().setText("");
        
        removeOld(lmodel, symbol);
        guiListEntries.put(symbol, entry.toHTML());
        // puts string at the end
        lmodel.addElement(guiListEntries.get(symbol));
        
        gui.getIsEODCheckBox().setSelected(false);
        updateFields(gui);
        
    }
    
    static void removeSelectedFromSource(GUI gui) {
        int index = gui.getInformationSourceEntriesList().getSelectedIndex();
        String text = (String) gui.getInformationSourceEntriesListModel().get(index);
        char charToRemove = text.charAt(InformationSourceEntry.positionOfSymbolInHTML());
        source.removeEntryFor(charToRemove);
        gui.getInformationSourceEntriesListModel().remove(index);
        guiListEntries.remove(charToRemove);
        
        updateFields(gui);
    }
    
    static void removeLatestFromSource(GUI gui) {
        char charToRemove = source.removeLatestEntry();
        String textToRemove = guiListEntries.remove(charToRemove);
        gui.getInformationSourceEntriesListModel().removeElement(textToRemove);
        
        updateFields(gui);
    }
    
    static void clearInformationSource(GUI gui) {
        source = new InformationSource();
        guiListEntries = new HashMap<>();
        gui.getInformationSourceEntriesListModel().clear();
        updateFields(gui);
    }
    
    static void generateRandomText(GUI gui) {
        try {
            int length = Integer.parseInt(gui.getRandomTextLengthTextField().getText());
            if (length < 0) throw new NumberFormatException("Negative Value!");
            
            gui.getInputTextTextArea().setText(source.generateText(sourceLL, length));
        }
        catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(gui,
                                          "Please enter a positive whole number,",
                                          "Message Length Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                                          ex.getMessage(),
                                          "Text Generation Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    static void openFile(GUI gui) {
        JFileChooser offc = gui.getOpenFileFileChooser();
        String userHome = System.getProperty("user.home");
        String fsep = System.getProperty("file.separator");
        offc.setCurrentDirectory(new File(userHome+fsep+"Documents"));
        
        switch (offc.showOpenDialog(gui)) {
            case JFileChooser.ERROR_OPTION:  // Error message on error.
                JOptionPane.showMessageDialog(gui,
                                          "Access denied!",
                                          "Open File Error",
                                          JOptionPane.ERROR_MESSAGE);
            case JFileChooser.CANCEL_OPTION:  // Return on error and cancel.
                return;
        }
        
        String filepath = offc.getSelectedFile().toString();
        
        if (filepath.endsWith(".pdf")) {
            // Apache PDF parser
            try {
                PDFParser parser = new PDFParser(new RandomAccessFile(new File(filepath), "r"));
                parser.parse();
                PDDocument pddoc = new PDDocument(parser.getDocument());
                PDFTextStripper pdfstrip = new PDFTextStripper();
                
                pdfstrip.setStartPage(1);
                pdfstrip.setEndPage(pddoc.getNumberOfPages());
                String pdfText = pdfstrip.getText(pddoc);
                gui.getInputTextTextArea().setText(pdfText);
                
                pddoc.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(gui,
                                          ioe.getMessage(),//"Access denied!",
                                          "Open File Error",
                                          JOptionPane.ERROR_MESSAGE);
            }   
        }
        else  {
            // Default File Reader
            if (!filepath.endsWith(".txt"))
                JOptionPane.showMessageDialog(gui,
                                            "File extension not supported!\n"
                                                    + "Errors may occur",
                                            "Open File Information",
                                            JOptionPane.INFORMATION_MESSAGE);
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filepath));
                JTextArea textA = gui.getInputTextTextArea();
                textA.setText("");

                for (int c = reader.read(); c != -1; c = reader.read())
                    textA.append(""+(char)c);

            }
            catch (IOException ioe) {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(gui,
                                              ioe.getMessage(),//"Access denied!",
                                              "File Access Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    private static void estimateInformationSource(GUI gui) {
        String text = gui.getInputTextTextArea().getText();
        source = InformationSource.estimateFromText(text);
        DefaultListModel lmodel = gui.getInformationSourceEntriesListModel();

        /** {@link Handlers#sourceLL} has not been updated yet. */
        for (InformationSourceEntry entry : source.toLinkedList()) {
            guiListEntries.put(entry.getSymbol(), entry.toHTML());
            lmodel.addElement(guiListEntries.get(entry.getSymbol()));
        }
        
        updateFields(gui);
    }
    
    private static int parseBase(GUI gui) {
        try {
            int base = Integer.parseInt(gui.getValueOfBaseTextField().getText());
            if (base < 2) throw new NumberFormatException("Value not large enough!");
            return base;
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                                          "Please enter a positive integer.",
                                          "Invalid value of n.",
                                          JOptionPane.ERROR_MESSAGE);
            return PARSE_FAILED;
        }
    }
    
    private static int parseTextAndBase(GUI gui) {
        
        if (source.size() == 0) estimateInformationSource(gui);
        
        inputText = gui.getInputTextTextArea().getText();
        if (inputText.length() == 0) {
            JOptionPane.showMessageDialog(gui,
                                          "No text to encode.\nPlease enter text or generate one randomly.",
                                          "Encoding Error",
                                          JOptionPane.ERROR_MESSAGE);
            return PARSE_FAILED;
        }
        
        return parseBase(gui);
        
    }
    
    private static void setUsed(String techniqueName, CompressionTechnique encoding) {
        switch (techniqueName) {
            case "huffman":
                huffmanUsed = encoding;
                break;
            case "arithmetic":
                arithmeticUsed = encoding;
                break;
        }
    }
    
    static void encode(GUI gui, String techniqueName) {

        int base = parseTextAndBase(gui);
        if (base == PARSE_FAILED) {
            huffmanUsed = null;
            return;
        }
        
        CompressionTechnique encoding;
        String encoded;
        long start, end;
        try {
            switch (techniqueName) {
                case "arithmetic":
                    encoding = new Arithmetic(sourceLL, source.getEOD());
                    break;
                case "huffman":
                    encoding = new Huffman(sourceLL, source.getEOD());
                    ((Huffman)encoding).buildTree(base);
                    break;
                default:
                    System.err.println("Static technique names should be found: " + techniqueName);
                    throw new Exception("Trying to use unknown coding!");
            }
            
            start = System.nanoTime();
            encoded = encoding.encode(inputText);
            end = System.nanoTime();
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            String text = ex.getMessage().trim();
            if (text.length() == 0)
                text = "Could not encode text!\n" +
                        "Please check that all symbols are in the info source!";
            JOptionPane.showMessageDialog(gui,
                                          text,
                                          "Encoding Error",
                                          JOptionPane.ERROR_MESSAGE);
            setUsed(techniqueName, null);
            return;
        }
        
        gui.getCodedTextArea(techniqueName).setText(encoded);
        double elapsed = (end-start)/1000000.0;
        String timeInfo = String.format("Time: %.2f ms", elapsed);
        gui.getCompressionDataLabel(techniqueName, "time").setText(timeInfo);
        long compressedLength = encoded.length();
        long regularLength = inputText.length()*Character.BYTES*8;
        String compressRate = String.format("Compression Ratio: %.3f", (regularLength*1.0/compressedLength));
        gui.getCompressionDataLabel(techniqueName, "ratio").setText(compressRate);
        
        DefaultListModel lmodel = gui.getInformationSourceEntriesListModel();
        guiListEntries.forEach((symbol, entryText) -> {
            int index = lmodel.indexOf(entryText);
            String htmlCode = source.getEntryFor(symbol).toHTML();
            String coding = encoding.getCoding(symbol);
            String updatedText = htmlCode + TABS + coding;
            guiListEntries.put(symbol, updatedText);
            lmodel.set(index, guiListEntries.get(symbol));
        });
        
        setLengthsDisplayed(gui, techniqueName, elapsed);
        setUsed(techniqueName, encoding);
    }
    
    private static CompressionTechnique getUsed(String techniqueName) {
        switch (techniqueName) {
            case "huffman":
                return huffmanUsed;
            case "arithmetic":
                return arithmeticUsed;
            default:
                return null;
        }
    }
    
    static String decode(GUI gui, String techniqueName, String coded) {
        try {
            return getUsed(techniqueName).decode(coded);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                                          "The encoding was not recognized.\n" +
                                                  "Try encoding again.",
                                          "Decoding Error",
                                          JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
    
    static void saveToFile(GUI gui) {
        try {
            ChartGenerator.writeToFile(gui.getFilepathTextField().getText());
        }
        catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                                          ex.getMessage(),
                                          "Chart Creation Error!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
