package capaDominio.kakuro;

import java.util.*;

import capaDominio.casilla.*;


public class Kakuro {

    private int ID;

    private Casilla[][] tablero;

    private ArrayList<CasillaNegra> casillasNegras = new ArrayList<>(); // Solo agreganos las casillas negras que tengan sumaVertical o Horizontal

    public Kakuro(int h, int v) {
        ID = -1;
        tablero = new Casilla[h][v];       
    }
    
    
    /** 
     * @param i
     * @param j
     * @param c
     */
    public void setCasillaKakuro(int i, int j, Casilla c) {
        tablero[i][j] = c;
        if(c instanceof CasillaNegra){
            casillasNegras.add((CasillaNegra) c);
        }
    }

    
    /** 
     * @param listaCasilla
     */
    public void setCasillaNegra(ArrayList<CasillaNegra> listaCasilla) {
        casillasNegras = listaCasilla;
    }

    
    /** 
     * @param num
     */
    public void setId(int num) {
        ID = num;
    }

    
    /** 
     * @return ArrayList<CasillaNegra>
     */
    public ArrayList<CasillaNegra> getCasillasNegras() { 
        return casillasNegras;
    }

    
    /** 
     * @param i
     * @param j
     * @return Casilla
     */
    public Casilla getCasillaKakuro(int i, int j) {
        return tablero[i][j];
    }

    
    /** 
     * @return int
     */
    public int getAltura() {
        return tablero.length;
    }

    
    /** 
     * @return int
     */
    public int getAnchura() {
        if (tablero.length == 0)
            return 0;
        return tablero[0].length;
    }

    
    /** 
     * @return int
     */
    public int getId() {
        return ID;
    }

    
    /** 
     * @param i
     * @param j
     * @return boolean
     */
    public boolean esNegra(int i, int j) {
        return tablero[i][j] instanceof CasillaNegra;
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> toArrayList() {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();

        res.add(new ArrayList<String>()); // solucion
        res.add(new ArrayList<String>());
        int altura = tablero.length;
        int anchura;
        if (altura == 0)
            anchura = 0;
        else anchura = tablero[0].length;
        res.get(0).add(Integer.toString(altura)); 
        res.get(0).add(Integer.toString(anchura));
    

        for (int i = 0; i < tablero.length; i++){
            res.add(new ArrayList<String>());
            for (int j = 0; j < tablero[i].length; j++){
                res.get(i+2).add(tablero[i][j].toStringSol());
                if (tablero[i][j].getModificable()) res.get(1).add("1");
                else res.get(1).add("0");
            }
        }
        return res;
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> toArrayListPartida() {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
 
 
        res.add(new ArrayList<String>()); // elegidos
        res.add(new ArrayList<String>());
 
        res.get(0).add(Integer.toString(tablero.length)); 
        res.get(0).add(Integer.toString(tablero[0].length));
 
        for (int i = 0; i < tablero.length; i++){
            res.add(new ArrayList<String>());
            for (int j = 0; j < tablero[i].length; j++){
                res.get(i+2).add(tablero[i][j].toString());
                if (tablero[i][j].getModificable()) res.get(1).add("1");
                else res.get(1).add("0");
            }
        }
        return res;
    }

    public void vaciar() {
        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[i].length; j++){
                if (tablero[i][j] instanceof CasillaBlanca) {
                    tablero[i][j].setNumeroElejido(-1);
                }
            }
        }
    }
}