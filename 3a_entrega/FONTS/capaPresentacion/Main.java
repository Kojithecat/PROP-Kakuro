package capaPresentacion;

public class Main {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ControladorPresentacion ctrlPresentacion = new ControladorPresentacion();
                ctrlPresentacion.inicializarPresentacion();
            }
        });
    }
}
