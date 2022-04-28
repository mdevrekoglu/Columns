import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import jdk.swing.interop.SwingInterOpUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

public class Columns {

    public static enigma.console.Console cn = Enigma.getConsole("Columns", 65, 20, 20, 1);

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

    Scanner scan = new Scanner(System.in);

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
        while (true) {
            consoleClear();
            printMenu();

            if(keypr == 1) {

                if (rkey == KeyEvent.VK_1) {
                    consoleClear();
                    printGameArea();

                    while (true) {
                        if (mousepr == 1) {
                            cn.getTextWindow().setCursorPosition(mousex, mousey);
                            box.pop();

                            mousepr = 0;
                        }
                        Thread.sleep(50);
                    }
                }

                else if(rkey == KeyEvent.VK_2){
                    consoleClear();
                    cn.getTextWindow().setCursorPosition(28, 0);
                    System.out.println("HOW TO PLAY?");
                }

                else if(rkey == KeyEvent.VK_3){
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

    //VK
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

        for (int i = 1; i < 5; i++) {
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
