package kakuro;

import java.util.Scanner;
import casilla.*;
import read.*;
import pair.*;
import java.util.ArrayList;

public class DriverKakuro {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        Scanner cin2 = new Scanner(System.in);


        System.out.println("");
        opcionesDriver();

        String input = cin.nextLine();
        String[][] tabla = ReadFile.main(args[0]);
        Kakuro k = testLeerKakuro(tabla);
        encontrarPadres(k);    
        while (!input.equals("s")) {
            if (input.equals("k")) {
                System.out.println("Altura: " + k.getAltura()+ " Anchura: " + k.getAnchura());
                printKakuro(k);
                //Kakuro k = new Kakuro(tablero.length, tablero[0].length);
            }
            else if(input.equals("cnk")){
                ArrayList<CasillaNegra> cns = k.getCasillasNegras();
                for(int i = 0; i< cns.size(); i++){
                    int p1 = cns.get(i).getPosFirst();
                    int p2 = cns.get(i).getPosSecond();

                    System.out.println("Hay una casilla negra en la posicion: ");
                    System.out.println(p1 + " " + p2);

                }

            }
            opcionesDriver();
            input = cin.nextLine();
        }
        cin.close();
        cin2.close();
    } 

    private static void opcionesDriver() {
        System.out.println("");
        System.out.println("¿Que quieres probar?");
        System.out.println(" - Para ver un kakuro --> k");
        System.out.println(" - Para ver la información de las casillas negras de un kakuro --> cnk");
        System.out.println(" - Para salir --> s");
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

}
