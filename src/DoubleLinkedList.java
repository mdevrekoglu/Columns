public class DoubleLinkedList {

    private DoubleLinkedListNode head;
    private DoubleLinkedListNode tail;

    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    public void add(Player dataToAdd) {

        if ((head == null) && (tail == null)) {
            DoubleLinkedListNode newNode = new DoubleLinkedListNode(dataToAdd);
            head = newNode;
            tail = newNode;
        } else {
            DoubleLinkedListNode newNode = new DoubleLinkedListNode(dataToAdd);
            DoubleLinkedListNode temp = head;
            while (temp.getNext() != null && (Integer.compare((Integer) dataToAdd.getScore(), (Integer) ((Player) temp.getNext().getData()).getScore()) > 0)) {
                temp = temp.getNext();
            }
            newNode.setPrev(temp);
            newNode.setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPrev(newNode);
            } else
                tail = newNode;
            temp.setNext(newNode);
        }
    }

    public void removeFirstElement() {
        if (head == null) {
            System.out.println("linked list is empty");
        } else if (size() == 1) {
            head = null;
        } else {
            DoubleLinkedListNode temp = head;
            temp.getNext().setPrev(null);
            if (head.getNext() != null)
                head = head.getNext();

        }
    }

    public int size() {
        if (head == null) {
            return 0;
        } else {
            int count = 0;
            DoubleLinkedListNode temp = head;

            while (temp != null) {
                temp = temp.getNext();
                count++;
            }

            return count;
        }
    }

    public void display() {
        if (head == null) {
            System.out.println("List is empty!");
        } else {
            DoubleLinkedListNode temp = tail;
            while (temp != null) {
                System.out.print(((Player) temp.getData()).getName() + " " + ((Player) temp.getData()).getScore());
                temp = temp.getPrev();
            }
        }
    }

}


