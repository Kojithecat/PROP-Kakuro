package pair;

import java.util.Scanner;

public class DriverPair {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        System.out.println("");
        opcionesDriver();
        String input = cin.nextLine();
        while(!input.equals("s")) {
            if (input.equals("cp")) {
                System.out.println("Escribe el primer número del Pair");
                String x = cin.nextLine();
                int xInt = Integer.parseInt(x);
                System.out.println("Escribe el segundo número del Pair");
                String y = cin.nextLine();
                int yInt = Integer.parseInt(y);
                Pair a = new Pair(xInt,yInt);
                System.out.println("Primer número del pair: " + a.getFirst() + " Segundo número del pair: " + a.getSecond());
            }
            else if (input.equals("rp1")) {
                System.out.println("Escribe el primer número del Pair");
                String x = cin.nextLine();
                int xInt = Integer.parseInt(x);
                System.out.println("Escribe el segundo número del Pair");
                String y = cin.nextLine();
                int yInt = Integer.parseInt(y);
                Pair a = new Pair(xInt,yInt);
                System.out.println("Primer número del pair: " + a.getFirst() + " Segundo número del pair: " + a.getSecond());

                System.out.println("Escribe otro número para el primer número del Pair");
                x = cin.nextLine();
                xInt = Integer.parseInt(x);
                System.out.println("Escribe otro número para el segundo número del Pair");
                y = cin.nextLine();
                yInt = Integer.parseInt(y);
                a.setFirst(xInt);
                a.setSecond(yInt);
                System.out.println("El pair se ha sobreescrito");
                System.out.println("Primer número del pair: " + a.getFirst() + " Segundo número del pair: " + a.getSecond());
            }
            else if (input.equals("rp2")) {
                System.out.println("Escribe el primer número del Pair");
                String x = cin.nextLine();
                int xInt = Integer.parseInt(x);
                System.out.println("Escribe el segundo número del Pair");
                String y = cin.nextLine();
                int yInt = Integer.parseInt(y);
                Pair a = new Pair(xInt,yInt);
                System.out.println("Primer número del pair: " + a.getFirst() + " Segundo número del pair: " + a.getSecond());

                System.out.println("Escribe otro número para el primer número del Pair");
                x = cin.nextLine();
                xInt = Integer.parseInt(x);
                System.out.println("Escribe otro número para el segundo número del Pair");
                y = cin.nextLine();
                yInt = Integer.parseInt(y);
                a.setFstSnd(xInt, yInt);
                System.out.println("El pair se ha sobreescrito");
                System.out.println("Primer número del pair: " + a.getFirst() + " Segundo número del pair: " + a.getSecond());
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
        System.out.println(" - Para comprobar numeros del pair --> cp");
        System.out.println(" - Para reescribir el número del pair --> rp1");
        System.out.println(" - Para reescribir el número del pair --> rp2");
        System.out.println(" - Para salir --> s");
    }
}
