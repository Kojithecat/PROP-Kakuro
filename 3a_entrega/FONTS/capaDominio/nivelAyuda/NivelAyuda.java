package capaDominio.nivelAyuda;

public abstract class NivelAyuda {
    String tipoAyuda;
    double penalizacionAyuda;
    protected NivelAyuda() {
        tipoAyuda = "No hay nivel de ayuda asociado";
        penalizacionAyuda = -1;
    }

    
    /** 
     * @return String
     */
    public String getTipoAyuda() {
        return tipoAyuda;
    }

    
    /** 
     * @return double
     */
    public double getPenalizacionAyuda() {
        return penalizacionAyuda;
    }

    
    /** 
     * @param nivel
     * @return NivelAyuda
     */
    public static NivelAyuda parseNivelAyuda(String nivel) {
        NivelAyuda conversion = null;
        if (nivel.equals("Nada")) {
            conversion = new NivelAyudaNada();
        }
        else if (nivel.equals("Poco")) {
            conversion = new NivelAyudaPoco();
        }
        else if (nivel.equals("Medio")) {
            conversion = new NivelAyudaMedio();
        }
        else if (nivel.equals("Mucho")) {
            conversion = new NivelAyudaMucho();
        }
        return conversion;
    }
}