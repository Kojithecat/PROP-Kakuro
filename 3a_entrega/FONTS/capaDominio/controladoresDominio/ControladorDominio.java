package capaDominio.controladoresDominio;

import java.util.ArrayList;
import capaDominio.usuario.*;
import capaDominio.kakuro.*;
import capaDominio.partida.*;

import capaDominio.controladoresDominio.controladores.ControladorKakuro;
import capaDominio.controladoresDominio.controladores.ControladorPartida;
import capaDominio.controladoresDominio.controladores.ControladorRanking;
import capaDominio.controladoresDominio.controladores.ControladorUsuario;
import capaDominio.controladoresDominio.controladores.ControladorRecord;

public class ControladorDominio {
    private Usuario cuentaActual;
    private static ControladorDominio instanciaContro = null;

    private static class ControladorDominioHelper {
        private static final ControladorDominio instanciaContro = new ControladorDominio();
    }

    
    /** 
     * @return ControladorDominio
     */
    public static ControladorDominio getInstance(){
        return ControladorDominioHelper.instanciaContro;
    }

    
    /** 
     * @param usuario
     * @param password
     * @return boolean
     */
    // usuario
    public boolean comprobarUsuario(String usuario, String password) {
        ControladorUsuario cu = ControladorUsuario.getInstance();
        boolean valid = cu.comprobarUsuario(usuario, password);
        if (valid)
            cuentaActual = new Usuario(usuario, password);
        return valid;
    }

    
    /** 
     * @param usuario
     * @param password
     * @return boolean
     */
    public boolean crearUsuario(String usuario, String password) {
        ControladorUsuario cu = ControladorUsuario.getInstance();
        return cu.crearUsuario(usuario, password);
    }

    public void cerrarSesionUsuario() {
        cuentaActual = null;
    }

    
    /** 
     * @param kakuro
     * @return int
     */
    //kakuro
    public int guardarKakuro(Kakuro kakuro) {
        ControladorKakuro ck = ControladorKakuro.getInstance();
        return ck.guardarKakuro(kakuro);
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerKakuro(int Id) {
        ControladorKakuro ck = ControladorKakuro.getInstance();
        return ck.leerKakuro(Id);
    }
    
    
    /** 
     * @return int
     */
    public int leerKakuroExterno(String pathCompleto) { // retorna -1 si no se ha encontrado el path, retorna -2 si el kakuro no tiene solucion unica
        ControladorKakuro ck = ControladorKakuro.getInstance();
        return ck.leerKakuroExterno(pathCompleto);
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewKakuro() {
        ControladorKakuro ck = ControladorKakuro.getInstance();
        return ck.leerPreviewKakuro();
    }

    
    /** 
     * @param tam
     * @param numero_blancas
     * @param dificultadLoteria
     * @param patron
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearKakuro(ArrayList<String> tam, String numero_blancas, String dificultadLoteria, String patron)   {
        ControladorKakuro ck = ControladorKakuro.getInstance();
        return ck.crearKakuro(tam, numero_blancas, dificultadLoteria, patron) ;
    }


    
    /** 
     * @param partida
     * @return boolean
     */
    public boolean comprobarKakuro(ArrayList<ArrayList<String>> partida) {
        ArrayList<ArrayList<String>> kak = leerKakuro(Integer.parseInt(partida.get(10).get(0)));
        ArrayList<ArrayList<String>> tablero = new ArrayList<ArrayList<String>>(); 
        for(int i = 13; i< partida.size(); i++ ){
            tablero.add(partida.get(i));
        }
        
        boolean estaBien = true;
        for(int i = 0; i< tablero.size() && estaBien;i++){  
            for(int j =0; j<tablero.get(0).size() && estaBien;j++){
                if(!tablero.get(i).get(j).equals(kak.get(i+2).get(j)))
                    estaBien = false;
            }
        }
        return estaBien;
    }
   

    
    /** 
     * @param p
     * @param finalizar
     * @param rendido
     * @return int
     */
    //partida
    public int guardarPartida(ArrayList<ArrayList<String>> p, boolean finalizar, boolean rendido) {
        ControladorPartida cp = ControladorPartida.getInstance();
        int id = cp.guardarPartida(cuentaActual, p, finalizar, rendido);
        if (finalizar && !rendido) {
            ArrayList<ArrayList<String>> partidaArray = cp.leerPartida(cuentaActual, id);
            Partida partida = ControladorPartida.partidaFromArrayList(partidaArray);

            ControladorRanking cra = ControladorRanking.getInstance();
            cra.actualizarRanking(cuentaActual, partida);
            ControladorRecord cre = ControladorRecord.getInstance();
            cre.actualizarRecord(cuentaActual, partida);
        }
        return id;
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPartida(int Id) {
        ControladorPartida cp = ControladorPartida.getInstance();
        return cp.leerPartida(cuentaActual, Id);
    }

    
    /** 
     * @param arrayPartida
     * @return ArrayList<String>
     */
    public ArrayList<String> pedirAyuda(ArrayList<ArrayList<String>> arrayPartida) {
        ControladorPartida cp = ControladorPartida.getInstance();
        return cp.pedirAyuda(cuentaActual, arrayPartida);
    }

    
    /** 
     * @param parametros
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearPartida(ArrayList<String> parametros) {
        ControladorPartida cp = ControladorPartida.getInstance();
        return cp.crearPartida(cuentaActual, parametros);
    }
   
    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewPartida () {
        ControladorPartida cp = ControladorPartida.getInstance();
        return cp.leerPreviewPartida(cuentaActual);
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerHistorialPartidas () {
        ControladorPartida cp = ControladorPartida.getInstance();
        return cp.leerHistorialPartidas(cuentaActual);
    }
    
    
    /** 
     * @param id
     * @return ArrayList<ArrayList<String>>
     */
    //Ranking
    public ArrayList<ArrayList<String>> leerRanking(int id) {
        ControladorRanking cra = ControladorRanking.getInstance();
        return cra.leerRanking(id);
    }

    
    /** 
     * @param id
     * @return ArrayList<ArrayList<String>>
     */
    //Record
    public ArrayList<ArrayList<String>> leerRecord(int id) {
        ControladorRecord cre = ControladorRecord.getInstance();
        return cre.leerRecord(cuentaActual, id);
    }

   
}
