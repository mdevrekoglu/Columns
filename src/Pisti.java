import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pisti {

    
    // Additional variable
	private static Stack pistiDect;
	private static Stack poolDect;
	private static Card[] playerDect;
	private static Card[] computerDect;
	enigma.console.Console cn = Columns.cn;
    private int keypr;
    private int rkey;
    private KeyListener klis;

    


     Pisti() throws InterruptedException{

        listener();
        int playerScore = 0, computerScore = 0, turn = 1;     
        fillPistiBoxs();
        displayPisti();
        Card tempCard = null;
        keypr = 0;
        Boolean isSelected1 = false, isSelected2 = false;

        while(true){          

            if(keypr == 1){
                if(((rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) && playerDect[0] != null)
                || ((rkey == KeyEvent.VK_2 || rkey == KeyEvent.VK_NUMPAD2) && playerDect[1] != null)
                || ((rkey == KeyEvent.VK_3 || rkey == KeyEvent.VK_NUMPAD3) && playerDect[2] != null)
                || ((rkey == KeyEvent.VK_4 || rkey == KeyEvent.VK_NUMPAD1) && playerDect[3] != null)){
                    
                    if(rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1){
                        tempCard = playerDect[0];
                        playerDect[0] = null;
                    }
                    else if(rkey == KeyEvent.VK_2 || rkey == KeyEvent.VK_NUMPAD2){
                        tempCard = playerDect[1];
                        playerDect[1] = null;
                    }
                    else if(rkey == KeyEvent.VK_3 || rkey == KeyEvent.VK_NUMPAD3){
                        tempCard = playerDect[2];
                        playerDect[2] = null;
                    }
                    else if(rkey == KeyEvent.VK_4 || rkey == KeyEvent.VK_NUMPAD4){
                        tempCard = playerDect[3];
                        playerDect[3] = null;
                    }

                    if(tempCard.getNumber().equalsIgnoreCase("Joker")){
                        while(!poolDect.isEmpty()){
                            poolDect.pop();
                            playerScore++;
                        }
                    }
                    else if(tempCard.getNumber().equalsIgnoreCase(((Card)poolDect.peek()).getNumber())){
                        while(!poolDect.isEmpty()){
                            poolDect.pop();
                            playerScore++;
                        }
                    }
                    else{
                        poolDect.push(tempCard);
                    }

                
                    tempCard = null;                   
                    displayPisti();
                    Thread.sleep(1000);

                    
                    for (int i = 0; i < 4; i++) {
                        if(!poolDect.isEmpty() && computerDect[i] != null && computerDect[i].getNumber().equalsIgnoreCase("Joker")){
                            tempCard = computerDect[i];
                            computerDect[i] = null;
                            isSelected1 = true;
                            break;
                        }
                    }

                    if(!isSelected1){

                        for (int i = 0; i < 4; i++) {
                            if(computerDect[i] != null && ((Card)poolDect.peek()).getNumber().equalsIgnoreCase(computerDect[i].getNumber())){
                                tempCard = computerDect[i];
                                computerDect[i] = null;
                                isSelected2 = true;
                                break;
                            }
                        }

                        if(!isSelected2){

                            for (int i = 0; i < 4; i++) {
                                if(computerDect[i] != null){
                                    tempCard = computerDect[i];
                                    computerDect[i] = null;
                                    break;
                                }
                            }
                        }
                    }




                    if(tempCard.getNumber().equalsIgnoreCase("Joker")){
                        while(!poolDect.isEmpty()){
                            poolDect.pop();
                            computerScore++;
                        }
                    }
                    else if(tempCard.getNumber().equalsIgnoreCase(((Card)poolDect.peek()).getNumber())){
                        while(!poolDect.isEmpty()){
                            poolDect.pop();
                            computerScore++;
                        }
                    }
                    else{
                        poolDect.push(tempCard);
                    }

                    displayPisti();
                    Thread.sleep(1000);

                    turn++;
                    if(turn != 1 && (turn - 1) % 4 == 0){
                        for (int i = 0; i < 4; i++) {
                            playerDect[i] = (Card)pistiDect.pop();
                            computerDect[i] = (Card)pistiDect.pop();
                        }
                    }
                    isSelected1 = isSelected2 = false;
                    displayPisti();
                }
                keypr = 0;
            }
         
            Thread.sleep(50);
           
        }
        
	

    }

    private void listener() {			
		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);
	}

    private void displayPisti(){
        Columns.consoleClear();
		int countCards = 0;

		cn.getTextWindow().setCursorPosition(28, 9);
		System.out.print("+---------+");
		cn.getTextWindow().setCursorPosition(28, 10);
		System.out.print(String.format("|%-6s  ", ((Card)poolDect.peek()).getNumber()) +  ((Card)poolDect.peek()).getType() + "|");
		cn.getTextWindow().setCursorPosition(28, 11);
		System.out.print("+---------+");
		
		
		for (int i = 0; i < 4; i++) {

			if(playerDect[i] != null)
				countCards++;		
		}

		cn.getTextWindow().setCursorPosition(5, 20);

		for (int i = 0; i < 4; i++)
            if(playerDect[i] != null)
			    System.out.printf("+----%s----+\t", i + 1);

		cn.getTextWindow().setCursorPosition(5, 21);

		for (int i = 0; i < 4; i++)
			if(playerDect[i] != null)
				System.out.print(String.format("|%-6s  ", playerDect[i].getNumber())  +  playerDect[i].getType() + "|\t");

	}

    private void fillPistiBoxs() {
		pistiDect = new Stack(52);
		poolDect = new Stack(52);
		playerDect = new Card[4];
		computerDect = new Card[4];

		// Numberts 1 to 10
    	for(int i = 1; i <= 10; i++) {
    		pistiDect.push(new Card(Integer.toString(i), "S"));
			pistiDect.push(new Card(Integer.toString(i), "C"));
			pistiDect.push(new Card(Integer.toString(i), "D"));
			pistiDect.push(new Card(Integer.toString(i), "H"));
    	}

		// Joker
    	pistiDect.push(new Card("Joker", "S"));
		pistiDect.push(new Card("Joker", "C"));
		pistiDect.push(new Card("Joker", "D"));
		pistiDect.push(new Card("Joker", "H"));

		// Queen
		pistiDect.push(new Card("Queen", "S"));
		pistiDect.push(new Card("Queen", "C"));
		pistiDect.push(new Card("Queen", "D"));
		pistiDect.push(new Card("Queen", "H"));

		// King
		pistiDect.push(new Card("King", "S"));
		pistiDect.push(new Card("King", "C"));
		pistiDect.push(new Card("King", "D"));
		pistiDect.push(new Card("King", "H"));

		pistiDect.shuffle();

		// Filling the dects
		for(int i = 0; i < 4; i++){
			poolDect.push(pistiDect.pop());
			playerDect[i] = (Card)pistiDect.pop();
			computerDect[i] = (Card)pistiDect.pop();
		}
    }
    
}
