package partida;

import kakuro.*;
import usuario.*;
import repositorioPartidas.*;
import modo.*;

import java.time.*;
import java.util.*; 
import nivelAyuda.*;

public class Partida {
    //Atributos basicos de la clase Partida
    private int id;
    private LocalDateTime tiempoInicioSesion;
    private long tiempoTranscurrido;
   
    private LocalDateTime tiempoInicio;
    private boolean estaAcabada;
    private boolean rendido;
    private int puntuacion;
    //Relaciones de la clase Partida
    private NivelAyuda nivelAyuda;
    private Modo modoDeJuego;
    private Kakuro kakuroAJugar;
    private Kakuro checkPointKakuro;    // guarda el estado del kakuro en el momento en que se guarda
    private Kakuro kakuroSolucionado;
    private Usuario propietario;

    public Partida(Kakuro k, Usuario usuario, NivelAyuda ayuda, Modo m){
        tiempoInicio = LocalDateTime.now();
        tiempoInicioSesion = LocalDateTime.now();
        tiempoTranscurrido = 0;

        estaAcabada = false;
        rendido = false;
        modoDeJuego = m;
        puntuacion = 0;

        nivelAyuda = ayuda;
        kakuroSolucionado = k;
        kakuroAJugar = StubVaciaKakuro.vaciar(k);
        checkPointKakuro = kakuroAJugar;
        propietario = usuario;
    }

    public void setId(int identificador) {
        id = identificador;
    }
    
    public void setNivelAyuda(NivelAyuda ayuda){
        nivelAyuda = ayuda;
    }

    public void setModo(Modo modoJ){
        modoDeJuego = modoJ;
    } 

    public void setKakuro(Kakuro k){
        kakuroAJugar = k;
    }

    public void setUsuario(Usuario usuario){
        propietario = usuario;
    } 

    public int getId() {
        return id;
    }

    public Modo getModo(){
        return modoDeJuego;
    }

    public Kakuro getKakuro(){
        return kakuroAJugar;
    }

    public String getNivelAyuda() {
        return nivelAyuda.getTipoAyuda();
    }

    public Usuario getUsuario(){
        return propietario;
    } 

    public void cargarPartida() {
        if (! estaAcabada) {
            tiempoInicioSesion = LocalDateTime.now();
            checkPointKakuro = kakuroAJugar;
        }
        else System.out.println("La partida ya ha acabado");
    }

    public void acabarPartida(boolean rendido){
        estaAcabada = true;
        if (rendido) {
            System.out.println("jaja, te has rendido");
            this.rendido = true;
        }
        else System.out.println("Has completado la partida");
        tiempoTranscurrido += (long)(Duration.between(tiempoInicioSesion,LocalDateTime.now())).getSeconds();
    }

    public void guardarPartida(){
        LocalDateTime tiempoGuardado = LocalDateTime.now();
        tiempoTranscurrido += (long) (Duration.between(tiempoInicioSesion,tiempoGuardado)).getSeconds();
        System.out.println(tiempoTranscurrido);
    }

    public void establecerPuntuacion(){
        if(estaAcabada && ! rendido){
            //calcula la puntuación de esta partida en función del tiempo, nivel de ayuda......
            puntuacion = (int) Math.floor(formulaPuntuacion(modoDeJuego, nivelAyuda));

        }
        else if(!estaAcabada){ 
            System.out.println("Esta partida no esta terminada");
            puntuacion = 0;
        }
        else{
            System.out.println("Te has rendido, no tienes puntuación");
            puntuacion = 0;
        }
    }

    public int getPuntuacion() {
        establecerPuntuacion();
        return puntuacion;
    }

    public long getTiempoTranscurrido(){
        return tiempoTranscurrido;
    }

    public LocalDateTime getTiempoInicioSession(){
        return tiempoInicioSesion;
    }

    private double formulaPuntuacion(Modo m, NivelAyuda nV){
        return ((1000000.0 - (double) tiempoTranscurrido)/(m.getPenalizacionModo()))/nV.getPenalizacionAyuda();
    }   //Esta formula es un placeholder

}
