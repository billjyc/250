import java.util.ArrayList;

/**
 * Created by billjyc on 2015/12/3.
 */
public class SparseMatrix {
    private int N;
    ArrayList<Double>[] rows;
    public SparseMatrix(int N) {
        this.N = N;
        rows = new ArrayList[N];
        for(int i = 0; i < N; i++) {
            rows[i] = new ArrayList<Double>();
        }
    }

    public void put(int i, int j, double value) {
        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
        if (j < 0 || j >= N) throw new RuntimeException("Illegal index");
        rows[i].add(Double.valueOf(j));
        rows[i].add(value);
    }

    public double get(int i, int j) {
        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
        if (j < 0 || j >= N) throw new RuntimeException("Illegal index");
        int index = rows[i].indexOf(Double.valueOf(j));
        if(index == -1 || index % 2 == 1) {
            return 0.0;
        }
        return rows[i].get(index + 1);
    }
}
