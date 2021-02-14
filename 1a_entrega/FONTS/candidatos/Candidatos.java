package candidatos;



public enum Candidatos { ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9);
    private int valor;
    private Candidatos(int val) {
        this.valor = val;
    }
    public static Candidatos intTOenum (int x) {
        switch(x) {
            case 1:
              return Candidatos.ONE;
            case 2:
                return Candidatos.TWO;
            case 3:
                return Candidatos.THREE;
            case 4:
                return Candidatos.FOUR;
            case 5:
                return Candidatos.FIVE;
            case 6:
                return Candidatos.SIX;
            case 7:
                return Candidatos.SEVEN;
            case 8:
                return Candidatos.EIGHT;
            case 9:
                return Candidatos.NINE;
            default:
                return null;

          }
    }

    public static int enumTOint (Candidatos x) {
        return x.valor;
    }
}

