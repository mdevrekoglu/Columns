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

        else {
            ColumnNode temp = head;
            while (temp != null) {
                if (ColumnNum == temp.getColumnNumber()) {
                    NumberNode temp2 = temp.getDown();
                    if (temp2 == null) {
                        NumberNode newNode = new NumberNode(number);
                        temp.setDown(newNode);
                    } else {
                        while (temp2.getNext() != null)
                            temp2 = temp2.getNext();
                        NumberNode newNode = new NumberNode(number);
                        temp2.setNext(newNode);
                    }

                }
                temp = temp.getRight();
            }
        }
    }

    public void printColumns(enigma.console.Console obj) {
        if (head == null)
            System.out.println("List is empty.");
        else {
            int y, x = 4;
            ColumnNode temp = head;
            while (temp != null) {
                y = 0;
                NumberNode temp2 = temp.getDown();
                while(temp2 != null){
                    obj.getTextWindow().setCursorPosition(x - 1,y + 3);
                    System.out.print("|" + temp2.getNumber());
                    if(temp2.getNumber() != 10)
                        System.out.print(" ");
                    System.out.print("|");
                    y++;
                    temp2 = temp2.getNext();
                }
                temp = temp.getRight();
                x += 8;
            }
            }
        }
        public int getColumnSize(int column) {
            if (head == null) {
                System.out.println("List is empty.");
                return 0;
            }
            else {
                ColumnNode temp = head;
                int count = 1;
                switch (column) {
                    case 1:
                        NumberNode temp2 = temp.getDown();
                        while (temp2.getNext() != null) {
                            count++;
                            temp2 = temp2.getNext();
                        }
                        return count;
                    case 2:
                        temp = temp.getRight();
                        NumberNode temp3 = temp.getDown();
                        while (temp3.getNext() != null) {
                            count++;
                            temp3 = temp3.getNext();
                        }
                        return count;
                    case 3:
                        temp = temp.getRight().getRight();
                        NumberNode temp4 = temp.getDown();
                        while (temp4.getNext() != null) {
                            count++;
                            temp4 = temp4.getNext();
                        }
                        return count;
                    case 4:
                        temp = temp.getRight().getRight().getRight();
                        NumberNode temp5 = temp.getDown();
                        while (temp5.getNext() != null) {
                            count++;
                            temp5 = temp5.getNext();
                        }
                        return count;
                    case 5:
                        temp = temp.getRight().getRight().getRight().getRight();
                        NumberNode temp6 = temp.getDown();
                        while (temp6.getNext() != null) {
                            count++;
                            temp6 = temp6.getNext();
                        }
                        return count;
                    default:
                        return count;
                }
            }
        }
    }
