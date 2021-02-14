package read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {  
  
  public static String[][] main(String arg) {  
    String[][]k = new String[0][0];
     try {
      File myObj = new File(arg);    
      Scanner myReader = new Scanner(myObj);
     
      if (myReader.hasNextLine()) {  // Lee los dos primeros valores para saber tamaño del tablero
        String data = myReader.nextLine();
        String [] dataSplit = data.split(",");
        int rows = Integer.parseInt(dataSplit[0]);
        int columns = Integer.parseInt(dataSplit[1]); 
        k = new String[rows][columns];
      }
      
      for (int i = 0; myReader.hasNextLine() && i < k.length; ++i) { 
        String data = myReader.nextLine();
        String [] dataSplit = data.split(",");
        for (int j = 0; j < dataSplit.length; j++) {
          k[i][j] = dataSplit[j].replace(" ", "");
        }
      }
      myReader.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("El fichero no fue encontrado.");
      e.printStackTrace();
    } 
    
    return k;
   
  }  
} 
