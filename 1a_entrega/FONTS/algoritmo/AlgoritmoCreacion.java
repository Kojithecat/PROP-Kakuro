package algoritmo;

import kakuro.*;
import pair.*;

import java.util.*;

import casilla.*;
import secuencia.*;
import candidatos.*;


public class AlgoritmoCreacion {

    private static Combinacion comb = new Combinacion();
    private static Secuencia sec = new Secuencia();
    private static Queue<Casilla> pend = new LinkedList<Casilla>();

    public static Kakuro crearKakuro(int altura, int anchura) {
        

        Kakuro k =  new Kakuro(altura, anchura);
        crearTablero(k);
        initCasillas(k);
        rellenarTablero(k);

        ArrayList<EnumSet<Candidatos>> casillasSalvadas = obtenConvergidos(k.getCasillasNegras());

        int res = AlgoritmoResolucion.algoritmoValidacion(k);

        while (res != 1) {
            Pair pos = new Pair(-1,-1);
            AlgoritmoResolucion.comprobarSolucion(k, pos);
            Casilla cB = k.getCasillaKakuro(pos.getFirst(), pos.getSecond());

            reponerCasillasNegras(k, casillasSalvadas);

            if (cB.getPosFirst() != -1 && cB.getPosSecond() != -1){
                
                recalcularKakuro(k, cB);
            } 
            res = AlgoritmoResolucion.algoritmoValidacion(k);
            printKakuro(k);
            System.out.println("");
        }
        return k;
    }

    private static ArrayList<EnumSet<Candidatos>> obtenConvergidos(ArrayList<CasillaNegra> AC)
    {
        ArrayList<EnumSet<Candidatos>>  res = new ArrayList<>();
        for(int i = 0; i < AC.size(); i++)
        {
            res.add(AC.get(i).getConvergenHorizontal());
            res.add(AC.get(i).getConvergenVertical());
        }
        return res;
    }

    private static void printKakuro(Kakuro k) {
        for (int i = 0; i < k.getAltura(); i++){
            for(int j = 0; j < k.getAnchura();j++){
                k.getCasillaKakuro(i, j).printCasilla();
                System.out.print("\t\t");
            }
            System.out.println();
        }
    }

    private static void reponerCasillasNegras(Kakuro k, ArrayList<EnumSet<Candidatos>> casillasSalvadas) {
        for (int i = 0; i < casillasSalvadas.size()/2; i++) {
            int j = 2*i;
            k.getCasillasNegras().get(i).setConvergenH(casillasSalvadas.get(j));
            k.getCasillasNegras().get(i).setConvergenV(casillasSalvadas.get(j+1));
        }
    }

    private static void recalcularKakuro(Kakuro k, Casilla cB) {
        Pair posPadreV = cB.getCasillaNegraV(); 
        Pair posPadreH = cB.getCasillaNegraH();
        Casilla cPadreV = k.getCasillaKakuro(posPadreV.getFirst(),posPadreV.getSecond());
        Casilla cPadreH = k.getCasillaKakuro(posPadreH.getFirst(),posPadreH.getSecond());
        int posRelativaH = cB.getPosSecond() - cPadreH.getPosSecond();
        int posRelativaV = cB.getPosFirst() - cPadreV.getPosFirst();

        int numCasilla = cB.getNumElejido();
        if (numCasilla != -1) {
            cPadreV.getConvergenVertical().remove(Candidatos.intTOenum(numCasilla));
            cPadreH.getConvergenHorizontal().remove(Candidatos.intTOenum(numCasilla));
        }

        EnumSet<Candidatos> candi = cPadreV.getConvergenVertical();
        HashSet<Integer> setV = new HashSet<>(); 
        boolean first = true;
        for (Candidatos can : candi) {
            if (first) {
                setV.addAll(comb.getCombinacion(cPadreV.getNumCellVertical(), Candidatos.enumTOint(can)));
                first = false;
            }
            else setV.retainAll(comb.getCombinacion(cPadreV.getNumCellVertical(), Candidatos.enumTOint(can)));
        }

        candi = cPadreH.getConvergenHorizontal();
        HashSet<Integer> setH = new HashSet<>(); 
        first = true;
        for (Candidatos can : candi) {
            if (first) {
                setH.addAll(comb.getCombinacion(cPadreH.getNumCellHorizontal(), Candidatos.enumTOint(can)));
                first = false;
            }
            else setH.retainAll(comb.getCombinacion(cPadreH.getNumCellHorizontal(), Candidatos.enumTOint(can)));
        }

        //setV.removeAll(setH);
       // setH.removeAll(setV);
        // Poner sumas totales a los padres
        int posValor = (int) Math.floor(Math.random() * setV.size());
        Integer[] arrCandiV =  (Integer[])setV.toArray(new Integer[setV.size()]);
        int valor = arrCandiV[posValor].intValue();
        cPadreV.setSumaVertical(valor);

        posValor = (int) Math.floor(Math.random() * setH.size());
        Integer[] arrCandiH =  (Integer[])setH.toArray(new Integer[setH.size()]);
        valor = (arrCandiH[posValor]).intValue();
        cPadreH.setSumaHorizontal(valor);

        ArrayList<ArrayList<Integer>> secH = sec.getSecuencia(cPadreH.getSumaHorizontal(), cPadreH.getNumCellHorizontal());
        cPadreH.setSecuenciasCandidatasHorizontal(secH);
        ArrayList<ArrayList<Integer>> secV = sec.getSecuencia(cPadreV.getSumaVertical(), cPadreV.getNumCellVertical());
        cPadreV.setSecuenciasCandidatasVertical(secV);

        // Elegir numero para casilla blanca

        EnumSet<Candidatos> can = calcularCandidatos(cPadreH, cPadreV, posRelativaH, posRelativaV);
        Candidatos[] arrCandi =  (Candidatos[])can.toArray(new Candidatos[can.size()]);
        int valCB = Candidatos.enumTOint(arrCandi[0]);
        cB.setNumeroElejido(valCB);
        cPadreH.setConvergenHAdd(arrCandi[0]);
        cPadreV.setConvergenVAdd(arrCandi[0]);

        //Filtrar Secuencias
        filtrarConvergidosEnPadres(cPadreH, cPadreV, valCB);
        calcularCandidatosBlancas(cPadreH, posRelativaH, false);
        calcularCandidatosBlancas(cPadreV, posRelativaV, true);



        if(!pend.contains(cPadreH))pend.add(cPadreH);
        if(!pend.contains(cPadreV))pend.add(cPadreV);
        
    }

    private static void rellenarTablero(Kakuro k){
        int altura = k.getAltura();
        int anchura = k.getAnchura();

       
        boolean primero = false;
        for(int i = 0; i<altura && !primero; i++){  // Busca la primera casilla blanca
            for (int j = 0; j<anchura && !primero; j++){
                if(k.getCasillaKakuro(i, j) instanceof CasillaBlanca){
                    Pair posPadre =k.getCasillaKakuro(i, j).getCasillaNegraH(); 
                    pend.add(k.getCasillaKakuro(posPadre.getFirst(), posPadre.getSecond()));
                    posPadre =k.getCasillaKakuro(i, j).getCasillaNegraV(); 
                    pend.add(k.getCasillaKakuro(posPadre.getFirst(), posPadre.getSecond()));
                    primero = true;
                }
            }
        }

        while(!pend.isEmpty()){
            Casilla c = pend.peek(); pend.poll(); //Cogemos el primer elemento de la cola
            int numCellH = c.getNumCellHorizontal();
            int numCellV = c.getNumCellVertical();

            //Pair posC = new Pair(c.getPosFirst(),c.getPosSecond());
            if(numCellH != 0){
                Casilla cB = buscarSiguienteCasillaBlanca(k, c.getPosFirst(), c.getPosSecond(), false);
                if(cB != null) casuisticaNegraHV(k, cB);
            }
            if(numCellV != 0){
                Casilla cB = buscarSiguienteCasillaBlanca(k, c.getPosFirst(), c.getPosSecond(), true);
                if(cB != null) casuisticaNegraHV(k, cB);
            }
        }
    }

    private static void casuisticaNegraHV(Kakuro k, Casilla cB) {
        Pair posPadreV = cB.getCasillaNegraV(); 
        Pair posPadreH = cB.getCasillaNegraH();
        Casilla cPadreV = k.getCasillaKakuro(posPadreV.getFirst(),posPadreV.getSecond());
        Casilla cPadreH = k.getCasillaKakuro(posPadreH.getFirst(),posPadreH.getSecond());
        int sumaV =  cPadreV.getSumaVertical();
        int sumaH = cPadreH.getSumaHorizontal(); 

        if(sumaV != 0 && sumaH != 0) casoUnoUno(k, cB, cPadreH, cPadreV);
        else if(sumaV != 0) casoUnoCero(cB, cPadreH, cPadreV);
        else if(sumaH != 0) casoCeroUno(cB, cPadreH, cPadreV);
        else if(sumaV == 0 && sumaH == 0) casoCeroCero(cB, cPadreH, cPadreV);
    }

    private static void casoUnoUno(Kakuro k, Casilla cB, Casilla cPadreH, Casilla cPadreV) { // Padre Vertical y Horizontal ya tiene suma total
        // Obtener un posible valor random
        int posRelativaH = cB.getPosSecond() - cPadreH.getPosSecond();
        int posRelativaV = cB.getPosFirst() - cPadreV.getPosFirst();
        EnumSet<Candidatos> candi = calcularCandidatos(cPadreH, cPadreV, posRelativaH, posRelativaV);
        if (candi.size() == 0) recalcularKakuro(k, cB);

        int posValor = (int) Math.floor(Math.random() * candi.size());

        Candidatos[] arrCandi =  (Candidatos[])candi.toArray(new Candidatos[candi.size()]);
        int valor = Candidatos.enumTOint(arrCandi[posValor]);
        cB.setNumeroElejido(valor);

        //Filtrar Secuencias

        filtrarConvergidosEnPadres(cPadreH, cPadreV, valor);
        calcularCandidatosBlancas(cPadreH, posRelativaH, false);
        calcularCandidatosBlancas(cPadreV, posRelativaV, true);

        if(!pend.contains(cPadreH))pend.add(cPadreH);
        if(!pend.contains(cPadreV))pend.add(cPadreV);
    }

    private static void casoUnoCero(Casilla cB, Casilla cPadreH, Casilla cPadreV) { // Padre Vertical ya tiene suma total
        // Obtener un posible valor random
        int posRelativaH = cB.getPosSecond() - cPadreH.getPosSecond();
        int posRelativaV = cB.getPosFirst() - cPadreV.getPosFirst();

        EnumSet<Candidatos> candi = cPadreV.getCandidatosVertical().get(posRelativaV);

        int posValor = (int) Math.floor(Math.random() * candi.size());

        Candidatos[] arrCandi =  (Candidatos[])candi.toArray(new Candidatos[candi.size()]);
        int valor = Candidatos.enumTOint(arrCandi[posValor]);
        cB.setNumeroElejido(valor);

        // Elegir una sumatotal para padre H
        ArrayList<Integer> combH = comb.getCombinacion(cPadreH.getNumCellHorizontal(), valor);
        int indiceSumaH = (int) Math.floor(Math.random() * combH.size());
        cPadreH.setSumaHorizontal(combH.get(indiceSumaH));

        //Agregar y filtrar Secuencias
        ArrayList<ArrayList<Integer>> secH = sec.getSecuencia(combH.get(indiceSumaH), cPadreH.getNumCellHorizontal());
        cPadreH.setSecuenciasCandidatasHorizontal(secH);

        filtrarConvergidosEnPadres(cPadreH, cPadreV, valor);
        calcularCandidatosBlancas(cPadreH, posRelativaH, false);
        calcularCandidatosBlancas(cPadreV, posRelativaV, true);

        if(!pend.contains(cPadreH))pend.add(cPadreH);
        if(!pend.contains(cPadreV))pend.add(cPadreV);
    }

    private static void casoCeroUno(Casilla cB, Casilla cPadreH, Casilla cPadreV) { // Padre horizontal ya tiene suma total
        // Obtener un posible valor random
        int posRelativaH = cB.getPosSecond() - cPadreH.getPosSecond();
        int posRelativaV = cB.getPosFirst() - cPadreV.getPosFirst();

        EnumSet<Candidatos> candi = cPadreH.getCandidatosHorizontal().get(posRelativaH);

        int posValor = (int) Math.floor(Math.random() * candi.size());

        Candidatos[] arrCandi =  (Candidatos[])candi.toArray(new Candidatos[candi.size()]);
        int valor = Candidatos.enumTOint(arrCandi[posValor]);
        cB.setNumeroElejido(valor);

        // Elegir una sumatotal para padre V
        ArrayList<Integer> combV = comb.getCombinacion(cPadreV.getNumCellVertical(), valor);
        int indiceSumaV = (int) Math.floor(Math.random() * combV.size());
        cPadreV.setSumaVertical(combV.get(indiceSumaV));

        //Agregar y filtrar Secuencias
        ArrayList<ArrayList<Integer>> secV = sec.getSecuencia(combV.get(indiceSumaV), cPadreV.getNumCellVertical());
        cPadreV.setSecuenciasCandidatasVertical(secV);

        filtrarConvergidosEnPadres(cPadreH, cPadreV, valor);
        calcularCandidatosBlancas(cPadreH, posRelativaH, false);
        calcularCandidatosBlancas(cPadreV, posRelativaV, true);

        if(!pend.contains(cPadreH))pend.add(cPadreH);
        if(!pend.contains(cPadreV))pend.add(cPadreV);
    }

    private static void casoCeroCero(Casilla cB, Casilla cPadreH, Casilla cPadreV) { // Ningun padre tiene suma total
        int posRelativaH = cB.getPosSecond() - cPadreH.getPosSecond();
        int posRelativaV = cB.getPosFirst() - cPadreV.getPosFirst();

        int valor = (int) Math.floor(Math.random() * 9. + 1.);
        cB.setNumeroElejido(valor);

        ArrayList<Integer> combH = comb.getCombinacion(cPadreH.getNumCellHorizontal(), valor);
        ArrayList<Integer> combV = comb.getCombinacion(cPadreV.getNumCellVertical(), valor);
        int indiceSumaH = (int) Math.floor(Math.random() * combH.size());
        int indiceSumaV = (int) Math.floor(Math.random() * combV.size());
        cPadreH.setSumaHorizontal(combH.get(indiceSumaH));
        cPadreV.setSumaVertical(combV.get(indiceSumaV));

        //Agregar y filtrar Secuencias
        ArrayList<ArrayList<Integer>> secH = sec.getSecuencia(combH.get(indiceSumaH), cPadreH.getNumCellHorizontal());
        ArrayList<ArrayList<Integer>> secV = sec.getSecuencia(combV.get(indiceSumaV), cPadreV.getNumCellVertical());

        cPadreH.setSecuenciasCandidatasHorizontal(secH);
        cPadreV.setSecuenciasCandidatasVertical(secV);
        
        filtrarConvergidosEnPadres(cPadreH, cPadreV, valor);
        calcularCandidatosBlancas(cPadreH, posRelativaH, false);
        calcularCandidatosBlancas(cPadreV, posRelativaV, true);

        if(!pend.contains(cPadreH))pend.add(cPadreH);
        if(!pend.contains(cPadreV))pend.add(cPadreV);
    }

    private static void calcularCandidatosBlancas(Casilla c, int posRelativa, boolean vertical) {
        if (vertical) {
            EnumSet<Candidatos> candidatos = EnumSet.noneOf(Candidatos.class);
            candidatos.addAll(c.getCandidatosVertical().get(posRelativa));
            candidatos.removeAll(c.getConvergenVertical());
            
            for (int i = 1; i <= c.getNumCellVertical(); i++ ) {
                c.getCandidatosVertical().get(i).retainAll(candidatos);
            }
        }
        else {
            EnumSet<Candidatos> candidatos = EnumSet.noneOf(Candidatos.class);
            candidatos.addAll(c.getCandidatosHorizontal().get(posRelativa));
            candidatos.removeAll(c.getConvergenHorizontal());
            
            for (int i = 1; i <= c.getNumCellHorizontal(); i++ ) {
                c.getCandidatosHorizontal().get(i).retainAll(candidatos);
            }
        }
    }
    
    
    private static EnumSet<Candidatos> calcularCandidatos(Casilla padreHorizontal, Casilla padreVertical, int posRelativaH, int posRelativaV) {
        EnumSet<Candidatos> candidatos = EnumSet.noneOf(Candidatos.class);
        candidatos.addAll(padreVertical.getCandidatosVertical().get(posRelativaV));
        candidatos.retainAll(padreHorizontal.getCandidatosHorizontal().get(posRelativaH)); //interseccion VH
        candidatos.removeAll(padreVertical.getConvergenVertical());
        candidatos.removeAll(padreHorizontal.getConvergenHorizontal()); //resta de la union VH
        return candidatos;
    }

    private static void filtrarConvergidosEnPadres(Casilla cPadreH, Casilla cPadreV, int valor){
        cPadreH.setConvergenHAdd(Candidatos.intTOenum(valor));
        cPadreH.filtroDebeContenerHorizontal(valor);

        cPadreV.setConvergenVAdd(Candidatos.intTOenum(valor));
        cPadreV.filtroDebeContenerVertical(valor);
    }

    private static Casilla buscarSiguienteCasillaBlanca(Kakuro k, int posV, int posH, boolean vertical) { // retora nulo si no quedan mas casillas
        boolean encontrado = false;
        Casilla cB = null;
        Casilla cBR = null;
        Casilla cN = k.getCasillaKakuro(posV, posH);
        
        if (vertical) {
            int numCellV = cN.getNumCellVertical();
            for(int i = 1; i<=numCellV && !encontrado; i++){
                cB = k.getCasillaKakuro(cN.getPosFirst()+i, cN.getPosSecond());
                encontrado = (cB.getNumElejido() == -1);  
                if (encontrado) cBR = cB;   
            }
        }
        else {
            int numCellH = cN.getNumCellHorizontal();
            for(int i = 1; i<=numCellH && !encontrado; i++){
                cB = k.getCasillaKakuro(cN.getPosFirst(), cN.getPosSecond()+i);
                encontrado = (cB.getNumElejido() == -1);
                if (encontrado) cBR = cB;
            }
        }
        return cBR;
    }

    private static boolean serNegraSumaVertical(Kakuro k, int posV, int posH) {
        if ((posV+1 >=  k.getAltura()) || k.esNegra(posV+1, posH)) return false;
        return true;
    }

    private static boolean serNegraSumaHorizontal(Kakuro k, int posV, int posH) {
        if ((posH+1 >=   k.getAnchura()) || k.esNegra(posV, posH+1)) return false;
        return true;
    }

    private static void crearTablero(Kakuro k){
        for(int i = 0; i < k.getAltura(); i++){
            for(int j = 0; j < k.getAnchura(); j++){
                Pair pos = new Pair(i, j);
                if(i == 0 || j == 0){ 
                    CasillaNegra cN = new CasillaNegra(pos); 
                    k.setCasillaKakuro(i, j, cN);
                }
                else{
                    CasillaBlanca cB = new CasillaBlanca(pos); 
                    k.setCasillaKakuro(i, j, cB);
                }
            }
        }
    }

    private static int numCasBlancas(Kakuro k, int posVertical, int posHorizontal, boolean vertical) { // contar numero de casillas blancas                                
        boolean encontrado = false;                                                                                 //  que tiene una negra
        int contador = 1;
        if (vertical) {
            while (contador+posVertical < k.getAltura() && ! encontrado) {
                encontrado = k.esNegra(posVertical+contador, posHorizontal);
                if (! encontrado) contador++;
            }
        }
        else {
            while (contador+posHorizontal < k.getAnchura() && ! encontrado) {
                encontrado = k.esNegra(posVertical, posHorizontal+contador);
                if (! encontrado) contador++;
            }
        }
        return contador-1;
    }

    private static void initCasillas(Kakuro k) { 

        for (int i = 0; i < k.getAltura(); i++){
            for (int j = 0; j < k.getAnchura(); j++){
                Casilla c = k.getCasillaKakuro(i, j);
               if (c instanceof CasillaNegra) {
                    calcularPadredeCasillaBlanca(k,i,j);           
                    if (serNegraSumaVertical(k,i,j)) {
                        int num = numCasBlancas(k, i, j, true); // Cuantas casillas blancas tiene la negra
                        c.setNumCellVertical(num); // Numero de casillas blancas asociada a una casilla negra
                        c.setCandidatosV(initCandidatosHijos(c,true));
                    }
                    if (serNegraSumaHorizontal(k,i,j)) {
                        int num = numCasBlancas(k, i, j, false); // Cuantas casillas blancas tiene la negra
                        c.setNumCellHorizontal(num); // Numero de casillas blancas asociada a una casilla negra
                        c.setCandidatosH(initCandidatosHijos(c,false));
                    }
               } 
            }
        }
    }

    private static  ArrayList<EnumSet<Candidatos>> initCandidatosHijos(Casilla c, boolean vertical) {
        ArrayList<EnumSet<Candidatos>> candi = new ArrayList<EnumSet<Candidatos>>();
        int numCell;
        if (vertical) numCell = c.getNumCellVertical();
        else numCell = c.getNumCellHorizontal();
        for (int i = 0; i <= numCell; i++) {
            candi.add(EnumSet.allOf(Candidatos.class));
        }
        return candi;
    }
    
    private static void calcularPadredeCasillaBlanca(Kakuro k, int posVertical, int posHorizontal) {  // Calcula que padres tiene una casilla blanca
        int contador = 1;
        while (contador+posVertical < k.getAltura() && !(k.getCasillaKakuro(posVertical+contador, posHorizontal) instanceof CasillaNegra)) {
            k.getCasillaKakuro(posVertical+contador, posHorizontal).setCasillaNegraV(posVertical, posHorizontal);
            contador++;
        }
        contador = 1;
        while (contador+posHorizontal < k.getAnchura() && !(k.getCasillaKakuro(posVertical, posHorizontal+contador) instanceof CasillaNegra)) {
            k.getCasillaKakuro(posVertical, posHorizontal+contador).setCasillaNegraH(posVertical, posHorizontal);
            contador++;
        }
    }
}
