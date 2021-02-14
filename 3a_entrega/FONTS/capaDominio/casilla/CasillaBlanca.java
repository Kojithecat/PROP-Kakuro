package capaDominio.casilla;

import java.util.EnumSet;
import java.util.Iterator;

import capaDominio.candidatos.Candidatos;
import capaDominio.pair.*;

public class CasillaBlanca extends Casilla {
    boolean modificable;
    int numeroElejido;
    int numeroSolucion;
    
    Pair casillaNegraVerticalCoord = new Pair();
    Pair casillaNegraHorizontalCoord = new Pair();
    
    private EnumSet<Candidatos> posibles = EnumSet.noneOf(Candidatos.class);

    public CasillaBlanca(Pair pos){
        super(pos);
        numeroElejido = -1;
        modificable = true;
        numeroSolucion = -1;
    }

    public CasillaBlanca(Pair pos, int num, boolean esMod){
        super(pos);
        numeroElejido = num;
        modificable = esMod;
        numeroSolucion = num;
    }
    
    /** 
     * @param xV
     * @param yV
     */
    @Override
    public void setCasillaNegraV(int xV, int yV){
        casillaNegraVerticalCoord.setFstSnd(xV, yV);
    }
    
    /** 
     * @param xH
     * @param yH
     */
    @Override
    public void setCasillaNegraH(int xH, int yH){
        casillaNegraHorizontalCoord.setFstSnd(xH, yH);
    }
    
    /** 
     * @param numeroMod
     */
    @Override
    public void setNumeroElejido(int numeroMod){
        if(modificable){
            numeroElejido = numeroMod;
        }
    }
    
    /** 
     * @param numeroMod
     */
    @Override
    public void setNumeroSolucion(int numeroMod){
        numeroSolucion = numeroMod;
    }
    
    /** 
     * @param modCandidatos
     */
    @Override
    public void setCandidatos(EnumSet<Candidatos> modCandidatos){
        posibles = modCandidatos;
    }
    @Override
    public void setCandidatosAddAll(){
        posibles = EnumSet.allOf(Candidatos.class);
    }
    
    /** 
     * @param esMod
     */
    @Override
    public void setModificable(boolean esMod){
        modificable = esMod;
    }
    
    /** 
     * @return int
     */
    @Override
    public int getNumElejido(){
        return numeroElejido;
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean getModificable(){
        return modificable;
    }
    
    /** 
     * @return int
     */
    @Override
    public int getNumeroSolucion(){
        return numeroSolucion;
    }
    
    /** 
     * @return Pair
     */
    @Override
    public Pair getCasillaNegraV(){
        return casillaNegraVerticalCoord;
    }
    
    /** 
     * @return Pair
     */
    @Override
    public Pair getCasillaNegraH(){
        return casillaNegraHorizontalCoord;
    }
    
    /** 
     * @param val
     * @return boolean
     */
    @Override
    public boolean getCandidatosContains(Candidatos val){
        return posibles.contains(val);
    }
    
    /** 
     * @return int
     */
    @Override
    public int getCandidatosSize() {
        return posibles.size();
    }
    
    /** 
     * @return EnumSet<Candidatos>
     */
    @Override
    public EnumSet<Candidatos> getCandidatosSet() {
        return posibles;
    }
    
    /** 
     * @return int[]
     */
    @Override
    public int[] getCandidatosArray() {
        int size = posibles.size();
        int[] Array = new int[size];
        Iterator<Candidatos> iter = posibles.iterator();
        int i = 0;
        while (iter.hasNext() && i < size) {
            int x = Candidatos.enumTOint(iter.next());
            Array[i] = x;
            i++;
        }
        return Array;
    }

    public void printCasilla() {
        System.out.print(numeroSolucion);
    }

    
    /** 
     * @return String
     */
    public String toString() {
        if (numeroElejido == -1) return "?";
        return Integer.toString(numeroElejido);
    }

    
    /** 
     * @return String
     */
    public String toStringSol()  {
        return Integer.toString(numeroSolucion);
    }
}