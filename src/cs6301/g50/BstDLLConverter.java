package cs6301.g50;
import java.util.Random;

/**
 * Created by G50
 * Venkata Sarath Chandra Prasad Nelapati
 * Varun Simha Balaraju
 * Jithin Paul
 * Sunit Mathew
 */
public class BstDLLConverter<T> {

    // Global pointer - head pointer of given DLL
    DoubleLinkedList.Entry<T> head = null;

    /*
    * Method to convert DLL to BST
    * @params size of DLL
     */
    DoubleLinkedList.Entry<T> dllToBst(int size) {
        if (size <= 0) return null;
        DoubleLinkedList.Entry<T> left = dllToBst(size / 2);
        DoubleLinkedList.Entry<T> root = head;
        root.prev = left;
        head = head.next;
        DoubleLinkedList.Entry<T> right = dllToBst(size - (size / 2) - 1);
        root.next = right;
        return root;
    }

    //Global pointers
    DoubleLinkedList.Entry<T> listHead = null;
    DoubleLinkedList.Entry<T> listPrev = null;

    /*
     * Method to convert BST to DLL
     * @params root of the BST
     * @return void - sets listHead pointer to the starting of the converted DLL.
     */
    void bstToDll(DoubleLinkedList.Entry<T> root) {
        if (root == null) return;

        //convert left subtree to a list
        bstToDll(root.prev);

        // will be true at leftmost node
        if (listPrev == null) {
            listHead = root;
        } else {
            root.prev = listPrev;
            listPrev.next = root;
        }
        listPrev = root;

        //convert right subtree as list
        bstToDll(root.next);
    }

    public static void main(String[] args) {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();

        // Sorted DLL
        Random rand = new Random();
        for (int i = 1; i < 20; i += rand.nextInt(3) + 1) {
            list.addEntry(i);
        }

        BstDLLConverter<Integer> dllToBst = new BstDLLConverter<>();
        System.out.println("Doubly LinkedList Sorted : ");
        list.print();

        dllToBst.head = list.head;

        DoubleLinkedList.Entry<Integer> root = dllToBst.dllToBst(list.size());
        System.out.println("\nBalanced binary search tree : ");
        System.out.print("\t Inorder : ");
        dllToBst.printInorder(root);
        System.out.print("\n\t PostOrder : ");
        dllToBst.postOrder(root);

        // the same tree to DLL
        dllToBst.bstToDll(root);
        System.out.println("\n\nBST to DLL :");
        dllToBst.printList(dllToBst.listHead);

    }

    void printInorder(DoubleLinkedList.Entry<T> root) {
        if (root == null) return;
        printInorder(root.prev);
        System.out.print(root.data + " , ");
        printInorder(root.next);
    }

    void postOrder(DoubleLinkedList.Entry<T> root) {

        if (root == null) return;
        System.out.print(root.data + " , ");
        postOrder(root.prev);
        postOrder(root.next);
    }

    void printList(DoubleLinkedList.Entry<T> head) {
        while (head.next != null) {
            System.out.print(head.data + " , ");
            head = head.next;
        }
        System.out.print(head.data);
    }

    void printTree(DoubleLinkedList.Entry<T> root, int step) {
        if (root == null) return;
        step += 5;
        printTree(root.next, step);
        System.out.println();
        for (int i = 5; i < step; i++)
            System.out.print(" ");
        System.out.print(root.data + "\n");
        printTree(root.prev, step);
    }
}
