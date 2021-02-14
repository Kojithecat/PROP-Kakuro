package capaPersistencia.gestor;

import java.io.*;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GestorPartida {
    String Path;
    String nombreFichero;
    String nombreCarpeta;
    String ficheroPreview;
    String ficheroEstado;
    String ficheroHistorial;
    Integer ID;

    public GestorPartida() {
        Path = Paths.get("./datos").toAbsolutePath().toString();
        nombreCarpeta = "partidas";
        ficheroPreview = "preview";
        ficheroEstado = "gestorPartida";
        ficheroHistorial = "historialPartidas";

        ID = 0;
    }


    
    /** 
     * @param path
     * @param carpeta
     */
    private void crearCarpeta(String path, String carpeta) {
       
        File directorio = new File(path, carpeta);
        if (! directorio.exists()){
            if (directorio.mkdir())
                System.out.println("El directorio se ha creado correctamente");
            else
                System.out.println("No ha podido ser creado el directorio "+carpeta);
        }
    }

    
    /** 
     * @param path
     * @param nombre
     * @return boolean
     */
    private boolean crearFichero(String path, String nombre) {
        boolean existe = false;
        File fichero = new File(path, nombre);
        try {
            existe = fichero.exists();
            if (! existe){
                if (fichero.createNewFile())
                    System.out.println("El fichero se ha creado correctamente");
                else
                    System.out.println("No ha podido ser creado el fichero "+nombre);
            }
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return existe;
    }

    
    /** 
     * @param usuario
     * @param partida
     * @param previewInfo
     * @param previewHistorial
     * @return int
     */
    public int guardarPartida(String usuario, ArrayList<ArrayList<String>> partida, ArrayList<ArrayList<String>> previewInfo, ArrayList<ArrayList<String>> previewHistorial) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        int res = -1;
        String Id = "" ;
        boolean nuevaPartida = true;

        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);
        if (partida.get(0).get(0).equals("-1")) {
            if (crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, ficheroEstado)) {
                ArrayList<ArrayList<String>> lista = leerPartida(usuario, ficheroEstado);
            
                if (!lista.isEmpty()) {
                    try {
                    ID = Integer.parseInt(lista.get(0).get(0));
                    }catch (NumberFormatException e){ 
                        e.getStackTrace();
                    }
                }
                else  ID = siguienteIdPartida(usuario);
            }
            else ID = siguienteIdPartida(usuario);
            Id = Integer.toString(ID);
        }else {
            Id = partida.get(0).get(0);
            nuevaPartida = false;
        }
        
        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, Id);

        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+usuario+"/"+Id);
            
            pw = new PrintWriter(fichero);

            for (int i = 0; i < partida.size(); i++) {
                if (i == 0) pw.print(Id);
                else pw.print(partida.get(i).get(0));
                for (int j = 1; j < partida.get(i).size(); j++) {
                    pw.print(","+partida.get(i).get(j));
                }
                pw.println();
            }
            res = Integer.parseInt(Id);
            if (nuevaPartida) {
                ID++;
                guardarEstadoGestor(usuario);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fichero.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            try{
                pw.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        if (partida.get(5).get(0).equals("false")) {
            guardarInfoArchivo(usuario,  previewInfo, ficheroPreview, previewInfo.size(), Id);
        }
        else if (partida.get(5).get(0).equals("true")){
            actualizarPreviewPartida(usuario, ficheroPreview, previewInfo.size(), Id); 
        }
        guardarInfoArchivo(usuario,  previewHistorial, ficheroHistorial, previewHistorial.size(), Id);
        return res;
    }

    
    /** 
     * @param usuario
     * @return int
     */
    private int siguienteIdPartida(String usuario) {
        File directorio = new File(Path+"/"+nombreCarpeta, usuario);
        String[] lista = directorio.list();
        int res = -1;
        for (String s : lista) {
            if (!s.equals(ficheroPreview) && !s.equals(ficheroEstado) && !s.equals(ficheroHistorial)) {
                int intS = Integer.parseInt(s);
                if (res < intS) res = intS;
            }
        }
        return res+1;
    }


    
    /** 
     * @param usuario
     */
    private void guardarEstadoGestor(String usuario) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+usuario+"/"+ficheroEstado);
            pw =   new PrintWriter(fichero);
            pw.println(ID);
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }finally {
            try {
                fichero.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            try{
                pw.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

   /* private void guardarHistorial(String usuario,  ArrayList<ArrayList<String>> info) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);

        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, ficheroHistorial);


    }*/

    private void guardarInfoArchivo(String usuario, ArrayList<ArrayList<String>> info, String archivo, int salto, String id) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);

        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, archivo);

        try {
           
            ArrayList<ArrayList<String>> pre = leerInfo(usuario, archivo);

            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+usuario+"/"+archivo);
            pw = new PrintWriter(fichero);
            int pos = -1;
            boolean encontrado = false;
            for (int i = 0; i < pre.size() && !encontrado; i+=salto) {
                String s = pre.get(i).get(0);
                if (s.equals(id)) {
                    pos = i;
                    encontrado = true;
                }
            }
            if (pos != -1) {
                for (int i = 0; i < salto; i++) 
                    pre.remove(pos);
            }
        

            // guarda todo pre y luego guardar info

            for (ArrayList<String> l: pre) {
                pw.print(l.get(0));
                for (int i = 1; i < l.size(); i++) {
                    pw.print(","+l.get(i));
                }
                pw.println();
            }
            for (int i = 0; i < info.size(); i++) {
                if (i == 0) pw.print(id);
                else pw.print(info.get(i).get(0));
                for (int j = 1; j < info.get(i).size(); j++) {
                    pw.print(","+info.get(i).get(j));
                }
                pw.println();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fichero.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            try{
                pw.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    
    /** 
     * @param usuario
     * @param archivo
     * @param salto
     * @param id
     */
    private void actualizarPreviewPartida(String usuario, String archivo, int salto, String id) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);

        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, archivo);

        try {
            
            ArrayList<ArrayList<String>> pre = leerInfo(usuario, archivo);
            
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+usuario+"/"+archivo);
            pw = new PrintWriter(fichero);
            int pos = -1;
            boolean encontrado = false;
            for (int i = 0; i < pre.size() && !encontrado; i+=salto) {
                String s = pre.get(i).get(0);
                if (id.equals(s)) {
                    pos = i;
                    encontrado = true;
                }
            }
            if (pos != -1) {
                for (int i = 0; i < salto; i++) 
                    pre.remove(pos);
            }
       

            // guardar todo pre restante

            for (ArrayList<String> l: pre) {
                pw.print(l.get(0));
                for (int i = 1; i < l.size(); i++) {
                    pw.print(","+l.get(i));
                }
                pw.println();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fichero.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            try{
                pw.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }


    
    /** 
     * @param usuario
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPartida(String usuario, String Id) {
        ArrayList<ArrayList<String>>partidas = new ArrayList<ArrayList<String>>();

        try {
            File myObj = new File(Path+"/"+nombreCarpeta+"/"+usuario, Id);    
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
            
                while (myReader.hasNext()) { 
                    partidas.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        partidas.get(partidas.size()-1).add(dato);
                }
                myReader.close();
            }
            else {
                System.out.println("El fichero no fue encontrado.");
            }
            
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
          return partidas;
    }
    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewPartidas(String usuario) {
        return leerInfo(usuario, ficheroPreview);
    }

    
    /** 
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerHistorialPartidas(String usuario) {
        return leerInfo(usuario, ficheroHistorial);
    }


    
    /** 
     * @param usuario
     * @param fichero
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerInfo(String usuario, String fichero) {
        ArrayList<ArrayList<String>>partidas = new ArrayList<ArrayList<String>>();

        try {
            File myObj = new File(Path+"/"+nombreCarpeta+"/"+usuario, fichero);    
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
            
                while (myReader.hasNext()) { 
                    partidas.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        partidas.get(partidas.size()-1).add(dato);
                }
                myReader.close();
            }
            else {
                System.out.println("El fichero no fue encontrado.");
            }
            
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
          return partidas;
    }
}