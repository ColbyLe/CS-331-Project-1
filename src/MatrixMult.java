import java.util.Random;
import java.util.Scanner;
// import apache commons language stopwatch package
import org.apache.commons.lang3.time.StopWatch;

public class MatrixMult {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        StopWatch sw = new StopWatch();
        int size = 0;

        // initialize time and accumulator variables
        long acc = 0, time = 0;

        // continue running program as long as n>0
        do {
            System.out.print("Enter matrix size n (enter value less than 1 to exit): ");
            size = kb.nextInt();

            // declare matrices
            int[][] a, b, c;
            // set accumulator to 0
            acc = 0;
            // run each algorithm 5 times, then get average run time
            System.out.println("Standard Multiplication");
            for(int i=1; i<6; i++) {
                // initialize each matrix
                a = new int[size][size];
                b = new int[size][size];
                c = new int[size][size];
                // randomize matrices a and b for each iteration
                randomizer(a);
                randomizer(b);
                // start stopwatch
                sw.start();
                standard(a, b, c);
                // stop stopwatch
                sw.stop();
                // get time in ns
                time = sw.getNanoTime();
                // add time to accumulator
                acc += time;
                // print run time, then reset stopwatch
                System.out.println(i + ") Time Elapsed: " + time);
                sw.reset();
            }
            // get average run time
            acc/=5;
            System.out.println("\nAverage Time Elapsed: " + acc + "\n");

            // reset accumulator
            acc = 0;
            // run strassen algorithm
            System.out.println("Strassen Multiplication");
            for(int i=1; i<6; i++) {
                // initialize and randomize matrices
                a = new int[size][size];
                b = new int[size][size];
                randomizer(a);
                randomizer(b);
                sw.start();
                strassen(a, b);
                sw.stop();
                time = sw.getNanoTime();
                acc += time;
                System.out.println(i + ") Time Elapsed: " + time);
                sw.reset();
            }
            acc/=5;
            // print average time elapsed
            System.out.println("\nAverage Time Elapsed: " + acc + "\n");
            sw.reset();
            System.out.println("\n");
        } while(size > 0);
    }

    // method to fill matrices with random numbers
    public static void randomizer(int[][] x) {
        Random rng = new Random();
        for(int i=0; i<x.length; i++) {
            for(int j=0; j<x[i].length; j++) {
                x[i][j] = rng.nextInt(9)+1;
            }
        }
    }

    // standard matrix multiplication method
    public static void standard(int[][] a, int[][] b, int[][] c) {
        // for each element of c, c[i][j] += a[i][k]*b[k][j]
        for(int i=0; i<a.length; i++) {
            for(int j=0; j<a.length; j++) {
                c[i][j] = 0;
                for(int k=0; k<a.length; k++) {
                    c[i][j] = c[i][j] + (a[i][k] * b[k][j]);
                }
            }
        }
    }

    public static int[][] strassen(int[][] a, int[][] b) {
        // save size n
        int n = a.length;
        // initialize result array
        int [][] result = new int[n][n];

        // partition matrices into submatrices
        if((n%2 != 0 ) && (n !=1))
        {
            int[][] a1, b1, c1;
            int n1 = n+1;
            a1 = new int[n1][n1];
            b1 = new int[n1][n1];
            c1 = new int[n1][n1];

            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                {
                    a1[i][j] =a[i][j];
                    b1[i][j] =b[i][j];
                }
            c1 = strassen(a1, b1);
            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                    result[i][j] =c1[i][j];
            return result;
        }

        if(n == 1)
        {
            result[0][0] = a[0][0] * b[0][0];
        }
        else
        {
            int [][] A11 = new int[n/2][n/2];
            int [][] A12 = new int[n/2][n/2];
            int [][] A21 = new int[n/2][n/2];
            int [][] A22 = new int[n/2][n/2];

            int [][] B11 = new int[n/2][n/2];
            int [][] B12 = new int[n/2][n/2];
            int [][] B21 = new int[n/2][n/2];
            int [][] B22 = new int[n/2][n/2];

            divide(a, A11, 0 , 0);
            divide(a, A12, 0 , n/2);
            divide(a, A21, n/2, 0);
            divide(a, A22, n/2, n/2);

            divide(b, B11, 0 , 0);
            divide(b, B12, 0 , n/2);
            divide(b, B21, n/2, 0);
            divide(b, B22, n/2, n/2);

            // recursively create submatrices
            int [][] P1 = strassen(add(A11, A22), add(B11, B22));
            int [][] P2 = strassen(add(A21, A22), B11);
            int [][] P3 = strassen(A11, sub(B12, B22));
            int [][] P4 = strassen(A22, sub(B21, B11));
            int [][] P5 = strassen(add(A11, A12), B22);
            int [][] P6 = strassen(sub(A21, A11), add(B11, B12));
            int [][] P7 = strassen(sub(A12, A22), add(B21, B22));

            int [][] C11 = add(sub(add(P1, P4), P5), P7);
            int [][] C12 = add(P3, P5);
            int [][] C21 = add(P2, P4);
            int [][] C22 = add(sub(add(P1, P3), P2), P6);

            copy(C11, result, 0 , 0);
            copy(C12, result, 0 , n/2);
            copy(C21, result, n/2, 0);
            copy(C22, result, n/2, n/2);
        }
        return result;
    }

    // basic matrix functions
    public static int [][] add(int [][] A, int [][] B)
    {
        int n = A.length;

        int [][] result = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = A[i][j] + B[i][j];

        return result;
    }

    public static int [][] sub(int [][] A, int [][] B)
    {
        int n = A.length;

        int [][] result = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = A[i][j] - B[i][j];

        return result;
    }

    public static void divide(int[][] p1, int[][] c1, int iB, int jB)
    {
        for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
            for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
            {
                c1[i1][j1] = p1[i2][j2];
            }
    }


    public static void copy(int[][] c1, int[][] p1, int iB, int jB)
    {
        for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
            for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
            {
                p1[i2][j2] = c1[i1][j1];
            }
    }

}
