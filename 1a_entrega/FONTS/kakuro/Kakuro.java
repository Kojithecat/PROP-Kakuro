package kakuro;

import casilla.*;

import java.util.*;


public class Kakuro {

    private int ID;

    private Casilla[][] tablero;

    private ArrayList<CasillaNegra> casillasNegras = new ArrayList<>(); // Solo agreganos las casillas negras que tengan sumaVertical o Horizontal

    public Kakuro(int h, int v) {
        ID = -1;
        tablero = new Casilla[h][v];       
    }
    
    public void setCasillaKakuro(int i, int j, Casilla c) {
        tablero[i][j] = c;
        if(c instanceof CasillaNegra){
            casillasNegras.add((CasillaNegra) c);
        }
    }

    public void setCasillaNegra(ArrayList<CasillaNegra> listaCasilla) {
        casillasNegras = listaCasilla;
    }

    public void setId(int num) {
        ID = num;
    }

    public ArrayList<CasillaNegra> getCasillasNegras() { 
        return casillasNegras;
    }

    public Casilla getCasillaKakuro(int i, int j) {
        return tablero[i][j];
    }

    public int getAltura() {
        return tablero.length;
    }

    public int getAnchura() {
        return tablero[0].length;
    }

    public int getId() {
        return ID;
    }

    public boolean esNegra(int i, int j) {
        return tablero[i][j] instanceof CasillaNegra;
    }
}