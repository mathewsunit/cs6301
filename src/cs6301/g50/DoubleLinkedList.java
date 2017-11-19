package cs6301.g50;
/**
 * Created by Sarath on 9/23/2017.
 */
public class DoubleLinkedList<T> {

    Entry<T> head;
    Entry<T> tail;
    int listSize = 0;

    void addEntry(T data) {
        listSize++;
        if(head == null){
            head = new Entry<>(data);
            tail = head;
            return;
        }
        Entry<T> temp = new Entry<>(data);
        temp.prev = tail;
        tail.next = temp;
        tail=temp;
    }

    int size() {
        return listSize;
    }

    void print(){
        Entry<T> temp = head;
        while(temp.next!=null){
            System.out.print(temp.data+" , ");
            temp=temp.next;
        }
        System.out.print(temp.data+"\n");
    }

    static class Entry<T> {
        T data;
        Entry<T> next;
        Entry<T> prev;
        Entry(T data){
            this.data = data;
            next = prev = null;
        }
    }
}
