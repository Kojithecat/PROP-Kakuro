package capaDominio.pair;

public class Pair {
    private int x;
    private int y;

    public Pair(){   
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    /** 
     * @param x
     */
    public void setFirst(int x) {
        this.x = x;
    }
    
    /** 
     * @param y
     */
    public void setSecond(int y) {
        this.y = y;
    }

    
    /** 
     * @param x
     * @param y
     */
    public void setFstSnd(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    /** 
     * @return int
     */
    public int getFirst() {
        return x;
    }
    
    /** 
     * @return int
     */
    public int getSecond() {
        return y;
    }

}
