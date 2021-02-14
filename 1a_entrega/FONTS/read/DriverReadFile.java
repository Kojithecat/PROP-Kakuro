package read;

public class DriverReadFile {
    String fichero;

    public DriverReadFile(String f){
        fichero = f;
    }
    
    public void testRecogerFicheroLeido() {

        String[][] res = ReadFile.main(fichero);

        // Escribir por consola el resultado

        int row = res.length;
        int column = res[0].length;
        System.out.println(row + " " + column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++){
                if (j != column-1) System.out.print(res[i][j] + " ");
                else System.out.println(res[i][j]);
            }
        }
    }
}