package algoritmo;

import java.util.Scanner;

import casilla.*;
import pair.Pair;
import read.*;
import kakuro.*;

public class DriverAlgoritmoResolucion {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Se necesita pasar un parametro");
            return;
        }

        
        Scanner cin = new Scanner(System.in);
        String[][] ficheroLeido = ReadFile.main(args[0]);

        Kakuro kakuro = testLeerKakuro(ficheroLeido);

        encontrarPadres(kakuro);
        int altura = kakuro.getAltura();
        int anchura = kakuro.getAnchura();

        System.out.println("Altura: " + altura + " Anchura: " + anchura);
        printKakuro(kakuro);

        System.out.println("");
        opcionesDriver();


        String input = cin.nextLine();
        while (!input.equals("s")) {
            if (input.equals("sk")) {
                System.out.println(AlgoritmoResolucion.algoritmoValidacion(kakuro));
                printKakuro(kakuro);
            }
            else if (input.equals("rc")) {
                escribirRelaciones(kakuro);
            }
            opcionesDriver();
            input = cin.nextLine();
        }
        cin.close();
    } 

    private static void opcionesDriver() {
        System.out.println("");
        System.out.println("¿Que quieres probar?");
        System.out.println(" - Para solucionar kakuro --> sk");
        System.out.println(" - Para escribir informacion de casillas --> rc");
        System.out.println(" - Para salir --> s");
    }


    public static Kakuro testLeerKakuro(String[][] lectura) {
        int altura = lectura.length;
        int anchura = lectura[0].length;
        Kakuro kakuro = new Kakuro(altura,anchura);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < anchura; j++) {
                if (lectura[i][j].equals("?")) kakuro.setCasillaKakuro(i, j, new CasillaBlanca(new Pair(i,j)));
                else if (lectura[i][j].equals("*")) kakuro.setCasillaKakuro(i, j,  new CasillaNegra(new Pair(i,j)));
                else if (lectura[i][j].contains("C") && lectura[i][j].contains("F")) {
                    String[] sumas = lectura[i][j].split("F");
                    int totalH = Integer.parseInt(sumas[1]);
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = Integer.parseInt(sumas[0].substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = numCasBlancas(lectura, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = numCasBlancas(lectura, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (lectura[i][j].contains("C")) {
                    int totalH = 0;
                    int totalV = Integer.parseInt(lectura[i][j].substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = 0;
                    int cantidadBlancasV = numCasBlancas(lectura, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (lectura[i][j].contains("F")) {
                    int totalH = Integer.parseInt(lectura[i][j].substring(1));
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = 0;       //  Omite la C (el PRIMER caracter)
                    int cantidadBlancasH = numCasBlancas(lectura, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = 0;
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else {
                    int nCasilla = Integer.parseInt(lectura[i][j]);
                    if (nCasilla > 9 || nCasilla < 1) System.out.println("Algo fue terriblemente mal");
                    kakuro.setCasillaKakuro(i, j,new CasillaBlanca(new Pair(i,j), nCasilla, false));
                }
            }
        }
        return kakuro;
    }

    private static int numCasBlancas(String[][] tablero, int posVertical, int posHorizontal, boolean vertical) { // contar numero de casillas blancas                                
        boolean encontrado = false;                                                                                 //  que tiene una negra
        int contador = 1;
        if (vertical) {
            while (contador+posVertical < tablero.length && ! encontrado) {
                encontrado = esNegra(tablero, posVertical+contador, posHorizontal);
                if (! encontrado) contador++;
            }
        }
        else {
            while (contador+posHorizontal < tablero[0].length && ! encontrado) {
                encontrado = esNegra(tablero, posVertical, posHorizontal+contador);
                if (! encontrado) contador++;
            }
        }
        return contador-1;
    }

    private static boolean esNegra(String[][] tablero, int posV, int posH){
        if (tablero[posV][posH].contains("C")) return true;
        if (tablero[posV][posH].contains("F")) return true;
        if (tablero[posV][posH].contains("*")) return true;
        return false;
    }

    private static void encontrarPadres(Kakuro k) {
        for (int i = 0; i < k.getAltura(); i++){
            for (int j = 0; j < k.getAnchura(); j++){
                Casilla c = k.getCasillaKakuro(i, j);
               if (c instanceof CasillaNegra) {
                    encontrarHijos(k,i,j);
               } 
            }
        }
    }

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

    public static void escribirRelaciones (Kakuro k){
        for (int i = 0; i < k.getAltura(); i++){
            for (int j = 0; j < k.getAnchura(); j++){
                System.out.println("Pos: " + i + " " + j);
                Casilla c = k.getCasillaKakuro(i, j);
                if (c instanceof CasillaBlanca) {
                    Pair aux = c.getCasillaNegraH();
                    System.out.println("\t Coordenadas del Padre Horizontal " + aux.getFirst() + " " + aux.getSecond());
                    aux = c.getCasillaNegraV();
                    System.out.println("\t Coordenadas del Padre Vertical " + aux.getFirst() + " " + aux.getSecond());
                }
                else {
                    System.out.println("\t Num de Hijos en Horizontal " + c.getNumCellHorizontal());
                    System.out.println("\t Num de Hijos en Vertical " + c.getNumCellVertical());
                }
            }
        }
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
}