package capaDominio.controladoresDominio.controladores;

import java.util.ArrayList;

import capaPersistencia.*;
import capaDominio.partida.*;
import capaDominio.usuario.*;

public class ControladorRecord {
    private static ControladorRecord instanciaContro = null;

    private static class ControladorRecordoHelper {
        private static final ControladorRecord instanciaContro = new ControladorRecord();
    }

    
    /** 
     * @return ControladorRecord
     */
    public static ControladorRecord getInstance(){
        return ControladorRecordoHelper.instanciaContro;
    }

    
    /** 
     * @param u
     * @param p
     */
    public void actualizarRecord(Usuario u, Partida p) {

        ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
        info.add(new ArrayList<String>());
        info.get(0).add(Integer.toString(p.getId()));
        info.add(new ArrayList<String>());
        info.get(1).add(Integer.toString(p.getPuntuacion()));
        String idKakuro = Integer.toString(p.getKakuro());
        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        ArrayList<ArrayList<String>> memo = CP.leerInfoRecord(idKakuro, u.getName());
        ArrayList<ArrayList<String>> record;
        if (memo.isEmpty() || Integer.parseInt(memo.get(1).get(0)) < Integer.parseInt(info.get(1).get(0)))
            record = info;
        else
            record = memo;

        CP.actualizarInfoRecord(idKakuro, u.getName(), record);
    }

    
    /** 
     * @param u
     * @param id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerRecord(Usuario u, int id) {
        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        return CP.leerInfoRecord(Integer.toString(id), u.getName());
    }
    
}
