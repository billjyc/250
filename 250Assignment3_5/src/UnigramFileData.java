import java.util.HashMap;

/**
 * Created by billjyc on 2015/10/18.
 */
public class UnigramFileData {
    private int totalCount;
    private HashMap<String, Integer> countTables = new HashMap<>();

    public UnigramFileData(int totalCount, HashMap<String, Integer> countTables) {
        this.totalCount = totalCount;
        this.countTables = countTables;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public HashMap<String, Integer> getCountTables() {
        return countTables;
    }

    public void setCountTables(HashMap<String, Integer> countTables) {
        this.countTables = countTables;
    }
}
