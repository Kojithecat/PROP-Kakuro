package capaDominio.secuencia;

import java.util.*;

public class Combinacion {
    ArrayList<ArrayList<ArrayList<Integer>>> comb = new ArrayList<ArrayList<ArrayList<Integer>>>(10);
    public Combinacion()
    {
       
        for(int i = 0; i < 10; i++)
        {
            comb.add(new ArrayList<ArrayList<Integer>>());
            for (int j = 0; j < 46; j++)
            {
                comb.get(i).add(new ArrayList<Integer>());
            }
        }
        int []start = {1,2,3,4,5,6,7,8,9}; 
        int n = start.length; 

        Boolean[] sumandos = {false,false,false,false,false,false,false,false,false,false};

        calcularCombinaciones(start, 0, n, 0, comb, sumandos);
    }

    
    /** 
     * @param res
     * @param sumandos
     */
    // Calculates sums of all subsets of arr[l..r] 
    private void calcularCombinaciones(int []arr, int l, int r, int sum, ArrayList<ArrayList<ArrayList<Integer>>> res, Boolean[] sumandos) 
    {           
        // Print current subset 
        if (l == r) 
        {
            int nums = 0;
            for(int i = 0; i < 10; i++)
                if (sumandos[i]) nums += 1;
            
            for (int i = 1; i < 10; i++)
                if (sumandos[i] && !res.get(nums).get(i).contains(sum)) res.get(nums).get(i).add(sum);
            return; 
        } 
    
        // Subset including arr[l]
        sumandos[arr[l]] = true;
        calcularCombinaciones(arr, l + 1, r, sum + arr[l], res, sumandos); 
    
        // Subset excluding arr[l] 
        sumandos[arr[l]] = false;
        calcularCombinaciones(arr, l + 1, r, sum, res, sumandos); 
    } 

    
    /** 
     * @param numCasillas
     * @param numero
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getCombinacion(int numCasillas, int numero) {
        return  new ArrayList<>(comb.get(numCasillas).get(numero));
    }
}