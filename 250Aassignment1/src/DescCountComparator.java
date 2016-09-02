import java.util.Comparator;

/**
 * Comparator used for sorting the words in descending order by their count
 * Created by billjyc on 2015/10/4.
 */
public class DescCountComparator implements Comparator<WordsData> {
    @Override
    public int compare(WordsData o1, WordsData o2) {
        if(o1.getCount() < o2.getCount()) {
            return 1;
        } else if (o1.getCount() > o2.getCount()) {
            return -1;
        }
        return 0;
    }
}
