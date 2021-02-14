package capaDominio.controladoresDominio.controladores;

import java.util.ArrayList;

import capaDominio.kakuro.*;
import capaPersistencia.*;
import capaDominio.casilla.*;
import capaDominio.pair.*;
import java.util.HashMap;
import capaDominio.algoritmo.*;

public class ControladorKakuro {
    private static ControladorKakuro instanciaContro = null;
    private HashMap<Integer,Kakuro> repoKakuro = new HashMap<Integer,Kakuro>();

    private static class ControladorKakuroHelper {
        private static final ControladorKakuro instanciaContro = new ControladorKakuro();
    }

    
    /** 
     * @return ControladorKakuro
     */
    public static ControladorKakuro getInstance(){
        return ControladorKakuroHelper.instanciaContro;
    }

    
    /** 
     * @param kakuro
     * @return int
     */
    public int guardarKakuro(Kakuro kakuro) {
        ControladorPersistencia control = ControladorPersistencia.getInstance();

        ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>(1);    //crear funcion getInfo()
        info.add(new ArrayList<String>());
        info.get(0).add(Integer.toString(kakuro.getAltura()));
        info.get(0).add(Integer.toString(kakuro.getAnchura()));
        int ID = control.guardarInfoKakuro(kakuro.toArrayList(), info);
        if (ID > -1) {
            kakuro.setId(ID);
            repoKakuro.put(ID, kakuro);
        }
        else
            System.out.println("Algo fue terriblemente mal");
        return ID;
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerKakuro(int Id) {
        if (repoKakuro.containsKey(Id)) return repoKakuro.get(Id).toArrayList();
        ControladorPersistencia control = ControladorPersistencia.getInstance();
        ArrayList<ArrayList<String>> kakuroArray = control.leerInfoKakuro(Integer.toString(Id));
        Kakuro k = fromArrayList(kakuroArray);
        k.setId(Id);
        repoKakuro.put(Id,k);
        return kakuroArray;
    }

    
    /** 
     * @param pathCompleto
     * @return int
     */
    public int leerKakuroExterno(String pathCompleto) {
        ControladorPersistencia control = ControladorPersistencia.getInstance();

        String[] pathSplit = pathCompleto.split("/");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pathSplit.length-1; i++) {
            if (pathSplit.length-2 == i)sb.append(pathSplit[i]);
            else sb.append(pathSplit[i] + "/");
        }
           
        String path = sb.toString();
        String fichero = pathSplit[pathSplit.length-1];
        ArrayList<ArrayList<String>> kakuroArray = control.leerKakuroExterno(path, fichero);

        ArrayList<String> bitList = new ArrayList<String>(); 
        ArrayList<ArrayList<String>> kakopia = (ArrayList<ArrayList<String>>) kakuroArray.clone();
        kakopia.remove(0);
        int altura = kakopia.size();
        int anchura = kakopia.get(1).size();

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < anchura; j++) {
                String sl = kakopia.get(i).get(j);
                if (sl.equals("?"))
                    bitList.add("1");
                else
                    bitList.add("0");
            }
        }
        kakuroArray.add(1,bitList);
        Kakuro k = fromArrayList(kakuroArray);
        encontrarPadres(k);
        int Id;
        if (AlgoritmoResolucion.algoritmoValidacion(k) != 1) {
            Id = -2;
        }
        else {
            Id = guardarKakuro(k);
            k.setId(Id);
            repoKakuro.put(Id,k);
        }
        return Id;
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewKakuro() {
        ControladorPersistencia control = ControladorPersistencia.getInstance();
        return control.leerPreviewKakuros();
    }

    
    /** 
     * @param tam
     * @param numero_blancas
     * @param dificultadLoteria
     * @param patron
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearKakuro(ArrayList<String> tam, String numero_blancas, String dificultadLoteria, String patron) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>> ();
        if (tam.size() == 2) {
            int altura;
            int anchura;
            int numB;
            int dificultad;
            int patrol;
            try {
                altura = Integer.parseInt(tam.get(0));
                anchura = Integer.parseInt(tam.get(1));
                numB = Integer.parseInt(numero_blancas);
                dificultad = Integer.parseInt(dificultadLoteria);
                patrol = Integer.parseInt(patron);
                Kakuro k = AlgoritmoCreacionSimple.crearKakuro(altura, anchura, numB, dificultad, patrol);
                if (k.getAltura() != 0)
                    guardarKakuro(k);
                res = k.toArrayList();
            }
            catch (NumberFormatException e){ 
                e.getStackTrace();
            }
        }
        else {
            System.out.println("ArrayList de tamano diferente a 2");
        }
        return res;
    }

    
    /** 
     * @param arrayKakuro
     * @return Kakuro
     */
    public static Kakuro fromArrayList(ArrayList<ArrayList<String>> arrayKakuro) {

        ArrayList<ArrayList<String>> kakopia = (ArrayList<ArrayList<String>>) arrayKakuro.clone();
        kakopia.remove(0); //eliminar fila size
        ArrayList<String> mod = kakopia.get(0);
        kakopia.remove(0); //eliminar fila modificables
        int altura = kakopia.size();
        int anchura;
        if (altura == 0)
            anchura = 0;
        else 
            anchura = kakopia.get(1).size();
        
        Kakuro kakuro = new Kakuro(altura,anchura);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < anchura; j++) {
                String sl = kakopia.get(i).get(j);
                if (sl.equals("?")) kakuro.setCasillaKakuro(i, j, new CasillaBlanca(new Pair(i,j)));
                else if (sl.equals("*")) kakuro.setCasillaKakuro(i, j,  new CasillaNegra(new Pair(i,j)));
                else if (sl.contains("C") && sl.contains("F")) {
                    String[] sumas = sl.split("F");
                    int totalH = Integer.parseInt(sumas[1]);
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = Integer.parseInt(sumas[0].substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = numCasBlancas(kakopia, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = numCasBlancas(kakopia, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (sl.contains("C")) {
                    int totalH = 0;
                    int totalV = Integer.parseInt(sl.substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = 0;
                    int cantidadBlancasV = numCasBlancas(kakopia, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (sl.contains("F")) {
                    int totalH = Integer.parseInt(sl.substring(1));
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = 0;       //  Omite la C (el PRIMER caracter)
                    int cantidadBlancasH = numCasBlancas(kakopia, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = 0;
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else {
                    int nCasilla = Integer.parseInt(sl);
                    if (nCasilla > 9 || nCasilla < 1) System.out.println("Algo fue terriblemente mal");
                    else {
                        CasillaBlanca CB = new CasillaBlanca(new Pair(i,j), nCasilla, true);
                        if (mod.get(i*anchura+j).equals("0")) CB.setModificable(false);
                        kakuro.setCasillaKakuro(i, j, CB);
                    }
                }
            }
        }
        return kakuro;
    }

    
    /** 
     * @param vertical
     * @return int
     */
    private static int numCasBlancas(ArrayList<ArrayList<String>> tablero, int posVertical, int posHorizontal, boolean vertical) { // contar numero de casillas blancas                                
        boolean encontrado = false;                                                                                 //  que tiene una negra
        int contador = 1;
        if (vertical) {
            while (contador+posVertical < tablero.size() && ! encontrado) {
                encontrado = esNegra(tablero, posVertical+contador, posHorizontal);
                if (! encontrado) contador++;
            }
        }
        else {
            while (contador+posHorizontal < tablero.get(1).size() && ! encontrado) {
                encontrado = esNegra(tablero, posVertical, posHorizontal+contador);
                if (! encontrado) contador++;
            }
        }
        return contador-1;
    }

    
    /** 
     * @param tablero
     * @param posV
     * @param posH
     * @return boolean
     */
    private static boolean esNegra(ArrayList<ArrayList<String>> tablero, int posV, int posH){
        if (tablero.get(posV).get(posH).contains("C")) return true;
        if (tablero.get(posV).get(posH).contains("F")) return true;
        if (tablero.get(posV).get(posH).contains("*")) return true;
        return false;
    }

    
    /** 
     * @param k
     */
    public static void encontrarPadres(Kakuro k) {
        for (int i = 0; i < k.getAltura(); i++){
            for (int j = 0; j < k.getAnchura(); j++){
                Casilla c = k.getCasillaKakuro(i, j);
               if (c instanceof CasillaNegra) {
                    encontrarHijos(k,i,j);
               } 
            }
        }
    }

    
    /** 
     * @param k
     * @param posVertical
     * @param posHorizontal
     */
    private static void encontrarHijos(Kakuro k, int posVertical, int posHorizontal) {
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
