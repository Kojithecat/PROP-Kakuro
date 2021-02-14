package algoritmo;

import kakuro.*;

public class DriverAlgoritmoCreacion {
    public static void main(String[] args) {
        Kakuro k = AlgoritmoCreacion.crearKakuro(4, 4);
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
