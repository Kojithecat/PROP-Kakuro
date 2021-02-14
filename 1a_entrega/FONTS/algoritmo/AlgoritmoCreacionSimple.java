package algoritmo;

import casilla.*;
import kakuro.*;
import pair.*;
import java.util.*;

public class AlgoritmoCreacionSimple {

    public static Kakuro crearKakuro(int altura, int anchura, int patron) {
        Kakuro k =  new Kakuro(altura, anchura);
        ArrayList<CasillaBlanca> casillasBlancas = patronBlancoNegro(k,patron);
        boolean test = false;
        while (!test) {
            test = backtrackingCutre(k,casillasBlancas,new Stack<Integer>());
        }
        return k;
    }

    private static ArrayList<CasillaBlanca> patronBlancoNegro (Kakuro k, int x) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        switch(x) {
            case 1:
            casillasBlancas = patronCuadrado(k);
            break;
            case 2:
            casillasBlancas =  patronEscalera(k);
            break;
            default:
            break;
        }
        inicializarKakuro(k);
        return casillasBlancas;
    }

    private static ArrayList<CasillaBlanca> patronCuadrado (Kakuro k) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        for (int i = 0; i < k.getAltura(); i++) {
            for (int j = 0; j < k.getAnchura(); j++) {
                if (i == 0 || j  == 0) k.setCasillaKakuro(i,j,new CasillaNegra(new Pair(i,j)));
                else if (true) {
                    CasillaBlanca cB = new CasillaBlanca(new Pair(i,j));
                    k.setCasillaKakuro(i,j,cB);
                    casillasBlancas.add(cB);
                }
            }
        }
        return casillasBlancas;
    }

    private static ArrayList<CasillaBlanca> patronEscalera (Kakuro k) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        for (int i = 0; i < k.getAltura(); i++) {
            for (int j = 0; j < k.getAnchura(); j++) {
                if (i == 0 || j  == 0) {
                    k.setCasillaKakuro(i,j,new CasillaNegra(new Pair(i,j)));
                }
                else if ((i-j) == 0 || (i-j) == -1 || (i-j) == 1) {
                    CasillaBlanca cB = new CasillaBlanca(new Pair(i,j));
                    k.setCasillaKakuro(i,j,cB);
                    casillasBlancas.add(cB);
                }
                else {
                    k.setCasillaKakuro(i,j,new CasillaNegra(new Pair(i,j)));
                }
            }
        }
        return casillasBlancas;
    }

    private static void inicializarKakuro (Kakuro k) {
        ArrayList<CasillaNegra> casillasNegras = k.getCasillasNegras();
        Iterator<CasillaNegra> it = casillasNegras.iterator();
        while (it.hasNext()) {
            CasillaNegra cN = it.next();
            int posV = cN.getPosFirst();
            int posH = cN.getPosSecond();

            int numCellV = 0;
            int i = posV+1;
            while (i < k.getAltura()) {
                if (k.esNegra(i, posH)) break;
                else {
                    k.getCasillaKakuro(i, posH).setCasillaNegraV(posV, posH);
                    numCellV++;
                }
                i++;
            }
            cN.setNumCellVertical(numCellV);

            int numCellH = 0;
            int j = posH+1;
            while (j < k.getAnchura()) {
                if (k.esNegra(posV, j)) break;
                else {
                    k.getCasillaKakuro(posV, j).setCasillaNegraH(posV, posH);
                    numCellH++;
                }
                j++;
            }
            cN.setNumCellHorizontal(numCellH);
        }
    }

    private static boolean backtrackingCutre (Kakuro k, ArrayList<CasillaBlanca> casillasBlancas, Stack<Integer> solucion) {
        if (casillasBlancas.size() == solucion.size()) {
            int i = 0;
            Iterator<CasillaBlanca> it = casillasBlancas.iterator();
            while (it.hasNext() && i < solucion.size()) {
                CasillaBlanca cB = it.next();
                cB.setNumeroSolucion(solucion.get(i));
                i++;
            }
            boolean no_repetidos = calcularSumas(k); //actualiza las negras
            if (!no_repetidos) return false;
            int res = AlgoritmoResolucion.algoritmoValidacion(k);
            System.out.println(res);
            if (res == 1) return true;
            else return false;
        }
        else {
            ArrayList<Integer> permutacion = new ArrayList<Integer>( List.of(1,2,3,4,5,6,7,8,9) );
            Collections.shuffle(permutacion);
            boolean finished = false;
            Iterator<Integer> it = permutacion.iterator();
            while (it.hasNext() && !finished){
                Integer x = it.next();
                solucion.add(x);
                finished = backtrackingCutre(k,casillasBlancas,solucion);
                solucion.pop();
            }
            return finished;
        }
    }

    private static boolean calcularSumas (Kakuro k) {
        ArrayList<CasillaNegra> casillasNegras = k.getCasillasNegras();
        Iterator<CasillaNegra> it = casillasNegras.iterator();
        while (it.hasNext()) {
            CasillaNegra cN = it.next();
            Integer posV = cN.getPosFirst();
            Integer posH = cN.getPosSecond();
            boolean[] repetido = new boolean[10];
            
            Arrays.fill(repetido, false);
            Integer sumV = 0;
            for(int i = 1; i <= cN.getNumCellVertical(); i++) {
                Casilla cB = k.getCasillaKakuro(posV + i, posH);
                Integer num = cB.getNumeroSolucion();
                if (num <= 9 && num >= 1) sumV += num;
                else System.out.println("**");
                if (repetido[num]) return false;
                else repetido[num] = true;
            }
            cN.setSumaVertical(sumV);

            Arrays.fill(repetido, false);
            Integer sumH = 0;
            for(int j = 1; j <= cN.getNumCellHorizontal(); j++) {
                Casilla cB = k.getCasillaKakuro(posV, posH + j);
                Integer num = cB.getNumeroSolucion();
                if (num <= 9 && num >= 1) sumH += num;
                if (repetido[num]) return false;
                else repetido[num] = true;
            }
            cN.setSumaHorizontal(sumH);
        }
        return true;
    }

}
