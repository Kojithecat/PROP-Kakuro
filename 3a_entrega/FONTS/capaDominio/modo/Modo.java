package capaDominio.modo;

public abstract class Modo {
    String tipoModo;
    double penalizacionModo;
    protected Modo() {
        tipoModo = "No hay un tipo de modo asociado";
        penalizacionModo = -1;
    }

    
    /** 
     * @return String
     */
    public String getTipoModo() {
        return tipoModo;
    }

    
    /** 
     * @return double
     */
    public double getPenalizacionModo() {
        return penalizacionModo;
    }

    
    /** 
     * @param nivel
     * @return Modo
     */
    public static Modo parseModo(String nivel) {
        Modo conversion = null;
        if (nivel.equals("Normal")) {
            conversion = new ModoNormal();
        }
        else if (nivel.equals("Ranking")) {
            conversion = new ModoRanking();
        }
        return conversion;
    }
}