package iview;

import java.util.function.Consumer;

/**
 * Insert an item into linked list
 *
 * @author sshchahratsou
 */
public class LinkedListQuestion {
    private static class Item {
        Item next;
        private final String data;

        public Item(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data;
        }
    }

    private static class LinkedList {
        private Item start;

        public LinkedList(Item start) {
            this.start = start;
        }

        public void traverse(Consumer<Item> consumer) {
            Item current = start;
            while (current != null) {
                consumer.accept(current);
                current = current.next;
            }
        }

        public void insert(int pos, Item item) {
            Item current = start;
            Item previous = null;
            int i = 0;
            while (i++ < pos) {
                previous = current;
                current = current.next;
            }
            item.next = current;
            if (previous == null) {
                start = item;
            } else {
                previous.next = item;
            }
        }
    }

    public static void main(String[] args) {
        Item item1 = new Item("first");
        item1.next = new Item("second");

        LinkedList list = new LinkedList(item1);
        list.insert(0, new Item("zero"));
        list.insert(2, new Item("xxx"));
        list.insert(4, new Item("zzz"));

        list.traverse(System.out::println);
    }
}
