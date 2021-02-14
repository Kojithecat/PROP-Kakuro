package modo;

public abstract class Modo {
    String tipoModo;
    double penalizacionModo;
    protected Modo() {
        tipoModo = "No hay un tipo de modo asociado";
        penalizacionModo = -1;
    }

    public String getTipoModo() {
        return tipoModo;
    }

    public double getPenalizacionModo() {
        return penalizacionModo;
    }
}