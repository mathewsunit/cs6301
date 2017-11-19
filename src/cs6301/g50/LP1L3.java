/*
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 * on 9/19/2017.
 */

package cs6301.g50;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class LP1L3 {
	ArrayList<Num> numFinalList = new ArrayList<Num>(); // Stores the final output values that will be 
	                                                    // printed on the console.
	Num tempList[] = new Num[26]; //stores the value of each variable.
    

	/* 
	   Evaluates each line of input of the following two forms,
	   - 'var' or 'var=<value>'   and
	   - 'var=<postfixexpression>'
	   Post-fix expressions are evaluated by means of a stack<Num>. 
    */
    public void evaluate(String line) {
    	if(line.length()==1) {
    		int index = Character.getNumericValue(line.charAt(0)) -  Character.getNumericValue('a');
    		numFinalList.add(tempList[index]);
    	}
    	else if(line.contains("+") || line.contains("-") || line.contains("*") || line.contains("/")
    				|| line.contains("%") || line.contains("^") || line.contains("|")) {
    		int index = Character.getNumericValue(line.charAt(0)) -  Character.getNumericValue('a');
    		Stack<Num> st = new Stack<Num>();    		
    		for(int i=2;i<line.length();i++) {
    			char c = line.charAt(i); 
    			Num a,b;
    			switch (c){
    				    case '+':
    					    st.push(Num.add(st.pop(),st.pop()));
    					    break;
    				    case '-':
    				    	a = st.pop();
    				    	b = st.pop();
    					    st.push(Num.subtract(b,a));
    					    break;
    				    case '*':
    					    st.push(Num.product(st.pop(),st.pop()));
    					    break;
    				    case '/':
    				    	a = st.pop();
    				    	b = st.pop();
    					    st.push(Num.divide(b,a));
    					    break;
    				    case '%':
    				    	a = st.pop();
    				    	b = st.pop();
    					    st.push(Num.mod(b,a));
    					    break;
    				    case '^':
    				    	a = st.pop();
    				    	b = st.pop();
    					    st.push(Num.power(b,a));
    					    break;
    				    case '|':
    					    st.push(Num.squareRoot(st.pop()));
    					    break;
    				    default:
    				    	if(Pattern.matches("[0-9]", Character.toString(c))) {
    				    		String numStr = "";
    				    		while(Pattern.matches("[0-9]", Character.toString(line.charAt(i))))
    				    		    	numStr += Character.toString(line.charAt(i++));
    				    		st.push(new Num(numStr));
    				    		i--;
    				    	}
    				    	else
    				    	    st.push(tempList[Character.getNumericValue(c)- Character.getNumericValue('a')]);
    				    	break;
    				}
    		}
    		tempList[index] = st.pop();
    		numFinalList.add(tempList[index]);   		
    	}
    	else {
    		int index = Character.getNumericValue(line.charAt(0)) -  Character.getNumericValue('a'); 
    		tempList[index] = new Num(line.substring(2));
    		numFinalList.add(tempList[index]);
    	}
    }
	 
    
    
    /* 
       Prints the output values of each expression.  At the end, internal representation of the 
       last variable that was assigned a value is printed.      
       For expressions of the form 'var ;' ,value of the variable is printed;
       For expressions of the form 'var = postfix expression;' ,value of the evaluated expression is printed;
    */
    public void printResult() {
    	int i;
        for(i=0;i<numFinalList.size();i++)
        	System.out.println(numFinalList.get(i));
        numFinalList.get(i-1).printList();
    }
    
    
    /* 
       Driver method for Level 3. 
       Post-fix expressions are expected to contain ONLY the operators {+, -, *, /, %, ^, |} along with
	   variables and integer constants ( [0-9]* ). Presence of other characters can result in exceptions. 
    */
	public static void main(String[] args) throws FileNotFoundException{
    	   Scanner in;
    	   if (args.length > 0) {
               File inputFile = new File(args[0]);
               in = new Scanner(inputFile);
           } else
               in = new Scanner(System.in);

	       LP1L3 x = new LP1L3();	
	       while(in.hasNext()) {
	            String line = in.nextLine(); // read line by line
	            line = line.replaceAll("\\s",""); // Remove all spaces in the expression.
	            if(line.equals(";"))
	            	break;
	            line = line.substring(0, line.length() - 1); // Remove ';' from the expression.
                x.evaluate(line);
	       }
	       x.printResult();
	       in.close();
        }
}
