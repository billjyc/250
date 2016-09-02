import java.util.ArrayList;

/**
 * The whole file data, including the list of word data and total counts.
 * Created by billjyc on 2015/10/4.
 */
public class FileData {
    private int totalCounts;
    private ArrayList<WordsData> wordList = new ArrayList<>();

    public int getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(int totalCounts) {
        this.totalCounts = totalCounts;
    }

    public ArrayList<WordsData> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<WordsData> wordList) {
        this.wordList = wordList;
    }
}
