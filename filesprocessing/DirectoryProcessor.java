package filesprocessing;

import Filters.Filter;
import Filters.FilterFactory;
import Orders.OrderFactory;
import Orders.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryProcessor {

    /*class constants*/
    /*FILTER String*/
    private static final String FILTER = "FILTER";
    /*ORDER String*/
    private static final String ORDER = "ORDER";
    /*Illegal argument String*/
    private static final String ILLEGAL_ARGUMENTS = "ERROR: Illegal Arguments\n";
    /*warning in line message*/
    private static final String WARNING_IN_LINE = "Warning in line ";
    /*bad commands format message*/
    private static final String ERROR_BAD_COMMANDS_FORMAT = "ERROR: Bad COMMANDS format\n";
    /*apply error String*/
    private static final String FILTER_MISSING = "ERROR: Filter sub-sector is missing\n";
    /*order error String*/
    private static final String FILE_PROBLEM = "ERROR: file format is wrong\n";
    /*abs order type*/
    private static final String ABS = "abs";
    /*FILTER boolean flag*/
    private static boolean FILTER_FLAG;
    /*ORDER boolean flag*/
    private static boolean ORDER_FLAG;
    /*middle of section flag*/
    private static boolean MIDDLE_FLAG;

    /*array of received files*/
    private File[] filesArray;
    /*array of filtered files*/
    public static ArrayList<File> filteredFilesArray;
    /*array of line in which there's a warning*/
    private static ArrayList<Integer> warningsToPrint;
    /*arrays of file names to print*/
    private static ArrayList<String> fileNamesToPrint;
    /*command file object*/
    private File commandFileObject;
    /*lines counter*/
    private static int lastLine = 0;



    public DirectoryProcessor(String sourceDirectory, String commandFile) {
        //creates an array of File object from the given sourceDirectory
        filesArray = new File(sourceDirectory).listFiles();
        nullifyVariables();
        // calling the apply factory class and creating an array of the wanted filters
        if (sectionsValidityCheck(commandFile)) {
            sectionFactory();
        }
    }

    private void nullifyVariables(){
        warningsToPrint = new ArrayList<>();
        fileNamesToPrint = new ArrayList<>();
        filteredFilesArray = new ArrayList<>();
        FILTER_FLAG=false;
        ORDER_FLAG=false;
        MIDDLE_FLAG=false;
        lastLine=0;
    }

    private boolean sectionsValidityCheck(String commandFile) {
        commandFileObject = new File(commandFile);
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(commandFileObject));
            int sectionCounter = 0;
            for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
                lastLine++;
                switch (sectionCounter) {
                    case 0:
                        if (line.equals(FILTER)) {
                            sectionCounter++;
                            FILTER_FLAG = true;
                        } else {
                            System.err.println(FILTER_MISSING);
                            return false;
                        }
                        break;
                    case 1:
                        if ((FILTER_FLAG)) {
                            sectionCounter++;
                            FILTER_FLAG = false;
                            MIDDLE_FLAG = true;
                        }
                        break;
                    case 2:
                        if (line.equals(ORDER)) {
                            sectionCounter++;
                            ORDER_FLAG = true;
                            MIDDLE_FLAG = false;
                        } else{
                            System.err.println(ERROR_BAD_COMMANDS_FORMAT);
                            return false;
                        }
                        break;
                    case 3:
                        if (ORDER_FLAG) {
                            if (line.equals(FILTER)){
                                sectionCounter = 1;
                                ORDER_FLAG = false;
                                FILTER_FLAG=true;
                            } else {
                                sectionCounter = 0;
                                ORDER_FLAG = false;
                            }
                        }
                        break;
                }
            }
            if (MIDDLE_FLAG){
                System.err.println(ERROR_BAD_COMMANDS_FORMAT);
                return false;
            }
        } catch (IOException e) {
            System.err.println(FILE_PROBLEM);
        }
        return true;
    }

    private void sectionFactory() {
        try {
            warningsToPrint = new ArrayList<>();
            BufferedReader lineReader = new BufferedReader(new FileReader(commandFileObject));
            int lineNumber = 0, sectionCounter = 0;
            for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
                lineNumber++;
                if ((line.equals(FILTER)) && (sectionCounter == 0)) {
                    FILTER_FLAG = true;
                    sectionCounter++;
                } else if (FILTER_FLAG) {
                    filterManager(line, lineNumber);
                    FILTER_FLAG = false;
                    sectionCounter++;
                } else if ((line.equals(ORDER)) && (sectionCounter == 2)) {
                    ORDER_FLAG = true;
                    sectionCounter++;
                    if (lineNumber == lastLine) {
                        orderManager(ABS, lineNumber);
                    }
                } else if (ORDER_FLAG) {
                    if (line.equals(FILTER)) {
                        orderManager(ABS, lineNumber);
                        ORDER_FLAG = false;
                        FILTER_FLAG = true;
                        sectionCounter = 1;
                    } else {
                        orderManager(line, lineNumber);
                        ORDER_FLAG = false;
                        sectionCounter = 0;
                    }
                }
            } if (FILTER_FLAG){
                System.err.println(ERROR_BAD_COMMANDS_FORMAT);
                return;
            }
        } catch (IOException e) {
            System.err.println(ERROR_BAD_COMMANDS_FORMAT);
        }
        printAll();
    }

    private void printAll() {
        for (int lineNumber : warningsToPrint) {
            System.err.println(WARNING_IN_LINE + lineNumber);
        }
        for (String fileName : fileNamesToPrint) {
            System.out.println(fileName);
        }
    }

    private void filterManager(String line, int lineNumber) {
        boolean noWarnings = FilterFactory.parseFilter(line);
        if (noWarnings) {
            Filter filter = FilterFactory.getFilter();
            String firstArgument = FilterFactory.getFirstArgument();
            String secondArgument = FilterFactory.getSecondArgument();
            ArrayList<File> temp = new ArrayList<>(Arrays.asList(filesArray));
            for (File file : filesArray) {
                if (!(filter.apply(file, firstArgument, secondArgument))) {
                    temp.remove(file);
                }
            }
            filteredFilesArray = temp;
        } else {
            warningsToPrint.add(lineNumber);
            filteredFilesArray = new ArrayList<>(Arrays.asList(filesArray));
        }
    }

    private void orderManager(String line, int lineNumber) {
        boolean noWarnings;
        if (!line.equals(FILTER)){
            noWarnings = OrderFactory.parseOrder(line);
        } else {
            noWarnings=true;
        }
        Order order = OrderFactory.getOrder();
        ArrayList<File> orderedFiles = order.orderFilteredFiles();
        for (File file : orderedFiles) {
            if (!file.isDirectory()){
                fileNamesToPrint.add(file.getName());
            }
        }
        if (!noWarnings) {
            warningsToPrint.add(lineNumber);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(ILLEGAL_ARGUMENTS);
            return;
        }
        String sourceDirectory = args[0], commandFile = args[1];
        new DirectoryProcessor(sourceDirectory, commandFile);
    }
}
