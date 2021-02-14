package pair;

public class Pair {
    private int x;
    private int y;

    public Pair(){   
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setFirst(int x) {
        this.x = x;
    }
    public void setSecond(int y) {
        this.y = y;
    }

    public void setFstSnd(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getFirst() {
        return x;
    }
    public int getSecond() {
        return y;
    }

}
