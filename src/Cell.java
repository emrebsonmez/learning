/**
 * Created by emresonmez on 9/21/15.
 */
public class Cell {
    private int x;
    private int y;
    private int value;

    public Cell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
