package nivelAyuda;

public abstract class NivelAyuda {
    String tipoAyuda;
    double penalizacionAyuda;
    protected NivelAyuda() {
        tipoAyuda = "No hay nivel de ayuda asociado";
        penalizacionAyuda = -1;
    }

    public String getTipoAyuda() {
        return tipoAyuda;
    }

    public double getPenalizacionAyuda() {
        return penalizacionAyuda;
    }
}