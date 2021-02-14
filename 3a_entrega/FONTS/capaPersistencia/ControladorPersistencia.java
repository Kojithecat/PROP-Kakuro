package capaPersistencia;

import capaPersistencia.gestor.*;

import java.io.File;
import java.util.ArrayList;

public class ControladorPersistencia {

    private static ControladorPersistencia instanciaContro = null;
    String Path;
    String nombreCarpeta;


    public ControladorPersistencia() {
        Path = "./";
        nombreCarpeta = "datos";
    }
    private static class ControladorPersistenciaHelper {
        private static final ControladorPersistencia instanciaContro = new ControladorPersistencia();
    }

    
    /** 
     * @return ControladorPersistencia
     */
    public static ControladorPersistencia getInstance(){
        return ControladorPersistenciaHelper.instanciaContro;
    }

    private void crearCarpeta() {
       
        File directorio = new File(Path, nombreCarpeta);
        if (! directorio.exists()){
            if (directorio.mkdir())
                System.out.println("El directorio " + nombreCarpeta + " se ha creado correctamente");
            else
                System.out.println("No ha podido ser creado el directorio " + nombreCarpeta);
        }
    }

    
    /** 
     * @param ArrayList>kakuro
     * @param info
     * @return int
     */
    // guardar info
    public int guardarInfoKakuro(ArrayList<ArrayList<String>>kakuro, ArrayList<ArrayList<String>> info)  {
        crearCarpeta();
        GestorKakuro k = new GestorKakuro();
        return k.guardarKakuro(kakuro, info);
    }

    
    /** 
     * @param usuario
     * @param ArrayList>partida
     * @param previewInfo
     * @param previewHistorial
     * @return int
     */
    public int guardarInfoPartida (String usuario, ArrayList<ArrayList<String>>partida, ArrayList<ArrayList<String>> previewInfo, ArrayList<ArrayList<String>> previewHistorial)  {
        crearCarpeta();
        GestorPartida p = new GestorPartida();
        return p.guardarPartida(usuario, partida, previewInfo, previewHistorial);
    }

    
    /** 
     * @param info
     */
    public void actualizarInfoRanking(String Id, ArrayList<ArrayList<String>>info)  {
        crearCarpeta();
        GestorRanking ra = new GestorRanking();
        ra.actualizarRanking(Id, info);
    }

    
    /** 
     * @param info
     */
    public void actualizarInfoRecord(String Id, String usuario, ArrayList<ArrayList<String>>info)  {
        crearCarpeta();
        GestorRecord re = new GestorRecord();
        re.actualizarRecord(Id, usuario, info);
    }

    
    /** 
     * @param ArrayListinfo
     * @return boolean
     */
    public boolean guardarInfoUsuario(ArrayList<String>info)  {
        crearCarpeta();
        GestorUsuario u = new GestorUsuario();
        return u.guardarUsuario(info);
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    // leer info
    public ArrayList<ArrayList<String>> leerInfoKakuro(String Id) {
        crearCarpeta();
        GestorKakuro k = new GestorKakuro();
        return k.leerKakuro(Id);
    }

    
    /** 
     * @param path
     * @param nombreFichero
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerKakuroExterno (String path, String nombreFichero) {
        crearCarpeta();
        GestorKakuro k = new GestorKakuro();
        return k.leerKakuroFicheroExterno(path, nombreFichero);
    }

    
    /** 
     * @param Id
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerInfoPartida(String Id, String usuario) {
        crearCarpeta();
        GestorPartida p = new GestorPartida();
        return p.leerPartida(Id, usuario);
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerInfoRanking(String Id) {
        crearCarpeta();
        GestorRanking ra = new GestorRanking();
        return ra.leerRanking(Id);
    }

    
    /** 
     * @param Id
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerInfoRecord(String Id, String usuario) {
        crearCarpeta();
        GestorRecord re = new GestorRecord();
        return re.leerRecord(Id, usuario);
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerInfoUsuario() {
        crearCarpeta();
        GestorUsuario u = new GestorUsuario();
        return u.leerUsuarios();
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    // leer preview
    public  ArrayList<ArrayList<String>> leerPreviewKakuros() {
        crearCarpeta();
        GestorKakuro k = new GestorKakuro();
        return k.leerPreviewKakuros();
    }

    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public  ArrayList<ArrayList<String>> leerPreviewPartidas(String usuario) {
        crearCarpeta();
        GestorPartida p = new GestorPartida();
        return p.leerPreviewPartidas(usuario);
    }
    
    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    // leer historial
    public  ArrayList<ArrayList<String>> leerHistorialPartidas(String usuario) {
        crearCarpeta();
        GestorPartida p = new GestorPartida();
        return p.leerHistorialPartidas(usuario);
    }
}

