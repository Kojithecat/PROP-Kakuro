package ranking;

import java.util.Scanner;
import kakuro.*;
import modo.*;
import nivelAyuda.NivelAyuda;
import nivelAyuda.NivelAyudaMucho;
import nivelAyuda.NivelAyudaPoco;
import partida.*;
import usuario.Usuario;

public class DriverRanking {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        Scanner cin2 = new Scanner(System.in);
        Ranking r = new Ranking();

        System.out.println("");
        opcionesDriver();

        String input = cin.nextLine();
        while (!input.equals("s")) {
            if (input.equals("pRank")) {
                System.out.println("¿Quieres una partida con puntuación alta (alta) o puntuación baja (baja)?");
                String input2 = cin.nextLine();
                while(!input2.equals("alta") && !input2.equals("baja")){
                    System.out.println("Quieres una partida con puntuacion alta (alta) o puntuación baja (baja)");
                    input2 = cin.nextLine();
                }
                Partida p;
                if(input2.equals("alta")){
                    Kakuro k = new Kakuro(5,5);
                    Modo m = new ModoRanking();
                    NivelAyuda nV = new NivelAyudaPoco();
                    Usuario u = new Usuario("JP", "haskell");
                    p = new Partida(k,u,nV,m);     
                }
                else{
                    Kakuro k = new Kakuro(5,5);
                    Modo m = new ModoNormal();
                    NivelAyuda nV = new NivelAyudaMucho();
                    Usuario u = new Usuario("Coco", "Coco");
                    p = new Partida(k,u,nV,m);
                }
                p.acabarPartida(false);
                r.anadirPartida(p);
                System.out.println("Se ha añadido una partida con puntuación: " + p.getPuntuacion() + " al ranking");
                
            }
            else if(input.equals("bestP")){
                if(r.getSize() == 0) System.out.println("No hay partidas en ranking");
                else{
                    Partida part = r.getBest();
                    System.out.println("La partida con mejor puntuacion tiene " + part.getPuntuacion() + " puntos");
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
        System.out.println(" - Crear partida y meterla en el ranking --> pRank");
        System.out.println(" - Partida con mejor puntuación --> bestP");
        System.out.println(" - Salir --> s");
    }
}
