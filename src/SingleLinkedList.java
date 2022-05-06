public class SingleLinkedList {
	Node head;

	public void add(Object data) {

		if(head == null) {
			Node newNode = new Node(data);
			head = newNode;
		}
		else {
			Node temp = head;
			Node newNode = new Node(data);

			while(temp.getLink() != null) {
				temp = temp.getLink();
			}

			temp.setLink(newNode);
		}
	}

	public int size() {

		if(head == null) {
			return 0;
		}
		else {
			int counter = 0;
			Node temp = head;

			while(temp != null) {
				counter++;
				temp = temp.getLink();
			}

			return counter;
		}
	}

	public void display() {
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else {
			Node temp = head;

			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
	}

	public int findMax() {
		if(head == null) {
			System.out.println("Linked list is empty!");
			return Integer.MIN_VALUE;
		}
		else {
			int maxValue = Integer.MIN_VALUE;
			Node temp = head;

			while(temp != null) {
				if((Integer)temp.getData() > maxValue) {
					maxValue = (Integer)temp.getData();
				}
				temp = temp.getLink();
			}
			return maxValue;
		}
	}

	public int findMin() {
		if(head == null) {
			//System.out.println("Linked list is empty!");
			return Integer.MAX_VALUE;
		}
		else {
			int minValue = Integer.MAX_VALUE;

			Node temp = head;

			while(temp != null) {
				if((Integer)temp.getData() < minValue) {
					minValue = (Integer)temp.getData();
				}
				temp = temp.getLink();
			}
			return minValue;
		}
	}

	public Object peek() {
		if(head == null) {
			return 'X';
		}
		else {
			return head.getData();
		}
	}

	public void pop() {
		if(head == null) {
			System.out.print('X');
		}
		else{
			System.out.print(head.getData());
			head = head.getLink();
		}
	}

	public boolean isNumberCountFull(Object data) {
		if(head == null) {
			return false;
		}
		else {
			Node temp = head;
			int sameNumberCounter = 0;
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)data) {
					sameNumberCounter++;
				}
				temp = temp.getLink();
			}
			if(sameNumberCounter >= 5)
				return true;
			else
				return false;

		}
	}
}
