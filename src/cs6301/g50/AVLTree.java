package cs6301.g50;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

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

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {

    private Entry root;
    private int numNodes;

    class Entry extends BST<T>.Entry {
        int height;

        Entry(T x, BST<T>.Entry left, BST<T>.Entry right) {
            super(x, left, right);
            height = 0;
        }

        Entry(T x) {
            super(x);
            height = 0;
        }
    }

    @Override
    public boolean add(T x) {
        Entry e = (Entry) find(root, x);
        if (e != null && e.element.compareTo(x) == 0)
            return false;
        this.root = add(root, x);
        numNodes++;
        return true;
    }

    private Entry add(Entry root, T x) {
        if (root == null) return new Entry(x);
        if (x.compareTo(root.element) > 0) {
            root.right = add((Entry) root.right, x);
        } else if (x.compareTo(root.element) < 0) {
            root.left = add((Entry) root.left, x);
        }
        return balance(root);
    }

    @Override
    public T remove(T x) {
        Entry e = (Entry) find(root, x);
        if (e != null && e.element.compareTo(x) == 0) {
            remove(root, x);
            numNodes--;
        }
        return x;
    }

    private Entry remove(Entry root, T x) {
        if (root == null) return null;
        if (x.compareTo(root.element) < 0)
            root.left = remove((Entry) root.left, x);
        else if (x.compareTo(root.element) > 0)
            root.right = remove((Entry) root.right, x);
        else if (root.left != null && root.right != null) // Two children
        {
            root.element = find(root.right, root.element).element;
            root.right = remove((Entry) root.right, root.element);
        } else
            root = (Entry) ((root.left != null) ? root.left : root.right);
        return balance(root);
    }

    @Override
    public boolean contains(T x) {
        Entry entry = (Entry) find(root, x);
        return find(root, x) != null && entry.element.compareTo(x) == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator(root);
    }


    private Entry balance(Entry root) {
        if (root == null)
            return null;

        if (getHeight((Entry) root.left) - getHeight((Entry) root.right) > 1)
            root = getHeight((Entry) root.left.left) >= getHeight((Entry) root.left.right) ?
                    rotateWithLeft(root) : doubleRotationWithLeft(root);
        else if (getHeight((Entry) root.right) - getHeight((Entry) root.left) > 1)
            root = getHeight((Entry) root.right.right) >= getHeight((Entry) root.right.left) ?
                    rotateWithRight(root) : doubleRotationWithRight(root);

        root.height = Math.max(getHeight((Entry) root.left), getHeight((Entry) root.right)) + 1;
        return root;
    }


    /**
     * Rotate binary tree node with left child. For AVL trees, this is a single
     * rotation for case 1. Update heights, then return new root.
     */

    private Entry rotateWithLeft(Entry node) {
        Entry leftNode = (Entry) node.left;
        Entry leftRightNode = (Entry) leftNode.right;

        leftNode.right = node;
        node.left = leftRightNode;

        node.height = Math.max(getHeight((Entry) node.left), getHeight((Entry) node.right)) + 1;
        leftNode.height = Math.max(getHeight((Entry) leftNode.left), getHeight((Entry) leftNode.right)) + 1;

        return leftNode;
    }

    /**
     * Rotate binary tree node with right child. For AVL trees, this is a single
     * rotation for case 4. Update heights, then return new root.
     */
    private Entry rotateWithRight(Entry node) {
        Entry rightNode = (Entry) node.right;
        Entry rightLeftNode = (Entry) rightNode.left;

        rightNode.left = node;
        node.right = rightLeftNode;

        node.height = Math.max(getHeight((Entry) node.left), getHeight((Entry) node.right)) + 1;
        rightNode.height = Math.max(getHeight((Entry) rightNode.left), getHeight((Entry) rightNode.right)) + 1;

        return rightNode;

    }

    private Entry doubleRotationWithLeft(Entry k3) {
        k3.left = rotateWithRight((Entry) k3.left);
        return rotateWithLeft(k3);
    }

    /**
     * Double rotate binary tree node: first right child with its left child;
     * then node k1 with new right child. For AVL trees, this is a double
     * rotation for case 3. Update heights, then return new root.
     */
    private Entry doubleRotationWithRight(Entry node) {
        node.right = rotateWithLeft((Entry) node.right);
        return rotateWithRight(node);
    }

    private int getHeight(Entry node) {
        return node == null ? -1 : node.height;
    }

    void printLevelOrder(Entry root) {
        System.out.println();
        int h = getHeight(root);
        int i;
        for (i = 0; i <= h; i++) {
            printGivenLevel(root, i);
            System.out.println();
        }
    }

    /* Print nodes at a given level */
    void printGivenLevel(Entry root, int level) {
        if (root == null) {
            System.out.print("[]");
            return;
        }
        if (level == 0) {
            System.out.print(root.element + "  ");
        } else if (level > 0) {
            printGivenLevel((Entry) root.left, level - 1);
            printGivenLevel((Entry) root.right, level - 1);
        }
    }

    public void displayTree() {
        System.out.println("\n");
        Stack<Entry> globalStack = new Stack<>();
        globalStack.push(root);
        int emptyLeaf = (int) Math.pow(2, root.height + 1);
        boolean isRowEmpty = false;
        while (!isRowEmpty) {
            Stack<Entry> localStack = new Stack<>();
            isRowEmpty = true;
            for (int j = 0; j < emptyLeaf; j++)
                System.out.print(" ");
            while (!globalStack.isEmpty()) {
                Entry temp = globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.element + " ");
                    localStack.push((Entry) temp.left);
                    localStack.push((Entry) temp.right);
                    if (temp.left != null || temp.right != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("[]");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < emptyLeaf * 2 - 2; j++)
                    System.out.print(" ");
            }
            System.out.println();
            emptyLeaf /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        System.out.println("\n\n");

    }

    @Override
    public int size() {
        return numNodes;
    }

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                tree.add(x);
//                tree.printLevelOrder(tree.root);
                tree.displayTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                tree.remove(-x);
//                tree.printLevelOrder(tree.root);
                tree.displayTree();
            } else {
                Comparable[] arr = tree.toArray(tree.root);
                System.out.print("Final: ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                System.out.println("Verification : ");
                tree.printBalanceFactors();
                System.out.println("Contains : 10 ? " + tree.contains(10));
                System.out.println("Contains : 3 ? " + tree.contains(3));

                return;
            }
        }
        System.out.println("Done");
    }

    public void printBalanceFactors() {
        printBalanceFactors(root);
    }

    private void printBalanceFactors(Entry root) {
        if (root == null) return;
        System.out.println("Node : " + root.element + " Balance Factor : "
                + Math.abs(getHeight((Entry) root.left) - getHeight((Entry) root.right)));
        printBalanceFactors((Entry) root.left);
        printBalanceFactors((Entry) root.right);

    }

}