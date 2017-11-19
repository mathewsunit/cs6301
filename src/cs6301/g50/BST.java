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
import java.util.*;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    protected Stack<Entry> stack = new Stack<Entry>();
    protected Entry root;
    private Entry previous;

    
    
    class Entry {
        T element;
        Entry left, right;

        Entry(T x) {
            this.element = x;
        }

        Entry(T x, Entry left, Entry right) {
            this.left = left;
            this.right = right;
            this.element = x;
        }
    }

    
    
    public BST() {
        this.root = null;
    }

    
    
    public BST(Comparator<T> cmp) {
        this.root = null;
    }

    
    
    public int size() {
        return size(root);
    }

    
    
    private int size(Entry x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    
    
    Entry find(T x) {
        stack = new Stack<Entry>();
        stack.push(null);
        if (root == null) return null;
        return find(root, x);
    }

    
    
    T get(T x) {
        return find(x).element;
    }

    
    
    /**   @params : t - Root node
     *    @params : x - Element to be searched
     *    @return : If element was found that node is returned else
     *    in case of add it return the Node where new element
     *    is to be added and in remove if element doesn't equal element
     *    passed will return the proper node where remove is to be done.
     **/
    Entry find(Entry t, T x) {
        if (t == null) return t;
        if (t.element == x) return t;
        if (x.compareTo(t.element) == -1) {
            if (t.left == null) {
                return t;
            }
            stack.push(t);
            return find(t.left, x);
        } else if (x.compareTo(t.element) == 1) {
            if (t.right == null) {
                return t;
            }
            stack.push(t);
            return find(t.right, x);
        }
        return t;
    }

    
    
    /**  Method used to check if an element is in BST.
     *   @params: x element to search in the tree
     *   @return: returns true if found, false otherwise.
     **/
    public boolean contains(T x) {
        Entry t = find(x);
        return (t != null && t.element.equals(x));
    }

    
    
    public T min() {
        if (root == null) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        stack = new Stack<>();
        stack.push(null);
        return min(root).element;
    }

    
    
    protected Entry min(Entry x) {
        if (x.left == null) return x;
        else {
            stack.push(x);
            return min(x.left);
        }
    }

    
    
    /**  Finds the max element
     *  @returns : max element value.
     **/
    public T max() {
        if (root == null) {
            return null;
        }
        stack = new Stack<>();
        stack.push(null);
        Entry t = root;
        while (t.right != null) {
            stack.push(t);
            t = t.right;
        }
        return t.element;
    }

    
    
    /**   Add method finds the location where an element should be added
     *    using find and adds the element at the appropriate location.
     *    @params Node value to be added
     **/
    public boolean add(T x) {
        if (x == null) throw new IllegalArgumentException("cannot call add() with a null element");
        if (root == null) {
            root = new Entry(x);
        }
        Entry t = find(x);
        if (x.compareTo(t.element) == 0) {
            t.element = x;
            return false;
        } else if (x.compareTo(t.element) == -1) {
            t.left = new Entry(x);
        } else {
            t.right = new Entry(x);
        }
        return true;
    }

    

    /**   Remove method finds the location where the element is to be removed
     *    using find method and then removes element at that appropriate method.
     *    @params : x- Node value to be deleted
     **/
    public T remove(T x) {
        if (x == null) throw new IllegalArgumentException("cannot call remove() with a null element");
        if (root == null) {
            return null;
        }
        Entry t = find(x);
        if (t.element != x) {
            return null;
        }
        T result = t.element;
        if (t.left == null || t.right == null) {
            bypass(t);
        } else {
            stack.push(t);
            Entry minRight = find(t.right, t.element);
            t.element = minRight.element;
            bypass(minRight);
        }
        return result;
    }

    
    
    public void bypass(Entry t) {
        Entry pt = stack.peek();
        Entry c = t.left == null ? t.right : t.left;
        if (pt == null) {
            root = c;
        } else if (pt.left == t) {
            pt.left = c;
        } else {
            pt.right = c;
        }
    }

    
    
    public Comparable[] toArray(Entry root) {
        Comparable[] arr = new Comparable[size()];    /* write code to place elements in array here */
        List<Entry> nodeList = new ArrayList<>();
        toArray(root, nodeList);
        int i = 0;
        for (Entry e : nodeList) arr[i++] = e.element;
        return arr;
    }

    
    
    private void toArray(Entry root, List<Entry> list) {
        if (root == null) return;
        toArray(root.left, list);
        list.add(root);
        toArray(root.right, list);
    }

    
    
    public Iterator iterator() {
        return new BSTIterator(root);
    }

    
    
    class BSTIterator implements Iterator {
        public BSTIterator(Entry root) {
            stack = new Stack<Entry>();
            traverseLeft(root);
        }

        /**
         * return whether we have a next smallest number
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * returns the next smallest number
         */
        public T next() {
            Entry t = stack.pop();
            traverseLeft(t.right);
            return t.element;
        }

        private void traverseLeft(Entry t) {
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }
    }
    
    /**BST Verification
     *@params T - the root or the node from where test to be done.
     **/
    public boolean isBst(Entry t) {
        if (t != null) {
            if (!isBst(t.left)) return false;
            if (previous != null && (t.element.compareTo(previous.element) == -1 || t.element.compareTo(previous.element) == 0))
                return false;
            previous = t;
            return isBst(t.right);
        }
        return true;
    }

    public static void main(String args[]){
        BST<Integer> bst = new BST();

        for(int i=0;i<1280;i++){
            bst.add(Integer.valueOf(i));
            bst.remove(Integer.valueOf(i));
        }
    }
}