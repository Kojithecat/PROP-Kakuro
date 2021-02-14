package casilla;

import pair.*;
import java.util.Scanner;

import java.util.EnumSet;
import java.util.Iterator;
import candidatos.Candidatos;


public class DriverCasillaBlanca {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        System.out.println("");
        opcionesDriver();
        String input = cin.nextLine();
        while(!input.equals("s")) {
            if (input.equals("PosCB")) {
                System.out.println("Escribe la primera pos de Casillas blanca");
                String posX = cin.nextLine();
                int posXInt = Integer.parseInt(posX);
                System.out.println("Escribe la primera pos de Casillas blanca");
                String posY = cin.nextLine();
                int posYInt = Integer.parseInt(posY);
                CasillaBlanca cB = new CasillaBlanca(new Pair(posXInt, posYInt));
                System.out.println("Casilla Blanca Primera Posicion " + cB.getPosFirst());
                System.out.println("Casilla Blanca Segunda Posicion " + cB.getPosSecond());
            }
            else if (input.equals("CEsMod")) {
                System.out.println("Escribe numero elejido de la Casilla blanca");
                String num = cin.nextLine();
                int numInt = Integer.parseInt(num);
                System.out.println("¿Es modificable? 0-No 1-Si");
                String esMod = cin.nextLine();
                int esModInt = Integer.parseInt(esMod);
                boolean esModBoolean = esModInt == 1;
                CasillaBlanca cB = new CasillaBlanca(new Pair(), numInt, esModBoolean);
                System.out.println("Escribe otro numero elejido");
                String numMod = cin.nextLine();
                int numModInt = Integer.parseInt(numMod);
                cB.setNumeroElejido(numModInt);
                System.out.println("Numero Modificado " + cB.getNumElejido());
            }
            else if (input.equals("CArr")) {
                CasillaBlanca cB = new CasillaBlanca(new Pair());
                System.out.println("Escribe Candidatos uno a uno. Para terminar de escribir -1");
                EnumSet<Candidatos> posibles =EnumSet.noneOf(Candidatos.class);
                String num = cin.nextLine();
                int numInt = Integer.parseInt(num);
                while (numInt != -1) {
                    if (numInt >= 1 && numInt <= 9) {
                        posibles.add(Candidatos.intTOenum(numInt));
                        Iterator<Candidatos> iter = posibles.iterator();
                        System.out.print("Candidatos de casilla:");
                        while (iter.hasNext()) {
                            int x = Candidatos.enumTOint(iter.next());
                            System.out.print(" " + x );
                        }
                        System.out.println("");
                    }
                    System.out.println("Escribe otro. Para terminar de escribir -1");
                    num = cin.nextLine();
                    numInt = Integer.parseInt(num);
                }
                cB.setCandidatos(posibles);
                int[] res = cB.getCandidatosArray();
                System.out.print("Los candidatos guardados en casilla son:");
                for (int i : res) System.out.print(" " + i); 
                System.out.println("");
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
        System.out.println(" - Para posiciones de casilla --> PosCB");
        System.out.println(" - Para probar si una casilal es modificable --> CEsMod");
        System.out.println(" - Para probar getCandidatosArray --> CArr");
        System.out.println(" - Para salir --> s");
    }
}
