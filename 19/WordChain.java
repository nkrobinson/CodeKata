import java.io.*;
import java.util.*;

public class WordChain {

    List<String> wordList = new ArrayList<String>();
    Map<String,List<String>> wordMap = new HashMap<String,List<String>>();

    public static void main(String[] args) {

        WordChain wc = new WordChain(args[0]);

        Scanner scanner = new Scanner(System.in);
        String from;
        String to;

        System.out.println("Enter an empty parameter to end WordChain");
        while(true) {
            System.out.println("Enter a Start Word");
            from = scanner.nextLine().toLowerCase();
            if(from.length() == 0) 
                break;
            if(!wc.wordList.contains(from)) {
                System.out.println(from + " is not in the word");
                continue;
            }


            System.out.println("Enter an End Word");
            to = scanner.nextLine().toLowerCase();
            if(to.length() == 0) 
                break;
            if(!wc.wordList.contains(to)) {
                System.out.println(to + " is not in the word");
                continue;
            }
            
            if(to.length() != to.length()) {
                System.out.println("Enter 2 words with the same length");
                continue;
            }

            wc.chainTo(from, to);
        }

        scanner.close();
    }

    public WordChain() {
        fillDictionary("wordlist.txt");
        System.out.println("Word List Loaded");
        buildWordMap();
    }

    public WordChain(String filename) {

        fillDictionary(filename);
        System.out.println("Word List Loaded");
        buildWordMap();

        // String word, word2;
        // for (int i = 0; i < wordList.size(); i++) {
        //     word = wordList.get(i);
        //     System.out.print(word + ": ");
        //     for (int j = 0; j < wordMap.get(word).size(); j++) {
        //         word2 = wordMap.get(word).get(j);
        //         System.out.print(word2 + " ");
        //     }
        //     System.out.print("\n");
        // }
    }

    private void fillDictionary(String filename) {
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
    }

    private void buildWordMap() {

        String word, word2;
        int wordLength;
        List<String> similarWords;

        for (int i = 0; i < wordList.size(); i++) {
            word = wordList.get(i);
            wordLength = word.length();
            similarWords = new ArrayList<String>();

            for (int j = 0; j < wordList.size(); j++) {
                word2 = wordList.get(j);
                if(word2.length() != wordLength || word == word2)
                    continue;
                if(compareWords(word, word2, wordLength))
                    similarWords.add(word2);
            }

            wordMap.put(word, similarWords);
        }
    }

    private boolean compareWords(String word1, String word2, int wordLength) {
        int differences = 0;
        for(int i = 0; i < wordLength; i++) {
            if(word1.charAt(i) != word2.charAt(i))
                differences++;
            if(differences > 1)
                return false;
        }
        return true;
    }

    public void chainTo(String from, String to) {
        int steps = chainTo(from, to, 0);
        if(steps == -1)
            System.out.println("The steps to reach " + to + " from " + from + " exceeded 5000.");
        else
            System.out.println("It took " + steps + " to reach " + to + " from " + from + ".");
    }

    private int chainTo(String from, String to, int steps) {
        System.out.println(from);
        steps++;
        if(from == to)
            return steps;
        if(steps > 5000)
            return -1;
        
        List<String> fromWordList = wordMap.get(from);
        if(fromWordList.contains(to)) {
            steps = chainTo(to, to, steps);
        } else {
            steps = chainTo(fromWordList.get(new Random().nextInt(fromWordList.size())), to, steps);
        }
        return steps;
    }
}