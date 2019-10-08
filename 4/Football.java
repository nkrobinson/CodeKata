import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Football {

    public static void main(String[] args) {
        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "football.dat";
        }
        Football f = new Football(filename);
    }

    public Football(String filename) {

        String minSpreadTeam = "";
        float minSpread = Integer.MAX_VALUE;

        try {
            File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            String team;
            float goalSpread;
            while ((line = br.readLine()) != null) {
                if (isValueLine(line)) {
                    team = getTeam(line);
                    goalSpread = getGoalSpread(line);
                    if (goalSpread < minSpread) {
                        minSpreadTeam = team;
                        minSpread = goalSpread;
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

        System.out.println("MINIMUM GOAL SPREAD");
        System.out.print("TEAM: \t\t");
        System.out.print(minSpreadTeam);
        System.out.print("\nGoal Spread: \t");
        System.out.print(minSpread);

    }

    private boolean isValueLine(String line) {
        if (line == "")
            return false;
        return line.matches("\\s+(\\d+)\\.\\s.+");
    }

    private String getTeam(String line) {
        Pattern team = Pattern.compile("\\w+");
        Matcher m = team.matcher(line);
        if (m.find())
            return m.group(0);
        return "";
    }

    private float getGoalSpread(String line) {
        Pattern goal = Pattern.compile("(\\d+)\\s+\\-\\s+(\\d+)\\s+\\d+$");
        Matcher m    = goal.matcher(line);

        if (m.find()) {
            float maxTemp = Float.parseFloat(m.group(1));
            float minTemp = Float.parseFloat(m.group(2));
            return Math.abs(maxTemp - minTemp);
        }
        return Float.MAX_VALUE;

    }

}