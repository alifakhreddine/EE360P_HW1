//UT-EID=


import java.util.*;
import java.util.concurrent.*;


public class PMerge implements Runnable {
    private int[] A;
    private int[] B;
    private int[] C;
    private int num;


    public PMerge(int[] A, int[] B, int[] C, int num) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.num = num;
    }

    public static void parallelMerge(int[] A, int[] B, int[]C, int numThreads) {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < A.length; i ++) {
            threadPool.execute(new PMerge(A, B, C, A[i]));
        }
        for (int i = 0; i < B.length; i ++) {
            threadPool.execute(new PMerge(A, B, C, B[i]));
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()  {
        int count = 0;
        //int i = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] < num) {
                count++;
            }
        }
        for (int j = 0; j < B.length; j++) {
            if (B[j] < num) {
                count++;
            }
        }
//        while(A[i] < num && i < A.length) {
//            count ++;
//            i ++;
//        }
//        int j = 0;
//        while(B[j] < num && j < B.length) {
//            count ++;
//            j ++;
//        }
        C[count] = num;
    }
}