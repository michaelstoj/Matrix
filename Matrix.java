import java.util.concurrent.CyclicBarrier;

public class Matrix {

    double[][] grid;

    public static void fillGrid(double[][] grid, int rowsCols, int top, int bottom, int right, int left) {
        for (int i = 0; i < rowsCols ; i++) {
            for (int j = 0; j < rowsCols ; j++) {
                if (i == 0) {
                    grid[i][j] = top;
                } else if (i == rowsCols - 1) {
                    grid[i][j] = bottom;
                } else if (j == 0) {
                    grid[i][j] = left;
                } else if (j == rowsCols - 1) {
                    grid[i][j] = right;
                } else {
                    grid[i][j] = 0;
                }
            }
        }


    }
}