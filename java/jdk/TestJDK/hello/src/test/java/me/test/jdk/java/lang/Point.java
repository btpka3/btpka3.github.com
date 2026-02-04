package me.test.jdk.java.lang;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/4
 */
public /*value*/ class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}