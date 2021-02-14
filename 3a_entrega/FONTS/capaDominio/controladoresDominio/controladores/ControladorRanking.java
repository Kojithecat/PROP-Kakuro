package capaDominio.controladoresDominio.controladores;

import capaPersistencia.*;
import capaDominio.partida.*;
import capaDominio.usuario.*;

import java.util.ArrayList;

public class ControladorRanking {
    private static ControladorRanking instanciaContro = null;

    private static class ControladorRankingoHelper {
        private static final ControladorRanking instanciaContro = new ControladorRanking();
    }

    
    /** 
     * @return ControladorRanking
     */
    public static ControladorRanking getInstance(){
        return ControladorRankingoHelper.instanciaContro;
    }

    
    /** 
     * @param u
     * @param p
     */
    public void actualizarRanking(Usuario u, Partida p)
    {
        ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
        info.add(new ArrayList<String>());
        info.get(0).add(Integer.toString(p.getId()));
        info.add(new ArrayList<String>());
        info.get(1).add(u.getName());
        info.add(new ArrayList<String>());
        info.get(2).add(Integer.toString(p.getPuntuacion()));

        int Id = p.getKakuro();

        ArrayList<ArrayList<String>> mem = leerRanking(Id);
        if (mem.size() < 30) {
            boolean changed = false;
            int pos = -1;
            for (int i = 0; i < mem.size() && !changed; i+=3) {
                if (Integer.parseInt(mem.get(i+2).get(0)) < Integer.parseInt(info.get(2).get(0))) {
                    pos = i;
                    changed = true;
                }
            }
            if (pos == -1) 
                pos = mem.size();
            mem.add(pos,info.get(0));
            mem.add(pos+1,info.get(1));
            mem.add(pos+2,info.get(2));
        }
        else if (Integer.parseInt(mem.get(29).get(0)) < Integer.parseInt(info.get(2).get(0))) {
            boolean changed = false;
            for (int i = 0; i < 30 && !changed; i+=3) {
                if (Integer.parseInt(mem.get(i+2).get(0)) < Integer.parseInt(info.get(2).get(0))) {
                    changed = true;
                    mem.add(i,info.get(0));
                    mem.add(i+1,info.get(1));
                    mem.add(i+2,info.get(2));

                    while (mem.size() > 10*3)
                        mem.remove(mem.size()-1);
                }
            }
        }
        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        CP.actualizarInfoRanking(Integer.toString(Id), mem);
    }

    
    /** 
     * @param id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerRanking(int id) {
        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        return CP.leerInfoRanking(Integer.toString(id));
    }
}