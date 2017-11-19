package cs6301.g50;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;


/**

 * Created by

 * Group 50

 * Varun Simha Balaraju

 * Venkata Sarath Chandra Prasad Nelapati

 * Jithin Paul

 * Sunit Mathew

 *

 */
public class FibonacciTest {
    @Test
    public void Test0thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(0);
        Assert.assertEquals(BigInteger.valueOf(0), fibNumber);
    }

    @Test
    public void Test1thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(1);
        Assert.assertEquals(BigInteger.valueOf(1), fibNumber);
    }

    @Test
    public void Test4thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(4);
        Assert.assertEquals(BigInteger.valueOf(3), fibNumber);
    }

    @Test
    public void Test10thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(10);
        Assert.assertEquals(BigInteger.valueOf(55), fibNumber);
    }

    @Test
    public void TestNeg2thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(-2);
        Assert.assertEquals(BigInteger.valueOf(-1), fibNumber);
    }

    @Test
    public void TestNeg10thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(-10);
        Assert.assertEquals(BigInteger.valueOf(-1), fibNumber);
    }

    @Test
    public void Test100thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(100);
        Assert.assertEquals("354224848179261915075", fibNumber.toString());
    }

    @Test
    public void TestMultiple1000thFibonacci() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(1000);
        Assert.assertEquals("43466557686937456435688527675040625802564660517371780402481729089536555417949051890403879840079255169295922593080322634775209689623239873322471161642996440906533187938298969649928516003704476137795166849228875", fibNumber.toString());
        fibNumber = FibonacciTest.TestFibonacci(1000);
        Assert.assertEquals("43466557686937456435688527675040625802564660517371780402481729089536555417949051890403879840079255169295922593080322634775209689623239873322471161642996440906533187938298969649928516003704476137795166849228875", fibNumber.toString());
        fibNumber = FibonacciTest.TestFibonacci(1000);
        Assert.assertEquals("43466557686937456435688527675040625802564660517371780402481729089536555417949051890403879840079255169295922593080322634775209689623239873322471161642996440906533187938298969649928516003704476137795166849228875", fibNumber.toString());
    }

    @Test
    public void TestMultipleFibonacciNumbers() throws Exception {
        BigInteger fibNumber = FibonacciTest.TestFibonacci(50);
        Assert.assertEquals("12586269025", fibNumber.toString());
        fibNumber = FibonacciTest.TestFibonacci(80);
        Assert.assertEquals("23416728348467685", fibNumber.toString());
        fibNumber = FibonacciTest.TestFibonacci(100);
        Assert.assertEquals("354224848179261915075", fibNumber.toString());
    }

    @Test
    public void TestMatrixMultiply2x2() throws Exception {
        BigInteger[][] matrix = new BigInteger[][]{
                {BigInteger.ONE, BigInteger.ONE},
                {BigInteger.ONE, BigInteger.ONE}
        };

        BigInteger[][] identityMatrix = new BigInteger[][]{
                {BigInteger.ONE, BigInteger.ZERO},
                {BigInteger.ZERO, BigInteger.ONE}
        };

        BigInteger[][] finalMatrix = Fibonacci.MatrixMultiply2x2(matrix, identityMatrix);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[0][0]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[0][1]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[1][0]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[1][1]);
    }

    @Test
    public void TestMatrixMultiply2x2Square() throws Exception {
        BigInteger[][] matrix = new BigInteger[][]{
                {BigInteger.ONE, BigInteger.ONE},
                {BigInteger.ONE, BigInteger.ZERO}
        };

        BigInteger[][] finalMatrix = Fibonacci.MatrixMultiply2x2(matrix, matrix);
        Assert.assertEquals(BigInteger.valueOf(2), finalMatrix[0][0]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[0][1]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[1][0]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[1][1]);
    }

    @Test
    public void TestPowerMatrix() throws Exception {
        BigInteger[][] finalMatrix = Fibonacci.PowerMatrix(3);
        Assert.assertEquals(BigInteger.valueOf(3), finalMatrix[0][0]);
        Assert.assertEquals(BigInteger.valueOf(2), finalMatrix[0][1]);
        Assert.assertEquals(BigInteger.valueOf(2), finalMatrix[1][0]);
        Assert.assertEquals(BigInteger.ONE, finalMatrix[1][1]);
    }

    private static BigInteger TestFibonacci(int n) {
        BigInteger linearFibValue = Fibonacci.linearFibonacci(n);
        BigInteger logFibValue = Fibonacci.logFibonacci(n);
        Assert.assertEquals(linearFibValue, logFibValue);
        return linearFibValue;
    }
}
