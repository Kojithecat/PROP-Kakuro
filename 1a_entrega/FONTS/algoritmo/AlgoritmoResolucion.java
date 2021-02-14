package algoritmo;

import casilla.*;
import kakuro.*;
import java.util.*;
import pair.*;
import candidatos.Candidatos;
import secuencia.*;

public class AlgoritmoResolucion {

    //////////////////////////// 
    //       Validacion       //
    ////////////////////////////
    private static Set<Casilla> pendientes = new HashSet<Casilla>(); //Set de que casillas negras no han "propagado" informacion nueva
    private static Stack<Casilla> prioridad = new Stack<>(); //Stack de casillas negras que tienen que volverse a analizar
    private static Secuencia sec = new Secuencia();
    
    private static void algortimoValidacionInicializacion(Kakuro k) {
        //Inicializacion nueva de atributos de classe para casillas y inicializacion de pendientes y prioridad
        //Inicializacion de casillas negras casillas negras
        ArrayList<CasillaNegra> arrayNegras = k.getCasillasNegras();
        Iterator<CasillaNegra> it_cN = arrayNegras.iterator();
        while (it_cN.hasNext()){ //todas las casillas negras no vacias
            CasillaNegra cN = it_cN.next();

            ArrayList<ArrayList<Integer>> secuenciasCanV = new ArrayList<ArrayList<Integer>> (); //o no tienen secuencias candidatas
            if (cN.getSumaVertical() >= 3 && cN.getNumCellVertical() >= 2) {
                secuenciasCanV = sec.getSecuencia(cN.getSumaVertical(), cN.getNumCellVertical()); //o tiene secuencias correspondientes a su suma y numero de casillas
            }
            cN.setSecuenciasCandidatasVertical(secuenciasCanV); //assignacion de las nuevas sequencias
            cN.setConvergenV(EnumSet.noneOf(Candidatos.class)); //las casillas negras empiezan por no tener numeros "ya resueltos"

            ArrayList<ArrayList<Integer>> secuenciasCanH = new ArrayList<ArrayList<Integer>> (); //mismo proceso para las secuencias horizontales
            if (cN.getSumaHorizontal() >= 3 && cN.getNumCellHorizontal() >= 2) {
                secuenciasCanH =  sec.getSecuencia(cN.getSumaHorizontal(), cN.getNumCellHorizontal());
            }
            cN.setSecuenciasCandidatasHorizontal(secuenciasCanH);
            cN.setConvergenH(EnumSet.noneOf(Candidatos.class));
            //casillas negras unitarias? hemos puesto que no hay
        }
        //Encontrar casillas ya Convergidas y añadirlas a los padres Casilla Negra
        for (int i = 0; i < k.getAltura(); i++) {
            for (int j = 0; j < k.getAnchura(); j++) {
                Casilla c = k.getCasillaKakuro(i, j);
                if (c instanceof CasillaBlanca) { //por cada casilla balnca del tablero (que tenemos que buscar ierativamente, eventualmente se mejorara)
                    Pair padreVerticalCoord = c.getCasillaNegraV(); //encuentra sus padres
                    Casilla padreVertical = k.getCasillaKakuro(padreVerticalCoord.getFirst(), padreVerticalCoord.getSecond());
                    Pair padreHorizontalCoord = c.getCasillaNegraH(); //padre horizontal
                    Casilla padreHorizontal = k.getCasillaKakuro(padreHorizontalCoord.getFirst(), padreHorizontalCoord.getSecond());
                    if ( !(c.getModificable()) ) { //si la casilla no es modificable, tiene numero ya assignado
                        c.setNumeroSolucion(c.getNumElejido()); //la solucion es el elegido
                        padreVertical.setConvergenVAdd(Candidatos.intTOenum(c.getNumElejido()));  //sus padres debgen añadir ese numero a los "ya resueltos"
                        padreHorizontal.setConvergenHAdd(Candidatos.intTOenum(c.getNumElejido()));
                        EnumSet<Candidatos> x = EnumSet.noneOf(Candidatos.class);
                        x.add(Candidatos.intTOenum(c.getNumElejido())); //el unico candidato es el numero elegido
                        c.setCandidatos(x);
                    } else { //si la casilla blanca viene como modificable, ignoramos su contenido
                        c.setNumeroSolucion(-1); //la solucion para esa casilla blanca es desconocida
                        c.setCandidatosAddAll(); //tiene por candidatos todos
                    }
                }
            }
        }
        //Recorrer todas las Casillas negras para calular las Secuencias posibles inicialmente (quizas redundante, quizas se elimina en versiones posteriores)
        Iterator<CasillaNegra> it2_cN = arrayNegras.iterator();
        while (it2_cN.hasNext()){
            CasillaNegra cN = it2_cN.next();
            EnumSet<Candidatos> conV = cN.getConvergenVertical();
            EnumSet<Candidatos> conH = cN.getConvergenHorizontal();
            Iterator<Candidatos> itConV = conV.iterator();
            Iterator<Candidatos> itConH = conH.iterator();
            while (itConV.hasNext()) {
                cN.filtroDebeContenerVertical(Candidatos.enumTOint(itConV.next()));
            }
            while (itConH.hasNext()) {
                cN.filtroDebeContenerHorizontal(Candidatos.enumTOint(itConH.next()));
            }
            backtrackingFiltroSecuencias(cN, k, true);
            backtrackingFiltroSecuencias(cN, k, false);
        }

        //Preparar la pila de trabajo por hacer
        pendientes.addAll(k.getCasillasNegras()); //todas las casillas negras tienen que considerarse como que no han propagado informacion
        prioridad.addAll(k.getCasillasNegras()); //todas las casillas negras estan "por revisar"

    }

    public static int algoritmoValidacion(Kakuro k) {
        int finished = 0; // 1 tiene una solucion / 2 tiene mas de una solucion / 3 no tiene solucion / 4 la solucion es incorrecta
        algortimoValidacionInicializacion(k); //inicializacion des de 0 a partir de los parametros "fijos" de las casillas
        while (!(pendientes.isEmpty()) && finished == 0) { //si el algoritmo tiene cosas pendientes y no ha llegado a una conclusion sobre si se puede resolver el kakuro
            Casilla cN = prioridad.peek();
            prioridad.pop();
            if (pendientes.contains(cN)) { //si el elemento pendiente no ha sido tratado anteriormente
                pendientes.remove(cN); //lo estamos tratando, y si todo va bien, acabaremos y ya no estara pendiente
                int posV = cN.getPosFirst();
                int posH = cN.getPosSecond();
                //a partir de la posicion del padre y el numero de celdas de sus tiras, visitamos todos sus hijos (casillas blancas)
                for (int i = 1; i <= cN.getNumCellVertical(); i++) { //la i es la posicion relativa respecto al padre [1 .. NumCell+1]
                    Casilla cB = k.getCasillaKakuro(posV+i, posH);
                    if (cB.getModificable() && cB.getNumeroSolucion() == -1) {
                        finished = comprobarCasillaBlanca(cB,k); //por cada casilla blanca hija actualizamos sus candidatos y comprobamos su estado
                    }
                }

                for (int i = 1; i <= cN.getNumCellHorizontal(); i++) {
                    Casilla cB = k.getCasillaKakuro(posV, posH+i);
                    if (cB.getModificable() && cB.getNumeroSolucion() == -1) {
                        finished = comprobarCasillaBlanca(cB,k);
                    }
                }
                //tras visitar todas las casillas blancas, revisamos que secuencias siguen siendo possibles/validas
                boolean cambiosVertical = false;
                boolean cambiosHorizontal = false;
                if (cN.getSumaVertical() > 0) {
                    cambiosVertical = backtrackingFiltroSecuencias(cN, k, true); //ha cambiado algo en las cadenas verticales?
                }
                if (cN.getSumaHorizontal() > 0) {
                    cambiosHorizontal = backtrackingFiltroSecuencias(cN, k, false); //ha cambiado algo en las cadenas verticales?
                }
                if (cambiosVertical || cambiosHorizontal){ //si algo a cambiado, deberemos volver a revisar las casillas blancas anteriores, re empilamos el padre negro
                    pendientes.add(cN);
                    prioridad.add(cN);
                }
            }
        }
        Pair temp = new Pair(-1,-1);
        if (finished != 3) finished = comprobarSolucion(k,temp); //(esta resuelto bien o tiene multiple solucion) xor (no tiene solucion)
        return finished;
    }

    private static int comprobarCasillaBlanca (Casilla cB, Kakuro k) { //dada una casilla blanca, miramos que candidatos le corresponden
        int finished = 0; //retornaremos si la casilla colapsa, hemos decidido darle una variable por si surgian mas estados aparte del 3
        Pair padreVerticalCoord = cB.getCasillaNegraV(); //encontramos sus padres
        Casilla padreVertical = k.getCasillaKakuro(padreVerticalCoord.getFirst(), padreVerticalCoord.getSecond());
        Pair padreHorizontalCoord = cB.getCasillaNegraH();
        Casilla padreHorizontal = k.getCasillaKakuro(padreHorizontalCoord.getFirst(), padreHorizontalCoord.getSecond());
        int posRelativaV = cB.getPosFirst() - padreVertical.getPosFirst(); //calculamos la posicion relativa de la casilla blanca
        int posRelativaH = cB.getPosSecond() - padreHorizontal.getPosSecond(); //podriamos reutilizar el parametro i del for que llama esta funcion, queda por mejorar
        EnumSet<Candidatos> newCandidatos = calcularCandidatos(padreVertical, padreHorizontal, posRelativaV, posRelativaH); //llamamos a la funcion para calcular los nuevos candidatos de la casilla
        if ( !(newCandidatos.equals(cB.getCandidatosSet()) )  ) { //si ha cambiado los candidatos, hemos "descubierto nueva informacion"
            pendientes.add(padreVertical); //esta nueva informacion hay que propagarla a los padres
            prioridad.add(padreVertical);
            pendientes.add(padreHorizontal);
            prioridad.add(padreHorizontal);
        }
        cB.setCandidatos(newCandidatos);
        if (cB.getCandidatosSize() == 1) { //caso base de si solo tienes un candidato
            int[] candCB = cB.getCandidatosArray();
            int convergencia = candCB[0];
            cB.setNumeroSolucion(convergencia); //entonces ese candidato es el numero solucion de la casilla

            padreVertical.setConvergenVAdd(Candidatos.intTOenum(convergencia)); //ese numero solucion debe ponerse en el set de "enconrados" del padre
            padreVertical.filtroDebeContenerVertical(convergencia); //al encontrar un numero solucion, podemos inmediatamente ejecutar un "filtro" en tiempo polinomico
            //evaluando si las cadenas candidatas contienen el numero solucion encontrado
            //no pasa nada si esto crea nueva informacion porque ya hemos empilado los padres al actualizar newCandidatos
            padreHorizontal.setConvergenHAdd(Candidatos.intTOenum(convergencia));
            padreHorizontal.filtroDebeContenerHorizontal(convergencia);
        } else if (cB.getCandidatosSize() == 0){
            finished = 3; //si no tiene candidatos, el kakuro no tiene solucion
        }
        return finished; //como se aprecia, se podria usar return y eliminar la variable loca finished, queda por limpiar
    }

    
    private static EnumSet<Candidatos> calcularCandidatos(Casilla padreVertical, Casilla padreHorizontal, int posRelativaV, int posRelativaH) {
        //calculo de los candidatos nuevos de una casilla blanca
        EnumSet<Candidatos> candidatos = EnumSet.noneOf(Candidatos.class);
        candidatos.addAll(padreVertical.getCandidatosVertical().get(posRelativaV));
        candidatos.retainAll(padreHorizontal.getCandidatosHorizontal().get(posRelativaH)); //interseccion de candidatos del padre V con padre H
        candidatos.removeAll(padreVertical.getConvergenVertical()); //resta de los numeros que ya han aparecido vericalmente
        candidatos.removeAll(padreHorizontal.getConvergenHorizontal()); //resta de los numeros que ya han aparecido horizontalmente
        return candidatos;
    }


    private static ArrayList<EnumSet<Candidatos>> backtrackingFiltroImmersion (Kakuro k, ArrayList<Integer> secuencia, int posV, int posH, int i, int numCell, boolean[] NumerosVisitados, int[] permutacion, boolean vertical) {
        ArrayList<EnumSet<Candidatos>> partialCandidatos = new ArrayList<EnumSet<Candidatos>> (numCell+1);
        if (i > numCell) {
            //la permutacion se ha generado completa y correctamente
            //añade a partial candidatos los candidatos de esta cadena que ha resultado bien
            partialCandidatos.add(EnumSet.noneOf(Candidatos.class));
            for (int x = 1; x <= numCell; x++) {
                partialCandidatos.add(x, EnumSet.of(Candidatos.intTOenum(permutacion[x])));
            }
            return partialCandidatos; //caso base
        }
        Casilla cb;
        ArrayList<EnumSet<Candidatos>> resultCandidatos = new ArrayList<EnumSet<Candidatos>> (numCell+1);
        for (int x = 0; x <= numCell; x++) {
            resultCandidatos.add(EnumSet.noneOf(Candidatos.class));
        }
        if (vertical) cb = k.getCasillaKakuro(posV+i, posH);
        else cb = k.getCasillaKakuro(posV, posH+i);
        for (int j = 0; j < secuencia.size(); j++) { //por cada casilla intentas todas los numeros de la cadena possibles
            if (!(NumerosVisitados[j])) {
                if (cb.getNumeroSolucion() == secuencia.get(j)) {
                    NumerosVisitados[j] = true; //has visitado el num j
                    permutacion[i] = secuencia.get(j); //visitas el num j en la pos i
                    partialCandidatos = backtrackingFiltroImmersion (k, secuencia, posV, posH, i+1, numCell, NumerosVisitados, permutacion, vertical);
                    juntarCandidatos(numCell, resultCandidatos, partialCandidatos);
                    permutacion[i] = -1;
                    NumerosVisitados[j] = false;
                } else if (cb.getCandidatosContains(Candidatos.intTOenum(secuencia.get(j))) && cb.getNumeroSolucion() == -1) {
                    NumerosVisitados[j] = true;
                    permutacion[i] = secuencia.get(j);
                    partialCandidatos = backtrackingFiltroImmersion (k, secuencia, posV, posH, i+1, numCell, NumerosVisitados, permutacion, vertical);
                    juntarCandidatos(numCell, resultCandidatos, partialCandidatos);
                    permutacion[i] = -1;
                    NumerosVisitados[j] = false;
                }
            }
        }
        return resultCandidatos;
    }

    private static void juntarCandidatos (int numCell, ArrayList<EnumSet<Candidatos>> resultCandidatos, ArrayList<EnumSet<Candidatos>> partialCandidatos) {
        //fusiona uno a uno los elementos de ambos array de candidatos
        for (int x = 1; x <= numCell; x++) {
            resultCandidatos.get(x).addAll(partialCandidatos.get(x));
        }
    }

    private static boolean candidatosArrayListEmpty (int numCell, ArrayList<EnumSet<Candidatos>> partialCandidatos) {
        //devuelve si alguno de los elementos del array de candidatos esta vacio
        for (int x = 1; x <= numCell; x++) {
            if (partialCandidatos.get(x).isEmpty()) return true;
        }
        return false;
    }

    private static boolean backtrackingFiltroSecuencias(Casilla c, Kakuro k, boolean vertical) {
        //se encarga de hacer todas las deducciones y filtrados de secuencias de una casilla negra
        boolean changes = false; //devuelve si se ha quitado alguna de las cadenas
        int numCell;
        ArrayList<ArrayList<Integer>> secuencias_por_revisar;
        if (vertical) {
            numCell = c.getNumCellVertical();
            secuencias_por_revisar = c.getSecuenciasCandidatasVertical();
        } else {
            numCell = c.getNumCellHorizontal();
            secuencias_por_revisar = c.getSecuenciasCandidatasHorizontal();
        }
        Iterator<ArrayList<Integer>> it = secuencias_por_revisar.iterator();
        ArrayList<EnumSet<Candidatos>> newCandidatos = new ArrayList<EnumSet<Candidatos>>(); //empieza vacio
        for (int x = 0; x <= numCell; x++) {
            newCandidatos.add(EnumSet.noneOf(Candidatos.class));
        }
        //queda por hacer los filtros polinomicos de presencia y ausencia de numeros o casillas en versiones porsteriores del algoritmo
        //estos filtrosreducirian los casos mejores porque evitan intentar encontrar permutaciones de secuencias que es impossible que tengan
        while (it.hasNext()) {
            ArrayList<Integer> secuencia = it.next(); //por cada secuencia que era candidata
            //Array de candidatos generados a partir solo de la secuencia bajo analisis
            ArrayList<EnumSet<Candidatos>> partialCandidatos = new ArrayList<EnumSet<Candidatos>> ();
            //otra inicializacion vacia que tenemos que re escribir o encontrar una manera mas simple de hacer
            for (int x = 0; x <= numCell; x++) {
                partialCandidatos.add(EnumSet.noneOf(Candidatos.class));
            }
            boolean[] NumerosVisitados = new boolean[secuencia.size()]; //numeros de la secuencia ya visitados
            Arrays.fill(NumerosVisitados, false);
            int[] permutacion = new int[numCell+1]; //de 1 a numCell se asignaran las permutaciones
            Arrays.fill(permutacion, -1);
            partialCandidatos = backtrackingFiltroImmersion (k, secuencia, c.getPosFirst(), c.getPosSecond(), 1, numCell, NumerosVisitados, permutacion, vertical);
            if (candidatosArrayListEmpty(numCell, partialCandidatos)) { //si alguna de las posiciones no tiene candidatos, no hay permutacion valida
                it.remove();
            } else {
                //actualizar los candidatos añadiendo los candidatos de la cadena recien calculada
                juntarCandidatos(numCell, newCandidatos, partialCandidatos);
            }
        }
        //comprobamos si hemos ganado informacion/reducido los candidatos
        if (vertical) {
            if ( ! c.getCandidatosVertical().equals(newCandidatos)) { 
                changes = true; //hemos ganado informacion, hay que avisar para empilar
            }
            c.setCandidatosV(newCandidatos);
        }
        else  {
            if ( ! c.getCandidatosHorizontal().equals(newCandidatos)) {
                changes = true;
            }
            c.setCandidatosH(newCandidatos);
        }
        return changes;
    }


    public static int comprobarSolucion (Kakuro k, Pair casillaMultiple) {
        //comprueba si el kakuro esta bien resuelto, o no ha dado solucion (2), o ha dado solucion erronea (veredict error prevention)
        //si tiene multiple solucion, devuelve la primera casilla que encuentre con multiple solucion por parametro
        //ATENCION esta funcion asume que los rangos de NumCell son correctos, si no, puede dar out of bounds
        ArrayList<CasillaNegra> arrayNegras = k.getCasillasNegras();
        Iterator<CasillaNegra> itCN = arrayNegras.iterator();
        while (itCN.hasNext()){
            CasillaNegra cN = itCN.next();
            Integer posV = cN.getPosFirst();
            Integer posH = cN.getPosSecond();
            Integer sumTempV = 0;
            Integer sumTempH = 0;
            Integer suma; //Se usan para la comprobacion vertical y horizontal
            if (cN.getSumaVertical() > 0) {
                suma = cN.getSumaVertical();
                boolean[] repetido = new boolean[10];
                Arrays.fill(repetido, false);
                for(int i = 1; i <= cN.getNumCellVertical(); i++) {
                    Casilla cB = k.getCasillaKakuro(posV + i, posH);
                    Integer num = cB.getNumeroSolucion();
                    if (num == -1){
                        casillaMultiple.setFstSnd(cB.getPosFirst(), cB.getPosSecond());
                        return 2; //tiene multiples soluciones
                    }
                    else if (num <= 9 && num >= 1) sumTempV += num;
                    if (repetido[num]) return 4; //hay un numero repetido
                    else repetido[num] = true; //ponemos que hemos visitado ese numero
                }
                if (! suma.equals(sumTempV)) return 4; //la solucion no es correcta
                //else -> esa suma ha dado correctamente
            }
            if (cN.getSumaHorizontal() > 0) { //for simetrico horizontalmente, como todo en este algoritmo
                suma = cN.getSumaHorizontal();
                boolean[] repetido = new boolean[10];
                Arrays.fill(repetido, false);
                for(int i = 1; i <= cN.getNumCellHorizontal(); i++) {
                    Casilla cB = k.getCasillaKakuro(posV, posH + i);
                    Integer num = cB.getNumeroSolucion();
                    if (num == -1){
                        casillaMultiple.setFstSnd(cB.getPosFirst(), cB.getPosSecond());
                        return 2;
                    }
                    else if (num <= 9 && num >= 1) sumTempH += num;
                    if (repetido[num]) return 4;
                    else repetido[num] = true;
                }
                if (! suma.equals(sumTempH)) return 4;
            }
        }
        //si todas las sumas dan bien... -> todo bien
        return 1;
    }
}











