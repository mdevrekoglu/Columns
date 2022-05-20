import java.awt.Color;

import enigma.console.TextAttributes;

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
                    String text = String.format("|%-2d|", temp2.getNumber());
                    System.out.print(text);
                    y++;
                    temp2 = temp2.getNext();
                }
                obj.getTextWindow().setCursorPosition(x - 1,y + 3);
                //System.out.print("+--+");
                temp = temp.getRight();
                x += 8;
            }
        }
    }

    public int sizeOfColumn(int column) {
    	
    	if(head == null) {
    		return -1;
    	}
    	else {
    		ColumnNode temp = head;
    		
    		int counter = 0;		
    		while(temp != null) {
    			temp = temp.getRight();
    			counter++;
    		}
    		
    		if(counter >= column) {
    			temp = head;
    			
    			for(int i = 1; i < column; i++) {
    				temp = temp.getRight();
    			}
    			
    			NumberNode temp2 = temp.getDown();
    			counter = 0;		
    			while(temp2 != null) {
    				counter++;   
    				temp2 = temp2.getNext();
    			}
    			return counter;   			
    		}
    		else {
    			return -1;
    		}
    	}
    }

    public void printeColoredColumn(enigma.console.Console obj, int column, int color) {
    	if(head == null) {
    		
    	}
    	else {
    		ColumnNode temp = head;
    		TextAttributes coloredNumber = new TextAttributes(Color.RED, Color.BLACK);
    		
    		for(int i = 1; i < column; i++)
    			temp = temp.getRight();
    		
    		NumberNode temp2 = temp.getDown();
    		int counter = 0;
    		while(temp2 != null) {
    			obj.getTextWindow().setCursorPosition(((column) * 8) - 5, counter + 3);
    			String text = String.format("|%-2d|", temp2.getNumber());
    			if(counter >= color)
    				obj.getTextWindow().output(text, coloredNumber);
    			else
    				obj.getTextWindow().output(text);
    			temp2 = temp2.getNext();
    			counter++;
    		}
    		//obj.getTextWindow().setCursorPosition(column * 8 - 5, counter);
    		//obj.getTextWindow().output("+--+", coloredNumber);
    	}
    }
    
    public void transfer(int firstColumn, int secondColumn, int element) {
    	
    	
    	ColumnNode tempColumn1 = head;
		ColumnNode tempColumn2 = head;
		
		
		for(int i = 1; i < firstColumn; i++)
			tempColumn1 = tempColumn1.getRight();
		
		for(int i = 1; i < secondColumn; i++)
			tempColumn2 = tempColumn2.getRight();
		
		NumberNode column1 = tempColumn1.getDown();
		NumberNode column2 = tempColumn2.getDown();
		
		
		int[] arr = new int[50];
		int counter = 0;
		while(column1 != null) {    			
			arr[counter] = column1.getNumber();
			column1 = column1.getNext();
			counter++;
		}
		
		
		if(tempColumn2.getDown() == null ) {
			if(arr[element] == 1 || arr[element] == 10) {
				tempColumn2.setDown(new NumberNode(arr[element]));
				column2 = tempColumn2.getDown();
				for (int i = element + 1; i < counter; i++) {
					NumberNode temp = new NumberNode(arr[i]);

					column2.setNext(temp);
					column2 = column2.getNext();
				}

				column1 = tempColumn1.getDown();

				if (element == 0) {
					tempColumn1.setDown(null);
				} else {
					for (int i = 1; i < element; i++) {
						column1 = column1.getNext();
					}
					column1.setNext(null);
				}
				Columns.transfer++;
			}
		}
		
		else {
		
		while(column2.getNext() != null) {
			column2 = column2.getNext();
		}
		
		
		
		int diff = Math.abs(arr[element] - column2.getNumber());
		
		if(diff <= 1) {
			Columns.transfer++;
			for(int i = element; i < counter; i++) {
				NumberNode temp = new NumberNode(arr[i]);
				
				column2.setNext(temp);
				column2 = column2.getNext();
			}
			
			column1 = tempColumn1.getDown();
			
			if(element == 0) {
				tempColumn1.setDown(null);
			}
			else {
				for(int i = 1; i < element; i++) {
    				column1 = column1.getNext();
    			}
    			column1.setNext(null);
			}  			
			 			
		}
		}
		
		Columns.printGameArea();
    	
    	
    	
    	
    	
    }
}
