// Sample code for Level 2 driver for lp1

// Change following line to your group number
package cs6301.g50;

public class LP1L2 {
    public static void main(String[] args) {
    	for(int  i=1; i<Integer.MAX_VALUE; i++){
			Num x = new Num(i);
			Num z = Num.squareRoot(x);
			if(z.compareTo(Num.product(x,x))>0){
				System.out.println(z);
			}
		}
    }
}
