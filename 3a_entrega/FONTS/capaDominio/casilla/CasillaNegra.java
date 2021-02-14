package capaDominio.casilla;

import java.util.*;

import capaDominio.candidatos.Candidatos;
import capaDominio.pair.Pair;

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
    
    /** 
     * @param (it.hasNext()
     */
    @Override
    public void filtroDebeContenerVertical(int valor) { // Borra todas las secuencias que no contienen valor
        Iterator<ArrayList<Integer>> it = secuenciasCandidatasVertical.iterator();
        while (it.hasNext()) {
            ArrayList<Integer> candidato = it.next();
            if(! candidato.contains(valor))it.remove();
        }
    }
    
    /** 
     * @param (it.hasNext()
     */
    @Override
    public void filtroDebeContenerHorizontal(int valor) { // Borra todas las secuencias que no contienen valor
        Iterator<ArrayList<Integer>> it = secuenciasCandidatasHorizontal.iterator();
        while (it.hasNext()) {
            ArrayList<Integer> candidato = it.next();
            if(! candidato.contains(valor))it.remove();
        }
    }
    
    /** 
     * @param secuenciasCan
     */
    @Override
    public void setSecuenciasCandidatasVertical(ArrayList<ArrayList<Integer>> secuenciasCan) {
        secuenciasCandidatasVertical = secuenciasCan;
    }
    
    /** 
     * @param secuenciasCan
     */
    @Override
    public void setSecuenciasCandidatasHorizontal(ArrayList<ArrayList<Integer>> secuenciasCan) {
        secuenciasCandidatasHorizontal = secuenciasCan;
    }
    
    /** 
     * @param sV
     */
    @Override
    public void setSumaVertical(int sV){
        sumaVertical = sV;
    }
    
    /** 
     * @param sH
     */
    @Override
    public void setSumaHorizontal(int sH){
        sumaHorizontal = sH;
    }
    
    /** 
     * @param numCellV
     */
    @Override
    public void setNumCellVertical(int numCellV){
        numCellVertical = numCellV;
    }
    
    /** 
     * @param numCellH
     */
    @Override
    public void setNumCellHorizontal(int numCellH){
        numCellHorizontal = numCellH;
    }
    
    /** 
     * @param modCandidatos
     */
    @Override
    public void setCandidatosV(ArrayList<EnumSet<Candidatos>> modCandidatos){
        CandidatosVertical = modCandidatos;
    }
    
    /** 
     * @param modCandidatos
     */
    @Override
    public void setCandidatosH(ArrayList<EnumSet<Candidatos>> modCandidatos){
        CandidatosHorizontal = modCandidatos;
    }
    
    /** 
     * @param modConvergen
     */
    @Override
    public void setConvergenV(EnumSet<Candidatos> modConvergen){
        ConvergenVertical = modConvergen;
    }
    
    /** 
     * @param val
     */
    @Override
    public void setConvergenVAdd(Candidatos val){
        ConvergenVertical.add(val);
    }
    
    /** 
     * @param modConvergen
     */
    @Override
    public void setConvergenH(EnumSet<Candidatos> modConvergen){
        ConvergenHorizontal = modConvergen;
    }
    
    /** 
     * @param val
     */
    @Override
    public void setConvergenHAdd(Candidatos val){
        ConvergenHorizontal.add(val);
    }
    
    /** 
     * @return ArrayList<ArrayList<Integer>>
     */
    @Override
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasVertical() {
        return secuenciasCandidatasVertical;
    }
    
    /** 
     * @return ArrayList<ArrayList<Integer>>
     */
    @Override
    public ArrayList<ArrayList<Integer>> getSecuenciasCandidatasHorizontal() {
        return secuenciasCandidatasHorizontal;
    }
    
    /** 
     * @return int
     */
    @Override
    public int  getSumaVertical(){
        return sumaVertical;
    }
    
    /** 
     * @return int
     */
    @Override
    public int  getSumaHorizontal(){
        return sumaHorizontal;
    }
    
    /** 
     * @return int
     */
    @Override
    public int  getNumCellVertical(){
        return numCellVertical;
    }
    
    /** 
     * @return int
     */
    @Override
    public int  getNumCellHorizontal(){
        return numCellHorizontal;
    }
    
    /** 
     * @return ArrayList<EnumSet<Candidatos>>
     */
    @Override
    public ArrayList<EnumSet<Candidatos>>  getCandidatosVertical(){
        return CandidatosVertical;
    }
    
    /** 
     * @return ArrayList<EnumSet<Candidatos>>
     */
    @Override
    public ArrayList<EnumSet<Candidatos>>  getCandidatosHorizontal(){
        return CandidatosHorizontal;
    }
    
    /** 
     * @return EnumSet<Candidatos>
     */
    @Override
    public EnumSet<Candidatos>  getConvergenVertical(){
        return ConvergenVertical;
    }
    
    /** 
     * @return EnumSet<Candidatos>
     */
    @Override
    public EnumSet<Candidatos>  getConvergenHorizontal(){
        return ConvergenHorizontal;
    }

    public void printCasilla() {
        System.out.print(sumaVertical + "╲" + sumaHorizontal);
    }

    
    /** 
     * @return String
     */
    public String toString() {
        String res = "";
        if (sumaHorizontal == 0 && sumaVertical == 0) res =  "*";
        else if (sumaHorizontal != 0 && sumaVertical != 0) 
            res =  "C"+Integer.toString(sumaVertical)+"F"+Integer.toString(sumaHorizontal);
        else if (sumaHorizontal != 0)
            res = "F"+Integer.toString(sumaHorizontal);
        else 
            res =  "C"+Integer.toString(sumaVertical);
        return res;
    }

    
    /** 
     * @return String
     */
    public String toStringSol()  {
        return toString();
    }
}
