package partida;

import kakuro.*;

public class StubVaciaKakuro {
    public static Kakuro vaciar(Kakuro k) {
        return new Kakuro(k.getAltura(),k.getAnchura());
    }
}
