package capaPersistencia.gestor;

import java.io.*;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GestorRecord {
    String Path;
    String nombreFichero ;
    String nombreCarpeta ;

    public GestorRecord() {
        Path = Paths.get("./datos").toAbsolutePath().toString();
        nombreCarpeta = "record";
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
     * @param Id
     */
    private void crearFichero(String path, String Id) {
        File fichero = new File(path, Id);
        if (! fichero.exists()){
            try {
                if (fichero.createNewFile())
                  System.out.println("El fichero se ha creado correctamente");
                else
                  System.out.println("No ha podido ser creado el fichero "+ Id);
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    
    /** 
     * @param record
     */
    public void actualizarRecord(String Id, String usuario, ArrayList<ArrayList<String>> record) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);

        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, Id);
        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+usuario+"/"+Id);
            
            pw = new PrintWriter(fichero);

            for (ArrayList<String> l : record) {
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
     * @param usuario
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerRecord(String Id, String usuario) {
        ArrayList<ArrayList<String>>kakuros = new ArrayList<ArrayList<String>>();
        crearCarpeta(Path, this.nombreCarpeta);
        crearCarpeta(Path+"/"+this.nombreCarpeta, usuario);

        crearFichero(Path+"/"+nombreCarpeta+"/"+usuario, Id);
        try {
            File myObj = new File(Path+"/"+nombreCarpeta+"/"+usuario, Id);    
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

