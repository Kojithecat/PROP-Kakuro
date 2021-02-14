package capaDominio.partida;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import capaDominio.kakuro.*;
import capaDominio.modo.*;
import capaDominio.nivelAyuda.*;


public class Partida {
    //Atributos basicos de la clase Partida
    private int id;
    private LocalDateTime tiempoInicioSesion;
    private float tiempoTranscurrido;

    private LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFinalizarPartida;
    private boolean estaAcabada;
    private boolean rendido;
    private int puntuacion;

    //Relaciones de la clase Partida
    private NivelAyuda nivelAyuda;
    private Modo modoDeJuego;
    private int kakuroAJugar;
    private Kakuro checkPointKakuro;    // guarda el estado del kakuro en el momento en que se guarda
    //private Kakuro kakuroSolucionado;
    private String propietario;


    public Partida(Kakuro k, String usuario, NivelAyuda ayuda, Modo m){
        id = -1;
        tiempoInicio = LocalDateTime.now();
        tiempoInicioSesion = tiempoInicio;
        tiempoTranscurrido = 0;
        tiempoFinalizarPartida = LocalDateTime.MIN;

        estaAcabada = false;
        rendido = false;
        modoDeJuego = m;
        puntuacion = 0;

        nivelAyuda = ayuda;
        kakuroAJugar = k.getId();

        checkPointKakuro = k;
        
        propietario = usuario;
    }    
    
    public Partida(int id, Kakuro k, String usuario, NivelAyuda ayuda, Modo m, LocalDateTime tInicio, LocalDateTime finPartida, float tiempoTrans,  
                    boolean esAcabada, boolean rendido, int puntuacion){
        
        this.id = id;
        tiempoInicio = tInicio;
        tiempoFinalizarPartida = finPartida;

        tiempoInicioSesion = LocalDateTime.MIN;
        tiempoTranscurrido = tiempoTrans;

        estaAcabada = esAcabada;
        this.rendido = rendido;
        modoDeJuego = m;
        this.puntuacion = puntuacion;

        nivelAyuda = ayuda;
       
        kakuroAJugar = k.getId();
    
        this.checkPointKakuro = k;

        propietario = usuario;
    }

    public void iniciarSesion() {
        tiempoInicioSesion = LocalDateTime.now();
    }

    
    /** 
     * @param tInicioSesion
     */
    public void acabarSesion(LocalDateTime tInicioSesion) {
        tiempoTranscurrido += (float) (Duration.between(tInicioSesion,LocalDateTime.now())).getSeconds();
    }

    
    /** 
     * @param identificador
     */
    public void setId(int identificador) {
        id = identificador;
    }
    
    
    /** 
     * @param ayuda
     */
    public void setNivelAyuda(NivelAyuda ayuda){
        nivelAyuda = ayuda;
    }

    
    /** 
     * @param modoJ
     */
    public void setModo(Modo modoJ){
        modoDeJuego = modoJ;
    } 

   /* public void setKakuro(Kakuro k){
        kakuroAJugar = k;
    }*/

    public void setUsuario(String usuario){
        propietario = usuario;
    } 

    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    
    /** 
     * @return Modo
     */
    public Modo getModo(){
        return modoDeJuego;
    }

    
    /** 
     * @return int
     */
    public int getKakuro(){
        return kakuroAJugar;
    }

    
    /** 
     * @return String
     */
    public String getNivelAyuda() {
        return nivelAyuda.getTipoAyuda();
    }

    
    /** 
     * @return String
     */
    public String getUsuario(){
        return propietario;
    } 

    
    /** 
     * @param tiempo
     * @return String
     */
    public String getTiempoString(LocalDateTime tiempo) {
        DateTimeFormatter formato = DateTimeFormatter.ISO_DATE_TIME;
        String formatoDiaTiempo = tiempo.format(formato);
        return formatoDiaTiempo;
    }

    
    /** 
     * @return Kakuro
     */
    public Kakuro getCheckPointKakuro() {
        return checkPointKakuro;
    }

  /*  public void cargarPartida() {
        if (! estaAcabada) {
            tiempoInicioSesion = LocalDateTime.now();
            checkPointKakuro = kakuroAJugar;
        }
        else System.out.println("La partida ya ha acabado");
    }*/

    public void acabarPartida(LocalDateTime tInicioSesion, boolean rendido){
        estaAcabada = true;
        if (rendido)   
            this.rendido = true;
        
        tiempoFinalizarPartida = LocalDateTime.now();
        acabarSesion(tInicioSesion);
        establecerPuntuacion();
    }

 /*   public void guardarPartida(){
        LocalDateTime tiempoGuardado = LocalDateTime.now();
        tiempoTranscurrido += (long) (Duration.between(tiempoInicioSesion,tiempoGuardado)).getSeconds();
        System.out.println(tiempoTranscurrido);
    }*/

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

    
    /** 
     * @return int
     */
    public int getPuntuacion() {
       // establecerPuntuacion();
        return puntuacion;
    }

    
    /** 
     * @return float
     */
    public float getTiempoTranscurrido(){
        return tiempoTranscurrido;
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getTiempoInicioSession(){
        return tiempoInicioSesion;
    }

    
    /** 
     * @param m
     * @param nV
     * @return double
     */
    private double formulaPuntuacion(Modo m, NivelAyuda nV){
        return ((1000000.0 - (double) tiempoTranscurrido)/(m.getPenalizacionModo()))/nV.getPenalizacionAyuda();
    }   //Esta formula es un placeholder

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> toArrayList() {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>(); 
        
        res.add(new ArrayList<String>());
        res.get(0).add(Integer.toString(id));
    
        res.add(new ArrayList<String>());
        res.get(1).add(propietario);

        res.add(new ArrayList<String>());
        res.get(2).add(getTiempoString(tiempoInicio));

        res.add(new ArrayList<String>());
        res.get(3).add(getTiempoString(tiempoFinalizarPartida));

        res.add(new ArrayList<String>());
        res.get(4).add(String.valueOf(tiempoTranscurrido));

        res.add(new ArrayList<String>());
        res.get(5).add(String.valueOf(estaAcabada));

        res.add(new ArrayList<String>());
        res.get(6).add(String.valueOf(rendido));

        res.add(new ArrayList<String>());
        res.get(7).add(String.valueOf(puntuacion));

        res.add(new ArrayList<String>());
        res.get(8).add(nivelAyuda.getTipoAyuda());

        res.add(new ArrayList<String>());
        res.get(9).add(modoDeJuego.getTipoModo());

        res.add(new ArrayList<String>());
        res.get(10).add(Integer.toString(kakuroAJugar));

        int h = checkPointKakuro.getAltura();
        int w = checkPointKakuro.getAnchura();
        
        res.add(new ArrayList<String>());
        res.get(11).add(Integer.toString(h)); 
        res.get(11).add(Integer.toString(w));

        res.add(new ArrayList<String>());
 
        for (int i = 0; i < h; i++){
            res.add(new ArrayList<String>());
            for (int j = 0; j < w; j++){
                if (checkPointKakuro.getCasillaKakuro(i, j).getModificable()) res.get(12).add("1");
                else res.get(12).add("0");
                res.get(i+11+2).add(checkPointKakuro.getCasillaKakuro(i, j).toString());
            }
        }
        return res;
    }
}
