import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Columns {
	
	public static enigma.console.Console cn = Enigma.getConsole("Columns", 65, 20, 40, 1);

	private TextMouseListener tmlis;
	private KeyListener klis;

	private int mousepr;
	private int mousex, mousey;
	private int keypr;
	private int rkey;
	private static int score = 0;
	private static int transfer = 0;
	
	private static Random rnd = new Random();
	
	private static SingleLinkedList box = new SingleLinkedList();

	Columns() throws InterruptedException {
		consoleClear();

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

		//-----------------------------------------------------
		
		
		printGameArea();
		
		
		while(true) {
			if(mousepr == 1) {
				cn.getTextWindow().setCursorPosition(mousex, mousey);
				box.pop();
				
				mousepr = 0;
			}
			
			
			
			Thread.sleep(50);
		}
		
		
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
		
		for(int i = 1; i < 5; i++) {
			cn.getTextWindow().setCursorPosition(10 * i - 5, 0);
			System.out.print("+--+");
			cn.getTextWindow().setCursorPosition(10 * i - 5, 1);
			System.out.print("|C" + i + "|");
			cn.getTextWindow().setCursorPosition(10 * i - 5, 2);
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
		System.out.print("| " + box.peek() + " |");
		
		cn.getTextWindow().setCursorPosition(48, 11);
		System.out.print("+---+");
		
	}

}
