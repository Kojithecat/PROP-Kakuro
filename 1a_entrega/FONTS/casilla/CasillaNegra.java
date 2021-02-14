package casilla;

import java.util.*;

import candidatos.Candidatos;
import pair.Pair;

public class CasillaNegra extends Casilla {
    private int sumaHorizontal;
    private int sumaVertical;
    private int numCellHorizontal;
    private int numCellVertical;

    private ArrayList<ArrayList<Integer>> secuenciasCandidatasVertical;
    private ArrayList<ArrayList<Integer>> secuenciasCandidatasHorizontal;

    private ArrayList<EnumSet<Candidatos>> CandidatosHorizontal = new ArrayList<EnumSet<Candidatos>>();
    private ArrayList<EnumSet<Candidatos>> CandidatosVertical = new ArrayList<EnumSet<Candidatos>>();
    private EnumSet<Candidatos> ConvergenHorizontal = EnumSet.noneOf(Candidatos.class);
    private EnumSet<Candidatos> ConvergenVertical = EnumSet.noneOf(Candidatos.class);

    public CasillaNegra(Pair pos){
        super(pos);
        sumaHorizontal = 0;
        sumaVertical = 0;
        numCellHorizontal = 0;
        numCellVertical = 0;
    }
    public CasillaNegra(Pair pos, int sH, int sV, int ncH, int ncV){
        super(pos);
        sumaHorizontal = sH;
        sumaVertical = sV;
        numCellHorizontal = ncH;
        numCellVertical = ncV;
    }


    public CasillaNegra(Pair pos, int sH, int sV, int ncH, int ncV, ArrayList<ArrayList<Integer>> secuenciasCanH, ArrayList<ArrayList<Integer>> secuenciasCanV){
        super(pos);
        sumaHorizontal = sH;
        sumaVertical = sV;
        numCellHorizontal = ncH;
        numCellVertical = ncV;
        secuenciasCandidatasVertical = secuenciasCanV;
        secuenciasCandidatasHorizontal = secuenciasCanH;
    }

    public CasillaNegra(Pair pos, int sH, int sV, ArrayList<ArrayList<Integer>> secuenciasCanH, ArrayList<ArrayList<Integer>> secuenciasCanV){
        super(pos);
        sumaHorizontal = sH;
        sumaVertical = sV;
        numCellHorizontal = 0;
        numCellVertical = 0;
        secuenciasCandidatasVertical = secuenciasCanV;
        secuenciasCandidatasHorizontal = secuenciasCanH;
    }
    @Override
    public void filtroDebeContenerVertical(int valor) { // Borra todas las secuencias que no contienen valor
        Iterator<ArrayList<Integer>> it = secuenciasCandidatasVertical.iterator();
        while (it.hasNext()) {
            ArrayList<Integer> candidato = it.next();
            if(! candidato.contains(valor))it.remove();
        }
    }
    @Override
    public void filtroDebeContenerHorizontal(int valor) { // Borra todas las secuencias que no contienen valor
        Iterator<ArrayList<Integer>> it = secuenciasCandidatasHorizontal.iterator();
        while (it.hasNext()) {
            ArrayList<Integer> candidato = it.next();
            if(! candidato.contains(valor))it.remove();
        }
    }
    @Override
    public void setSecuenciasCandidatasVertical(ArrayList<ArrayList<Integer>> secuenciasCan) {
        secuenciasCandidatasVertical = secuenciasCan;
    }
    @Override
    public void setSecuenciasCandidatasHorizontal(ArrayList<ArrayList<Integer>> secuenciasCan) {
        secuenciasCandidatasHorizontal = secuenciasCan;
    }
    @Override
    public void setSumaVertical(int sV){
        sumaVertical = sV;
    }
    @Override
    public void setSumaHorizontal(int sH){
        sumaHorizontal = sH;
    }
    @Override
    public void setNumCellVertical(int numCellV){
        numCellVertical = numCellV;
    }
    @Override
    public void setNumCellHorizontal(int numCellH){
        numCellHorizontal = numCellH;
    }
    @Override
    public void setCandidatosV(ArrayList<EnumSet<Candidatos>> modCandidatos){
        CandidatosVertical = modCandidatos;
    }
    @Override
    public void setCandidatosH(ArrayList<EnumSet<Candidatos>> modCandidatos){
        CandidatosHorizontal = modCandidatos;
    }
    @Override
    public void setConvergenV(EnumSet<Candidatos> modConvergen){
        ConvergenVertical = modConvergen;
    }
    @Override
    public void setConvergenVAdd(Candidatos val){
        ConvergenVertical.add(val);
    }
    @Override
    public void setConvergenH(EnumSet<Candidatos> modConvergen){
        ConvergenHorizontal = modConvergen;
    }
    @Override
    public void setConvergenHAdd(Candidatos val){
        ConvergenHorizontal.add(val);
    }
    @Override
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasVertical() {
        return secuenciasCandidatasVertical;
    }
    @Override
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasHorizontal() {
        return secuenciasCandidatasHorizontal;
    }
    @Override
    public int  getSumaVertical(){
        return sumaVertical;
    }
    @Override
    public int  getSumaHorizontal(){
        return sumaHorizontal;
    }
    @Override
    public int  getNumCellVertical(){
        return numCellVertical;
    }
    @Override
    public int  getNumCellHorizontal(){
        return numCellHorizontal;
    }
    @Override
    public ArrayList<EnumSet<Candidatos>>  getCandidatosVertical(){
        return CandidatosVertical;
    }
    @Override
    public ArrayList<EnumSet<Candidatos>>  getCandidatosHorizontal(){
        return CandidatosHorizontal;
    }
    @Override
    public EnumSet<Candidatos>  getConvergenVertical(){
        return ConvergenVertical;
    }
    @Override
    public EnumSet<Candidatos>  getConvergenHorizontal(){
        return ConvergenHorizontal;
    }

    public void printCasilla() {
        System.out.print(sumaVertical + "╲" + sumaHorizontal);
    }
}
