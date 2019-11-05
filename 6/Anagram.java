import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Anagram {

    ArrayList<String> wordList = new ArrayList<String>();

    public static void main(String[] args) {
        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "wordlist.txt";
        }
        Anagram a = new Anagram(filename);
    }

    public Anagram(String filename) {
        try {
            File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.toLowerCase());
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: FILE NOT FOUND");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("FILE READ");

        int maxMatches = 0;
        int maxIndex = -1;
        int currentMatches;
        for (int i = 0; i < wordList.size(); i ++) {
            currentMatches = wordMatches(i).size();
            if (currentMatches > maxMatches) {
                maxMatches = currentMatches;
                maxIndex = i;
            }
            // System.out.println(i);
        }

        System.out.println("MAXIMUM WORD MATCHES");
        System.out.print("Words: ");
        System.out.print(wordList.get(maxIndex) + "\t");
        for (int i : wordMatches(maxIndex)) {
            System.out.print(wordList.get(i) + "\t");
        }

    }

    private ArrayList<Integer> wordMatches(int index) {
        String indexWord = wordList.get(index);
        ArrayList<Integer> indexList = new ArrayList<Integer>();

        for (int i = 0; i < wordList.size(); i++) {
            if (i == index)
                continue;
            if (areAnagram(indexWord.toCharArray(), wordList.get(i).toCharArray()))
                indexList.add(i);
        }

        return indexList;
    }

    
    static boolean areAnagram(char[] word1, char[] word2) {

        // Get lenghts of both strings 
        int n1 = word1.length; 
        int n2 = word2.length; 
  
        // If length of both strings is not same, 
        // then they cannot be anagram 
        if (n1 != n2) 
            return false; 
  
        // Sort both strings 
        Arrays.sort(word1); 
        Arrays.sort(word2); 
  
        // Compare sorted strings 
        for (int i = 0; i < n1; i++) 
            if (word1[i] != word2[i]) 
                return false; 
  
        return true; 
    }

}