package casilla;

import pair.*;
import java.util.Scanner;



public class DriverCasillaNegra {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        System.out.println("");
        opcionesDriver();
        String input = cin.nextLine();
        while(!input.equals("s")) {
            if (input.equals("PosCN")) {
                System.out.println("Escribe la primera pos de la Casilla negra");
                String posX = cin.nextLine();
                int posXInt = Integer.parseInt(posX);
                System.out.println("Escribe la primera pos de la Casilla negra");
                String posY = cin.nextLine();
                int posYInt = Integer.parseInt(posY);
                CasillaNegra cN = new CasillaNegra(new Pair(posXInt, posYInt));
                System.out.println("Casilla Negra Primera Posicion " + cN.getPosFirst());
                System.out.println("Casilla Negra Segunda Posicion " + cN.getPosSecond());
            }
            else if (input.equals("CSum")) {
                CasillaNegra cN = new CasillaNegra(new Pair(1,1));
                System.out.println("Escribe la suma horizontal de la casilla");
                String num = cin.nextLine();
                int numInt = Integer.parseInt(num);
                cN.setSumaHorizontal(numInt);
                System.out.println("Escribe la suma vertical de la casilla");
                num = cin.nextLine();
                numInt = Integer.parseInt(num);
                cN.setSumaVertical(numInt);
                System.out.println("Esta casilla tiene suma horizontal: " + cN.getSumaHorizontal());
                System.out.println("Esta casilla tiene suma vertical: " + cN.getSumaVertical());
            }
           
            System.out.println("");
            opcionesDriver();
            input = cin.nextLine();
        }
        cin.close();
    }

    private static void opcionesDriver() {
        System.out.println("");
        System.out.println("¿Que quieres probar?");
        System.out.println(" - Para posiciones de casilla --> PosCN");
        System.out.println(" - Para ver las sumas de una casilla --> CSum");
        System.out.println(" - Para salir --> s");
    }
}
