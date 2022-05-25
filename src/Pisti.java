import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pisti {

    // Additional variable
	private static Stack pistiDect;
	private static Stack poolDect;
	private static Card[] playerDect;
	private static Card[] computerDect;

    // The enigma console from main class
	enigma.console.Console cn = Columns.cn;

    // Necessary variables to use keyboard
    private int keypr;
    private int rkey;
    private KeyListener klis;

    // Scores and other stuff
    private int playerScore = 0, computerScore = 0, turn = 24;
    private Boolean isSelected1, isSelected2;
    private Card tempCard;

     Pisti() throws InterruptedException{

        // To add keyboard listener
        listener();
             
        // To fill boxses
        fillPistiBoxs();

        // To print game area
        displayPisti();

        // An temp variable to store 'Selected card'
        tempCard = null;
        isSelected1 = isSelected2 = false;

        // Boleans for loops and if statements
        keypr = 0;

        while(true){          

            // If key pressed
            if(keypr == 1){
                if(((rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) && playerDect[0] != null)
                || ((rkey == KeyEvent.VK_2 || rkey == KeyEvent.VK_NUMPAD2) && playerDect[1] != null)
                || ((rkey == KeyEvent.VK_3 || rkey == KeyEvent.VK_NUMPAD3) && playerDect[2] != null)
                || ((rkey == KeyEvent.VK_4 || rkey == KeyEvent.VK_NUMPAD1) && playerDect[3] != null)){
                    
                    // This part selects the card and removes it from player's cards
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

                    // Checks if there is a match
                    if(tempCard.getNumber().equalsIgnoreCase("Joker")){
                        
                        poolDect.push(tempCard);
                        while(!poolDect.isEmpty()){
                            addScore(playerScore);      
                            poolDect.pop();
                        }
                    }
                    else if(tempCard.getNumber().equalsIgnoreCase(((Card)poolDect.peek()).getNumber())){
                      
                        poolDect.push(tempCard);                    
                        if(poolDect.size() == 2){
                            playerScore += 10;                            
                        }

                        while(!poolDect.isEmpty()){
                            addScore(playerScore); 
                            poolDect.pop();
                        }
                    }
                    else{
                        poolDect.push(tempCard);
                    }

                    // Resets temp variable to use again for computer                  
                    displayPisti();
                    Thread.sleep(1000);

                    // Checks if computer has any Joker card
                    for (int i = 0; i < 4; i++) {
                        if(!poolDect.isEmpty() && computerDect[i] != null && computerDect[i].getNumber().equalsIgnoreCase("Joker")){                            
                            poolDect.push(computerDect[i]);
                            computerDect[i] = null;
                            while(!poolDect.isEmpty()){
                                addScore(computerScore); 
                                poolDect.pop();
                            }
                            isSelected1 = true;
                            break;
                        }
                    }

                    // If the card is not selected it checks if there is a matched number
                    if(!isSelected1){
                        for (int i = 0; i < 4; i++) {
                            if(computerDect[i] != null && ((Card)poolDect.peek()).getNumber().equalsIgnoreCase(computerDect[i].getNumber())){
                                poolDect.push(computerDect[i]);
                                computerDect[i] = null;
                                
                                if(poolDect.size() == 2){
                                    computerScore += 10;
                                }

                                while(!poolDect.isEmpty()){
                                    addScore(computerScore);
                                    poolDect.pop();
                                }

                                isSelected2 = true;
                                break;
                            }
                        }

                        // If the card is still not slected it selects a random card to play
                        if(!isSelected2){
                            for (int i = 0; i < 4; i++) {
                                if(computerDect[i] != null){
                                    poolDect.push(computerDect[i]);
                                    computerDect[i] = null;
                                    break;
                                }
                            }
                        }
                    }

                    displayPisti();
                    Thread.sleep(1000);

                    // If the turn reaches 25 it means game has been ended
                    turn++;
                    if(turn == 25){            
                        endGame();
                        break;
                    }

                    if(turn != 1 && (turn - 1) % 4 == 0){
                        for (int i = 0; i < 4; i++) {
                            playerDect[i] = (Card)pistiDect.pop();
                            computerDect[i] = (Card)pistiDect.pop();
                        }
                    }

                    // Resetting necessary variables
                    isSelected1 = isSelected2 = false;
                    tempCard = null; 

                    displayPisti();             
                }
                keypr = 0;
            }
         
            // Thread sleep to make code more efficient
            Thread.sleep(100);      
        }
    }

    // This functions calculates the score that is going to be added
    private void addScore(int score){
        if(((Card)poolDect.peek()).getNumber().equalsIgnoreCase("Joker"))
            score++;
        else if(((Card)poolDect.peek()).getNumber().equalsIgnoreCase("1"))
            score++;
        else if(((Card)poolDect.peek()).getNumber().equalsIgnoreCase("2") 
                && ((Card)poolDect.peek()).getType().equalsIgnoreCase("C"))
                score += 2;
        else if(((Card)poolDect.peek()).getNumber().equalsIgnoreCase("10") 
        && ((Card)poolDect.peek()).getType().equalsIgnoreCase("C"))
            score += 3;
    }

    // Key listener for game
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

    // This one prints the game area
    private void displayPisti(){
        Columns.consoleClear();

		cn.getTextWindow().setCursorPosition(28, 9);
		System.out.print("+---------+");
		cn.getTextWindow().setCursorPosition(28, 10);
		System.out.print(String.format("|%-6s  ", ((Card)poolDect.peek()).getNumber()) +  ((Card)poolDect.peek()).getType() + "| " + turn);
		cn.getTextWindow().setCursorPosition(28, 11);
		System.out.print("+---------+");

		cn.getTextWindow().setCursorPosition(5, 20);
		for (int i = 0; i < 4; i++)
            if(playerDect[i] != null)
			    System.out.printf("+----%s----+\t", i + 1);

		cn.getTextWindow().setCursorPosition(5, 21);
		for (int i = 0; i < 4; i++)
			if(playerDect[i] != null)
				System.out.print(String.format("|%-6s  ", playerDect[i].getNumber())  +  playerDect[i].getType() + "|\t");

	}

    // This one fills the boxeses
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

        // If the first element of dect is joker the cards are being distributed
        if(((Card)poolDect.peek()).getNumber().equalsIgnoreCase("Joker"))
            fillPistiBoxs();
    }
    
    // If the game is ended this function is being called and it prints the winner
    private void endGame() throws InterruptedException{
        Columns.consoleClear();
        cn.getTextWindow().setCursorPosition(20, 10);

        if(playerScore > computerScore){
            System.out.print("Player wins with score: " + (playerScore - computerScore));
        }
        else if(playerScore < computerScore){
            System.out.print("Computer wins with score: " + (computerScore - playerScore));
        }
        else{
            System.out.println("        No one wins!");
        }

        cn.getTextWindow().setCursorPosition(0, 25);
        System.out.print("Please press ESC to return menu");

        keypr = 0;
        while(true){
            if(keypr == 1){
                keypr = 0;
                if(rkey == KeyEvent.VK_ESCAPE){
                    break;
                }
            }
            Thread.sleep(500);
        }
    }
}