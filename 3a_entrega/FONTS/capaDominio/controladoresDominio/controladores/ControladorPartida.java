package capaDominio.controladoresDominio.controladores;

import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import capaDominio.partida.*;
import capaDominio.kakuro.*;
import capaDominio.casilla.*;
import capaDominio.controladoresDominio.ControladorDominio;
import capaDominio.usuario.*;
import capaDominio.pair.*;
import capaDominio.nivelAyuda.*;
import capaDominio.modo.*;
import capaPersistencia.*;

public class ControladorPartida {
    private static ControladorPartida instanciaContro = null;
    private HashMap<ArrayList<String>,Partida> repoPartida = new HashMap<ArrayList<String>,Partida>();
    private LocalDateTime tiempoInicioSesion;
    private static class ControladorPartidaHelper {
        private static final ControladorPartida instanciaContro = new ControladorPartida();
    }

    
    /** 
     * @return ControladorPartida
     */
    public static ControladorPartida getInstance(){
        return ControladorPartidaHelper.instanciaContro;
    }

    
    /** 
     * @param p
     * @param finalizar
     * @param rendido
     * @return int
     */
    public int guardarPartida(Usuario usuario, ArrayList<ArrayList<String>> p, boolean finalizar, boolean rendido) {
        ArrayList<String> mapId = new ArrayList<String>();

        Partida partida = partidaFromArrayList(p);

        if (finalizar)
            partida.acabarPartida(tiempoInicioSesion, rendido);
        else
            partida.acabarSesion(tiempoInicioSesion);
        ControladorPersistencia CP = ControladorPersistencia.getInstance();

        ArrayList<ArrayList<String>> partidaArray = partida.toArrayList();
        ArrayList<ArrayList<String>> infoPreview = crearPreview(partidaArray);
        ArrayList<ArrayList<String>> infoHistorial = crearHistorial(partidaArray);

        int pId = CP.guardarInfoPartida(usuario.getName(), partidaArray, infoPreview, infoHistorial); //implementar capa Dominio
        if (pId == -1)
            System.out.println("Algo fue terriblemente mal");

        // actualizar mapa
        mapId.add(usuario.getName());
        mapId.add(p.get(0).get(0));
        repoPartida.remove(mapId);
        repoPartida.put(mapId, partida);

        return pId;
    }

    
    /** 
     * @param usuario
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPartida(Usuario usuario, int Id) {
        ArrayList<String> mapId = new ArrayList<String>();
        mapId.add(usuario.getName());
        mapId.add(Integer.toString(Id));
        if (repoPartida.containsKey(mapId)) {
            Partida p = repoPartida.get(mapId);
            p.iniciarSesion();
            return repoPartida.get(mapId).toArrayList();
        }
        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        ArrayList<ArrayList<String>> partidaArray = CP.leerInfoPartida(usuario.getName(), Integer.toString(Id));
        Partida p = partidaFromArrayList(partidaArray);
        p.setId(Id);
        //p.iniciarSesion();
        tiempoInicioSesion =  LocalDateTime.now(); // guardamos el momento de carga de la ultima partida leida
        repoPartida.put(mapId,p);
       
        return p.toArrayList();
    }

    
    /** 
     * @param partida
     * @return ArrayList<ArrayList<String>>
     */
    private ArrayList<ArrayList<String>> crearPreview(ArrayList<ArrayList<String>> partida) {
        ArrayList<ArrayList<String>>info = new ArrayList<ArrayList<String>>();
        info.add(new ArrayList<String>());
        info.get(0).add(partida.get(0).get(0)); //id partida

        info.add(new ArrayList<String>());
        info.get(1).add(partida.get(2).get(0)); //tiempo inicio
        
        info.add(new ArrayList<String>());
        info.get(2).add(partida.get(4).get(0)); //tiempoTranscurrido

        info.add(new ArrayList<String>());
        info.get(3).add(partida.get(8).get(0)); //nivel de ayuda

        info.add(new ArrayList<String>());
        info.get(4).add(partida.get(9).get(0)); //nivel de modo

        info.add(new ArrayList<String>());
        info.get(5).add(partida.get(10).get(0)); //id kakuro a jugar
        
        return info;
    }

    
    /** 
     * @param partida
     * @return ArrayList<ArrayList<String>>
     */
    private ArrayList<ArrayList<String>> crearHistorial(ArrayList<ArrayList<String>> partida) {
        ArrayList<ArrayList<String>>info = new ArrayList<ArrayList<String>>();
        info.add(new ArrayList<String>());
        info.get(0).add(partida.get(0).get(0)); //id partida

        info.add(new ArrayList<String>());
        info.get(1).add(partida.get(2).get(0)); //tiempo inicio

        info.add(new ArrayList<String>());
        info.get(2).add(partida.get(3).get(0)); //tiempo Finalizar Partida
        
        info.add(new ArrayList<String>());
        info.get(3).add(partida.get(4).get(0)); //tiempoTranscurrido

        info.add(new ArrayList<String>());
        info.get(4).add(partida.get(5).get(0)); //estaAcabada

        info.add(new ArrayList<String>());
        info.get(5).add(partida.get(6).get(0)); //rendido

        info.add(new ArrayList<String>());
        info.get(6).add(partida.get(7).get(0)); //puntuacion

        info.add(new ArrayList<String>());
        info.get(7).add(partida.get(8).get(0)); //nivel de ayuda

        info.add(new ArrayList<String>());
        info.get(8).add(partida.get(9).get(0)); //nivel de modo

        info.add(new ArrayList<String>());
        info.get(9).add(partida.get(10).get(0)); //id kakuro a jugar

        return info;
    }

    
    /** 
     * @param usuario
     * @param parametros
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> crearPartida(Usuario usuario, ArrayList<String> parametros) {
        int id = Integer.parseInt(parametros.get(0));
        NivelAyuda nivel = NivelAyuda.parseNivelAyuda(parametros.get(1));
        Modo modo = Modo.parseModo(parametros.get(2));

        ControladorPersistencia CP = ControladorPersistencia.getInstance();
        Kakuro kakuro = fromArrayList(CP.leerInfoKakuro(Integer.toString(id)),true);
        kakuro.setId(id);

        Partida partida = new Partida(kakuro, usuario.getName(), nivel, modo);
        ArrayList<ArrayList<String>> partidaArray = partida.toArrayList();


        ArrayList<ArrayList<String>> infoPreview = crearPreview(partidaArray);
        ArrayList<ArrayList<String>> infoHistorial = crearHistorial(partidaArray);

        int pId = CP.guardarInfoPartida(usuario.getName(), partidaArray, infoPreview, infoHistorial);
        if (pId > -1) {
            partida.setId(pId);
            ArrayList<String> mapId = new ArrayList<String>();
            mapId.add(usuario.getName());
            mapId.add(Integer.toString(pId));
            repoPartida.put(mapId, partida);
            partidaArray.get(0).remove(0);
            partidaArray.get(0).add(Integer.toString(pId));
            tiempoInicioSesion =  LocalDateTime.now(); // guardamos el momento de carga de la ultima partida leida
        }
        else
            System.out.println("Algo fue terriblemente mal");

        return partidaArray;
    }


    
    /** 
     * @param arrayPartida
     * @return ArrayList<String>
     */
    public ArrayList<String> pedirAyuda(Usuario usuario, ArrayList<ArrayList<String>> arrayPartida) {
        int id = Integer.parseInt(arrayPartida.get(0).get(0));

        ArrayList<String> mapId = new ArrayList<String>();
        mapId.add(usuario.getName());
        mapId.add(Integer.toString(id));
        Partida p = repoPartida.get(mapId);
        
        Kakuro k = p.getCheckPointKakuro();

        ArrayList<ArrayList<String>> kakak = (ArrayList<ArrayList<String>>) arrayPartida.clone();
        for (int i = 0; i < 13; i++) kakak.remove(0);

        ArrayList<ArrayList<String>> kakillas = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < kakak.size(); i++) {
            for (int j = 0; j < kakak.get(i).size(); j++) {
                Casilla c = k.getCasillaKakuro(i, j);
                if (c instanceof CasillaBlanca && !kakak.get(i).get(j).equals(Integer.toString(c.getNumeroSolucion()))) {
                    ArrayList<String> pos = new ArrayList<String>();
                    pos.add(Integer.toString(i));
                    pos.add(Integer.toString(j));
                    pos.add(Integer.toString(c.getNumeroSolucion()));
                    kakillas.add(pos);
                }
            }
        }
        ArrayList<String> res = new ArrayList<String>();
        if (kakillas.size() < 1) {
            res.add("-1");
            res.add("-1");
            res.add("-1");
        }
        else {
            int num = (int) Math.floor(Math.random() * kakillas.size());
            res = kakillas.get(num);
        }

        return res;
    }

    
    /** 
     * @param partida
     * @return Partida
     */
    public static Partida partidaFromArrayList(ArrayList<ArrayList<String>> partida) {
        DateTimeFormatter formato = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime tiempoInicio = LocalDateTime.parse(partida.get(2).get(0), formato);
        LocalDateTime tiempoFinalizarPartida = LocalDateTime.parse(partida.get(3).get(0), formato);

        int id = Integer.parseInt(partida.get(0).get(0));
        String propietario = partida.get(1).get(0);
        float tiempoTranscurrido = Float.parseFloat(partida.get(4).get(0));
        boolean estaAcabada = Boolean.parseBoolean(partida.get(5).get(0));
        boolean rendido = Boolean.parseBoolean(partida.get(6).get(0));
        int puntuacion = Integer.parseInt(partida.get(7).get(0));
        NivelAyuda nivel = NivelAyuda.parseNivelAyuda(partida.get(8).get(0));
        Modo modo = Modo.parseModo(partida.get(9).get(0));
        String kakuroAJugar = partida.get(10).get(0);

        ArrayList<ArrayList<String>> kakak = (ArrayList<ArrayList<String>>) partida.clone();
        for (int i = 0; i < 11; i++) kakak.remove(0);
        Kakuro kakuro = fromArrayList(kakak, false);
        
        ControladorDominio CD = ControladorDominio.getInstance();
        ArrayList<ArrayList<String>> kakuroSol = CD.leerKakuro(Integer.parseInt(kakuroAJugar));
        kakuroSetSol(kakuro, kakuroSol);
        kakuro.setId(Integer.parseInt(kakuroAJugar));

        return new Partida(id, kakuro, propietario, nivel, modo, tiempoInicio, tiempoFinalizarPartida, tiempoTranscurrido, 
                           estaAcabada, rendido, puntuacion);
    }

    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewPartida (Usuario usuario) {
        ControladorPersistencia cp = ControladorPersistencia.getInstance();
        return cp.leerPreviewPartidas(usuario.getName());
    }

    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerHistorialPartidas (Usuario usuario) {
        ControladorPersistencia cp = ControladorPersistencia.getInstance();
        return cp.leerHistorialPartidas(usuario.getName());
    }

    
    /** 
     * @param arrayKakuro
     * @param esNuevo
     * @return Kakuro
     */
    public static Kakuro fromArrayList(ArrayList<ArrayList<String>> arrayKakuro, boolean esNuevo) {
        ArrayList<ArrayList<String>> kakopia = (ArrayList<ArrayList<String>>) arrayKakuro.clone();
        kakopia.remove(0);
        ArrayList<String> mod = kakopia.get(0);
        kakopia.remove(0);
        int altura = kakopia.size();
        int anchura = kakopia.get(1).size();
        Kakuro kakuro = new Kakuro(altura,anchura);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < anchura; j++) {
                String sl = kakopia.get(i).get(j);
                if (sl.equals("?")) kakuro.setCasillaKakuro(i, j, new CasillaBlanca(new Pair(i,j)));
                else if (sl.equals("*")) kakuro.setCasillaKakuro(i, j,  new CasillaNegra(new Pair(i,j)));
                else if (sl.contains("C") && sl.contains("F")) {
                    String[] sumas = sl.split("F");
                    int totalH = Integer.parseInt(sumas[1]);
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = Integer.parseInt(sumas[0].substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = numCasBlancas(kakopia, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = numCasBlancas(kakopia, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (sl.contains("C")) {
                    int totalH = 0;
                    int totalV = Integer.parseInt(sl.substring(1));       //  Omite la C (el PRIMER caracter)
                    if (totalV > 45 || totalV < 3) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasH = 0;
                    int cantidadBlancasV = numCasBlancas(kakopia, i, j, true);
                    if (cantidadBlancasV > 9 || cantidadBlancasV < 2) System.out.println("Algo fue terriblemente mal");
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else if (sl.contains("F")) {
                    int totalH = Integer.parseInt(sl.substring(1));
                    if (totalH > 45 || totalH < 3) System.out.println("Algo fue terriblemente mal");
                    int totalV = 0;       //  Omite la C (el PRIMER caracter)
                    int cantidadBlancasH = numCasBlancas(kakopia, i, j, false);
                    if (cantidadBlancasH > 9 || cantidadBlancasH < 2) System.out.println("Algo fue terriblemente mal");
                    int cantidadBlancasV = 0;
                    kakuro.setCasillaKakuro(i, j, new CasillaNegra(new Pair(i,j), totalH, totalV, 
                            cantidadBlancasH, cantidadBlancasV));
                }
                else {
                    int nCasilla = Integer.parseInt(sl);
                    if (nCasilla > 9 || nCasilla < 1) System.out.println("Algo fue terriblemente mal");
                    else {
                        CasillaBlanca cB = new CasillaBlanca(new Pair(i,j));
                        if (esNuevo)
                            cB.setNumeroSolucion(nCasilla);
                        else 
                            cB.setNumeroElejido(nCasilla);
                        if (mod.get(i*anchura+j).equals("0")) cB.setModificable(false);
                        kakuro.setCasillaKakuro(i, j, cB);
                    }
                }
            }
        }
        
        return kakuro;
    }

    
    /** 
     * @param arrayKakuro
     */
    public static void kakuroSetSol(Kakuro kakuro, ArrayList<ArrayList<String>> arrayKakuro) {
        ArrayList<ArrayList<String>> kakopia = (ArrayList<ArrayList<String>>) arrayKakuro.clone();
        kakopia.remove(0);
        kakopia.remove(0);
        int altura = kakopia.size();
        int anchura = kakopia.get(1).size();

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < anchura; j++) {
                String sl = kakopia.get(i).get(j);
                if (! ( (sl.equals("?")) || (sl.equals("*")) || (sl.contains("C")) || (sl.contains("F"))))  {
                    int nCasilla = Integer.parseInt(sl);
                    if (nCasilla > 9 || nCasilla < 1) System.out.println("Algo fue terriblemente mal");
                    else {
                        kakuro.getCasillaKakuro(i, j).setNumeroSolucion(nCasilla);
                    }
                }
            }
        }
    }

    
    /** 
     * @param vertical
     * @return int
     */
    private static int numCasBlancas(ArrayList<ArrayList<String>> tablero, int posVertical, int posHorizontal, boolean vertical) { // contar numero de casillas blancas                                
        boolean encontrado = false;                                                                                 //  que tiene una negra
        int contador = 1;
        if (vertical) {
            while (contador+posVertical < tablero.size() && ! encontrado) {
                encontrado = esNegra(tablero, posVertical+contador, posHorizontal);
                if (! encontrado) contador++;
            }
        }
        else {
            while (contador+posHorizontal < tablero.get(1).size() && ! encontrado) {
                encontrado = esNegra(tablero, posVertical, posHorizontal+contador);
                if (! encontrado) contador++;
            }
        }
        return contador-1;
    }

    
    /** 
     * @param tablero
     * @param posV
     * @param posH
     * @return boolean
     */
    private static boolean esNegra(ArrayList<ArrayList<String>> tablero, int posV, int posH){
        if (tablero.get(posV).get(posH).contains("C")) return true;
        if (tablero.get(posV).get(posH).contains("F")) return true;
        if (tablero.get(posV).get(posH).contains("*")) return true;
        return false;
    }

    
    /** 
     * @param k
     */
    public static void encontrarPadres(Kakuro k) {
        for (int i = 0; i < k.getAltura(); i++){
            for (int j = 0; j < k.getAnchura(); j++){
                Casilla c = k.getCasillaKakuro(i, j);
               if (c instanceof CasillaNegra) {
                    encontrarHijos(k,i,j);
               } 
            }
        }
    }

    
    /** 
     * @param k
     * @param posVertical
     * @param posHorizontal
     */
    private static void encontrarHijos(Kakuro k, int posVertical, int posHorizontal) {
        int contador = 1;
        while (contador+posVertical < k.getAltura() && !(k.getCasillaKakuro(posVertical+contador, posHorizontal) instanceof CasillaNegra)) {
            k.getCasillaKakuro(posVertical+contador, posHorizontal).setCasillaNegraV(posVertical, posHorizontal);
            contador++;
        }
        contador = 1;
        while (contador+posHorizontal < k.getAnchura() && !(k.getCasillaKakuro(posVertical, posHorizontal+contador) instanceof CasillaNegra)) {
            k.getCasillaKakuro(posVertical, posHorizontal+contador).setCasillaNegraH(posVertical, posHorizontal);
            contador++;
        }
    }
}