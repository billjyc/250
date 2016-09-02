import Jama.Matrix;

/**
 * Created by billjyc on 2015/11/30.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        double[][] I = {{1,0},{0,1}};
        double[][] Ppi = {{0.75, 0.25},{0.25,0.75}};
        double[][] R = {{-2}, {4}};
        double gamma = 2.0 / 3.0;
        Matrix result = main.computeLinearBellmanEquation(Matrix.constructWithCopy(I),
                gamma,
                Matrix.constructWithCopy(Ppi),
                Matrix.constructWithCopy(R));
        double[][] array = result.getArray();
        main.printArray(array);
    }

    public Matrix computeLinearBellmanEquation(Matrix I, double gamma, Matrix Ppi, Matrix R) {
        Matrix result = I.minus(Ppi.times(gamma)).inverse().times(R);
        return result;
    }

    public void printArray(double[][] array) {
       for(int i = 0; i < array.length; i++) {
           for(int j = 0; j < array[i].length; j++) {
               System.out.print(array[i][j] + "\t");
           }
           System.out.println();
       }
    }
}
