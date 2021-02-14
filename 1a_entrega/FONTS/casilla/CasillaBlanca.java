package casilla;

import java.util.EnumSet;
import java.util.Iterator;

import candidatos.Candidatos;
import pair.*;

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
        numeroSolucion = -1;
    }
    @Override
    public void setCasillaNegraV(int xV, int yV){
        casillaNegraVerticalCoord.setFstSnd(xV, yV);
    }
    @Override
    public void setCasillaNegraH(int xH, int yH){
        casillaNegraHorizontalCoord.setFstSnd(xH, yH);
    }
    @Override
    public void setNumeroElejido(int numeroMod){
        if(modificable){
            numeroElejido = numeroMod;
        }
    }
    @Override
    public void setNumeroSolucion(int numeroMod){
        numeroSolucion = numeroMod;
    }
    @Override
    public void setCandidatos(EnumSet<Candidatos> modCandidatos){
        posibles = modCandidatos;
    }
    @Override
    public void setCandidatosAddAll(){
        posibles = EnumSet.allOf(Candidatos.class);
    }
    @Override
    public void setModificable(boolean esMod){
        modificable = esMod;
    }
    @Override
    public int getNumElejido(){
        return numeroElejido;
    }
    @Override
    public boolean getModificable(){
        return modificable;
    }
    @Override
    public int getNumeroSolucion(){
        return numeroSolucion;
    }
    @Override
    public Pair getCasillaNegraV(){
        return casillaNegraVerticalCoord;
    }
    @Override
    public Pair getCasillaNegraH(){
        return casillaNegraHorizontalCoord;
    }
    @Override
    public boolean getCandidatosContains(Candidatos val){
        return posibles.contains(val);
    }
    @Override
    public int getCandidatosSize() {
        return posibles.size();
    }
    @Override
    public EnumSet<Candidatos> getCandidatosSet() {
        return posibles;
    }
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
}