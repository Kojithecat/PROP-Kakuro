package capaPresentacion;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;

import capaDominio.controladoresDominio.ControladorDominio;

public class ControladorPresentacion {

    // Funcion de inicialización:
    private ControladorDominio ctrlDominio = ControladorDominio.getInstance();

    private PantallaLogIn vistaPrincipal = null;

    private static ControladorPresentacion instanciaPres = null;
    private static class ControladorPresentacionHelper {
        private static final ControladorPresentacion instanciaPres = new ControladorPresentacion();
    }

    
    /** 
     * @return ControladorPresentacion
     */
    public static ControladorPresentacion getInstance(){
        return ControladorPresentacionHelper.instanciaPres;
    }

    public ControladorPresentacion() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }


    public void inicializarPresentacion() {
        //ctrlDominio.inicializarCtrlDominio();
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.PLAIN, 16) ); 
        String[] a = null;
        vistaPrincipal = new PantallaLogIn();
        vistaPrincipal.setVisible(true);
    }

    
    /** 
     * @param usr
     * @param pwrd
     * @return boolean
     */
    public boolean iniciarSesion(String usr, String pwrd){
        boolean inicia =ctrlDominio.comprobarUsuario(usr,pwrd);
        //if(inicia) usuario = usr;
        return inicia;
    }

    
    /** 
     * @param usr
     * @param pwrd
     * @return boolean
     */
    public boolean crearUsuario(String usr, String pwrd){
        if(usr.length() > 0 && pwrd.length() > 0) return ctrlDominio.crearUsuario(usr,pwrd); 
    

        else{
            JOptionPane.showMessageDialog(null, "¡Usuario y contraseña no pueden estar vacíos!", "User Creation", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public void cerrarSesionUsuario(){
        ctrlDominio.cerrarSesionUsuario();
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> previewKakuro(){
        ArrayList<ArrayList<String>> list = ctrlDominio.leerPreviewKakuro();
        ArrayList<String> infoKakuro = new ArrayList<String>();
        for(int i = 0; i<list.size(); i = i+2){
            String str = "ID: "+ list.get(i).get(0) + " Tamaño: " + list.get(i+1).get(0) +"x"+list.get(i+1).get(1);
            infoKakuro.add(str);
             
        }
        //for(String elem : infoKakuro) System.out.println(elem);
        return infoKakuro;
    
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> partidas(){
        ArrayList<ArrayList<String>> list = ctrlDominio.leerHistorialPartidas();
        ArrayList<String> infoHistorial = new ArrayList<String>();
        for(int i = 0; i<list.size(); i= i +10){ 
            String i2 = list.get(i+2).get(0).substring(0, 19);
                if(list.get(i+4).get(0).equals("false")){
                    i2 = "No ha terminado la partida"; 
                    list.get(i+6).set(0,"0");
                }
                String str = "ID:                    " + list.get(i).get(0) + "\n" + "Fecha de Creacion:     " +list.get(i+1).get(0).substring(0, 19) +
                             "\n"+ "Fecha de Finalización: " +i2 + "\n"+ "Tiempo transcurrido:   " + list.get(i+3).get(0) + "\n"+ "Partida Acabada:       " + list.get(i+4).get(0) +
                              "\n"+ "Partida Rendida:       " + list.get(i+5).get(0) + "\n"+ "Puntuación:            " +list.get(i+6).get(0) + "\n"+ "Nivel de ayuda:        " + list.get(i+7).get(0) 
                             + "\n"+ "Modo de juego:         " +list.get(i+8).get(0) + "\n"+"ID del kakuro:         "+list.get(i+9).get(0);
                infoHistorial.add(str);
            }
        return infoHistorial;
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> previewPartidas(){
        ArrayList<ArrayList<String>> list = ctrlDominio.leerHistorialPartidas();
        ArrayList<String> infoHistorial = new ArrayList<String>();
        for(int i = 0; i<list.size(); i= i +10){ 
                String str = "ID: " + list.get(i).get(0) + " Fecha de creacion: " + list.get(i+1).get(0).substring(0, 19); 
                infoHistorial.add(str);
            }
        return infoHistorial;
    }

    
    /** 
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> partidaNoAcabada(int Id){
        return ctrlDominio.leerPartida(Id);
    }

    
    /** 
     * @param Id
     * @return String
     */
    public String puntuacionPartida(int Id){
        return ctrlDominio.leerPartida(Id).get(7).get(0);
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> previewPartidasnoAcabadas(){
        ArrayList<ArrayList<String>> list = ctrlDominio.leerPreviewPartida();
        ArrayList<String> infoHistorial = new ArrayList<String>();
        for(int i = 0; i<list.size(); i= i +6){ 
                String str = "ID: " + list.get(i).get(0) + " Fecha de creacion: " + list.get(i+1).get(0).substring(0, 19); 
                infoHistorial.add(str);
            }
        return infoHistorial;

    }

    
    /** 
     * @param partida
     * @return ArrayList<String>
     */
    public ArrayList<String> pedirAyudas(ArrayList<ArrayList<String>> partida){
        return ctrlDominio.pedirAyuda(partida);
    }



    
    /** 
     * @param p
     * @param finalizar
     * @param rendido
     * @return int
     */
    public int guardarPartida(ArrayList<ArrayList<String>> p, boolean finalizar, boolean rendido) {
        return ctrlDominio.guardarPartida(p, finalizar, rendido);
    }

    
    /** 
     * @param parametros
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearPartida(ArrayList<String> parametros){
        return ctrlDominio.crearPartida(parametros);

    }

    
    /** 
     * @param partida
     * @return boolean
     */
    public boolean comprobarPartida(ArrayList<ArrayList<String>> partida){
        return ctrlDominio.comprobarKakuro(partida);
    }

    
    /** 
     * @param selectedPath
     * @return int
     */
    public int crearKakuroPath(String selectedPath) {
        return ctrlDominio.leerKakuroExterno(selectedPath);
    }

    
    /** 
     * @param tam
     * @param numero_blancas
     * @param dificultadLoteria
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearKakuro(ArrayList<String> tam, String numero_blancas, String dificultadLoteria){
        return ctrlDominio.crearKakuro(tam, numero_blancas, dificultadLoteria, "3");
    }

    
    /** 
     * @param Id
     * @return ArrayList<String>
     */
    public ArrayList<String> listaRanking(int Id){
        ArrayList<ArrayList<String>> listaRank = ctrlDominio.leerRanking(Id);
        ArrayList<String> listaRankBonita = new ArrayList<String>();
        //System.out.println(listaRank);
        for(int i = 0; i < listaRank.size();i =i+3){
           listaRankBonita.add("Id partida: " + listaRank.get(i).get(0) + " Usuario: " + listaRank.get(i+1).get(0) + " Puntuación: " + listaRank.get(i+2).get(0)); 
        }
        return listaRankBonita;
    }

    
    /** 
     * @param Id
     * @return ArrayList<String>
     */
    public ArrayList<String> listaRecord(int Id){
        ArrayList<ArrayList<String>> listaRec = ctrlDominio.leerRecord(Id);
        ArrayList<String> listaRecBonita = new ArrayList<String>();
        System.out.println(listaRec);
        if(listaRec.size() != 0)listaRecBonita.add("Id partida: " +listaRec.get(0).get(0) + " Puntuacion: " + listaRec.get(1).get(0));
        return listaRecBonita;
    }


    // comprobar usuario -> relegar a la capa Dominio

    // crear usuario -> " "

    //
}
