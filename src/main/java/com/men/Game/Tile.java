package com.men.Game;

public class Tile {
    int number;

    public Tile() {
        this.number = 0;
    }

    public Tile(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%5d", number);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tile))
            return false;

        return ((Tile) object).getNumber() == this.getNumber();
    }

    // public String getColor() {
    // switch(number) {

    // }
    // }
}
