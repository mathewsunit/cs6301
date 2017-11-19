/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 *
 */

package cs6301.g50;

import org.junit.Assert;
import org.junit.Test;


public class Shunting_YardTest {
    @Test
    public void TestInfixToPostFixEasySample() throws Exception {
        Shunting_Yard sy = new Shunting_Yard();
        sy.infixToPostfix("a + b");
        StringBuilder builder = new StringBuilder();
        while (!sy.output.isEmpty()){
            builder.append(sy.output.remove());
        }

        Assert.assertEquals("ab+", builder.toString());
    }

    @Test
    public void TestInfixToPostFix() throws Exception {
        Shunting_Yard sy = new Shunting_Yard();
        sy.infixToPostfix("( a * b )");
        StringBuilder builder = new StringBuilder();
        while (!sy.output.isEmpty()){
            builder.append(sy.output.remove());
        }

        Assert.assertEquals("ab*", builder.toString());
    }

    @Test
    public void TestInfixToPostFixLargeSample() throws Exception {
        Shunting_Yard sy = new Shunting_Yard();
        sy.infixToPostfix("a * b + c - d * e * f - g ^ f - f");
        StringBuilder builder = new StringBuilder();
        while (!sy.output.isEmpty()){
            builder.append(sy.output.remove());
        }

        Assert.assertEquals("ab*c+de*f*-gf^-f-", builder.toString());
    }

    @Test
    public void TestInfixToPostFixSingleCharacter() throws Exception {
        Shunting_Yard sy = new Shunting_Yard();
        sy.infixToPostfix("a");
        StringBuilder builder = new StringBuilder();
        while (!sy.output.isEmpty()){
            builder.append(sy.output.remove());
        }

        Assert.assertEquals("a", builder.toString());
    }

}