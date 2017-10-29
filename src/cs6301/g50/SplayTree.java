/*
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

public class SplayTree<T extends Comparable<? super T>> extends BST<T> {
	
	/* Constructor */
    SplayTree() {
	    super();
    }


    /* Returns true if 'x' is part of the tree, false otherwise. */
    public boolean contains(T x) {
    	boolean ret = super.contains(x);
    	splay(x);
	    return ret;
    }
    
    
    /* Returns the element in tree that is equal to 'x', null otherwise. */
    public T get(T x) {
    	T ret = super.get(x);
    	splay(x);
	    return ret;
    }
    

    /*  If tree contains a node with same key, replaces element by 'x'. 
        Returns true if 'x' is a new element added to tree. */
    public boolean add(T x) {
    	boolean ret = super.add(x);
    	splay(x);
	    return ret;
    }

    
    /* Removes 'x' from tree. Returns 'x' if found, otherwise returns null */
    public T remove(T x) {
    	T ret = super.remove(x);
    	splay(x);
	    return ret;
    }
    
    
    /* Returns the maximum element */
    public T max(){
    	T ret = super.max();
    	splay(ret);
	    return ret;
    }
    
    
    /* Returns the minimum element */
    public T min(){
    	T ret = super.max();
    	splay(ret);
	    return ret;
    }
    
    
    
     /* Utility method to right rotate subtree rooted at 'x' */
     public void rightRotate(Entry x){
         Entry y = x.left;
         x.left = y.right;
         y.right = x;
     }
  
 
 
     /* Utility method to left rotate subtree rooted at 'x' */
     public void leftRotate(Entry x){
         Entry y = x.right;
         x.right = y.left;
         y.left = x;
     }
 
 
    
    /* Rearranges the tree with rotations such that top element of stack is at the root of the tree. */
    public void splay(T x){
        if(stack.size()==1) return; // only element 'null' is present in stack
        Entry t;
        if(stack.peek().left!=null && (stack.peek().left.element).compareTo(x)==0) 
        	t=stack.peek().left;
        else if(stack.peek().right!=null && (stack.peek().right.element).compareTo(x)==0) 
        	t=stack.peek().right;
        else 
        	t = stack.pop();
    	while(t != root) {
            if(t==root.left) {rightRotate(root); root = t;}
    		else if(t==root.right) {leftRotate(root); root= t;}
    		else {
    			Entry p_t = stack.pop();
    			Entry g_t = stack.pop();
    			if(g_t.left==p_t && p_t.left==t) {
    			    rightRotate(g_t);
    				rightRotate(p_t);
    			}
    			else if(g_t.right==p_t && p_t.right==t) {
    				leftRotate(g_t);
				    leftRotate(p_t);
				}
    			else if(g_t.left==p_t && p_t.right==t) {
    				leftRotate(p_t);
				    rightRotate(g_t);
    			}
    			else if(g_t.right==p_t && p_t.left==t) {
    				rightRotate(p_t);
				    leftRotate(g_t);
    			}
    			if(g_t==root) root = t;
    			else if(stack.peek().left==g_t) stack.peek().left = t;
    			else stack.peek().right = t;
    		}
    	}
    }
    
    
    public static void main( String [ ] args ){
        SplayTree<Integer> t = new SplayTree<>( );
        final int NUMS = 1000;
        for( int i = 0; i != NUMS; i++ ) {
            t.add( i );
            t.get(i-8);
            t.max();
            t.min();
            t.remove(i-3);
            t.contains(i-20);
        }
    }
}