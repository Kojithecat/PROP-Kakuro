package capaDominio.secuencia;

import java.util.ArrayList;


public class Secuencia { 
    public ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> pos = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>(46);
    private static final int[] numeros = new int[] {1,2,3,4,5,6,7,8,9};

    public Secuencia(){
        for(int i = 0; i < 46; i++){
            pos.add(new ArrayList<ArrayList<ArrayList<Integer>>>());
            for(int j = 0; j < 10; j++){
                pos.get(i).add(new ArrayList<ArrayList<Integer>>());
            } 
        }
        ArrayList<Integer> combi = new ArrayList<Integer>();
        combinaciones(combi, 0, 0);

    }
 
    
    /** 
     * @param comb
     * @return int
     */
    private int suma(ArrayList<Integer> comb){
        int sum = 0;
        for(int i = 0; i < comb.size(); i++)
             sum += comb.get(i);
        return sum;
    }

    
    /** 
     * @param comb
     */
    private void guardar(ArrayList<Integer> comb){
       pos.get(suma(comb)).get(comb.size()).add(comb);// suma(comb) nos da la suma que tienen las casillas de comb,
                                                       // comb.size() nos da el numero de casillas, es ahi donde insertamos la nueva combinacion
    }                                                           

    
    /** 
     * @param comb
     * @param length
     * @param 10
     */
    private void combinaciones(ArrayList<Integer> comb, int length, int maxVal) { // k = numCeldas
        if(length < 10){
            //Ha llegdo al length deseado, ahora toca guardarlo
            if(length > 1) guardar(comb);
            for(int i : numeros){ 
                if(i > maxVal){ 
                    ArrayList<Integer> nextComb = new ArrayList<>(comb);
                    nextComb.add(i);
                    combinaciones(nextComb,length+1,i);              
                } 
                
            }
        }
    }

    
    /** 
     * @param SumaTotal
     * @param NumCasillas
     * @return int
     */
    public int getNumeroSecuencias(int SumaTotal, int NumCasillas) {
        return pos.get(SumaTotal).get(NumCasillas).size();
    }

    
    /** 
     * @param SumaTotal
     * @param NumCasillas
     * @param SumaTotal
     * @param NumCasillas
     * @return int[]
     */
    public int[] getSecuencia(int SumaTotal, int NumCasillas, int Eleccion) {       // Estaría interesante retornar una matriz con todas las elecciones
        int[] array = pos.get(SumaTotal).get(NumCasillas).get(Eleccion).stream().mapToInt(i->i).toArray(); // Mirar si devuelve una copia o un puntero
        return array;
    }

    
    /** 
     * @param SumaTotal
     * @param NumCasillas
     * @return ArrayList<ArrayList<Integer>>
     */
    public ArrayList<ArrayList<Integer>> getSecuencia(int SumaTotal, int NumCasillas) {
        ArrayList<ArrayList<Integer>> array = new ArrayList<>(pos.get(SumaTotal).get(NumCasillas));
        return array;
    }
}
