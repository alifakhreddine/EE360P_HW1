//UT-EID=


import java.util.*;
import java.util.concurrent.*;

public class PSort extends RecursiveAction {
    private int[] A;
    private int begin;
    private int end;

    public PSort(int[] A, int begin, int end) {
        this.A = A;
        this.begin = begin;
        this.end = end;
    }

    public static void parallelSort(int[] A, int begin, int end) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PSort(A, begin, end));
        //ForkJoinPool.commonPool().invoke(new PSort(A, begin, end));
    }

    @Override
    protected void compute() {
        if (end - begin <= 16) {
            for (int i = begin + 1; i < end; i++) {
                int j = i;
                while (j > 0 && A[j] < A[j - 1]) {
                    swap(A, j, j -1);
                    j--;
                }
            }
        } else if (begin != end){
            int mid = partition(A, begin, end);
            PSort left = new PSort(A, begin, mid);
            left.fork();
            PSort right = new PSort(A, mid, end);
            right.compute();
            left.join();
        }

    }

    private int partition(int [] A, int begin, int end) {
        int pivot = A[(begin + end) / 2];
        int pivot_index = begin;
        for (int i = begin; i < end; i++) {
            if (A[i] < pivot) {
                swap(A, i, pivot_index);
                pivot_index++;
            }
        }
        swap(A, pivot_index, end - 1);
        return pivot_index;
    }

    private void swap(int[] A, int first, int second) {
        int temp = A[first];
        A[first] = A[second];
        A[second] = temp;
    }
}