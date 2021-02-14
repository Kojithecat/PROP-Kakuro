package capaPersistencia.gestor;

import java.io.*;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorKakuro {
    String Path;
    String nombreFichero ;
    String nombreCarpeta ;
    String ficheroInfo;
    String ficheroEstado;
    Integer ID;

    public GestorKakuro() {
        Path = Paths.get("./datos").toAbsolutePath().toString();
        nombreCarpeta = "kakuros";
        ficheroInfo = "preview";
        ficheroEstado = "gestorKakuro";

        crearCarpeta();
        if (crearFichero(ficheroEstado)) {
            ArrayList<ArrayList<String>> lista = leerKakuro(ficheroEstado);
            
                if (!lista.isEmpty()) {
                    try {
                    ID = Integer.parseInt(lista.get(0).get(0));
                    }catch (NumberFormatException e){ 
                        e.getStackTrace();
                    }
                }
                else  ID = siguienteIdKakuro();
        }
        else ID = siguienteIdKakuro();
        
    }

    private void guardarEstadoGestor() {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+ficheroEstado);
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

    private void crearCarpeta() {
       
        File directorio = new File(Path, nombreCarpeta);
        if (! directorio.exists()){
            if (directorio.mkdir())
                System.out.println("El directorio se ha creado correctamente");
            else
                System.out.println("No ha podido ser creado el directorio "+nombreCarpeta);
        }
    }

    
    /** 
     * @param Id
     * @return boolean
     */
    private boolean crearFichero(String Id) {
        boolean existe = false;
        File fichero = new File(Path+"/"+nombreCarpeta, Id);
        existe = fichero.exists();
        if (! existe){
            try {
                if (fichero.createNewFile())
                  System.out.println("El fichero se ha creado correctamente");
                else
                  System.out.println("No ha podido ser creado el fichero " + Id);
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return existe;
    }

    
    /** 
     * @return int
     */
    private int siguienteIdKakuro() {
        File directorio = new File(Path, nombreCarpeta);
        String[] lista = directorio.list();
        int res = -1;
        for (String s : lista) {
            if (!s.equals(ficheroInfo) && !s.equals(ficheroEstado)) {
                int intS = Integer.parseInt(s);
                if (res < intS) res = intS;
            }
        }
        return res+1;
    }

    
    /** 
     * @param kakuro
     * @param info
     * @return int
     */
    public int guardarKakuro(ArrayList<ArrayList<String>> kakuro, ArrayList<ArrayList<String>> info) {

        crearCarpeta();
        FileWriter fichero  = null;
        PrintWriter pw = null;
        int res = -1;
        String Id = Integer.toString(ID);
        boolean existe = crearFichero(Id);
        if (!existe) {
            try {
                fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+Id, true);
    
                pw = new PrintWriter(fichero);
    
                for (ArrayList<String> l : kakuro) {
                    pw.print(l.get(0));
                    for (int i = 1; i < l.size(); i++) {
                        pw.print(","+l.get(i));
                    }
                    pw.println();
                }
                guardarPreviewKakuro(info, Id);
                res = ID;
                ID++;
                guardarEstadoGestor();
    
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
        else {
            System.out.println("El kakuro ya estaba creado");
        }   
        return res;
    }

    
    /** 
     * @param info
     * @param Id
     */
    private void guardarPreviewKakuro(ArrayList<ArrayList<String>> info, String Id) {
        
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearFichero(ficheroInfo);

        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+ficheroInfo, true);
            
            pw = new PrintWriter(fichero);
            pw.println(Id);

            for (ArrayList<String> l : info) {
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
     * @param Id
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerKakuro(String Id) {
        ArrayList<ArrayList<String>>kakuros = new ArrayList<ArrayList<String>>();

        try {
            File myObj = new File(Path+"/"+nombreCarpeta, Id);    
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
            
                while (myReader.hasNext()) { 
                    kakuros.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        kakuros.get(kakuros.size()-1).add(dato);
                }
                myReader.close();
            }
            else {
                System.out.println("El fichero no fue encontrado.");
            }
            
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
          return kakuros;
    }

    
    /** 
     * @param path
     * @param nombreFichero
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerKakuroFicheroExterno(String path, String nombreFichero) {
        ArrayList<ArrayList<String>>kakuros = new ArrayList<ArrayList<String>>();

        try {
            File myObj = new File(path, nombreFichero);    
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
            
                while (myReader.hasNext()) { 
                    kakuros.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        kakuros.get(kakuros.size()-1).add(dato);
                }
                myReader.close();
            }
            else {
                System.out.println("El fichero no fue encontrado.");
            }
            
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
          return kakuros;
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerPreviewKakuros() {
        ArrayList<ArrayList<String>>kakuros = new ArrayList<ArrayList<String>>();

        try {
            File myObj = new File(Path+"/"+nombreCarpeta, ficheroInfo);    
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
            
                while (myReader.hasNext()) { 
                    kakuros.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        kakuros.get(kakuros.size()-1).add(dato);
                }
                myReader.close();
            }
            else {
                System.out.println("El fichero no fue encontrado.");
            }
            
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } 
          return kakuros;
    }

}