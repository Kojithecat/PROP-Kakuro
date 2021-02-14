package capaDominio.controladoresDominio.controladores;

import java.util.HashMap;
import java.util.ArrayList;

import capaDominio.usuario.*;
import capaPersistencia.*;


public class ControladorUsuario {
    private static ControladorUsuario instanciaContro = null;
    private HashMap<String,String> repoUsuarios = new HashMap<String,String>();

    public ControladorUsuario(){
        obtenerUsuarios();
    }

    private static class ControladorUsuarioHelper {
        private static final ControladorUsuario instanciaContro = new ControladorUsuario();
    }

    
    /** 
     * @return ControladorUsuario
     */
    public static ControladorUsuario getInstance(){
        return ControladorUsuarioHelper.instanciaContro;
    }

    private void obtenerUsuarios() {
        if (repoUsuarios.size() == 0) {
            ControladorPersistencia control = ControladorPersistencia.getInstance();
            ArrayList<ArrayList<String>> info = control.leerInfoUsuario();
            for (ArrayList<String> usu: info)  {
                repoUsuarios.put(usu.get(0), usu.get(1));
            }
        }
    }

    
    /** 
     * @param usuario
     * @param password
     * @return boolean
     */
    public boolean comprobarUsuario(String usuario, String password) {
        if (repoUsuarios.containsKey(usuario)) {
            String usPwd = repoUsuarios.get(usuario);
            if (usPwd.equals( Usuario.encrypt(password))) return true;
        }
        return false;
    }

    
    /** 
     * @param usuario
     * @param password
     * @return boolean
     */
    public boolean crearUsuario(String usuario, String password){
        if (! repoUsuarios.containsKey(usuario)) {
            ControladorPersistencia control = ControladorPersistencia.getInstance();
            String encryptPwd = Usuario.encrypt(password);
            ArrayList <String> paquete = new ArrayList <String>();
            paquete.add(usuario);
            paquete.add(encryptPwd);
            boolean creado = control.guardarInfoUsuario(paquete);

            if (creado)
                repoUsuarios.put(usuario, encryptPwd);
            return creado;
        }
        return false;
    }
}
