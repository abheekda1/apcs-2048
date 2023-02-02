package com.men;

import com.men.Game.Board;
import com.men.Game.Tile;
import com.men.Gfx.LibC;

import com.sun.jna.Native;

public class App {
    private static LibC.termios oldTerm = new LibC.termios();
    private static LibC.termios newTerm = new LibC.termios();

    public static void main(String[] args) throws InterruptedException {
        System.out.print("\033[H\033[2J");

        boolean gameWon = false;
        boolean gameFinished = false;

        System.out.println("Creating a new board:");
        Board board = new Board(4);
        Tile tile = new Tile();
        board.printBoard();

        final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);

        libc.tcgetattr(LibC.STDIN_FILENO, oldTerm);
        libc.tcgetattr(LibC.STDIN_FILENO, newTerm);

        newTerm.c_lflag &= ~(LibC.ECHO | LibC.ICANON);

        libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, newTerm);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, oldTerm);
            }
        });

        /*
         * System.out.print("\033[H\033[2J");
         * for (int i = 0; i < 16; i++) {
         * for (int j = 0; j < 16; j++) {
         * int code = i * 16 + j;
         * System.out.printf("%5s", "\033[48;5;" + code + "m " + code);
         * }
         * }
         */
        // System.exit(0);

        // board.moveTiles(0);

        for (;;) {
            char keyPressed = ' ';

            char[] c = new char[1];
            libc.read(LibC.STDIN_FILENO, c, 1);

            // https://viewsourcecode.org/snaptoken/kilo/03.rawInputAndOutput.html
            /*
             * if (c[0] == 27) {
             * System.out.println("ESC PRESSED!");
             * char[] seq = new char[3];
             * 
             * libc.read(LibC.STDIN_FILENO, seq, 3);
             * 
             * System.out.printf("\n\n---\n\n%d %d %d\n\n---\n\n", (int)seq[0], (int)seq[1],
             * (int)seq[2]);
             * 
             * /*if (seq[0] == '[') {
             * switch (seq[1]) {
             * case 'A':
             * keyPressed = 'w';
             * case 'B':
             * keyPressed = 's';
             * case 'C':
             * keyPressed = 'd';
             * case 'D':
             * keyPressed = 'a';
             * }
             * }
             */

            // switch (seq[1]) {}
            // } else {
            keyPressed = c[0];
            // }
            if (new String("wasd").contains(String.valueOf(keyPressed))) {
                if (keyPressed == 'w') {
                    System.out.println("UP!");
                    board.moveTiles(0);
                }
                if (keyPressed == 'a') {
                    System.out.println("LEFT!");
                    board.moveTiles(1);
                }
                if (keyPressed == 's') {
                    System.out.println("DOWN!");
                    board.moveTiles(3);
                }
                if (keyPressed == 'd') {
                    System.out.println("RIGHT!");
                    board.moveTiles(2);
                }

                System.out.print("\033[H\033[2J");
                board.printBoard();
            }
        }
    }
}
