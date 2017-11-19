package cs6301.g50;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

/**

 * Created by

 * Group 50

 * Varun Simha Balaraju

 * Venkata Sarath Chandra Prasad Nelapati

 * Jithin Paul

 * Sunit Mathew

 *

 */
public class Fibonacci {

    private static ArrayList<BigInteger> fibonacciNumbers = new ArrayList<>(Arrays.asList(BigInteger.ZERO, BigInteger.ONE));

    public static BigInteger linearFibonacci(int n) {
        if(n < 0) return BigInteger.valueOf(-1);

        if(fibonacciNumbers.size() > n){
            return fibonacciNumbers.get(n);
        }

        int nextFibNumberToBeCalculated = fibonacciNumbers.size();
        for(int i = nextFibNumberToBeCalculated; i <= n; i++){
            BigInteger prevFibonacci = fibonacciNumbers.get(i-1);
            BigInteger secondPrevFibonacci = fibonacciNumbers.get(i-2);
            BigInteger nthFibonacci = prevFibonacci.add(secondPrevFibonacci);
            fibonacciNumbers.add(i, nthFibonacci);
        }

        return fibonacciNumbers.get(n);
    }

    static BigInteger logFibonacci(int n) {
        if(n < 0) return BigInteger.valueOf(-1);
        if(n == 0) return BigInteger.ZERO;
        BigInteger[][] matrix = PowerMatrix(n-1);
        BigInteger nthFibNumber = matrix[0][0];
        return nthFibNumber;
    }

    public static BigInteger[][] PowerMatrix(int n) {
        if(n == 0) return new BigInteger[][]{
                {BigInteger.ONE, BigInteger.ZERO},
                {BigInteger.ZERO, BigInteger.ONE}
        };

        BigInteger[][] M = new BigInteger[][] {
                { BigInteger.ONE, BigInteger.ONE },
                { BigInteger.ONE, BigInteger.ZERO }
        };

        if(n == 1) return M;
        int index = (int)Math.floor(n/2);
        BigInteger[][] matrix = PowerMatrix(index);
        BigInteger[][] squareMatrix = MatrixMultiply2x2(matrix, matrix);
        if(n % 2 == 0){
            return squareMatrix;
        }

        return MatrixMultiply2x2(squareMatrix, M);
    }

    public static BigInteger[][] MatrixMultiply2x2(BigInteger[][] matrix1, BigInteger[][] matrix2) {
        BigInteger[][] matrix = new BigInteger[2][2];
        for(int i=0;i<2;i++){
            matrix[i] = new BigInteger[2];
        }

        matrix[0][0] = matrix1[0][0].multiply(matrix2[0][0]).add(matrix1[0][1].multiply(matrix2[1][0]));
        matrix[0][1] = matrix1[0][0].multiply(matrix2[0][1]).add(matrix1[0][1].multiply(matrix2[1][1]));
        matrix[1][0] = matrix1[1][0].multiply(matrix2[0][0]).add(matrix1[1][1].multiply(matrix2[1][0]));
        matrix[1][1] = matrix1[1][0].multiply(matrix2[0][1]).add(matrix1[1][1].multiply(matrix2[1][1]));

        return matrix;
    }
}
