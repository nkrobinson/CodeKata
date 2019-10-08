import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DifferenceList {

    private String valueLineRegex;
    private String identRegex;
    private String spreadValuesRegex;

    public DifferenceList() {
        this.valueLineRegex = "";
        this.identRegex = "";
        this.spreadValuesRegex = "";
    }

    public DifferenceList(String valueLineRegex, String identRegex, String spreadValuesRegex) {
        this.valueLineRegex = valueLineRegex;
        this.identRegex = identRegex;
        this.spreadValuesRegex = spreadValuesRegex;
    }

    public void printValueDifference(String filename) {

        String minValueDifferenceIdent = "";
        float minValueDifference = Integer.MAX_VALUE;

        try {
            File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            String ident;
            float valueDifference;
            while ((line = br.readLine()) != null) {
                if (isValueLine(line)) {
                    ident = getIdent(line);
                    valueDifference = getValuesDifference(line);
                    if (valueDifference < minValueDifference) {
                        minValueDifferenceIdent = ident;
                        minValueDifference = valueDifference;
                    }
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: FILE NOT FOUND");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("MINIMUM VALUE DIFFERENCE");
        System.out.print("IDENT: \t\t");
        System.out.print(minValueDifferenceIdent);
        System.out.print("\nVALUE DIFFERENCE: \t");
        System.out.print(minValueDifference);
        System.out.print("\n");
    }

    public void setValueLineRegex(String regex) {
        valueLineRegex = regex;
    }

    private boolean isValueLine(String line) {
        if (line == "")
            return false;
        return line.matches(valueLineRegex);
    }

    public void setIdentRegex(String regex) {
        identRegex = regex;
    }

    private String getIdent(String line) {
        Pattern p = Pattern.compile(identRegex);
        Matcher m = p.matcher(line);
        if (m.find())
            return m.group(0);
        return "";
    }

    public void setSpreadValuesRegex(String regex) {
        spreadValuesRegex = regex;
    }

    private float getValuesDifference(String line) {
        Pattern p = Pattern.compile(spreadValuesRegex);
        Matcher m = p.matcher(line);

        if (m.find()) {
            float maxTemp = Float.parseFloat(m.group(1));
            float minTemp = Float.parseFloat(m.group(2));
            return Math.abs(maxTemp - minTemp);
        }
        return Float.MAX_VALUE;

    }

}