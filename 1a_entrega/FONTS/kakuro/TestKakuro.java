package kakuro;

import casilla.*;
import pair.*;

import static org.junit.Assert.*;
import org.junit.Test;

import org.junit.*;

public class TestKakuro {
    @Test
    public void testAltura(){
        Kakuro k = new Kakuro(10, 9);
        int result = k.getAltura();
        assertEquals(10, result);
    }

    @Test
    public void testAnchura(){
        Kakuro k = new Kakuro(10, 9);
        int result = k.getAnchura();
        assertEquals(9, result);
    }

    @Test
    public void testSetCasillaBlanca(){
        Kakuro k = new Kakuro(10, 9);
        k.setCasillaKakuro(0, 1, new CasillaBlanca(new Pair(0,1)));
        Casilla cB = k.getCasillaKakuro(0, 1);
        boolean result = k.getCasillasNegras().contains(cB);
        assertFalse(result);
    }
    @Test
    public void testSetCasillaNegra(){
        Kakuro k = new Kakuro(10, 9);
        CasillaNegra cN = new CasillaNegra(new Pair(0,1));
        cN.setSumaHorizontal(25);
        k.setCasillaKakuro(0, 1, cN);
        Casilla c = k.getCasillaKakuro(0, 1);
        boolean result = k.getCasillasNegras().contains(c);
        assertTrue(result);
    }
    @Test
    public void testEsCasillaNegra(){
        Kakuro k = new Kakuro(10, 9);
        CasillaNegra cN = new CasillaNegra(new Pair(0,1));
        cN.setSumaHorizontal(25);
        k.setCasillaKakuro(0, 1, cN);
        assertTrue(k.esNegra(0, 1));
    }

    @Test
    public void testNOEsCasillaNegra(){
        Kakuro k = new Kakuro(10, 9);
        CasillaBlanca cN = new CasillaBlanca(new Pair(0,1));
        k.setCasillaKakuro(0, 1, cN);
        assertFalse(k.esNegra(0, 1));
    }

}