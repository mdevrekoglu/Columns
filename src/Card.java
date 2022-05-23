import enigma.console.TextAttributes;

public class Card {

	String number;
	String type;
	TextAttributes coloredNumber;
	
	
	public Card(String number, String type, TextAttributes coloredNumber) {
		super();
		this.number = number;
		this.type = type;
		this.coloredNumber = coloredNumber;
	}	
}