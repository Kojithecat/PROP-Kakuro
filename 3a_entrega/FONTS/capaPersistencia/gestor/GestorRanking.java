package capaPersistencia.gestor;

import java.io.*;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorRanking {
    String Path;
    String nombreFichero ;
    String nombreCarpeta ;

    public GestorRanking() {
        Path = Paths.get("./datos").toAbsolutePath().toString();
        nombreCarpeta = "ranking";
    }


    private void crearCarpeta() {
       
        File directorio = new File(Path, nombreCarpeta);
        if (! directorio.exists()){
            if (directorio.mkdir())
                System.out.println("El directorio se ha creado correctamente");
            else
                System.out.println("No ha podido ser creado el directorio "+ nombreCarpeta);
        }
    }

    
    /** 
     * @param Id
     */
    private void crearFichero(String Id) {
       
        System.out.println(Path);
        File fichero = new File(Path+"/"+nombreCarpeta, Id);
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
     * @param ranking
     */
    public void actualizarRanking(String Id, ArrayList<ArrayList<String>> ranking) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta();
        crearFichero(Id);
        try {
            fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+Id);
            
            pw = new PrintWriter(fichero);

            for (ArrayList<String> l : ranking) {
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
    public ArrayList<ArrayList<String>> leerRanking(String Id) {
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

}