package com.men;

import com.men.Game.Board;
import com.men.Gfx.LibC;

import com.sun.jna.Native;

public class App {
    private static LibC.termios oldTerm = new LibC.termios();
    private static LibC.termios newTerm = new LibC.termios();
    private static LibC.winsize winsize = new LibC.winsize();

    public static void main(String[] args) throws InterruptedException {
        // clear and reset
        System.out.print("\033[H\033[2J");

        // create board
        int size = 4;
        Board board = new Board(size);
        System.out.printf("Created a new %dx%d board:\n", size, size);

        // libc
        final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);

        libc.tcgetattr(LibC.STDIN_FILENO, oldTerm);
        libc.tcgetattr(LibC.STDIN_FILENO, newTerm);

        // diable canonical mode to get input
        newTerm.c_lflag &= ~(LibC.ECHO | LibC.ICANON);

        libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, newTerm);

        libc.ioctl(LibC.STDIN_FILENO, LibC.TIOCGWINSZ, winsize);
        board.printBoard(winsize);

        // reset terminal on exit
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

        // get winsize
        LibC.winsize prevWinsize = winsize;
        for (;;) {
            libc.ioctl(LibC.STDIN_FILENO, LibC.TIOCGWINSZ, winsize);

            if (winsize.ws_col != prevWinsize.ws_col || winsize.ws_row != prevWinsize.ws_row) {
                System.out.print("\033[H\033[2J");
                board.printBoard(winsize);

                prevWinsize = winsize;

                continue;
            }

            prevWinsize = winsize;

            char keyPressed = ' ';

            // get input
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
            // switch on keypress wasd
            if (new String("wasd").contains(String.valueOf(keyPressed))) {
                int toMove = 0;
                if (keyPressed == 'w') {
                    //System.out.println("UP!");
                    toMove = 0;
                }
                if (keyPressed == 'a') {
                    //System.out.println("LEFT!");
                    toMove = 1;
                }
                if (keyPressed == 's') {
                    //System.out.println("DOWN!");
                    toMove = 3;
                }
                if (keyPressed == 'd') {
                    //System.out.println("RIGHT!");
                    toMove = 2;
                }

                // check game end condition and exit
                boolean gameOver = board.moveTiles(toMove);
                System.out.print("\033[H\033[2J");
                board.printBoard(winsize);
                if (gameOver) {
                    System.out.println("you lose, your score sucks too ngl");
                    break;
                }
            }
        }
    }
}
