package iview;

import java.util.function.Consumer;

/**
 * Traverse Binary Tree
 */
public class BTreeTraversal {
    public static void main(String[] args) {
        Node root = new Node(null, "root");
        root.left = new Node(root, "lvl 1, left");
        ((Node) root.left).left = new Leaf(root.left, "lvl2, left leaf");
        ((Node) root.left).right = new Leaf(root.left, "lvl2, right leaf");
        root.right = new Leaf(root, "lvl1, right leaf");

        traverse(root, System.out::println);
    }

    private static void traverse(Item item, Consumer<Item> itemConsumer) {
        itemConsumer.accept(item);
        for (Item child : item.getChildren()) {
            traverse(child, itemConsumer);
        }
    }
}

abstract class Item {
    final Item parent;
    final String name;

    public Item(Item parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    protected abstract Item[] getChildren();

    @Override
    public String toString() {
        return name;
    }
}

class Leaf extends Item {
    public Leaf(Item parent, String name) {
        super(parent, name);
    }

    @Override
    protected Item[] getChildren() {
        return new Leaf[]{};
    }
}

class Node extends Item {
    public Node(Item parent, String name) {
        super(parent, name);
    }

    Item left;
    Item right;

    @Override
    protected Item[] getChildren() {
        if (left != null) {
            if (right != null) {
                return new Item[]{left, right};
            } else {
                return new Item[]{left};
            }
        } else {
            if (right != null) {
                return new Item[]{right};
            } else {
                return new Item[]{};
            }
        }
    }
}