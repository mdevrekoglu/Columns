import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class Columns {

    public static enigma.console.Console cn = Enigma.getConsole("Columns", 70, 30, 20, 2);

    // 
    private TextMouseListener tmlis;
    private KeyListener klis;

    // Variables for game play
    private int mousepr;
    private int mousex, mousey;
    private int keypr;
    private int rkey;
    private int finishedSets = 0;
    private static int score = 0;
    private static int transfer = 1;

    // Box and High Score
    private static SingleLinkedList box = new SingleLinkedList();
    private static DoubleLinkedList highScoreList = new DoubleLinkedList();
	private static Player player;
    
    // Columns
    private static MultiLinkedList columns = new MultiLinkedList();

	Columns() throws InterruptedException {		
		consoleClear();// An function to clear console
		// Key listener for mouse and keyboard
		listener();
		generateBox();
		createInitialColumns();		



		
		// Main game loop
		while(true) {
			consoleClear();
			printMenu();
			
            // If key pressed
            if(keypr == 1) {

                if (rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) {
					consoleClear();
					askForName();
                    consoleClear();
                    printGameArea();
                    keypr = 0;

                    while (true) {
                    	if(rkey == KeyEvent.VK_B) { // If the B key is pressed, the element at the top of the box appears on the screen
                        	cn.getTextWindow().setCursorPosition(48, 10);
                    		System.out.print("| " + box.peek() + " |");
                    		rkey = 0;
							keypr = 0;
                        }
                    	
                        if (mousepr == 1) {
                            cn.getTextWindow().setCursorPosition(mousex, mousey);
                            box.pop();
                            printGameArea();
                            keypr =0;
                            mousepr = 0;
                        }
                        
                        if (keypr == 1) {
                            if(rkey == KeyEvent.VK_ESCAPE) {
                                generateHighScoreTable();
                            }
                            keypr = 0;
							Thread.sleep(3000);
							break;
                        }
                        
                        Thread.sleep(50);
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
	private static void consoleClear() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < cn.getTextWindow().getRows(); i++) {
			for (int j = 0; j < cn.getTextWindow().getColumns() - 1; j++)
				System.out.print(" ");
			if (i != cn.getTextWindow().getRows() - 1)
				System.out.println();
		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}


	private static void printGameArea() {
		
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
		while(box.size() < 50) {
			int randomNumber = createRandomNumber(1,10); // Generates random numbers between 1 and 10 [1,10]
			if(!box.isNumberCountFull(randomNumber)) { // if the same number is added to the box 5 times, it will not be added again
				box.add(randomNumber);
			}
		}
	}

	private void askForName(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter your name and surname: ");
		String name = scan.nextLine();
		System.out.print("Please enter your previous score: ");
		double score = scan.nextDouble();
		player = new Player(name, score);
		scan.close();

	}
	
    private void generateHighScoreTable() { // Creates a highscore table and writes it ti the highscore.txt
        consoleClear();
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
}
