package capaPersistencia.gestor;

import java.io.*;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorUsuario {
    String Path;
    String nombreFichero;
    String nombreCarpeta;

    public GestorUsuario() {
        Path = Paths.get("./datos").toAbsolutePath().toString();
        nombreCarpeta = "usuarios";
        nombreFichero = "Usuarios";
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

    private void crearFichero() {
       
        System.out.println(Path);
        File fichero = new File(Path+"/"+nombreCarpeta, nombreFichero);
        if (! fichero.exists()){
            try {
                if (fichero.createNewFile())
                  System.out.println("El fichero se ha creado correctamente");
                else
                  System.out.println("No ha podido ser creado el fichero "+ nombreFichero);
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    
    /** 
     * @param usuario
     * @return boolean
     */
    private boolean existeUsuario(String usuario) {
        ArrayList<ArrayList<String>> usuarios = leerUsuarios();
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size() && !encontrado; i++) {
            encontrado = usuarios.get(i).get(0).equals(usuario);
        }
        return encontrado;
    }

    
    /** 
     * @param usuario
     * @return boolean
     */
    public boolean guardarUsuario(ArrayList<String> usuario) {
        FileWriter fichero  = null;
        PrintWriter pw = null;
        crearCarpeta();
        crearFichero();
        boolean creado = false;
        if (usuario.size() == 2 && !existeUsuario(usuario.get(0))) { // el primer elemento es el nombre del usuario
            try {
                fichero = new FileWriter(Path+"/"+nombreCarpeta+"/"+nombreFichero, true);
                pw = new PrintWriter(fichero);
    
                pw.println(usuario.get(0)+","+usuario.get(1));
                creado = true;
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
            System.out.println("Ya existe este nombre de usuario");
        }
        return creado;
    }


    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> leerUsuarios() {
        ArrayList<ArrayList<String>>usuarios = new ArrayList<ArrayList<String>>();

        File fichero = new File(Path+"/"+nombreCarpeta, nombreFichero);
        if (fichero.exists()) {
            try {
                File myObj = new File(Path+"/"+nombreCarpeta, nombreFichero);    
                Scanner myReader = new Scanner(myObj);
                
                while (myReader.hasNext()) { 
                    usuarios.add(new ArrayList<String>());
                    String data = myReader.nextLine();
                    String [] dataSplit = data.split(",");
                    for (String dato : dataSplit)
                        usuarios.get(usuarios.size()-1).add(dato);
                }
                myReader.close();
                
            } catch (FileNotFoundException e) {
                System.out.println("El fichero no fue encontrado.");
                e.printStackTrace();
            } 
        }
    return usuarios;
    }
}
