import java.util.HashMap;

/**
 * Created by billjyc on 2015/10/18.
 */
public class BigramFileData {
    //key: token; value: index of the token
    private HashMap<String, Integer> indexMap;
    //key: (index of w2), value: (#count that w1 is followed by w2)
    private HashMap<Integer, Integer> w1w2CountMap;
    //key: index value: token
    private HashMap<Integer, String> reverseIndexMap;

    private int countOfW1;
    public BigramFileData(HashMap<String, Integer> indexMap, HashMap<Integer, String> reverseIndexMap,
                          HashMap<Integer, Integer> w1w2CountMap, int countOfW1) {
        this.indexMap = indexMap;
        this.w1w2CountMap = w1w2CountMap;
        this.reverseIndexMap = reverseIndexMap;
        this.countOfW1 = countOfW1;
    }

    public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(HashMap<String, Integer> indexMap) {
        this.indexMap = indexMap;
    }

    public HashMap<Integer, Integer> getW1W2CountMap() {
        return w1w2CountMap;
    }

    public void setW1W2CountMap(HashMap<Integer, Integer> w1w2CountMap) {
        this.w1w2CountMap = w1w2CountMap;
    }

    public HashMap<Integer, String> getReverseIndexMap() {
        return reverseIndexMap;
    }

    public void setReverseIndexMap(HashMap<Integer, String> reverseIndexMap) {
        this.reverseIndexMap = reverseIndexMap;
    }

    public int getCountOfW1() {
        return countOfW1;
    }

    public void setCountOfW1(int countOfW1) {
        this.countOfW1 = countOfW1;
    }
}
