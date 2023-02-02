package com.men.Gfx;

public class Color {
    public static String getColorEscCode(int number) {
        if (number == 0)
            return getClearEscCode();
        String fmt = "\033[48;5;%dm";
        for (int i = 1; i < 16; i++) {
            if (number == (1 << i)) {
                /*
                 * if (i <= 8)
                 * return String.format(fmt, 39 + i);
                 * else
                 * return String.format(fmt, 99 + i);
                 */

                return String.format(fmt, i);
            }
        }

        return String.format(fmt, 107);
    }

    public static String getClearEscCode() {
        return "\033[0m";
    }
}
