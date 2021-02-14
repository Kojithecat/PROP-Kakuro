package capaDominio.casilla;

import java.util.EnumSet;

import capaDominio.candidatos.Candidatos;
import capaDominio.pair.Pair;

import java.util.ArrayList;


public abstract class Casilla {
    Pair pos = new Pair();

    protected Casilla (Pair posicion) {
        pos.setFstSnd(posicion.getFirst(), posicion.getSecond());
    }

    
    /** 
     * @return int
     */
    //Casilla
    public int getPosFirst() {
        return pos.getFirst();
    }
    
    
    /** 
     * @return int
     */
    public int getPosSecond() {
        return pos.getSecond();
    }

    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     */
    public abstract void printCasilla();

    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     * @return String
     */
    public abstract String toString();
    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     * @return String
     */
    public abstract String toStringSol();
    
    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     */
    //Casilla blanca

    public void setNumeroElejido(int numeroMod){}
    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     */
    public void setModificable(boolean esMod){}
    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     */
    public void setNumeroSolucion(int numeroMod){}
    
    /** 
     * @param xV
     * @param xH
     * @param getNumElejido(
     */
    public void setCasillaNegraV(int xV, int yV){}
    
    /** 
     * @param xH
     * @param getNumElejido(
     */
    public void setCasillaNegraH(int xH, int yH){}
    
    /** 
     * @param getNumElejido(
     */
    public void setCandidatos(EnumSet<Candidatos> modCandidatos){}
    
    /** 
     * @param getNumElejido(
     */
    public void setCandidatosAddAll(){}

    
    /** 
     * @return int
     */
    public int getNumElejido(){
        return -1;
    }
    
    /** 
     * @return boolean
     */
    public boolean getModificable(){
        return false;
    }
    
    /** 
     * @return int
     */
    public int getNumeroSolucion(){
        return -1;
    }

    
    /** 
     * @return Pair
     */
    public Pair getCasillaNegraV(){
        return new Pair (-1,-1);
        
    }
    
    /** 
     * @return Pair
     */
    public Pair getCasillaNegraH(){
        return new Pair (-1,-1);
    }

    
    /** 
     * @param val
     * @return boolean
     */
    public boolean getCandidatosContains(Candidatos val) {
        return false;
    }

    
    /** 
     * @return int
     */
    public int getCandidatosSize() {
        return -1;
    }

    
    /** 
     * @return EnumSet<Candidatos>
     */
    public EnumSet<Candidatos> getCandidatosSet() {
        return EnumSet.noneOf(Candidatos.class);
    }
    
    /** 
     * @return int[]
     */
    public int[] getCandidatosArray() {
        return new int[0];
    }

    
    /** 
     * @param getSumaVertical(
     */
    //Casilla negra

    public void filtroDebeContenerVertical(int valor) {}
    
    /** 
     * @param getSumaVertical(
     */
    public void filtroDebeContenerHorizontal(int valor) {}
    
    /** 
     * @param getSumaVertical(
     */
    public void setSecuenciasCandidatasVertical(ArrayList<ArrayList<Integer>> secuenciasCan) {}
    
    /** 
     * @param getSumaVertical(
     */
    public void setSecuenciasCandidatasHorizontal(ArrayList<ArrayList<Integer>> secuenciasCan) {}
    
    /** 
     * @param getSumaVertical(
     */
    public void setSumaVertical(int sV){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setSumaHorizontal(int sH){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setNumCellVertical(int numCellV){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setNumCellHorizontal(int numCellH){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setCandidatosV(ArrayList<EnumSet<Candidatos>>modCandidatos){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setCandidatosH(ArrayList<EnumSet<Candidatos>> modCandidatos){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setConvergenV(EnumSet<Candidatos> modConvergen){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setConvergenVAdd(Candidatos val){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setConvergenH(EnumSet<Candidatos> modConvergen){}
    
    /** 
     * @param getSumaVertical(
     */
    public void setConvergenHAdd(Candidatos val){}

    
    /** 
     * @return int
     */
    public  int getSumaVertical(){
        return -1;
    }
    
    /** 
     * @return int
     */
    public  int getSumaHorizontal(){
        return -1;
    }  
    
    /** 
     * @return int
     */
    public  int getNumCellVertical(){
        return -1;
    }
    
    /** 
     * @return int
     */
    public  int getNumCellHorizontal(){
        return -1;
    }

    
    /** 
     * @return ArrayList<EnumSet<Candidatos>>
     */
    public ArrayList<EnumSet<Candidatos>> getCandidatosVertical(){
        return new ArrayList<EnumSet<Candidatos>>();
    }
    
    /** 
     * @return ArrayList<EnumSet<Candidatos>>
     */
    public ArrayList<EnumSet<Candidatos>>  getCandidatosHorizontal(){
        return new ArrayList<EnumSet<Candidatos>>();
    }
    
    /** 
     * @return EnumSet<Candidatos>
     */
    public EnumSet<Candidatos>  getConvergenVertical(){
        return EnumSet.noneOf(Candidatos.class);
    }
    
    /** 
     * @return EnumSet<Candidatos>
     */
    public EnumSet<Candidatos>  getConvergenHorizontal(){
        return EnumSet.noneOf(Candidatos.class);
    }
    
    /** 
     * @return ArrayList<ArrayList<Integer>>
     */
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasVertical() {
        return new ArrayList<ArrayList<Integer>>(0);
    }
    
    /** 
     * @return ArrayList<ArrayList<Integer>>
     */
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasHorizontal() {
        return new ArrayList<ArrayList<Integer>>(0);
    }

}
