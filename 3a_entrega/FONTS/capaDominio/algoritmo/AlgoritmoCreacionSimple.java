package capaDominio.algoritmo;

import java.util.*;

import capaDominio.casilla.*;
import capaDominio.kakuro.*;
import capaDominio.pair.*;

public class AlgoritmoCreacionSimple {
    
    private static double progress;
    private static int r;
    private static int q = 2; //lottery

    
    /** 
     * @param altura
     * @param anchura
     * @param numBlancas
     * @param dificultad_loteria
     * @param patron
     * @return Kakuro
     */
    public static Kakuro crearKakuro(int altura, int anchura, int numBlancas, int dificultad_loteria, int patron) {
        if(altura <= 0 || anchura <= 0) {
            System.out.println("El usuario pedia tamaños malos");
            return new Kakuro(0,0);
        }
        int loop_cap = 10;
        q = dificultad_loteria;
        Kakuro k =  new Kakuro(altura, anchura);
        ArrayList<CasillaBlanca> casillasBlancas = patronBlancoNegro(k,patron,numBlancas);
        inicializarKakuro(k);
        boolean test = false;
        progress = 0.0;
        r = Math.max(4, casillasBlancas.size() - 10*q);
        //System.out.println((progress*100.0)+"%");
        test = backtrackingCutre(k,casillasBlancas,new Stack<Integer>());
        while (!test && loop_cap > 0) { //fuerza el aleatorio si falla el request
            System.out.println("Creation failed by exhaustion, generating new random pattern.");
            System.gc();
            casillasBlancas = patronBlancoNegro(k,3,numBlancas);
            inicializarKakuro(k);
            progress = 0.0;
            r = Math.max(4, casillasBlancas.size() - 10*q);
            //System.out.println((progress*100.0)+"%");
            test = backtrackingCutre(k,casillasBlancas,new Stack<Integer>());
            loop_cap--;
        }
        if (loop_cap <= 0) {
            System.out.println("Se terminaron los intentos");
            return new Kakuro(0,0);
        }
        int res = AlgoritmoResolucion.algoritmoValidacion(k);
        System.out.println("AlgVal comprueba: " + res);
        System.gc();
        return k;
    }

    
    /** 
     * @param k
     * @param x
     * @param numBlancas
     * @return ArrayList<CasillaBlanca>
     */
    private static ArrayList<CasillaBlanca> patronBlancoNegro (Kakuro k, int x, int numBlancas) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        switch(x) {
            case 1:
            casillasBlancas = patronCuadrado(k);
            break;
            case 2:
            casillasBlancas =  patronEscalera(k);
            break;
            case 3:
            casillasBlancas =  patronAleatorio(k, numBlancas);
            break;
            /*case 4:
            casillasBlancas =  patronEstilizado(k);
            break;
            */
            default:
            break;
        }
        Collections.shuffle(casillasBlancas);
        return casillasBlancas;
    }

    
    /** 
     * @param k
     * @return ArrayList<CasillaBlanca>
     */
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

    
    /** 
     * @param k
     * @return ArrayList<CasillaBlanca>
     */
    private static ArrayList<CasillaBlanca> patronEscalera (Kakuro k) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        boolean suficientementeGrande = (k.getAltura() > 2 && k.getAnchura() > 2);
        for (int i = 0; i < k.getAltura(); i++) {
            for (int j = 0; j < k.getAnchura(); j++) {
                if (i == 0 || j  == 0) {
                    k.setCasillaKakuro(i,j,new CasillaNegra(new Pair(i,j)));
                }
                else if ( ((i-j) == 0 || (i-j) == -1 || (i-j) == 1) && (i < k.getAnchura() && j < k.getAltura()) && suficientementeGrande) {
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

    
    /** 
     * @param k
     * @param numBlancas
     * @return ArrayList<CasillaBlanca>
     */
    private static ArrayList<CasillaBlanca> patronAleatorio (Kakuro k, int numBlancas) {
        int h = k.getAltura();
        int w = k.getAnchura();
        boolean Map[][] = new boolean[h-1][w-1];
        for (boolean[] row: Map) Arrays.fill(row, false);
        if (h <= 2 || w <= 2) { //no se pueden hacer patrones
            return pharseBinMap(k,Map);
        }
        else {
            boolean plantilla2x2Map[][] = new boolean[h-2][w-2];
            for (boolean[] row: plantilla2x2Map) Arrays.fill(row, false);
            generate2x2Map(plantilla2x2Map, Map, h, w, numBlancas);
            pharsePlantilla2x2(Map, plantilla2x2Map,h,w); //clean and restore Map from plantilla
            //printMap(Map);
            return pharseBinMap(k,Map);
        }
    }

    
    /** 
     * @param plantilla2x2Map[][]
     * @param Map[][]
     * @param h
     * @param w
     * @param target_numBlancas
     */
    private static void generate2x2Map (boolean plantilla2x2Map[][], boolean Map[][], int h, int w, int target_numBlancas) {
        //int average_target = (int) Math.ceil((h*w/4)/2);
        //target_numBlancas = Math.min(target_numBlancas, average_target);
        int real_numBlancas = 0;
        int buffer_numBlancas;
        int randAttempts = Math.max((int) Math.ceil((1.5*h*w*(0.5*Math.random()+0.5))), 2);
        for (int a = 0; a < randAttempts; a++) {
            boolean olda = true;
            boolean oldb = true;
            boolean oldc = true;
            boolean oldd = true;
            Random random = new Random();   
            int x = random.nextInt(h-2);
            int y = random.nextInt(w-2);
            plantilla2x2Map[x][y] = true;
            buffer_numBlancas = 0;
            if (!Map[x][y]) {
                olda = false;
                Map[x][y] = true;
                buffer_numBlancas++;
            }
            if (!Map[x+1][y]) {
                oldb = false;
                Map[x+1][y] = true;
                buffer_numBlancas++;
            }
            if (!Map[x][y+1]) {
                oldc = false;
                Map[x][y+1] = true;
                buffer_numBlancas++;
            }
            if (!Map[x+1][y+1]) {
                oldd = false;
                Map[x+1][y+1] = true;
                buffer_numBlancas++;
            }
            boolean correct = false;
            if (buffer_numBlancas + real_numBlancas <= target_numBlancas) {
                correct = checkMap(Map,x,y);
                if (correct) {
                    correct = checkMap(Map,x+1,y+1);
                }
            }
            if (!correct) {
                plantilla2x2Map[x][y] = false;
                Map[x][y] = olda;
                Map[x+1][y] = oldb;
                Map[x][y+1] = oldc;
                Map[x+1][y+1] = oldd;
            } else { //the step is correct, keep it
                real_numBlancas += buffer_numBlancas;
            }
        }
    }

    
    /** 
     * @param Map[][]
     * @param plantilla2x2Map[][]
     * @param h
     * @param w
     */
    private static void pharsePlantilla2x2 (boolean Map[][], boolean plantilla2x2Map[][], int h, int w) {
        for (boolean[] row: Map) Arrays.fill(row, false);
        for (int i = 0; i < plantilla2x2Map.length; i++) {
            for (int j = 0; j < plantilla2x2Map[i].length; j++) {
                if (plantilla2x2Map[i][j]) {
                    Map[i][j] = true;
                    Map[i+1][j] = true;
                    Map[i][j+1] = true;
                    Map[i+1][j+1] = true;
                }
            }
        }
    }

    
    /** 
     * @param Map[][]
     * @param x
     * @param y
     * @return boolean
     */
    private static boolean checkMap (boolean Map[][], int x, int y) {
        int i = x-1;
        int j = y;
        boolean finished = false;
        int sumLeft = 0;
        while (inRange(i,j,Map) && !finished) {
            if (!Map[i][j]) finished = true;
            else {
                sumLeft++;
                if (sumLeft > 8) return false;
                i--;
            }
        }
        i = x+1;
        j = y;
        finished = false;
        int sumRight = 0;
        while (inRange(i,j,Map) && !finished) {
            if (!Map[i][j]) finished = true;
            else {
                sumRight++;
                if (sumRight + sumLeft > 8) return false;
                i++;
            }
        }
        i = x;
        j = y-1;
        finished = false;
        int sumUnder = 0;
        while (inRange(i,j,Map) && !finished) {
            if (!Map[i][j]) finished = true;
            else {
                sumUnder++;
                if (sumUnder > 8) return false;
                j--;
            }
        }
        i = x;
        j = y+1;
        finished = false;
        int sumAbove = 0;
        while (inRange(i,j,Map) && !finished) {
            if (!Map[i][j]) finished = true;
            else {
                sumAbove++;
                if (sumAbove + sumUnder > 8) return false;
                j++;
            }
        }
        return true;
    }

    
    /** 
     * @param x
     * @param y
     * @param Map[][]
     * @return boolean
     */
    private static boolean inRange(int x, int y, boolean Map[][]) {
        if (x < Map.length && x >= 0) {
            if (y < Map[x].length && y >= 0) {
                return true;
            }
        }
        return false;
    }

    
    /** 
     * @param k
     * @param Map[][]
     * @return ArrayList<CasillaBlanca>
     */
    private static ArrayList<CasillaBlanca> pharseBinMap (Kakuro k, boolean Map[][]) {
        ArrayList<CasillaBlanca> casillasBlancas = new ArrayList<CasillaBlanca>();
        for (int i = 0; i < k.getAltura(); i++) {
            for (int j = 0; j < k.getAnchura(); j++) {
                if (i == 0 || j  == 0) {
                    k.setCasillaKakuro(i,j,new CasillaNegra(new Pair(i,j)));
                }
                else if ( Map[i-1][j-1] ) {
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

    
    /** 
     * @param Map[][]
     */
    private static void printMap(boolean Map[][]) {
        for (boolean[] row: Map) {
            System.out.println("");
            for (boolean elem: row) {
                if (elem) System.out.print("1");
                else System.out.print("0");
            }
        }
        System.out.println("");
    }

    
    /** 
     * @param k
     */
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
            cN.setSumaVertical(0);

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
            cN.setSumaHorizontal(0);
        }
    }

    
    /** 
     * @param k
     * @param casillasBlancas
     * @param solucion
     * @return boolean
     */
    private static boolean backtrackingCutre (Kakuro k, ArrayList<CasillaBlanca> casillasBlancas, Stack<Integer> solucion) {
        if (casillasBlancas.size() == solucion.size()) {
            boolean no_repetidos = calcularSumas(k); //actualiza las negras
            if (!no_repetidos) return false;
            int res = AlgoritmoResolucion.algoritmoValidacion(k);

            //System.out.println(res);
            if (res == 1) return true;
            else {
                //restaurar los valores que el algoritmoValidacion ha modificado
                int i = 0;
                Iterator<CasillaBlanca> it = casillasBlancas.iterator();
                while (it.hasNext() && i < solucion.size()) {
                    CasillaBlanca cB = it.next();
                    cB.setNumeroSolucion(solucion.get(i));
                    i++;
                }
                return false;
            }
        }
        else {
            ArrayList<Integer> permutacion = new ArrayList<Integer>( List.of(1,2,3,4,5,6,7,8,9) );
            Collections.shuffle(permutacion);
            for (int i  = 9; i > q; i--) permutacion.remove(permutacion.size()-1); //lottery
            CasillaBlanca cB = casillasBlancas.get(solucion.size());
            Pair posV = cB.getCasillaNegraV();
            Casilla PV = k.getCasillaKakuro(posV.getFirst(), posV.getSecond());
            //int oldSumV = PV.getSumaVertical();
            Pair posH = cB.getCasillaNegraH();
            Casilla PH = k.getCasillaKakuro(posH.getFirst(), posH.getSecond());
            //int oldSumH = PV.getSumaHorizontal();
            boolean finished = false;
            Iterator<Integer> it = permutacion.iterator();
            while (it.hasNext() && !finished){
                Integer x = it.next();
                solucion.add(x);
                cB.setNumeroSolucion(x);
                //PV.setSumaVertical(oldSumV + x);
                //PH.setSumaHorizontal(oldSumH + x);
                boolean poda = podarSiRepetidos(k, PV, PH);
                if (!poda) finished = backtrackingCutre(k,casillasBlancas,solucion);
                //PV.setSumaVertical(oldSumV);
                //PH.setSumaHorizontal(oldSumH);
                cB.setNumeroSolucion(-1);
                solucion.pop();
            }
            if (solucion.size() == r) {
                //progress += 1/(Math.pow(q, r));
                //System.out.println((progress*100.0)+"%");
            }
            return finished;
        }
    }

    
    /** 
     * @param k
     * @return boolean
     */
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
                else System.out.println("*ERROR -1 EN LA GENERACION V*");
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
                else System.out.println("*ERROR -1 EN LA GENERACION H*");
                if (repetido[num]) return false;
                else repetido[num] = true;
            }
            cN.setSumaHorizontal(sumH);
        }
        return true;
    }

    
    /** 
     * @param k
     * @param pV
     * @param pH
     * @return boolean
     */
    private static boolean podarSiRepetidos (Kakuro k, Casilla pV, Casilla pH) {
        int i;
        boolean finished;
        boolean[] repetido = new boolean[10];

        i = 1;
        finished = false;
        Arrays.fill(repetido, false);
        while (i <= pV.getNumCellVertical() && !finished) {
            Casilla tempB = k.getCasillaKakuro(pV.getPosFirst() + i, pV.getPosSecond());
            Integer num = tempB.getNumeroSolucion();
            if (num <= 9 && num >= 1) {
                if (repetido[num]) return true; //poda
                else repetido[num] = true;
            }
            i++;
        }

        i = 1;
        finished = false;
        Arrays.fill(repetido, false);
        while (i <= pH.getNumCellHorizontal() && !finished) {
            Casilla tempB = k.getCasillaKakuro(pH.getPosFirst(), pH.getPosSecond() + i);
            Integer num = tempB.getNumeroSolucion();
            if (num <= 9 && num >= 1) {
                if (repetido[num]) return true; //poda
                else repetido[num] = true;
            }
            i++;
        }

        return false; //no podar
    }

}
