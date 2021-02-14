package algoritmo;

import java.util.Scanner;

import kakuro.*;

public class DriverAlgoritmoCreacionSimple {
    public static Scanner cin = new Scanner(System.in);
    public static void main(String[] args) {
        int altura;
        int anchura;
        int patron;
        System.out.println("Que dimensiones quieres que tenga el kakuro:");
        System.out.print("Altura: ");
        altura = cin.nextInt();
        while(altura < 1){
            System.out.print("kakuro no puede tener dimensiones menores que 1");
            System.out.print("Altura: ");
            altura = cin.nextInt();
        }
        System.out.print("Anchura: ");
        anchura = cin.nextInt();
        while(anchura < 1){
            System.out.print("kakuro no puede tener dimensiones menores que 1");
            System.out.print("Anchura: ");
            anchura = cin.nextInt();
        }
        System.out.print("Patron de kakuro (1 para patron L / 2 para patron escalera): ");
        patron = cin.nextInt();
        while(patron != 1 && patron != 2){
            System.out.print("No hay patron con ese numero");
            System.out.print("Patron de kakuro (1 para patron L / 2 para patron escalera): ");
            patron = cin.nextInt();
        }

        Kakuro k = AlgoritmoCreacionSimple.crearKakuro(altura, anchura, patron);
        printKakuro(k);
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