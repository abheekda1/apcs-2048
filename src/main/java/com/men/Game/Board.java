package com.men.Game;

import java.util.ArrayList;

import com.men.Gfx.Color;

public class Board {
    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Tile[][] tiles;
    private int size;
    private int score = 0;

    public Board(int size) {
        // the 2d array wil by default fill Tile objects with null
        this.size = size;
        tiles = new Tile[size][size];

        /*
         * for each tile in the board:
         * define the tile as a new tile with empty constructor
         */

        /*
         * for (Tile[] tileRow : tiles) {
         * for (Tile tileColumn : tileRow) {
         * tileColumn = new Tile();
         * }
         * }
         */

        Point start = new Point((int) (Math.random() * size), (int) (Math.random() * size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // tiles[i][j] = new Tile(Math.random() > 0.5 ? 2 : 0);
                // tiles[i][j] = new Tile(Math.min(2 << (int)(Math.random() * 16), 8192));
                if (j == start.x && i == start.y)
                    tiles[i][j] = new Tile(2);
                else
                    tiles[i][j] = new Tile();
            }
        }

    }

    public boolean moveTiles(int direction /* 0 is up, 1 is right, 2 is left, 3 is down */)
            throws InterruptedException {
        // use tile.equals(otherTile) to check if two tiles are equal
        // if down arrow then start in bottom row and move everything as down as
        // possible, and then go up one row at a time to see what tiles to move
        // if up arrow, start in topmost row and check what tiles to move, moving down
        // one row to check every time
        // System.out.println(direction);
        // Thread.sleep(500);
        boolean hadMovement = false;
        if (direction == 0) { // up
            for (int i = 0; i < size; i++) {
                // Tile[] row = tiles[i];
                for (int j = 0; j < size; j++) {
                    // int h = i+1;
                    // System.out.println(tiles[i][j]);
                    // match h with i(row) and loop it down
                    Tile currentTile = tiles[i][j];
                    if (currentTile.getNumber() != 0) {
                        int h = i - 1;
                        while (h >= 0 && tiles[h][j].getNumber() == 0) {
                            h--;
                        } // at end h should be last index currentTile cannot go past

                        if (h < 0) {
                            tiles[h + 1][j].setNumber(tiles[i][j].getNumber());
                            if (h + 1 != i) {
                                hadMovement = true;
                                tiles[i][j].setNumber(0);
                            }
                        } else if (tiles[h][j].equals(tiles[i][j])) {
                            hadMovement = true;
                            tiles[h][j].setNumber(tiles[h][j].getNumber() << 1);
                            tiles[i][j].setNumber(0);
                            score += tiles[h][j].getNumber() << 1;
                        } else {
                            tiles[h + 1][j].setNumber(tiles[i][j].getNumber());
                            if (h + 1 != i) {
                                hadMovement = true;
                                tiles[i][j].setNumber(0);
                            }
                        }
                    }
                }
            }
        } else if (direction == 1) { // left
            for (int i = 0; i < size; i++) {
                // printBoard();
                // Tile[] row = tiles[i];
                for (int j = 0; j < size; j++) {
                    // int h = i+1;
                    // System.out.println(tiles[i][j]);
                    // match h with i(row) and loop it down
                    Tile currentTile = tiles[j][i];
                    if (currentTile.getNumber() != 0) {
                        int h = i - 1;
                        while (h >= 0 && tiles[j][h].getNumber() == 0) {
                            h--;
                        } // at end h should be last index currentTile cannot go past

                        if (h < 0) {
                            tiles[j][h + 1].setNumber(tiles[j][i].getNumber());
                            if (h + 1 != i) {
                                hadMovement = true;
                                tiles[j][i].setNumber(0);
                            }
                        } else if (tiles[j][h].equals(tiles[j][i])) {
                            hadMovement = true;
                            tiles[j][h].setNumber(tiles[j][h].getNumber() << 1);
                            tiles[j][i].setNumber(0);
                            score += tiles[j][h].getNumber() << 1;
                        } else {
                            tiles[j][h + 1].setNumber(tiles[j][i].getNumber());
                            if (h + 1 != i) {
                                hadMovement = true;
                                tiles[j][i].setNumber(0);
                            }
                        }
                    }
                }
            }
        } else if (direction == 2) { // right
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size; j++) {
                    // match h with i (col) and loop it down
                    Tile currentTile = tiles[j][i];
                    if (currentTile.getNumber() != 0) {
                        int h = i + 1;
                        while (h < size && tiles[j][h].getNumber() == 0) {
                            h++;
                        } // at end h should be last index currentTile cannot go past

                        if (h >= size) {
                            tiles[j][h - 1].setNumber(tiles[j][i].getNumber());
                            if (h - 1 != i) {
                                hadMovement = true;
                                tiles[j][i].setNumber(0);
                            }
                        } else if (tiles[j][h].equals(tiles[j][i])) {
                            hadMovement = true;
                            tiles[j][h].setNumber(tiles[j][h].getNumber() << 1);
                            tiles[j][i].setNumber(0);
                            score += tiles[j][h].getNumber() << 1;
                        } else {
                            tiles[j][h - 1].setNumber(tiles[j][i].getNumber());
                            if (h - 1 != i) {
                                hadMovement = true;
                                tiles[j][i].setNumber(0);
                            }
                        }
                    }
                }
            }
        } else if (direction == 3) { // down
            for (int i = size - 1; i >= 0; i--) {
                // Tile[] row = tiles[i];
                for (int j = 0; j < size; j++) {
                    // int h = i+1;
                    // System.out.println(tiles[i][j]);
                    // match h with i(row) and loop it down
                    Tile currentTile = tiles[i][j];
                    if (currentTile.getNumber() != 0) {
                        int h = i + 1;
                        while (h < size && tiles[h][j].getNumber() == 0) {
                            h++;
                        } // at end h should be last index currentTile cannot go past

                        if (h >= tiles.length) {
                            tiles[h - 1][j].setNumber(tiles[i][j].getNumber());
                            if (h - 1 != i) {
                                hadMovement = true;
                                tiles[i][j].setNumber(0);
                            }
                        } else if (tiles[h][j].equals(tiles[i][j])) {
                            hadMovement = true;
                            tiles[h][j].setNumber(tiles[h][j].getNumber() << 1);
                            tiles[i][j].setNumber(0);
                            score += tiles[j][h].getNumber() << 1;
                        } else {
                            tiles[h - 1][j].setNumber(tiles[i][j].getNumber());
                            if (h - 1 != i) {
                                hadMovement = true;
                                tiles[i][j].setNumber(0);
                            }
                        }
                    }
                }
            }
        }

        if (hadMovement) {
            int emptyTiles = addRandomTile();
            if (emptyTiles == 1) {
                // check end condition
                return boardFull();
            }
        }

        return false;
    }

    boolean boardFull() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                int minXBound = Math.max(col - 1, 0);
                int maxXBound = Math.min(col + 1, size - 1);
                int minYBound = Math.max(row - 1, 0);
                int maxYBound = Math.min(row + 1, size - 1);

                for (int i = minYBound; i <= maxYBound; i++) {
                    if (i == row)
                        continue;

                    if (tiles[row][col].equals(tiles[i][col]) || tiles[i][col].getNumber() == 0) {
                        return false;
                    }
                }

                for (int j = minXBound; j <= maxXBound; j++) {
                    if (j == col)
                        continue;

                    if (tiles[row][col].equals(tiles[row][j]) || tiles[row][j].getNumber() == 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private int addRandomTile() {
        ArrayList<Point> emptyTiles = new ArrayList<Point>();

        // loop through all elements
        // if the tile att the current location
        // is empty, then add that index (as a point)
        // to emptyTiles

        // ArrayList arr = {(0, 1), (0, 2)};
        // Point point = new Point(...);
        // arr.add(new Point(1, 2));
        // arr -> {(0, 1), (0, 2), (1, 2)}
        // arr.size() == 3
        // Math.random() returns 0 - 0.99999....
        // arr.size() * Math.random() == 0 - 2.99999999....
        // say arr.size() * Math.random() returns 1.2, casting to int removes decimal ->
        // 1
        // (int)(arr.size() * Math.random()) == 0 - 2
        // arr.get((int)(arr.size() * Math.random())) -> some random element at a random
        // index

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getNumber() == 0) {
                    emptyTiles.add(new Point(j, i));
                }
            }
        }

        if (emptyTiles.size() > 0) {
            // random empty point
            Point pointToReplace = emptyTiles.get((int) (Math.random() * emptyTiles.size()));

            if (Math.random() > 0.5) {
                tiles[pointToReplace.y][pointToReplace.x].setNumber(2);
            } else {
                tiles[pointToReplace.y][pointToReplace.x].setNumber(4);
            }
        }

        return emptyTiles.size();
    }

    public void newTwo() {
        int x = 0;
        int n = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getNumber() == 0) {
                    x += 1;
                }
            }
        }
        n = (int) (Math.random() * x);

    }

    public void printBoard() {
        /*
         * for (Tile[] tileRow : tiles) {
         * for (Tile tileColumn : tileRow) {
         * System.out.print(tileColumn.getNumber() + " ");
         * }
         * 
         * System.out.println();
         * }
         */
        printBoardNew();
    }

    public void printBoardNew() {
        System.out.printf("Score: %d\n", score);
        for (int i = 0; i < tiles.length; i++) {
            Tile[] tileRow = tiles[i];

            for (int tileColumn = 0; tileColumn < tileRow.length; tileColumn++) {
                System.out.print(Color.getColorEscCode(tileRow[tileColumn].getNumber()));
                System.out.print(String.format("┌%" + 6 + "s┐", " ").replace(" ", "─"));
                System.out.print(Color.getClearEscCode());
                if (tileColumn != tileRow.length - 1)
                    System.out.print(" │ ");
            }

            System.out.println();

            for (int tileColumn = 0; tileColumn < tileRow.length; tileColumn++) {
                System.out.print(Color.getColorEscCode(tileRow[tileColumn].getNumber()));
                String numString = Integer.toString(tileRow[tileColumn].getNumber());
                int left = (6 - numString.length()) / 2;
                int right = 6 - (left + numString.length());
                System.out.printf(
                        "│%" + left + "s" + (tileRow[tileColumn].getNumber() == 0 ? " " : numString) + "%" + right
                                + "s│",
                        " ",
                        " ");
                System.out.print(Color.getClearEscCode());
                if (tileColumn != tileRow.length - 1)
                    System.out.print(" │ ");
            }

            System.out.println();

            for (int tileColumn = 0; tileColumn < tileRow.length; tileColumn++) {
                System.out.print(Color.getColorEscCode(tileRow[tileColumn].getNumber()));
                System.out.print(String.format("└%" + 6 + "s┘", " ").replace(" ", "─"));
                System.out.print(Color.getClearEscCode());
                if (tileColumn != tileRow.length - 1)
                    System.out.print(" │ ");
            }

            System.out.println();

            if (i != tiles.length - 1) {
                String rowLine = String.format("%" + (tileRow.length * 8 + (tileRow.length - 1) * 3) + "s", " ")
                        .replace(" ",
                                "─");
                for (int idx = 10; idx <= rowLine.length(); idx += 11) {
                    rowLine = rowLine.substring(0, idx - 1) + "┼" + rowLine.substring(idx);
                }
                System.out.println(rowLine);
            }
        }

        System.out.println();
    }

    public int getScore() {
        return score;
    }
}
