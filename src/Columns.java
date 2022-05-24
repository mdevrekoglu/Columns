import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class Columns {

    public static enigma.console.Console cn = Enigma.getConsole("Columns", 70, 30, 20, 2);

    // Klis
    private TextMouseListener tmlis;
    private KeyListener klis;

    // Variables for game play
    private int mousepr, mouserl;
    private int mousex, mousey;
    private int keyX = 4;
    private int keyY = 3;
    private int columnsX = 1;
    private int columnsY = 0;
    private int prevColumnsX = -1;
    private int prevColumnsY = -1;
    private int keypr;
    private int rkey;
    private int finishedSets = 0;
    public static int transfer = 0;
	private static Boolean firstPress = false;
	private static Boolean secondPress = false;
	private Boolean isZPressed = false;

    // Box and High Score
    private static SingleLinkedList box;
    private static DoubleLinkedList highScoreList;
	public static Player player;
	
    // Additional variable
	private static Stack pistiBox;
	private static Stack pool;
	
    // Columns
    private static MultiLinkedList columns = new MultiLinkedList();

	Columns() throws InterruptedException {		
		consoleClear();// An function to clear console
		// Key listener for mouse and keyboard
		listener();	
		
		// Main game loop
		while(true) {
			consoleClear();
			printMenu();
			
            // If key pressed
            if(keypr == 1) {

                if (rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) {
                	
                	rkey = keypr = 0;
                	consoleClear();
                	System.out.println("\n\n\n\n");
                	System.out.println("                        Please select a game mode");
                	System.out.println("\n\n");
                	System.out.println("                               1-Classic");
                	System.out.println("                               2-Pisti");
                	do {
                		if(keypr == 1) {
                			keypr = 0;
                		}
                		Thread.sleep(50);         		
                		
                	}while(rkey != KeyEvent.VK_1 && rkey != KeyEvent.VK_NUMPAD1
                			&& rkey != KeyEvent.VK_2 && rkey != KeyEvent.VK_NUMPAD2);
                	
                	
                	if(rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) {
                		
                		generateBox();
            			createInitialColumns();
                        keypr = 0;
    					consoleClear();
    					
    					// Player operations
    					Scanner scan = new Scanner(System.in);
    					String name;
    		    		do {
    		    			consoleClear();
    		    			System.out.print("Please enter your name and surname: ");
    		    			name = scan.nextLine();
        					
    		    		}while(name.trim().split(" ").length != 2);   					
    					
    					player = new Player(name, 0);
    					scan.close();
    					
    					
                        printGameArea();
                        TextAttributes coloredNumber = new TextAttributes(Color.GRAY, Color.CYAN);
                		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
                		cn.getTextWindow().output(">", coloredNumber);
                		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
                		cn.getTextWindow().output("<", coloredNumber);

                    	keypr = rkey = 0;
                        
                        // Main game loop
                        while (true) {
                        	keyListeners();
                            mouseListeners();
                            Thread.sleep(50);
                            if(rkey == KeyEvent.VK_ESCAPE || player.getScore() == 5000) {
                            	generateHighScoreTable();
                            	keypr = rkey = 0;
                            	while(rkey != KeyEvent.VK_ESCAPE ) {
                            		Thread.sleep(200);
                            	}
                            	keypr = rkey = 0;
                            	break;
                            }
                        }           		
                	}
                	else {
						int score = 0;
                		consoleClear();
						fillPistiBox();
						displayPisti(score);


						while(true){

							break;




						}
                		
                		
                		
                		System.out.println("Welcome to pisti");
                		Thread.sleep(5000);
                		
                		
                	}            	
        			
                }

                else if(rkey == KeyEvent.VK_2 || rkey == KeyEvent.VK_NUMPAD2){
                    consoleClear();
                    cn.getTextWindow().setCursorPosition(28, 0);
                    System.out.println("HOW TO PLAY?");
					System.out.println("Press 'ESC' to return the menu");
					keypr = 0;
					while(true) {
						if(keypr == 1){
							if (rkey == KeyEvent.VK_ESCAPE) {
								break;
							}
							keypr = 0;
						}
						Thread.sleep(50);
					}
                }

                else if(rkey == KeyEvent.VK_3 || rkey == KeyEvent.VK_NUMPAD3){
                    consoleClear();
                    exitMessage();
                    Thread.sleep(3000);
                    System.exit(0);
                }
            }
            keypr = 0;
            Thread.sleep(50);
        }
	}

	
	private void createInitialColumns() { // adds 5 random numbers to the each column.
		columns.addColumn(1); columns.addColumn(2); columns.addColumn(3);
		columns.addColumn(4); columns.addColumn(5);

		for(int i = 0; i < 6; i++) {
			columns.addNumber(1, (Integer) box.pop());
			columns.addNumber(2, (Integer) box.pop());
			columns.addNumber(3, (Integer) box.pop());
			columns.addNumber(4, (Integer) box.pop());
			columns.addNumber(5, (Integer) box.pop());
		}
	}

	private void listener() {
		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
				if (mouserl == 0) {
					mouserl = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);

		
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
		
	public static void printMenu() {
        cn.getTextWindow().setCursorPosition(15, 3);
        System.out.println("---------------------------------");
        cn.getTextWindow().setCursorPosition(28, 6);
        System.out.println(" MENU ");
        cn.getTextWindow().setCursorPosition(24, 8);
        System.out.println("1. Start");
        cn.getTextWindow().setCursorPosition(24, 9);
        System.out.println("2. How to Play?");
        cn.getTextWindow().setCursorPosition(24, 10);
        System.out.println("3. Exit");
        cn.getTextWindow().setCursorPosition(15, 13);
        System.out.println("---------------------------------");
    }

    public static void exitMessage(){
        cn.getTextWindow().setCursorPosition(15,6);
        System.out.println("    _/_/_/    _/      _/  _/_/_/_/  ");
        cn.getTextWindow().setCursorPosition(15,7);
        System.out.println("   _/    _/    _/  _/    _/         ");
        cn.getTextWindow().setCursorPosition(15,8);
        System.out.println("  _/_/_/        _/      _/_/_/      ");
        cn.getTextWindow().setCursorPosition(15,9);
        System.out.println(" _/    _/      _/      _/           ");
        cn.getTextWindow().setCursorPosition(15,10);
        System.out.println("_/_/_/        _/      _/_/_/_/      ");
    }
	
	// Clear console
	public static void consoleClear() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < cn.getTextWindow().getRows(); i++) {
			for (int j = 0; j < cn.getTextWindow().getColumns() - 1; j++)
				System.out.print(" ");
			if (i != cn.getTextWindow().getRows() - 1)
				System.out.println();
		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	public static void printGameArea() {	
        consoleClear();
		
		// This function creates playing area by using for loops
		for(int i = 1; i < 6; i++) {
			cn.getTextWindow().setCursorPosition(8 * i - 5, 0); 
			System.out.print("+--+");
			cn.getTextWindow().setCursorPosition(8 * i - 5, 1);
			System.out.print("|C" + i + "|");
			cn.getTextWindow().setCursorPosition(8 * i - 5, 2);
			System.out.print("+--+");
		}
		
		cn.getTextWindow().setCursorPosition(46, 1);
		System.out.print("Transfer: " + transfer);
		
		cn.getTextWindow().setCursorPosition(46, 2);
		System.out.print("Score   : " + player.getScore());
		
		cn.getTextWindow().setCursorPosition(48, 8);
		System.out.print(" BOX ");
		
		cn.getTextWindow().setCursorPosition(48, 9);
		System.out.print("+---+");
		
		cn.getTextWindow().setCursorPosition(48, 10);
		if (!box.isEmpty() && firstPress && (int) box.peek() != 10)
			System.out.print("| " + box.peek() + " |");
		else if (!box.isEmpty() && (int) box.peek() == 10 && firstPress)
			System.out.print("|" + box.peek() + " |");
		else
			System.out.print("| " + " " + " |");
		
		cn.getTextWindow().setCursorPosition(48, 11);
		System.out.print("+---+");
		
		// Printing initial columns on the screen
		columns.printColumns(cn);
		
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	private int createRandomNumber(int minValue, int maxValue) {
		Random random = new Random();
		int number = random.nextInt((maxValue - minValue) +  1) + minValue;
		return number;
	}
	
	private void generateBox() { // Fills the box with 50 random numbers from 1-10
		box = new SingleLinkedList();
		while(box.size() < 50) {
			int randomNumber = createRandomNumber(1,10); // Generates random numbers between 1 and 10 [1,10]
			if(!box.isNumberCountFull(randomNumber)) { // if the same number is added to the box 5 times, it will not be added again
				box.add(randomNumber);
			}
		}
	}
	
    private void generateHighScoreTable() { // Creates a highscore table and writes it ti the highscore.txt
        consoleClear();
        finishedSets = (int)player.getScore() / 1000;
 		player.setScore(100*finishedSets + (player.getScore()/transfer));
    
        try {    	
        	FileReader file = new FileReader("highscore.txt"); // Read the highscore.txt
            Scanner sc = new Scanner(file); // Scanner for the highscore.txt
			highScoreList = new DoubleLinkedList();
        	highScoreList.add(player); // add the player to the highscore
        	
        	
            while(sc.hasNext()){ // Read the highscore.txt
                highScoreList.add(new Player(sc.next() + " " + sc.next(),Double.parseDouble(sc.next()))); // add the players to the highscore
            }
            sc.close(); // Close the scanner
            
            highScoreList.display(); // Display the highscore  
            PrintWriter pw = new PrintWriter("highscore.txt"); // open a print writer for the highscore
            
            for(int i = 1; i < highScoreList.size() + 1; i++) {
            	Player tempPlayer = (Player)highScoreList.getElement(i);
            	pw.print(tempPlayer.getName() + " " + tempPlayer.getScore() + "\n"); // print the highscore line by line
            }
            
            pw.close(); // close the print writer
        }catch(Exception e) { // catch exception
        	System.out.println("There is not file such as highscore.txt");
        }       
    }

    private void keyListeners() {
    	if(rkey == KeyEvent.VK_B) { // If the B key is pressed, the element at the top of the box appears on the screen
        	
			if (firstPress) {
				secondPress = true;
				firstPress = false;
			}
			if (!secondPress) {
				firstPress = true;
			}
			printGameArea();
    		rkey = 0;
			keypr = 0;
        }
    	if(rkey == KeyEvent.VK_UP) {
        	if(keyY > 3 ) {
        		keyY = keyY - 1;
        		columnsY = columnsY - 1;
        		TextAttributes coloredNumber = new TextAttributes(Color.GRAY, Color.CYAN);
        		printGameArea();
        		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
        		cn.getTextWindow().output(">", coloredNumber);
        		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
        		cn.getTextWindow().output("<", coloredNumber);
        	}
        	rkey = 0;
    		keypr = 0;
        }
        if(rkey == KeyEvent.VK_DOWN ) {
        	if(keyY > 2 && keyY <= columns.sizeOfColumn(columnsX) + 1) {
        		keyY = keyY + 1;
        		columnsY = columnsY + 1;
        		TextAttributes coloredNumber = new TextAttributes(Color.GRAY, Color.CYAN);
        		printGameArea();
        		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
        		cn.getTextWindow().output(">", coloredNumber);
        		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
        		cn.getTextWindow().output("<", coloredNumber);
        	}
        	rkey = 0;
    		keypr = 0;
        }
        if(rkey == KeyEvent.VK_RIGHT) {
        	if(keyX < 35 && columnsX < 5) {
        		keyX= keyX + 8;
        		columnsX++;
        		TextAttributes coloredNumber = new TextAttributes(Color.GRAY, Color.CYAN);
        		printGameArea();
        		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
        		cn.getTextWindow().output(">", coloredNumber);
        		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
        		cn.getTextWindow().output("<", coloredNumber);
        	}
        	rkey = 0;
    		keypr = 0;
        }
        if(rkey == KeyEvent.VK_LEFT ) {
        	if(keyX > 3 && columnsX > 1) {
        		keyX = keyX - 8;
        		columnsX--;
        		TextAttributes coloredNumber = new TextAttributes(Color.GRAY, Color.CYAN);
        		printGameArea();
        		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
        		cn.getTextWindow().output(">", coloredNumber);
        		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
        		cn.getTextWindow().output("<", coloredNumber);
        	}
        	rkey = 0;
    		keypr = 0;
        }
        if(rkey == KeyEvent.VK_Z) {
        	columns.printeColoredColumn(cn, columnsX, columnsY);
        	prevColumnsX = columnsX;
        	prevColumnsY = columnsY;
			isZPressed = true;
			secondPress = false;
        	rkey = 0;
    		keypr = 0;
        }
        if(rkey == KeyEvent.VK_X) {
        	if(prevColumnsX != -1 && prevColumnsY != -1 && isZPressed) {
        		columns.transfer(prevColumnsX,columnsX, prevColumnsY);   
        		columns.checkMatching(prevColumnsX, columnsX);
				isZPressed = false;
        		/*
        		keyX = 4;
        		keyY = 3;
        		columnsX = 1;
        		columnsY = 0;
        		prevColumnsX = -1;
        		prevColumnsY= -1;
        		printGameArea();
        		cn.getTextWindow().setCursorPosition(keyX -1, keyY);
        		System.out.println(">");
        		cn.getTextWindow().setCursorPosition(keyX + 2, keyY);
        		System.out.println("<");
        		*/
        	}
        	if (secondPress && !box.isEmpty() && Math.abs((int) box.peek() - columns.getLastNumber(columnsX)) <= 1) {
				columns.addNumber(columnsX, (Integer) box.pop());
				secondPress = false;
				firstPress = false;
				columns.checkMatching(columnsX);
				printGameArea();
			}
    		rkey = 0;
    		keypr = 0;
        }
        else {
        	rkey = 0;
            keypr = 0;
        }
    }

    private void mouseListeners() throws InterruptedException {
    	if (mousepr == 1) {
            keypr =0;
            mousepr = 0;
            Boolean isNumberSelected = false;
			Boolean isNumberOfBoxWanted = false;
            int firstX = mousex;
            int firstY = mousey;
            // cn.getTextWindow().setCursorPosition(8 * i - 5, 2); 
            // 3 - 11 - 19 - 27 - 35
            if(firstY - 3 >= 0) {
            	if((firstX + 4) % 8 == 0 && (firstX + 4) / 8 <=5 && columns.sizeOfColumn((firstX + 4) / 8) > firstY - 3) {                                
                	isNumberSelected = true;
                	columns.printeColoredColumn(cn, (firstX + 4) / 8, firstY - 3);
                }
            	if((firstX + 4) % 8 == 0 && (firstX + 4) / 8 <=5 && firstY == columns.sizeOfColumn((firstX + 4) / 8) + 3 && Math.abs((int) box.peek() - columns.getLastNumber((firstX + 4) / 8)) <= 1) {
					isNumberOfBoxWanted = true;
				}
            }
            while(mouserl == 0) {             	
            	Thread.sleep(50);
            	/*
            	if(mousepr == 1)
            		break;
            	*/
            }
            
            if(isNumberSelected) {
            	if((mousex + 4) % 8 == 0) {
            		//int firstColumn, int secondColumn, int element

            		columns.transfer((firstX + 4) / 8, (mousex + 4) / 8, firstY - 3);   
            		columns.checkMatching((firstX + 4) / 8, (mousex + 4) / 8);
            	}
            	else {
            		printGameArea();
            	}
            }
			if(isNumberOfBoxWanted) {
				if(!box.isEmpty()) {
					columns.addNumber((firstX + 4) / 8, (Integer) box.pop());
					columns.checkMatching((firstX + 4) / 8);
					firstPress = false;
					printGameArea();
				}
			}
            mouserl = 0;
        }
    }

    private void fillPistiBox() {
		pistiBox = new Stack(52);
		pool = new Stack(52);

		// Numberts 1 to 10
    	for(int i = 1; i <= 10; i++) {
    		pistiBox.push(new Card(Integer.toString(i), "Spade", new TextAttributes(Color.WHITE, Color.BLACK)));//black
			pistiBox.push(new Card(Integer.toString(i), "Club", new TextAttributes(Color.WHITE, Color.BLACK)));//black
			pistiBox.push(new Card(Integer.toString(i), "Diamond", new TextAttributes(Color.RED, Color.BLACK)));//red
			pistiBox.push(new Card(Integer.toString(i), "Heard", new TextAttributes(Color.RED, Color.BLACK)));//red
    	}

		// Joker
    	pistiBox.push(new Card("Joker", "Spade", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("Joker", "Club", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("Joker", "Diamond", new TextAttributes(Color.RED, Color.BLACK)));
		pistiBox.push(new Card("Joker", "Heard", new TextAttributes(Color.RED, Color.BLACK)));

		// Queen
		pistiBox.push(new Card("Queen", "Spade", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("Queen", "Club", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("Queen", "Diamond", new TextAttributes(Color.RED, Color.BLACK)));
		pistiBox.push(new Card("Queen", "Heard", new TextAttributes(Color.RED, Color.BLACK)));

		// King
		pistiBox.push(new Card("King", "Spade", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("King", "Club", new TextAttributes(Color.WHITE, Color.BLACK)));
		pistiBox.push(new Card("King", "Diamond", new TextAttributes(Color.RED, Color.BLACK)));
		pistiBox.push(new Card("King", "Heard", new TextAttributes(Color.RED, Color.BLACK)));

		pistiBox.shuffle();
    }

	private void displayPisti(int score){

		cn.getTextWindow().setCursorPosition(28, 9);
		System.out.println("+-----------+");
		cn.getTextWindow().setCursorPosition(28, 10);
		System.out.println(String.format("|%-11s|", pool.peek()));
		cn.getTextWindow().setCursorPosition(28, 11);
		System.out.println("+-----------+");



	}





}
