import java.io.*;
import java.util.*;
import java.util.regex.*;
import DifferenceList;

public class Football2 {

    public static void main(String[] args) {

        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "football.dat";
        }

        DifferenceList dl = new DifferenceList();
        dl.setValueLineRegex("\\s+(\\d+)\\.\\s.+");
        dl.setIdentRegex("\\w+");
        dl.setSpreadValuesRegex("(\\d+)\\s+\\-\\s+(\\d+)\\s+\\d+$");

        dl.printValueDifference(filename);
    }
}