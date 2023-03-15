import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.*;
/**
 * This class is the override function.
 *<p>
 *     This class is the one that has an overridden main function. It ensures that the threads
 *     are running into the correct quadrants as well as splitting work evenly.
 *</p>
 */
public class AverageThread extends java.lang.Thread implements Runnable {
    int rowsCols;
    private int rowS;
    private int rowE;
    private int colS;
    private int colE;
    double[] tAverage;
    double[] tError;
    double[][] grid;
    int threadID;

    /**
     * This is the constructor.
     * @param rowS - an int for rows
     * @param rowE - an int for rows
     * @param colS - an int for columns
     * @param colE - an int for columns
     * @param grid - an object for the matrix
     */
    public AverageThread(int rowsCols, int rowS , int rowE , int colS , int colE, double[][] grid, double[] tAverage, double[] tError, int threadID) {
        this.rowsCols = rowsCols;
        this.rowE = rowE;
        this.rowS = rowS;
        this.colS = colS;
        this.colE = colE;
        this.grid = grid;
        this.tAverage = tAverage;
        this.tError = tError;
        this.threadID = threadID;
    }

    /**
     * This is the run() function that ensures the threads are working in the correct rows.
     */
    @Override
    public void run() {

        System.out.println("Thread: " + Thread.currentThread().getName());

        CyclicBarrier barrierBlock = new CyclicBarrier(4);
        do {
            // reset total Error & total Average for next iteration
            tError[threadID] = 0;
            tAverage[threadID] = 0;

//            try {
//                System.out.println("error: before");
//                barrierBlock.await();
//                System.out.println("error: after ");
//            } catch (InterruptedException e) {
//                System.out.println("error: " + e);
//                throw new RuntimeException(e);
//            } catch (BrokenBarrierException e) {
//                System.out.println("error: " + e);
//                throw new RuntimeException(e);
//            }

            System.out.println("this here now");

            for (int i = rowS; i <= rowE; i++) {
                for (int j = colS; j <= colE; j++) {
                    double oldAvg = 0;
                    double updatedAvg = 0;


                    oldAvg = grid[i][j];

                    double average = (grid[i-1][j] + grid[i+1][j] + grid[i][j-1] + grid[i][j+1]);

                    grid[i][j] = ((average / 4 * 10.0) / 10.0);
                    updatedAvg = grid[i][j];
                    tAverage[threadID] += updatedAvg;

                    double error = updatedAvg - oldAvg;

                    tError[threadID] =  tError[threadID] + error;
                }
            }

            try {
                barrierBlock.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }


            // print out results
            System.out.println("Map:");
            for (int i = 0; i < rowsCols; i++) {
                for (int j = 0; j < rowsCols; j++) {
                    System.out.print(grid[i][j] + " ");

                }
                System.out.println();
            }

        } while ((tError[1] + tError[2] + tError[3] + tError[4]) > 5);


    }
}
