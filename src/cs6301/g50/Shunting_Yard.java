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
import java.util.*;
import java.util.Map;

public class Shunting_Yard {

    private static final String REGEX_SPACE = "\\s";
    private static final String OPEN_PARANTHESIS = "(";
    private static final String CLOSE_PARANTHESIS = ")";
    public Queue<String> output = new LinkedList<String>(); // Public so that test
    private static int left_associative=0;
    private static int right_associative=1;
    final private static String STRING_EMPTY = "";

    /*
        Defining Legal Operators
     */
    private Map<String,int[]> operators = new HashMap<>();
    {
        operators.put("-", new int[] {0,left_associative});
        operators.put("+", new int[] {0,left_associative});
        operators.put("/", new int[] {1,left_associative});
        operators.put("*", new int[] {1,left_associative});
        operators.put("^", new int[] {2,right_associative});
    }
/*
   Method to check if the operator is of the passed associative type.
 */
    private boolean isLeftAssociative(String token,int type){
        return operators.get(token)[1] == type;

    }
/*
    Method to compare the precedence of two given operators
   @param  type operator 1 to be compared
   @param  type operator 2 to be compared
 */

    private int hasGreaterPrecedence(String operator_1, String operator_2){
        return operators.get(operator_1)[0]-operators.get(operator_2)[0];
    }

    /*
      This method checks if a given input is an operator
      @ param String token
      @ returns boolean
     */

    private boolean isOperator(String token) {
        return operators.containsKey(token);
    }

    /*
      This method implements the Shunting Yard Algorithm
      @ param input is a infix expression string
     */
    public void infixToPostfix(String s) {
        String token_type = STRING_EMPTY;
        Stack<String> op_stack = new Stack<>();
        String[] tokens = s.split(REGEX_SPACE);
        for (String token : tokens) {
            try {
                token_type = Tokenizer.tokenize(token).toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if (token_type.equals("VAR")) {
                output.add(token);
            }
            if (token_type.equals("FAC")) {
                output.add(token);
            }
            if(token_type.equals("OP")){
                while(!op_stack.empty() &&isOperator(op_stack.peek())){
                    // if the operand is left associative and if a has GreaterPrecedence than b
                    String otherToken = op_stack.peek();
                    if(isLeftAssociative(token,left_associative)&& hasGreaterPrecedence(token, otherToken)<=0){
                            output.add(op_stack.pop());
                            continue;
                    }
                    break;
                }
                op_stack.push(token);
            }
            if(token.equals(OPEN_PARANTHESIS)){
                op_stack.push(token);
            }
            if(token.equals(CLOSE_PARANTHESIS)) {
                while (!op_stack.peek().equals(OPEN_PARANTHESIS)) {
                       output.add(op_stack.pop());
                }
                op_stack.pop();
            }
        }

        while(!op_stack.empty()){
            output.add(op_stack.pop());
        }
    }


    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        Shunting_Yard sy = new Shunting_Yard();
        // Example input 1
        String input1 ="a + b * c / ( d - e ) ^ f ^ g";
        System.out.println( " Input 1 :  a + b * c / ( d - e ) ^ f ^ g");
        sy.infixToPostfix(input1);
        System.out.println(" Post Fix Expression ");
        while (!sy.output.isEmpty()){
            System.out.print(" " + sy.output.remove());
        }
        System.out.println( "                       "); // For clarity.
        System.out.println( "                       "); // For clarity.

        // Example input 2
        String input2 ="a / b ^ c + d * e - a * c";
        System.out.println( " Input 2 : a / b ^ c + d * e - a * c");
        sy.infixToPostfix(input2);
        System.out.println(" Post Fix Expression ");
        while (!sy.output.isEmpty()){
            System.out.print(" " + sy.output.remove());
        }




    }


}
