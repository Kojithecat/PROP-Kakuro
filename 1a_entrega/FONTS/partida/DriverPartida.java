package partida;


import java.util.Scanner;
import kakuro.Kakuro;
import modo.ModoNormal;
import nivelAyuda.NivelAyuda;
import nivelAyuda.NivelAyudaMucho;
import usuario.Usuario;
import modo.*;

public class DriverPartida {
    public static Scanner cin = new Scanner(System.in); 
    public static void main(String[] args) {
        opcionesDriver();
        Partida p = null;
        String input = cin.nextLine();
        while(!input.equals("s")){
            switch(input){
                case "np":
                    Kakuro k = new Kakuro(5, 5);
                    NivelAyuda nA = new NivelAyudaMucho();
                    Usuario u = new Usuario("Elvira", "pswd") ;
                    Modo m = new ModoNormal();
                    p = new Partida(k, u, nA, m);
                    System.out.println("Partida Creada");
                    break;
                case "gp":
                    if(p != null){
                        p.guardarPartida();
                        System.out.println("Tiempo transcurrido: "+ p.getTiempoTranscurrido());//Esto ya no peta
                    }
                    else System.out.println("No has creado la partida");
                    break;
                case "cp":
                    if(p != null){
                        p.cargarPartida();

                        System.out.println("Tiempo inicio session: "+ p.getTiempoInicioSession());//Esto peta
                    }
                    else System.out.println("No has creado la partida");
                    break;
                case "ap":
                    if(p != null){
                        System.out.println("¿Te rindes (r) o has completado la partida (c)?");
                        boolean rendido = false;
                        input = cin.nextLine();
                        while(!input.equals("r") && !input.equals("c")){ 
                            System.out.println("¿Te rindes (r) o has completado la partida (c)?");
                            input = cin.nextLine();
                        }
                        if(input.equals("r")) rendido = true;
                        p.acabarPartida(rendido);
                        System.out.println("La puntuacion de la partida es: "+ p.getPuntuacion());
                    }
                    else System.out.println("No has creado la partida");
                    break;


                default: break;

            }
            opcionesDriver();
            input = cin.nextLine();
        } 
        cin.close();
    }
    private static void opcionesDriver() {
        System.out.println("");
        System.out.println("¿Que quieres probar?");
        System.out.println(" - Nueva Partida --> np");
        System.out.println(" - Guardar Partida --> gp");
        System.out.println(" - Cargar Partida --> cp"); 
        System.out.println(" - Acabar Partida --> ap");
        System.out.println(" - Salir --> s");
    }
}