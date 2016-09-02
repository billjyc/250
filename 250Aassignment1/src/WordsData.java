/**
 * Data of each line, contained the word and its count
 * Created by billjyc on 2015/10/4.
 */
public class WordsData implements Comparable<WordsData>{
    private String word;
    private int count;

    public WordsData(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(WordsData wd) {
        if(this.count < wd.count) {
            return -1;
        } else if (this.count > wd.count){
            return 1;
        }
        return 0;
    }
}
