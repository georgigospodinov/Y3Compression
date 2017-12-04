package ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Contains static methods and fields used to build
 * the HTML document with the JavaScript Chart.
 * Uses final {@link String}s to hold the common HTML elements
 * and various containers for the different data points.
 *
 * @see ChartGenerator#addDataPoint(String, long, long)
 * @see ChartGenerator#DATA_POINTS
 *
 * @version 1.4
 * @author 150009974
 */
class ChartGenerator {

    /**
     * Contains the ids of the lines that will appear in the chart.
     * The values here are used to iterate over other structures in this class.
     */
    private static final String[] COLUMN_IDS = {
        "Huffman Length",
        "Huffman Time",
        "Arithmetic Length",
        "Arithmetic Time",
        "Entropy"
    };
    /*
     * The contents of this class are ordered in the way they will appear in the HTML page.
     */
    private static final String BEFORE_TITLE =
            "<html>\n" +
            "    <head>\n" +
            "        <title>";
    private static final String AFTER_TITLE = "</title>\n" +
            "        <meta charset=\"utf-8\" />\n" +
            "        <link href=\"https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.css\" rel=\"stylesheet\" />\n" +
            "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.6/d3.min.js\"></script>\n" +
            "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.js\"></script>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div id=\"chart\"></div>\n" +
            "        <script>\n" +
            "            var chart = c3.generate({\n" +
            "                data: {\n" +
            "                    x: 'x',\n" +
            "                    columns: [\n";
    private static final PriorityQueue<Long> MESSAGE_LENGTHS = new PriorityQueue<>();
    /**
     * Contains a mapping between a Column ID and the data points.
     * A {@link HashMap}<{@Long}, {@Long}> is used to represent data points
     * because of the quick access times.
     *
     * @see ChartGenerator
     * @see ChartGenerator#createColumns()
     */
    private static final HashMap<String, HashMap<Long, Long>> DATA_POINTS = new HashMap<>();
    private static final String CLOSE_COLUMNS_ARRAY =
            "                    ],\n" +
            "                    axes: {\n";
    private static final LinkedList<String> BELONGING_AXES = new LinkedList<>();
    private static final String END =
            "                    }\n" +
            "                },\n" +
            "                axis: {\n" +
            "                    x: {\n" +
            "                        label: \"Message Length (bits)\",\n" +
            "                    },\n" +
            "                    y: {\n" +
            "                        label: \"Coded Text Length (bits)\",\n" +
            "                    },\n" +
            "                    y2: {\n"+
            "                        show: true,\n" +
            "                        label: \"Coding Time (ms)\",\n" +
            "                    }\n" +
            "                },\n" +
            "                zoom: {\n" +
            "                    enabled: true\n" +
            "                },\n" +
            "            });\n" +
            "        </script>\n" +
            "</body>\n" +
            "</html>";
    
    static {
        for(String id : COLUMN_IDS) {
            DATA_POINTS.put(id, new HashMap<>());
            String yOrY2 = id.endsWith("Time") ? "y2" : "y";
            BELONGING_AXES.addLast("                        \""+id+"\": \""+yOrY2+"\",\n");
        }
    }

    /**
     * Adds a new data point to the arrays to be displayed.
     * @param lineID The ID (name) of the line to be charted.
     *               This must be a value in {@link ChartGenerator#COLUMN_IDS}
     * @param x The x coordinate of the point.
     *          Specifically the length of the text at which lineID passes through y.
     * @param y The y coordinate of the point.
     *          Specifically the value that lineID passes through when the length is x.
     */
    static void addDataPoint(String lineID, long x, long y) {
        DATA_POINTS.get(lineID).put(x, y);
        if (!MESSAGE_LENGTHS.contains(x))
            MESSAGE_LENGTHS.add(x);
    }

    /**
     * Extracts the data from the containers to build a {@link String},
     * containing all the data and ready to be put in a JavaScript function call.
     *
     * @see ChartGenerator#DATA_POINTS
     * @return the String representation of the data.
     */
    private static String createColumns() {
        StringBuilder xs = new StringBuilder("                        ['x',");
        HashMap<String, StringBuilder> columns = new HashMap<>();
        for (String id : COLUMN_IDS)
            columns.put(id, new StringBuilder("                        ['"+id+"',"));
        
        while(!MESSAGE_LENGTHS.isEmpty()) {
            long msgL = MESSAGE_LENGTHS.poll();
            xs.append(msgL).append(",");
            columns.forEach((String coding, StringBuilder column) -> {
                // data corresponding to msgL (can be null, therefor Object Long)
                Long data = DATA_POINTS.get(coding).get(msgL);
                
                if (data != null && coding.endsWith("Time")) 
                    column.append(data*1.0/1000.0);  // get ms here
                else column.append(data);
                column.append(",");
            });
        }
        xs.append("],\n");
        
        columns.forEach((String coding, StringBuilder column) -> {
            column.append("],\n");
            xs.append(column);
            // clear data points
            DATA_POINTS.put(coding, new HashMap<>());
        });
        return xs.toString();
    }
    
    private static String concatBelongingAxes() {
        StringBuilder concatenated = new StringBuilder();
        for (String axis : BELONGING_AXES)
            concatenated.append(axis);
        
        return concatenated.toString();
    }
    
    static void writeToFile(String filepath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath+".html"));
        writer.write(BEFORE_TITLE);

        int index = filepath.lastIndexOf('/');
        if (index == -1) writer.write(filepath);
        else writer.write(filepath.substring(index+1));  // skip '/' when writing title.
        writer.write(AFTER_TITLE);
        writer.write(createColumns());
        writer.write(CLOSE_COLUMNS_ARRAY);
        writer.write(concatBelongingAxes());
        writer.write(END);
        writer.close();
    }
    
}
