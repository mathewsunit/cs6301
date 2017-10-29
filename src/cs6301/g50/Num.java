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
import java.util.ArrayList;

public class Num  implements Comparable<Num> {
    static long defaultBase = 10;  // This can be changed to what you want it to be.
    static long base = defaultBase;  // Change as needed
    long val;
    Num next;
    boolean negsign;


    /***** Start of Level 1 *****/

    
    /* Constructor 1 */
    Num(String s) {
        ArrayList<Character> char_s = new ArrayList<Character>();
        for (char c : s.toCharArray())
            char_s.add(c);
        negsign = false;
        if(char_s.get(0)=='-'){
            negsign = true;
            char_s.remove(0);
        }
        if(!char_s.isEmpty()){
            this.val = Character.getNumericValue(char_s.get(char_s.size()-1));
            char_s.remove(char_s.size()-1);
            if(!char_s.isEmpty())
                this.next = new Num(char_s, negsign);
        }
    }



    boolean hasNext(){
        return next != null;
    }



    /* Constructor 2 */
    Num(ArrayList<Character> char_s, boolean negsign) {
        this.val = Character.getNumericValue(char_s.get(char_s.size()-1));
        this.negsign = negsign;
        char_s.remove(char_s.size()-1);
        if(!char_s.isEmpty())
            this.next = new Num(char_s, negsign);
    }



    /* Constructor 3 */
    Num(long x) {
        if(x>base){
            next = new Num(x/base);
            x = x%base;
        }
        if(x<0){
            val = -1*x;
            negsign = true;
        }else{
            val = x;
            negsign = false;
        }
    }



    static Num add(Num a, Num b, long carry) {
        long sum = b.val + a.val + carry;
        Num nextDigit = new Num(sum%base);
        carry = sum/base;
        if((a.hasNext())&&(b.hasNext()))
            nextDigit.next = add(a.next,b.next,carry);
        else if(a.hasNext())
            nextDigit.next = add(a.next,carry);
        else if(b.hasNext())
            nextDigit.next = add(b.next,carry);
        else if(carry>0)
            nextDigit.next = new Num(carry);
        return nextDigit;
    }



    static Num subtract(Num a, Num b, long carry) {
        long sum = a.val - b.val + carry;
        Num nextDigit;
        if(sum<0){
            carry = -1;
            nextDigit = new Num(sum+base);
        }else {
            nextDigit = new Num(sum%base);
            carry = sum/base;
        }
        if((a.hasNext())&&(b.hasNext())){
            nextDigit.next = subtract(a.next,b.next,carry);
            nextDigit.negsign = nextDigit.next.negsign;
        }else if(a.hasNext()){
            nextDigit.next = subtract(a.next,carry);
            if(nextDigit.hasNext()){
                nextDigit.negsign = nextDigit.next.negsign;
            }
        }else if(b.hasNext()){
            nextDigit.next = subtract(new Num(0), b.next,carry);
            if(nextDigit.hasNext()){
                nextDigit.negsign = nextDigit.next.negsign;
            }
        }else if(carry<0)
            nextDigit.negsign = true;
        return nextDigit;
    }



    static Num add(Num a, Num b) {
        if(a.negsign&&b.negsign)
            return negate(add(a,b,0));
        else if(!a.negsign&&!b.negsign)
            return add(a,b,0);
        else{
            Num numNext;
            if(a.negsign)
                numNext = subtract(b,a,0);
            else
                numNext = subtract(a,b,0);
            if(numNext.negsign){
                getComplement(numNext);
                numNext = add(numNext,1);
            }
            return numNext;
        }
    }



    static void getComplement(Num a){
        a.val = base-1-a.val;
        if(a.hasNext())
            getComplement(a.next);
    }



    static Num copy(Num a){
        Num nextNum = new Num(a.val);
        nextNum.negsign = a.negsign;
        if(a.hasNext())
            nextNum.next=copy(a.next);
        return nextNum;
    }



    static Num add(Num a, long carry){
        long sum = a.val + carry;
        Num nextDigit = new Num(sum%base);
        nextDigit.negsign = a.negsign;
        carry = sum/base;
        if(a.hasNext())
            nextDigit.next = add(a.next,carry);
        else if (carry > 0)
                nextDigit.next = new Num(carry);
        return nextDigit;
    }



    static Num removePadding(Num a){
        if(a.hasNext()) {
            a.next = removePadding(a.next);
            if(a.next==null && a.val==0)
                return null;
            return a;
        }else{
            if(a.val==0)
                return null;
            return a;
        }
    }



    static Num subtract(Num a, long carry){
        long sum = a.val + carry;
        Num nextDigit;
        if(sum<0){
            carry = -1;
            nextDigit = new Num(sum+base);
        }else {
            nextDigit = new Num(sum%base);
            carry = sum/base;
        }
        if(a.hasNext()){
            nextDigit.next = subtract(a.next,carry);
            if(nextDigit.hasNext()){
                nextDigit.negsign = nextDigit.next.negsign;
            }
        }else if(carry<0)
            nextDigit.negsign = true;
        return nextDigit;
    }



    static Num negate(Num a){
        a.negsign = !a.negsign;
        if(a.hasNext()){
            a.next = negate(a.next);
            if(null==a.next.next&&a.next.val==0)
                a.next = null;
        }
        return a;
    }



    static Num subtract(Num a, Num b) {
        if(a.negsign&&!b.negsign)
            return negate(add(a,b,0));
        else if(!a.negsign&&b.negsign)
            return add(a,b,0);
        else{
            Num numNext;
            if(a.negsign)
                numNext = subtract(b,a,0);
            else
                numNext = subtract(a,b,0);
            if(numNext.negsign){
                getComplement(numNext);
                numNext = add(numNext,1);
            }
            return numNext;
        }
    }



    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
        Num[] padded = padEqual(copy(a),copy(b));
        if(a.negsign!=b.negsign)
            return negate(removePadding(productRecursive(padded[0],padded[1], padded[2].getDecimalValue())));
        return removePadding(productRecursive(padded[0],padded[1], padded[2].getDecimalValue()));
    }



    static Num[] kSplit(Num a){
        Num aTrackerFull = a;
        Num aTrackerHalf = a;
        Num aTrackerPrevHalf = a;
        long length = 0;
        while(null!=aTrackerFull){
            length++;
            if(length%2==0){
                aTrackerPrevHalf = aTrackerHalf;
                aTrackerHalf = aTrackerHalf.next;
            }
            aTrackerFull = aTrackerFull.next;
        }
        aTrackerPrevHalf.next = null;
        //Hack that packages the length in the same function call
        return new Num[]{aTrackerHalf,a,new Num(length)};
    }



    static Num productRecursive(Num a, Num b, long length) {
        if(length==1){
            long product = a.val * b.val;
            return convertToBase(product);
        }
        Num[] a_split = kSplit(copy(a));
        Num[] b_split = kSplit(copy(b));
        long length3 = a_split[2].getDecimalValue();
        long length1 = length3%2==0?(length3/2):(length3/2+1);
        long length2 = length3/2;

        Num firstterm = productRecursive(a_split[0],b_split[0],length1);
        Num lastterm = productRecursive(a_split[1],b_split[1],length2);
        Num[] sumproducts = padEqual(add(a_split[0],a_split[1]),add(b_split[0],b_split[1]));

        Num midterm = productRecursive(sumproducts[0],sumproducts[1],sumproducts[2].getDecimalValue());

        Num firsttermPad = padRight(firstterm,2*length2);
        Num secondtermPad = padRight(subtract(subtract(midterm,lastterm),firstterm),length2);

        Num final_res = add(add(firsttermPad,secondtermPad),lastterm);

        return final_res;
    }


    static Num convertToBase(long number){
        long quotient = number/base;
        long remainder = number%base;
        if(quotient == 0)
            return new Num(remainder);
        else{
            Num res = new Num(remainder);
            res.next = convertToBase(quotient);
            return res;
        }
    }


    public long getDecimalValue(){
        if(this.hasNext()){
            return (this.val+10*(this.next.getDecimalValue()));
        }else{
            return this.val;
        }
    }



    static Num[] padEqual(Num a, Num b){
        Num aTrackerFull = a;
        Num bTrackerFull = b;
        long length = 0;
        while(null!=aTrackerFull&&null!=bTrackerFull){
            aTrackerFull = aTrackerFull.next;
            bTrackerFull = bTrackerFull.next;
            length++;
        }
        if(null!=aTrackerFull){
            long padding = 0;
            while (null!=aTrackerFull){
                aTrackerFull = aTrackerFull.next;
                padding++;
                length++;
            }
            b = padLeft(b,padding);
        }
        if(null!=bTrackerFull){
            long padding = 0;
            while (null!=bTrackerFull){
                bTrackerFull = bTrackerFull.next;
                padding++;
                length++;
            }
            a = padLeft(a,padding);
        }
        //Hack for returning length without extra call
        return new Num[] {a,b,new Num(length)};
    }



    static Num padRight(Num a, long padding){
        while (padding>0){
            Num addendum = new Num(0);
            addendum.next = a;
            a = addendum;
            padding --;
        }
        return a;
    }



    static Num padLeft(Num a, long padding){
        Num addendum = a;
        while (null!=addendum.next)
            addendum = addendum.next;
        while (padding>0){
            addendum.next = new Num(0);
            addendum = addendum.next;
            padding --;
        }
        return a;
    }



    // Use divide and conquer
    static Num power(Num a, long n) {
        if(n==0)
            return new Num(1);
        if (n == 1)
            return a;
        if(n%2==0)
            return power(product(a,a), n / 2);
        else
            return product(a,power(product(a,a), n / 2));
    }


    /***** End of Level 1 *****/




    /***** Start of Level 2 *****/


    static Num divide(Num a, Num b) {
        if(b.compareTo(new Num(0))==0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        if(a.compareTo(new Num(0))==0)
            return a;
        return divideR(removePadding(copy(a)),removePadding(copy(b)))[0];
    }



    static Num[] divideR(Num a, Num b) {
        if (b.compareTo(a)<0){
            if(a.hasNext()){
                if (b.compareTo(a.next)<0){
                    Num[] nextQR = divideR(a.next,b);
                    Num start = new Num(a.val);
                    start.next = nextQR[1];
                    Num quotient = new Num(0);
                    while(start.compareTo(b)>=0){
                        quotient = add(quotient,1);
                        start = subtract(start,b);
                    }
                    quotient.next = nextQR[0];
                    return new Num[]{quotient, start};
                }else{
                    Num start = copy(a);
                    Num quotient = new Num(0);
                    while(start.compareTo(b)>=0){
                        quotient = add(quotient,1);
                        start = subtract(start,b);
                    }
                    return new Num[]{quotient,start};
                }
            }else{
                Num start = copy(a);
                Num quotient = new Num(0);
                while(start.compareTo(b)>=0){
                    quotient = add(quotient,1);
                    start = subtract(start,b);
                }
                return new Num[]{quotient,start};
            }
        }else if(b.compareTo(a)>0){
            return new Num[]{new Num(0),copy(a)};
        }else{
            return new Num[]{new Num(1),new Num(0)};
        }
    }



    static Num mod(Num a, Num b) {
        if(a.compareTo(new Num(0))==0){
            return a;
        }
        if(b.compareTo(new Num(0))==0){
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        }
        return divideR(removePadding(a),removePadding(b))[1];
    }

    /* returns a^n, where a and n are both Num */
    static Num power(Num a, Num n) {
    	if(n.compareTo(new Num(0))==0)
    		return new Num(1);
    	if(n.compareTo(new Num(1))==0)
    		return a;
    	Num temp = mod(n,new Num(2));
    	if(temp.compareTo(new Num(0))==0)
    	    return power(product(a,a),divide(n,new Num(2)));
    	else{
    	    Num divisor = divide(n,new Num(2));
    	    Num product = product(a,a);
            Num other = power(product,divisor);
            return product(a,other);
        }
    }

    /*returns the square root of 'a' (truncated)*/
    static Num squareRoot(Num a) {
    	if(a.compareTo(new Num("1"))<=0)
    		return a;
    	Num begin = new Num(1);
    	Num end = divide(a,new Num(2));
    	Num ret = new Num("0");
    	while(begin.compareTo(end)<=0) {
    		Num mid  = divide(add(begin,end),new Num(2));
    		Num temp = product(mid,mid);
    	    if(temp.compareTo(a)==0)
    		    return mid;
    	    else if (temp.compareTo(a) > 0)
    	    	end = subtract(mid, new Num(1));
    	    else {
    	    	begin = add(mid,1); 
    	    	ret = mid;
    	    } 
    	}
    	return ret;
    }


    /***** End of Level 2 *****/



    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
	    if(hasNext()&&other.hasNext()){
            int prev = next.compareTo(other.next);
            if(prev==0){
                if(val==other.val)
                    return 0;
                else
                    return val>other.val?1:-1;
            }
            return prev;
        }else if(hasNext()){
            if(other.val==val){
                Num tracker = next;
                while(null!=tracker){
                    if(tracker.val>0)
                        return 1;
                    else
                        tracker = tracker.next;
                }
                return 0;
            } else if(other.val>val){
                Num tracker = next;
                while(null!=tracker){
                    if(tracker.val>0)
                        return 1;
                    else
                        tracker = tracker.next;
                }
                return -1;
            }
            return 1;
        }else if(other.hasNext()){
            if(other.val==val){
                Num tracker = other.next;
                while(null!=tracker){
                    if(tracker.val>0){
                        return -1;
                    }else{
                        tracker = tracker.next;
                    }
                }
                return 0;
            }else if(val>other.val){
                Num tracker = other.next;
                while(null!=tracker){
                    if(tracker.val>0){
                        return -1;
                    }else{
                        tracker = tracker.next;
                    }
                }
                return 1;
            }
            return -1;
        }else{
            if(val==other.val){
                return 0;
            }else{
                return val>other.val?1:-1;
            }
        }
    }



    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList() {
    	System.out.print(base() + ": ");
    	Num temp = this;
    	System.out.print(temp.val + " ");
    	while(temp.hasNext()) {
    		temp = temp.next;
    		System.out.print(temp.val + " ");
    	}
    	System.out.println("\n");    	
    }



    // Return number to a string in base 10
    public String toString() {
	    Num tracker = this;
	    String initial = "";
	    while(null!=tracker){
            initial = (Long.toString(tracker.val))+initial;
            tracker = tracker.next;
        }
        if(this.negsign){
            initial = "-"+initial;
        }
        return initial;
    }



    public long base() { return base; }



    public static void main(String args[]){
//        Num test = new Num("11");
        Num test = new Num("9");
        Num test2 = new Num("-91");
//        Num test2 = new Num("0");
//        Num testm = add(test, test2);
        int num = test2.compareTo(test);
        Num testm = power(test, 7);
//        removePadding(testm);
//        test = new Num(50000);
        int a =1;
    }
}
