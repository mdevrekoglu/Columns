public class MultiLinkedList {
    private ColumnNode head;

    public void addColumn(int columnNumber) {
        if (head == null) {
            head = new ColumnNode(columnNumber);
        } else {
            ColumnNode temp = head;
            while (temp.getRight() != null)
                temp = temp.getRight();
            ColumnNode newNode = new ColumnNode(columnNumber);
            temp.setRight(newNode);
        }
    }

    public void addNumber(int ColumnNum, int number) {
        if (head == null)
            System.out.println("Please add a column first.");

        else{
            ColumnNode temp = head;
            while(temp != null){
                if(ColumnNum == temp.getColumnNumber()){
                    NumberNode temp2 = temp.getDown();
                    if(temp2 == null){
                        NumberNode newNode = new NumberNode(number);
                        temp.setDown(newNode);
                    }
                    else{
                        while(temp2.getNext() != null)
                            temp2 = temp2.getNext();
                        NumberNode newNode = new NumberNode(number);
                        temp2.setNext(newNode);
                    }

                }
                temp = temp.getRight();
            }
        }
        }
    }
