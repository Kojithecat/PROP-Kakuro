package casilla;

import java.util.EnumSet;

import candidatos.Candidatos;
import pair.Pair;
import java.util.ArrayList;


public abstract class Casilla {
    Pair pos = new Pair();

    protected Casilla (Pair posicion) {
        pos.setFstSnd(posicion.getFirst(), posicion.getSecond());
    }

    //Casilla
    public int getPosFirst() {
        return pos.getFirst();
    }
    
    public int getPosSecond() {
        return pos.getSecond();
    }

    public abstract void printCasilla();
    
    //Casilla blanca

    public void setNumeroElejido(int numeroMod){}
    public void setModificable(boolean esMod){}
    public void setNumeroSolucion(int numeroMod){}
    public void setCasillaNegraV(int xV, int yV){}
    public void setCasillaNegraH(int xH, int yH){}
    public void setCandidatos(EnumSet<Candidatos> modCandidatos){}
    public void setCandidatosAddAll(){}

    public int getNumElejido(){
        return -1;
    }
    public boolean getModificable(){
        return false;
    }
    public int getNumeroSolucion(){
        return -1;
    }

    public Pair getCasillaNegraV(){
        return new Pair (-1,-1);
        
    }
    public Pair getCasillaNegraH(){
        return new Pair (-1,-1);
    }

    public boolean getCandidatosContains(Candidatos val) {
        return false;
    }

    public int getCandidatosSize() {
        return -1;
    }

    public EnumSet<Candidatos> getCandidatosSet() {
        return EnumSet.noneOf(Candidatos.class);
    }
    public int[] getCandidatosArray() {
        return new int[0];
    }

    //Casilla negra

    public void filtroDebeContenerVertical(int valor) {}
    public void filtroDebeContenerHorizontal(int valor) {}
    public void setSecuenciasCandidatasVertical(ArrayList<ArrayList<Integer>> secuenciasCan) {}
    public void setSecuenciasCandidatasHorizontal(ArrayList<ArrayList<Integer>> secuenciasCan) {}
    public void setSumaVertical(int sV){}
    public void setSumaHorizontal(int sH){}
    public void setNumCellVertical(int numCellV){}
    public void setNumCellHorizontal(int numCellH){}
    public void setCandidatosV(ArrayList<EnumSet<Candidatos>>modCandidatos){}
    public void setCandidatosH(ArrayList<EnumSet<Candidatos>> modCandidatos){}
    public void setConvergenV(EnumSet<Candidatos> modConvergen){}
    public void setConvergenVAdd(Candidatos val){}
    public void setConvergenH(EnumSet<Candidatos> modConvergen){}
    public void setConvergenHAdd(Candidatos val){}

    public  int getSumaVertical(){
        return -1;
    }
    public  int getSumaHorizontal(){
        return -1;
    }  
    public  int getNumCellVertical(){
        return -1;
    }
    public  int getNumCellHorizontal(){
        return -1;
    }

    public ArrayList<EnumSet<Candidatos>> getCandidatosVertical(){
        return new ArrayList<EnumSet<Candidatos>>();
    }
    public ArrayList<EnumSet<Candidatos>>  getCandidatosHorizontal(){
        return new ArrayList<EnumSet<Candidatos>>();
    }
    public EnumSet<Candidatos>  getConvergenVertical(){
        return EnumSet.noneOf(Candidatos.class);
    }
    public EnumSet<Candidatos>  getConvergenHorizontal(){
        return EnumSet.noneOf(Candidatos.class);
    }
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasVertical() {
        return new ArrayList<ArrayList<Integer>>(0);
    }
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasHorizontal() {
        return new ArrayList<ArrayList<Integer>>(0);
    }

}
