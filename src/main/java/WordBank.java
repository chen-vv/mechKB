import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WordBank {
    private static final int MAX_RANDOM_WORDS = 150;
    public ArrayList<String> wordList;

    public WordBank() {
        try {
            String path = new File("src/main/resources/word_bank.txt").getAbsolutePath();

            Scanner s = new Scanner(new File(path));
            wordList = new ArrayList<>();
            while(s.hasNext()) {
                wordList.add(s.next());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found, cannot generate words");
        }
    }

    public String getRandomWords() {
        StringBuilder randomWords = new StringBuilder();
        Collections.shuffle(wordList);

        for (int i = 0; i < MAX_RANDOM_WORDS; i++) {
            randomWords.append(wordList.get(i)).append(" ");
        }

        return randomWords.toString();
    }
}
