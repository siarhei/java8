package ch06;

import java.util.Arrays;
import java.util.StringJoiner;

public class Ex09 {
    //You can use the parallelPrefix method to parallelize the computation of Fi-
    //bonacci numbers. We use the fact that the nth Fibonacci number is the top
    //left coefficient of Fn, where F = (1 1 ). Make an array filled with 2 × 2 matri- 10
    //ces. Define a Matrix class with a multiplication method, use parallelSetAll to make an array of matrices, and use parallelPrefix to multiply them.

    private static class Matrix {
        private final long[][] value = {
                {1, 1},
                {1, 0}
        };


        Matrix multiply(Matrix m) {
            long v00 = value[0][0]*m.value[0][0] + value[0][1]*m.value[1][0];
            long v01 = value[0][0]*m.value[0][1] + value[0][1]*m.value[1][1];
            long v10 = value[1][0]*m.value[0][0] + value[1][1]*m.value[1][0];
            long v11 = value[1][0]*m.value[0][1] + value[1][1]*m.value[1][1];

            m.value[0][0] = v00;
            m.value[0][1] = v01;
            m.value[1][0] = v10;
            m.value[1][1] = v11;

            return m;
        }

        long fibonacci() {
            return value[0][0];
        }

        @Override
        public String toString() {
            return new StringBuilder("[[")
                    .append(new StringJoiner(", ").add(""+value[0][0]).add(""+value[0][1]).toString())
                    .append("], [")
            .append(new StringJoiner(", ").add(""+value[1][0]).add(""+value[1][1]).toString())
                    .append("]]").toString();
        }
    }

    public static void main(String[] args) {
        Matrix[] matrices = new Matrix[70];
        Arrays.parallelSetAll(matrices, (i) -> new Matrix());

        Arrays.parallelPrefix(matrices, Matrix::multiply);

        System.out.printf("Fib(70)=%d\n", matrices[69].fibonacci());
    }
}
