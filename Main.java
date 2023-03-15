public class Main {
    public static void main(String[] args) {

        double[] totalAvg = new double[4];
        double[] totalError = new double[4];
        int top = 90;
        int bottom = 10;
        int right = 80;
        int left = 20;

        int rowsCols = 100;
        int numThreads = 1;

        // Initialize thread count
        double[][] grid = new double[rowsCols][rowsCols];
        Matrix.fillGrid(grid, rowsCols, top, bottom, right, left);


//        System.out.println("Final Grid");
//        for (int i = 0; i < rowsCols; i++) {
//            for (int j = 0; j < rowsCols; j++) {
//                System.out.print(grid[i][j] + " ");
//
//            }
//            System.out.println();
//        }

        System.out.println("4 Barrier Multithreaded Solution");

        // Start timer
        long start = System.currentTimeMillis();

        // Initialize number of rows
        int middle = rowsCols - 2;
        int part = rowsCols / 2;
        int RowS = 0 , ColS = 0 , RowE = 0 , ColE = 0 ;

        // Thread array using extended thread class
        Thread[] averages = new Thread[numThreads];

        System.out.println("start threads");

        for(int i = 0; i < numThreads  ; i++ ) {
			/*
			The following section splits up the work for all the threads
			 */
            if ( i == 0 ) {  				// top left
                RowS = 1;
                ColS = 1;
                RowE = middle / 2;
                ColE = middle / 2;
            }

            if ( i == 1 ) { 				// top right
                RowS = 1;
                ColS = middle / 2 + 1;
                RowE = (middle / 2);
                ColE = (middle / 2 + 1) + ((middle / 2) - 1);
            }

            if ( i == 2 ) { 				// bottom left
                RowS = middle / 2 + 1;
                ColS = 1;
                RowE = (middle / 2 + 1) + ((middle / 2) - 1);
                ColE = middle / 2;
            }
            if ( i == 3 ) {					// bottom right
                RowS = middle / 2 + 1;
                ColS = (middle / 2 + 1);
                RowE = (middle / 2 + 1) + ((middle / 2) - 1);
                ColE = (middle / 2 + 1) + ((middle / 2) - 1);
            }

            // Send these to the override function to ensure the work is split
            averages[i] = new AverageThread(rowsCols, RowS , RowE , ColS , ColE , grid, totalAvg, totalError, i);
            //Threads[i] = averages[i];
            averages[i].start();

        }

        System.out.println("end");

//        for(int i = 0 ; i < numThreads  ; i++ ) {
//            try {
//                //Threads[i].join();
//                averages[i].join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        for(int i = 0; i < numThreads; i++) {
            System.out.println("Thread " + i + " Average: " + totalAvg[i]);
            System.out.println("Thread " + i + " Error: " + totalError[i]);
        }

        // Stop timer
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        // print out results
        System.out.println("Final Grid");
        for (int i = 0; i < rowsCols; i++) {
            for (int j = 0; j < rowsCols; j++) {
                System.out.print(grid[i][j] + " ");

            }
            System.out.println();
        }

        System.out.println("\nTime Elapsed : " +  timeElapsed + "ms" );
    }
}
