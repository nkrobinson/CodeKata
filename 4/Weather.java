import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Weather {

    public static void main(String[] args) {
        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "weather.dat";
        }
        Weather w = new Weather(filename);
    }

    public Weather(String filename) {

        int minSpreadDay = 0;
        float minSpread = Float.MAX_VALUE;

        try {
            File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            int day;
            float tempSpread;
            while ((line = br.readLine()) != null) {
                if (isValueLine(line)) {
                    day = getDay(line);
                    tempSpread = getTempSpread(line);
                    if (tempSpread < minSpread) {
                        minSpreadDay = day;
                        minSpread = tempSpread;
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

        System.out.println("MINIMUM TEMPERATURE SPREAD");
        System.out.print("DAY: \t\t");
        System.out.print(minSpreadDay);
        System.out.print("\nTemp Spread: \t");
        System.out.print(minSpread);

    }

    private boolean isValueLine(String line) {
        if (line == "")
            return false;
        return line.matches(".+\\s+(\\d+)\\s+.+");
    }

    private int getDay(String line) {
        Pattern day = Pattern.compile("\\d+");
        Matcher m = temp.matcher(line);
        if (m.find())
            return Integer.parseInt(m.group(0));
        return -1;
    }

    private float getTempSpread(String line) {
        Pattern temp = Pattern.compile("\\s+\\d+\\s+(\\d+)\\s+(\\d+)");
        Matcher m    = temp.matcher(line);

        if (m.find()) {
            float maxTemp = Float.parseFloat(m.group(1));
            float minTemp = Float.parseFloat(m.group(2));
            return maxTemp - minTemp;
        }
        return Float.MAX_VALUE;

    }

}