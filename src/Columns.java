import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class Columns {



    public static enigma.console.Console cn = Enigma.getConsole("Columns");

    private TextMouseListener tmlis;
    private KeyListener klis;

    private int mousepr;
    private int mousex, mousey;
    private int keypr;
    private int rkey;
    private int finishedSets = 0;
    private static int score = 0;
    private static int transfer = 1;

    private static Random rnd = new Random();
    private static SingleLinkedList box = new SingleLinkedList();
    private static DoubleLinkedList highScoreList = new DoubleLinkedList();
    private static SingleLinkedList column1 = new SingleLinkedList();
	private static SingleLinkedList column2 = new SingleLinkedList();
	private static SingleLinkedList column3 = new SingleLinkedList();
	private static SingleLinkedList column4 = new SingleLinkedList();
	private static SingleLinkedList column5 = new SingleLinkedList();

	Columns() throws InterruptedException, FileNotFoundException {
		consoleClear();
		mouseListener();
		
		generateBox();
		createInitialColumns();
		printGameArea();
		
		
		while(true) {
			consoleClear();
            printMenu();

            if(keypr == 1) {

                if (rkey == KeyEvent.VK_1 || rkey == KeyEvent.VK_NUMPAD1) {
                    consoleClear();
                    printGameArea();
                    keypr = 0;

                    while (true) {
                    	if(rkey == KeyEvent.VK_B) { // if the B key is pressed, the element at the top of the box appears on the screen
                        	cn.getTextWindow().setCursorPosition(48, 10);
                    		System.out.print("| " + box.peek() + " |");
                    		rkey = 0;
                        }
                    	
                        if (mousepr == 1) {
                            cn.getTextWindow().setCursorPosition(mousex, mousey);
                            box.pop();
                            printGameArea();
                            keypr =0;

                            mousepr = 0;
                        }
                        
                        if (keypr == 1) {
                            if(rkey == KeyEvent.VK_E) {
                                generateHighScoreTable();
                            }
                            keypr = 0;
                        }
                        
                        Thread.sleep(500);
                    }
                }

                else if(rkey == KeyEvent.VK_2 || rkey == KeyEvent.VK_NUMPAD2){
                    consoleClear();
                    cn.getTextWindow().setCursorPosition(28, 0);
                    System.out.println("HOW TO PLAY?");
                }

                else if(rkey == KeyEvent.VK_3 || rkey == KeyEvent.VK_NUMPAD3){
                    consoleClear();
                    exitMessage();
                    Thread.sleep(3000);
                    break;
                }
            }
            keypr = 0;
            Thread.sleep(50);
        }
	}
	
	private static void printColumn(SingleLinkedList column, int x) { // prints each number in the column to the screen, respectively.
		for(int i = 0; i < column.size(); i ++) {
			cn.getTextWindow().setCursorPosition(x, 3 + i);
			int number = (int)column.getElement(i + 1);
			System.out.print(number);
		}
	}
	
	private void createInitialColumns() { // adds 5 random numbers to the each column.
		for(int i = 0; i < 5; i++) {
			column1.add(box.pop());
			column2.add(box.pop());
			column3.add(box.pop());
			column4.add(box.pop());
			column5.add(box.pop());
		}
		
	}

	private void mouseListener() {
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

	private static void printGameArea() { // 5 columns
		
		for(int i = 1; i < 6; i++) {
			cn.getTextWindow().setCursorPosition(8 * i - 5, 0); 
			System.out.print("+--+");
			cn.getTextWindow().setCursorPosition(8 * i - 5, 1);
			System.out.print("|C" + i + "|");
			cn.getTextWindow().setCursorPosition(8 * i - 5, 2);
			System.out.print("|--|");
		}
		
		cn.getTextWindow().setCursorPosition(46, 1);
		System.out.print("Transfer: " + transfer);
		
		cn.getTextWindow().setCursorPosition(46, 2);
		System.out.print("Score   : " + score);
		
		cn.getTextWindow().setCursorPosition(48, 8);
		System.out.print(" BOX ");
		
		cn.getTextWindow().setCursorPosition(48, 9);
		System.out.print("+---+");
		
		cn.getTextWindow().setCursorPosition(48, 10);
		System.out.print("| " + " " + " |");
		
		cn.getTextWindow().setCursorPosition(48, 11);
		System.out.print("+---+");
		
		// Printing initial columns on the screen
		printColumn(column1, 4);
		printColumn(column2, 12);
		printColumn(column3, 20);
		printColumn(column4, 28);
		printColumn(column5, 36);
		
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
	
    private void generateHighScoreTable() throws FileNotFoundException {
        consoleClear();
        FileReader file = new FileReader("highscore.txt");
        Scanner sc = new Scanner(file);
        highScoreList.add(new Player("Player PlayerSurname", score));
        while(sc.hasNext()){
            highScoreList.add(new Player(sc.next() + " " + sc.next(),Double.parseDouble(sc.next())));
        }
        sc.close();
        
        score = 100*finishedSets + (score/transfer);
        
        highScoreList.display();
        
        PrintWriter pw = new PrintWriter("highscore.txt");
        
        for(int i = 1; i < highScoreList.size() + 1; i++) {
        	Player tempPlayer = (Player)highScoreList.getElement(i);
        	pw.print(tempPlayer.getName() + " " + tempPlayer.getScore() + "\n");
        }
        pw.close();
    }
}
