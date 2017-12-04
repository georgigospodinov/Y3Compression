package ui;

import java.awt.event.*;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

/**
 * The main part of the Graphical User Interface.
 * Java swing is used to create components and set interactions.
 * NetBeans has bee used to generate some initialization code (see report).
 *
 * @version 2.1
 * @author 150009974
 */
@SuppressWarnings("FieldCanBeLocal")
public class GUI extends JFrame {
    
    static final HashMap<String, String> LENGTH_EQUALS_STRINGS = new HashMap<>();
    static {
        LENGTH_EQUALS_STRINGS.put("huffman", "Huffman Code Length = ");
        LENGTH_EQUALS_STRINGS.put("arithmetic", "Arithmetic Code Length = ");
        LENGTH_EQUALS_STRINGS.put("entropy", "Entropy Length = ");
    }
    
    private boolean shiftDown = false;
    
    private GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * This code is auto-generated by NetBeans IDE while designing.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openFileFileChooser = new JFileChooser();
        addButton = new JButton();
        clearInfoSourceButton = new JButton();
        infoSourceLabel = new JLabel();
        inputSymbolTextField = new JTextField();
        inputProbabilityTextField = new JTextField();
        generateRandomTextButton = new JButton();
        encodeArithmeticButton = new JButton();
        decodeHuffmanButton = new JButton();
        symbolLabel = new JLabel();
        probabilityLabel = new JLabel();
        jScrollPane2 = new JScrollPane();
        inputTextTextArea = new JTextArea();
        jScrollPane4 = new JScrollPane();
        arithmeticCodedTextArea = new JTextArea();
        jScrollPane6 = new JScrollPane();
        arithmeticDecodedTextArea = new JTextArea();
        encodeHuffmanButton = new JButton();
        removeLatestButton = new JButton();
        valueOfBaseTextField = new JTextField();
        jScrollPane3 = new JScrollPane();
        informationSourceEntriesListModel = new DefaultListModel();
        informationSourceEntriesList = new JList(informationSourceEntriesListModel);
        removeSelectedButton = new JButton();
        decodeArithmeticButton = new JButton();
        huffmanCompressionTimeLabel = new JLabel();
        huffmanCompressionRatioLabel = new JLabel();
        randomTextLengthTextField = new JTextField();
        randomTextLengthLabel = new JLabel();
        baseEqualsLabel = new JLabel();
        jScrollPane5 = new JScrollPane();
        huffmanCodedTextArea = new JTextArea();
        jScrollPane7 = new JScrollPane();
        huffmanDecodedTextArea = new JTextArea();
        arithmeticCompressionTimeLabel = new JLabel();
        arithmeticCompressionRatioLabel = new JLabel();
        openFileButton = new JButton();
        isEODCheckBox = new JCheckBox();
        entropyLabel = new JLabel();
        huffmanCodingLengthLabel = new JLabel();
        arithmeticCodingLengthLabel = new JLabel();
        entropyLengthLabel = new JLabel();
        generateChartButton = new JButton();
        filepathTextField = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new Point(500, 400));

        addButton.setText("Add");
        addButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                addButtonMousePressed();
            }
        });

        clearInfoSourceButton.setText("Clear Information Source");
        clearInfoSourceButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                clearInfoSourceButtonMousePressed();
            }
        });

        infoSourceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoSourceLabel.setText("<html>Current Information Source:<br>Symbol&emsp;&emsp;Probability&emsp;&emsp;Encoding</html>");
        infoSourceLabel.setAlignmentY(0.0F);

        inputSymbolTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                inputSymbolTextFieldFocusGained();
            }
        });
        inputSymbolTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                inputSymbolTextFieldKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                inputSymbolTextFieldKeyReleased(evt);
            }
        });

        inputProbabilityTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                inputProbabilityTextFieldFocusGained();
            }
        });
        inputProbabilityTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                inputProbabilityTextFieldKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                inputProbabilityTextFieldKeyReleased(evt);
            }
        });

        generateRandomTextButton.setText("Generate Random Text");
        generateRandomTextButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                generateRandomTextButtonMousePressed();
            }
        });

        encodeArithmeticButton.setText("Encode Arithmetic");
        encodeArithmeticButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                encodeArithmeticButtonMousePressed();
            }
        });

        decodeHuffmanButton.setText("Decode Huffman");
        decodeHuffmanButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                decodeHuffmanButtonMousePressed();
            }
        });

        symbolLabel.setText("Symbol:");

        probabilityLabel.setText("Probability:");

        inputTextTextArea.setColumns(20);
        inputTextTextArea.setLineWrap(true);
        inputTextTextArea.setRows(5);
        inputTextTextArea.setText("Text");
        inputTextTextArea.setWrapStyleWord(true);
        inputTextTextArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                inputTextTextAreaFocusGained();
            }
        });
        jScrollPane2.setViewportView(inputTextTextArea);

        arithmeticCodedTextArea.setColumns(20);
        arithmeticCodedTextArea.setLineWrap(true);
        arithmeticCodedTextArea.setRows(5);
        arithmeticCodedTextArea.setText("Arithmetic Coded");
        arithmeticCodedTextArea.setWrapStyleWord(true);
        arithmeticCodedTextArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                arithmeticCodedTextAreaFocusGained();
            }
        });
        jScrollPane4.setViewportView(arithmeticCodedTextArea);

        arithmeticDecodedTextArea.setColumns(20);
        arithmeticDecodedTextArea.setLineWrap(true);
        arithmeticDecodedTextArea.setRows(5);
        arithmeticDecodedTextArea.setText("Decoded");
        arithmeticDecodedTextArea.setWrapStyleWord(true);
        arithmeticDecodedTextArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                arithmeticDecodedTextAreaFocusGained();
            }
        });
        jScrollPane6.setViewportView(arithmeticDecodedTextArea);

        encodeHuffmanButton.setText("Encode Huffman");
        encodeHuffmanButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                encodeHuffmanButtonMousePressed();
            }
        });

        removeLatestButton.setText("Remove Latest");
        removeLatestButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                removeLatestButtonMousePressed();
            }
        });

        valueOfBaseTextField.setText("2");

        jScrollPane3.setViewportView(informationSourceEntriesList);

        removeSelectedButton.setText("Remove Selected");
        removeSelectedButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                removeSelectedButtonMousePressed();
            }
        });

        decodeArithmeticButton.setText("Decode Arithmetic");
        decodeArithmeticButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                decodeArithmeticButtonMousePressed();
            }
        });

        huffmanCompressionTimeLabel.setText("Time:");

        huffmanCompressionRatioLabel.setText("Compression Ratio:");

        randomTextLengthTextField.setText("30");
        randomTextLengthTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                randomTextLengthTextFieldFocusGained();
            }
        });
        randomTextLengthTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                randomTextLengthTextFieldKeyPressed(evt);
            }
        });

        randomTextLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        randomTextLengthLabel.setText("Length =");

        baseEqualsLabel.setText("Base=");

        huffmanCodedTextArea.setColumns(20);
        huffmanCodedTextArea.setLineWrap(true);
        huffmanCodedTextArea.setRows(5);
        huffmanCodedTextArea.setText("Huffman Coded");
        huffmanCodedTextArea.setWrapStyleWord(true);
        huffmanCodedTextArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                huffmanCodedTextAreaFocusGained();
            }
        });
        jScrollPane5.setViewportView(huffmanCodedTextArea);

        huffmanDecodedTextArea.setColumns(20);
        huffmanDecodedTextArea.setLineWrap(true);
        huffmanDecodedTextArea.setRows(5);
        huffmanDecodedTextArea.setText("Huffman Decoded");
        huffmanDecodedTextArea.setWrapStyleWord(true);
        huffmanDecodedTextArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                huffmanDecodedTextAreaFocusGained();
            }
        });
        jScrollPane7.setViewportView(huffmanDecodedTextArea);

        arithmeticCompressionTimeLabel.setText("Time");

        arithmeticCompressionRatioLabel.setText("Compression Ratio");

        openFileButton.setText("Open File");
        openFileButton.setHorizontalTextPosition(SwingConstants.CENTER);
        openFileButton.setMaximumSize(new Dimension(70, 20));
        openFileButton.setMinimumSize(new Dimension(70, 20));
        openFileButton.setPreferredSize(new Dimension(70, 20));
        openFileButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                openFileButtonMousePressed();
            }
        });

        isEODCheckBox.setText("End Of Data Symbol?");
        isEODCheckBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                isEODCheckBoxKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                isEODCheckBoxKeyReleased(evt);
            }
        });

        entropyLabel.setText("Entropy = ");

        huffmanCodingLengthLabel.setText(LENGTH_EQUALS_STRINGS.get("huffman"));

        arithmeticCodingLengthLabel.setText(LENGTH_EQUALS_STRINGS.get("arithmetic"));

        entropyLengthLabel.setText(LENGTH_EQUALS_STRINGS.get("entropy"));

        generateChartButton.setText("<html>Generate Chart<br>& Save in file:");
        generateChartButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                generateChartButtonMousePressed();
            }
        });

        filepathTextField.setText("chart");
        filepathTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                filepathTextFieldFocusGained();
            }
        });
        filepathTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                filepathTextFieldKeyPressed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(symbolLabel)
                                .addGap(8, 8, 8)
                                .addComponent(inputSymbolTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(inputProbabilityTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(infoSourceLabel, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
                            .addComponent(probabilityLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(isEODCheckBox, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(entropyLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(removeSelectedButton, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(removeLatestButton, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(clearInfoSourceButton, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openFileButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(generateRandomTextButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(randomTextLengthLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(randomTextLengthTextField, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(generateChartButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(filepathTextField, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(huffmanCodingLengthLabel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(entropyLengthLabel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(arithmeticCodingLengthLabel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(huffmanCompressionTimeLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(huffmanCompressionRatioLabel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(arithmeticCompressionTimeLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(arithmeticCompressionRatioLabel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(encodeHuffmanButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(baseEqualsLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(valueOfBaseTextField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(220, 220, 220)
                                .addComponent(encodeArithmeticButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(decodeHuffmanButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                .addGap(330, 330, 330)
                                .addComponent(decodeArithmeticButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(symbolLabel))
                    .addComponent(inputSymbolTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(inputProbabilityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(infoSourceLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(probabilityLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(isEODCheckBox)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 517, GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(entropyLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(removeSelectedButton)
                .addGap(5, 5, 5)
                .addComponent(removeLatestButton)
                .addGap(5, 5, 5)
                .addComponent(clearInfoSourceButton))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(generateChartButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(openFileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(generateRandomTextButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(randomTextLengthLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(randomTextLengthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(filepathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(encodeHuffmanButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(baseEqualsLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueOfBaseTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(encodeArithmeticButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(huffmanCodingLengthLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(entropyLengthLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(arithmeticCodingLengthLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(huffmanCompressionTimeLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(huffmanCompressionRatioLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(arithmeticCompressionTimeLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(arithmeticCompressionRatioLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(decodeHuffmanButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(decodeArithmeticButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonMousePressed() {//GEN-FIRST:event_addButtonMousePressed
        Handlers.addToSource(this);
    }//GEN-LAST:event_addButtonMousePressed

    private void inputSymbolTextFieldKeyPressed(KeyEvent evt) {//GEN-FIRST:event_inputSymbolTextFieldKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Handlers.addToSource(this);
                break;
            case KeyEvent.VK_TAB:
                if (shiftDown) isEODCheckBox.requestFocusInWindow();
                else inputProbabilityTextField.requestFocusInWindow();
                break;
            case KeyEvent.VK_SHIFT:
                shiftDown = true;
                break;
        }
    }//GEN-LAST:event_inputSymbolTextFieldKeyPressed

    private void inputProbabilityTextFieldKeyPressed(KeyEvent evt) {//GEN-FIRST:event_inputProbabilityTextFieldKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Handlers.addToSource(this);
                inputSymbolTextField.requestFocusInWindow();
                break;
            case KeyEvent.VK_TAB:
                if (shiftDown) inputSymbolTextField.requestFocusInWindow();
                else isEODCheckBox.requestFocusInWindow();
                break;
            case KeyEvent.VK_SHIFT:
                shiftDown = true;
                break;
        }
    }//GEN-LAST:event_inputProbabilityTextFieldKeyPressed

    private void inputSymbolTextFieldFocusGained() {//GEN-FIRST:event_inputSymbolTextFieldFocusGained
        inputSymbolTextField.selectAll();
    }//GEN-LAST:event_inputSymbolTextFieldFocusGained

    private void inputProbabilityTextFieldFocusGained() {//GEN-FIRST:event_inputProbabilityTextFieldFocusGained
        inputProbabilityTextField.selectAll();
    }//GEN-LAST:event_inputProbabilityTextFieldFocusGained

    private void removeLatestButtonMousePressed() {//GEN-FIRST:event_removeLatestButtonMousePressed
        Handlers.removeLatestFromSource(this);
    }//GEN-LAST:event_removeLatestButtonMousePressed

    private void removeSelectedButtonMousePressed() {//GEN-FIRST:event_removeSelectedButtonMousePressed
        Handlers.removeSelectedFromSource(this);
    }//GEN-LAST:event_removeSelectedButtonMousePressed

    private void clearInfoSourceButtonMousePressed() {//GEN-FIRST:event_clearInfoSourceButtonMousePressed
        String[] options = {"Yes, I'm sure.", "Oh, no! This was a misclick!"};
        if (JOptionPane.showOptionDialog(
                rootPane,
                "Are you sure you wish to clear the Information Source?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]) == 0)
            Handlers.clearInformationSource(this);
    }//GEN-LAST:event_clearInfoSourceButtonMousePressed

    private void generateRandomTextButtonMousePressed() {//GEN-FIRST:event_generateRandomTextButtonMousePressed
        Handlers.generateRandomText(this);
    }//GEN-LAST:event_generateRandomTextButtonMousePressed

    private void encodeHuffmanButtonMousePressed() {//GEN-FIRST:event_encodeHuffmanButtonMousePressed
        Handlers.encode(this, "huffman");
    }//GEN-LAST:event_encodeHuffmanButtonMousePressed

    private void decodeHuffmanButtonMousePressed() {//GEN-FIRST:event_decodeHuffmanButtonMousePressed
        String coded = huffmanCodedTextArea.getText();
        String decoded = Handlers.decode(this, "huffman", coded);
        huffmanDecodedTextArea.setText(decoded);
    }//GEN-LAST:event_decodeHuffmanButtonMousePressed

    private void encodeArithmeticButtonMousePressed() {//GEN-FIRST:event_encodeArithmeticButtonMousePressed
        Handlers.encode(this, "arithmetic");
    }//GEN-LAST:event_encodeArithmeticButtonMousePressed

    private void decodeArithmeticButtonMousePressed() {//GEN-FIRST:event_decodeArithmeticButtonMousePressed
        String coded = arithmeticCodedTextArea.getText();
        String decoded = Handlers.decode(this, "arithmetic", coded);
        arithmeticDecodedTextArea.setText(decoded);
    }//GEN-LAST:event_decodeArithmeticButtonMousePressed

    private void openFileButtonMousePressed() {//GEN-FIRST:event_openFileButtonMousePressed
        Handlers.openFile(this);
    }//GEN-LAST:event_openFileButtonMousePressed

    private void isEODCheckBoxKeyPressed(KeyEvent evt) {//GEN-FIRST:event_isEODCheckBoxKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Handlers.addToSource(this);
                inputSymbolTextField.requestFocusInWindow();
                break;
            case KeyEvent.VK_TAB:
                if (shiftDown) inputProbabilityTextField.requestFocusInWindow();
                else inputSymbolTextField.requestFocusInWindow();
                break;
            case KeyEvent.VK_SHIFT:
                shiftDown = true;
                break;
        }
    }//GEN-LAST:event_isEODCheckBoxKeyPressed

    private void inputSymbolTextFieldKeyReleased(KeyEvent evt) {//GEN-FIRST:event_inputSymbolTextFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) shiftDown = false;
    }//GEN-LAST:event_inputSymbolTextFieldKeyReleased

    private void inputProbabilityTextFieldKeyReleased(KeyEvent evt) {//GEN-FIRST:event_inputProbabilityTextFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) shiftDown = false;
    }//GEN-LAST:event_inputProbabilityTextFieldKeyReleased

    private void isEODCheckBoxKeyReleased(KeyEvent evt) {//GEN-FIRST:event_isEODCheckBoxKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) shiftDown = false;
    }//GEN-LAST:event_isEODCheckBoxKeyReleased

    private void inputTextTextAreaFocusGained() {//GEN-FIRST:event_inputTextTextAreaFocusGained
        inputTextTextArea.selectAll();
    }//GEN-LAST:event_inputTextTextAreaFocusGained

    private void huffmanCodedTextAreaFocusGained() {//GEN-FIRST:event_huffmanCodedTextAreaFocusGained
        huffmanCodedTextArea.selectAll();
    }//GEN-LAST:event_huffmanCodedTextAreaFocusGained

    private void arithmeticCodedTextAreaFocusGained() {//GEN-FIRST:event_arithmeticCodedTextAreaFocusGained
        arithmeticCodedTextArea.selectAll();
    }//GEN-LAST:event_arithmeticCodedTextAreaFocusGained

    private void huffmanDecodedTextAreaFocusGained() {//GEN-FIRST:event_huffmanDecodedTextAreaFocusGained
        huffmanDecodedTextArea.selectAll();
    }//GEN-LAST:event_huffmanDecodedTextAreaFocusGained

    private void arithmeticDecodedTextAreaFocusGained() {//GEN-FIRST:event_arithmeticDecodedTextAreaFocusGained
        arithmeticDecodedTextArea.selectAll();
    }//GEN-LAST:event_arithmeticDecodedTextAreaFocusGained

    private void randomTextLengthTextFieldFocusGained() {//GEN-FIRST:event_randomTextLengthTextFieldFocusGained
        randomTextLengthTextField.selectAll();
    }//GEN-LAST:event_randomTextLengthTextFieldFocusGained

    private void randomTextLengthTextFieldKeyPressed(KeyEvent evt) {//GEN-FIRST:event_randomTextLengthTextFieldKeyPressed
        if (evt.getKeyCode() != KeyEvent.VK_ENTER)
            return;
        generateRandomTextButtonMousePressed();
        encodeHuffmanButtonMousePressed();
        encodeArithmeticButtonMousePressed();
        
    }//GEN-LAST:event_randomTextLengthTextFieldKeyPressed

    private void generateChartButtonMousePressed() {//GEN-FIRST:event_generateChartButtonMousePressed
        Handlers.saveToFile(this);
    }//GEN-LAST:event_generateChartButtonMousePressed

    private void filepathTextFieldFocusGained() {//GEN-FIRST:event_filepathTextFieldFocusGained
        filepathTextField.selectAll();
    }//GEN-LAST:event_filepathTextFieldFocusGained

    private void filepathTextFieldKeyPressed(KeyEvent evt) {//GEN-FIRST:event_filepathTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            Handlers.saveToFile(this);
    }//GEN-LAST:event_filepathTextFieldKeyPressed

    public static void main(String args[]) {
        // Set the Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            if (!UIManager.getLookAndFeel().getName().equals("Nimbus"))
                System.err.println("Failed to set \"Look and Feel\"\n"
                        + "Elements may appear misaligned!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create and display the form
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.setTitle("Compression");
                gui.getInputSymbolTextField().setFocusTraversalKeysEnabled(false);
                gui.getInputProbabilityTextField().setFocusTraversalKeysEnabled(false);
                gui.getIsEODCheckBox().setFocusTraversalKeysEnabled(false);
                gui.setVisible(true);

            }
        });
    }

    JTextField getInputProbabilityTextField() {
        return inputProbabilityTextField;
    }

    JTextField getInputSymbolTextField() {
        return inputSymbolTextField;
    }

    DefaultListModel getInformationSourceEntriesListModel() {
        return informationSourceEntriesListModel;
    }
    
    JList<String> getInformationSourceEntriesList() {
        return informationSourceEntriesList;
    }

    JTextArea getInputTextTextArea() {
        return inputTextTextArea;
    }

    JTextField getRandomTextLengthTextField() {
        return randomTextLengthTextField;
    }

    JTextField getFilepathTextField() {
        return filepathTextField;
    }
    
    JTextArea getCodedTextArea(String techniqueName) {
        switch (techniqueName) {
            case "arithmetic":
                return arithmeticCodedTextArea;
            case "huffman":
                return huffmanCodedTextArea;
            default:
                System.err.println("Bad Text Area Request: " + techniqueName);
                return null;
        }
    }

    JLabel getCompressionDataLabel(String techniqueName, String data) {
        switch (techniqueName) {
            case "arithmetic":
                if (data.equals("time")) return arithmeticCompressionTimeLabel;
                else if (data.equals("ratio")) return arithmeticCompressionRatioLabel;
                break;
            case "huffman":
                if (data.equals("time")) return huffmanCompressionTimeLabel;
                else if (data.equals("ratio")) return huffmanCompressionRatioLabel;
                break;
        }

        System.err.println("Bad Label Request: " + techniqueName + " " + data);
        return null;
    }
    
    JLabel getCodingLengthLabel(String techniqueName) {
        switch (techniqueName) {
            case "arithmetic":
                return arithmeticCodingLengthLabel;
            case "huffman":
                return huffmanCodingLengthLabel;
            case "entropy":
                return entropyLengthLabel;
            default:
                System.err.println("Bad Label Request: " + techniqueName);
                return null;
        }
    }

    JTextField getValueOfBaseTextField() {
        return valueOfBaseTextField;
    }

    JFileChooser getOpenFileFileChooser() {
        return openFileFileChooser;
    }

    JCheckBox getIsEODCheckBox() {
        return isEODCheckBox;
    }

    JLabel getEntropyLabel() {
        return entropyLabel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton addButton;
    private JTextArea arithmeticCodedTextArea;
    private JLabel arithmeticCodingLengthLabel;
    private JLabel arithmeticCompressionRatioLabel;
    private JLabel arithmeticCompressionTimeLabel;
    private JTextArea arithmeticDecodedTextArea;
    private JLabel baseEqualsLabel;
    private JButton clearInfoSourceButton;
    private JButton decodeArithmeticButton;
    private JButton decodeHuffmanButton;
    private JButton encodeArithmeticButton;
    private JButton encodeHuffmanButton;
    private JLabel entropyLengthLabel;
    private JLabel entropyLabel;
    private JTextField filepathTextField;
    private JButton generateChartButton;
    private JButton generateRandomTextButton;
    private JTextArea huffmanCodedTextArea;
    private JLabel huffmanCodingLengthLabel;
    private JLabel huffmanCompressionRatioLabel;
    private JLabel huffmanCompressionTimeLabel;
    private JTextArea huffmanDecodedTextArea;
    private JLabel infoSourceLabel;
    private DefaultListModel informationSourceEntriesListModel;
    private JList<String> informationSourceEntriesList;
    private JTextField inputProbabilityTextField;
    private JTextField inputSymbolTextField;
    private JTextArea inputTextTextArea;
    private JCheckBox isEODCheckBox;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JButton openFileButton;
    private JFileChooser openFileFileChooser;
    private JLabel probabilityLabel;
    private JLabel randomTextLengthLabel;
    private JTextField randomTextLengthTextField;
    private JButton removeLatestButton;
    private JButton removeSelectedButton;
    private JLabel symbolLabel;
    private JTextField valueOfBaseTextField;
    // End of variables declaration//GEN-END:variables
}
